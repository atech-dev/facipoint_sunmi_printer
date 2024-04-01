package ao.co.atech.dev.facipoint_sunmi_printer.printers.aisino

import android.content.Context
import android.content.res.AssetManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.DashPathEffect
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PathEffect
import android.graphics.Rect
import android.graphics.Typeface
import android.os.Bundle
import androidx.core.content.ContextCompat
import ao.co.atech.dev.facipoint_sunmi_printer.R
import ao.co.atech.dev.facipoint_sunmi_printer.printers.PrinterInterface
import com.vanstone.trans.api.PrinterApi
import io.flutter.plugin.common.MethodChannel

class AisinoPrinterCanva (override val context: Context) : PrinterInterface {

    override val printerTag: String get() = "AisinoPrinterCanva"

    override lateinit var result: MethodChannel.Result
        set

    private var bitmap: Bitmap? = null
    private var canvas: Canvas? = null
    val manager: AssetManager = context.assets
    private var font: Typeface? = null

    val maxWidth: Int = 384
    val maxHeight: Int = 1500

    var contentWidth = 0
    var contentHeight = 0
    var canvasX = 0
    var canvasY = 0.0f

    val rect = Rect()
    val paint = Paint()

    override fun printText(text: String) {
        // Line spacing
        canvasY += 10

        paint.getTextBounds(text, 0, text.length, rect)
        contentHeight = rect.height()
        canvasY += contentHeight.toFloat()
        canvas?.drawText(text, 0f, canvasY, paint)
    }

    override fun setBold(enable: Boolean) {
        if(enable) {
            paint.typeface = Typeface.createFromAsset(manager, "gomonob.ttf")
        } else {
            paint.typeface = Typeface.createFromAsset(manager, "gomono.ttf")
        }
    }

    override fun setUnderline(enable: Boolean) {
        super.setUnderline(enable)
    }

    override fun setFontSize(size: Int) {
        paint.textSize = size.toFloat()
    }

    override fun lineWrap(lines: Int) {
        canvasY += (lines * 10).toFloat()
    }

    override fun setAlign(int: Int) {
        var textAlign = Paint.Align.LEFT
        if(int == 1) {
            textAlign = Paint.Align.CENTER
        } else if(int == 2) {
            textAlign = Paint.Align.RIGHT
        }
        paint.textAlign = textAlign
    }

    override fun feedPaper() {
        super.feedPaper()
    }

    override fun printQr(data: String, moduleSize: Int, errorLevel: Int) {
        super.printQr(data, moduleSize, errorLevel)
    }

    override fun printRow(texts: Array<String>, width: IntArray, align: IntArray) {
        // TODO: Check this better
        if(texts.count() == 2) {
            canvasY = drawSingleLineText(canvas!!, paint, rect, texts.first(), texts.last(), canvasY)
        } else {
            this.printText(texts.joinToString(", "))
        }
    }

    fun line() {
        val linePath = Path()
        val pathEffect: PathEffect = DashPathEffect(floatArrayOf(10f, 5f), 0f)

        canvasY = drawLine(canvas!!, paint, linePath, pathEffect, canvasY)
    }

    override fun printBitmap(bitmap: Bitmap, orientation: Int) {
        // var bmpSignature = BitmapFactory.decodeByteArray(TransParams.signature, 0, TransParams.signatureLen)
        val matrix = Matrix()
        matrix.postScale(1.5f, 1.5f) //长和宽放大缩小的比例
        var bitmapAux = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
        canvas!!.drawBitmap(bitmapAux, ((maxWidth - bitmapAux.width) / 2).toFloat(), canvasY, paint)

        // TODO Maybe line space
        canvasY += bitmapAux.height + 20
    }

    override fun startTransaction() {
        paint.color = ContextCompat.getColor(context, R.color.black)
        paint.style = Paint.Style.FILL
        paint.isAntiAlias = true
        // val manager: AssetManager = this@TicketActivity.assets
        // var font = Typeface.createFromAsset(manager, "gomonob.ttf")
        // paint.typeface = font

        bitmap = Bitmap.createBitmap(maxWidth, maxHeight, Bitmap.Config.ARGB_8888)
        canvas = Canvas(bitmap!!)
        canvas!!.drawColor(ContextCompat.getColor(context, R.color.white))
        canvas!!.save()

        font = Typeface.createFromAsset(manager, "gomono.ttf")
        paint.typeface = font
        paint.textSize = 20f
        paint.strokeWidth = 10f

        result.success(true)
    }

    override fun endTransaction() {
        canvasY += 120f
        canvas!!.save()
        bitmap = Bitmap.createBitmap(bitmap!!, 0, 0, maxWidth, canvasY.toInt())

        PrinterApi.PrnClrBuff_Api()
        val bundle = Bundle()
        bundle.putBoolean(PrinterApi.USE_DRIVER_PRINT, true)
        PrinterApi.PrnSetParams_Api(bundle)
        PrinterApi.printSetGray_Api(10)
        PrinterApi.PrnLogo_Api(bitmap)
        PrinterApi.PrnStart_Api()

        result.success(true)
    }

    private fun drawSingleLineText(canvas: Canvas, paint: Paint, rect: Rect, leftText: String, rightText: String, canvasY: Float): Float {
        var canvasY = canvasY
        paint.textAlign = Paint.Align.LEFT
        paint.getTextBounds(leftText, 0, leftText.length, rect)
        canvasY += (22 + 6) // 22: set each line of text takes 22 pixels 10: set line space to 10 pixels
        canvas.drawText(leftText, 0f, canvasY, paint)
        paint.textAlign = Paint.Align.RIGHT
        paint.getTextBounds(rightText, 0, rightText.length, rect)
        canvas.drawText(rightText, maxWidth.toFloat(), canvasY, paint)
        return canvasY
    }

    private fun drawLine(canvas: Canvas, paint: Paint, path: Path, pathEffect: PathEffect, canvasY: Float): Float {
        var canvasY = canvasY
        canvasY += 10
        path.moveTo(0f, canvasY)
        path.lineTo(maxWidth.toFloat(), canvasY)
        paint.pathEffect = pathEffect
        paint.strokeWidth = 1f
        canvas.drawLine(0f, canvasY, maxWidth.toFloat(), canvasY, paint)
        return canvasY
    }
}
