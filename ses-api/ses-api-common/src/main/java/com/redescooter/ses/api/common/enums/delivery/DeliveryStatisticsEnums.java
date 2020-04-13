package com.redescooter.ses.api.common.enums.delivery;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @ClassName:DeliveryStatisticsEnums
 * @description: 主要记录终结状态的订单 是否被订单任务 抓到 订单数据中
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/31 13:13
 */
@Getter
@AllArgsConstructor
public enum DeliveryStatisticsEnums {

    COUNTED("COUNTED", "已统计", "1"),
    NOT_COUNTED("NOT_COUNTED", "未统计", "0");

    private String code;

    private String message;

    private String value;
}
