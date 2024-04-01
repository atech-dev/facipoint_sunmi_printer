import 'dart:convert';

import 'package:facipoint_sunmi_printer_example/AppPrinterService.dart';
import 'package:facipoint_sunmi_printer_example/extensions.dart';
import 'package:flutter/material.dart';

enum EAttributeType {
  inputCombo("DIN"),
  inputString("VAR"),
  inputNumber("NUM"),
  none("_");

  const EAttributeType(this.code);

  final String code;

  static EAttributeType byCode(String? code) {
    var classifierNumber = EAttributeType.values.singleWhere(
          (e) => e.code == code,
      orElse: () => EAttributeType.none,
    );

    return classifierNumber;
  }
}

enum EMovementOperationStatus { credit, debit, waiting }

enum EPaymentType {
  direct("DIR"),
  reference("REF");

  const EPaymentType(this.name);
  final String name;

  static EPaymentType byName(String? name) {
    return EPaymentType.values.singleWhere(
          (e) => e.name == name,
      orElse: () => EPaymentType.direct,
    );
  }
}

enum EAccountType {
  particular("PART", "Particular"),
  business("EMP", "Empresa"),
  subAccount("SUB", "SubConta"),
  unknown("", "Desconhecido");

  final String name;
  final String fullName;

  String get pFullName => (this == subAccount) ? name : "Conta $fullName";

  static List<EAccountType> get prioritizedAccounts => [
    EAccountType.particular,
    EAccountType.business,
    EAccountType.subAccount,
  ];

  const EAccountType(this.name, this.fullName);

  static EAccountType byName(String? name) {
    return EAccountType.values.singleWhere(
          (e) => e.name == name,
      orElse: () => EAccountType.unknown,
    );
  }
}

enum EMovementType {
  payment("Directo"),
  govSettlementNotePayment("Imposto ao Estado"),
  load("Carregamento"),
  transfer("Transferência"),
  paymentRequest("Solicitado"),
  unload("Levantamento"),
  referencePayment("MULTCX"),
  paymentRequestSent("Solicitação de pagamento enviada"),
  tax("Tarifa FaciPay"),
  membershipBonus("Bónus de Adesão"),
  contractCredit("Crédito de Contrato"),
  externalPaymentRequest("Pagamento Externo"),
  loadRequest(""),
  paymentShared(""),
  prePaymentCancel(""),
  unloadRequest(""),
  agentCommission("Comissão do agente"),
  loadTPI("Carregamento FaciPoint"),
  withdrawalTPI("Levantamento FaciPoint"),
  openedTPI("Abertura de Conta"),
  closedTPI("Fecho de Conta"),;

  const EMovementType(this.name);
  final String name;
}

enum UserGender { M, F }

enum EOperationType {
  addToWallet("carregamento"),
  withdrawal("levantamento");
  const EOperationType(this.name);
  final String name;
}

enum EOperationRequestType {
  qrCode("qr code"),
  nfc("nfc");
  const EOperationRequestType(this.name);
  final String name;
}

class UserModel {
  String? id;
  String? number;
  String? name;
  String? email;
  String? emailOrPhone;
  String? phone;
  String? address;
  String? birthDate;
  String? password;
  String? image;
  UserGender? userGender;
  String? inviteCode;
  EAccountType accountType;
  String? accessKey;
  String? accountNumber;

  UserModel({
    this.id,
    this.number,
    this.name,
    this.email,
    this.emailOrPhone,
    this.phone,
    this.address,
    this.birthDate,
    this.userGender,
    this.image,
    this.password,
    this.inviteCode,
    this.accountType = EAccountType.particular,
    this.accessKey,
    this.accountNumber,
  });

  bool get hasAccessKey =>
      (accessKey != null && (accessKey?.isNotEmpty ?? false));

  bool get hasPhone => (phone != null && (phone?.isNotEmpty ?? false));

  void setEmailOrPhone(String sEmailOrPhone) {
    emailOrPhone = sEmailOrPhone;
    accessKey = accessKey ?? (hasPhone ? phone : email);
  }

