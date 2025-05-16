// ignore: avoid_catches_without_on_clauses
import 'package:facipoint_sunmi_printer/facipoint_sunmi_printer.dart';
import 'package:facipoint_sunmi_printer_example/extensions.dart';
import 'package:facipoint_sunmi_printer_example/models.dart';
import 'package:flutter/services.dart';
import 'package:fluttertoast/fluttertoast.dart';

import 'constants.dart';

String cAgentName = "Josue Capita";
String agentNIF = "005211378LA047";
String agentANumber = "FP 000 204";

class AppPrinterService {
  String transactionTitle = "";
  String transactionDescription = "";
  Future<void> Function()? customTitle;
  bool hideAgentDetailsInTitle = false;
  Function()? printSections;
  double receiptValue = 0;
  bool isInitialized = false;
  bool isPrinting = false;

  String receiptNumber = "12357872";

  // region QRCode
  bool showQrCode = true;
  String qrCodeContent = "https://facipoint.co.ao/";
  int qrCodeSize = 3;

  // endregion

  AppPrinterService({
    this.transactionTitle = "",
    this.transactionDescription = "",
    this.customTitle,
    this.hideAgentDetailsInTitle = false,
  });

  static AppPrinterService get instance {
    var laps = AppPrinterService();
    laps.resetPrinting();

    return laps;
  }

  // region Main methods
  Future<void> printReceipt({
    String transactionTitle = "",
    String transactionDescription = "",
    List<PrinterContentSection> sections = const [],
  }) async {
    if (isPrinting) return;

    if (!isInitialized) {
      return;
    }

    setStartPrinting();

    this.transactionTitle = transactionTitle.isNotNullOrEmpty
        ? transactionTitle
        : this.transactionTitle;
    this.transactionDescription = transactionDescription.isNotNullOrEmpty
        ? transactionDescription
        : this.transactionDescription;

    await startPrintingTransaction();
    await printReceiptHeader();
    await printReceiptContent(sections);
    await printReceiptFooter();
    await endPrintReceipt();

    setStopPrinting();
  }

  Future<bool> hasPrinter() async {
    try {
      return await FaciPointSunmiPrinter.hasPrinter();
    } catch (e, stackTrace) {
      log("ERROR initPrinter");
      log(e);
      log(stackTrace);
      return false;
    }
  }

  Future<AppPrinterService> initPrinterService() async {
    try {
      var init = await FaciPointSunmiPrinter.initializePrinter();
      await FaciPointSunmiPrinter.showPrinterStatus();
      var has = await FaciPointSunmiPrinter.hasPrinter();
      print("init: $init");
      print("has: $has");
      if (init) {
        isInitialized = true;
      } else {
        _showAlert("Ocorreu um erro desconhecido ao conectar com a impressora");
        isInitialized = false;
      }
      /*var dprinterStatus = await SunmiPrinter.initPrinter();
        print("dprinterStatus: $dprinterStatus");
        var gdprinterStatus = await SunmiPrinter.drawerStatus();
        print("gdprinterStatus: $gdprinterStatus");
        var printerStatus = await SunmiPrinter.getPrinterStatus();
        print("printerStatus: $printerStatus");

        switch(printerStatus) {
          case PrinterStatus.NORMAL:
            return true;
          case PrinterStatus.OUT_OF_PAPER:
            _showAlert("Ocorreu um erro desconhecido ao conectar com a impressora");
            return false;
          case PrinterStatus.PREPARING:
            _showAlert("Impressora a inicializar, por favor tente novamente daqui a alguns instantes");
            return false;
          case PrinterStatus.OVERHEATED:
            _showAlert("Impressora super aquecida, por favor aguarde alguns instantes");
            return false;
          case PrinterStatus.OPEN_THE_LID:
            _showAlert("Por favor, feche a impressora para continuar com a impressão");
            return false;
          case PrinterStatus.UNKNOWN:
          case PrinterStatus.ERROR:
          case PrinterStatus.PAPER_CUTTER_ABNORMAL:
          case PrinterStatus.PAPER_CUTTER_RECOVERED:
            _showAlert("Ocorreu um erro desconhecido ao imprimir");
            return false;
          default:
          // UNKNOWN
          // EXCEPTION
          // ABNORMAL_COMMUNICATION
          // NO_PRINTER_DETECTED
          // NO_BLACK_MARK
          // FAILED_TO_UPGRADE_FIRMWARE
            break;
        }*/
    } catch (e, stackTrace) {
      log("ERROR initPrinter");
      log(e);
      log(stackTrace);

      _showAlert("Ocorreu um erro desconhecido ao inicializar a impressora");
      isInitialized = false;
    }

    return this;
  }

