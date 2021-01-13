package com.redescooter.ses.mobile.rps.dm;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * @author assert
 * @date 2021/1/13 16:10
 */
@ApiModel(value = "com-redescooter-ses-mobile-rps-dm-OpeInWhouseScooterB")
@Data
public class OpeInWhouseScooterB {
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
     * 关联的入库单id
     */
    @ApiModelProperty(value = "关联的入库单id")
    private Long inWhId;

    /**
     * 车型（规格分组）的id
     */
    @ApiModelProperty(value = "车型（规格分组）的id")
    private Long groupId;

    /**
     * 颜色id
     */
    @ApiModelProperty(value = "颜色id")
    private Long colorId;

    /**
     * bom车辆id
     */
    @ApiModelProperty(value = "bom车辆id")
    private Long scooterBomId;

    /**
     * 组装数量
     */
    @ApiModelProperty(value = "组装数量")
    private Integer combinQty;

    /**
     * 可入库数量
     */
    @ApiModelProperty(value = "可入库数量")
    private Integer ableInWhQty;

    /**
     * 入库数量
     */
    @ApiModelProperty(value = "入库数量")
    private Integer inWhQty;

    /**
     * 实际入库数量
     */
    @ApiModelProperty(value = "实际入库数量")
    private Integer actInWhQty;

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
}