  String get _emailOrPhone {
    if (emailOrPhone != null && (emailOrPhone?.isNotEmpty ?? false)) {
      return emailOrPhone!;
    } else if (phone != null && (phone?.isNotEmpty ?? false)) {
      return phone!;
    } else if (email != null && (email?.isNotEmpty ?? false)) {
      return email!;
    } else {
      return "";
    }
  }
}


class MovementModel {
  final String? operationNumber;
  String id;
  String? idAccountSend;
  String? idAccountReceiver;
  double amount;
  final String dateTime;
  final String? lastTransactionDateTime;
  int? status;
  EPaymentType? paymentType;
  double creditBeforeMove;
  double creditAfterMove;
  final String? reference;
  final String? image;
  String name;
  String description;
  UserModel account;
  EMovementType? type;
  double? fee;
  final List<dynamic>? movements;
  String? signal;
  EMISEntityReferenceModel? emisEntityData;
  int quantity;
  Map<String, dynamic>? data;
  bool isFavourite;
  Classificador? classificador;

  MovementModel({
    this.operationNumber,
    this.id = "",
    this.idAccountSend,
    this.idAccountReceiver,
    this.amount = 0.0,
    this.dateTime = "",
    this.lastTransactionDateTime,
    this.status,
    this.paymentType,
    this.name = "",
    this.description = "",
    required this.account,
    this.creditBeforeMove = 0.0,
    this.creditAfterMove = 0.0,
    this.reference,
    this.image,
    this.type,
    this.fee,
    this.movements,
    this.signal,
    this.emisEntityData,
    this.quantity = -1,
    this.data,
    this.isFavourite = false,
    this.classificador
  }) {
    _getMovementType();
    _getCreditBeforeAndAfterMov();
    _getMovementAmount();
  }

  bool get isTPIOpen {
    return type == EMovementType.openedTPI;
  }

  bool get isTPIClosed {
    return type == EMovementType.closedTPI;
  }

  bool get isGovPayment {
    return type == EMovementType.govSettlementNotePayment;
  }

  bool get isTPIWithdrawal {
    return type == EMovementType.withdrawalTPI;
  }

  bool get isTPIDeposit {
    return type == EMovementType.loadTPI;
  }

  bool get isTax {
    return type == EMovementType.tax;
  }

  bool get isCanceled {
    return (status ?? 2) == 2;
  }

  bool get isAgentCommission {
    return type == EMovementType.agentCommission;
  }

  bool get isExternalPaymentRequest {
    return type == EMovementType.externalPaymentRequest;
  }

  bool get isMembershipBonus {
    return type == EMovementType.membershipBonus ||
        type == EMovementType.contractCredit;
  }

  bool get isWithdrawal {
    return type == EMovementType.unload || type == EMovementType.unloadRequest;
  }

  bool get isMultcxPayment {
    return ((creditBeforeMove ?? 0.0) == 0.0 &&
        (creditAfterMove ?? 0.0) == 0.0 &&
        isMultcxPaymentType);
  }

  bool get isMultcxPaymentType {
    return type == EMovementType.referencePayment;
  }

  bool get isLoadRequest {
    return type == EMovementType.load;
  }

  bool get isPaymentRequest {
    return type == EMovementType.paymentRequest;
  }

  // region Helper methods
  void _getMovementType() {
    type = EMovementType.payment;
  }

  void _getCreditBeforeAndAfterMov() {
    var operationsList = movements;
    var loggedUserIdAccount = 0;

    if (operationsList != null) {
      if (operationsList.isNotEmpty) {
        var operations = operationsList
            .where(
              (e) => "${e["idFpConta"]}" == loggedUserIdAccount,
          // orElse: () => null,
        )
            .toList();

        dynamic operation;
        if (operations.isNotEmpty) {
          operation = operations[0];
        }

        if (operation != null) {
          signal = operation["tpSinal"];
          creditBeforeMove =
              double.tryParse("${operation["vlSaldoPreMov"]}") ?? 0.00;
          creditAfterMove = (operation["tpSinal"] == "-")
              ? creditBeforeMove - amount
              : creditBeforeMove + amount;

          return;
        }
      }
    }

    creditBeforeMove = 0.00;
    creditAfterMove = 0.00;
  }