  Future<void> startPrintingTransaction() async {
    try {
      await FaciPointSunmiPrinter.startTransaction();
    } catch (e, stackTrace) {
      log("ERROR startPrintingTransaction");
      log(e);
      log(stackTrace);

      _showAlert("Ocorreu um erro desconhecido ao imprimir");

      setStopPrinting();
    }
  }

  Future<void> printReceiptHeader() async {
    try {
      await FaciPointSunmiPrinter.lineWrap(lines: 1);

      await FaciPointSunmiPrinter.setAlignment(
          SunmiPrintAlign.center); // Center align
      var bytes =
          await _getImageFromAsset('assets/images/facipoint_logo_receipt.bmp');
      await FaciPointSunmiPrinter.printBitmap(bytes, 0);

      await FaciPointSunmiPrinter.lineWrap(lines: 2);
    } catch (e, stackTrace) {
      log("ERROR printReceiptHeader");
      log(e);
      log(stackTrace);
      setStopPrinting();
    }
  }

  Future<void> printReceiptContent(List<PrinterContentSection> sections) async {
    try {
      await AppPrinterContent.transactionTitle(
        transactionTitle,
        transactionDescription,
        hideAgentDetailsInTitle,
        customTitle,
      );

      var i = 0;
      await Future.doWhile(() async {
        var section = sections[i];
        // Section with Rows with two(2) columns
        if (section is PrinterContentSection2) {
          await AppPrinterContent.section2(section);
        } else if (section is PrinterContentSection3) {
          await AppPrinterContent.section3(section);
        }

        i++;
        return i < sections.length;
      });

      await AppPrinterContent.line();
    } catch (e, stackTrace) {
      log("ERROR printReceiptContent");
      log(e);
      log(stackTrace);
      setStopPrinting();
    }
  }

  Future<void> printReceiptCustomTitle() async {
    try {
      AppPrinterContent.transactionTitle(
        transactionTitle,
        transactionDescription,
        hideAgentDetailsInTitle,
        customTitle,
      );
    } catch (e, stackTrace) {
      log("ERROR printReceiptCustomTitle");
      log(e);
      log(stackTrace);
      setStopPrinting();
    }
  }

  Future<void> printReceiptFooter() async {
    try {
      if (showQrCode) {
        // await FaciPointSunmiPrinter.setAlignment(SunmiPrintAlign.center);
        /*await FaciPointSunmiPrinter.printQr(
          qrCodeContent,
          qrCodeSize,
          30,
        );*/
        // await FaciPointSunmiPrinter.setAlignment(SunmiPrintAlign.center);
        // await FaciPointSunmiPrinter.setFontSize(12);
        // await FaciPointSunmiPrinter.printText('LEIA O QR CODE PARA OBTER MAIS DETALHES');
        await FaciPointSunmiPrinter.lineWrap(lines: 1);
        await FaciPointSunmiPrinter.setAlignment(SunmiPrintAlign.center);
        await FaciPointSunmiPrinter.setFontSize(20);
        await FaciPointSunmiPrinter.printText(
            'SOLICITE SEMPRE O SEU COMPROVATIVO');
      }

      await FaciPointSunmiPrinter.lineWrap(lines: 1);

      // await SunmiPrinter.setAlignment(SunmiPrintAlign.CENTER);
      // await SunmiPrinter.printText('RECIBO Nº $receiptNumber');

      await FaciPointSunmiPrinter.lineWrap(lines: 4); // Jump 2 lines
    } catch (e, stackTrace) {
      log("ERROR printReceiptFooter");
      log(e);
      log(stackTrace);
      setStopPrinting();
    }
  }

