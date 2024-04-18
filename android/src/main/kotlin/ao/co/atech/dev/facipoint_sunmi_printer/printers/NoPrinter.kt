package ao.co.atech.dev.facipoint_sunmi_printer.printers

import android.content.Context
import android.graphics.Bitmap
import io.flutter.plugin.common.MethodChannel

class NoPrinter : PrinterInterface {

    override var context: Context = TODO("Not implemented")
        set
    override lateinit var result: MethodChannel.Result
        set

    override val printerTag: String get() = "NoPrinter"

    private fun result() {
        result.error("[NoPrinter] NotImplemented", "NotImplemented", null)
    }

    override fun mainInitPrinterService() {
        super.mainInitPrinterService()
        result()
    }

    override fun initPrinterService() {
        super.initPrinterService()
        result()
    }

    override fun initPrinter() {
        super.initPrinter()
        result()
    }

    override fun hasPrinter() {
        super.hasPrinter()
        result()
    }

    override fun printText(text: String) {
        super.printText(text)
        result()
    }

    override fun setBold(enable: Boolean) {
        super.setBold(enable)
        result()
    }

    override fun setUnderline(enable: Boolean) {
        super.setUnderline(enable)
        result()
    }

    override fun setFontSize(size: Int) {
        super.setFontSize(size)
        result()
    }

    override fun printerVersion() {
        super.printerVersion()
        result()
    }

    override fun deInitPrinterService() {
        super.deInitPrinterService()
        result()
    }

    override fun sendRawData(data: ByteArray) {
        super.sendRawData(data)
        result()
    }

    override fun cutPaper() {
        super.cutPaper()
        result()
    }

    override fun lineWrap(lines: Int) {
        super.lineWrap(lines)
        result()
    }

    override fun getPrinterHead() {
        super.getPrinterHead()
        result()
    }

    override fun getPrinterDistance() {
        super.getPrinterDistance()
        result()
    }

    override fun setAlign(int: Int) {
        super.setAlign(int)
        result()
    }

    override fun feedPaper() {
        super.feedPaper()
        result()
    }

    override fun printBarCode(
        data: String,
        symbology: Int,
        height: Int,
        width: Int,
        textPosition: Int
    ) {
        super.printBarCode(data, symbology, height, width, textPosition)
        result()
    }

    override fun printQr(data: String, moduleSize: Int, errorLevel: Int) {
        super.printQr(data, moduleSize, errorLevel)
        result()
    }

    override fun printRow(texts: Array<String>, width: IntArray, align: IntArray) {
        super.printRow(texts, width, align)
        result()
    }

    override fun printBitmap(bitmap: Bitmap, orientation: Int) {
        super.printBitmap(bitmap, orientation)
        result()
    }

    override fun startTransaction() {
        super.startTransaction()
        result()
    }

    override fun endTransaction() {
        super.endTransaction()
        result()
    }

    override fun openCashBox() {
        super.openCashBox()
        result()
    }

    override fun controlLcd(flag: Int) {
        super.controlLcd(flag)
        result()
    }

    override fun sendTextToLcd() {
        super.sendTextToLcd()
        result()
    }

    override fun sendTextsToLcd() {
        super.sendTextsToLcd()
        result()
    }

    override fun sendPicToLcd(pic: Bitmap) {
        super.sendPicToLcd(pic)
        result()
    }

    override fun showPrinterStatus() {
        super.showPrinterStatus()
        result()
    }

    override fun printOneLabel() {
        super.printOneLabel()
        result()
    }

    override fun printMultiLabel(count: Int) {
        super.printMultiLabel(count)
        result()
    }
}