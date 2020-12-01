package com.redescooter.ses.web.ros.vo.website;

import com.redescooter.ses.api.common.annotation.MaximumLength;
import com.redescooter.ses.api.common.annotation.MinimumLength;
import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.annotation.Regexp;
import com.redescooter.ses.api.common.constant.RegexpConstant;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.ros.exception.ValidationExceptionCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @ClassNameEditCustomerEnter
 * @Description
 * @Author Aleks
 * @Date2020/5/26 15:28
 * @Version V1.0
 **/
@ApiModel(value = "Edit Customer Enter", description = "Edit Customer Enter")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class WebEditCustomerEnter extends GeneralEnter {

    @ApiModelProperty(value = "First Name")
    @NotNull(code = ValidationExceptionCode.FIRST_NAME_IS_EMPTY,message = "名字为空")
    @MaximumLength(value ="60", code = ValidationExceptionCode.NAME_IS_ILLEGAL,message = "名字不合法")
    @MinimumLength(value = "2",code = ValidationExceptionCode.NAME_IS_ILLEGAL,message = "名字不合法")
    @Regexp(value = RegexpConstant.name,code = ValidationExceptionCode.NAME_IS_ILLEGAL,message = "名字非法")
    private String firstName;

    @ApiModelProperty(value = "Last Name")
    @NotNull(code = ValidationExceptionCode.LAST_NAME_IS_EMPTY,message = "名字为空")
    @MaximumLength(value ="60", code = ValidationExceptionCode.NAME_IS_ILLEGAL,message = "名字不合法")
    @MinimumLength(value = "2",code = ValidationExceptionCode.NAME_IS_ILLEGAL,message = "名字不合法")
    @Regexp(value = RegexpConstant.name,code = ValidationExceptionCode.NAME_IS_ILLEGAL,message = "名字非法")
    private String lastName;

    @ApiModelProperty(value = "address")
    @NotNull(code = ValidationExceptionCode.ADDRESS_INFO_IS_EMPTY, message = "地址为空")
    @MinimumLength(value = "2",code = ValidationExceptionCode.ADDRESS_CHAR_IS_NOT_ILLEGAL, message = "地址字符长度不合法，2-200字符")
    @MaximumLength(value = "200",code = ValidationExceptionCode.ADDRESS_CHAR_IS_NOT_ILLEGAL, message = "地址字符长度不合法，2-200字符")
    private String address;

    @ApiModelProperty(value = "latitude")
    private String lat;

    @ApiModelProperty(value = "Longitude")
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

    @ApiModelProperty(value = "city")
    @NotNull(code = ValidationExceptionCode.COUNTRY_CITY_MSG_IS_EMPTY, message = "国家城市信息为空")
    @MaximumLength(value ="60", code = ValidationExceptionCode.COUNTRY_CITY_ILLEGAL,message = "国家城市非法")
    @MinimumLength(value = "2",code = ValidationExceptionCode.COUNTRY_CITY_ILLEGAL,message = "国家城市非法")
    @Regexp(code = ValidationExceptionCode.COUNTRY_CITY_ILLEGAL,message = "国家城市非法")
    private String city;

}
