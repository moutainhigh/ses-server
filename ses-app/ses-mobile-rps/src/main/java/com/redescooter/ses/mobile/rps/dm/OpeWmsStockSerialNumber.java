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
 * @date 2021/1/25 10:55
 */
@ApiModel(value = "com-redescooter-ses-mobile-rps-dm-OpeWmsStockSerialNumber")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OpeWmsStockSerialNumber {
    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    private Long id;

    /**
     * 逻辑删除
     */
    @ApiModelProperty(value = "逻辑删除")
    private Integer dr;

    /**
     * 租户ID
     */
    @ApiModelProperty(value = "租户ID")
    private Long tenantId;

    /**
     * 部门id（做数据权限用）
     */
    @ApiModelProperty(value = "部门id（做数据权限用）")
    private Long deptId;

    /**
     * 产品类型 1车辆 2组装件 3部件
     */
    @ApiModelProperty(value = "产品类型 1车辆 2组装件 3部件")
    private Integer relationType;

    /**
     * 关联id
     */
    @ApiModelProperty(value = "关联id")
    private Long relationId;

    /**
     * 仓库类型，1:中国仓库，2:法国仓库
     */
    @ApiModelProperty(value = "仓库类型，1:中国仓库，2:法国仓库")
    private Integer stockType;

    /**
     * 序列号
     */
    @ApiModelProperty(value = "序列号")
    private String rsn;

    /**
     * 库存状态 1可继续使用 2已被别人使用
     */
    @ApiModelProperty(value = "库存状态 1可继续使用 2已被别人使用")
    private Integer stockStatus;

    /**
     * 批次号
     */
    @ApiModelProperty(value = "批次号")
    private String lotNum;

    /**
     * 供应商序列号
     */
    @ApiModelProperty(value = "供应商序列号")
    private String sn;

    /**
     * 蓝牙mac地址
     */
    @ApiModelProperty(value = "蓝牙mac地址")
    private String bluetoothMacAddress;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

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

    public static OpeWmsStockSerialNumberBuilder builder() {
        return new OpeWmsStockSerialNumberBuilder();
    }
}