package com.example.cartona_sunmi_printer

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.annotation.NonNull
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import org.json.JSONArray

const val initPrinter = "INIT_PRINTER"
const val printerVersion = "PRINTER_VERSION"
const val printText = "PRINT_TEXT"
const val setBold = "BOLD"
const val setUnderline = "UNDERLINE"
const val setFontSize = "FONT_SIZE"
const val initSunmiPrinterService = "INIT_SUNMI_PRINTER_SERVICE"
const val deInitSunmiPrinterService = "DEINIT_SUNMI_PRINTER_SERVICE"
const val sendRawData = "SEND_RAW_DATA"
const val cutPaper = "CUT_PAPER"
const val lineWrap = "LINE_WRAP"
const val getPrinterHead = "GET_PRINTER_HEAD"
const val getPrinterDistance = "GET_PRINTER_DISTANCE"
const val setAlign = "SET_ALIGN"
const val feedPaper = "FEED_PAPER"
const val printBarCode = "PRINT_BARCODE"
const val printQr = "PRINT_QR"
const val printRow = "PRINT_ROW"
const val printBitmap = "PRINT_BITMAP"
const val startTrans = "START_TRANS"
const val endTrans = "END_TRANS"
const val openCashBox = "OPEN_CASHBOX"
const val controlLcd = "CONTROL_LCD"
const val sendTextToLcd = "SEND_TEXT_TO_LCD"
const val sendTextsToLcd = "SEND_TEXTS_TO_LCD"
const val sendPicToLcd = "SEND_PIC_TO_LCD"
const val showPrinterStatus = "SHOW_PRINTER_STATUS"
const val printOneLabel = "PRINT_ONE_LABEL"
const val printMultiLabel = "PRINT_MULTI_LABEL"

/** CartonaSunmiPrinterPlugin */
class CartonaSunmiPrinterPlugin : FlutterPlugin, MethodCallHandler {

    private lateinit var context: Context
    private var channel: MethodChannel? = null
    private val printer: SunmiPrintHelper = SunmiPrintHelper.getInstance()

