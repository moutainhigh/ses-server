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
}
