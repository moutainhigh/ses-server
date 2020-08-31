package com.redescooter.ses.web.ros.exception;

/**
 * @ClassName:SellsyValidationExceptionCode
 * @description: SellsyValidationExceptionCode
 * @author: Alex
 * @Version：1.3
 * @create: 2020/08/26 19:39
 */
public interface ThirdValidationExceptionCode extends ValidationExceptionCode{
    // sellsy 方法是空
    int SELLSY_METHOD_IS_EMPTY=11000;
    //sellsy 方法类型是空
    int SELLSY_METHOD_TYPE_IS_EMPTY=11001;
    //客户Id
    int SELLSY_CLIENT_ID_IS_EMPTY =11002;
    
    //单据类型为空
    int SELLSY_DOCTYPE_IS_EMPTY =11003;

    // 文档主题为空
    int SELLSY_DOCUMENT_OBJECT_IS_EMPTY = 11004;

    // 产品类型为空
    int SELLSY_CATALOGUE_TYPE_IS_EMPTY = 11005;

    // 开具发票时间
    int SELLSY_DOCUMENT_DISPLAYEDDATE_IS_EMPTY = 11006;

    // 发票上填充收货人地址
    int SELLSY_DOCUMENT_DISPLAYSHIPADDRESS_IS_EMPTY = 11007;

    // 货币单位为空
    int SELLSY_DOCUMENT_CURRENCY_IS_EMPTY = 11008;

    // 发票布局为空
    int SELLSY_DOCUMENT_DOCLAYOUT_IS_EMPTY = 11009;

    // 发票语言为空
    int SELLSY_DOCUMENT_LANG_IS_EMPTY = 11010;

    // 支付方式为空
    int SELLSY_DOCUMENT_PAY_TYPE_IS_EMPTY = 11011;

    // 公司地址为空
    int SELLSY_DOCUMENT_COMPANY_ADDRESS_IS_EMPTY = 11012;

    // 产品数量为空
    int SELLSY_DOCUMENT_PRODUCT_QTY_IS_EMPTY = 11013;

    // 产品重量为空
    int SELLSY_DOCUMENT_PRODUCT_WIGHT_IS_EMPTY = 11014;

    //行类型
    int ROS_TYPE_IS_EMTPY = 11015;

    //行标题为空
    int SELLSY_DOCUMENT_ROW_TITLE_IS_EMPTY = 11015;

    //地址Id为空
    int SELLSY_ADDRESS_ID_EMPTY = 11016;
}
