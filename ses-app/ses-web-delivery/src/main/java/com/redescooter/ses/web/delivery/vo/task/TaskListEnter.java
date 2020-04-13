package com.redescooter.ses.web.delivery.vo.task;

import com.redescooter.ses.api.common.vo.base.PageEnter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

import java.util.List;

/**
 * @ClassName:DeliveryListEnter
 * @description: DeliveryListEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/01/15 15:14
 */
@ApiModel(value = "任务列表", description = "任务列表")
/**
 *生成getter,setter等函数
 * 生成全参数构造函数
 * 生成无参构造函数
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class TaskListEnter extends PageEnter {

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "时间统计类型")
    private String taskTimeCountType;

    @ApiModelProperty(value = "任务配送开始时间")
    private String taskStartTime;

    @ApiModelProperty(value = "任务配送结束时间")
    private String taskEndTime;

    @ApiModelProperty(value = "开始开始配送时间")
    private String startStartTime;

    @ApiModelProperty(value = "结束开始配送时间")
    private String startEndTime;

    @ApiModelProperty(value = "配送完成开始时间")
    private String deliveredStartTime;

    @ApiModelProperty(value = "配送完成结束时间")
    private String deliveredEndTime;

    @ApiModelProperty(value = "关键字")
    private String keyword;
}
