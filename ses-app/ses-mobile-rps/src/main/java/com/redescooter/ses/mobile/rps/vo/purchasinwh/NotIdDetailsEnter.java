package com.redescooter.ses.mobile.rps.vo.purchasinwh;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.exception.ValidationExceptionCode;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @ClassNameNotIdDetailsEnter
 * @Description
 * @Author Joan
 * @Date2020/4/28 18:44
 * @Version V1.0
 **/
@ApiModel(value = "无ID入参", description = "无ID入参")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class NotIdDetailsEnter extends GeneralEnter {
    @ApiModelProperty(value = "主键",required = true)
    @NotNull(code = ValidationExceptionCode.ID_IS_EMPTY, message = "主键不能为空")
    private Long id;
}