    override fun onAttachedToEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        context = binding.applicationContext
        channel = MethodChannel(binding.binaryMessenger, "cartona_sunmi_printer")
        printer.initSunmiPrinterService(context)
        channel?.setMethodCallHandler(this)
        println("[CartonaSunmiPrinter] Initialization Success")
    }

    override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) =
        when (call.method) {
            initPrinter -> {
                println("[CartonaSunmiPrinter] Init printer")
                printer.initPrinter()
                result.success(true)
            }
            printText -> {
                call.argument<String?>("text")?.let {
                    println("[CartonaSunmiPrinter] Printing text: $it")
                    printer.printText(it)
                    result.success(true)
                } ?: result.success(false)
            }
            setBold -> {
                call.argument<Boolean?>("enable")?.let {
                    println("[CartonaSunmiPrinter] Set bold: $it")
                    printer.setBold(it)
                    result.success(true)
                } ?: result.success(false)
            }
            setUnderline -> {
                call.argument<Boolean?>("enable")?.let {
                    println("[CartonaSunmiPrinter] Set underline: $it")
                    printer.setUnderline(it)
                    result.success(true)
                } ?: result.success(false)
            }
            setFontSize -> {
                call.argument<Double?>("fontSize")?.let {
                    println("[CartonaSunmiPrinter] Set font size: $it")
                    printer.setFontSize(it.toFloat())
                    result.success(true)
                } ?: result.success(false)
            }
            printerVersion -> {
                println("[CartonaSunmiPrinter] Printer Version: ${printer.printerVersion}")
                result.success("Printer Version: ${printer.printerVersion}")
            }
            initSunmiPrinterService -> {
                println("[CartonaSunmiPrinter] Init sunmi printer service")
                printer.initSunmiPrinterService(context);
                result.success(true)
            }
            deInitSunmiPrinterService -> {
                println("[CartonaSunmiPrinter] De-init sunmi printer service")
                printer.deInitSunmiPrinterService(context);
                result.success(true)
            }
            sendRawData -> {
                call.argument<ByteArray?>("bytes")?.let {
                    println("[CartonaSunmiPrinter] Printing raw data: $it")
                    printer.sendRawData(it)
                    result.success(true)
                } ?: result.success(false)
            }
            cutPaper -> {
                println("[CartonaSunmiPrinter] Cut paper")
                printer.cutpaper();
                result.success(true)
            }
            lineWrap -> {
                call.argument<Int?>("lines")?.let {
                    println("[CartonaSunmiPrinter] Print line wraps: $it")
                    printer.lineWrap(it);
                    result.success(true)
                } ?: result.success(false)
            }
            getPrinterHead -> {
                println("[CartonaSunmiPrinter] Get printer head")
                printer.getPrinterHead(null);
                result.success(true)
            }
            getPrinterDistance -> {
                println("[CartonaSunmiPrinter] Get printer distance")
                printer.getPrinterDistance(null);
                result.success(true)
            }
            setAlign -> {
                call.argument<Int?>("align")?.let {
                    println("[CartonaSunmiPrinter] Set align: $it")
                    printer.setAlign(it);
                    result.success(true)
                } ?: result.success(false)
            }
            feedPaper -> {
                println("[CartonaSunmiPrinter] Feed paper")
                printer.feedPaper();
                result.success(true)
            }
            printBarCode -> {
                val data = call.argument<String>("data")
                val symbology = call.argument<Int>("symbology")
                val height = call.argument<Int>("height")
                val width = call.argument<Int>("width")
                val position = call.argument<Int>("position")
                val hasData = listOf(data, symbology, height, width, position).all { it != null }
                if (hasData) {
                    println("[CartonaSunmiPrinter] Printing barcode: $data")
                    printer.printBarCode(data, symbology!!, height!!, width!!, position!!);
                    result.success(true)
                } else {
                    println("[CartonaSunmiPrinter] No data to print barcode")
                    result.success(false)
                }
            }
            printQr -> {
                val data = call.argument<String>("data")
                val moduleSize = call.argument<Int>("moduleSize")
                val errorLevel = call.argument<Int>("errorLevel")
                val hasData = listOf(data, moduleSize, errorLevel).all { it != null }
                if (hasData) {
                    println("[CartonaSunmiPrinter] Printing QR: $data")
                    printer.printQr(data, moduleSize!!, errorLevel!!);
                    result.success(true)
                } else {
                    println("[CartonaSunmiPrinter] No data to print QR code")
                    result.success(false)
                }
            }
            printRow -> {
                val textsJson = call.argument<String>("texts")
                val widthStr = call.argument<String>("width")
                val alignStr = call.argument<String>("align")

                val texts = textsJson?.let {
                    val texts = mutableListOf<String>()
                    val jsonArray = JSONArray(it)
                    for (i in 0 until jsonArray.length()) {
                        texts.add(jsonArray.getString(i))
                    }
                    texts.toTypedArray()
                } ?: emptyArray()
                val width =
                    widthStr?.split(",")?.map { it.toInt() }?.toIntArray() ?: intArrayOf()
                val align =
                    alignStr?.split(",")?.map { it.toInt() }?.toIntArray() ?: intArrayOf()
                val hasData = listOf(texts, width, align).all { it != null }
                if (hasData) {
                    println("[CartonaSunmiPrinter] Printing row: ${texts!!.joinToString()}")
                    printer.printRow(texts!!, width!!, align!!);
                    result.success(true)
                } else {
                    println("[CartonaSunmiPrinter] No data to print row")
                    result.success(false)
                }
            }
            printBitmap -> {
                val bytes = call.argument<ByteArray>("bitmap")
                val orientation = call.argument<Int>("orientation")
                val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes!!.size)
                val hasData = listOf(bitmap, orientation).all { it != null }
                if (hasData) {
                    println("[CartonaSunmiPrinter] Printing bitmap $bitmap")
                    printer.printBitmap(bitmap, orientation!!);
                    result.success(true)
                } else {
                    println("[CartonaSunmiPrinter] No data to print bitmap")
                    result.success(false)
                }
            }
            startTrans -> {
                println("[CartonaSunmiPrinter] Start print transaction")
                printer.startTransection();
                result.success(true)
            }
            endTrans -> {
                println("[CartonaSunmiPrinter] End print transaction")
                printer.endTransection();
                result.success(true)
            }
            openCashBox -> {
                println("[CartonaSunmiPrinter] Open cash box")
                printer.openCashBox();
                result.success(true)
            }
            controlLcd -> {
                call.argument<Int>("flag")?.let {
                    println("[CartonaSunmiPrinter] Control LCD: $it")
                    printer.controlLcd(it);
                    result.success(true)
                } ?: result.success(false)
            }
            sendTextToLcd -> {
                println("[CartonaSunmiPrinter] Send text to LCD")
                printer.sendTextToLcd();
                result.success(true)
            }
            sendTextsToLcd -> {
                println("[CartonaSunmiPrinter] Send texts to LCD")
                printer.sendTextToLcd();
                result.success(true)
            }
            sendPicToLcd -> {
                call.argument<Bitmap>("bitmap")?.let {
                    println("[CartonaSunmiPrinter] Send pic to LCD: $it")
                    printer.sendPicToLcd(it);
                    result.success(true)
                } ?: result.success(false)
            }
            showPrinterStatus -> {
                println("[CartonaSunmiPrinter] Show printer status")
                printer.showPrinterStatus(context);
                result.success(true)
            }
            printOneLabel -> {
                println("[CartonaSunmiPrinter] Print one label")
                printer.printOneLabel();
                result.success(true)
            }
            printMultiLabel -> {
                call.argument<Int>("count")?.let {
                    println("[CartonaSunmiPrinter] Print $it labels")
                    printer.printMultiLabel(it);
                    result.success(true)
                } ?: result.success(false)
            }
            else -> result.notImplemented()
        }

    override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        channel?.setMethodCallHandler(null)
    }
}