package com.redescooter.ses.web.ros.vo.restproductionorder.allocateorder;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassNameAllocateNoDataResult
 * @Description
 * @Author Aleks
 * @Date2020/10/23 20:49
 * @Version V1.0
 **/
@Data
@ApiModel(value = "调拨单号下拉出参",description = "调拨单号下拉出参")
public class AllocateNoDataResult extends GeneralResult {

    @ApiModelProperty(value = "调拨单id")
    private Long allocateId;

    @ApiModelProperty(value = "调拨单号")
    private String allocateNo;

    @ApiModelProperty(value = "收货人id")
    private Long consigneeUser;

    @ApiModelProperty(value = "收货人名称")
    private String consigneeUserName;

    @ApiModelProperty(value = "收货人地址")
    private String consigneeAddress;

    @ApiModelProperty(value = "收货人电话")
    private String consigneeUserTelephone;

    @ApiModelProperty(value = "收货人邮箱")
    private String consigneeUserMail;


}
