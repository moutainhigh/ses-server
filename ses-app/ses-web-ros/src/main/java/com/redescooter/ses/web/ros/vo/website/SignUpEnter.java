package com.redescooter.ses.web.ros.vo.website;

import com.redescooter.ses.api.common.annotation.MaximumLength;
import com.redescooter.ses.api.common.annotation.MinimumLength;
import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.annotation.Regexp;
import com.redescooter.ses.api.common.constant.RegexpConstant;
import com.redescooter.ses.api.common.exception.ValidationExceptionBaseCode;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.ros.exception.ValidationExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

/**
 * @ClassName:SignUpEnter
 * @description: SignUpEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/05/12 12:08
 */
@ApiModel(value = "Sign Up Enter", description = "Sign Up Enter")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class SignUpEnter extends GeneralEnter {

    @ApiModelProperty(value = "First Name")
    @NotNull(code = ValidationExceptionCode.FIRST_NAME_IS_EMPTY,message = "姓名为空")
    @MaximumLength(value ="60", code = ValidationExceptionCode.NAME_IS_ILLEGAL,message = "名字不合法")
    @MinimumLength(value = "2",code = ValidationExceptionCode.NAME_IS_ILLEGAL,message = "名字不合法")
    @Regexp(value = RegexpConstant.name,code = ValidationExceptionCode.NAME_IS_ILLEGAL,message = "名字不合法")
    private String firstName;

    @ApiModelProperty(value = "Last Name")
    @NotNull(code = ValidationExceptionCode.LAST_NAME_IS_EMPTY,message = "姓名为空")
    @MaximumLength(value ="60", code = ValidationExceptionCode.NAME_IS_ILLEGAL,message = "名字不合法")
    @MinimumLength(value = "2",code = ValidationExceptionCode.NAME_IS_ILLEGAL,message = "名字不合法")
    @Regexp(value = RegexpConstant.name,code = ValidationExceptionCode.NAME_IS_ILLEGAL,message = "名字不合法")
    private String lastName;

    @ApiModelProperty(value = "email")
    @NotNull(code = ValidationExceptionBaseCode.EMAIL_IS_EMPTY,message = "邮箱为空")
    private String email;

    @ApiModelProperty(value = "password")
    @NotNull(code = ValidationExceptionBaseCode.PASSWORD_IS_EMPTY,message = "密码为空")
    private String password;

    @ApiModelProperty(value = "address")
    @NotNull(code = ValidationExceptionCode.ADDRESS_INFO_IS_EMPTY, message = "地址为空")
    @MinimumLength(value = "2",code = ValidationExceptionCode.ADDRESS_CHAR_IS_NOT_ILLEGAL, message = "地址字符长度不合法，2-200字符")
    @MaximumLength(value = "200",code = ValidationExceptionCode.ADDRESS_CHAR_IS_NOT_ILLEGAL, message = "地址字符长度不合法，2-200字符")
    private String address;

    @ApiModelProperty(value = "latitude")
    @Regexp(value = RegexpConstant.lat,code = ValidationExceptionCode.LAT_IS_ILLEGAL,message = "维度不合法")
    private String lat;

    @ApiModelProperty(value = "Longitude")
    @Regexp(value = RegexpConstant.lat,code = ValidationExceptionCode.LNG_ILLEGAL,message = "经度不合法")
    private String lng;

    @ApiModelProperty(value = "address Id")
    private String placeId;

    @ApiModelProperty(value = "Postal Code")
    @NotNull(code = ValidationExceptionCode.COUNTRY_CITY_MSG_IS_EMPTY, message = "国家城市信息为空")
    @MaximumLength(value ="60", code = ValidationExceptionCode.COUNTRY_CITY_ILLEGAL,message = "国家城市非法")
    @MinimumLength(value = "2",code = ValidationExceptionCode.COUNTRY_CITY_ILLEGAL,message = "国家城市非法")
    @Regexp(code = ValidationExceptionCode.COUNTRY_CITY_ILLEGAL,message = "国家城市非法")
    private String district;

    @ApiModelProperty(value = "country")
    @NotNull(code = ValidationExceptionCode.COUNTRY_CITY_MSG_IS_EMPTY, message = "国家城市信息为空")
    @MaximumLength(value ="60", code = ValidationExceptionCode.COUNTRY_CITY_ILLEGAL,message = "国家城市非法")
    @MinimumLength(value = "2",code = ValidationExceptionCode.COUNTRY_CITY_ILLEGAL,message = "国家城市非法")
    @Regexp(code = ValidationExceptionCode.COUNTRY_CITY_ILLEGAL,message = "国家城市非法")
    private String customerCountry;

    @ApiModelProperty(value = "country Id")
    @NotNull(code = ValidationExceptionCode.COUNTRY_CITY_MSG_IS_EMPTY, message = "国家城市信息为空")
    private Long countryId;

    @ApiModelProperty("city")
    @NotNull(code = ValidationExceptionCode.COUNTRY_CITY_MSG_IS_EMPTY, message = "国家城市信息为空")
    @MaximumLength(value ="60", code = ValidationExceptionCode.COUNTRY_CITY_ILLEGAL,message = "国家城市非法")
    @MinimumLength(value = "2",code = ValidationExceptionCode.COUNTRY_CITY_ILLEGAL,message = "国家城市非法")
    @Regexp(code = ValidationExceptionCode.COUNTRY_CITY_ILLEGAL,message = "国家城市非法")
    private String city;
}
