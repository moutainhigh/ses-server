package com.redescooter.ses.web.ros.vo.restproductionorder.outboundorder;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description:
 * @author: Aleks
 * @create: 2021/01/04 15:03
 */
@Data
@ApiModel("新增或编辑部件出库单获取的关联发货单的明细")
public class SaveOrUpdateOutPartsBEnter {

    @ApiModelProperty(value="部件名称(英文名称)")
    private String partsName;

    @ApiModelProperty(value="部件编号")
    private String partsNo;

    @ApiModelProperty(value="部件类型，1：Parts，2：Accessory，3：Battery，4：Scooter，5：Combination")
    private Integer partsType;

    @ApiModelProperty(value="采购数量")
    private Integer qty;

    @ApiModelProperty("部件ID")
    private Long partsId;

    @ApiModelProperty("上限数量")
    private Integer ableQty;

    @ApiModelProperty("总数量")
    private Integer totalQty;
}
