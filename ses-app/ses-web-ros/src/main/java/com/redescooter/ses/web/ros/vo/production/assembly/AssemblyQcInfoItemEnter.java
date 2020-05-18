package com.redescooter.ses.web.ros.vo.production.assembly;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.constant.DateConstant;
import com.redescooter.ses.api.common.exception.ValidationExceptionCode;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
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
 * @ClassName:AssemblyQcInfoItemEnter
 * @description: AssemblyQcInfoItemEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/05/08 11:49
 */
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "Qc质检记录条目", description = "Qc质检记录条目")
public class AssemblyQcInfoItemEnter extends GeneralEnter {
    @ApiModelProperty(value = "id")
    @NotNull(code = ValidationExceptionCode.ID_IS_EMPTY, message = "Id 为空")
    private Long id;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "质检开始时间")
    @DateTimeFormat(pattern = DateConstant.DEFAULT_DATETIME_FORMAT)
    @JsonFormat(pattern = DateConstant.DEFAULT_DATETIME_FORMAT, timezone = DateConstant.UTC)
    private Date qcStartTime;

    @ApiModelProperty(value = "质检结束时间")
    @DateTimeFormat(pattern = DateConstant.DEFAULT_DATETIME_FORMAT)
    @JsonFormat(pattern = DateConstant.DEFAULT_DATETIME_FORMAT, timezone = DateConstant.UTC)
    private Date qcEndTime;
}