  void _getMovementAmount() {
    /*if (classifierNumber != EClassifierNumber.transfer && !isDebit) {
      amount = (amount ?? 0.0) - (fee ?? 0.0);
    }*/
  }

// endregion
}

class EMISEntityReferenceModel {
  EMISEntityReferenceModel({
    required this.number,
    required this.name,
    required this.reference,
  });

  factory EMISEntityReferenceModel.fromJson(Map<String, dynamic> json) {
    return EMISEntityReferenceModel(
      number: json["nuEntidade"] ?? "",
      name: json["noEntidade"] ?? "",
      reference: json["cdReferenciaEmis"] ?? "",
    );
  }

  final String number;
  final String name;
  final String reference;
}


class Classificador{
  final int?    idClassificador;
  final String?  noClassificador;
  final String?  nrClassificador;
  final String ? contentId;

  Classificador({
    required this.contentId,
    required this.idClassificador,
    required this.noClassificador,
    required this.nrClassificador
  });

  factory Classificador.fromJson(Map<String, dynamic> json) {
    return Classificador(
        contentId: json['content_id'],
        idClassificador: json['idClassificador'],
        noClassificador: json['noClassificador'],
        nrClassificador: json['nrClassificador']
    );
  }
}

class FacipayServiceModel {
  FacipayServiceModel({
    required this.idDocumento,
    required this.idDocumentoTitulo,
    required this.sgDocumento,
    required this.dsDocumento,
    required this.noDocumento,
    required this.flAtivo,
    required this.contentId,
    required this.noComponente,
    required this.flTemFilhos,
    required this.flTemConteudo,
    this.attributes,
  });

  String idDocumento;
  String idDocumentoTitulo;
  String sgDocumento;
  String dsDocumento;
  String noDocumento;
  String flAtivo;
  String contentId;
  String noComponente;
  String flTemFilhos;
  String flTemConteudo;
  List<ServiceAttributeModel>? attributes;

  void setAttribute(String name, dynamic value, [String? description]) {
    bool changeValue = false;
    List<ServiceAttributeModel>? attributesList = [];
    if (attributes != null) {
      attributesList = attributes?.map((e) {
        if (e.noAtributo == name) {
          changeValue = true;
          e.vlAtributo = value;
        }
        return e;
      }).toList();
    }
    if (changeValue) {
      attributes = attributesList;
    }
  }

  void setAttributeIdChild(String name, dynamic value) {
    bool changeValue = false;
    List<ServiceAttributeModel>? attributesList = [];
    if (attributes != null) {
      attributesList = attributes?.map((e) {
        if (e.noAtributo == name) {
          changeValue = true;
          e.vlChildAttribute = value;
        }
        return e;
      }).toList();
    }
    if (changeValue) {
      attributes = attributesList;
    }
  }

  void setAttributeDs(String name, String? value) {
    bool changeValue = false;
    List<ServiceAttributeModel>? attributesList = [];
    if (attributes != null) {
      attributesList = attributes?.map((e) {
        if (e.noAtributo == name) {
          changeValue = true;
          e.dsVlAtributo = value;
        }
        return e;
      }).toList();
    }
    if (changeValue) {
      attributes = attributesList;
    }
  }

  void setCustomExtraFlag(String name, bool value) {
    bool changeValue = false;
    List<ServiceAttributeModel>? attributesList = [];
    if (attributes != null) {
      attributesList = attributes?.map((e) {
        if (e.noAtributo == name) {
          changeValue = true;
          e.hasCustom = value;
        }
        return e;
      }).toList();
    }
    if (changeValue) {
      attributes = attributesList;
    }
  }

  String? getAttributeIdChild(String name) {
    List<ServiceAttributeModel>? attributesList = [];
    if (attributes != null) {
      attributesList = attributes!.where((e) => e.noAtributo == name).toList();
      if (attributesList.isNotEmpty) {
        return attributesList.first.vlChildAttribute;
      }
    }
    return null;
  }

  String? getAttributeDs(String name) {
    List<ServiceAttributeModel>? attributesList = [];
    if (attributes != null) {
      attributesList = attributes!.where((e) => e.noAtributo == name).toList();
      if (attributesList.isNotEmpty) {
        return attributesList.first.dsVlAtributo;
      }
    }
    return null;
  }

