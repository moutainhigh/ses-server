package com.redescooter.ses.web.ros.vo.restproductionorder.outboundorder;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description:
 * @author: Aleks
 * @create: 2021/01/04 14:53
 */
@Data
@ApiModel("新增或编辑组装件出库单获取的关联发货单的明细")
public class SaveOrUpdateOutCombinBEnter {

    @ApiModelProperty(value="组装件名称(中文名称)")
    private String combinName;

    @ApiModelProperty(value="组装件编号")
    private String combinNo;

    @ApiModelProperty(value="组装件id")
    private Long productionCombinBomId;

    @ApiModelProperty(value="组装数量")
    private Integer qty;

}
