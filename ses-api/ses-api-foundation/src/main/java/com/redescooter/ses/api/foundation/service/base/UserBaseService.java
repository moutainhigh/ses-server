package com.redescooter.ses.api.foundation.service.base;

import com.redescooter.ses.api.common.vo.base.BaseUserResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.StringEnter;
import com.redescooter.ses.api.foundation.vo.user.QueryUserResult;

import java.util.List;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 4/1/2020 7:14 下午
 * @ClassName: UserBaseService
 * @Function: TODO
 */
public interface UserBaseService {

    /**
     * 查询user
     *
     * @param enter
     * @return
     */
    QueryUserResult queryUserById(GeneralEnter enter);

    /**
     * 根据邮箱获取用户信息
     *
     * @param enter
     * @return
     */
    List<BaseUserResult> queryEmailInfo(StringEnter enter);

}