  bool getCustomExtraFlag(String name) {
    List<ServiceAttributeModel>? attributesList = [];
    if (attributes != null) {
      attributesList = attributes!.where((e) => e.noAtributo == name).toList();
      if (attributesList.isNotEmpty) {
        return attributesList.first.hasCustom;
      }
    }
    return false;
  }

  void setAttributeValues(
      String name, List<ServiceAttributeValueModel> values) {
    bool changeValue = false;
    List<ServiceAttributeModel>? attributesList = [];
    if (attributes != null && values != null) {
      attributesList = attributes?.map((e) {
        if (e.noAtributo == name) {
          changeValue = true;
          e.values = values;
        }
        return e;
      }).toList();
    }

    if (changeValue) {
      attributes = attributesList;
    }
  }

  void setLoadingAttributeValues(String name, bool loading) {
    bool changeValue = false;
    List<ServiceAttributeModel>? attributesList = [];
    if (attributes != null) {
      attributesList = attributes?.map((e) {
        if (e.noAtributo == name) {
          changeValue = true;
          e.loading = loading;
        }
        return e;
      }).toList();
    }

    if (changeValue) {
      attributes = attributesList;
    }
  }

  ServiceAttributeModel? getAttribute(String name) {
    List<ServiceAttributeModel> attributesList =
        attributes?.where((e) => e.noAtributo == name).toList() ?? [];
    if (attributesList.isNotEmpty) {
      return attributesList.first;
    }
    return null;
  }

  bool getAttributeValueLoading(String name) {
    List<ServiceAttributeModel> attributesList =
        attributes?.where((e) => e.noAtributo == name).toList() ?? [];
    if (attributesList.isNotEmpty) {
      return attributesList.first.loading;
    }
    return false;
  }

  ServiceAttributeModel? getAttributeByIdTitle(int idTitle) {
    List<ServiceAttributeModel> attributesList =
        attributes?.where((e) => e.idAtributoTitulo == idTitle).toList() ?? [];
    if (attributesList.isNotEmpty) {
      return attributesList.first;
    }
    return null;
  }

  ServiceAttributeModel? getAttributeById(int idTitle) {
    List<ServiceAttributeModel> attributesList =
        attributes?.where((e) => e.idAtributo == idTitle).toList() ?? [];
    if (attributesList.isNotEmpty) {
      return attributesList.first;
    }
    return null;
  }

  String getAttributeId(String name) {
    var attribute = getAttribute(name);
    if (attribute != null) {
      return attribute.idAtributo.toString();
    }
    return "";
  }

  String? getAttributeMainDs(String name) {
    var attribute = getAttribute(name);
    if (attribute != null) {
      return attribute.dsAtributo;
    }
    return "";
  }

  String? getAttributeValue(String name) {
    var attribute = getAttribute(name);
    if (attribute != null) {
      if (attribute.values != null) {
        var attributes = attribute.values ?? [];
        var item = attributes
            .where((servico) => servico.id == attribute.vlAtributo)
            .toList();
        if (item.isNotEmpty) {
          return item.first.value;
        }
        return attribute!.vlAtributo;
      }
      if (!attribute.vlAtributo.isNullOrEmpty) {
        return attribute!.vlAtributo;
      }
    }
    return null;
  }

  Map<String, dynamic>? getAttributeValueAsMap(String name) {
    var value = getAttributeValue(name);
    if (value != null) {
      return jsonDecode(value);
    }
    return null;
  }

  String? get getRechargeToken {
    var responseRecharge = getAttributeValueAsMap("fpservicoResponseRecarga");
    if (responseRecharge != null) {
      return responseRecharge["rechargeToken"];
    }
    return null;
  }

  ServiceAttributeValueModel? getAttributeDiscriptionValue(String name) {
    var attribute = getAttribute(name);
    if (attribute != null) {
      if (attribute.values != null) {
        var attributes = attribute.values ?? [];
        var item = attributes
            .where((servico) => servico.id == attribute.vlAtributo)
            .toList();
        if (item.isNotEmpty) {
          return item.first;
        }
        return attribute!.values!.first;
      }
      if (!attribute.values!.isNotEmpty) {
        return attribute!.values!.first;
      }
    }
    return null;
  }

