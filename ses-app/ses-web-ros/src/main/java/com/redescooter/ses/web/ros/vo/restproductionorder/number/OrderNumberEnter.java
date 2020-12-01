package com.redescooter.ses.web.ros.vo.restproductionorder.number;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.ros.exception.ValidationExceptionCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 *  @author: alex
 *  @Date: 2020/10/28 12:48
 *  @version：V ROS 1.8.3
 *  @Description:
 */
@ApiModel(value = "单据编号入参", description = "单据编号入参")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OrderNumberEnter extends GeneralEnter {

    @ApiModelProperty(value = "订单类型")
    @NotNull(code = ValidationExceptionCode.TYPE_IS_EMPTY, message = "单据类型为空")
    private Integer orderType;
}
