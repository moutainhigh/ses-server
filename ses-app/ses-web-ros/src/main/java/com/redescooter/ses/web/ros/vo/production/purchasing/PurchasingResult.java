package com.redescooter.ses.web.ros.vo.production.purchasing;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.tool.utils.DateUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * @ClassName:PurchasingResult
 * @description: PurchasingResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/18 17:00
 */
@ApiModel(value = "采购单列表", description = "采购单列表")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class PurchasingResult extends GeneralResult {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "合同编号")
    private String contractN;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "收货人名字")
    private String consigneeFirstName;

    @ApiModelProperty(value = "收货人名字")
    private String consigneeLastName;

    @ApiModelProperty(value = "收货人电话")
    private String consigneePhone;

    @ApiModelProperty(value = "收货人邮箱")
    private String consigneeEmail;

    @ApiModelProperty(value = "工厂Id")
    private Long factoryId;

    @ApiModelProperty(value = "工常名字")
    private String factoryName;

    @ApiModelProperty(value = "工厂联系人名字")
    private String factoryContactFirstName;

    @ApiModelProperty(value = "工厂联系人名字")
    private String factoryContactLastName;

    @ApiModelProperty(value = "联系人电话")
    private String contactPhone;

    @ApiModelProperty(value = "联系人邮箱")
    private String contactEmail;

    @ApiModelProperty(value = "总价格")
    private String totalPrice;

    @ApiModelProperty(value = "单位")
    private String unit;

    @ApiModelProperty(value = "付款类型")
    private String paymentType;

    @ApiModelProperty(value = "对帐日")
    @DateTimeFormat(pattern = DateUtil.DEFAULT_DATETIME_FORMAT)
    @JsonFormat(pattern = DateUtil.DEFAULT_DATETIME_FORMAT, timezone = DateUtil.UTC)
    private Date statementDate;

    @ApiModelProperty(value = "天数")
    private Integer days;

    @ApiModelProperty(value = "分期总数")
    private Integer stagTotal;

    @ApiModelProperty(value = "支付分期数")
    private Integer paidstagNum;

    @ApiModelProperty(value = "部件数量")
    private Integer partsQty;

    @ApiModelProperty(value = "创建时间")
    @DateTimeFormat(pattern = DateUtil.DEFAULT_DATETIME_FORMAT)
    @JsonFormat(pattern = DateUtil.DEFAULT_DATETIME_FORMAT, timezone = DateUtil.UTC)
    private Date createdTime;

    @ApiModelProperty(value = "支付记录详情")
    private List<PaymentItemDetailResult> paymentItemDetailResultList;
}
