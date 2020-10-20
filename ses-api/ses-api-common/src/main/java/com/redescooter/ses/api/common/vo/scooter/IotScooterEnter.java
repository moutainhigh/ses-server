package com.redescooter.ses.api.common.vo.scooter;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.exception.ValidationExceptionBaseCode;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @ClassName:IotScooterEnter
 * @description: IotScooterEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/26 20:31
 */
@ApiModel(value = "IOT入参", description = "IOT入参")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class IotScooterEnter extends GeneralEnter {

    @ApiModelProperty(value = "scooter Id")
    @NotNull(code = ValidationExceptionBaseCode.ID_IS_EMPTY,message = "Id 不能为空")
    private Long id;

    @ApiModelProperty(value = "事件")
    @NotNull(code = ValidationExceptionBaseCode.EVENT_IS_EMPTY,message = "事件 不能为空")
    private String event;

    @ApiModelProperty(value = "当前用户经度")
    @NotNull(code = ValidationExceptionBaseCode.LNG_IS_EMPTY,message = "经度 不能为空")
    private BigDecimal longitude;

    @ApiModelProperty(value = "当前当前纬度")
    @NotNull(code = ValidationExceptionBaseCode.LAT_IS_EMPTY,message = "维度 不能为空")
    private BigDecimal latitude;

    @ApiModelProperty(value = "是否启用蓝牙")
    private Boolean bluetoothCommunication = Boolean.FALSE;

}
