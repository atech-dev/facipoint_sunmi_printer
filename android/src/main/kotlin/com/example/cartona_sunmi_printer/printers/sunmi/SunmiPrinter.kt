package com.example.cartona_sunmi_printer.printers.sunmi

import io.flutter.plugin.common.MethodChannel
import android.content.Context
import android.graphics.Bitmap
import com.example.cartona_sunmi_printer.printers.PrinterInterface

class SunmiPrinter(override val context: Context) : PrinterInterface {

    val sunmiPrinterHelper: SunmiPrintHelper = SunmiPrintHelper.getInstance()

    override val printerTag: String get() = "SunmiPrinter"

    override lateinit var result: MethodChannel.Result
        set

    override fun initPrinter() {
        sunmiPrinterHelper.initPrinter()
        result.success(true)
    }

    override fun mainInitPrinterService() {
        sunmiPrinterHelper.initSunmiPrinterService(context)
    }

    override fun initPrinterService() {
        this.mainInitPrinterService()
        result.success(true)
    }

    override fun deInitPrinterService() {
        sunmiPrinterHelper.deInitSunmiPrinterService(context)
        result.success(true)
    }

    override fun startTransaction() {
        sunmiPrinterHelper.startTransection()
        result.success(true)
    }

    override fun endTransaction() {
        sunmiPrinterHelper.endTransection()
        result.success(true)
    }

    override fun hasPrinter() {
        var hasPrinter = false
        if (sunmiPrinterHelper.sunmiPrinter == SunmiPrintHelper.FoundSunmiPrinter) {
            hasPrinter = true
        } else if (sunmiPrinterHelper.sunmiPrinter == SunmiPrintHelper.CheckSunmiPrinter ||
            sunmiPrinterHelper.sunmiPrinter == SunmiPrintHelper.LostSunmiPrinter) {

            sunmiPrinterHelper.initSunmiPrinterService(context)
            if (sunmiPrinterHelper.sunmiPrinter == SunmiPrintHelper.FoundSunmiPrinter) {
                hasPrinter = true
            }

        }
        result.success(hasPrinter)
    }

    override fun printText(text: String) {
        sunmiPrinterHelper.printText(text)
    }

    override fun setBold(enable: Boolean) {
        sunmiPrinterHelper.setBold(enable)
    }

    override fun setUnderline(enable: Boolean) {
        sunmiPrinterHelper.setUnderline(enable)
    }

    override fun setFontSize(size: Int) {
        sunmiPrinterHelper.setFontSize(size.toFloat())
    }

    override fun printerVersion() {
        val version = sunmiPrinterHelper.printerVersion

        result.success("Printer Version: $version")
    }

    override fun sendRawData(data: ByteArray) {
        sunmiPrinterHelper.sendRawData(data)
        result.success(true)
    }

    override fun cutPaper() {
        sunmiPrinterHelper.cutpaper()
    }

    override fun lineWrap(lines: Int) {
        sunmiPrinterHelper.lineWrap(lines)
    }

    override fun getPrinterHead() {
        sunmiPrinterHelper.getPrinterHead(null)
        result.success(true)
    }

    override fun getPrinterDistance() {
        sunmiPrinterHelper.getPrinterDistance(null)
        result.success(true)
    }

    override fun setAlign(int: Int) {
        sunmiPrinterHelper.setAlign(int)
    }

    override fun feedPaper() {
        sunmiPrinterHelper.feedPaper()
    }

    override fun printBarCode(data: String, symbology: Int, height: Int, width: Int, textPosition: Int) {
        sunmiPrinterHelper.printBarCode(data, symbology, height, width, textPosition)
        result.success(true)
    }

    override fun printQr(data: String, moduleSize: Int, errorLevel: Int) {
        sunmiPrinterHelper.printQr(data, moduleSize, errorLevel)
    }

    override fun printRow(texts: Array<String>, width: IntArray, align: IntArray) {
        sunmiPrinterHelper.printRow(texts, width, align);
    }

    override fun printBitmap(bitmap: Bitmap, orientation: Int) {
        sunmiPrinterHelper.printBitmap(bitmap, orientation)
    }

    override fun openCashBox() {
        sunmiPrinterHelper.openCashBox()
        result.success(true)
    }

    override fun controlLcd(flag: Int) {
        sunmiPrinterHelper.controlLcd(flag)
        result.success(true)
    }

    override fun sendTextToLcd() {
        sunmiPrinterHelper.sendTextToLcd()
        result.success(true)
    }

    override fun sendTextsToLcd() {
        sunmiPrinterHelper.sendTextsToLcd()
        result.success(true)
    }

    override fun sendPicToLcd(pic: Bitmap) {
        sunmiPrinterHelper.sendPicToLcd(pic)
        result.success(true)
    }

    override fun showPrinterStatus() {
        sunmiPrinterHelper.showPrinterStatus(context)
        result.success(true)
    }

    override fun printOneLabel() {
        sunmiPrinterHelper.printOneLabel()
        result.success(true)
    }

    override fun printMultiLabel(count: Int) {
        sunmiPrinterHelper.printMultiLabel(count)
        result.success(true)
    }
}