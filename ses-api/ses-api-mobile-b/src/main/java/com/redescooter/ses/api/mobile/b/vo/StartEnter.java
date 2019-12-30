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
 * @ClassName:StartEnter
 * @description: StartEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/29 15:55
 */
@ApiModel(value = "开始订单入参", description = "开始订单入参")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class StartEnter extends GeneralEnter {

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
