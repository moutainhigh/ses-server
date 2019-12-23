package com.redescooter.ses.service.hub.dm.customer.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

@ApiModel(value="com-redescooter-ses-service-hub-dm-customer-base-ConUserProfile")
@Data
public class ConUserProfile implements Serializable {
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
    * 姓
    */
    @ApiModelProperty(value="姓")
    private String firstName;

    /**
    * 名
    */
    @ApiModelProperty(value="名")
    private String lastName;

    /**
    * 姓名
    */
    @ApiModelProperty(value="姓名")
    private String fullName;

    /**
    * 照片
    */
    @ApiModelProperty(value="照片")
    private String picture;

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
    * 出生地
    */
    @ApiModelProperty(value="出生地")
    private String placeBirth;

    /**
    * 居住地址
    */
    @ApiModelProperty(value="居住地址")
    private String address;

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