package com.redescooter.ses.api.foundation.vo.account;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
@ApiModel(value = "验证用户账户", description = "验证系统账户")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class VerifyAccountEnter extends GeneralEnter {


    @ApiModelProperty(value = "用户密码")
    private String password;
}
