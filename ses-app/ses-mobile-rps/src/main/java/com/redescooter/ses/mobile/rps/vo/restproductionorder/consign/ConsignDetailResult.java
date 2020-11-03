package com.redescooter.ses.mobile.rps.vo.restproductionorder.consign;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import lombok.*;

import java.util.Date;
import java.util.List;

import io.swagger.annotations.*;

/**
 * @ClassName:ConsignDetailResult
 * @description: ConsignDetailResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/11/03 18:32 
 */
@ApiModel(value = "", description = "")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
public class ConsignDetailResult extends GeneralResult {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "订单编号")
    private String orderNo;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "交货日期")
    private Date deliveryDate;

    @ApiModelProperty(value = "运输方式")
    private Integer transType;

    @ApiModelProperty(value = "应发货总数")
    private Integer consignorTotal;

    @ApiModelProperty(value = "已发货总数")
    private Integer consignorQty;

    @ApiModelProperty(value = "产品信息")
    private List<ConsignDetailProductResult> consignDetailProductResultList;
}
