package com.redescooter.ses.web.ros.vo.restproductionorder.optrace;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.ros.exception.ValidationExceptionCode;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *  @author: alex
 *  @Date: 2020/10/27 14:51
 *  @version：V ROS 1.8.3
 *  @Description:
 */
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SaveOpTraceEnter extends GeneralEnter {
    @ApiModelProperty("主键id")
    @NotNull(code = ValidationExceptionCode.ID_IS_EMPTY, message = "Id 不为空")
    private Long id;

    @ApiModelProperty("关联的单据id")
    @NotNull(code = ValidationExceptionCode.ID_IS_EMPTY, message = "关联单据Id不为空")
    private Long relationId;

    @ApiModelProperty(value = "订单类型")
    @NotNull(code = ValidationExceptionCode.TYPE_IS_EMPTY, message = "订单类型为空")
    private Integer orderType;

    @ApiModelProperty("操作类型，1：创建，2：编辑，3：下单，4：删除，5：取消订单，6：关闭订单，7：备货，8：装车，9：开始质检，10：提交出库，11：发货，12：签收,13：准备质检，14：确认入库")
    @NotNull(code = ValidationExceptionCode.TYPE_IS_EMPTY, message = "单据类型不为空")
    private Integer opType;

    @ApiModelProperty("备注")
    private String remark;
}
