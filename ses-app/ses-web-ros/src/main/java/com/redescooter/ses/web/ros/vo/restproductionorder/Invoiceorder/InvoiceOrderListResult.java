package com.redescooter.ses.web.ros.vo.restproductionorder.Invoiceorder;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 *  @author: alex
 *  @Date: 2020/10/23 13:30
 *  @version：V ROS 1.8.3
 *  @Description:
 */
@ApiModel(value = "采购单列表", description = "采购单列表")
@Data
@EqualsAndHashCode(callSuper = true)
public class InvoiceOrderListResult extends GeneralResult {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "采购单单号")
    private String invoiceNo;

    @ApiModelProperty(value = "采购单状态")
    private Integer invoiceStatus;

    @ApiModelProperty(value = "采购单类型")
    private Integer invoiceType;

    @ApiModelProperty(value = "采购数量")
    private Integer invoiceQty;

    @ApiModelProperty(value = "关联单据单号")
    private Long purchaseId;

    @ApiModelProperty(value = "关联单据单号")
    private String purchaseNo;

    @ApiModelProperty(value = "到期交付时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Date deliveryDate;

    @ApiModelProperty(value = "收货人Id")
    private Long receiverId;

    @ApiModelProperty(value = "收货人名称")
    private String receiverFirstName;

    @ApiModelProperty(value = "收货人名称")
    private String receiverLastName;

    @ApiModelProperty(value = "收货人电话国家代码")
    private String receiverCountryCode;

    @ApiModelProperty(value = "收货人电话")
    private String receiverTelephone;

    @ApiModelProperty(value = "收货人邮箱")
    private String receiverMail;

    @ApiModelProperty(value = "创建人Id")
    private Long createById;

    @ApiModelProperty(value = "创建人名称")
    private String createByFirstName;

    @ApiModelProperty(value = "创建人名称")
    private String createByLastName;

    @ApiModelProperty(value = "创建时间")
    private Date createByDate;
}
