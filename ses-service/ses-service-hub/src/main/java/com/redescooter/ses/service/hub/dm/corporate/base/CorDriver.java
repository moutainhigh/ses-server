package com.redescooter.ses.service.hub.dm.corporate.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

@ApiModel(value="com-redescooter-ses-service-hub-dm-corporate-base-CorDriver")
@Data
public class CorDriver implements Serializable {
    /**
    * ID
    */
    @ApiModelProperty(value="ID")
    private Long id;

    /**
    * 逻辑删除标识 0正常 1删除
    */
    @ApiModelProperty(value="逻辑删除标识 0正常 1删除")
    private Integer dr;

    /**
    * 租户ID
    */
    @ApiModelProperty(value="租户ID")
    private Long tenantId;

    /**
    * USER_ID
    */
    @ApiModelProperty(value="USER_ID")
    private Long userId;

    /**
    * 驾照
    */
    @ApiModelProperty(value="驾照")
    private String drivingLicense;

    /**
    * 评分
    */
    @ApiModelProperty(value="评分")
    private Integer score;

    /**
    * 状态 VACATION 休假，WORKING 上班中OFFWORK 下班中
    */
    @ApiModelProperty(value="状态 VACATION 休假，WORKING 上班中OFFWORK 下班中")
    private String status;

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
    private String def5;

    /**
    * 冗余字段
    */
    @ApiModelProperty(value="冗余字段")
    private Double def6;

    private static final long serialVersionUID = 1L;
}