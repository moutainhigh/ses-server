package com.redescooter.ses.api.scooter.vo.emqx;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 车辆上报BMS电池信息 DTO
 * @author assert
 * @date 2020/11/20 15:44
 */
@Data
public class ScooterBmsReportedDTO {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 车辆编号
     */
    private String scooterNo;

    /**
     * 批次号
     */
    private Integer batchNo;

    /**
     * 电芯类型 0：三元电池 1：磷酸铁锂
     */
    private Integer batteryType;

    /**
     * 是否故障 true是 false否
     */
    private Boolean breakdown;

    /**
     * 是否充电 true是 false否
     */
    private Boolean charge;

    /**
     * 充电次数
     */
    private Integer chargeAmount;

    /**
     * 充电mos是否打开 true是 false否
     */
    private Boolean chargeMosOpen;

    /**
     * 是否充电过流保护 true是 false否
     */
    private Boolean chargeOverIProtect;

    /**
     * 充电过流保护次数
     */
    private Integer chargeOverIProtectAmount;

    /**
     * 生产日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Date date;

    /**
     * 硬件版本号
     */
    private Integer deviceVersionNo;

    /**
     * 是否放电 true是 false否
     */
    private Boolean discharge;

    /**
     * 放电次数
     */
    private Integer dischargeAmount;

    /**
     * 放电mos是否打开 true是 false否
     */
    private Boolean dischargeMosOpen;

    /**
     * 是否放电过流保护 true是 false否
     */
    private Boolean dischargeOverIProtect;

    /**
     * 放电过流保护次数
     */
    private Integer dischargeOverIProtectAmount;

    /**
     * 电流，精度0.01A
     */
    private Double electricity;

    /**
     * 厂商
     */
    private String factory;

    /**
     * 出厂号/流水号
     */
    private Long factoryNo;

    /**
     * 范围0-100
     */
    private Double health;

    /**
     * 是否高温保护 true是 false否
     */
    private Boolean highTProtect;

    /**
     * 高温保护次数
     */
    private Integer highTProtectAmount;

    /**
     * 是否欠压保护 true是 false否
     */
    private Boolean lackVProtect;

    /**
     * 欠压保护次数
     */
    private Integer lackVProtectAmount;

    /**
     * 是否低温保护 true是 false否
     */
    private Boolean lowTProtect;

    /**
     * 低温保护次数
     */
    private Integer lowTProtectAmount;

    /**
     * 充电mos是否故障 true是 false否
     */
    private Boolean mosChargeBreakdown;

    /**
     * 放电mos是否故障 true是 false否
     */
    private Boolean mosDischargeBreakdown;

    /**
     * 过冲次数
     */
    private Integer overChargeAmount;

    /**
     * 过放次数
     */
    private Integer overDischargeAmount;

    /**
     * 是否过压保护 true是 false否
     */
    private Boolean overVProtect;

    /**
     * 过压保护次数
     */
    private Integer overVProtectAmount;

    /**
     * 厂家 0：优贝特 1：飞毛腿
     */
    private Integer packFactory;

    /**
     * 是否短路保护 true是 false否
     */
    private Boolean shortProtect;

    /**
     * 短路保护次数
     */
    private Integer shortProtectAmount;

    /**
     * 电量，范围0-100
     */
    private Double soc;

    /**
     * 软件版本
     */
    private Integer softWareVersion;

    /**
     * 温度
     */
    private Double temperature;

    /**
     * 累计充电电量总和
     */
    private Long totalCharge;

    /**
     * 累计放电电量总和
     */
    private Long totalDischarge;

    /**
     * 12V是否接入 true是 false否
     */
    private Boolean v12Connected;

    /**
     * 5V是否接入 true是 false否
     */
    private Boolean v5Connected;

    /**
     * 电压，精度0.01V
     */
    private Double voltage;

    /**
     * 是否电压平衡 true是 false否
     */
    private Boolean voltageBalance;

    /**
     * 电池仓位 0：没有接入 1：1号电池仓，其他类推
     */
    private Integer wareNo;

    /**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 更新时间
     */
    private Date updatedTime;

}
