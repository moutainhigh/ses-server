package com.redescooter.ses.web.ros.vo.restproductionorder.purchass;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import lombok.*;

import io.swagger.annotations.*;

/**
 * @ClassName:PurchasDetailProductListResult
 * @description: PurchasDetailProductListResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/11/10 15:00 
 */
@ApiModel(value = "单据详情部件列表", description = "单据详情部件列表")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
public class PurchasDetailProductListResult extends GeneralResult {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "部品Id")
    private Long partId;

    @ApiModelProperty(value = "部品编号")
    private String partN;

    @ApiModelProperty(value = "部品名称")
    private String partName;

    @ApiModelProperty(value = "部品类型")
    private Integer partType;

    @ApiModelProperty(value = "数量")
    private Integer qty;

    @ApiModelProperty(value = "单价金额")
    private String amount;
}
