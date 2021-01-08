package com.redescooter.ses.mobile.rps.exception;

/**
 * @description: ValidationExceptionCode
 * @author: Darren
 * @create: 2019/01/17 10:26
 */
public interface ValidationExceptionCode {

    // ###############################10001~10030公共入参异常#######################################
    // id 为空
    int ID_IS_EMPTY = 10008;
    //长度过长，长度为2-20字符
    int CHARACTER_IS_TOO_LONG = 10010;
    //长度过短，长度为2-20字符
    int CHARACTER_IS_TOO_SHORT = 10011;
    //国家手机号编码不能为空
    int COUNTRY_CODE_IS_EMPTY = 100012;
    //URL不能为空
    int URL_IS_EMPTY = 10013;

    // ###############################10030~无穷大公共入参异常#######################################
    //条码打印结果为空
    int PRINT_CODE_RESULT = 10030;
    //部件列表入参为空
    int PART_LIST_JSON = 10031;
    //数量不能为空
    int QTY_IS_EMPTY = 10032;
    //部件结果模板为空
    int PART_TRMPLATE_RESULT_IS_EMPTY = 10033;
    //单据来源类型
    int SOURCE_TYPE_IS_EMPTY = 10034;
    // 批次号
    int BATCH_NO = 10036;
    //序列号
    int SERIAL_NUM=10037;

    //状态为空
    int STATUS_IS_EMPTY = 10038;
    //类型为空
    int TYPE_IS_EMPTY = 10039;
    // 出库单类型为空
    int OUT_WH_ORDER_TYPE_IS_EMPTY = 10040;
    // 产品id为空
    int PRODUCT_ID_IS_EMPTY = 10041;
    // 产品类型为空
    int PRODUCT_TYPE_IS_EMPTY = 10042;
    // 出库单id为空
    int OUT_WH_ORDER_ID_IS_EMPTY = 10043;

}
