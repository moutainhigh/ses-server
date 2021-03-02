package com.redescooter.ses.api.common.vo.scooter;

import lombok.Data;

import java.io.Serializable;

/**
 * 同步车辆数据 DTO
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
     * 车辆编号(暂时用车辆Id)
     */
    private String scooterNo;

    /**
     * 车载平板序列号
     */
    private String tabletSn;

    /**
     * 车辆型号 1-E25 2-E50 3-E100 4-E125
     */
    private String model;

    /**
     * 用户Id
     */
    private Long userId;

}
