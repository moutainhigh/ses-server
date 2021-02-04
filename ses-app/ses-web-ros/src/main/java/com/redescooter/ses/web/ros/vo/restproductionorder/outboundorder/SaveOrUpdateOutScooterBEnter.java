package com.redescooter.ses.web.ros.vo.restproductionorder.outboundorder;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description:
 * @author: Aleks
 * @create: 2021/01/04 14:28
 */
@Data
@ApiModel("新增或编辑车辆出库单获取的关联发货单的明细")
public class SaveOrUpdateOutScooterBEnter {

    @ApiModelProperty(value="车型（规格分组）的id")
    private Long groupId;

    @ApiModelProperty(value="颜色id")
    private Long colorId;

    @ApiModelProperty(value="车型（规格分组）的名字")
    private String groupName;

    @ApiModelProperty(value="颜色的名字")
    private String colorName;

    @ApiModelProperty(value="色值")
    private String colorValue;

    @ApiModelProperty(value="组装数量")
    private Integer qty;

    @ApiModelProperty("上限数量")
    private Integer ableQty;

    @ApiModelProperty("总数量")
    private Integer totalQty;

}
