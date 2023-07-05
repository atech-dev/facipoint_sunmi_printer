import 'package:flutter/material.dart';
import 'dart:async';

import 'package:cartona_sunmi_printer/cartona_sunmi_printer.dart';

void main() => runApp(const CartonaPrinterDemo());

class CartonaPrinterDemo extends StatelessWidget {
  const CartonaPrinterDemo({Key? key}) : super(key: key);

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
  bool _isBound = false;

  @override
  void initState() {
    super.initState();
    debugPrint('[CartonaPrinterDemo] Initializing printer');
    CartonaSunmiPrinter.initializePrinter().then((_) {
      setState(() => _isBound = true);
      debugPrint('[CartonaPrinterDemo] Printer initialized');
      showMessage('[CartonaPrinterDemo] Printer initialized');
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Cartona Sunmi Printer Demo'),
      ),
      body: Center(
        child: ListView(
          shrinkWrap: true,
          padding: const EdgeInsets.symmetric(horizontal: 32),
          children: [
            ElevatedButton(
              onPressed: !_isBound ? null : () => printVersion(),
              child: const Text('Check Printer Version'),
            ),
            ElevatedButton(
              onPressed: !_isBound ? null : () => printSampleReceipt(),
              child: const Text('Print Sample Receipt'),
            ),
          ],
        ),
      ),
    );
  }

  Future<void> printVersion() async {
    final version = await CartonaSunmiPrinter.getPrinterVersion() ?? 'Unknown';
    showMessage('Running on: $version');
  }

  Future<void> printSampleReceipt() async {
    var colWidths = [5, 1, 5, 1, 18];
    var colAlignments = List.generate(5, (_) => SunmiPrintAlign.right);

    await CartonaSunmiPrinter.startTransection();

    await CartonaSunmiPrinter.setFontSize(24);
    await CartonaSunmiPrinter.setBold(true);

    await CartonaSunmiPrinter.setAlignment(SunmiPrintAlign.center);
    await CartonaSunmiPrinter.printText("جميل");

    await CartonaSunmiPrinter.setAlignment(SunmiPrintAlign.center);
    await CartonaSunmiPrinter.printText('التاريخ: 21/12/2019');

    await CartonaSunmiPrinter.setAlignment(SunmiPrintAlign.center);
    await CartonaSunmiPrinter.printText('رقم الفاتوره: 12345678');

    await CartonaSunmiPrinter.lineWrap(lines: 1);

    await CartonaSunmiPrinter.line();

    await CartonaSunmiPrinter.setFontSize(20);
    await CartonaSunmiPrinter.setBold(false);

    final labels = ['السعر', '', 'الكمية', '', 'اسم'];
    await CartonaSunmiPrinter.printRow(labels, colWidths, colAlignments);

    await CartonaSunmiPrinter.line();

    final p1 = ['100', '', '2x', '', 'مياه طبيعية بركة - 1.5 لتر'];
    await CartonaSunmiPrinter.printRow(p1, colWidths, colAlignments);

    await CartonaSunmiPrinter.line();

    final p2 = ['5', '', '1x', '', 'اللحيمي لانشون رومي بقطع الرومي - 250جم'];
    await CartonaSunmiPrinter.printRow(p2, colWidths, colAlignments);

    await CartonaSunmiPrinter.setBold(true);
    await CartonaSunmiPrinter.setFontSize(24);

    await CartonaSunmiPrinter.line();

    await CartonaSunmiPrinter.setAlignment(SunmiPrintAlign.center);
    await CartonaSunmiPrinter.printText('اجمالي المشتريات: ${100}');

    await CartonaSunmiPrinter.setAlignment(SunmiPrintAlign.center);
    await CartonaSunmiPrinter.printText('الخصم: ${10}');

    await CartonaSunmiPrinter.setAlignment(SunmiPrintAlign.center);
    await CartonaSunmiPrinter.printText('اجمالي: ${90}');

    await CartonaSunmiPrinter.lineWrap();

    await CartonaSunmiPrinter.setBold(false);

    await CartonaSunmiPrinter.endTransection();
  }

  void showMessage(String text) =>
      ScaffoldMessenger.of(context).showSnackBar(SnackBar(content: Text(text)));
}
