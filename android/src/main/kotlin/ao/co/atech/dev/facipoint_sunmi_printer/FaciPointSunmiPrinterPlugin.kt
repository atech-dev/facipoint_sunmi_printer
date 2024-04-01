package ao.co.atech.dev.facipoint_sunmi_printer

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.annotation.NonNull
import ao.co.atech.dev.facipoint_sunmi_printer.printers.PrinterInterface
import ao.co.atech.dev.facipoint_sunmi_printer.printers.aisino.AisinoPrinter
import ao.co.atech.dev.facipoint_sunmi_printer.printers.sunmi.SunmiPrintHelper
import ao.co.atech.dev.facipoint_sunmi_printer.printers.sunmi.SunmiPrinter
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import org.json.JSONArray

const val hasPrinter = "HAS_PRINTER"
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

/** FaciPointSunmiPrinterPlugin */
class FaciPointSunmiPrinterPlugin : FlutterPlugin, MethodCallHandler {

    private lateinit var context: Context
    private var channel: MethodChannel? = null

    // Configured Printers
    private lateinit var printer: PrinterInterface
    private lateinit var sunmiPrinter: SunmiPrinter
    private lateinit var aisinoPrinter: AisinoPrinter

    override fun onAttachedToEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        context = binding.applicationContext
        channel = MethodChannel(binding.binaryMessenger, "facipoint_sunmi_printer")

        initPrinter()

