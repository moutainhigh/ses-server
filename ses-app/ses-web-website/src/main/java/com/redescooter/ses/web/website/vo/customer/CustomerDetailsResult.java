package com.redescooter.ses.web.website.vo.customer;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * @Author jerry
 * @Date 2021/1/6 3:46 上午
 * @Description 客户结果集出参
 **/
@ApiModel(value = "customer result", description = "customer result")
@Data
@EqualsAndHashCode(callSuper = true)
public class CustomerDetailsResult extends GeneralResult {

    /**
     * 主键
     */
    @ApiModelProperty(value = "主建")
    private Long id;

    /**
     * 国家编号，中国 +86
     */
    @ApiModelProperty(value = "Country number, China + 86")
    private String countryCode;

    /**
     * 客户头像
     */
    @ApiModelProperty(value = "Customer profile")
    private String customerHeadPicture;

    /**
     * 客户名字
     */
    @ApiModelProperty(value = "Customer name")
    private String customerFirstName;

    /**
     * 客户姓氏
     */
    @ApiModelProperty(value = "Customer last name")
    private String customerLastName;

    /**
     * 国家名称
     */
    @ApiModelProperty(value = "Country name")
    private String countryName;

    /**
     * 城市名称
     */
    @ApiModelProperty(value = "City name")
    private String cityName;

    /**
     * 区域
     */
    @ApiModelProperty(value = "Area")
    private String areaName;

    /**
     * 区域邮编
     */
    @ApiModelProperty(value = "Regional zip code")
    private String postcode;

    /**
     * 地址
     */
    @ApiModelProperty(value = "address")
    private String address;

    /**
     * 地点编号
     */
    @ApiModelProperty(value = "Location No. location No")
    private String placeId;

    /**
     * 经度
     */
    @ApiModelProperty(value = "longitude")
    private BigDecimal longitude;

    /**
     * 纬度
     */
    @ApiModelProperty(value = "latitude")
    private BigDecimal latitude;

    /**
     * 电话
     */
    @ApiModelProperty(value = "telephone")
    private String telephone;

    /**
     * 邮件
     */
    @ApiModelProperty(value = "email")
    private String email;

}
