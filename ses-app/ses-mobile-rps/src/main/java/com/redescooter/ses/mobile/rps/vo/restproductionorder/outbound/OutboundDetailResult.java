package com.redescooter.ses.mobile.rps.vo.restproductionorder.outbound;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import lombok.*;

import java.util.List;

import io.swagger.annotations.*;

/**
 * @ClassName:OutboundDetailResult
 * @description: OutboundDetailResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/11/02 13:26 
 */
@ApiModel(value = "单据详情", description = "单据详情")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
public class OutboundDetailResult extends GeneralResult {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "出库单号")
    private String outboundOrderNo;

    @ApiModelProperty(value = "产品类型")
    private Integer productType;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "出库总数量")
    private Integer stockTotal;

    @ApiModelProperty(value = "质检数量")
    private Integer qcQty;

    @ApiModelProperty(value = "产品列表")
    private List<OutboundDetailProductResult> outboundDetailProductResultList;
}
