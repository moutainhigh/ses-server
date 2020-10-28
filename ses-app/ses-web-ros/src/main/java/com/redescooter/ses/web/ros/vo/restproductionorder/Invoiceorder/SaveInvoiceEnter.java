package com.redescooter.ses.web.ros.vo.restproductionorder.Invoiceorder;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 *  @author: alex
 *  @Date: 2020/10/26 15:57
 *  @version：V ROS 1.8.3
 *  @Description:
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SaveInvoiceEnter extends GeneralEnter {

    private Long id;


    @ApiModelProperty(value = "关联的采购单号")
    private String purchaseNo;

    @ApiModelProperty(value = "关联的采购单id")
    private Long purchaseId;

    @ApiModelProperty(value = "发货单号")
    private String invoiceNo;

    @ApiModelProperty(value = "发货单状态，0：待备货，10：备货中，20：待装车，30：待发货，40：待签收，50：已签收，60：已取消")
    private Integer invoiceStatus;

    @ApiModelProperty(value = "发货单类型，1：整车，2：组装件，3：部件")
    private Integer invoiceType;

    @ApiModelProperty(value = "发货数量（应发数量）")
    private Integer invoiceQty;

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

    @ApiModelProperty(value = "工厂id")
    private Long factoryId;

    @ApiModelProperty(value = "交货日期")
    private Date deliveryDate;

    @ApiModelProperty(value = "发货人id")
    private Long consignorUser;

    @ApiModelProperty(value = "发货人国家编码+86")
    private String consignorCountryCode;

    @ApiModelProperty(value = "发货人电话")
    private String consignorTelephone;

    @ApiModelProperty(value = "发货人邮箱")
    private String consignorMail;

    @ApiModelProperty(value = "通知人")
    private Long notifyUserName;

    @ApiModelProperty(value = "通知人国家编码如+86")
    private String notifyCountryCode;

    @ApiModelProperty(value = "通知人电话")
    private String notifyUserTelephone;

    @ApiModelProperty(value = "通知人邮箱")
    private String notifyUserMail;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "创建人")
    private Long createdBy;

    @ApiModelProperty(value = "创建时间")
    private Date createdTime;

    @ApiModelProperty(value = "更新人")
    private Long updatedBy;

    @ApiModelProperty(value = "更新时间")
    private Date updatedTime;
}
