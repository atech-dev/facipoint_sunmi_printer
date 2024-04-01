import 'package:characters/characters.dart';
import 'package:intl/intl.dart';

extension DiacriticsNull on String? {
  bool get isNullOrEmpty {
    return !(this != null &&
        (this?.replaceAll(" ", "").replaceAll("null", "").isNotEmpty ?? false));
  }

  bool get isNotNullOrEmpty {
    return !isNullOrEmpty;
  }
}

extension CharactersLibExtensions on Characters {
  String capitalize() {
    String cap(Characters s) {
      if (s.isNotEmpty) {
        return "${s.first.toUpperCase()}${s.skip(1).toLowerCase()}";
      } else {
        return s.string.toUpperCase();
      }
    }

    List<Characters> words = split(Characters(" ")).toList();
    int wordsLength = words.length - 1;
    String finalString = "";

    for (var i = 0; i <= wordsLength; i++) {
      finalString += cap(words[i]);
      finalString += (i < wordsLength) ? " " : "";
    }

    return finalString;
  }
}

extension Diacritics on String {

  String firstAndLastWords() {
    if (this != null) {
      if (this!.isNotEmpty) {
        var strings = this!.characters.split(Characters(" "));
        if (strings.length > 1 && strings.last.isNotEmpty) {
          return "${strings.first.capitalize()} ${strings.last.capitalize()}";
        }

        return strings.first.capitalize();
      }

      return this!;
    }
    return "";
  }


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

  String get removeDiacritics {
    return replaceAllMapped(
      RegExp('[À-ž]'),
          (m) => diacriticsMapping[m.group(0)] ?? '',
    );
  }

  bool get isNullOrEmpty {
    return !(this != null &&
        (this?.replaceAll(" ", "").replaceAll("null", "").isNotEmpty ?? false));
  }

  bool get isNotNullOrEmpty {
    return !isNullOrEmpty;
  }

  String truncate({int maxLength = 16}) {
    return (length >= maxLength ? "${substring(0, maxLength)}..." : this);
  }

  String dateFormatV5({bool withSeconds = false}) {
    if (this != null) {
      final dateTime = DateTime.tryParse(this);

      if (dateTime != null) {
        return "${dateTime.hour.toString().twoDigitNumber()}"
            ":${dateTime.minute.toString().twoDigitNumber()}${withSeconds ? ":${dateTime.second.toString().twoDigitNumber()}" : ""}";
      }
    }
    return this;
  }

  String dateFormatIntl([String format = "dd/MM/yyyy"]) {
    if (this != null) {
      return DateFormat(format, "pt_BR")
          .format(DateTime.tryParse(this) ?? DateTime.now());
    }
    return this;
  }

  String twoDigitNumber() {
    if (isNotEmpty) return (split("").length > 1) ? this : "0$this";
    return this;
  }

  String groupBy({int group = 3}) {
    if (this != null) {
      String endString = "";
      for (var i = 0; i < this!.length; i += group) {
        var limit = i + group;
        endString +=
        "${this!.substring(i, limit > this!.length ? this!.length : limit)} ";
      }

      return endString;
    }
    return "";
  }

  String capitalize() {
    if (this != null) {
      List<String> words = split(" ");
      int wordsLength = words.length - 1;
      String finalString = "";

      for (var i = 0; i <= wordsLength; i++) {
        finalString += words[i].capitalizeWord();
        finalString += (i < wordsLength) ? " " : "";
      }

      return finalString;
    }
    return this;
  }

  String capitalizeWord() {
    if (this != null) {
      if (isNotEmpty) {
        return "${substring(0, 1).toUpperCase()}${substring(1).toLowerCase()}";
      }
    }
    return this;
  }
}

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