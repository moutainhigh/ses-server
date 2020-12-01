package com.redescooter.ses.api.scooter.vo.emqx;

import lombok.Data;

/**
 * EMQ X向车辆平板端下发锁操作消息 DTO
 * @author assert
 * @date 2020/11/19 11:44
 */
@Data
public class ScooterLockPublishDTO {

    /**
     * 车辆平板序列号
     */
    private String tabletSn;
    /**
     * 车辆锁操作类型 1-lock上锁 2-unlock解锁
     */
    private Integer type;

}
