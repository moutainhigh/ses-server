package com.redescooter.ses.web.delivery.vo.edorder;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;

import com.redescooter.ses.web.delivery.exception.ValidationExceptionCode;
import lombok.*;

import io.swagger.annotations.*;
@ApiModel(value = "重新分配订单", description = "重新分配订单")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class ChanageExpressOrderEnter extends GeneralEnter {

    @ApiModelProperty(value = "订单Id")
    @NotNull(code = ValidationExceptionCode.ID_IS_EMPTY,message = "Id 不为空")
    private Long id;

    @ApiModelProperty(value = "司机Id")
    @NotNull(code = ValidationExceptionCode.ID_IS_EMPTY,message = "id 不为空")
    private Long driverId;

    @ApiModelProperty(value = "任务时间")
    @NotNull(code = ValidationExceptionCode.TASK_TIME,message = "任务时间 为空")
    private String taskTime;
}
