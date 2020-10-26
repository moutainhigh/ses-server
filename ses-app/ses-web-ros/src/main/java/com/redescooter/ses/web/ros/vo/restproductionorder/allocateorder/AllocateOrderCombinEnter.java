package com.redescooter.ses.web.ros.vo.restproductionorder.allocateorder;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @ClassNameAllocateOrderCombinEnter
 * @Description
 * @Author Aleks
 * @Date2020/10/26 11:41
 * @Version V1.0
 **/
@ApiModel(value = "调拨单组装件产品新增入参",description = "调拨单组装件产品新增入参")
@Data
public class AllocateOrderCombinEnter extends GeneralEnter {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty(value = "组装件名称(英文名称)")
    private String combinName;

    @ApiModelProperty(value = "组装件id")
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
