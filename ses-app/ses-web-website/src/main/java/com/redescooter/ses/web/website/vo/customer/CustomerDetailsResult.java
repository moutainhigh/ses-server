package com.redescooter.ses.web.website.vo.customer;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author jerry
 * @Date 2021/1/6 3:46 上午
 * @Description 客户结果集出参
 **/
@ApiModel(value = "客户结果集出参", description = "客户结果集出参")
@Data
@EqualsAndHashCode(callSuper = true)
public class CustomerDetailsResult extends GeneralResult {
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
     * 客户全名
     */
    @ApiModelProperty(value = "客户全名")
    private String customerFullName;

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
     * 电话
     */
    @ApiModelProperty(value = "电话")
    private String telephone;

    /**
     * 邮件
     */
    @ApiModelProperty(value = "邮件")
    private String email;
}
