package com.redescooter.ses.web.ros.vo.restproductionorder.allocateorder;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @ClassNameAllocateOrderCombinDetailResult
 * @Description
 * @Author Aleks
 * @Date2020/10/23 16:46
 * @Version V1.0
 **/
@Data
public class AllocateOrderCombinDetailResult {

    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("组装件名称")
    private String combinName;

    @ApiModelProperty("组装件id")
    private Long productionCombinBomId;

    @ApiModelProperty("组装件编号")
    private String bomNo;

    @ApiModelProperty("调拨数量")
    private Integer qty;

    @ApiModelProperty("期望发货日期")
    private Date expectDeliveryDate;

    @ApiModelProperty("期望收货日期")
    private Date expectShipDate;

}
