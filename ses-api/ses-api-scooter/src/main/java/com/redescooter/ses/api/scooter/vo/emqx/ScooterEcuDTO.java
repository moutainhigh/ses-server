package com.redescooter.ses.api.scooter.vo.emqx;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author assert
 * @date 2020/11/20 14:32
 */
@Data
public class ScooterEcuDTO {
    /**
    * 主键id
    */
    private Long id;

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
    * 车辆是否锁住 true是 false否
    */
    private Boolean scooterLock;

    /**
    * 平板序列号
    */
    private String tabletSn;

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
     * 创建时间
     */
    private Date createdTime;

    /**
     * 修改时间
     */
    private Date updatedTime;

}