package com.redescooter.ses.web.ros.vo.restproductionorder.allocateorder;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @ClassNameAllocateOrderPartsEnter
 * @Description
 * @Author Aleks
 * @Date2020/10/26 11:53
 * @Version V1.0
 **/
@Data
@ApiModel(value = "调拨单部件产品新增编辑入参",description = "调拨单部件产品新增编辑入参")
public class AllocateOrderPartsEnter extends GeneralEnter {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty(value = "部件id")
    private Long partsId;

    @ApiModelProperty(value = "部件名称(英文名称)")
    private String partsName;

    @ApiModelProperty(value = "部件编号")
    private String partsNo;

    @ApiModelProperty(value = "部件类型，1：Parts，2：Accessory，3：Battery，4：Scooter，5：Combination")
    private Integer partsType;

    @ApiModelProperty("调拨数量")
    private Integer qty;

    @ApiModelProperty("期望发货日期")
    private Date expectDeliveryDate;

    @ApiModelProperty("期望收货日期")
    private Date expectShipDate;

}
