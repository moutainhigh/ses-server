package com.redescooter.ses.web.website.vo.distributor;

import com.baomidou.mybatisplus.annotation.TableField;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * @Author jerry
 * @Date 2021/1/6 3:44 上午
 * @Description 经销商结果集出参
 **/
@ApiModel(value = "新增经销商入参", description = "新增经销商入参")
@Data
@EqualsAndHashCode(callSuper = true)
public class DealerDetailsResult extends GeneralResult {

    /**
     * 主键
     */
    @ApiModelProperty(value = "id")
    private Long id;

    /**
     * 门店编码
     */
    @ApiModelProperty(value="Store code")
    private String code;

    /**
     * 门店名称
     */
    @ApiModelProperty(value = "Store name")
    private String name;

    /**
     * 门店logo
     */
    @ApiModelProperty(value = "Store logo")
    private String logoUrl;

    /**
     * 国家代码 86中国 33法国
     */
    @ApiModelProperty(value = "Country code 86 China 33 France")
    private String countryCode;

    /**
     * 电话
     */
    @ApiModelProperty(value = "Telephone")
    private String tel;

    /**
     * 邮件地址
     */
    @ApiModelProperty(value = "email")
    private String email;

    /**
     * 地址
     */
    @ApiModelProperty(value = "address")
    private String address;

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
     * 邮编
     */
    @ApiModelProperty(value = "Postcode")
    private String cp;

    /**
     * 城市
     */
    @ApiModelProperty(value = "city")
    private String city;

    /**
     * 地区
     */
    @ApiModelProperty(value = "area")
    private String area;

    /**
     * 备注
     */
    @ApiModelProperty(value = "remarks")
    private String remark;

    /**
     * 门店类型,1销售门店,2维修仓库，3销售及维修
     */
    @ApiModelProperty(value = "Store type, 1 sales store, 2 maintenance warehouse, 3 sales and maintenance")
    private String type;

}
