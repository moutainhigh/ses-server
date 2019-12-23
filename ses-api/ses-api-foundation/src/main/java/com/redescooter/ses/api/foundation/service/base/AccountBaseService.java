package com.redescooter.ses.api.foundation.service.base;

import com.redescooter.ses.api.common.vo.base.BaseCustomerResult;
import com.redescooter.ses.api.common.vo.base.BaseUserResult;
import com.redescooter.ses.api.common.vo.base.DateTimeParmEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.foundation.vo.login.QueryAccountListEnter;
import com.redescooter.ses.api.foundation.vo.login.QueryAccountListResult;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 20/12/2019 2:07 下午
 * @ClassName: AccountBaseService
 * @Function: TODO
 */
public interface AccountBaseService {

    /**
     * 账号开通
     *
     * @param enter
     * @return
     */
    BaseUserResult open(DateTimeParmEnter<BaseCustomerResult> enter);

    /**
     * 租户验证邮箱
     *
     * @param mail
     * @return
     */
    Boolean chectMail(String mail);
}
