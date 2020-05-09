package com.redescooter.ses.mobile.rps.vo.bo;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import io.swagger.annotations.*;

/**
 * @ClassName:ReturnCompletePartDto
 * @description: ReturnCompletePartDto
 * @author: Alex
 * @Version：1.3
 * @create: 2020/05/08 20:30
 */
@ApiModel(value = "退货并返回时对主订单的数据", description = "退货并返回时对主订单的数据")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class ReturnCompletePartDto extends GeneralEnter {

    @ApiModelProperty(value = "id 主订单id")
    private Long id;

    @ApiModelProperty(value = "原始价格")
    private BigDecimal originalAmount;

    @ApiModelProperty(value = "退款价格")
    private BigDecimal retureTotalPrice;

    @ApiModelProperty(value = "退货数量")
    private Integer returnTotal;
}
