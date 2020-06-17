package com.redescooter.ses.web.ros.vo.production.assembly;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.annotation.Regexp;
import com.redescooter.ses.api.common.constant.DateConstant;
import com.redescooter.ses.api.common.constant.RegexpConstant;
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
    @NotNull(code = com.redescooter.ses.api.common.exception.ValidationExceptionCode.ID_IS_EMPTY, message = "id为空")
    private Long id;

    @ApiModelProperty(value = "加工费比例 百分数扩大100倍 传值（无需取整后台会自动进行保留两位、四舍五入，详情中也是扩大100倍 回传数据） eg：10.367", required = true)
    @NotNull(code = ValidationExceptionCode.PROCESSCOST_RATIO_IS_EMPTY, message = "加工费为空")
    private BigDecimal processCost;

    @ApiModelProperty(value = "总价格（无需取整后台会自动进行保留两位、四舍五入）", required = true)
    private BigDecimal totalPrice;

    @ApiModelProperty(value = "付款方式", required = true)
    @NotNull(code = ValidationExceptionCode.PAYMENT_TYPE_IS_EMPTY, message = "付款方式为空")
    //@Regexp(value = RegexpConstant.specialCharacters,code = ValidationExceptionCode.PAYMENT_TYPE_ILLEGAL_CHARACTER,message = "付款方式存在非法字符")
    private String paymentType;

    @ApiModelProperty(value = "付款时间")
    @DateTimeFormat(pattern = DateConstant.DEFAULT_DATETIME_FORMAT)
    @JsonFormat(pattern = DateConstant.DEFAULT_DATETIME_FORMAT, timezone = DateConstant.UTC)
    private Date statementdate;

    @ApiModelProperty(value = "天数")
    private Integer days;

    @ApiModelProperty(value = "备注")
    //@Regexp(value = RegexpConstant.specialCharacters,code = ValidationExceptionCode.REMARK_ILLEGAL_CHARACTER,message = "备注存在非法字符")
    private String remark;

    @ApiModelProperty(value = "付款周期，格式：estimatedPaymentDate：2020-3-2 00：00：00，paymentRatio:20.2，price:20.1（无需取整后台会自动进行保留两位、四舍五入）,remark:123")
//    @NotNull(code = ValidationExceptionCode.PAYMENTINFO_IS_EMPTY, message = "付款信息")
    private String paymentInfoList;
}
