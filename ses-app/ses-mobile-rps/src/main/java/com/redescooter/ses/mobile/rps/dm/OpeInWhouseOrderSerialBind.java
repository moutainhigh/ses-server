package com.redescooter.ses.mobile.rps.dm;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author assert
 * @date 2021/1/22 20:09
 */
@ApiModel(value = "com-redescooter-ses-mobile-rps-dm-OpeInWhouseOrderSerialBind")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OpeInWhouseOrderSerialBind {
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Long id;

    /**
     * 逻辑删除
     */
    @ApiModelProperty(value = "逻辑删除")
    private Integer dr;

    /**
     * 入库单产品表id
     */
    @ApiModelProperty(value = "入库单产品表id")
    private Long orderBId;

    /**
     * 入库单产品类型 1车辆 2组装件 3部件
     */
    @ApiModelProperty(value = "入库单产品类型 1车辆 2组装件 3部件")
    private Integer orderType;

    /**
     * 序列号
     */
    @ApiModelProperty(value = "序列号")
    private String serialNum;

    /**
     * 批次号
     */
    @ApiModelProperty(value = "批次号")
    private String lot;

    /**
     * 产品Id
     */
    @ApiModelProperty(value = "产品Id")
    private Long productId;

    /**
     * 产品类型：1、整车 2、组合 3、零部件
     */
    @ApiModelProperty(value = "产品类型：1、整车 2、组合 3、零部件")
    private Integer productType;

    /**
     * 数量
     */
    @ApiModelProperty(value = "数量")
    private Integer qty;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 蓝牙mac地址
     */
    @ApiModelProperty(value = "蓝牙mac地址")
    private String bluetoothMacAddress;

    /**
     * 部件本身序列号(厂商序列号)
     */
    @ApiModelProperty(value = "部件本身序列号(厂商序列号)")
    private String defaultSerialNum;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private Long createdBy;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createdTime;

    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人")
    private Long updatedBy;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private Date updatedTime;

    /**
     * 冗余字段
     */
    @ApiModelProperty(value = "冗余字段")
    private String def1;

    /**
     * 冗余字段
     */
    @ApiModelProperty(value = "冗余字段")
    private String def2;

    /**
     * 冗余字段
     */
    @ApiModelProperty(value = "冗余字段")
    private String def3;

    /**
     * 冗余字段
     */
    @ApiModelProperty(value = "冗余字段")
    private String def4;

    /**
     * 冗余字段
     */
    @ApiModelProperty(value = "冗余字段")
    private BigDecimal def5;

    public static OpeInWhouseOrderSerialBindBuilder builder() {
        return new OpeInWhouseOrderSerialBindBuilder();
    }
}