  Future<void> endPrintReceipt() async {
    try {
      await FaciPointSunmiPrinter.cutPaper(); // SUBMIT and cut paper
      await FaciPointSunmiPrinter.endTransaction(); // Close the transaction
      // await FaciPointSunmiPrinter.feedPaper(); // Clear paper
    } catch (e, stackTrace) {
      log("ERROR endPrintReceipt");
      log(e);
      log(stackTrace);
      setStopPrinting();
    }
  }

  void setStartPrinting() {
    isPrinting = true;
  }

  void setStopPrinting() {
    isPrinting = false;
  }

  void resetPrinting() {
    transactionTitle = "";
    transactionDescription = "";
    customTitle = null;
    hideAgentDetailsInTitle = false;
  }

  // endregion

  // region Print helpers
  static Future<Uint8List> readFileBytes(String path) async {
    ByteData fileData = await rootBundle.load(path);
    Uint8List fileUnit8List = fileData.buffer
        .asUint8List(fileData.offsetInBytes, fileData.lengthInBytes);
    return fileUnit8List;
  }

  static Future<Uint8List> _getImageFromAsset(String iconPath) async {
    return await readFileBytes(iconPath);
  }

  // endregion

  // region other
  _showAlert(String msg) {
    Fluttertoast.showToast(
      msg: msg,
      toastLength: Toast.LENGTH_LONG,
    );
  }

  // endregion

  // region Templates

  Future<void> printMovServiceTemplate(
    MovementModel movementModel,
    FacipayServiceModel facipayServiceModel, [
    String? rechargeToken,
  ]) async {
    transactionTitle = "Pagamento de Serviço";
    transactionDescription = (movementModel.account.name ?? "").toUpperCase();
    hideAgentDetailsInTitle = true;

    String? valueDescription;
    String movDesc = movementModel.description;
    if (movDesc.contains("_")) {
      valueDescription = movDesc.split("_").first;
    } else {
      valueDescription = movDesc;
    }

    customTitle = () async {
      await AppPrinterContent.text("Carregamento Directo");
      if (valueDescription.isNotNullOrEmpty) {
        await AppPrinterContent.text(valueDescription!);
      }
      if (rechargeToken.isNotNullOrEmpty) {
        await AppPrinterContent.line();
        await AppPrinterContent.text("Referência");
        await AppPrinterContent.text(rechargeToken!.groupBy(group: 4));
      }
    };

    List<PrinterContentRow2> itemsDescription = [];

    var serviceAttributeModels = facipayServiceModel.attributes != null
        ? facipayServiceModel.attributes!
            .where((e) => ![].contains(e.noAtributo))
            .toList()
        : [];

    for (var sam in serviceAttributeModels) {
      String title = sam.dsAtributo;
      String value =
          facipayServiceModel.getAttributeValue(sam.noAtributo) ?? "-";
      itemsDescription.add(PrinterContentRow2(title, value));
    }

    await printReceipt(
      sections: [
        PrinterContentSection2(itemsDescription),
        _printMovTemplateAgentDataSection(),
        PrinterContentSection2(
          [
            PrinterContentRow2(
              "AOA",
              simpleNumberFormat(movementModel.amount.toStringAsFixed(2))
                  .trim(),
            ),
            PrinterContentRow2(
              "Data",
              (movementModel.lastTransactionDateTime ??
                      DateTime.now().toIso8601String())
                  .dateFormatIntl("dd/MM/yyyy HH:mm"),
            ),
          ],
        ),
      ],
    );
  }

