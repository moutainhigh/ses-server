package com.redescooter.ses.mobile.rps.dm;

import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.Data;

/**
 *@author assert
 *@date 2021/1/14 15:44
 */
@ApiModel(value="com-redescooter-ses-mobile-rps-dm-OpeWmsQualifiedScooterStock")
@Data
public class OpeWmsQualifiedScooterStock {
    /**
    * 主键
    */
    @ApiModelProperty(value="主键")
    private Long id;

    /**
    * 逻辑删除
    */
    @TableLogic
    @ApiModelProperty(value="逻辑删除")
    private Integer dr;

    /**
    * 车型ID
    */
    @ApiModelProperty(value="车型ID")
    private Integer groupId;

    /**
    * 颜色ID
    */
    @ApiModelProperty(value="颜色ID")
    private Integer colorId;

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