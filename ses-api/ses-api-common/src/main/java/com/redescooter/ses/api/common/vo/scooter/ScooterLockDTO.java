package com.redescooter.ses.api.common.vo.scooter;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.exception.ValidationExceptionBaseCode;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 车辆锁操作入参 DTO
 * @author assert
 * @date 2020/11/17 17:53
 */
@Data
@ApiModel(value = "车辆锁操作入参")
@EqualsAndHashCode(callSuper = true)
public class ScooterLockDTO extends GeneralEnter {

    @ApiModelProperty(value = "事件 1-lock,2 unlock")
    @NotNull(code = ValidationExceptionBaseCode.EVENT_IS_EMPTY, message = "事件不能为空")
    private String event;

    @ApiModelProperty(value = "当前用户纬度")
    @NotNull(code = ValidationExceptionBaseCode.LNG_IS_EMPTY, message = "纬度不能为空")
    private String lng;

    @ApiModelProperty(value = "当前用户经度")
    @NotNull(code = ValidationExceptionBaseCode.LAT_IS_EMPTY, message = "经度不能为空")
    private String lat;

}
