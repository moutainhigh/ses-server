package com.redescooter.ses.web.website.vo.product;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author jerry
 * @Date 2021/1/6 1:22 上午
 * @Description 产品详情结果集出参
 **/
@ApiModel(value = "产品详情结果集出参", description = "产品详情结果集出参")
@Data
@EqualsAndHashCode(callSuper = true)
public class ProductDetailsResult extends AddProductEnter {

    /**
     * 主建
     */
    @ApiModelProperty(value = "主键")
    private Long id;

    /**
     * 图片
     */
    @ApiModelProperty(value = "图片")
    private String picture;

    /**
     * 中文名称
     */
    @ApiModelProperty(value = "中文名称")
    private String cnName;

    /**
     * 法文名称
     */
    @ApiModelProperty(value = "法文名称")
    private String frName;

    /**
     * 英文名称
     */
    @ApiModelProperty(value = "英文名称")
    private String enName;

    /**
     * 产品型号ID
     */
    @ApiModelProperty(value = "产品型号ID")
    private Long productModelId;

    /**
     * 最少电池数
     */
    @ApiModelProperty(value = "最少电池数")
    private Integer minBatteryNum;

    /**
     * 是否支持售后服务
     */
    @ApiModelProperty(value = "是否支持售后服务")
    private Boolean afterSalesFlag;

    /**
     * 是否支持增值服务
     */
    @ApiModelProperty(value = "是否支持增值服务")
    private Boolean addedServicesFlag;

    /**
     * 产品参数 存储JSON
     */
    @ApiModelProperty(value = "产品参数 存储JSON")
    private String materParameter;

    /**
     * 其他参数 存储JSON
     */
    @ApiModelProperty(value = "其他参数 存储JSON")
    private String otherParameter;

    /**
     * 速度
     */
    @ApiModelProperty(value = "速度")
    private String speed;

    /**
     * 功率
     */
    @ApiModelProperty(value = "功率")
    private String power;

    /**
     * 充电周期
     */
    @ApiModelProperty(value = "充电周期")
    private String chargeCycle;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

}
