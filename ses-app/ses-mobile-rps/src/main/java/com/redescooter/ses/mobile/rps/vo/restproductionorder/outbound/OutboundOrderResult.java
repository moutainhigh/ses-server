package com.redescooter.ses.mobile.rps.vo.restproductionorder.outbound;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import lombok.*;

import io.swagger.annotations.*;

/**
 * @ClassName:invoiceOrderResult
 * @description: invoiceOrderResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/11/02 12:06 
 */
@ApiModel(value = "单据列表", description = "单据列表")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
public class OutboundOrderResult extends GeneralResult {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "单据编号")
    private String outboundNo;

    @ApiModelProperty(value = "出库数量")
    private int qty;

    @ApiModelProperty(value = "产品类型")
    private Integer productType;

    @ApiModelProperty(value = "单据状态")
    private Integer status;

    @ApiModelProperty(value = "单据状态")
    private Integer orderType;
}
