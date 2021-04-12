package com.redescooter.ses.web.website.vo.customer;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.website.exception.SiteValidationExceptionCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * @Author jerry
 * @Date 2021/1/6 3:46 上午
 * @Description 客户编辑
 **/
@ApiModel(value = "Edit Site Customer", description = "Edit Site Customer")
@Data
@EqualsAndHashCode(callSuper = true)
public class EditSiteCustomerEnter extends GeneralEnter {

    /**
     * 主键
     */
    @ApiModelProperty(value = "Customer Id")
    @NotNull(code = SiteValidationExceptionCode.ID_IS_EMPTY, message = "Id 不为空")
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
    @NotNull(code = SiteValidationExceptionCode.FIRST_NAME_IS_EMPTY,message = "名字为空")
    private String customerFirstName;

    /**
     * 客户姓氏
     */
    @ApiModelProperty(value = "Customer last name")
    @NotNull(code = SiteValidationExceptionCode.LAST_NAME_IS_EMPTY,message = "姓氏为空")
    private String customerLastName;

    /**
     * 国家名称
     */
    @ApiModelProperty(value = "Country name")
    @NotNull(code = SiteValidationExceptionCode.COUNTRY_CANNOT_EMPTY,message = "国家为空")
    private String countryName;

    /**
     * 城市名称
     */
    @ApiModelProperty(value = "City name")
    @NotNull(code = SiteValidationExceptionCode.CITY_IS_EMPTY,message = "城市为空")
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
    @NotNull(code = SiteValidationExceptionCode.POST_CODE_IS_EMPTY,message = "邮编为空")
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
     * 邮箱
     */
    @ApiModelProperty(value = "email")
    private String email;

}
