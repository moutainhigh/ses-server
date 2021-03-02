package com.redescooter.ses.web.website.vo.product;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author jerry
 * @Date 2021/1/6 1:22 上午
 * @Description 产品详情结果集出参
 **/
@ApiModel(value = "Product details result set for reference", description = "产品详情结果集出参")
@Data
@EqualsAndHashCode(callSuper = true)
public class ProductDetailsResult extends GeneralResult {

    /**
     * 主建
     */
    @ApiModelProperty(value = "Product Id")
    private Long productId;

    /**
     * 图片
     */
    @ApiModelProperty(value = "picture")
    private String picture;

    /**
     * 中文名称
     */
    @ApiModelProperty(value = "Chinese name")
    private String cnName;

    /**
     * 法文名称
     */
    @ApiModelProperty(value = "French name")
    private String frName;

    /**
     * 英文名称
     */
    @ApiModelProperty(value = "English name")
    private String enName;

    /**
     * 产品型号ID
     */
    @ApiModelProperty(value = "Product model ID")
    private Long productModelId;

    /**
     * 最少电池数
     */
    @ApiModelProperty(value = "Minimum number of batteries")
    private Integer minBatteryNum;

    /**
     * 产品参数 存储JSON
     */
    @ApiModelProperty(value = "Product parameter storage JSON")
    private String materParameter;

    /**
     * 其他参数 存储JSON
     */
    @ApiModelProperty(value = "Other parameters store JSON")
    private String otherParameter;

    /**
     * 速度
     */
    @ApiModelProperty(value = "speed")
    private String speed;

    /**
     * 功率
     */
    @ApiModelProperty(value = "power")
    private String power;

    /**
     * 充电周期
     */
    @ApiModelProperty(value = "Charging cycle")
    private String chargeCycle;

    /**
     * 续航里程
     */
    @ApiModelProperty(value = "mileage")
    private String mileage;

}
