package com.redescooter.ses.web.ros.vo.production.purchasing;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
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
    private Long id;

    @ApiModelProperty(value = "金额", required = true)
    private String amount;

    @ApiModelProperty(value = "实际支付时间", required = true)
    @DateTimeFormat(pattern = DateUtil.DEFAULT_DATETIME_FORMAT)
    @JsonFormat(pattern = DateUtil.DEFAULT_DATETIME_FORMAT, timezone = DateUtil.UTC)
    private Date actualPaymentDate;

    @ApiModelProperty(value = "发票", required = true)
    private String invoicePicture;

    @ApiModelProperty(value = "发票号", required = true)
    private String invoiceNum;
}
