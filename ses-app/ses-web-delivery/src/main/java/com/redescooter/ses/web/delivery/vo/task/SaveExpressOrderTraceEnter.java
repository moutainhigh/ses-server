package com.redescooter.ses.web.delivery.vo.task;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.formula.functions.T;

import io.swagger.annotations.*;

/**
 * @ClassName:SaveExpressOrderTraceEnter
 * @description: SaveExpressOrderTraceEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/01/18 13:35
 */
@ApiModel(value = "保存order订单记录", description = "保存order订单记录")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class SaveExpressOrderTraceEnter<BaseExpressOrderEnter> extends GeneralEnter {

    @ApiModelProperty(value = "快递订单，分配时不为空，其他事件时为空")
    private Long expressDeliveryId;

    @ApiModelProperty(value = "order事件")
    private String orderEvent;

    @ApiModelProperty(value = "数据对象")
    private T t;
}
