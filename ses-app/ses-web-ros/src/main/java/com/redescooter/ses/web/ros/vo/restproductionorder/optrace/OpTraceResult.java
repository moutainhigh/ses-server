package com.redescooter.ses.web.ros.vo.restproductionorder.optrace;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @ClassNameOpTraceResult
 * @Description
 * @Author Aleks
 * @Date2020/10/23 17:41
 * @Version V1.0
 **/
@Data
@ApiModel(value = "调拨单操作动态详情出参", description = "调拨单操作动态详情出参")
public class OpTraceResult {

    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("关联的单据id")
    private Long relationId;

    @ApiModelProperty("操作类型，1：创建，2：编辑，3：下单，4：删除，5：取消订单，6：关闭订单，7：备货，8：装车，9：开始质检，10：提交出库，11：发货，12：签收")
    private Integer opType;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("操作人")
    private String createdByName;

    @ApiModelProperty("操作时间")
    private Date createdTime;
}
