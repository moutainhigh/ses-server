package com.redescooter.ses.web.ros.vo.restproductionorder.allocateorder;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @ClassNameAllocateOrderPartsDetailResult
 * @Description
 * @Author Aleks
 * @Date2020/10/23 17:16
 * @Version V1.0
 **/
@Data
@ApiModel(value = "调拨单部件详情出参", description = "调拨单部件详情出参")
public class AllocateOrderPartsDetailResult extends GeneralResult {

    @ApiModelProperty(value = "主键id")
    private Long id;

    @ApiModelProperty(value = "部件id")
    private Long partsId;

    @ApiModelProperty(value = "部件名称(英文名称)")
    private String partsName;

    @ApiModelProperty(value = "部件编号")
    private String partsNo;

    @ApiModelProperty(value = "部件类型，1：Parts，2：Accessory，3：Battery，4：Scooter，5：Combination")
    private Integer partsType;

    @ApiModelProperty(value = "调拨数量")
    private Integer qty;

    @ApiModelProperty(value = "期望发货日期")
    private Date expectDeliveryDate;

    @ApiModelProperty(value = "期望收货日期")
    private Date expectShipDate;


}
