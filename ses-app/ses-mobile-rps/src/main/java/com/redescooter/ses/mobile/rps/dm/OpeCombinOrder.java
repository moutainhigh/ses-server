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
 * @date 2021/1/22 10:30
 */
@ApiModel(value = "com-redescooter-ses-mobile-rps-dm-OpeCombinOrder")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OpeCombinOrder {
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
    private Integer tenantId;

    /**
     * 部门id（做数据权限用）
     */
    @ApiModelProperty(value = "部门id（做数据权限用）")
    private Long deptId;

    /**
     * 组装单类型，1：车辆组装单，2组装件组装单
     */
    @ApiModelProperty(value = "组装单类型，1：车辆组装单，2组装件组装单")
    private Integer combinType;

    /**
     * 组装单号
     */
    @ApiModelProperty(value = "组装单号")
    private String combinNo;

    /**
     * 组装单状态， 1：草稿，10：待备料，20:备料完成，30：待组装，40：组装中，50：待质检，60：质检中，70：待入库，80：部分入库，90：已入库
     */
    @ApiModelProperty(value = "组装单状态， 1：草稿，10：待备料，20:备料完成，30：待组装，40：组装中，50：待质检，60：质检中，70：待入库，80：部分入库，90：已入库")
    private Integer combinStatus;

    /**
     * 组装数量
     */
    @ApiModelProperty(value = "组装数量")
    private Integer combinQty;

    /**
     * 组装开始日期
     */
    @ApiModelProperty(value = "组装开始日期")
    private Date combinStartDate;

    /**
     * 组装结束日期
     */
    @ApiModelProperty(value = "组装结束日期")
    private Date combinEndDate;

    /**
     * 负责人id
     */
    @ApiModelProperty(value = "负责人id")
    private Long principalId;

    /**
     * 负责人名称
     */
    @ApiModelProperty(value = "负责人名称")
    private String principalName;

    /**
     * 国家编码，
     */
    @ApiModelProperty(value = "国家编码，")
    private String countryCode;

    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话")
    private String telephone;

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

    public static OpeCombinOrderBuilder builder() {
        return new OpeCombinOrderBuilder();
    }
}