package com.redescooter.ses.api.mobile.b.vo.express;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.mobile.b.exception.ValidationExceptionCode;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@ApiModel(value = "快递拒绝订单入参", description = "快递拒绝订单入参")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class EdRfuseEnter extends GeneralEnter {
    @ApiModelProperty(value = "主键")
    @NotNull
    private Long id;

    @ApiModelProperty(value = "是否启动蓝牙模式")
    @NotNull
    private Boolean bluetoothCommunication = Boolean.FALSE;

    @ApiModelProperty(value = "经度")
    @NotNull(code = ValidationExceptionCode.LAT_IS_EMPTY, message = "经度为u空")
    private String lat;

    @ApiModelProperty(value = "纬度")
    @NotNull(code = ValidationExceptionCode.LNG_IS_EMPTY, message = "经度为空")
    private String lng;
}