        channel?.setMethodCallHandler(this)
    }

    override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
        printer.result = result
        return when (call.method) {
            initPrinter -> {
                println("[${printer.printerTag}] Init printer")
                printer.initPrinter()
            }

            hasPrinter -> printer.hasPrinter()

            printText -> {
                call.argument<String?>("text")?.let {
                    println("[${printer.printerTag}] Printing text: $it")
                    printer.printText(it)
                    result.success(true)
                } ?: result.success(false)
            }

            setBold -> {
                call.argument<Boolean?>("enable")?.let {
                    println("[${printer.printerTag}] Set bold: $it")
                    printer.setBold(it)
                    result.success(true)
                } ?: result.success(false)
            }

            setUnderline -> {
                call.argument<Boolean?>("enable")?.let {
                    println("[${printer.printerTag}] Set underline: $it")
                    printer.setUnderline(it)
                    result.success(true)
                } ?: result.success(false)
            }

            setFontSize -> {
                call.argument<Int?>("fontSize")?.let {
                    println("[${printer.printerTag}] Set font size: $it")
                    printer.setFontSize(it)
                    result.success(true)
                } ?: result.success(false)
            }

            printerVersion -> {
                println("[${printer.printerTag}] Printer Version: ${printer.printerVersion()}")
            }

            initSunmiPrinterService -> {
                println("[${printer.printerTag}] Init printer service")
                printer.initPrinterService()
            }

            deInitSunmiPrinterService -> {
                println("[${printer.printerTag}] De-init printer service")
                printer.deInitPrinterService()
            }

            sendRawData -> {
                call.argument<ByteArray?>("bytes")?.let {
                    println("[${printer.printerTag}] Printing raw data: $it")
                    printer.sendRawData(it)
                } ?: result.success(false)
            }

            cutPaper -> {
                println("[${printer.printerTag}] Cut paper")
                printer.cutPaper();
                result.success(true)
            }

            lineWrap -> {
                call.argument<Int?>("lines")?.let {
                    println("[${printer.printerTag}] Print line wraps: $it")
                    printer.lineWrap(it);
                    result.success(true)
                } ?: result.success(false)
            }

            getPrinterHead -> {
                println("[${printer.printerTag}] Get printer head")
                printer.getPrinterHead()
            }

            getPrinterDistance -> {
                println("[${printer.printerTag}] Get printer distance")
                printer.getPrinterDistance();
            }

            setAlign -> {
                call.argument<Int?>("align")?.let {
                    println("[${printer.printerTag}] Set align: $it")
                    printer.setAlign(it);
                    result.success(true)
                } ?: result.success(false)
            }

            feedPaper -> {
                println("[${printer.printerTag}] Feed paper")
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
                    println("[${printer.printerTag}] Printing barcode: $data")
                    printer.printBarCode(data!!, symbology!!, height!!, width!!, position!!);
                } else {
                    println("[${printer.printerTag}] No data to print barcode")
                    result.success(false)
                }
            }

            printQr -> {
                val data = call.argument<String>("data")
                val moduleSize = call.argument<Int>("moduleSize")
                val errorLevel = call.argument<Int>("errorLevel")
                val hasData = listOf(data, moduleSize, errorLevel).all { it != null }
                if (hasData) {
                    println("[${printer.printerTag}] Printing QR: $data")
                    printer.printQr(data!!, moduleSize!!, errorLevel!!);
                    result.success(true)
                } else {
                    println("[${printer.printerTag}] No data to print QR code")
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
                    println("[${printer.printerTag}] Printing row: ${texts!!.joinToString()}")
                    printer.printRow(texts, width, align);
                    result.success(true)
                } else {
                    println("[${printer.printerTag}] No data to print row")
                    result.success(false)
                }
            }

            printBitmap -> {
                val bytes = call.argument<ByteArray>("bitmap")
                val orientation = call.argument<Int>("orientation")
                val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes!!.size)
                val hasData = listOf(bitmap, orientation).all { it != null }
                if (hasData) {
                    println("[${printer.printerTag}] Printing bitmap $bitmap")
                    printer.printBitmap(bitmap, orientation!!);
                    result.success(true)
                } else {
                    println("[${printer.printerTag}] No data to print bitmap")
                    result.success(false)
                }
            }

            startTrans -> {
                println("[${printer.printerTag}] Start print transaction")
                printer.startTransaction();
            }

            endTrans -> {
                println("[${printer.printerTag}] End print transaction")
                printer.endTransaction()
            }

            openCashBox -> {
                println("[${printer.printerTag}] Open cash box")
                printer.openCashBox()
            }

            controlLcd -> {
                call.argument<Int>("flag")?.let {
                    println("[${printer.printerTag}] Control LCD: $it")
                    printer.controlLcd(it)
                } ?: result.success(false)
            }

            sendTextToLcd -> {
                println("[${printer.printerTag}] Send text to LCD")
                printer.sendTextToLcd();
            }

            sendTextsToLcd -> {
                println("[${printer.printerTag}] Send texts to LCD")
                printer.sendTextToLcd()
            }

            sendPicToLcd -> {
                call.argument<Bitmap>("bitmap")?.let {
                    println("[${printer.printerTag}] Send pic to LCD: $it")
                    printer.sendPicToLcd(it)
                } ?: result.success(false)
            }

            showPrinterStatus -> {
                println("[${printer.printerTag}] Show printer status")
                printer.showPrinterStatus()
            }

            printOneLabel -> {
                println("[${printer.printerTag}] Print one label")
                printer.printOneLabel()
            }

            printMultiLabel -> {
                call.argument<Int>("count")?.let {
                    println("[${printer.printerTag}] Print $it labels")
                    printer.printMultiLabel(it)
                } ?: result.success(false)
            }

            else -> result.notImplemented()
        }
    }

    override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        channel?.setMethodCallHandler(null)
    }

    private fun initPrinter() {
        // Init configured printers
        sunmiPrinter = SunmiPrinter(context)
        aisinoPrinter = AisinoPrinter(context)

        // Try to init sunmi printer
        sunmiPrinter.mainInitPrinterService()
        // In case of error in init
        println("PRTER: ${sunmiPrinter.sunmiPrinterHelper.sunmiPrinter}")
        printer = if(sunmiPrinter.sunmiPrinterHelper.sunmiPrinter != SunmiPrintHelper.NoSunmiPrinter) {
            sunmiPrinter
        } else {
            // In case of error, try to init the printer service
            aisinoPrinter.mainInitPrinterService()
            aisinoPrinter
        }

        println("[${printer.printerTag}] Initialization")
    }
}
