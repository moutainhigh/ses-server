package com.redescooter.ses.mobile.rps.dm;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.Data;

/**
 *@author assert
 *@date 2021/1/14 15:44
 */
@ApiModel(value="com-redescooter-ses-mobile-rps-dm-OpeWmsQualifiedPartsStock")
@Data
public class OpeWmsQualifiedPartsStock {
    /**
    * 主键
    */
    @ApiModelProperty(value="主键")
    private Long id;

    /**
    * 逻辑删除
    */
    @ApiModelProperty(value="逻辑删除")
    private Integer dr;

    /**
    * 部件ID
    */
    @ApiModelProperty(value="部件ID")
    private Long partsId;

    /**
    * 部件编号
    */
    @ApiModelProperty(value="部件编号")
    private String partsNo;

    /**
    * 部件类型，1：Parts，2：Accessory，3：Battery，4：Scooter，5：Combination
    */
    @ApiModelProperty(value="部件类型，1：Parts，2：Accessory，3：Battery，4：Scooter，5：Combination")
    private Integer partsType;

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
    * 库存数量
    */
    @ApiModelProperty(value="库存数量")
    private Integer qty;

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
    private Double def5;
}