  ServiceAttributeModel? getAttributeWithValue(String name) {
    var attribute = getAttribute(name);
    if (attribute != null) {
      return (attribute.vlAtributo?.isNullOrEmpty ?? false) ? null : attribute;
    }
    return null;
  }

  ServiceAttributeValueModel? getAttributeWithSelectedValue(String name) {
    var attribute = getAttribute(name);
    if (attribute != null) {
      if ((attribute.values?.isNotEmpty ?? false) &&
          attribute.vlAtributo != null) {
        return attribute.values!
            .singleWhere((e) => e.id == attribute.vlAtributo.toString());
      }
    }
    return null;
  }

  String getAttributeValueFk(String name) {
    var attribute = getAttribute(name);
    if (attribute != null) {
      /*if (attribute.vlAtributo.isNotEmpty) {
        return attribute.vlAtributo.first.vlAtributoFk.toString();
      }*/
    }
    return "";
  }

  ServiceAttributeModel? getAttributeFromRange(List<String> attrNames) {
    var index = attributes!.indexWhere((e) => attrNames.contains(e.noAtributo));

    return index != -1 ? attributes![index] : null;
  }

  resetValues(String name) {
    setAttributeValues(name, []);
    setAttribute(name, null);
    setAttributeIdChild(name, null);
  }

  FacipayServiceModel copyWith({
    String? idDocumento,
    String? idDocumentoTitulo,
    String? sgDocumento,
    String? dsDocumento,
    String? noDocumento,
    String? flAtivo,
    String? contentId,
    String? noComponente,
    String? flTemFilhos,
    String? flTemConteudo,
  }) =>
      FacipayServiceModel(
        idDocumento: idDocumento ?? this.idDocumento,
        idDocumentoTitulo: idDocumentoTitulo ?? this.idDocumentoTitulo,
        sgDocumento: sgDocumento ?? this.sgDocumento,
        dsDocumento: dsDocumento ?? this.dsDocumento,
        noDocumento: noDocumento ?? this.noDocumento,
        flAtivo: flAtivo ?? this.flAtivo,
        contentId: contentId ?? this.contentId,
        noComponente: noComponente ?? this.noComponente,
        flTemFilhos: flTemFilhos ?? this.flTemFilhos,
        flTemConteudo: flTemConteudo ?? this.flTemConteudo,
      );

  factory FacipayServiceModel.fromRawJson(String str) =>
      FacipayServiceModel.fromJson(json.decode(str));

  String toRawJson() => json.encode(toJson());

  factory FacipayServiceModel.fromJson(Map<String, dynamic> json) {
    List<ServiceAttributeModel>? attributes;
    if (json["atributos"] != null) {
      List<dynamic> list = [];
      if (json["atributos"] is String) {
        list = jsonDecode(json["atributos"]);
      } else if (json["atributos"] is List<dynamic>) {
        list = json["atributos"];
      }
      attributes = list.map((e) => ServiceAttributeModel.fromJson(e)).toList();
    }

    return FacipayServiceModel(
      idDocumento: "${json["idDocumento"] ?? ""}",
      idDocumentoTitulo: "${json["idDocumentoTitulo"] ?? ""}",
      sgDocumento: json["sgDocumento"] ?? "",
      dsDocumento: json["dsDocumento"] ?? "",
      noDocumento: json["noDocumento"] ?? "",
      flAtivo: json["flAtivo"] ?? "",
      contentId: json["contentId"] ?? "",
      noComponente: json["noComponente"] ?? "",
      flTemFilhos: json["flTemFilhos"] ?? "",
      flTemConteudo: json["flTemConteudo"] ?? "",
      attributes: attributes,
    );
  }

  Map<String, dynamic> toJson() => {
    "idDocumento": idDocumento,
    "idDocumentoTitulo": idDocumentoTitulo,
    "sgDocumento": sgDocumento,
    "dsDocumento": dsDocumento,
    "noDocumento": noDocumento,
    "flAtivo": flAtivo,
    "contentId": contentId,
    "noComponente": noComponente,
    "flTemFilhos": flTemFilhos,
    "flTemConteudo": flTemConteudo,
  };
}

