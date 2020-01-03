package com.redescooter.ses.web.delivery.vo;

import com.redescooter.ses.api.common.annotation.MaximumLength;
import com.redescooter.ses.api.common.annotation.MinimumLength;
import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.delivery.exception.ValidationExceptionCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 31/12/2019 1:53 下午
 * @ClassName: SaveDriverEnter
 * @Function: TODO
 */
@ApiModel(value = "创建司机", description = "创建司机")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class SaveDriverEnter extends GeneralEnter {

    @ApiModelProperty(value = "司机主键")
    private Long id;

    @ApiModelProperty(value = "头像")
    @NotNull(code = ValidationExceptionCode.PICTURE_IS_EMPTY, message = "头像为空")
    private String avatar;

    @ApiModelProperty(value = "司机姓氏")
    @NotNull(code = ValidationExceptionCode.FIRST_NAME_IS_EMPTY, message = "名为空")
    @MinimumLength(code = ValidationExceptionCode.FIRST_NAME_IS_UNAVAILABLE, message = "名字字符长度为2-20字符")
    @MaximumLength(code = ValidationExceptionCode.FIRST_NAME_IS_UNAVAILABLE, message = "名字符长度为2-20字符")
    private String driverFirstName;

    @ApiModelProperty(value = "司机名字")
    @NotNull(code = ValidationExceptionCode.LAST_NAME_IS_EMPTY, message = "姓为空")
    @MinimumLength(code = ValidationExceptionCode.LAST_NAME_IS_UNAVAILABLE, message = "姓字符长度为2-20字符")
    @MaximumLength(code = ValidationExceptionCode.LAST_NAME_IS_UNAVAILABLE, message = "姓字符长度为2-20字符")
    private String driverLastName;

    @ApiModelProperty(value = "司机性别")
    @NotNull(code = ValidationExceptionCode.GENDER_IS_EMPTY, message = "性别为空")
    private String gender;

    @ApiModelProperty(value = "手机区号")
    @NotNull(code = ValidationExceptionCode.COUNTRY_CODE_IS_EMPTY, message = "手机区号为空")
    private String countryCodel;

    @ApiModelProperty(value = "司机手机号")
    @NotNull(code = ValidationExceptionCode.PHONE_IS_EMPTY, message = "手机号为空")
    @MinimumLength(code = ValidationExceptionCode.PHONE_IS_UNAVAILABLE, message = "手机号字符长度为2-20字符")
    @MaximumLength(code = ValidationExceptionCode.PHONE_IS_UNAVAILABLE, message = "手机号字符长度为2-20字符")
    private String driverPhone;

    @ApiModelProperty(value = "司机邮箱")
    @NotNull(code = ValidationExceptionCode.EMAIL_IS_EMPTY, message = "邮箱为空")
    @MinimumLength(code = ValidationExceptionCode.EMAIL_IS_UNAVAILABLE, message = "邮箱字符长度为2-20字符")
    @MaximumLength(code = ValidationExceptionCode.EMAIL_IS_UNAVAILABLE, message = "邮箱字符长度为2-20字符")
    private String email;

    @ApiModelProperty(value = "司机地址")
    @NotNull(code = ValidationExceptionCode.ADDRESS_IS_EMPTY, message = "地址为空")
    @MinimumLength(code = ValidationExceptionCode.ADDRESS_IS_UNAVAILABLE, message = "地址字符长度为2-20字符")
    @MaximumLength(code = ValidationExceptionCode.ADDRESS_IS_UNAVAILABLE, message = "地址字符长度为2-20字符")
    private String address;

    @ApiModelProperty(value = "司机生日")
    @NotNull(code = ValidationExceptionCode.BIRTHDAY_IS_EMPTY, message = "生日为空")
    private String birthday;

    @ApiModelProperty(value = "证件类型1身份证，2驾驶证，3护照")
    private String certificateType;

    @ApiModelProperty(value = "驾驶证")
    @NotNull(code = ValidationExceptionCode.DRIVER_LICENSE_IS_EMPTY, message = "驾驶证为空")
    private String driverLicense;

    @ApiModelProperty(value = "驾驶证附件上")
    @NotNull(code = ValidationExceptionCode.DRIVER_LICENSE_UPANNEX_IS_EMPTY, message = "驾驶证附件为空")
    private String driverLicenseUpAnnex;

    @ApiModelProperty(value = "驾驶证附件下")
    @NotNull(code = ValidationExceptionCode.DRIVER_LICENSE_DOWNANNEX_IS_EMPTY, message = "生日为空")
    private String driverLicenseDownAnnex;

}
