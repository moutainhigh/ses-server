package com.redescooter.ses.api.scooter.vo.emqx;

import lombok.Data;

/**
 * 车辆上报锁状态 DTO
 * @author assert
 * @date 2020/11/23 14:05
 */
@Data
public class ScooterLockReportedDTO {

    /**
     * 车辆平板序列号
     */
    private String tabletSn;

    /**
     * 车辆锁状态 1-lock 2-unlock
     */
    private Integer status;

}
