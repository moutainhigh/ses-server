package com.redescooter.ses.web.ros.vo.restproductionorder.allocateorder;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassNameAllocateProductListResult
 * @Description
 * @Author Aleks
 * @Date2020/11/9 15:08
 * @Version V1.0
 **/
@Data
@ApiModel(value = "调拨单的产品列表返参",description = "调拨单的产品列表返参")
public class AllocateProductListResult extends GeneralResult {

    @ApiModelProperty(value = "调拨单id")
    private Long allocateId;

    @ApiModelProperty(value = "调拨单类型，1：整车，2：组装件，3：部件")
    private Integer allocateType;

    @ApiModelProperty("车辆调拨产品")
    private List<AllocateOrderScooterDetailResult> scooters = new ArrayList<>();

    @ApiModelProperty("组装件调拨产品")
    private List<AllocateOrderCombinDetailResult> combins = new ArrayList<>();

    @ApiModelProperty("部件调拨产品")
    private List<AllocateOrderPartsDetailResult> parts = new ArrayList<>();

}
