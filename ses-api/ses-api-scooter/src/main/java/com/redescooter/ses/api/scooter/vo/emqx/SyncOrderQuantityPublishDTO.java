package com.redescooter.ses.api.scooter.vo.emqx;

import lombok.Data;

import java.io.Serializable;

/**
 * 同步订单数量到车载平板 -- 用于EMQ X指令下发时使用
 * @author assert
 * @date 2020/12/18 11:18
 */
@Data
public class SyncOrderQuantityPublishDTO implements Serializable {

    /**
     * 车辆平板序列号
     */
    private String tabletSn;

    /**
     * 订单数量 -- 只统计当前配送中、待配送的订单数量
     */
    private int quantity;

}
