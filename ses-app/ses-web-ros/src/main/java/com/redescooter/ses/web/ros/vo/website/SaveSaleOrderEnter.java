package com.redescooter.ses.web.ros.vo.website;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.redescooter.ses.api.common.annotation.MaximumLength;
import com.redescooter.ses.api.common.annotation.MinimumLength;
import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.annotation.Regexp;
import com.redescooter.ses.api.common.constant.DateConstant;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.ros.exception.ValidationExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @ClassName:SaveInquiryEnter
 * @description: SaveAboutUsEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/05/12 16:07
 */
@ApiModel(value = "Advance order", description = "Advance order")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class SaveSaleOrderEnter extends GeneralEnter {

    @ApiModelProperty(value = "id")
    private Long id;

//    @ApiModelProperty(value = "电话")
//    @NotNull(code = ValidationExceptionCode.TELEPHONE_IS_NUMBER, message = "电话为空")
//    private String telephone;
//
//    @ApiModelProperty(value = "地址")
//    //@NotNull(code = ValidationExceptionCode.ADDRESS_INFO_IS_EMPTY, message = "地址为空")
//    //@MinimumLength(value = "2",code = ValidationExceptionCode.ADDRESS_CHAR_IS_NOT_ILLEGAL, message = "地址字符长度不合法，2-200字符")
//    //@MaximumLength(value = "200",code = ValidationExceptionCode.ADDRESS_CHAR_IS_NOT_ILLEGAL, message = "地址字符长度不合法，2-200字符")
//    private String address;
//
//    @ApiModelProperty(value = "维度")
//    //@NotNull(code = com.redescooter.ses.api.common.exception.ValidationExceptionCode.LAT_IS_EMPTY, message = "维度为空")
//    //@Regexp(value = RegexpConstant.lat,code = ValidationExceptionCode.LAT_IS_ILLEGAL,message = "维度不合法")
//    private String lat;
//
//    @ApiModelProperty(value = "经度")
//    //@NotNull(code = com.redescooter.ses.api.common.exception.ValidationExceptionCode.LNG_IS_EMPTY, message = "经度为空")
//    //@Regexp(value = RegexpConstant.lat,code = ValidationExceptionCode.LNG_ILLEGAL,message = "经度不合法")
//    private String lng;
//
//    @ApiModelProperty(value = "地址Id")
//    private String placeId;
//
//    @ApiModelProperty(value = "区域Id")
//    //@NotNull(code = ValidationExceptionCode.COUNTRY_CITY_MSG_IS_EMPTY, message = "国家城市信息为空")
//    //@MaximumLength(value ="60", code = ValidationExceptionCode.COUNTRY_CITY_ILLEGAL,message = "国家城市非法")
//    //@MinimumLength(value = "2",code = ValidationExceptionCode.COUNTRY_CITY_ILLEGAL,message = "国家城市非法")
//    //@Regexp(code = ValidationExceptionCode.COUNTRY_CITY_ILLEGAL,message = "国家城市非法")
//    private String district;
//
//    @ApiModelProperty(value = "国家")
////    @NotNull(code = ValidationExceptionCode.COUNTRY_CITY_MSG_IS_EMPTY, message = "国家城市信息为空")
////    @MaximumLength(value ="60", code = ValidationExceptionCode.COUNTRY_CITY_ILLEGAL,message = "国家城市非法")
////    @MinimumLength(value = "2",code = ValidationExceptionCode.COUNTRY_CITY_ILLEGAL,message = "国家城市非法")
////    @Regexp(code = ValidationExceptionCode.COUNTRY_CITY_ILLEGAL,message = "国家城市非法")
//    private String customerCountry;

    @ApiModelProperty(value = "product Id")
    @NotNull(code = ValidationExceptionCode.TYPE_IS_EMPTY, message = "产品类型为空")
    private Long productId;

    @ApiModelProperty(value = "product model")
    @NotNull(code = ValidationExceptionCode.MODEL_IS_EMPTY, message = "产品类型为空")
    private String productModel;

    @ApiModelProperty(value = "product number")
    @NotNull(code = ValidationExceptionCode.QTY_IS_EMPTY, message = "产品数量为空")
    private Integer productQty;

    @ApiModelProperty(value = "Accessory battery Id")
    @NotNull(code = ValidationExceptionCode.BATTERY_ID_IS_EMPTY, message = "电池Id 为空")
    private Long accessoryBatteryId;

    @ApiModelProperty(value = "Accessory number")
    private Integer accessoryBatteryQty;

    @ApiModelProperty(value = "Do you want to buy a trunk")
    private Boolean buyTopCase;

    @ApiModelProperty(value = "Boot ID")
    private Long topCaseId;

    @ApiModelProperty(value = "Name of bank card")
    @NotNull(code = ValidationExceptionCode.BANKCARD_NAME_IS_EMPTY, message = "银行卡上姓名为空")
    @MinimumLength(value = "2",code = ValidationExceptionCode.BANKCARDNAME_IS_NOT_ILLEGAL,message = "银行卡上姓名不合法")
    @MaximumLength(value = "40",code = ValidationExceptionCode.BANKCARDNAME_IS_NOT_ILLEGAL,message = "银行卡上姓名不合法")
    private String bankCardName;

    @ApiModelProperty(value = "卡号",hidden = true)
    private String cardNum;

    @ApiModelProperty(value = "Expiration time")
    private Long expiredTime;

    @ApiModelProperty(value = "cvv")
    private String cvv;

}
