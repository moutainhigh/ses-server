package com.redescooter.ses.web.delivery.vo.task;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.delivery.exception.ValidationExceptionCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;
@ApiModel(value = "司机大订单构造对象", description = "司机大订单构造对象")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class DriverTaskEnter extends GeneralEnter {

    @ApiModelProperty(value = "小订单id")
    @NotNull(code = ValidationExceptionCode.ID_IS_EMPTY,message = "Id 为空")
    private List<Long> ids;

    @ApiModelProperty(value = "司机Id")
    @NotNull(code = ValidationExceptionCode.ID_IS_EMPTY,message = "Id 为空")
    private Long diverId;
}
