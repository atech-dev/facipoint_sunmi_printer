import 'dart:typed_data';

import 'package:facipoint_sunmi_printer_example/AppPrinterService.dart';
import 'package:flutter/material.dart';
import 'dart:async';

import 'package:facipoint_sunmi_printer/facipoint_sunmi_printer.dart';
import 'package:flutter/services.dart';

void main() => runApp(const FaciPointPrinterDemo());

class FaciPointPrinterDemo extends StatelessWidget {
  const FaciPointPrinterDemo({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return const MaterialApp(
      debugShowCheckedModeBanner: false,
      home: DemoScreen(),
    );
  }
}

class DemoScreen extends StatefulWidget {
  const DemoScreen({Key? key}) : super(key: key);

  @override
  State<DemoScreen> createState() => _DemoScreenState();
}

class _DemoScreenState extends State<DemoScreen> {
  int maxRowLength = 31;
  int maxRowTitleLength = 23;

  bool _isBound = false;

  late AppPrinterService apS;

  @override
  void initState() {
    super.initState();
    _initPrinterService();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('FaciPoint Sunmi Printer Demo'),
      ),
      body: Center(
        child: ListView(
          shrinkWrap: true,
          padding: const EdgeInsets.symmetric(horizontal: 32),
          children: [
            /*ElevatedButton(
              onPressed: !_isBound ? null : () => printVersion(),
              child: const Text('Check Printer Version'),
            ),
            ElevatedButton(
              onPressed: !_isBound ? null : () => printSampleReceipt(),
              child: const Text('Print Sample Receipt'),
            ),*/
            ElevatedButton(
              onPressed: !_isBound ? null : () => printFPSampleReceipt1(),
              child: const Text('Print Facipoint Sample Receipt Model 1'),
            ),
            ElevatedButton(
              onPressed: !_isBound ? null : () => printFPSampleReceipt2(),
              child: const Text('Print Facipoint Sample Receipt Model 2'),
            ),
            ElevatedButton(
              onPressed: !_isBound ? null : () => printFPSampleReceipt3(),
              child: const Text('Print Facipoint Sample Receipt Model 3'),
            ),
          ],
        ),
      ),
    );
  }

  Future<void> printVersion() async {
    final version = await FaciPointSunmiPrinter.getPrinterVersion() ?? 'Unknown';
    showMessage('Running on: $version');
  }

  Future<void> printSampleReceipt() async {
    var colWidths = [5, 1, 5, 1, 18];
    var colAlignments = List.generate(5, (_) => SunmiPrintAlign.right);

    await FaciPointSunmiPrinter.startTransaction();

    await FaciPointSunmiPrinter.setFontSize(24);
    await FaciPointSunmiPrinter.setBold(true);

    await FaciPointSunmiPrinter.setAlignment(SunmiPrintAlign.center);
    await FaciPointSunmiPrinter.printText("جميل");

    await FaciPointSunmiPrinter.setAlignment(SunmiPrintAlign.center);
    await FaciPointSunmiPrinter.printText('التاريخ: 21/12/2019');

    await FaciPointSunmiPrinter.setAlignment(SunmiPrintAlign.center);
    await FaciPointSunmiPrinter.printText('رقم الفاتوره: 12345678');

    await FaciPointSunmiPrinter.lineWrap(lines: 1);

    await FaciPointSunmiPrinter.line();

    await FaciPointSunmiPrinter.setFontSize(20);
    await FaciPointSunmiPrinter.setBold(false);

    final labels = ['السعر', '', 'الكمية', '', 'اسم'];
    await FaciPointSunmiPrinter.printRow(labels, colWidths, colAlignments);

    await FaciPointSunmiPrinter.line();

    final p1 = ['100', '', '2x', '', 'مياه طبيعية بركة - 1.5 لتر'];
    await FaciPointSunmiPrinter.printRow(p1, colWidths, colAlignments);

    await FaciPointSunmiPrinter.line();

    final p2 = ['5', '', '1x', '', 'اللحيمي لانشون رومي بقطع الرومي - 250جم'];
    await FaciPointSunmiPrinter.printRow(p2, colWidths, colAlignments);

    await FaciPointSunmiPrinter.setBold(true);
    await FaciPointSunmiPrinter.setFontSize(24);

    await FaciPointSunmiPrinter.line();

    await FaciPointSunmiPrinter.setAlignment(SunmiPrintAlign.center);
    await FaciPointSunmiPrinter.printText('اجمالي المشتريات: ${100}');

    await FaciPointSunmiPrinter.setAlignment(SunmiPrintAlign.center);
    await FaciPointSunmiPrinter.printText('الخصم: ${10}');

    await FaciPointSunmiPrinter.setAlignment(SunmiPrintAlign.center);
    await FaciPointSunmiPrinter.printText('اجمالي: ${90}');

    await FaciPointSunmiPrinter.lineWrap();

    await FaciPointSunmiPrinter.setBold(false);

    await FaciPointSunmiPrinter.endTransaction();
  }

  Future<void> printFPSampleReceipt1() async {
    await FaciPointSunmiPrinter.startTransaction();

    // Logo header
    await FaciPointSunmiPrinter.setAlignment(SunmiPrintAlign.center);
    var bytes =
        await _getImageFromAsset('assets/images/facipoint_logo_receipt.bmp');
    await FaciPointSunmiPrinter.printBitmap(bytes, 1);

    await FaciPointSunmiPrinter.lineWrap(lines: 2);

    await FaciPointSunmiPrinter.setAlignment(SunmiPrintAlign.center);
    await FaciPointSunmiPrinter.printText("Directo");

    await FaciPointSunmiPrinter.setAlignment(SunmiPrintAlign.center);
    await FaciPointSunmiPrinter.printText("Agente");

    await FaciPointSunmiPrinter.setAlignment(SunmiPrintAlign.center);
    await FaciPointSunmiPrinter.printText("Josué Capita");

    await FaciPointSunmiPrinter.lineWrap(lines: 2);

    await FaciPointSunmiPrinter.line(ch: "*");

    final alignments = [SunmiPrintAlign.left, SunmiPrintAlign.right];

    var labels = ['Agente', 'Josue Capita'];
    var widths = [getTitleLength(labels.first), getValueLength(labels.last)];
    await FaciPointSunmiPrinter.printRow(labels, widths, alignments);

    labels = ['NIF', '005211378LA047'];
    widths = [getTitleLength(labels.first), getValueLength(labels.last)];
    await FaciPointSunmiPrinter.printRow(labels, widths, alignments);

    labels = ['Nº de Conta', 'FP 000 204'];
    widths = [getTitleLength(labels.first), getValueLength(labels.last)];
    await FaciPointSunmiPrinter.printRow(labels, widths, alignments);

    await FaciPointSunmiPrinter.line(ch: "*");

    labels = ['Data', '16/11/2023 09:35'];
    widths = [getTitleLength(labels.first), getValueLength(labels.last)];
    await FaciPointSunmiPrinter.printRow(labels, widths, alignments);

    await FaciPointSunmiPrinter.line(ch: "*");

    labels = ['AOA', '10.000,00'];
    widths = [getTitleLength(labels.first), getValueLength(labels.last)];
    await FaciPointSunmiPrinter.printRow(labels, widths, alignments);

    await FaciPointSunmiPrinter.lineWrap();

    // await FaciPointSunmiPrinter.setAlignment(SunmiPrintAlign.center);
    // await FaciPointSunmiPrinter.printQr("OLA MUNDO", 3, 30);

    await FaciPointSunmiPrinter.setAlignment(SunmiPrintAlign.center);
    await FaciPointSunmiPrinter.setFontSize(20);
    await FaciPointSunmiPrinter.printText("SOLICITE SEMPRE O SEU COMPROVATIVO");

    await FaciPointSunmiPrinter.lineWrap(lines: 4);

    await FaciPointSunmiPrinter.endTransaction();
  }

  Future<void> printFPSampleReceipt2() async {
    apS.transactionTitle = "";
    apS.transactionDescription = "Recibo de Fecho de Conta";

    await apS.printReceipt(sections: [
      PrinterContentSection3(
        [
          PrinterContentRow3("AOA", "QTD", "TOTAL"),
        ],
        sepCh: "",
        lines: 0,
      ),
      PrinterContentSection3(
        [
          PrinterContentRow3("Abt conta", "1", "12.000,00 AOA", 0, 1),
          PrinterContentRow3("Pgt Directo", "2", "100.000,00 AOA", 0, 1),
          PrinterContentRow3("Pgt EMIS", "7", "80.000,00 AOA", 0, 1),
          PrinterContentRow3("Dpt Facipoint", "8", "80.000,00 AOA", 0, 1),
          PrinterContentRow3("Pgt Directo", "2", "100.000,00 AOA", 0, 1),
          PrinterContentRow3("Pgt EMIS", "7", "80.000,00 AOA", 0, 1),
          PrinterContentRow3("Dpt Facipoint", "8", "80.000,00 AOA"),
        ],
        sepCh: "=",
        lines: 0,
      ),
      PrinterContentSection2(
        [
          PrinterContentRow2("AOA", "150.000,00"),
        ],
        sepCh: "=",
        lines: 0,
      ),
      PrinterContentSection2([
        PrinterContentRow2("Data de Abertura", "01/12/2022 08:45"),
        PrinterContentRow2("Data de Fecho", "13/12/2022 08:45"),
        PrinterContentRow2("Valor de Entrada", "12.000,00 AOA"),
      ]),
      PrinterContentSection2([
        PrinterContentRow2("Agente", "Josué Capita"),
        PrinterContentRow2("NIF", "005211378LA047"),
        PrinterContentRow2("Nº de Conta", "FP000204", 3),
      ]),
    ]);
  }

  Future<void> printFPSampleReceipt3() async {
    apS.transactionTitle = "Pagamento ao Estado";
    apS.transactionDescription = "Adesão Cartão de Ambulante";

    await apS.printReceipt(sections: [
      PrinterContentSection2([
        PrinterContentRow2("Cód. Nota", "8086465675"),
        PrinterContentRow2("RUPE", "2314252637485913242312"),
        PrinterContentRow2("Data de Emissao", "01/12/2022"),
      ]),
      PrinterContentSection2([
        PrinterContentRow2("Agente", "Josué Capita"),
        PrinterContentRow2("NIF", "005211378LA047"),
        PrinterContentRow2("Nº de Conta", "FP000204", 3),
      ]),
      PrinterContentSection2([
        PrinterContentRow2("Cliente", "António Segredo"),
        PrinterContentRow2("NIF", "005211378LA048"),
      ]),
      PrinterContentSection2([
        PrinterContentRow2("AOA", "1.760,00"),
        PrinterContentRow2("01/12/2022", "19:28"),
      ]),
    ]);
  }

  // region Print helpers
  Future<Uint8List> readFileBytes(String path) async {
    ByteData fileData = await rootBundle.load(path);
    Uint8List fileUnit8List = fileData.buffer
        .asUint8List(fileData.offsetInBytes, fileData.lengthInBytes);
    return fileUnit8List;
  }

  Future<Uint8List> _getImageFromAsset(String iconPath) async {
    return await readFileBytes(iconPath);
  }

  int getTitleLength(String text) {
    return (maxRowTitleLength < text.diacriticLength
            ? maxRowTitleLength
            : text.diacriticLength) +
        1;
  }

  int getValueLength(String text) {
    return (maxRowTitleLength < text.diacriticLength
            ? maxRowTitleLength
            : text.diacriticLength) +
        1;
  }

  // endregion

  void showMessage(String text) =>
      ScaffoldMessenger.of(context).showSnackBar(SnackBar(content: Text(text)));

  void _initPrinterService() {
    try {
      apS = AppPrinterService();
      debugPrint('[FaciPointPrinterDemo] Initializing printer');
      apS.initPrinterService().then((_) {
        setState(() => _isBound = true);
        debugPrint('[FaciPointPrinterDemo] Printer initialized');
        showMessage('[FaciPointPrinterDemo] Printer initialized');
      });
    } catch(e, stackTrace) {
      print("ERROR INITIALIZING");
      print(e);
      print(stackTrace);
    }
  }
}

