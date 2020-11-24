package com.redescooter.ses.web.ros.vo.restproductionorder.allocateorder;

import com.redescooter.ses.api.common.annotation.MaximumLength;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.ros.exception.ValidationExceptionCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassNameAllocateOrderSaveEnter
 * @Description
 * @Author Aleks
 * @Date2020/10/23 14:37
 * @Version V1.0
 **/
@Data
@ApiModel(value = "调拨单新增编辑入参", description = "调拨单新增编辑入参")
public class AllocateOrderOrUpdateSaveEnter extends GeneralEnter {

    @ApiModelProperty("主键id")
    private Long Id;

    @ApiModelProperty("发货仓库id")
    private Long shipWh;

    @ApiModelProperty("接受仓库id")
    private Long receiptWh;

    @ApiModelProperty("收货人id")
    private Long consigneeUser;

    @ApiModelProperty(value = "收货人国家编码如+86")
    private String consigneeCountryCode;

    @ApiModelProperty("收货人电话")
    private String consigneeUserTelephone;

    @ApiModelProperty("收货人邮箱")
    private String consigneeUserMail;

    @ApiModelProperty("收货地址")
    private String consigneeAddress;

    @ApiModelProperty("收获邮编")
    private String consigneePostCode;

    @ApiModelProperty("通知人id")
    private Long  notifyUser;

    @ApiModelProperty("通知人名称")
    private String  notifyUserName;

    @ApiModelProperty("通知人国家编码如+86")
    private String notifyCountryCode;

    @ApiModelProperty("通知人电话")
    private String notifyUserTelephone;

    @ApiModelProperty("通知人邮箱")
    private String  notifyUserMail;

    @ApiModelProperty("备注")
    @MaximumLength(value = "200",code = ValidationExceptionCode.REMARK_ILLEGAL,message = "原因字符过长")
    private String remark;

    @ApiModelProperty("调拨产品")
    private String st;

    @ApiModelProperty("调拨单类型,1:车辆，2:组装件，3:部件")
    private Integer classType;

}
