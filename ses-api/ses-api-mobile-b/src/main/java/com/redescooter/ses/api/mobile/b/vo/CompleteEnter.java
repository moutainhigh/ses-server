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
 * @ClassName:CompleteEnter
 * @description: CompleteEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/29 17:22
 */
@ApiModel(value = "完成订单入参", description = "完成订单入参")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@Data
public class CompleteEnter extends GeneralEnter {

    @ApiModelProperty(value = "id")
    @NotNull
    private Long id;

    @ApiModelProperty(value = "是否启动蓝牙")
    private Boolean bluetoothCommunication = Boolean.FALSE;

    @ApiModelProperty(value = "经度")
    @NotNull(code = ValidationExceptionCode.LAT_IS_EMPTY, message = "经度为空")
    private String lat;

    @ApiModelProperty(value = "纬度")
    @NotNull(code = ValidationExceptionCode.LNG_IS_EMPTY, message = "纬度为空")
    private String lng;
}
