package com.redescooter.ses.web.ros.vo.production.assembly;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.redescooter.ses.api.common.constant.DateConstant;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.web.ros.vo.production.PaymentItemDetailResult;
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
 * @ClassName:AssemblyResult
 * @description: AssemblyResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/30 13:18
 */
@ApiModel(value = "组装单列表", description = "组装单列表")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class AssemblyResult extends GeneralResult {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "组装单编号")
    private String contractN;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "工厂Id")
    private Long factoryId;

    @ApiModelProperty(value = "工厂名字")
    private String factoryName;

    @ApiModelProperty(value = "联系人名字")
    private String contactFirstName;

    @ApiModelProperty(value = "联系人名字")
    private String contactLastName;

    @ApiModelProperty(value = "联系人名字")
    private String contactFullName;

    @ApiModelProperty(value = "联系人电话")
    private String contactPhone;

    @ApiModelProperty(value = "联系人电话代码")
    private String contactPhoneCountryCode;

    @ApiModelProperty(value = "联系人邮箱")
    private String contactEmail;

    @ApiModelProperty(value = "数量")
    private Integer qty;

    @ApiModelProperty(value = "创建时间")
    private Date createdTime;

    @ApiModelProperty(value = "收货人")
    private Long consigneeId;

    @ApiModelProperty(value = "收货人")
    private String consigneeFirstName;

    @ApiModelProperty(value = "收货人")
    private String consigneeLastName;

    @ApiModelProperty(value = "收货人")
    private String consigneeFullName;

    @ApiModelProperty(value = "收货人电话")
    private String consigneePhone;

    @ApiModelProperty(value = "收货人电话代码")
    private String consigneePhoneCountryCode;

    @ApiModelProperty(value = "收货人邮箱")
    private String consigneeEmail;

    @ApiModelProperty(value = "总价格")
    private String totalPrice;

    @ApiModelProperty(value = "付款类型")
    private String paymentType;

    @ApiModelProperty(value = "对帐日")
    @DateTimeFormat(pattern = DateConstant.DEFAULT_DATETIME_FORMAT)
    @JsonFormat(pattern = DateConstant.DEFAULT_DATETIME_FORMAT, timezone = DateConstant.UTC)
    private Date statementDate;

    @ApiModelProperty(value = "天数")
    private Integer days;

    @ApiModelProperty(value = "分期总数")
    private Integer stagTotal;

    @ApiModelProperty(value = "支付分期数")
    private Integer paidstagNum;

    @ApiModelProperty(value = "支付记录详情")
    private List<PaymentItemDetailResult> paymentItemDetailResultList;
}
