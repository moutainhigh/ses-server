package com.redescooter.ses.web.ros.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ThirdExceptionCodeEnums {

    SELLSY_CLIENT_IS_NOT_EXIST(11000, "Sellsy客户不存在"),

    SELLSY_DOCUMENT_IS_ALREADY_EXIST(11001, "发票已经存在"),

    SELLSY_DOCUMNT_RATECATEGORY_IS_EMPTY(11002, "税率不存在"),

    SELLSY_DOCUMENT_CURRENCY_IS_NOT_EXIST(11003, "货币单位不存在"),

    SELLSY_DOCUMENT_USESERVICEDATES_IS_EMPTY(11004, "服务时间为空"),

    SELLSY_DOCUMRNT_PACKING_ID_IS_EMPTY(11005, "打包Id为空"),

    SELLSY_DOCUMENT_PACKING_IS_NOT_EXIST(11006, "打包方式不存在"),

    SELLSY_LAYOUT_IS_NOT_EXIST(11007, "页面布局不存在"),

    SELLSY_TRANSLATION_LANG_IS_NOT_EXIST(11008, "翻译语言不存在"),

    SELLSY_ADDRESS_IS_NOT_EXIST(11009, "客户地址不存在"),

    SELLSY_DOCUMENT_INDENT_IS_ALREADY_EXIST(11010, "发票号已存在"),

    SELLSY_INVOICE_B_IS_NOT_EXIST(11011, "发票子记录不存在"),

    SELLSY_CORPINFO_IS_NOT_EXIST(11012, "个人信息不存在"),

    SELLSY_RATE_CATEGORY_IS_NOT_EXIST(11013, "文档增值税类型为空"),
    ;

    private int code;

    private String message;
}
