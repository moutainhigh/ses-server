package com.redescooter.ses.web.ros.vo.restproductionorder.allocateorder;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @ClassNameAllocateOrderScooterDetailResult
 * @Description
 * @Author Aleks
 * @Date2020/10/23 16:24
 * @Version V1.0
 **/
@Data
public class AllocateOrderScooterDetailResult extends GeneralResult {

    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("车型id")
    private Long groupId;

    @ApiModelProperty("车型名称")
    private String groupName;

    @ApiModelProperty("颜色id")
    private Long colorId;

    @ApiModelProperty("颜色名称")
    private String colorName;

    @ApiModelProperty("调拨数量")
    private Integer qty;

    @ApiModelProperty("期望发货日期")
    private Date expectDeliveryDate;

    @ApiModelProperty("期望收货日期")
    private Date expectShipDate;

}
