package com.redescooter.ses.mobile.rps.vo.preparematerial;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.exception.ValidationExceptionBaseCode;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @ClassName:ConfirmPreparationEnter
 * @description: ConfirmPreparationEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/22 22:42
 */
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class ConfirmPreparationEnter extends GeneralEnter {

    @ApiModelProperty(value = "id")
    @NotNull(code = ValidationExceptionBaseCode.ID_IS_EMPTY, message = "Id为空")
    private Long id;

    @ApiModelProperty(value = "单据来源类型")
    @NotNull(code = com.redescooter.ses.mobile.rps.exception.ValidationExceptionCode.SOURCE_TYPE_IS_EMPTY, message = "单据来源类型为空")
    private String sourceType;

}
