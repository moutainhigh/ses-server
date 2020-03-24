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

import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassName:SavePurchasingPaymentEnter
 * @description: SavePurchasingPaymentEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/24 10:54
 */
@ApiModel(value = "保存采购单付款方式入参", description = "保存采购单付款方式入参")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class SavePurchasingPaymentEnter extends GeneralEnter {

    @ApiModelProperty(value = "付款时间")
    @DateTimeFormat(pattern = DateUtil.DEFAULT_DATETIME_FORMAT)
    @JsonFormat(pattern = DateUtil.DEFAULT_DATETIME_FORMAT, timezone = DateUtil.UTC)
    private Date paymentTime;

    @ApiModelProperty(value = "价格")
    private BigDecimal price;

    @ApiModelProperty(value = "百分比")
    private Integer ratio;

    @ApiModelProperty(value = "累加多少天")
    private Integer days;

    @ApiModelProperty(value = "备注")
    private String remark;
}
