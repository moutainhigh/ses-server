package com.redescooter.ses.web.ros.vo.production.assembly;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.constant.DateConstant;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.ros.exception.ValidationExceptionCode;
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
 * @ClassName:SetPaymentAssemblyEnter
 * @description: SetPaymentAssemblyEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/30 14:33
 */
@ApiModel(value = "组装单支付信息保存", description = "组装单支付信息保存")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class SetPaymentAssemblyEnter extends GeneralEnter {

    @ApiModelProperty(value = "id", required = true)
    private Long id;

    @ApiModelProperty(value = "加工费", required = true)
    private int processCost;

    @ApiModelProperty(value = "付款方式", required = true)
    private BigDecimal totalPrice;

    @ApiModelProperty(value = "付款方式", required = true)
    @NotNull(code = ValidationExceptionCode.PAYMENT_TYPE_IS_EMPTY, message = "付款方式为空")
    private String paymentType;

    @ApiModelProperty(value = "付款时间")
    @DateTimeFormat(pattern = DateConstant.DEFAULT_DATETIME_FORMAT)
    @JsonFormat(pattern = DateConstant.DEFAULT_DATETIME_FORMAT, timezone = DateConstant.UTC)
    private Date statementdate;

    @ApiModelProperty(value = "天数")
    private Integer days;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "付款周期，格式：estimatedPaymentDate：2020-3-2 00：00：00，paymentRatio:20.2，price:20.1,remark:123")
//    @NotNull(code = ValidationExceptionCode.PAYMENTINFO_IS_EMPTY, message = "付款信息")
    private String paymentInfoList;
}
