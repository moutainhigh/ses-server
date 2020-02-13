package com.redescooter.ses.web.delivery.vo;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.delivery.exception.ValidationExceptionCode;
import lombok.*;

import io.swagger.annotations.*;
@ApiModel(value = "车辆列表", description = "")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class ScooterListEnter extends GeneralEnter {
    @ApiModelProperty(value = "车辆型号Id，详情 见 TAPD")
    @NotNull(code = ValidationExceptionCode.ID_IS_EMPTY,message = "Id 不为空")
    private String modelId;
}
