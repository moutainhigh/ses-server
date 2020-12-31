package com.redescooter.ses.service.scooter.dm.base;

import lombok.Data;

import java.util.Date;

/**
 * @author assert
 * @date 2020/11/20 14:32
 */
@Data
public class ScoScooterMcuControllerInfo {
    /**
    * 主键id
    */
    private Long id;

    /**
    * 逻辑删除标识 0正常 1删除
    */
    private Integer dr;

    /**
    * mcu控制器设备id
    */
    private Long mcuId;

    /**
    * 控制器是否过热保护 true是 false否
    */
    private Boolean controlOverTProtect;

    /**
    * 电机是否过热保护 true是 false否
    */
    private Boolean motorOverTProtect;

    /**
    * 控制器是否堵转保护 true是 false否
    */
    private Boolean controlBlockProtect;

    /**
    * 控制器是否过载 true是 false否
    */
    private Boolean controlOverLoad;

    /**
    * 控制器是否过流保护 true是 false否
    */
    private Boolean controlOverIProtect;

    /**
    * 静态功耗是否正常 true是 false否
    */
    private Boolean staticPower;

    /**
    * mos管是否故障 true是 false否
    */
    private Boolean mosBreakdown;

    /**
    * 创建人
    */
    private Long createdBy;

    /**
    * 创建时间
    */
    private Date createdTime;

    /**
    * 更新人
    */
    private Long updatedBy;

    /**
    * 更新时间
    */
    private Date updatedTime;
}