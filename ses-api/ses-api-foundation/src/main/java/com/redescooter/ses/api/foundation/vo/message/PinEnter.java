package com.redescooter.ses.api.foundation.vo.message;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import lombok.Data;

@Data
public class PinEnter extends GeneralEnter {
    //邮箱(登录名)
    private String loginName;
    //登录密码
    private String password;
    //PIN
    private String firstPin;

    //确认PIN
    private String confirmPin;
}
