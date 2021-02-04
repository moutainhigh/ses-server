package com.redescooter.ses.mobile.rps.vo.restproductionorder.consign;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import lombok.*;

import io.swagger.annotations.*;

/**
 * @ClassName:ConsignOrderListResult
 * @description: ConsignOrderListResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/11/03 18:04 
 */
@ApiModel(value = "委托单列表", description = "委托单列表")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
public class ConsignOrderListResult extends GeneralResult {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "单据编号")
    private String orderNo;

    @ApiModelProperty(value = "数量")
    private Integer qty;
}
