package com.redescooter.ses.web.delivery.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 2/1/2020 11:54 上午
 * @ClassName: DriverDetailsResult
 * @Function: TODO
 */
@ApiModel(value = "司机详情结果集", description = "司机详情结果集")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class DriverDetailsResult extends GeneralResult {

    @ApiModelProperty(value = "司机主键")
    private Long id;

    @ApiModelProperty(value = "司机账户登录类型")
    private String driverLoginType;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "司机姓氏")
    private String driverFirstName;

    @ApiModelProperty(value = "司机名字")
    private String driverLastName;

    @ApiModelProperty(value = "司机性别")
    private String gender;

    @ApiModelProperty(value = "手机区号")
    private String countryCodel;

    @ApiModelProperty(value = "司机手机号")
    private String driverPhone;

    @ApiModelProperty(value = "司机邮箱")
    private String email;

    @ApiModelProperty(value = "昵称账号")
    private String nickName;

    @ApiModelProperty(value = "司机地址")
    private String address;

    @ApiModelProperty(value = "司机生日")
    private String birthday;

    @ApiModelProperty(value = "车牌号")
    private String plateNumber;

    @ApiModelProperty(value = "证件类型1身份证，2驾驶证，3护照")
    private String certificateType;

    @ApiModelProperty(value = "驾驶证")
    private String driverLicense;

    @ApiModelProperty(value = "驾驶证附件上")
    private String driverLicenseUpAnnex;

    @ApiModelProperty(value = "驾驶证附件下")
    private String driverLicenseDownAnnex;

    @ApiModelProperty(value = "驾驶证附件下")
    private int age;

    @ApiModelProperty(value = "用户类型")
    private Integer userType;

    @ApiModelProperty(value = "加入时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Date joinDate;

    @ApiModelProperty(value = "驾照等级")
    private String driverLicenseLevel;

}
