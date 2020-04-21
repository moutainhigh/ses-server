package com.redescooter.ses.mobile.rps.vo.assembly;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.exception.ValidationExceptionCode;
import com.redescooter.ses.api.common.vo.base.PageEnter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

/**
 * @ClassName:AssemblyDetailEnter
 * @description: AssemblyDetailEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/14 11:02
 */
@ApiModel(value = "组装列表详情", description = "组装列表详情")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class AssemblyDetailEnter extends PageEnter {
    @ApiModelProperty(value = "id")
    @NotNull(code = ValidationExceptionCode.ID_IS_EMPTY, message = "id 不为空")
    private Long id;
}
