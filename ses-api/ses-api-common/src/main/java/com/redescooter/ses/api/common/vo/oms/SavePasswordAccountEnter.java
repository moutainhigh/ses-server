package com.redescooter.ses.api.common.vo.oms;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.exception.ValidationExceptionBaseCode;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "OMS创建用户信息", description = "OMS创建用户信息")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
public class SavePasswordAccountEnter extends EditAccountEnter {

    /**
     * 密码
     */
    @ApiModelProperty(value="密码")
    @NotNull(code = ValidationExceptionBaseCode.PASSWORD_IS_EMPTY, message = "密码不能为空")
    private String password;
}
