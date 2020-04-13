package com.redescooter.ses.web.ros.vo.production.allocate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.constant.DateConstant;
import com.redescooter.ses.api.common.vo.base.PageEnter;
import com.redescooter.ses.web.ros.exception.ValidationExceptionCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @ClassName:AllocateOrderEnter
 * @description: AllocateOrderEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/19 15:21
 */
@ApiModel(value = "调拨单列表入参", description = "调拨单列表入参")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class AllocateOrderEnter extends PageEnter {

    @ApiModelProperty(value = "类型", required = true)
    @NotNull(code = ValidationExceptionCode.TYPE_IS_EMPTY, message = "类型为空")
    private String classType;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "创建开始时间")
    @DateTimeFormat(pattern = DateConstant.DEFAULT_DATETIME_FORMAT)
    @JsonFormat(pattern = DateConstant.DEFAULT_DATETIME_FORMAT, timezone = DateConstant.UTC)
    private Date createdStartTime;

    @ApiModelProperty(value = "创建结束时间")
    @DateTimeFormat(pattern = DateConstant.DEFAULT_DATETIME_FORMAT)
    @JsonFormat(pattern = DateConstant.DEFAULT_DATETIME_FORMAT, timezone = DateConstant.UTC)
    private Date createdEndTime;

    @ApiModelProperty(value = "关键字")
    private String keyword;
}
