package com.redescooter.ses.api.foundation.service;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.foundation.vo.message.JpushUserEnter;
import com.redescooter.ses.api.foundation.vo.message.JpushUserResult;

import java.util.List;

/**
 * description: JpushUserService
 * author: jerry.li
 * create: 2019-05-22 11:28
 */

public interface JpushUserService {

    /**
     * 插入或者更新极光用户关系
     *
     * @param enter
     * @return
     */
    GeneralResult save(JpushUserEnter enter);

    /**
     * 根据用户主键获取极光注册ID
     *
     * @param userId
     * @return
     */
    List<JpushUserResult> queryJGUserInfo(String[] userId);

}