  Future<void> printMovTemplate(MovementModel movementModel) async {
    transactionTitle = transactionTitle.isEmpty
        ? movementModel.type?.name ?? ""
        : transactionTitle;

    await printReceipt(sections: [
      _printMovTemplateAgentDataSection(),
      PrinterContentSection2([
        PrinterContentRow2(
          "Data",
          (movementModel.lastTransactionDateTime ??
                  DateTime.now().toIso8601String())
              .dateFormatIntl("dd/MM/yyyy HH:mm"),
        ),
      ]),
      PrinterContentSection2(
        [
          PrinterContentRow2(
            "AOA",
            simpleNumberFormat(movementModel.amount.toStringAsFixed(2)).trim(),
          ),
        ],
      ),
    ]);
  }

  Future<void> printTopUpOrWithdrawalTemplate(
    EOperationType type,
    EOperationRequestType requestType,
    MovementModel movementModel,
  ) async {
    transactionTitle = "${type.name.capitalize()} pelo FaciPoint";
    transactionDescription = "Via ${requestType.name.toUpperCase()}";

    DateTime rawDate = movementModel.lastTransactionDateTime == null
        ? DateTime.now()
        : (DateTime.tryParse(movementModel.lastTransactionDateTime!) ??
            DateTime.now());
    String date = rawDate.toIso8601String();
    String receiptDate = date.dateFormatIntl();
    String receiptDateTime = date.dateFormatV5();

    await printReceipt(sections: [
      _printMovTemplateAgentDataSection(),
      PrinterContentSection2([
        PrinterContentRow2(
          "Agente",
          cAgentName,
        ),
        PrinterContentRow2(
          "Cliente",
          movementModel.account.name?.firstAndLastWords() ?? "",
        ),
      ]),
      PrinterContentSection2(
        [
          PrinterContentRow2(
            "AOA",
            simpleNumberFormat(movementModel.amount.toStringAsFixed(2)).trim(),
          ),
          PrinterContentRow2(
            receiptDate,
            receiptDateTime,
          ),
        ],
      ),
    ]);
  }

  Future<void> printSettlementNoteTemplate(
    SettlementNoteModel settlementNote,
    String clientName,
    String clientNIF,
  ) async {
    transactionTitle = "Pagamento ao Estado";

    String date = DateTime.now().toIso8601String();
    String receiptDate = date.dateFormatIntl();
    String receiptDateTime = date.dateFormatV5();

    var receiptValue =
        simpleNumberFormat(settlementNote.value.toStringAsFixed(2)).trim();

    await printReceipt(sections: [
      PrinterContentSection2([
        PrinterContentRow2(
          "Cód. Nota",
          settlementNote.code,
        ),
        PrinterContentRow2(
          "RUPE",
          settlementNote.rupe,
        ),
        PrinterContentRow2(
          "Data de Emissão",
          settlementNote.datetime.toIso8601String().dateFormatIntl(),
        ),
      ]),
      _printMovTemplateAgentDataSection(),
      PrinterContentSection2([
        PrinterContentRow2(
          "Cliente",
          clientName,
        ),
        PrinterContentRow2(
          "NIF",
          clientNIF,
        ),
      ]),
      PrinterContentSection2(
        [
          PrinterContentRow2(
            "AOA",
            receiptValue,
          ),
          PrinterContentRow2(
            receiptDate,
            receiptDateTime,
          ),
        ],
      ),
    ]);
  }

