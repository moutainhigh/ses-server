package com.redescooter.ses.web.ros.vo.restproductionorder.allocateorder;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @ClassNameAllocateOrderScooterEnter
 * @Description
 * @Author Aleks
 * @Date2020/10/23 15:27
 * @Version V1.0
 **/
@Data
@ApiModel(value = "调拨单车辆产品新增入参",description = "调拨单车辆产品新增入参")
public class AllocateOrderScooterEnter extends GeneralEnter {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("车型id")
    private Long groupId;

    @ApiModelProperty("颜色")
    private Long colorId;

    @ApiModelProperty("调拨数量")
    private Integer qty;

    @ApiModelProperty("期望发货日期")
    private Date expectDeliveryDate;

    @ApiModelProperty("期望收货日期")
    private Date expectShipDate;

}
