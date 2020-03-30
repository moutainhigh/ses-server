package com.redescooter.ses.web.ros.vo.production.purchasing;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.redescooter.ses.api.common.constant.DateConstant;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @ClassName:InstallmentPaymentEnter
 * @description: InstallmentPaymentEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/18 17:35
 */
@ApiModel(value = "分期付款", description = "分期付款")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class InstallmentPaymentEnter extends GeneralEnter {

    @ApiModelProperty(value = "付款时间")
    @DateTimeFormat(pattern = DateConstant.DEFAULT_DATETIME_FORMAT)
    @JsonFormat(pattern = DateConstant.DEFAULT_DATETIME_FORMAT, timezone = DateConstant.UTC)
    private Date estimatedPaymentDate;

    @ApiModelProperty(value = "付款比例")
    private String paymentRatio;

    @ApiModelProperty(value = "金额")
    private String price;

    @ApiModelProperty(value = "备注")
    private String remark;
}
