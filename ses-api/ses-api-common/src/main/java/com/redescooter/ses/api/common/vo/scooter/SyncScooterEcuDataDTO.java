package com.redescooter.ses.api.common.vo.scooter;

import lombok.Data;

import java.io.Serializable;

/**
 * 同步车辆仪表ECU数据 DTO
 * @author assert
 * @date 2020/12/10 20:35
 */
@Data
public class SyncScooterEcuDataDTO implements Serializable {

    /**
     * 车辆编号
     */
    private String scooterNo;

    /**
     * 蓝牙地址
     */
    private String bluetoothMacAddress;

    /**
     * 蓝牙名称
     */
    private String bluetoothName;

    /**
     * 用户Id
     */
    private Long userId;

}
