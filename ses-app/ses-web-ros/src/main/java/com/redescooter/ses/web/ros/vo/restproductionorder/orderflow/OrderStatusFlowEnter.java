package com.redescooter.ses.web.ros.vo.restproductionorder.orderflow;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.ros.exception.ValidationExceptionCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *  @author: alex
 *  @Date: 2020/10/27 19:01
 *  @version：V ROS 1.8.3
 *  @Description:
 */
@ApiModel(value = "订单节点表", description = "订单节点表")
@Data
@AllArgsConstructor
public class OrderStatusFlowEnter extends GeneralEnter {
    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "订单状态")
    @NotNull(code = ValidationExceptionCode.STATUS_IS_EMPTY, message = "状态为空")
    private Integer orderStatus;

    @ApiModelProperty(value = "订单类型")
    @NotNull(code = ValidationExceptionCode.TYPE_IS_EMPTY, message = "订单类型为空")
    private Integer orderType;

    @ApiModelProperty(value = "关联订单")
    @NotNull(code = ValidationExceptionCode.ID_IS_EMPTY, message = "id为空")
    private Long relationId;

    @ApiModelProperty(value = "备注")
    private String remark;
}
