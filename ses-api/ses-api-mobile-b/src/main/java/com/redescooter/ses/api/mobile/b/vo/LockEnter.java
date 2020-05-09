package com.redescooter.ses.api.mobile.b.vo;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.mobile.b.exception.ValidationExceptionCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @ClassName:LockEnter
 * @description: LockEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/30 13:41
 */
@ApiModel(value = "", description = "")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
public class LockEnter extends GeneralEnter {

    @ApiModelProperty(value = "事件 1 lock,2 unlock")
    @NotNull(code = ValidationExceptionCode.EVENT_IS_EMPTY, message = "事件为空")
    private String event;

    @ApiModelProperty(value = "当前用户纬度", hidden = true)
    @NotNull(code = ValidationExceptionCode.LNG_IS_EMPTY, message = "纬度为空")
    private String lng;

    @ApiModelProperty(value = "当前用户经度", hidden = true)
    @NotNull(code = ValidationExceptionCode.LAT_IS_EMPTY, message = "经度为空")
    private String lat;

    @ApiModelProperty(value = "是否启用蓝牙")
    private Boolean bluetoothCommunication = Boolean.FALSE;
}
