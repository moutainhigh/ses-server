package com.redescooter.ses.service.foundation.dao;

import java.util.List;

import com.redescooter.ses.api.foundation.vo.login.AccountsDto;
import com.redescooter.ses.api.foundation.vo.login.LoginEnter;
import com.redescooter.ses.api.foundation.vo.user.GetUserEnter;
import com.redescooter.ses.service.foundation.dm.base.PlaUser;
import com.redescooter.ses.service.foundation.dm.base.PlaUserPermission;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 30/12/2019 1:10 下午
 * @ClassName: UserTokenMapper
 * @Function: TODO
 */
public interface UserTokenMapper {

    /**
     * 检查PC用户
     * 
     * @param enter
     * @return
     */
    AccountsDto checkPcUser(LoginEnter enter);

    /**
     * 验证APP用户
     * 
     * @param enter
     * @return
     */
    List<AccountsDto> checkAPPUser(LoginEnter enter);

    /**
     * 权限校验
     * 
     * @param dto
     */
    List<PlaUserPermission> chectPermission(AccountsDto dto);

    /**
     * 根据租户id锁定所有账户
     * 
     * @param list
     */
    void lockBySaaSAccount(List<Long> list);

    /**
     * 解锁SaaS账户
     * 
     * @param list
     */
    void unlockBySaaSAccount(List<Long> list);

    /**
     * 获取APP用户，只取其中一条
     * 
     * @param enter
     * @return
     */
    PlaUser getUserLimitOne(GetUserEnter enter);

}
