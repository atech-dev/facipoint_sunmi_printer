package ao.co.atech.dev.facipoint_sunmi_printer.printers.aisino

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import ao.co.atech.dev.facipoint_sunmi_printer.printers.PrinterInterface
import com.vanstone.appsdk.client.ISdkStatue
import com.vanstone.l2.Common
import com.vanstone.l2.CommonCB
import com.vanstone.trans.api.CommApi
import com.vanstone.trans.api.PedApi
import com.vanstone.trans.api.PrinterApi
import com.vanstone.trans.api.SystemApi
import com.vanstone.utils.ByteUtils
import com.vanstone.utils.CommonConvert
import io.flutter.plugin.common.MethodChannel

class AisinoPrinter(override val context: Context) : PrinterInterface {

    // Configured printing types
    private val aisinoPrinterApi: AisinoPrinterPrinterApi = AisinoPrinterPrinterApi(context)
    private val aisinoPrinterCanva: AisinoPrinterCanva = AisinoPrinterCanva(context)

    private var printer: PrinterInterface = aisinoPrinterApi

    override val printerTag: String get() = printer.printerTag

    override var result: MethodChannel.Result
        get() = TODO()
        set(v) {printer.result = v}

    override fun initPrinter() {
        initSDK()
    }

    override fun mainInitPrinterService() {
        initSDK(false)
    }

    override fun initPrinterService() {
        initSDK()
    }

    override fun deInitPrinterService() {
        printer.result.success(false)
    }

    override fun startTransaction() {
        printer.startTransaction()
    }

    override fun endTransaction() {
        printer.endTransaction()
    }

    override fun hasPrinter() {
        printer.hasPrinter()
    }

    override fun printText(text: String) {
        printer.printText(text)
    }

    override fun setBold(enable: Boolean) {
        printer.setBold(enable)
    }

    override fun setUnderline(enable: Boolean) {
        printer.setUnderline(enable)
    }

    override fun setFontSize(size: Int) {
        printer.setFontSize(size)
    }

    override fun printerVersion() {
        printer.result.success(false)
    }

    override fun sendRawData(data: ByteArray) {
        printer.result.success(false)
    }

    override fun cutPaper() {
        PrinterApi.PrnCut_Api()
    }

    override fun lineWrap(lines: Int) {
        printer.lineWrap(lines)
    }

    override fun getPrinterHead() {
        printer.result.success(false)
    }

    override fun getPrinterDistance() {
        printer.result.success(false)
    }

    override fun setAlign(int: Int) {
        printer.setAlign(int)
    }

    override fun feedPaper() {
        printer.feedPaper()
    }

    override fun printBarCode(data: String, symbology: Int, height: Int, width: Int, textPosition: Int) {
        printer.result.success(true)
    }

    override fun printQr(data: String, moduleSize: Int, errorLevel: Int) {
        printer.printQr(data, moduleSize, errorLevel)
    }

    override fun printRow(texts: Array<String>, width: IntArray, align: IntArray) {
        printer.printRow(texts, width, align)
    }

    override fun printBitmap(bitmap: Bitmap, orientation: Int) {
        printer.printBitmap(bitmap, orientation)
    }

    override fun openCashBox() {
        printer.result.success(false)
    }

    override fun controlLcd(flag: Int) {
        printer.result.success(false)
    }

    override fun sendTextToLcd() {
        printer.result.success(false)
    }

    override fun sendTextsToLcd() {
        printer.result.success(false)
    }

    override fun sendPicToLcd(pic: Bitmap) {
        printer.result.success(false)
    }

    override fun showPrinterStatus() {
        printer.result.success(false)
    }

    override fun printOneLabel() {
        printer.result.success(false)
    }

    override fun printMultiLabel(count: Int) {
        printer.result.success(false)
    }

    // region Other
    private fun initSDK(returnResult: Boolean = true) {
        val curAppDir = context.filesDir.absolutePath
        SystemApi.SystemInit_Api(
            0,
            CommonConvert.StringToBytes("$curAppDir/\u0000"),
            context,
            object :
                ISdkStatue {
                override fun sdkInitSuccessed() {
                    Log.e("init", "success")

                    CommApi.InitComm_Api(context)
                    Common.Init_Api()
                    Common.DbgEN_Api(1)

                    Common.setCallback(ccb)

                    if (returnResult) {
                        printer.result.success(true)
                    }
                }

                override fun sdkInitFailed() {
                    Log.e("init", "failed")

                    if (returnResult) {
                        // printer.result.success(false)
                    }
                }
            })
    }

    private val ccb: CommonCB = object : CommonCB {
        override fun GetDateTime(bytes: ByteArray): Int {
            val dateTime = ByteArray(10)
            SystemApi.GetSysTime_Api(dateTime)
            ByteUtils.memcpy(bytes, 0, dateTime, 1, 6)
            return 0
        }

        override fun ReadSN(bytes: ByteArray): Int {
            val sTemp = ByteArray(32)
            if (PedApi.PEDReadPinPadSn_Api(sTemp) == 0x00) {
                val nLen = (sTemp[0] - 0x30) * 10 + (sTemp[1] - 0x30) + 2
                if (nLen > 11) ByteUtils.memcpy(
                    bytes,
                    0,
                    sTemp,
                    2 + nLen - 11,
                    11
                ) else ByteUtils.memcpy(bytes, 0, sTemp, 2, nLen)
            }
            return 0
        }

        override fun GetUnknowTLV(i: Int, bytes: ByteArray, i1: Int): Int {
            return -1
        }
    }
    // endregion
}
