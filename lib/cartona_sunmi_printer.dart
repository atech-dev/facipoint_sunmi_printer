import 'dart:async';
import 'dart:convert';
import 'dart:typed_data';

import 'package:flutter/services.dart';

const commandHasPrinter = "HAS_PRINTER";
const commandInitPrinter = "INIT_PRINTER";
const commandPrinterVersion = "PRINTER_VERSION";
const commandPrintText = "PRINT_TEXT";
const commandSetBold = "BOLD";
const commandSetUnderline = "UNDERLINE";
const commandSetFontSize = "FONT_SIZE";
const commandInitSunmiPrinterService = "INIT_SUNMI_PRINTER_SERVICE";
const commandDeInitSunmiPrinterService = "DEINIT_SUNMI_PRINTER_SERVICE";
const commandSendRawData = "SEND_RAW_DATA";
const commandCutPaper = "CUT_PAPER";
const commandLineWrap = "LINE_WRAP";
const commandGetPrinterHead = "GET_PRINTER_HEAD";
const commandGetPrinterDistance = "GET_PRINTER_DISTANCE";
const commandSetAlign = "SET_ALIGN";
const commandFeedPaper = "FEED_PAPER";
const commandPrintBarCode = "PRINT_BARCODE";
const commandPrintQr = "PRINT_QR";
const commandPrintRow = "PRINT_ROW";
const commandPrintBitmap = "PRINT_BITMAP";
const commandStartTrans = "START_TRANS";
const commandEndTrans = "END_TRANS";
const commandOpenCashBox = "OPEN_CASHBOX";
const commandShowPrinterStatus = "SHOW_PRINTER_STATUS";
const commandPrintOneLabel = "PRINT_ONE_LABEL";
const commandPrintMultiLabel = "PRINT_MULTI_LABEL";

enum SunmiPrintAlign {
  left,
  center,
  right,
}

class CartonaSunmiPrinter {
  static const MethodChannel _channel = MethodChannel('cartona_sunmi_printer');

  /// Used to initialize the printer, must only be called once at the startup
  static Future<bool> initializePrinter() async {
    return await _channel.invokeMethod(commandInitPrinter);
  }

  /// Used to initialize the printer, must only be called once at the startup
  static Future<bool> hasPrinter() async {
    return await _channel.invokeMethod(commandHasPrinter);
  }

  /// Returns the printer version on the devices
  static Future<String?> getPrinterVersion() async {
    final String? version = await _channel.invokeMethod(commandPrinterVersion);
    return version;
  }

  /// Prints the given text to the receipt, font and methods relating to other
  /// settings must be called before this method
  static Future<void> printText(String text) async {
    final args = <String, dynamic>{"text": '$text\n'};
    await _channel.invokeMethod(commandPrintText, args);
  }

  /// Sets the font to bold
  static Future<void> setBold(bool enable) async {
    final args = <String, dynamic>{"enable": enable};
    await _channel.invokeMethod(commandSetBold, args);
  }

  /// Sets the font to underline
  static Future<void> setUnderline(bool enable) async {
    final args = <String, dynamic>{"enable": enable};
    await _channel.invokeMethod(commandSetUnderline, args);
  }

  /// Sets the font size
  static Future<void> setFontSize([double size = 24]) async {
    final args = <String, dynamic>{"fontSize": size};
    await _channel.invokeMethod(commandSetFontSize, args);
  }

  /// Sets the alignment of the text
  static Future<void> setAlignment(SunmiPrintAlign alignment) async {
    final args = <String, dynamic>{"align": alignment.index};
    await _channel.invokeMethod(commandSetAlign, args);
  }

  /// Initializes the Sunmi printer service
  static Future<void> initSunmiPrinterService() async {
    await _channel.invokeMethod(commandInitSunmiPrinterService);
  }

  /// Deinitializes the Sunmi printer service
  static Future<void> deInitSunmiPrinterService() async {
    await _channel.invokeMethod(commandDeInitSunmiPrinterService);
  }

  /// Sends raw data to the printer
  static Future<void> sendRawData(Uint8List data) async {
    final args = <String, dynamic>{"bytes": data};
    await _channel.invokeMethod(commandSendRawData, args);
  }

  /// Cuts the paper (only supported by limited devices)
  static Future<void> cutPaper() async {
    await _channel.invokeMethod(commandCutPaper);
  }

  /// Draws a line on the receipt
  static Future<void> line({
    String ch = '-',
    int len = 31,
  }) async {
    await printText(List.filled(len, ch[0]).join());
  }

  /// Adds N lines in the receipt, can be used to make space between sections
  static Future<void> lineWrap({int lines = 3}) async {
    final args = <String, dynamic>{"lines": lines};
    await _channel.invokeMethod(commandLineWrap, args);
  }

  /// Returns the current printer head position
  static Future<void> getPrinterHead() async {
    await _channel.invokeMethod(commandGetPrinterHead);
  }

  /// Returns the current printer head distance
  static Future<void> getPrinterDistance() async {
    await _channel.invokeMethod(commandGetPrinterDistance);
  }

  /// Feeds the paper to the printer (only supported by limited devices)
  static Future<void> feedPaper() async {
    await _channel.invokeMethod(commandFeedPaper);
  }

  /// Prints a barcode
  static Future<void> printBarCode(
    String data,
    int symbology,
    int height,
    int width,
    int position,
  ) async {
    final args = <String, dynamic>{
      "data": data,
      "symbology": symbology,
      "height": height,
      "width": width,
      "position": position,
    };
    await _channel.invokeMethod(commandPrintBarCode, args);
  }

  /// Prints a QR code
  static Future<void> printQr(
    String data,
    int moduleSize,
    int errorLevel,
  ) async {
    final args = <String, dynamic>{
      "data": data,
      "moduleSize": moduleSize,
      "errorLevel": errorLevel,
    };
    await _channel.invokeMethod(commandPrintQr, args);
  }

  /// Prints a row of text
  static Future<void> printRow(
    List<String> texts,
    List<int> width,
    List<SunmiPrintAlign> align,
  ) async {
    final args = <String, dynamic>{
      "texts": jsonEncode(texts),
      "width": width.join(","),
      "align": align.map((e) => e.index).join(","),
    };
    await _channel.invokeMethod(commandPrintRow, args);
  }

  /// Prints a bitmap
  static Future<void> printBitmap(
    Uint8List img,
    int orientation,
  ) async {
    Map<String, dynamic> args = <String, dynamic>{};
    args.putIfAbsent("bitmap", () => img);
    args.putIfAbsent("orientation", () => orientation);
    await _channel.invokeMethod(commandPrintBitmap, args);
  }

  /// Starts a transaction
  static Future<void> startTransection() async {
    await _channel.invokeMethod(commandStartTrans);
  }

  /// Ends a transaction
  static Future<void> endTransection() async {
    await _channel.invokeMethod(commandEndTrans);
  }

  /// Sends texts to the LCD
  static Future<void> showPrinterStatus() async {
    await _channel.invokeMethod(commandShowPrinterStatus);
  }

  /// Prints one label (for testing)
  static Future<void> printOneLabel() async {
    await _channel.invokeMethod(commandPrintOneLabel);
  }

  /// Prints multiple labels (for testing)
  static Future<void> printMultiLabel(int count) async {
    final args = <String, dynamic>{"count": count};
    await _channel.invokeMethod(commandPrintMultiLabel, args);
  }
}
