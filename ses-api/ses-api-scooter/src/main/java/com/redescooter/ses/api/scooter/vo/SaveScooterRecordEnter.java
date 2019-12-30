package com.redescooter.ses.api.scooter.vo;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.scooter.exception.ValidationExceptionCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @ClassName:SaveScooterRecord
 * @description: SaveScooterRecord
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/27 10:19
 */
@ApiModel(value = "车辆操作记录入参", description = "车辆操作记录入参")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class SaveScooterRecordEnter<T> extends GeneralEnter {

    @ApiModelProperty(value = "操作类型")
    @NotNull(code = ValidationExceptionCode.EVENT_IS_EMPTY, message = "事件为空")
    private String actionType;

    @ApiModelProperty(value = "经度")
    @NotNull(code = ValidationExceptionCode.LNG_IS_EMPTY, message = "经度为空")
    private BigDecimal longitude;

    @ApiModelProperty(value = "纬度")
    @NotNull(code = ValidationExceptionCode.LAT_IS_EMPTY, message = "纬度为空")
    private BigDecimal latitude;

    @ApiModelProperty(value = "业务对象")
    @NotNull(code = ValidationExceptionCode.BUSSINESS_OBJ_IS_EMPTY, message = "业务对象为空")
    private T t;
}
