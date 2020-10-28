package com.redescooter.ses.web.ros.vo.restproductionorder.purchaseorder;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.optrace.OpTraceResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @ClassNamePurchaseDetailResult
 * @Description
 * @Author Aleks
 * @Date2020/10/23 20:07
 * @Version V1.0
 **/
@Data
@ApiModel(value = "采购单详情出参", description = "采购详情表出参")
public class PurchaseDetailResult extends GeneralResult {

    @ApiModelProperty(value = "主键id")
    private Long id;

    @ApiModelProperty(value = "采购单号")
    private String purchaseNo;

    @ApiModelProperty(value = "采购单状态，0：草稿，10：待备货，20：备货中，30：待发货，40：待签收，50：已签收，60：已完成，70：已取消")
    private Integer purchaseStatus;

    @ApiModelProperty(value = "关联的调拨单号")
    private String allocateNo;

    @ApiModelProperty(value = "关联的调拨单id")
    private Long allocateId;

    @ApiModelProperty(value = "运输方式，1：海运，2：陆运，3：空运")
    private Integer transType;

    @ApiModelProperty(value = "工厂id")
    private Long factoryId;

    @ApiModelProperty(value = "工厂名称")
    private String factoryName;

    @ApiModelProperty(value = "交货日期")
    private Date deliveryDate;

    @ApiModelProperty(value = "联系人id")
    private Long contactUser;

    @ApiModelProperty(value = "联系人名称")
    private String contactUserName;

    @ApiModelProperty(value = "联系人国家编码如+86")
    private String contactCountryCode;

    @ApiModelProperty(value = "联系电话")
    private String contactTelephone;

    @ApiModelProperty(value = "联系邮箱")
    private String contactMail;

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

    @ApiModelProperty(value = "收货人id")
    private Long consigneeUser;

    @ApiModelProperty(value = "收货人名称")
    private String consigneeUserName;

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

    @ApiModelProperty(value = "发货人名称")
    private String consignorUserName;

    @ApiModelProperty(value = "发货人国家编码+86")
    private String consignorCountryCode;

    @ApiModelProperty(value = "发货人电话")
    private String consignorTelephone;

    @ApiModelProperty(value = "发货人邮箱")
    private String consignorMail;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty(value = "支付方式，1：月结")
    private Integer paymentType;

    @ApiModelProperty(value = "预计付款时间")
    private Date plannedPaymentTime;

    @ApiModelProperty(value = "付款周期")
    private Integer paymentDay;

    @ApiModelProperty(value = "采购合同")
    private String purchaseContract;

    @ApiModelProperty(value = "采购单类型，1：整车，2：组装件，3：部件")
    private Integer purchaseType;

    @ApiModelProperty("车辆采购产品详情")
    private List<PurchaseScooterDetailResult> scooters;

    @ApiModelProperty("组装件采购产品详情")
    private List<PurchaseCombineDetailResult> combins;

    @ApiModelProperty("部件采购产品详情")
    private List<PurchasePartsDetailResult> parts;

    @ApiModelProperty("操作动态")
    private List<OpTraceResult> opTraces;

    // todo 关联的单据（调拨单、发货单、委托单）

}
