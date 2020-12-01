package com.redescooter.ses.api.scooter.vo.emqx;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 车辆上报BBI电池管理系统数据 DTO
 * @author assert
 * @date 2020/11/20 15:21
 */
@Data
public class ScooterBbiReportedDTO {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 车辆编号
     */
    private String scooterNo;

    /**
     * 车辆平板序列号
     */
    private String tabletSn;

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
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
     * 创建时间
     */
    private Date createdTime;

    /**
     * 更新时间
     */
    private Date updatedTime;

    /**
     * 电池仓位信息(电池卡槽),根据集合顺序表示电池仓位1,2,3....
     */
    private List<BatteryWareDTO> batteryWares;

}
