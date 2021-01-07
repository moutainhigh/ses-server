package com.redescooter.ses.web.website.vo.customer;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.exception.ValidationExceptionBaseCode;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * @Author jerry
 * @Date 2021/1/6 3:45 上午
 * @Description 新增客户入参
 **/
@ApiModel(value = "新增客户入参", description = "新增客户入参")
@Data
@EqualsAndHashCode(callSuper = true)
public class AddCustomerEnter extends GeneralEnter {

    /**
     * 国家编号，中国 +86
     */
    @ApiModelProperty(value = "国家编号，中国 +86")
    private String countryCode;

    /**
     * 客户头像
     */
    @ApiModelProperty(value = "客户头像")
    private String customerHeadPicture;

    /**
     * 客户名字
     */
    @ApiModelProperty(value = "客户名字")
    private String customerFirstName;

    /**
     * 客户姓氏
     */
    @ApiModelProperty(value = "客户姓氏")
    private String customerLastName;

    /**
     * 国家名称
     */
    @ApiModelProperty(value = "国家名称")
    private String countryName;

    /**
     * 城市名称
     */
    @ApiModelProperty(value = "城市名称")
    private String cityName;

    /**
     * 区域
     */
    @ApiModelProperty(value = "区域")
    private String areaName;

    /**
     * 区域邮编
     */
    @ApiModelProperty(value = "区域邮编")
    private String postcode;

    /**
     * 地址
     */
    @ApiModelProperty(value = "地址")
    private String address;

    /**
     * 地点编号
     */
    @ApiModelProperty(value = "地点编号")
    private String placeId;

    /**
     * 经度
     */
    @ApiModelProperty(value = "经度")
    private BigDecimal longitude;

    /**
     * 纬度
     */
    @ApiModelProperty(value = "纬度")
    private BigDecimal latitude;

    /**
     * 电话
     */
    @ApiModelProperty(value = "电话")
    private String telephone;

    /**
     * 邮件
     */
    @ApiModelProperty(value = "邮件")
    private String email;

    /**
     * 持卡人
     */
    @ApiModelProperty(value = "持卡人")
    private String cardholder;

    /**
     * cvv
     */
    @ApiModelProperty(value = "cvv")
    private String cvv;

    /**
     * 卡号
     */
    @ApiModelProperty(value = "卡号")
    private String cardNum;


    /******************/

    @ApiModelProperty(value = "密码")
    @NotNull(code = ValidationExceptionBaseCode.PASSWORD_IS_EMPTY, message = "密码为空")
    private String password;

    @NotNull(code = ValidationExceptionBaseCode.PASSWORD_IS_EMPTY, message = "密码为空")
    @ApiModelProperty(value = "确认密码")
    private String ConfirmPassword;


}
