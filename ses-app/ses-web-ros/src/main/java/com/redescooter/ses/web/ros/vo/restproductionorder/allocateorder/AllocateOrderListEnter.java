package com.redescooter.ses.web.ros.vo.restproductionorder.allocateorder;

import com.redescooter.ses.api.common.vo.base.PageEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassNameAllocateOrderListEnter
 * @Description
 * @Author Aleks
 * @Date2020/10/23 11:56
 * @Version V1.0
 **/
@Data
@ApiModel(value = "调拨单列表入参", description = "调拨单列表入参")
public class AllocateOrderListEnter extends PageEnter {

    @ApiModelProperty(value = "关键字")
    private String keyword;

    @ApiModelProperty(value = "调拨单状态，0：草稿，10：待处理，20：采购中，30：待发货，40：待签收，50：已签收，60：已完成，70：已取消")
    private Integer allocateStatus;

    @ApiModelProperty("调拨单类型,1:车辆，2:组装件，3:部件")
    private Integer classType;

}
