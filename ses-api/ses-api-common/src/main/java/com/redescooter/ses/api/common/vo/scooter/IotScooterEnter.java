package com.redescooter.ses.api.common.vo.scooter;

import java.math.BigDecimal;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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
    @NotNull
    private Long id;

    @ApiModelProperty(value = "事件")
    private String event;

    @ApiModelProperty(value = "当前用户经度")
    private BigDecimal longitude;

    @ApiModelProperty(value = "当前当前纬度")
    private BigDecimal latitude;

    @ApiModelProperty(value = "是否启用蓝牙")
    private Boolean bluetoothCommunication = Boolean.FALSE;

}
