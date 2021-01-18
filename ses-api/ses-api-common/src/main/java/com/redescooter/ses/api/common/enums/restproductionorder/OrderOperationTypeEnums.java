package com.redescooter.ses.api.common.enums.restproductionorder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 *  @author: alex
 *  @Date: 2020/10/27 18:47
 *  @version：V ROS 1.8.3
 *  @Description:
 */

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum OrderOperationTypeEnums {
    CREATE("CREATE", "创建", 1),
    EDIT("EDIT", "编辑", 2),
    PLACE_ORDER("PLACE_ORDER", "下单", 3),
    DELETE("DELETE", "删除", 4),
    CANCEL("CANCEL", "取消", 5),
    CLOSE("CLOSE", "关闭", 6),
    STOCK_UP("STOCK_UP", "备货", 7),
    LOADING("LOADING", "装车", 8),
    START_QC("START_QC", "开始质检", 9),
    OUT_STOCK("OUT_STOCK", "出库", 10),
    SHIPMENT("SHIPMENT", "发货", 11),
    SIGN_FOR("SIGN_FOR", "签收", 12),
    READY_QC("READY_QC", "准备质检", 13),
    CONFIRM_IN_WH("CONFIRM_IN_WH", "确认入库", 14),
    COMBIN("COMBIN","组装",15),
    RELEASE("COMBIN","发布",16),
    CONFIRM_OUT_WH("COMBIN","确认出库",17),
    ;


    private String code;

    private String message;

    private Integer value;
}
