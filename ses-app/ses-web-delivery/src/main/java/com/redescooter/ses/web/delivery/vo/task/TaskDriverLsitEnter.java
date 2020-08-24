package com.redescooter.ses.web.delivery.vo.task;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

/**
 * @ClassName:TaskDriverLsitEnter
 * @description: TaskDriverLsitEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/08/24 10:03
 */
@ApiModel(value = "任务分配 司机列表少筛选", description = "任务分配 司机列表少筛选")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class TaskDriverLsitEnter extends GeneralEnter {
    
    @ApiModelProperty(value = "关键字")
    private String keyword;
}