  Future<void> printSettlementNoteMovTemplate(
    MovementModel movementModel,
    SettlementNoteModel settlementNote,
  ) async {
    transactionTitle = "Pagamento ao Estado";

    String date = DateTime.now().toIso8601String();
    String receiptDate = date.dateFormatIntl();
    String receiptDateTime = date.dateFormatV5();

    var receiptValue =
        simpleNumberFormat(movementModel.amount.toStringAsFixed(2)).trim();

    await printReceipt(sections: [
      PrinterContentSection2([
        PrinterContentRow2(
          "Cód. Nota",
          settlementNote.code,
        ),
        PrinterContentRow2(
          "RUPE",
          settlementNote.rupe,
        ),
        PrinterContentRow2(
          "Data de Emissão",
          settlementNote.datetime.toIso8601String().dateFormatIntl(),
        ),
      ]),
      _printMovTemplateAgentDataSection(),
      PrinterContentSection2([
        PrinterContentRow2(
          "Cliente",
          movementModel.account.name?.firstAndLastWords() ?? "",
        ),
        PrinterContentRow2(
          "Nº de Conta",
          movementModel.account.number ?? "",
          3,
        ),
      ]),
      PrinterContentSection2(
        [
          PrinterContentRow2(
            "AOA",
            receiptValue,
          ),
          PrinterContentRow2(
            receiptDate,
            receiptDateTime,
          ),
        ],
      ),
    ]);
  }

  PrinterContentSection2 _printMovTemplateAgentDataSection() {
    return PrinterContentSection2([
      PrinterContentRow2(
        "Agente",
        cAgentName,
      ),
      PrinterContentRow2(
        "NIF",
        agentNIF,
      ),
      PrinterContentRow2(
        "Nº de Conta",
        agentANumber,
        3,
      ),
    ]);
  }

// endregion
}

// region Models
abstract class AppPrinterContent {
  static int maxRowLength = 31;
  static int maxRowTitleLength = 23;

  static Future<void> sep([String ch = "*"]) async {
    // Second row
    await FaciPointSunmiPrinter.setAlignment(SunmiPrintAlign.center);
    await FaciPointSunmiPrinter.line(ch: ch);
  }

  static Future<void> line([int lines = 1]) async {
    await FaciPointSunmiPrinter.lineWrap(lines: lines);
  }

  static Future<void> transactionTitle(
    String transactionTitle,
    String transactionDescription,
    bool hideAgentDetailsInTitle,
    Future<void> Function()? customTitle,
  ) async {
    if (transactionTitle.isNotEmpty) {
      await text(transactionTitle);
    }
    if (transactionDescription.isNotEmpty) {
      await text(transactionDescription);
    }
    if (!hideAgentDetailsInTitle) {
      await text("Agente");
      var agentName = cAgentName.truncate(maxLength: 30);
      await text(agentName);
    }
    if (customTitle != null) {
      await customTitle();
    }
    await line();
  }

  static Future<void> text(
    String text, {
    SunmiPrintAlign align = SunmiPrintAlign.center,
  }) async {
    await FaciPointSunmiPrinter.setAlignment(align);
    await FaciPointSunmiPrinter.printText(
      _maxEllipsis(text, text.diacriticLength, maxRowLength).removeDiacritics,
    );
  }

  // region 2dColumns
  static Future<void> section2(PrinterContentSection2 section) async {
    if (section.sepCh.isNotEmpty) {
      await sep(section.sepCh);
    }

    var i = 0;
    await Future.doWhile(() async {
      var row = section.rows[i];
      await row2(row);

      i++;
      return i < section.rows.length;
    });
  }

  static Future<void> row2(PrinterContentRow2 row) async {
    try {
      String key = row.title2;
      String value = row.description2;
      int? keyExtraLength = row.title2ExtraLength;

      int titleLength = (maxRowTitleLength < key.diacriticLength
              ? maxRowTitleLength
              : key.diacriticLength) +
          (keyExtraLength ?? 0) +
          1;
      int valueLength = value.diacriticLength;
      int valueLengthLimit = _safeSubtraction(maxRowLength, titleLength);

      // Row
      var labels = [
        _maxEllipsis(key, titleLength, maxRowTitleLength).removeDiacritics,
        _maxEllipsis(value, valueLength, valueLengthLimit, "").removeDiacritics
      ];
      var colWidths = [titleLength, valueLengthLimit];
      var colAlignments = [SunmiPrintAlign.left, SunmiPrintAlign.right];
      await FaciPointSunmiPrinter.printRow(labels, colWidths, colAlignments);
      if (row.lines != 0) {
        await line(row.lines);
      }
    } catch (e, stackTrace) {
      log("ERROR row2");
      log(e);
      log(stackTrace);
    }
  }