extension Diacritics on String {
  int get diacriticLength {
    var count = 0;
    for (var i = 0; i <= length - 1; i++) {
      var ss = [i];
      // ignore: iterable_contains_unrelated_type
      if (diacriticsMapping.keys.contains(ss)) {
        count = count + 2;
      } else {
        count++;
      }
    }

    return count;
  }
}

// region Hidden
const Map<String, String> diacriticsMapping = {
  //[À-ÿ]
  'À': 'A',
  'Á': 'A',
  'Â': 'A',
  'Ã': 'A',
  'Ä': 'A',
  'Å': 'A',
  'Æ': 'AE',
  'Ç': 'C',
  'È': 'E',
  'É': 'E',
  'Ê': 'E',
  'Ë': 'E',
  'Ì': 'I',
  'Í': 'I',
  'Î': 'I',
  'Ï': 'I',
  'Ð': 'D',
  'Ñ': 'N',
  'Ò': 'O',
  'Ó': 'O',
  'Ô': 'O',
  'Õ': 'O',
  'Ö': 'O',
  '×': 'x', //math multiplication
  'Ø': 'O',
  'Ù': 'U',
  'Ú': 'U',
  'Û': 'U',
  'Ü': 'U',
  'Ý': 'Y',
  'Þ': 'TH',
  'ß': 'SS',
  'à': 'a',
  'á': 'a',
  'â': 'a',
  'ã': 'a',
  'ä': 'a',
  'å': 'a',
  'æ': 'ae',
  'ç': 'c',
  'è': 'e',
  'é': 'e',
  'ê': 'e',
  'ë': 'e',
  'ì': 'i',
  'í': 'i',
  'î': 'i',
  'ï': 'i',
  'ð': 'd',
  'ñ': 'n',
  'ò': 'o',
  'ó': 'o',
  'ô': 'o',
  'õ': 'o',
  'ö': 'o',
  '÷': ' ', //math division
  'ø': 'o',
  'ù': 'u',
  'ú': 'u',
  'û': 'u',
  'ü': 'u',
  'ý': 'y',
  'þ': 'th',
  'ÿ': 'y',
  //[Ā-ž] EuropeanLatin
  'Ā': 'A',
  'ā': 'a',
  'Ă': 'A',
  'ă': 'a',
  'Ą': 'A',
  'ą': 'a',
  'Ć': 'C',
  'ć': 'c',
  'Ĉ': 'C',
  'ĉ': 'c',
  'Ċ': 'C',
  'ċ': 'c',
  'Č': 'C',
  'č': 'c',
  'Ď': 'D',
  'ď': 'd',
  'Đ': 'D',
  'đ': 'd',
  'Ē': 'E',
  'ē': 'e',
  'Ĕ': 'E',
  'ĕ': 'e',
  'Ė': 'E',
  'ė': 'e',
  'Ę': 'E',
  'ę': 'e',
  'Ě': 'E',
  'ě': 'e',
  'Ĝ': 'G',
  'ĝ': 'g',
  'Ğ': 'G',
  'ğ': 'g',
  'Ġ': 'G',
  'ġ': 'g',
  'Ģ': 'G',
  'ģ': 'g',
  'Ĥ': 'H',
  'ĥ': 'h',
  'Ħ': 'H',
  'ħ': 'h',
  'Ĩ': 'I',
  'ĩ': 'i',
  'Ī': 'I',
  'ī': 'i',
  'Ĭ': 'I',
  'ĭ': 'i',
  'Į': 'I',
  'į': 'i',
  'İ': 'I',
  'ı': 'i',
  'Ĳ': 'IJ',
  'ĳ': 'ij',
  'Ĵ': 'J',
  'ĵ': 'j',
  'Ķ': 'K',
  'ķ': 'k',
  'ĸ': 'k',
  'Ĺ': 'L',
  'ĺ': 'l',
  'Ļ': 'L',
  'ļ': 'l',
  'Ľ': 'L',
  'ľ': 'l',
  'Ŀ': 'L',
  'ŀ': 'l',
  'Ł': 'L',
  'ł': 'l',
  'Ń': 'N',
  'ń': 'n',
  'Ņ': 'N',
  'ņ': 'n',
  'Ň': 'N',
  'ň': 'n',
  'ŉ': 'n',
  'Ŋ': 'N',
  'ŋ': 'n',
  'Ō': 'O',
  'ō': 'o',
  'Ŏ': 'O',
  'ŏ': 'o',
  'Ő': 'O',
  'ő': 'o',
  'Œ': 'OE',
  'œ': 'oe',
  'Ŕ': 'R',
  'ŕ': 'r',
  'Ŗ': 'R',
  'ŗ': 'r',
  'Ř': 'R',
  'ř': 'r',
  'Ś': 'S',
  'ś': 's',
  'Ŝ': 'S',
  'ŝ': 's',
  'Ş': 'S',
  'ş': 's',
  'Š': 'S',
  'š': 's',
  'Ţ': 'T',
  'ţ': 't',
  'Ť': 'T',
  'ť': 't',
  'Ŧ': 'T',
  'ŧ': 't',
  'Ũ': 'U',
  'ũ': 'u',
  'Ū': 'U',
  'ū': 'u',
  'Ŭ': 'U',
  'ŭ': 'u',
  'Ů': 'U',
  'ů': 'u',
  'Ű': 'U',
  'ű': 'u',
  'Ų': 'U',
  'ų': 'u',
  'Ŵ': 'W',
  'ŵ': 'w',
  'Ŷ': 'Y',
  'ŷ': 'y',
  'Ÿ': 'Y',
  'Ź': 'Z',
  'ź': 'z',
  'Ż': 'Z',
  'ż': 'z',
  'Ž': 'Z',
  'ž': 'z',
};

// endregion
