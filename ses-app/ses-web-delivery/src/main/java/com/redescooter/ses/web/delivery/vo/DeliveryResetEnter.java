package com.redescooter.ses.web.delivery.vo;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.annotation.Regexp;
import com.redescooter.ses.api.common.constant.RegexpConstant;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.delivery.exception.ValidationExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

/**
 * @ClassName:DeliveryResetEnter
 * @description: DeliveryResetEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/01/06 19:25
 */
@ApiModel(value = "重新分配订单", description = "重新分配订单")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@Data
public class DeliveryResetEnter extends GeneralEnter {

    @ApiModelProperty(value = "订单Id")
    @NotNull(code = ValidationExceptionCode.ID_IS_EMPTY,message = "Id 为空")
    private Long id;

    @ApiModelProperty(value = "司机Id")
    @NotNull(code = ValidationExceptionCode.DRIVER_ID_IS_EMPTY, message = "未选择司机")
    private Long driverId;

    @ApiModelProperty(value = "耗时")
    @NotNull(code = ValidationExceptionCode.DURATION_IS_EMPTY,message = "耗时为空")
    private Integer duration;

}
