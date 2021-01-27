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
 *@date 2021/1/27 17:36
 */
@ApiModel(value="com-redescooter-ses-mobile-rps-dm-OpeCombinListRelationParts")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OpeCombinListRelationParts {
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
    * 组装清单表id
    */
    @ApiModelProperty(value="组装清单表id")
    private Long relationId;

    /**
    * 关联组装清单表类型 1车辆 2组装件
    */
    @ApiModelProperty(value="关联组装清单表类型 1车辆 2组装件")
    private Integer relationType;

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
    * 部件类型，1：Parts，2：Accessory，3：Battery，4：Scooter，5：Combination 6：ECU
    */
    @ApiModelProperty(value="部件类型，1：Parts，2：Accessory，3：Battery，4：Scooter，5：Combination 6：ECU")
    private Integer partsType;

    /**
    * 是否有序列号，0：否，1：是
    */
    @ApiModelProperty(value="是否有序列号，0：否，1：是")
    private Integer idCalss;

    /**
    * 部品数量
    */
    @ApiModelProperty(value="部品数量")
    private Integer qty;

    /**
    * 已扫码数量
    */
    @ApiModelProperty(value="已扫码数量")
    private Integer scanCodeQty;

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