package com.redescooter.ses.service.hub.dm.corporate.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@ApiModel(value="com-redescooter-ses-service-hub-dm-corporate-base-CorUserProfile")
@Data
public class CorUserProfile implements Serializable {
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
    * 用户ID
    */
    @ApiModelProperty(value="用户ID")
    private Long userId;

    /**
    * 是否主账号:1-主账号,0-子账号
    */
    @ApiModelProperty(value="是否主账号:1-主账号,0-子账号")
    private Integer isMaster;

    /**
    * 照片
    */
    @ApiModelProperty(value="照片")
    private String picture;

    /**
    * 名
    */
    @ApiModelProperty(value="名")
    private String firstName;

    /**
    * 姓
    */
    @ApiModelProperty(value="姓")
    private String lastName;

    /**
    * 全名
    */
    @ApiModelProperty(value="全名")
    private String fullName;

    /**
    * 邮箱
    */
    @ApiModelProperty(value="邮箱")
    private String email1;

    /**
    * 邮箱
    */
    @ApiModelProperty(value="邮箱")
    private String email2;

    /**
    * 邮箱
    */
    @ApiModelProperty(value="邮箱")
    private String email3;

    /**
    * 电话号
    */
    @ApiModelProperty(value="电话号")
    private String telNumber1;

    /**
    * 电话号
    */
    @ApiModelProperty(value="电话号")
    private String telNumber2;

    /**
    * 电话号
    */
    @ApiModelProperty(value="电话号")
    private String telNumber3;

    /**
    * 性别
    */
    @ApiModelProperty(value="性别")
    private String gender;

    /**
    * 生日
    */
    @ApiModelProperty(value="生日")
    private Date birthday;

    /**
    * 驾照
    */
    @ApiModelProperty(value="驾照")
    private String drivingLicense;

    /**
    * 驾照图片
    */
    @ApiModelProperty(value="驾照图片")
    private String drivingLicensePicture;

    /**
    * 身份证号
    */
    @ApiModelProperty(value="身份证号")
    private String identity;

    /**
    * 角色 DRIVER-司机；MANAGE-管理者
    */
    @ApiModelProperty(value="角色 DRIVER-司机；MANAGE-管理者")
    private String role;

    /**
    * 页面引导提示开关，默认true 打开
    */
    @ApiModelProperty(value="页面引导提示开关，默认true 打开")
    private Boolean pageBootTips;

    /**
    * 出生地
    */
    @ApiModelProperty(value="出生地")
    private String placeBirth;

    /**
    * 加入日期
    */
    @ApiModelProperty(value="加入日期")
    private Date joinDate;

    /**
    * 时区
    */
    @ApiModelProperty(value="时区")
    private String timeZone;

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