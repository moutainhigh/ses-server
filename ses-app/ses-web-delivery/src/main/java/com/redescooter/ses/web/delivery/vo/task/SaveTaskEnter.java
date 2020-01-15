package com.redescooter.ses.web.delivery.vo.task;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

import io.swagger.annotations.*;

/**
 * @ClassName:SaveTaskEnter
 * @description: SaveTaskEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/01/15 17:02
 */
@ApiModel(value = "大订单保存", description = "大订单保存")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class SaveTaskEnter extends GeneralEnter {

    @ApiModelProperty(value = "小定单id", required = true)
    private List<Long> ids;

    @ApiModelProperty(value = "司机Id", required = true)
    private Long diverId;

    @ApiModelProperty(value = "任务时间", required = true)
    private String taskTime;
}
