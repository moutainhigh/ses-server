package com.redescooter.ses.mobile.rps.vo.restproductionorder.outbound;

import com.redescooter.ses.api.common.vo.base.PageEnter;

import io.swagger.annotations.*;
import lombok.Data;

/**
 * @ClassName:OutboundOrderEnter
 * @description: OutboundOrderEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/11/02 12:10 
 */
@Data
@ApiModel(value = "出库单列表查询入参对象", description = "出库单列表查询入参对象")
public class OutboundOrderEnter extends PageEnter {


    @ApiModelProperty(value = "产品类型 1整车 2组装件 3部件", dataType = "Integer", required = true)
    private Integer productType;

    @ApiModelProperty(value = "订单类型 1销售调拨 2补料出库 3生产组装 4其它", dataType = "Integer", required = true)
    private Integer outboundOrderType;

    @ApiModelProperty(value = "出库单状态 0未出库 1已出库", dataType = "Integer", required = true)
    private Integer status;

    @ApiModelProperty(value = "关键字", dataType = "String")
    private String keyword;

}
