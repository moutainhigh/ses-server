package com.redescooter.ses.web.ros.vo.restproductionorder.consignorder;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 *  @author: alex
 *  @Date: 2020/10/29 13:54
 *  @version：V ROS 1.8.3
 *  @Description:
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SaveConsignEnter extends GeneralEnter {
    @ApiModelProperty(value = "主键id")
    private Long id;

    @ApiModelProperty(value = "关联的发货单id")
    private Long invoiceId;

    @ApiModelProperty(value = "发货单号")
    private String invoiceNo;

    @ApiModelProperty(value = "委托单类型，1：整车，2：组装件，3：部件")
    private Integer entrustType;

    @ApiModelProperty(value = "发货数量")
    private Integer consignorQty;

    @ApiModelProperty(value = "交货日期")
    private Date deliveryDate;

    @ApiModelProperty(value = "运输方式，1：海运，2：陆运，3：空运")
    private Integer transType;

    @ApiModelProperty(value = "收货人id")
    private Long consigneeUser;

    @ApiModelProperty(value = "收货人国家编码如+86")
    private String consigneeCountryCode;

    @ApiModelProperty(value = "收货人电话")
    private String consigneeUserTelephone;

    @ApiModelProperty(value = "收货人邮箱")
    private String consigneeUserMail;

    @ApiModelProperty(value = "收获地址")
    private String consigneeAddress;

    @ApiModelProperty(value = "收货地邮编")
    private String consigneePostCode;

    @ApiModelProperty(value = "发货人id")
    private Long consignorUser;

    @ApiModelProperty(value = "发货人国家编码如+86")
    private String consignorCountryCode;

    @ApiModelProperty(value = "发货人电话")
    private String consignorTelephone;

    @ApiModelProperty(value = "发货人邮箱")
    private String consignorMail;

    @ApiModelProperty(value = "通知人id")
    private Long notifyUser;

    @ApiModelProperty(value = "通知人名称")
    private String notifyUserName;

    @ApiModelProperty(value = "通知人国家编码如+86")
    private String notifyCountryCode;

    @ApiModelProperty(value = "通知人电话")
    private String notifyUserTelephone;

    @ApiModelProperty(value = "通知人邮箱")
    private String notifyUserMail;

    @ApiModelProperty(value = "备注")
    private String remark;
}
