package com.redescooter.ses.api.foundation.vo.login;


import com.redescooter.ses.api.common.annotation.NotEmpty;
import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @description: UserLoginEnter
 * @author: Darren
 * @create: 2019/01/16 09:56
 */


@Data
@EqualsAndHashCode(callSuper = false)
public class UserLoginEnter extends GeneralEnter {

    private int loginType = 1;

    private String loginName;

    private String password;

}