class ServiceAttributeModel {
  ServiceAttributeModel({
    required this.nuOrdem,
    required this.idAtributo,
    required this.idAtributoTitulo,
    required this.noAtributo,
    required this.dsAtributo,
    required this.tamAtributo,
    required this.tpDadoAtributo,
    required this.flFk,
    required this.flMultivalor,
    required this.flObrigatorio,
    required this.dsInfoAdicional,
    this.vlAtributo,
    this.dsVlAtributo,
    this.values,
    this.loading = true,
    this.vlChildAttribute,
    this.hasCustom = false,
  });

  int nuOrdem;
  int idAtributo;
  dynamic idAtributoTitulo;
  String noAtributo;
  String dsAtributo;
  int tamAtributo;
  EAttributeType tpDadoAtributo;
  String flFk;
  String flMultivalor;
  String flObrigatorio;
  Map<dynamic, dynamic>? dsInfoAdicional;
  String? vlAtributo;
  String? dsVlAtributo;
  List<ServiceAttributeValueModel>? values;
  bool loading;
  String? vlChildAttribute;
  bool hasCustom;

  bool get isRequired => flObrigatorio == "S";

  String? get additionalName => _getAdditionalData("noAdicional");

  String get additionalDescription => _getAdditionalData("dsAdicional");

  String get additionalData => _getAdditionalData("obtemDadoUtilizador");

  _getAdditionalData(String name) {
    return dsInfoAdicional != null ? dsInfoAdicional![name] : "";
  }

  ServiceAttributeModel copyWith({
    int? nuOrdem,
    int? idAtributo,
    dynamic idAtributoTitulo,
    String? noAtributo,
    String? dsAtributo,
    int? tamAtributo,
    EAttributeType? tpDadoAtributo,
    String? flFk,
    String? flMultivalor,
    String? flObrigatorio,
    Map<dynamic, dynamic>? dsInfoAdicional,
    String? vlAtributo,
    String? dsVlAtributo,
    List<ServiceAttributeValueModel>? values,
  }) =>
      ServiceAttributeModel(
        nuOrdem: nuOrdem ?? this.nuOrdem,
        idAtributo: idAtributo ?? this.idAtributo,
        idAtributoTitulo: idAtributoTitulo ?? this.idAtributoTitulo,
        noAtributo: noAtributo ?? this.noAtributo,
        dsAtributo: dsAtributo ?? this.dsAtributo,
        tamAtributo: tamAtributo ?? this.tamAtributo,
        tpDadoAtributo: tpDadoAtributo ?? this.tpDadoAtributo,
        flFk: flFk ?? this.flFk,
        flMultivalor: flMultivalor ?? this.flMultivalor,
        flObrigatorio: flObrigatorio ?? this.flObrigatorio,
        dsInfoAdicional: dsInfoAdicional ?? this.dsInfoAdicional,
        vlAtributo: vlAtributo ?? this.vlAtributo,
        dsVlAtributo: dsVlAtributo ?? this.dsVlAtributo,
        values: values ?? this.values,
      );

  factory ServiceAttributeModel.fromJson(Map<String, dynamic> json) {
    int? titleAttributeId = json["idAtributoTitulo"];
    return ServiceAttributeModel(
      nuOrdem: json["nuOrdem"] ?? 0,
      idAtributo: json["idAtributo"] ?? 0,
      idAtributoTitulo: titleAttributeId,
      loading: titleAttributeId == null,
      noAtributo: json["noAtributo"] ?? "",
      dsAtributo: json["dsAtributo"] ?? "",
      tamAtributo: json["tamAtributo"] ?? 0,
      tpDadoAtributo: EAttributeType.byCode(json["tpDadoAtributo"]),
      flFk: json["flFk"] ?? "",
      flMultivalor: json["flMultivalor"] ?? "",
      flObrigatorio: json["flObrigatorio"] ?? "",
      dsInfoAdicional: json["dsInfoAdicional"] != null
          ? jsonDecode(json["dsInfoAdicional"].toString().replaceAll("'", "\""))
          : null,
      vlAtributo: json["vlAtributo"],
    );
  }

