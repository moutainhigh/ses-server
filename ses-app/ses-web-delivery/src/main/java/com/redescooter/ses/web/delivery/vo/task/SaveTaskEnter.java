package com.redescooter.ses.web.delivery.vo.task;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.delivery.exception.ValidationExceptionCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

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

    @ApiModelProperty(value = "司机任务列表", required = true ,allowEmptyValue=true)
    private List<DriverTaskEnter> driverTaskEnterList;

    @ApiModelProperty(value = "任务时间", required = true)
    @NotNull(code = ValidationExceptionCode.TASK_TIME,message = "任务时间为空")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Date taskTime;
}
