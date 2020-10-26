package com.redescooter.ses.web.ros.vo.restproductionorder.purchaseorder;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassNamePurchaseSaveOrUpdateEnter
 * @Description
 * @Author Aleks
 * @Date2020/10/23 19:33
 * @Version V1.0
 **/
@Data
@ApiModel(value = "采购单新增编辑入参",description = "采购单新增编辑入参")
public class PurchaseSaveOrUpdateEnter extends GeneralEnter {

    @ApiModelProperty(value = "主键id")
    private Long id;

    @ApiModelProperty(value = "关联的调拨单号")
    private String allocateNo;

    @ApiModelProperty(value = "关联的调拨单id")
    private Long allocateId;

    @ApiModelProperty(value = "采购单状态，0：草稿，10：待备货，20：备货中，30：待发货，40：待签收，50：已签收，60：已完成，70：已取消")
    private Integer purchaseStatus;

    @ApiModelProperty(value = "运输方式，1：海运，2：陆运，3：空运")
    private Integer transType;

    @ApiModelProperty(value = "收货人id")
    private Long consigneeUser;

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

    @ApiModelProperty(value = "联系人")
    private String contactUser;

    @ApiModelProperty(value = "联系电话")
    private String contactTelephone;

    @ApiModelProperty(value = "联系邮箱")
    private String contactMail;

    @ApiModelProperty(value = "发货人id")
    private Long consignorUser;

    @ApiModelProperty(value = "发货人电话")
    private String consignorTelephone;

    @ApiModelProperty(value = "发货人邮箱")
    private String consignorMail;

    @ApiModelProperty(value = "通知人id")
    private Long notifyUser;

    @ApiModelProperty(value = "通知人电话")
    private String notifyUserTelephone;

    @ApiModelProperty(value = "通知人邮箱")
    private String notifyUserMail;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "支付方式，1：月结")
    private Integer paymentType;

    @ApiModelProperty(value = "预计付款时间")
    private Date plannedPaymentTime;

    @ApiModelProperty(value = "付款周期")
    private Integer paymentDay;

    @ApiModelProperty(value = "实际付款时间")
    private Date paymentTime;

    @ApiModelProperty(value = "支付状态,1:未支付，2:已支付")
    private Integer paymentStatus;

    @ApiModelProperty(value = "采购合同")
    private String purchaseContract;

    @ApiModelProperty("调拨产品")
    private String st;

    @ApiModelProperty("调拨单类型,1:车辆，2:组装件，3:部件")
    private Integer classType;

}
