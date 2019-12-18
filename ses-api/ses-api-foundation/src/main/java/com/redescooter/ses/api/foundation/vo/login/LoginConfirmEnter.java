package com.redescooter.ses.api.foundation.vo.login;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 22/11/2019 10:30 上午
 * @ClassName: LoginConfirmEnter
 * @Function: TODO
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class LoginConfirmEnter extends GeneralEnter {

    @ApiModelProperty("登录类型")
    private int loginType = 1;
    @ApiModelProperty(value = "请求id")
    private String confirmRequestId;
    @ApiModelProperty("确认用户主键")
    private Long confirmUserId;
}
