package com.redescooter.ses.web.ros.vo.restproductionorder.purchass;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import lombok.*;

import io.swagger.annotations.*;

import java.util.Date;

/**
 * @ClassName:SavePurchasPaymentEnter
 * @description: SavePurchasPaymentEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/11/11 17:38 
 */
@ApiModel(value = "采购单付款节点", description = "采购单付款节点")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
public class SavePurchasPaymentEnter extends GeneralEnter {
    @ApiModelProperty(value = "付款方式")
    private int paymentType;

    @ApiModelProperty(value = "日期")
    private Date date;

    @ApiModelProperty(value = "天数")
    private int days;

    @ApiModelProperty(value = "百分比")
    private int percentage;

    @ApiModelProperty(value = "金额")
    private String amount;
}
