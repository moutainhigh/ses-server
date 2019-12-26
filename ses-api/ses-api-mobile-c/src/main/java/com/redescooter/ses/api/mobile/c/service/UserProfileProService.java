package com.redescooter.ses.api.mobile.c.service;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.mobile.c.vo.SaveUserProfileEnter;

/**
 * @ClassName:UserProfileProService
 * @description: UserProfileProService
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/23 16:44
 */
public interface UserProfileProService {
    /**
     * 保存个人信息
     * @param enter
     * @return
     */
    GeneralResult saveUserPofile(SaveUserProfileEnter enter);
}
