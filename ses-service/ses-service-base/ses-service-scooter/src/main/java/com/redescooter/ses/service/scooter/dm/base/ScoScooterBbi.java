package com.redescooter.ses.service.scooter.dm.base;

import lombok.Data;

import java.util.Date;

/**
 * @author assert
 * @date 2020/11/20 14:32
 */
@Data
public class ScoScooterBbi {
    /**
    * 主键id
    */
    private Long id;

    /**
    * 逻辑删除标识 0正常 1删除
    */
    private Integer dr;

    /**
    * 车辆编号
    */
    private String scooterNo;

    /**
    * 批次号
    */
    private Long batchNo;

    /**
    * 是否故障 true是 false否
    */
    private Boolean breakdown;

    /**
    * 当前电流档位 1档35A 2档55A 3档90A 4档120A
    */
    private Integer currentShiftI;

    /**
    * 生产日期
    */
    private Date date;

    /**
    * 是否使能 true是 false否
    */
    private Boolean enable;

    /**
    * 出厂号/流水号
    */
    private Integer factoryNo;

    /**
    * 总开开关是否打开 true是 false否
    */
    private Boolean mainSwitchOpen;

    /**
    * 设置电流档位 1档35A 2档55A 3档90A 4档120A
    */
    private Integer shiftI;

    /**
    * 创建人
    */
    private Integer createdBy;

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