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
 *@date 2021/1/27 21:34
 */
@ApiModel(value="com-redescooter-ses-mobile-rps-dm-OpeProductionPartsRelation")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OpeProductionPartsRelation {
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
    * 关联的对象id（整车的草稿、bom，组装的草稿、bom）
    */
    @ApiModelProperty(value="关联的对象id（整车的草稿、bom，组装的草稿、bom）")
    private Long productionId;

    /**
    * 关联的对象类型，1：整车草稿，2：整车bom，3：组装草稿，4：组装bom
    */
    @ApiModelProperty(value="关联的对象类型，1：整车草稿，2：整车bom，3：组装草稿，4：组装bom")
    private Integer productionType;

    /**
    * 部件id
    */
    @ApiModelProperty(value="部件id")
    private Long partsId;

    /**
    * 部件编号
    */
    @ApiModelProperty(value="部件编号")
    private String partsNo;

    /**
    * 部件区域编码id
    */
    @ApiModelProperty(value="部件区域编码id")
    private Long partsSec;

    /**
    * 采购周期
    */
    @ApiModelProperty(value="采购周期")
    private Integer procurementCycle;

    /**
    * 部品数量
    */
    @ApiModelProperty(value="部品数量")
    private Integer partsQty;

    /**
    * 中文名称
    */
    @ApiModelProperty(value="中文名称")
    private String cnName;

    /**
    * 英文名称
    */
    @ApiModelProperty(value="英文名称")
    private String enName;

    /**
    * 法文名称
    */
    @ApiModelProperty(value="法文名称")
    private String frName;

    /**
    * 创建人
    */
    @ApiModelProperty(value="创建人")
    private Integer createdBy;

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