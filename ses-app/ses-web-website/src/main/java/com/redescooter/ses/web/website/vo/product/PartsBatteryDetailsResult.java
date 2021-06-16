package com.redescooter.ses.web.website.vo.product;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * @Author jerry
 * @Date 2021/1/6 3:32 上午
 * @Description 产品配件配置结果集出参
 **/
@ApiModel(value = "Product parts configuration result set output parameters", description = "产品配件配置结果集出参")
@Data
@EqualsAndHashCode(callSuper = true)
public class PartsBatteryDetailsResult extends GeneralResult {

    /**
     * 配件ID
     */
    @ApiModelProperty(value = "Parts Id")
    private Long partsId;

    /**
     * 数量
     */
    @ApiModelProperty(value = "number")
    private Integer qty;

    /**
     * 部件名称
     */
    @ApiModelProperty(value = "partsName")
    private String partsName;

    /**
     * 图片链接
     */
    @ApiModelProperty(value = "picture")
    private String picture;

    /**
     * 货币单位
     */
    @ApiModelProperty(value = "unit")
    private String unit;

    /**
     * 销售价格
     */
    @ApiModelProperty(value = "price")
    private BigDecimal price;
}
