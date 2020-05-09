package com.redescooter.ses.web.delivery.vo.edscooter;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.delivery.exception.ValidationExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

/**
 * @ClassName:chanageStatus
 * @description: chanageStatus
 * @author: Alex
 * @Version：1.3
 * @create: 2020/01/15 12:21
 */
@ApiModel(value = "修改车辆状态", description = "修改车辆状态")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class ChanageStatusEnter extends GeneralEnter {
    @ApiModelProperty(value = "id", required = true)
    @NotNull(code = ValidationExceptionCode.ID_IS_EMPTY, message = "Id 不为空")
    private Long id;

    @ApiModelProperty(value = "状态")
    private String status;
}
