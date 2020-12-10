package com.redescooter.ses.api.common.vo.workorder;

import com.redescooter.ses.api.common.vo.base.PageEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassNameWorkOrderListDTO
 * @Description
 * @Author Aleks
 * @Date2020/12/4 14:01
 * @Version V1.0
 **/
@Data
@ApiModel(value = "工单列表传参对象")
public class WorkOrderListEnter extends PageEnter {

    @ApiModelProperty(value="工单来源 1：APP，2：ROS，3：SAAS，4：OMS，5：RPS")
    private Integer source;

    @ApiModelProperty(value="工单状态 1：新建（Pending），2：处理中（In Progress），3：已完成（Completed），4：关闭（Closed）")
    private Integer workOrderStatus;

    @ApiModelProperty("关键字")
    private String keyword;

}
