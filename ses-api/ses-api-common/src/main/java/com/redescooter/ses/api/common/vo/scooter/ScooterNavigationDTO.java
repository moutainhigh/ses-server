package com.redescooter.ses.api.common.vo.scooter;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.exception.ValidationExceptionBaseCode;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 车辆开关导航入参 DTO
 * @author assert
 * @date 2020/11/18 18:36
 */
@Data
@ApiModel(value = "车辆导航入参", description = "车辆导航入参")
public class ScooterNavigationDTO extends GeneralEnter {

    @ApiModelProperty(value = "事件 1-开始导航 2-结束导航", required = true)
    @NotNull(code = ValidationExceptionBaseCode.EVENT_IS_EMPTY, message = "事件 不能为空")
    private String event;

    @ApiModelProperty(value = "当前用户经度", required = true)
    @NotNull(code = ValidationExceptionBaseCode.LNG_IS_EMPTY, message = "经度 不能为空")
    private String lng;

    @ApiModelProperty(value = "当前当前纬度", required = true)
    @NotNull(code = ValidationExceptionBaseCode.LAT_IS_EMPTY, message = "维度 不能为空")
    private String lat;

    @ApiModelProperty(value = "行驶公里数，结束导航时传递 单位/m")
    private String mileage;

    @ApiModelProperty(value = "耗时时间，结束导航时传递 单位/s")
    private Long duration;
}
