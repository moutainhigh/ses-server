package com.redescooter.ses.web.ros.vo.monday.enter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @ClassName:MondayBookOrderEnter
 * @description: MondayBookOrderEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/08/14 16:48
 */
@ApiModel(value = "支付订单", description = "支付订单入参")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class MondayBookOrderEnter{
    
    @ApiModelProperty(value = "产品名号")
    private String producModeltName;

    @ApiModelProperty(value = "产品颜色")
    private String color;
    
    @ApiModelProperty(value = "数量")
    private int qty;
}
