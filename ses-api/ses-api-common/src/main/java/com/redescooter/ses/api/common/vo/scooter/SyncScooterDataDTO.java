package com.redescooter.ses.api.common.vo.scooter;

import lombok.Data;

import java.io.Serializable;

/**
 * 同步车辆数据 DTO
 *
 * @author assert
 * @date 2020/12/10 20:35
 */
@Data
public class SyncScooterDataDTO implements Serializable {

    /**
     * 车辆Id
     */
    private Long id;

    /**
     * 车辆编号(整车rsn)
     */
    private String scooterNo;

    /**
     * 车载平板序列号
     */
    private String tabletSn;

    /**
     * 车载平板Mac蓝牙地址
     */
    private String bluetoothMacAddress;

    /**
     * 用户Id
     */
    private Long userId;

    /**
     * 颜色id
     */
    private Long colorId;

    /**
     * bbi
     */
    private String bbi;

    /**
     * 控制器
     */
    private String controller;

    /**
     * 电机
     */
    private String motor;

    /**
     * IMEI
     */
    private String imei;

}
