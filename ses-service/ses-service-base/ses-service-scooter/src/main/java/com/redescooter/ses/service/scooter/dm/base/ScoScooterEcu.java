package com.redescooter.ses.service.scooter.dm.base;

import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

/**
 * @author assert
 * @date 2020/11/27 17:46
 */
@Data
public class ScoScooterEcu {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 逻辑删除标识 0正常 1删除
     */
    @TableLogic
    private Integer dr;

    /**
     * 车辆编号
     */
    private String scooterNo;

    /**
     * 电量,范围0-100
     */
    private Integer battery;

    /**
     * 蓝牙mac地址
     */
    private String bluetoothMacAddress;

    /**
     * 蓝牙名称
     */
    private String bluetoothName;

    /**
     * Sim卡唯一标识
     */
    private String iccid;

    /**
     * 维度
     */
    private BigDecimal latitude;

    /**
     * 经度
     */
    private BigDecimal longitude;

    /**
     * 移动数据信号强度
     */
    private Integer mobileSignal;

    /**
     * 车辆是否锁住 true是 false否(1-true 0-false)
     */
    private Boolean scooterLock;

    /**
     * 平板序列号
     */
    private String sn;

    /**
     * 速度,单位km/h
     */
    private Double speed;

    /**
     * 备用电池电压,精度范围0.01V
     */
    private Double standbyVoltage;

    /**
     * 扭力，范围0-100
     */
    private Integer torsion;

    /**
     * 总里程,单位km
     */
    private Integer totalMiles;

    /**
     * 版本应用编码
     */
    private String versionCode;

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
