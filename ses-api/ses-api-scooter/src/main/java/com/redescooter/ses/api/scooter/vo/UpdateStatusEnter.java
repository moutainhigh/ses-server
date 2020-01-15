package com.redescooter.ses.api.scooter.vo;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.scooter.exception.ValidationExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

/**
 * @ClassName:UpdateStatusEnter
 * @description: UpdateStatusEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/01/15 13:19
 */
@ApiModel(value = "", description = "")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class UpdateStatusEnter extends GeneralEnter {

    @ApiModelProperty(value = "id")
    @NotNull(code = ValidationExceptionCode.ID_IS_EMPTY, message = "Id 不为空")
    private Long id;

    @ApiModelProperty(value = "车辆可用状态")
    @NotNull(code = ValidationExceptionCode.STATUS_IS_EMPTY, message = "状态不为空")
    private String availableStatus;
}