  Map<String, dynamic> toJson() => {
    "nuOrdem": nuOrdem,
    "idAtributo": idAtributo,
    "idAtributoTitulo": idAtributoTitulo,
    "noAtributo": noAtributo,
    "dsAtributo": dsAtributo,
    "tamAtributo": tamAtributo,
    "tpDadoAtributo": tpDadoAtributo,
    "flFk": flFk,
    "flMultivalor": flMultivalor,
    "flObrigatorio": flObrigatorio,
    "dsInfoAdicional": dsInfoAdicional,
  };

  Map<String, dynamic> toJsonRequest() {
    return {
      "idAtributo": idAtributo,
      "noAtributo": noAtributo,
      // TODO: Check for attachments
      "vlAtributo": vlChildAttribute ?? vlAtributo ?? "",
      "dsVlAtributo": idAtributoTitulo != null ? dsVlAtributo : null,
      // "vlAtributoFk": flFk == "S" ? value : "",
    };
  }
}

class ServiceAttributeValueModel {
  ServiceAttributeValueModel({
    required this.id,
    required this.value,
    required this.widget,
    required this.noItem,
    required this.vlItem,
  });

  //idItem;
  String id;

  // vlItem
  String value;
  Widget? widget;
  String? noItem;
  String? vlItem;

  ServiceAttributeValueModel copyWith({
    String? id,
    String? value,
    String? noItem,
    String? idItemTitulo,
    Widget? widget,
    String? vlItem,
  }) =>
      ServiceAttributeValueModel(
        id: id ?? this.id,
        value: value ?? this.value,
        widget: widget ?? this.widget,
        noItem: noItem ?? this.noItem,
        vlItem: vlItem ?? this.vlItem,
      );

  factory ServiceAttributeValueModel.fromJson(Map<String, dynamic> json) {
    return ServiceAttributeValueModel(
      id: json["id"],
      value: json["value"],
      widget: json["widget"],
      noItem: json["noItem"],
      vlItem: json["vlItem"],
    );
  }

  Map<String, dynamic> toJson() =>
      {"id": id, "value": value, "noItem": noItem, "vlItem": vlItem};

  @override
  String toString() {
    return "{id: $id, value:$value, noItem: $noItem, vlItem: $vlItem}";
  }
}

enum ESettlementNoteModelStatus {
  pendent("Pendente"),
  confirmed("Confirmado"),
  canceled("Cancelado");

  const ESettlementNoteModelStatus(this.name);

  final String name;
}

class SettlementNoteModel {
  String? transactionId;
  final String name;
  final String sgName;
  final String institution;
  final String sgInstitution;
  final String code;
  final String rupe;
  final double value;
  ESettlementNoteModelStatus status;
  final DateTime datetime;
  final DateTime dueDatetime;

  SettlementNoteModel({
    this.transactionId,
    required this.name,
    required this.sgName,
    required this.institution,
    required this.sgInstitution,
    required this.code,
    required this.rupe,
    required this.value,
    required this.datetime,
    required this.dueDatetime,
    required this.status,
  });

  factory SettlementNoteModel.fromJson(Map<String, dynamic> json) {
    return SettlementNoteModel(
      name: json["servicos"] ?? "",
      sgName: json["siglaPortal"] ?? "",
      institution: json["noPortal"] ?? "",
      sgInstitution: json["siglaPortal"] ?? "",
      code: "${json["nuDoc"] ?? ""}",
      rupe: json["rupe"] ?? "",
      value: double.tryParse("${json["valor"]}") ?? 0.0,
      dueDatetime: json["dtVencimento"] != null
          ? DateTime.tryParse(json["dtVencimento"]) ?? DateTime.now()
          : DateTime.now(),
      datetime: json["dtEmissao"] != null
          ? (DateTime.tryParse(json["dtEmissao"]) ?? DateTime.now())
          : DateTime.now(),
      status: ESettlementNoteModelStatus.pendent,
    );
  }

  Map<String, dynamic> toJson() {
    return {
      "servicos": name,
      "noPortal": institution,
      "siglaPortal": sgInstitution,
      "nuDoc": code,
      "rupe": rupe,
      "valor": value,
      "dtVencimento": dueDatetime.toIso8601String(),
      "dtEmissao": datetime.toIso8601String(),
    };
  }
}
