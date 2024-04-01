package ao.co.atech.dev.facipoint_sunmi_printer.printers.aisino

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import ao.co.atech.dev.facipoint_sunmi_printer.R
import ao.co.atech.dev.facipoint_sunmi_printer.printers.PrinterInterface
import com.vanstone.trans.api.PrinterApi
import io.flutter.plugin.common.MethodChannel

class AisinoPrinterPrinterApi(override val context: Context) : PrinterInterface {

    override val printerTag: String get() = "AisinoPrinterApi"

    override lateinit var result: MethodChannel.Result
        set

    override fun printText(text: String) {
        PrinterApi.PrnLineSpaceSet_Api(4.toShort(), 0x00)
        PrinterApi.PrnStr_Api(text)
        PrinterApi.PrnFontSet_Api(24, 24, 0)
    }

    override fun setBold(enable: Boolean) {
        PrinterApi.printSetBlodText_Api(enable)
    }

    override fun setUnderline(enable: Boolean) {
        PrinterApi.printSetLineThrough_Api(enable)
    }

    override fun setFontSize(size: Int) {
        // PrinterApi.printSetTextSize_Api(size)
        PrinterApi.PrnFontSet_Api(size, size, 0)
    }

    override fun lineWrap(lines: Int) {
        this.printText(List(lines, { "\n" }).joinToString(""))
    }

    override fun setAlign(int: Int) {
        PrinterApi.printSetAlign_Api(int)
    }

    override fun feedPaper() {
        // TODO: Get this better
        PrinterApi.printPaperFeed_Api(1)
    }

    override fun printQr(data: String, moduleSize: Int, errorLevel: Int) {
        // TODO: Check the align for the QRCode
        PrinterApi.printAddQrCode_Api(1, moduleSize, data)
    }

    override fun printRow(texts: Array<String>, width: IntArray, align: IntArray) {
        // TODO: Check this better
        if(texts.count() == 2) {
            this.printText(getText2(texts.first(), texts.last()))
        } else if(texts.count() > 2) {
            this.printText(getText3(texts, width, align))
        } else {
            this.printText(texts.joinToString(", "))
        }
    }

    override fun printBitmap(bitmap: Bitmap, orientation: Int) {
        // Using canvas
        val maxWidth = 384
        val maxHeight = 1500
        var canvasY = 0.0f

        var mainBitmap = Bitmap.createBitmap(maxWidth, maxHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(mainBitmap)
        canvas.drawColor(ContextCompat.getColor(context, R.color.white))
        canvas.save()

        val matrix = Matrix()
        matrix.postScale(1f, 1f)
        val bitmapAux = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
        canvas.drawBitmap(bitmapAux, ((maxWidth - bitmapAux.width) / 2).toFloat(), canvasY, null)

        canvasY += bitmapAux.height

        canvas.save()
        mainBitmap = Bitmap.createBitmap(mainBitmap, 0, 0, maxWidth, canvasY.toInt())
        val bundle = Bundle()
        bundle.putBoolean(PrinterApi.USE_DRIVER_PRINT, true)
        PrinterApi.PrnSetParams_Api(bundle)
        PrinterApi.printSetGray_Api(10)
        PrinterApi.PrnLogo_Api(mainBitmap)
    }

    override fun startTransaction() {
        PrinterApi.PrnClrBuff_Api()
        result.success(true)
    }

    override fun endTransaction() {
        printData()
    }

    private fun printData(): Int {
        val ret: Int
        var msg: String = ""
        while (true) {
            ret = PrinterApi.PrnStart_Api()
            Log.d("aabb", "PrnStart_Api:$ret")
            when (ret) {
                2 -> msg = "Paper is not enough"
                3 -> msg = "Printer is too hot"
                4 -> msg = "PLS put it back\nPress any key to reprint"
                5 -> msg = "Erro desconhecido de hardware com a impressora"
                6 -> msg = "A impressora não contém a fonte"
                7 -> msg = "Sobrecarga do buffer"
                8 -> msg = "Erro desconhecido com a impressora"
                9 -> msg = "Buffer da impressora vazio"
                0 -> {
                    result.success(true)
                    return 0
                }
            }
            Log.i("PrinterUtil - printData", msg);
            Toast.makeText(context,msg, Toast.LENGTH_SHORT).show()
            result.success(false)
            return -1
        }
    }

    private fun getText2(leftText: String, rightText: String) : String {
        val builder = StringBuffer(leftText)
        for (i in 0 until getSpaceCount(leftText, rightText)){
            builder.append(" ")
        }

        builder.append(rightText)
        return builder.toString()
    }

    private fun getText3(texts: Array<String>, width: IntArray, align: IntArray) : String {
        /// TODO: Check if the *width* array has an i value for the i in *texts* array
        /// TODO: Check if the *align* array has an i value for the i in *texts* array
        if((texts.size != width.size || texts.size != align.size) && texts.isNotEmpty()) {
            return texts.first()
        }

        val builder = StringBuffer()
        for (i in texts.indices) {
            val text = texts[i]

            // Count the spacing according the with of the column
            var spaceCount = width[i] - text.length

            // Only for the first element,
            // because for SUNMI the total sum of space is 31, for aisino is 32
            if (i == 0) spaceCount++

            if(align[i] == 1) { // ALIGN CENTER
                val slicedSpace: Int = kotlin.math.ceil((spaceCount / 2).toDouble()).toInt()
                setSpace(builder, slicedSpace)
                builder.append(text)
                setSpace(builder, slicedSpace)
            } else if(align[i] == 2) { // ALIGN RIGHT
                setSpace(builder, spaceCount)
                builder.append(text)
            } else { // ALIGN LEFT
                builder.append(text)
                setSpace(builder, spaceCount)
            }
        }

        return builder.toString()
    }

    private fun setSpace(builder: StringBuffer, spaceCount: Int) {
        for (i in 0 until spaceCount) builder.append(" ")
    }

    private fun getSpaceCount(leftText: String, rightText: String): Int {
        // 56
        return 32 - leftText.length - rightText.length
    }
}
