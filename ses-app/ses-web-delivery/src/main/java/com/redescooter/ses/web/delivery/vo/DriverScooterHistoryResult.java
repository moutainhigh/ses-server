package com.redescooter.ses.web.delivery.vo;

import java.util.Date;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

import io.swagger.annotations.*;

/**
 * @ClassName:DriverScooterHistoryResult
 * @description: DriverScooterHistoryResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/01/07 17:02
 */
@ApiModel(value = "司机车辆分配记录", description = "司机车辆分配记录")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@EqualsAndHashCode(callSuper = true)
public class DriverScooterHistoryResult extends GeneralResult {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "车牌")
    private String licensePlate;

    @ApiModelProperty(value = "开始时间")
    private Date beginTime;

    @ApiModelProperty(value = "结束时间")
    private Date endTime;

    @ApiModelProperty(value = "骑行公里数")
    private String mileage="0";
}
