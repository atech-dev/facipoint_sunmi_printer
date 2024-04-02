package ao.co.atech.dev.facipoint_sunmi_printer.printers

import android.content.Context
import android.graphics.Bitmap
import io.flutter.plugin.common.MethodChannel.Result

interface PrinterInterface {
    val printerTag: String get() = "Printer"
    var result: Result
    val context: Context

    fun mainInitPrinterService() {
        returnEmptyResult("mainInitPrinterService")
    }

    fun initPrinterService() {
        returnEmptyResult("initPrinterService")
    }

    fun initPrinter() {
        returnEmptyResult("initPrinter")
    }

    fun hasPrinter() {
        returnEmptyResult("hasPrinter")
    }

    fun printText(text: String) {
        returnEmptyResult("printText")
    }

    fun setBold(enable: Boolean) {
        returnEmptyResult("setBold")
    }

    fun setUnderline(enable: Boolean) {
        returnEmptyResult("setUnderline")
    }

    fun setFontSize(size: Int) {
        returnEmptyResult("setFontSize")
    }

    fun printerVersion() {
        returnEmptyResult("printerVersion")
    }

    fun deInitPrinterService() {
        returnEmptyResult("deInitPrinterService")
    }

    fun sendRawData(data: ByteArray) {
        returnEmptyResult("sendRawData")
    }

    fun cutPaper() {
        returnEmptyResult("cutPaper")
    }

    fun lineWrap(lines: Int) {
        returnEmptyResult("lineWrap")
    }

    fun getPrinterHead() {
        returnEmptyResult("getPrinterHead")
    }

    fun getPrinterDistance() {
        returnEmptyResult("getPrinterDistance")
    }

    fun setAlign(int: Int) {
        returnEmptyResult("setAlign")
    }

    fun feedPaper() {
        returnEmptyResult("feedPaper")
    }

    fun printBarCode(data: String, symbology: Int, height: Int, width: Int, textPosition: Int) {
        returnEmptyResult("printBarCode")
    }

    fun printQr(data: String, moduleSize: Int, errorLevel: Int) {
        returnEmptyResult("printQr")
    }

    fun printRow(texts: Array<String>, width: IntArray, align: IntArray) {
        returnEmptyResult("printRow")
    }

    fun printBitmap(bitmap: Bitmap, orientation: Int) {
        returnEmptyResult("printBitmap")
    }

    fun startTransaction() {
        returnEmptyResult("startTransaction")
    }

    fun endTransaction() {
        returnEmptyResult("endTransaction")
    }

    fun openCashBox() {
        returnEmptyResult("openCashBox")
    }

    fun controlLcd(flag: Int) {
        returnEmptyResult("controlLcd")
    }

    fun sendTextToLcd() {
        returnEmptyResult("sendTextToLcd")
    }

    fun sendTextsToLcd() {
        returnEmptyResult("sendTextsToLcd")
    }

    fun sendPicToLcd(pic: Bitmap) {
        returnEmptyResult("sendPicToLcd")
    }

    fun showPrinterStatus() {
        returnEmptyResult("showPrinterStatus")
    }

    fun printOneLabel() {
        returnEmptyResult("printOneLabel")
    }

    fun printMultiLabel(count: Int) {
        returnEmptyResult("printMultiLabel")
    }

    private fun returnEmptyResult(methodName: String) {
        println("[${methodName}] Not Implemented")
    }
}
