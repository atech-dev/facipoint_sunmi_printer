# Cartona Sunmi Printer

A wrapper around Sunmi's Android printer SDK.

## Installation

```bash
flutter pub add cartona_sunmi_printer
```

## Tested Devices

- Sunmi V2 Pro
- Sunmi V2S

## Sample Usage

In your flutter application, add the following:

```dart
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
              onPressed: !_isBound ? null : () => printSampleReceipt(),
              child: const Text('Print Sample Receipt'),
            ),
          ],
        ),
      ),
    );
  }

  Future<void> printSampleReceipt() async {
    await CartonaSunmiPrinter.startTransection();

    await CartonaSunmiPrinter.setFontSize(24);
    await CartonaSunmiPrinter.setBold(true);

    await CartonaSunmiPrinter.setAlignment(SunmiPrintAlign.center);
    await CartonaSunmiPrinter.printText("Hello World");

    await CartonaSunmiPrinter.endTransection();
  }
}
```

For more details on usage, please check on the sample app in the `example` directory.

## Available Methods

| Method | Description |
| ------ | ----------- |
| `initializePrinter()` | Initializes the printer. |
| `getPrinterVersion()` | Returns the printer version. |
| `printText(String text)` | Prints the given text. |
| `setBold(bool enable)` | Toggles font being bold. |
| `setUnderline(bool enable)` | Toggles font being underline. |
| `setFontSize(double size)` | Sets the font size. |
| `setAlignment(SunmiPrintAlign alignment)` | Sets the alignment. (left, center, right) |
| `sendRawData(Uint8List data)` | Sends raw data to the printer |
| `cutPaper()` | Cuts the paper (only supported by limited devices) |
| `line(String ch, int len)` | Draws a line on the receipt. (Default values: '-', 31) |
| `lineWrap(int lines)` | Adds N lines in the receipt, can be used to make space between sections. (Default value: 3) |
| `getPrinterHead()` | Returns the current printer head position |
| `getPrinterDistance()` | Returns the current printer head distance |
| `feedPaper()` | Feeds the paper to the printer (only supported by limited devices) |
| `printBarCode(String data, int symbology, int height, int width, int position)` | Prints a barcode |
| `printQr(String data, int moduleSize, int errorLevel)` | Prints a QR code |
| `printRow(List<String> texts, List<int> width, List<SunmiPrintAlign> align)` | Prints a row of text |
| `printBitmap(ByteData bitmap, int orientation)` | Prints a bitmap |
| `startTransection()` | Starts a transection. |
| `endTransection()` | Ends a transection. |
