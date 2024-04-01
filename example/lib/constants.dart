import 'package:intl/intl.dart';

log(object) {
  print(object);
}

const double kMinLimitToCompactCurrency = 10000000;

String simpleNumberFormat(
    String? number, {
      String formatType = "AOA",
      bool withSymbol = false,
      bool alwaysExtendedValue = false,
      double minLimitToCompatCurrency = kMinLimitToCompactCurrency,
      int decimalDigits = 2,
    }) {
  final numberToDouble = (double.tryParse(number ?? "0") ?? 0.00);

  var formattedNumber = NumberFormat.currency(
    locale: "es",
    symbol: (withSymbol) ? formatType : "",
    decimalDigits: decimalDigits,
  ).format(numberToDouble) ??
      "0,00";

  if (numberToDouble > minLimitToCompatCurrency && !alwaysExtendedValue) {
    return NumberFormat.compactCurrency(
      locale: "es",
      symbol: (withSymbol) ? " $formatType" : "",
      decimalDigits: decimalDigits,
    ).format(numberToDouble);
  }

  return formattedNumber;
}