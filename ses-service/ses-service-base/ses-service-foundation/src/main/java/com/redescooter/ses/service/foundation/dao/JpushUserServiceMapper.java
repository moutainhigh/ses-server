package com.redescooter.ses.service.foundation.dao;

import com.redescooter.ses.api.foundation.vo.message.JpushUserResult;
import com.redescooter.ses.service.foundation.dm.JpushUserData;
import com.redescooter.ses.service.foundation.dm.base.PlaJpushUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * description: JpushUserServiceMapper
 * author: jerry.li
 * create: 2019-05-22 11:44
 */

public interface JpushUserServiceMapper {

    /**
     * 重置极光设备绑定信息
     *
     * @param enter
     * @return
     */
    int reset(PlaJpushUser enter);

    /**
     * 根据用户主键获取极光注册ID
     *
     * @param userId
     * @return
     */
    List<JpushUserResult> queryRegistids(@Param("list") List<String> userId);

    /**
     * 根据设备id查询推送设备信息
     *
     * @param registrationId
     * @return
     */
    JpushUserData queryJpushUserByRegistrationId(@Param("registrationId") String registrationId);

}
