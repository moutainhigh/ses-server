package com.redescooter.ses.web.ros.vo.website;

import com.redescooter.ses.api.common.annotation.MaximumLength;
import com.redescooter.ses.api.common.annotation.MinimumLength;
import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.annotation.Regexp;
import com.redescooter.ses.api.common.constant.RegexpConstant;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.ros.exception.ValidationExceptionCode;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassNameEditCustomerEnter
 * @Description
 * @Author Aleks
 * @Date2020/5/26 15:28
 * @Version V1.0
 **/
@Data
public class WebEditCustomerEnter extends GeneralEnter {

    @ApiModelProperty(value = "姓名")
    @NotNull(code = ValidationExceptionCode.FIRST_NAME_IS_EMPTY,message = "名字为空")
    @MaximumLength(value ="60", code = ValidationExceptionCode.NAME_IS_ILLEGAL,message = "名字不合法")
    @MinimumLength(value = "2",code = ValidationExceptionCode.NAME_IS_ILLEGAL,message = "名字不合法")
    @Regexp(value = RegexpConstant.name,code = ValidationExceptionCode.NAME_IS_ILLEGAL,message = "名字非法")
    private String firstName;

    @ApiModelProperty(value = "姓名")
    @NotNull(code = ValidationExceptionCode.LAST_NAME_IS_EMPTY,message = "名字为空")
    @MaximumLength(value ="60", code = ValidationExceptionCode.NAME_IS_ILLEGAL,message = "名字不合法")
    @MinimumLength(value = "2",code = ValidationExceptionCode.NAME_IS_ILLEGAL,message = "名字不合法")
    @Regexp(value = RegexpConstant.name,code = ValidationExceptionCode.NAME_IS_ILLEGAL,message = "名字非法")
    private String lastName;

//    @ApiModelProperty(value = "电话")
//    private String telephone;

    @ApiModelProperty(value = "地址")
    @NotNull(code = ValidationExceptionCode.ADDRESS_INFO_IS_EMPTY, message = "地址为空")
    @MinimumLength(value = "2",code = ValidationExceptionCode.ADDRESS_CHAR_IS_NOT_ILLEGAL, message = "地址字符长度不合法，2-200字符")
    @MaximumLength(value = "200",code = ValidationExceptionCode.ADDRESS_CHAR_IS_NOT_ILLEGAL, message = "地址字符长度不合法，2-200字符")
    private String address;

    @ApiModelProperty(value = "维度")
    //@NotNull(code = com.redescooter.ses.api.common.exception.ValidationExceptionCode.LAT_IS_EMPTY, message = "维度为空")
    //@Regexp(value = RegexpConstant.lat,code = ValidationExceptionCode.LAT_IS_ILLEGAL,message = "维度不合法")
    private String lat;

    @ApiModelProperty(value = "经度")
    //@NotNull(code = com.redescooter.ses.api.common.exception.ValidationExceptionCode.LNG_IS_EMPTY, message = "经度为空")
    //@Regexp(value = RegexpConstant.lat,code = ValidationExceptionCode.LNG_ILLEGAL,message = "经度不合法")
    private String lng;

    @ApiModelProperty(value = "地址Id")
    private String placeId;

    @ApiModelProperty(value = "区域Id")
    @NotNull(code = ValidationExceptionCode.COUNTRY_CITY_MSG_IS_EMPTY, message = "国家城市信息为空")
    @MaximumLength(value ="60", code = ValidationExceptionCode.COUNTRY_CITY_ILLEGAL,message = "国家城市非法")
    @MinimumLength(value = "2",code = ValidationExceptionCode.COUNTRY_CITY_ILLEGAL,message = "国家城市非法")
    @Regexp(code = ValidationExceptionCode.COUNTRY_CITY_ILLEGAL,message = "国家城市非法")
    private String district;

    @ApiModelProperty(value = "国家")
    @NotNull(code = ValidationExceptionCode.COUNTRY_CITY_MSG_IS_EMPTY, message = "国家城市信息为空")
    @MaximumLength(value ="60", code = ValidationExceptionCode.COUNTRY_CITY_ILLEGAL,message = "国家城市非法")
    @MinimumLength(value = "2",code = ValidationExceptionCode.COUNTRY_CITY_ILLEGAL,message = "国家城市非法")
    @Regexp(code = ValidationExceptionCode.COUNTRY_CITY_ILLEGAL,message = "国家城市非法")
    private String customerCountry;

    @ApiModelProperty(value = "国家Id")
    @NotNull(code = ValidationExceptionCode.COUNTRY_CITY_MSG_IS_EMPTY, message = "国家城市信息为空")
    private Long countryId;

    @ApiModelProperty(value = "城市")
    @NotNull(code = ValidationExceptionCode.COUNTRY_CITY_MSG_IS_EMPTY, message = "国家城市信息为空")
    @MaximumLength(value ="60", code = ValidationExceptionCode.COUNTRY_CITY_ILLEGAL,message = "国家城市非法")
    @MinimumLength(value = "2",code = ValidationExceptionCode.COUNTRY_CITY_ILLEGAL,message = "国家城市非法")
    @Regexp(code = ValidationExceptionCode.COUNTRY_CITY_ILLEGAL,message = "国家城市非法")
    private String city;

}
