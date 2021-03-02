package com.redescooter.ses.api.common.vo.distributor;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @description:
 * @author: Aleks
 * @create: 2021/01/25 17:56
 */
@Data
public class SavaOrUpdateDistributorEnter implements Serializable {

    @ApiModelProperty(value = "状态 1启用中 2未启用")
    private Integer status;

    /**
     * 门店编码
     */
    @ApiModelProperty(value = "门店编码")
    private String code;

    /**
     * 门店名称
     */
    @ApiModelProperty(value = "门店名称")
    private String name;

    /**
     * 门店logo
     */
    @ApiModelProperty(value = "门店logo")
    private String logoUrl;

    /**
     * 国家代码 86中国 33法国
     */
    @ApiModelProperty(value = "国家代码 86中国 33法国")
    private String countryCode;

    /**
     * 电话
     */
    @ApiModelProperty(value = "电话")
    private String tel;

    /**
     * 邮件地址
     */
    @ApiModelProperty(value = "邮件地址")
    private String email;

    /**
     * 地址
     */
    @ApiModelProperty(value = "地址")
    private String address;

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
     * 邮编
     */
    @ApiModelProperty(value = "邮编")
    private String cp;

    /**
     * 城市
     */
    @ApiModelProperty(value = "城市")
    private String city;

    /**
     * 地区
     */
    @ApiModelProperty(value = "地区")
    private String area;

    /**
     * 合同url
     */
    @ApiModelProperty(value = "合同url")
    private String contractUrl;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 门店类型,1销售门店,2维修仓库，3销售及维修
     */
    @ApiModelProperty(value = "门店类型,1销售门店,2维修仓库，3销售及维修")
    private Integer type;

    /**
     * 是否同步
     */
    @ApiModelProperty(value = "是否同步")
    private Boolean synchronizeFlag;

    /**
     * 乐观锁
     */
    @ApiModelProperty(value = "乐观锁")
    private Integer revision;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private Long createdBy;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createdTime;

    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人")
    private Long updatedBy;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private Date updatedTime;


}
