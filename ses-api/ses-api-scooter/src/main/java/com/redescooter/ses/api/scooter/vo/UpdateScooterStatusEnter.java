package com.redescooter.ses.api.scooter.vo;


import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.scooter.exception.ValidationExceptionCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @description: UpdateScooterStatusEnter
 * @author: Darren
 * @create: 2019/02/19 19:12
 */
@ApiModel(value = "车辆状态修改", description = "车辆状态修改")
@Data
@EqualsAndHashCode(callSuper = false)
public class UpdateScooterStatusEnter extends GeneralEnter {

    @ApiModelProperty(value = " 车辆Id")
    @NotNull(code = ValidationExceptionCode.SCOOTERID_IS_EMPTY, message = "scooter id is not empty.")
    private Long scooterId;

    @ApiModelProperty(value = "车辆状态")
    @NotNull(code = ValidationExceptionCode.SCOOTER_STATUS_IS_EMPTY, message = "scooter status is not empty.")
    private String scooterStatus;
}
