package com.redescooter.ses.api.foundation.service.base;

import com.redescooter.ses.api.common.vo.base.BaseCustomerResult;
import com.redescooter.ses.api.common.vo.base.BaseUserResult;
import com.redescooter.ses.api.common.vo.base.DateTimeParmEnter;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.SetPasswordEnter;
import com.redescooter.ses.api.foundation.vo.tenant.QueryAccountListEnter;
import com.redescooter.ses.api.foundation.vo.tenant.QueryAccountListResult;
import com.redescooter.ses.api.foundation.vo.user.QueryUserResult;

import java.util.List;
import java.util.Map;

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
     *
     * @param enter
     * @return
     */
    int countTenantAccount(QueryAccountListEnter enter);

    /**
     * 查询 已创建的 账户记录
     *
     * @param enter
     * @return
     */
    List<QueryAccountListResult> tenantAccountRecords(QueryAccountListEnter enter);

    /**
     * 查询租户状态
     *
     * @return
     */
    Map<String, Integer> accountCountStatus();

    /**
     * 账户冻结
     *
     * @param enter
     * @return
     */
    GeneralResult freeze(DateTimeParmEnter<BaseCustomerResult> enter);

    /**
     * 账户解冻
     *
     * @param enter
     * @return
     */
    GeneralResult unFreezeAccount(DateTimeParmEnter<BaseCustomerResult> enter);

    /**
     * 账户续期
     *
     * @param enter
     * @return
     */
    GeneralResult renewAccont(DateTimeParmEnter<BaseCustomerResult> enter);

    /**
     * @param enter
     * @return
     */
    GeneralResult setPassword(SetPasswordEnter<BaseCustomerResult> enter);


    /**
     * 根据租户ID删除所有用户
     *
     * @param enter
     * @return
     */
    GeneralResult deleteUserbyTenantId(IdEnter enter);

    /**
     * 查询user
     *
     * @param enter
     * @return
     */
    QueryUserResult queryUserById(GeneralEnter enter);


    /**
     * 开通2B司机账户
     *
     * @param dto
     * @return
     */
    BaseUserResult openDriver2BAccout(SaveDriverAccountDto dto);

    /**
     * 关闭2B司机账号
     *
     * @return
     */
    GeneralResult cancelDriver2BAccout(IdEnter enter);

    /**
     * 再次发生激活邮件
     *
     * @param enter
     * @return
     */
    GeneralResult sendEmailAgian(IdEnter enter);


}