  // endregion

  // region 3dColumns
  static Future<void> section3(PrinterContentSection3 section) async {
    if (section.sepCh.isNotEmpty) {
      await sep(section.sepCh);
    }

    if (section.rows.isEmpty) return;

    int maxText3Length = 16;
    if (section.rows.length > 1) {
      maxText3Length =
          _getMaxText3Length(section.rows.map((e) => e.text3).toList());
    }

    var i = 0;
    await Future.doWhile(() async {
      var row = section.rows[i];
      await row3(row, maxText3Length);

      i++;
      return i < section.rows.length;
    });
    if (section.lines != 0) {
      await line(section.lines);
    }
  }

  static Future<void> row3(PrinterContentRow3 row, int maxTextLength) async {
    String text1 = row.text1;
    String text2 = row.text2;
    String text3 = row.text3;
    int? keyExtraLength = row.title3ExtraLength;

    int text2ColumnMaxRowLength = 4;
    int text1MaxLength = _safeSubtraction(
      maxRowLength,
      (text2ColumnMaxRowLength + maxTextLength),
    );
    int text1Length = text1.diacriticLength + 1 + (keyExtraLength ?? 0);

    // Row
    var labels = [
      _maxEllipsis(text1, text1Length, text1MaxLength).removeDiacritics,
      text2,
      text3,
    ];
    var colWidths = [text1MaxLength, text2ColumnMaxRowLength, maxTextLength];
    var colAlignments = [
      SunmiPrintAlign.left,
      SunmiPrintAlign.center,
      SunmiPrintAlign.right
    ];
    await FaciPointSunmiPrinter.printRow(labels, colWidths, colAlignments);
    if (row.lines != 0) {
      await line(row.lines);
    }
  }

  static String _maxEllipsis(
    String text,
    int length,
    int limit, [
    String ellipsis = "...",
  ]) {
    var rText = text;
    if (length > limit) {
      rText = text.substring(0, limit - 3);
      rText += ellipsis;
    }

    return rText;
  }

  static int _safeSubtraction(int max, int n2) => max > n2 ? max - n2 : max;

  static int _getMaxText3Length(List<String> text3s) {
    if (text3s.isNotEmpty) {
      var max = text3s.first.length;

      text3s.removeAt(0);
      for (var i = 0; i <= text3s.length - 1; i++) {
        if (max < text3s[i].length) {
          max = text3s[i].length;
        }
      }

      return max;
    }

    return 0;
  }
// endregion
}

class PrinterContentRow3 extends PrinterContentRow {
  String text1;
  String text2;
  String text3;
  int? title3ExtraLength;

  PrinterContentRow3(
    this.text1,
    this.text2,
    this.text3, [
    this.title3ExtraLength,
    int lines = 0,
  ]) : super(lines: lines);
}

class PrinterContentRow2 extends PrinterContentRow {
  String title2;
  int? title2ExtraLength;
  String description2;

  PrinterContentRow2(
    this.title2,
    this.description2, [
    this.title2ExtraLength,
    int lines = 0,
  ]) : super(lines: lines);
}

class PrinterContentRow {
  int lines;

  PrinterContentRow({this.lines = 0});
}

class PrinterContentSection3 extends PrinterContentSection {
  List<PrinterContentRow3> rows = [];

  PrinterContentSection3(
    this.rows, {
    super.sepCh,
    super.lines,
  });
}

class PrinterContentSection2 extends PrinterContentSection {
  List<PrinterContentRow2> rows = [];

  PrinterContentSection2(
    this.rows, {
    super.sepCh,
    super.lines,
  });
}

class PrinterContentSection {
  String sepCh;
  int lines;

  PrinterContentSection({this.sepCh = "*", this.lines = 1});
}

// endregion
