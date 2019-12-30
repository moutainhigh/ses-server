package com.redescooter.ses.api.mobile.b.service;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.mobile.b.vo.SaveUserProfileEnter;
import com.redescooter.ses.api.mobile.b.vo.UserProfileResult;

/**
 * @ClassName:UserProfileService
 * @description: UserProfileService
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/30 15:22
 */
public interface UserProfileMobileService {
    /**
     * 个人信息
     *
     * @param enter
     * @return
     */
    UserProfileResult userProfile(GeneralEnter enter);

    /**
     * 保存客户信息
     *
     * @param enter
     * @return
     */
    GeneralResult saveUserProfile(SaveUserProfileEnter enter);
}
