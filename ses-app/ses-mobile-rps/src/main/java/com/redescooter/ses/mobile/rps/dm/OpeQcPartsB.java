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
 *@author assert
 *@date 2021/1/26 15:38
 */
@ApiModel(value="com-redescooter-ses-mobile-rps-dm-OpeQcPartsB")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OpeQcPartsB {
    /**
    * 主键id
    */
    @ApiModelProperty(value="主键id")
    private Long id;

    /**
    * 逻辑删除
    */
    @ApiModelProperty(value="逻辑删除")
    private Integer dr;

    /**
    * 关联的质检单id
    */
    @ApiModelProperty(value="关联的质检单id")
    private Long qcId;

    /**
    * 部件id
    */
    @ApiModelProperty(value="部件id")
    private Integer partsId;

    /**
    * 部件名称(英文名称)
    */
    @ApiModelProperty(value="部件名称(英文名称)")
    private String partsName;

    /**
    * 部件编号
    */
    @ApiModelProperty(value="部件编号")
    private String partsNo;

    /**
    * 部件类型，1：Parts，2：Accessory，3：Battery，4：Scooter，5：Combination 6：ECU
    */
    @ApiModelProperty(value="部件类型，1：Parts，2：Accessory，3：Battery，4：Scooter，5：Combination 6：ECU")
    private Integer partsType;

    /**
    * 质检数量
    */
    @ApiModelProperty(value="质检数量")
    private Integer qty;

    /**
    * 不合格数量
    */
    @ApiModelProperty(value="不合格数量")
    private Integer unqualifiedQty;

    /**
    * 合格数量
    */
    @ApiModelProperty(value="合格数量")
    private Integer qualifiedQty;

    /**
    * 备注
    */
    @ApiModelProperty(value="备注")
    private String remark;

    /**
    * 创建人
    */
    @ApiModelProperty(value="创建人")
    private Long createdBy;

    /**
    * 创建时间
    */
    @ApiModelProperty(value="创建时间")
    private Date createdTime;

    /**
    * 更新人
    */
    @ApiModelProperty(value="更新人")
    private Long updatedBy;

    /**
    * 更新时间
    */
    @ApiModelProperty(value="更新时间")
    private Date updatedTime;

    /**
    * 冗余字段
    */
    @ApiModelProperty(value="冗余字段")
    private String def1;

    /**
    * 冗余字段
    */
    @ApiModelProperty(value="冗余字段")
    private String def2;

    /**
    * 冗余字段
    */
    @ApiModelProperty(value="冗余字段")
    private String def3;

    /**
    * 冗余字段
    */
    @ApiModelProperty(value="冗余字段")
    private String def4;

    /**
    * 冗余字段
    */
    @ApiModelProperty(value="冗余字段")
    private BigDecimal def5;
}