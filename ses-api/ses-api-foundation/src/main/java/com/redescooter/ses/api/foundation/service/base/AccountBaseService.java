package com.redescooter.ses.api.foundation.service.base;

import com.redescooter.ses.api.common.vo.base.BaseCustomerResult;
import com.redescooter.ses.api.common.vo.base.BaseUserResult;
import com.redescooter.ses.api.common.vo.base.DateTimeParmEnter;
import com.redescooter.ses.api.foundation.vo.tenant.QueryAccountListEnter;
import com.redescooter.ses.api.foundation.vo.tenant.QueryAccountListResult;

import java.util.List;

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

    /**
     * 查询已创建 的账户
     * @param enter
     * @return
     */
    int countTenantAccount(QueryAccountListEnter enter);

    /**
     * 查询 已创建的 账户记录
     * @param enter
     * @return
     */
    List<QueryAccountListResult> tenantAccountRecords(QueryAccountListEnter enter);
}
