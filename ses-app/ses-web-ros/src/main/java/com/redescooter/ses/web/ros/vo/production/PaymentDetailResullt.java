package com.redescooter.ses.web.ros.vo.production;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * @ClassName:paymentDetailResullt
 * @description: paymentDetailResullt
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/19 10:00
 */
@ApiModel(value = "付款详情", description = "付款详情")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class PaymentDetailResullt extends GeneralResult {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "总价格")
    private BigDecimal totalPrice;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "加工费")
    private BigDecimal processCost;

    @ApiModelProperty(value = "加工费比例")
    private BigDecimal processCostRatio;

    @ApiModelProperty(value = "条目集合")
    private List<PaymentItemDetailResult> paymentItemList;
}
