package com.redescooter.ses.api.mobile.b.vo;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @ClassName:SaveDeliveryTraceEnter
 * @description: SaveDeliveryTraceEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/29 16:12
 */
@ApiModel(value = "保存订单记录", description = "保存订单记录")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class SaveDeliveryTraceEnter<T> extends GeneralEnter {


    @ApiModelProperty(value = "事件")
    private String event;

    @ApiModelProperty(value = "原因")
    private String reason;

    @ApiModelProperty(value = "业务对象")
    private T t;
}
