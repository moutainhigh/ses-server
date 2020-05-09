package com.redescooter.ses.web.delivery.vo;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

/**
 * @ClassName:DriverScooterInforResult
 * @description: DriverScooterInforResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/01/06 16:31
 */
@ApiModel(value = "司机车辆详情", description = "司机车辆详情")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@EqualsAndHashCode(callSuper = true)
public class DriverScooterInforResult extends GeneralResult {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "车牌")
    private String licensePlate;

    @ApiModelProperty(value = "行驶公里数")
    private String mileage="0";

    @ApiModelProperty(value = "电量")
    private Integer battery;
}
