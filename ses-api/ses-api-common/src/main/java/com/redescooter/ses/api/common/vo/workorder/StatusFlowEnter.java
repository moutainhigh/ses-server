package com.redescooter.ses.api.common.vo.workorder;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassNameStatusFlowEnter
 * @Description
 * @Author Aleks
 * @Date2020/12/4 18:10
 * @Version V1.0
 **/
@Data
@ApiModel(value = "工单的状态流转入参")
public class StatusFlowEnter extends GeneralEnter {

    @ApiModelProperty("工单id")
    private Long id;

    @ApiModelProperty("工单状态 1：新建（Pending），2：处理中（In Progress），3：已完成（Completed），4：关闭（Closed）")
    private Integer workOrderStatus;


}
