package com.redescooter.ses.api.mobile.c.service;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.mobile.c.vo.EditUserProfile2CEnter;
import com.redescooter.ses.api.mobile.c.vo.SaveUserProfileEnter;

import java.util.List;

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
    GeneralResult saveUserProfile(SaveUserProfileEnter enter);

    /**
     * 删除用户信息
     */
    GeneralResult deleteUserProfile2C(List<Long> longs);

    /**
     * 修改个人信息 暂时只支持名字
     *
     * @param enter
     * @return
     */
    GeneralResult editUserProfile(EditUserProfile2CEnter enter);

    /**
     * 删除客户对于的个人信息
     */
    void deleteUserProfile(String email);

    /**
     * 删除客户车辆关系表
     */
    void deleteConUserScooter(Long scooterId);

}
