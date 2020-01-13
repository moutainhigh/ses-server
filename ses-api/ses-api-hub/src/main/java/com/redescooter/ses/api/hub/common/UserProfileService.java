package com.redescooter.ses.api.hub.common;

import com.redescooter.ses.api.common.vo.base.BaseUserResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.hub.vo.SaveUserProfileHubEnter;

import java.util.List;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 25/12/2019 10:30 上午
 * @ClassName: UserProfileService
 * @Function: TODO
 */
public interface UserProfileService {
    /**
     * Toc 保存个人信息
     *
     * @param enter
     */
    void saveUserProfile2C(SaveUserProfileHubEnter enter);

    /**
     * Tob 保存个人信息
     *
     * @param enter
     */
    void saveUserProfile2B(SaveUserProfileHubEnter enter);

    /**
     * Toc 删除个人信息
     *
     * @param longs
     */
    void deleteUserProfile2C(List<Long> longs);

    /**
     * Tob 删除个人信息
     *
     * @param longs
     */
    void deleteUserProfile2B(List<Long> longs);

}
