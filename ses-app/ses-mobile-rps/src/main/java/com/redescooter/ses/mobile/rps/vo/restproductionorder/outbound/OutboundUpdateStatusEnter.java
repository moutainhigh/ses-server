package com.redescooter.ses.mobile.rps.vo.restproductionorder.outbound;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import lombok.*;

import io.swagger.annotations.*;

/**
 * @ClassName:OutboundUpdateStatusEnter
 * @description: OutboundUpdateStatusEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/11/05 16:00 
 */
@ApiModel(value = "修改订单状态", description = "修改订单状态")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
public class OutboundUpdateStatusEnter extends GeneralEnter {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "操作动态")
    private Integer operatingDynamics;

    @ApiModelProperty(value = "数量")
    private Integer qty;
}
