package com.redescooter.ses.web.website.vo.distributor;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * @Author jerry
 * @Date 2021/1/6 3:43 上午
 * @Description 新增经销商入参
 **/

@ApiModel(value = "新增经销商入参", description = "新增经销商入参")
@Data
@EqualsAndHashCode(callSuper = true)
public class AddDealerEnter extends GeneralEnter {

    /**
     * 门店名称
     */
    @ApiModelProperty(value="门店名称")
    private String name;

    /**
     * 门店logo
     */
    @ApiModelProperty(value="门店logo")
    private String logoUrl;

    /**
     * 国家代码 86中国 33法国
     */
    @ApiModelProperty(value="国家代码 86中国 33法国")
    private String countryCode;

    /**
     * 电话
     */
    @ApiModelProperty(value="电话")
    private String tel;

    /**
     * 邮件地址
     */
    @ApiModelProperty(value="邮件地址")
    private String email;

    /**
     * 地址
     */
    @ApiModelProperty(value="地址")
    private String address;

    /**
     * 经度
     */
    @ApiModelProperty(value="经度")
    private BigDecimal longitude;

    /**
     * 纬度
     */
    @ApiModelProperty(value="纬度")
    private BigDecimal latitude;


    /**
     * 邮编
     */
    @ApiModelProperty(value="邮编")
    private String cp;

    /**
     * 城市
     */
    @ApiModelProperty(value="城市")
    private String city;

    /**
     * 地区
     */
    @ApiModelProperty(value="地区")
    private String area;

    /**
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String remark;

    /**
     * 门店类型,1销售门店,2维修仓库，3销售及维修
     */
    @ApiModelProperty(value="门店类型,1销售门店,2维修仓库，3销售及维修")
    private int type;
}
