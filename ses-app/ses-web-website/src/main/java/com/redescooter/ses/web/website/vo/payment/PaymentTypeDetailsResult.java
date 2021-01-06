package com.redescooter.ses.web.website.vo.payment;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author jerry
 * @Date 2021/1/6 3:38 上午
 * @Description 支付方式结果集出参
 **/
@ApiModel(value = "支付方式结果集出参", description = "支付方式结果集出参")
@Data
@EqualsAndHashCode(callSuper = true)
public class PaymentTypeDetailsResult extends GeneralResult {
    /**
     * 主建
     */
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value = "主建")
    private Long id;

    /**
     * 订单类型，1销售，2租赁
     */
    @TableField(value = "payment_name")
    @ApiModelProperty(value = "支付类型名称")
    private String paymentName;

    /**
     * 产品颜色
     */
    @TableField(value = "payment_code")
    @ApiModelProperty(value = "支付类型编码")
    private String paymentCode;

    /**
     * 其他参数配置
     */
    @ApiModelProperty(value = "其他参数配置")
    private String otherParam;

    /**
     * 备注
     */
    @TableField(value = "remarks")
    @ApiModelProperty(value = "备注")
    private String remarks;

}
