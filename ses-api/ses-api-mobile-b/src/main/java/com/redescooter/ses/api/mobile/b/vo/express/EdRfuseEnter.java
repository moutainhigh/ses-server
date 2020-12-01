package com.redescooter.ses.api.mobile.b.vo.express;

import com.redescooter.ses.api.common.annotation.MaximumLength;
import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.annotation.Regexp;
import com.redescooter.ses.api.common.constant.RegexpConstant;
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

    @ApiModelProperty(value = "拒绝原因")
    @NotNull(code = ValidationExceptionCode.REASON_IS_EMPTY, message = "拒绝原因为空")
    @MaximumLength(value = "200",code = ValidationExceptionCode.REASON_CHARACTER_IS_TOO_LONG,message = "原因字符过长")
    @Regexp(value = RegexpConstant.specialCharacters,code = ValidationExceptionCode.REASON_IS_ILLEGAL,message = "原因非法")
    private String reason;
}
