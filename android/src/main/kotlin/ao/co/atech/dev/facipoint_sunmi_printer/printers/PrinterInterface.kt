package ao.co.atech.dev.facipoint_sunmi_printer.printers

import android.content.Context
import android.graphics.Bitmap
import io.flutter.plugin.common.MethodChannel.Result

interface PrinterInterface {
    val printerTag: String get() = "Printer"
    var result: Result
    val context: Context

    fun mainInitPrinterService() {
        result.notImplemented()
    }

    fun initPrinterService() {
        result.notImplemented()
    }

    fun initPrinter() {
        result.notImplemented()
    }

    fun hasPrinter() {
        result.notImplemented()
    }

    fun printText(text: String) {
        result.notImplemented()
    }

    fun setBold(enable: Boolean) {
        result.notImplemented()
    }

    fun setUnderline(enable: Boolean) {
        result.notImplemented()
    }

    fun setFontSize(size: Int) {
        result.notImplemented()
    }

    fun printerVersion() {
        result.notImplemented()
    }

    fun deInitPrinterService() {
        result.notImplemented()
    }

    fun sendRawData(data: ByteArray) {
        result.notImplemented()
    }

    fun cutPaper() {
        result.notImplemented()
    }

    fun lineWrap(lines: Int) {
        result.notImplemented()
    }

    fun getPrinterHead() {
        result.notImplemented()
    }

    fun getPrinterDistance() {
        result.notImplemented()
    }

    fun setAlign(int: Int) {
        result.notImplemented()
    }

    fun feedPaper() {
        result.notImplemented()
    }

    fun printBarCode(data: String, symbology: Int, height: Int, width: Int, textPosition: Int) {
        result.notImplemented()
    }

    fun printQr(data: String, moduleSize: Int, errorLevel: Int) {
        result.notImplemented()
    }

    fun printRow(texts: Array<String>, width: IntArray, align: IntArray) {
        result.notImplemented()
    }

    fun printBitmap(bitmap: Bitmap, orientation: Int) {
        result.notImplemented()
    }

    fun startTransaction() {
        result.notImplemented()
    }

    fun endTransaction() {
        result.notImplemented()
    }

    fun openCashBox() {
        result.notImplemented()
    }

    fun controlLcd(flag: Int) {
        result.notImplemented()
    }

    fun sendTextToLcd() {
        result.notImplemented()
    }

    fun sendTextsToLcd() {
        result.notImplemented()
    }

    fun sendPicToLcd(pic: Bitmap) {
        result.notImplemented()
    }

    fun showPrinterStatus() {
        result.notImplemented()
    }

    fun printOneLabel() {
        result.notImplemented()
    }

    fun printMultiLabel(count: Int) {
        result.notImplemented()
    }
}
