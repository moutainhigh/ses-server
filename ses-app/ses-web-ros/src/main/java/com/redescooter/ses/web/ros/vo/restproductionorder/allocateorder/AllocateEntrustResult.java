package com.redescooter.ses.web.ros.vo.restproductionorder.allocateorder;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @ClassNameAllocateEntrustResult
 * @Description
 * @Author Aleks
 * @Date2020/10/26 20:11
 * @Version V1.0
 **/
@ApiModel(value = "调拨单关联的委托单",description = "调拨单关联的委托单")
@Data
public class AllocateEntrustResult {

    @ApiModelProperty("委托单id")
    private Long id;

    @ApiModelProperty("委托单号")
    private String entrustNo;

    @ApiModelProperty("委托单状态,0：待发货，10：待签收，20：已签收")
    private Integer entrustStatus;

    @ApiModelProperty("创建时间")
    private Date createdTime;

    @ApiModelProperty("单据类型")
    private Integer orderType;

}
