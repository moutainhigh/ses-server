package com.redescooter.ses.web.delivery.vo.edscooter;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.vo.base.PageEnter;
import com.redescooter.ses.web.delivery.exception.ValidationExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

/**
 * @ClassName:AssignMobileHistroyEnter
 * @description: AssignMobileHistroyEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/01/13 19:44
 */
@ApiModel(value = "分配车辆记录", description = "分配车辆记录")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class EdScooterHistroyEnter extends PageEnter {

    @ApiModelProperty(value = "scooterId", required = true)
    @NotNull(code = ValidationExceptionCode.ID_IS_EMPTY, message = "ID 不为空")
    private Long id;

    @ApiModelProperty(value = "分配时间")
    private String assignStartTime;

    @ApiModelProperty(value = "分配时间")
    private String assignEndTime;

    @ApiModelProperty(value = "结束开始时间")
    private String removeStartTime;

    @ApiModelProperty(value = "结束结束时间")
    private String removeEndTime;

    @ApiModelProperty(value = "关键字 只支持司机搜索")
    private String keyword;

}
