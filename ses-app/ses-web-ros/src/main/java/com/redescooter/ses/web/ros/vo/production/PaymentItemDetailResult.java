package com.redescooter.ses.web.ros.vo.production;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.redescooter.ses.api.common.constant.DateConstant;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassName:PaymentItemResult
 * @description: PaymentItemResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/19 10:05
 */
@ApiModel(value = "付款条目详情", description = "付款条目详情")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class PaymentItemDetailResult extends GeneralResult {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "付款类型")
    private String paymentType;

    @ApiModelProperty(value = "业务Id")
    private Long bizId;

    @ApiModelProperty(value = "价格")
    private BigDecimal amount;

    @ApiModelProperty(value = "付款比例")
    private BigDecimal paymentRatio;

    @ApiModelProperty(value = "发票单号")
    private String invoiceNum;

    @ApiModelProperty(value = "发票图片")
    private String invoicePicture;

    @ApiModelProperty(value = "预计付款日期")
    @DateTimeFormat(pattern = DateConstant.DEFAULT_DATETIME_FORMAT)
    @JsonFormat(pattern = DateConstant.DEFAULT_DATETIME_FORMAT, timezone = DateConstant.UTC)
    private Date estimatedPaymentDate;

    @ApiModelProperty(value = "创建时间")
    @DateTimeFormat(pattern = DateConstant.DEFAULT_DATE_FORMAT)
    @JsonFormat(pattern = DateConstant.DEFAULT_DATE_FORMAT)
    private Date plannedPaymentTime;

    @ApiModelProperty("年份")
    private String year;

    @ApiModelProperty("月份")
    private String month;

    @ApiModelProperty("日期")
    private String day;

    @ApiModelProperty(value = "天数")
    private Integer dayNum;

    @ApiModelProperty(value = "时间付款日期")
    @DateTimeFormat(pattern = DateConstant.DEFAULT_DATETIME_FORMAT)
    @JsonFormat(pattern = DateConstant.DEFAULT_DATETIME_FORMAT, timezone = DateConstant.UTC)
    private Date actualPaymentDate;

    @ApiModelProperty(value = "备注")
    private String remark;
}
