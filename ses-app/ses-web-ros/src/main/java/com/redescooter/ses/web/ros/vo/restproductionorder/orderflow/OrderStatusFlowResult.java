package com.redescooter.ses.web.ros.vo.restproductionorder.orderflow;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import lombok.AllArgsConstructor;
import lombok.Data;

import io.swagger.annotations.*;

/**
 *  @author: alex
 *  @Date: 2020/10/27 19:01
 *  @version：V ROS 1.8.3
 *  @Description:
 */
@ApiModel(value = "订单节点表", description = "订单节点表")
@Data
@AllArgsConstructor
public class OrderStatusFlowResult extends GeneralResult {
    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "订单状态")
    private Integer orderStatus;

    @ApiModelProperty(value = "关联订单")
    private Long relationId;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "创建人")
    private Long createById;

    @ApiModelProperty(value = "创建人")
    private String createByFirstName;

    @ApiModelProperty(value = "创建人")
    private String createByLastName;

    @ApiModelProperty(value = "更新人")
    private Long updateById;

    @ApiModelProperty(value = "更新人")
    private String updateByFirstName;

    @ApiModelProperty(value = "更新人")
    private String updateByLastName;
}
