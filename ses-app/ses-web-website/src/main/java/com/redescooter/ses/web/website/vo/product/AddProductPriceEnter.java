package com.redescooter.ses.web.website.vo.product;

import com.baomidou.mybatisplus.annotation.TableField;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * @Author jerry
 * @Date 2021/1/6 3:35 上午
 * @Description 新增产品价格入参
 **/
@ApiModel(value = "新增产品价格入参", description = "新增产品价格入参")
@Data
@EqualsAndHashCode(callSuper = true)
public class AddProductPriceEnter extends GeneralEnter {

    /**
     * 产品型号Id
     */
    @ApiModelProperty(value = "Product Model Id")
    private Long productModelId;
    /**
     * 状态,0全额付款，1分期付款
     */
    @ApiModelProperty(value = "状态,0全额付款，1分期付款")
    private String priceType;

    /**
     * 分期付款时间数，单位month
     */
    @ApiModelProperty(value = "分期付款时间数，单位month")
    private String installmentTime;

    /**
     * 销售价格 浮点型价格
     */
    @ApiModelProperty(value = "销售价格 浮点型价格")
    private BigDecimal price;

    /**
     * 起步价
     */
    @TableField(value = "start_price")
    @ApiModelProperty(value = "起步价")
    private BigDecimal startPrice;

    /**
     * 预付定金
     */
    @ApiModelProperty(value = "预付定金")
    private BigDecimal prepaidDeposit;

    /**
     * 优惠抵扣金额
     */
    @ApiModelProperty(value = "优惠抵扣金额")
    private BigDecimal amountDiscount;
}
