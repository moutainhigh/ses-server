package com.redescooter.ses.api.common.vo.base;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.annotation.Regexp;
import com.redescooter.ses.api.common.exception.ValidationExceptionCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @ClassName:CheckEmailEnter
 * @description: CheckEmailEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/06/01 09:58
 */
@ApiModel(value = "Check Email", description = "Check Email")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class CheckEmailEnter extends GeneralEnter {

    @ApiModelProperty(value = "email")
    @NotNull(code = ValidationExceptionCode.EMAIL_IS_EMPTY, message = "Email 为空")
    private String email;
}
