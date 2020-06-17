package com.redescooter.ses.api.mobile.c.vo;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.annotation.Regexp;
import com.redescooter.ses.api.common.constant.RegexpConstant;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.mobile.c.exception.ValidationExceptionCode;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @ClassName:ScooterNgvEnter
 * @description: ScooterNgvEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/02/20 11:06
 */
@ApiModel(value = "车辆导航入参", description = "车辆导航入参")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@EqualsAndHashCode(callSuper = true)
public class ScooterNavigationEnter extends GeneralEnter {
    @ApiModelProperty(value = "事件 见 状态文档", required = true)
    @NotNull(code = ValidationExceptionCode.EVENT_IS_EMPTY, message = "事件 不能为空")
    private String event;

    @ApiModelProperty(value = "当前用户经度", required = true)
    @NotNull(code = ValidationExceptionCode.LNG_IS_EMPTY, message = "经度 不能为空")
    private String lng;

    @ApiModelProperty(value = "当前当前纬度", required = true)
    @NotNull(code = ValidationExceptionCode.LAT_IS_EMPTY, message = "维度 不能为空")
    private String lat;

    @ApiModelProperty(value = "是否启用蓝牙")
    private Boolean bluetoothCommunication = Boolean.FALSE;

    @ApiModelProperty(value = "行驶公里数，结束导航时传递 单位 m")
    @NotNull(code = ValidationExceptionCode.MILEAGE_IS_EMPTY,message = "距离为空")
    //@Regexp(value = RegexpConstant.number,code = ValidationExceptionCode.DATA_IS_ILLEGAL,message = "距离为空")
    private String mileage;

    @ApiModelProperty(value = "行驶公里数，结束导航时传递 单位 s")
    private Long duration;
}
