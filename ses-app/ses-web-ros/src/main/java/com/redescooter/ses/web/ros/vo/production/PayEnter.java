package com.redescooter.ses.web.ros.vo.production;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.annotation.Regexp;
import com.redescooter.ses.api.common.constant.DateConstant;
import com.redescooter.ses.api.common.constant.RegexpConstant;
import com.redescooter.ses.api.common.exception.ValidationExceptionBaseCode;
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
 * @ClassName:PayEnter
 * @description: PayEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/19 11:18
 */
@ApiModel(value = "支付入参", description = "支付入参")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class PayEnter extends GeneralEnter {

    @ApiModelProperty(value = "id", required = true)
    @NotNull(code = ValidationExceptionBaseCode.ID_IS_EMPTY, message = "Id为空")
    private Long id;

    @ApiModelProperty(value = "金额", required = true)
    @NotNull(code = ValidationExceptionCode.AMOUNT_IS_EMPTY, message = "金额为空")
    private BigDecimal amount;

    @ApiModelProperty(value = "实际支付时间", required = true)
    @NotNull(code = ValidationExceptionCode.ACTUAL_PAYMENT_DATE_IS_EMPTY, message = "实际支付时间为空")
    @DateTimeFormat(pattern = DateConstant.DEFAULT_DATETIME_FORMAT)
    @JsonFormat(pattern = DateConstant.DEFAULT_DATETIME_FORMAT, timezone = DateConstant.UTC)
    private Date actualPaymentDate;

    @ApiModelProperty(value = "发票", required = true)
    @NotNull(code = ValidationExceptionCode.INVOICE_IS_EMPTY, message = "发票为空")
//    @Regexp(value = RegexpConstant.specialCharacters,code = ValidationExceptionCode.INVOICENUM_ILLEGAL_CHARACTER,message = "发票存在非法字符")
    private String invoicePicture;

    @ApiModelProperty(value = "发票号", required = true)
    @NotNull(code = ValidationExceptionCode.INVOICE_NUM_IS_EMPTY, message = "发票号为空")
    @Regexp(value = RegexpConstant.specialCharacters,code = ValidationExceptionCode.INVOICENUM_ILLEGAL_CHARACTER,message = "发票号存在非法字符")
    private String invoiceNum;
}
