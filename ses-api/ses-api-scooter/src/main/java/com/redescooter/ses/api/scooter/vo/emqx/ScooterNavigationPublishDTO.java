package com.redescooter.ses.api.scooter.vo.emqx;

import lombok.Data;

import java.math.BigDecimal;

/**
 * EMQ X向车辆平板端下发导航操作消息 DTO
 * @author assert
 * @date 2020/11/25 15:52
 */
@Data
public class ScooterNavigationPublishDTO {

    /**
     * 车辆平板序列号
     */
    private String tabletSn;

    /**
     * 经度(开始导航时需要)
     */
    private BigDecimal longitude;

    /**
     * 维度(开始导航时需要)
     */
    private BigDecimal latitude;

    /**
     * 车辆锁操作类型 1-开始导航 2-结束导航
     */
    private Integer type;

}
