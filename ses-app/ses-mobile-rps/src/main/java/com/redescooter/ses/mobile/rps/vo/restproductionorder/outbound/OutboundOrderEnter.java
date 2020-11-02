package com.redescooter.ses.mobile.rps.vo.restproductionorder.outbound;

import com.redescooter.ses.api.common.vo.base.PageEnter;
import lombok.*;

import io.swagger.annotations.*;

/**
 * @ClassName:OutboundOrderEnter
 * @description: OutboundOrderEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/11/02 12:10 
 */
@ApiModel(value = "出库单列表", description = "出库单列表")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
public class OutboundOrderEnter extends PageEnter {


    @ApiModelProperty(value = "产品类型")
    private Integer productType;

    @ApiModelProperty(value = "关键字")
    private String keyword;

    @ApiModelProperty(value = "订单类型")
    private Integer outboundOrderType;
}
