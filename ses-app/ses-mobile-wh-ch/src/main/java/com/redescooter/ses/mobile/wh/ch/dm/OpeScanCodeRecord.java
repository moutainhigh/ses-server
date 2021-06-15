package com.redescooter.ses.mobile.wh.ch.dm;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: // 部件扫码记录表(OpeScanCodeRecord)表实体类
 * @Date: 2021-06-04 15:56:54
 * @Author: Charles
 */
@Data
@TableName("ope_scan_code_record")
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "部件扫码记录表")
public class OpeScanCodeRecord extends Model<OpeScanCodeRecord> implements Serializable {

    private static final long serialVersionUID = 283334372905252370L;

    /**
     * 主键
     */
    @TableId(value = "id")
    @ApiModelProperty("主键")
    private Long id;

    /**
     * 逻辑删除
     */
    @TableLogic
    @ApiModelProperty("逻辑删除")
    private Integer dr;
    /**
     * 状态 1 未完成，2 已完成
     */
    @ApiModelProperty("状态 1 未完成，2 已完成")
    private Integer status;
    /**
     * 车辆名称
     */
    @ApiModelProperty("车辆名称")
    private String scooterName;
    /**
     * 颜色
     */
    @ApiModelProperty("颜色")
    private String colorCode;
    /**
     * rsn 码
     */
    @ApiModelProperty("rsn 码")
    private String rsn;
    /**
     * bbi sn码
     */
    @ApiModelProperty("bbi sn码")
    private String bbiSn;
    /**
     * 控制器 sn 码
     */
    @ApiModelProperty("控制器 sn 码")
    private String controllerSn;
    /**
     * 电机码
     */
    @ApiModelProperty("电机码")
    private String motorSn;
    /**
     * 仪表码
     */
    @ApiModelProperty("仪表码")
    private String meterSn;
    /**
     * 仪表iemi
     */
    @ApiModelProperty("仪表iemi")
    private String meterImei;
    /**
     * 仪表 bt
     */
    @ApiModelProperty("仪表 bt")
    private String meterBt;
    /**
     * 仪表 wifi
     */
    @ApiModelProperty("仪表 wifi")
    private String meterWifi;
    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remarks;
    /**
     * 仓库用户
     */
    @ApiModelProperty("仓库用户")
    private Long warehouseId;

    /**
     * 创建人邮箱
     */
    @ApiModelProperty("创建人邮箱")
    private String warehouseEmail;
    /**
     * 创建人
     */
    @ApiModelProperty("创建人")
    private Long createdBy;
    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Date createdTime;
    /**
     * 更新人
     */
    @ApiModelProperty("更新人")
    private Long updatedBy;
    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    private Date updatedTime;
    /**
     * 冗余字段
     */
    @ApiModelProperty("冗余字段")
    private String def1;
    /**
     * 冗余字段
     */
    @ApiModelProperty("冗余字段")
    private String def2;
    /**
     * 冗余字段
     */
    @ApiModelProperty("冗余字段")
    private String def3;
    /**
     * 冗余字段
     */
    @ApiModelProperty("冗余字段")
    private String def5;
    /**
     * 冗余字段
     */
    @ApiModelProperty("冗余字段")
    private Double def6;
}
