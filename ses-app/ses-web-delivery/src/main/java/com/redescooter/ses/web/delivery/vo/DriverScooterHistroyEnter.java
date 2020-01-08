package com.redescooter.ses.web.delivery.vo;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.PageEnter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

/**
 * @ClassName:DriverScooterHistroyEnter
 * @description: DriverScooterHistroyEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/01/07 17:08
 */
@ApiModel(value = "司机车辆分配", description = "司机车辆分配")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@EqualsAndHashCode(callSuper = true)
public class DriverScooterHistroyEnter extends PageEnter {
    @ApiModelProperty(value = "司机Id")
    private Long id;

    @ApiModelProperty(value = "分车开始时间")
    private String assignBeginTime;

    @ApiModelProperty(value = "分车结束时间")
    private String assignEndTime;

    @ApiModelProperty(value = "换车开始时间")
    private String removeBeginTime;

    @ApiModelProperty(value = "换车结束时间")
    private String removeEndTime;

    @ApiModelProperty(value = "关键字")
    private String keyword;
}
