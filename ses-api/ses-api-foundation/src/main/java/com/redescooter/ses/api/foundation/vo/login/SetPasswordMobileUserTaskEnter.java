package com.redescooter.ses.api.foundation.vo.login;

import com.redescooter.ses.api.common.vo.base.BaseMailTaskEnter;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 10/10/2019 6:57 下午
 * @ClassName: SetPasswordMobileUserTaskEnter
 * @Function: TODO
 */
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class SetPasswordMobileUserTaskEnter extends BaseMailTaskEnter {
    @ApiModelProperty(value = "验证码")
    private String code;
}
