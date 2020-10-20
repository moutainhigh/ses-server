package com.redescooter.ses.web.ros.vo.production.assembly;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.exception.ValidationExceptionBaseCode;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

/**
 * @ClassName:StartPrepareEnter
 * @description: StartPrepareEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/03 11:43
 */
@ApiModel(value = "开始备料入参", description = "开始备料入参")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class StartPrepareEnter extends GeneralEnter {

    @ApiModelProperty(value = "id")
    @NotNull(code = ValidationExceptionBaseCode.ID_IS_EMPTY, message = "id 为空")
    private Long id;

    @ApiModelProperty(value = "附件")
    @NotNull(code = com.redescooter.ses.web.ros.exception.ValidationExceptionCode.ANNEX_IS_EMPTY, message = "附件为空·")
    private String annex;
}
