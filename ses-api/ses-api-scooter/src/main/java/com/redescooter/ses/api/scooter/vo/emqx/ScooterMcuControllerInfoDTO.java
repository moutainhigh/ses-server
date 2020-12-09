package com.redescooter.ses.api.scooter.vo.emqx;

import lombok.Data;

import java.util.Date;

/**
 * @author assert
 * @date 2020/11/20 14:32
 */
@Data
public class ScooterMcuControllerInfoDTO {
    /**
    * 主键id
    */
    private Long id;

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
     * 创建时间
     */
    private Date createdTime;

    /**
     * 修改时间
     */
    private Date updatedTime;

}