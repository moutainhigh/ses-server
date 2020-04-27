package com.redescooter.ses.web.ros.service.sys;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.ros.vo.sys.user.UserInfoResult;

/**
 * @ClassNameUserProfileService
 * @Description
 * @Author Joan
 * @Date2020/4/27 18:47
 * @Version V1.0
 **/
public interface SysUserProfileService {

    /**
     * 用户信息
     *
     * @param enter
     * @return
     */
    UserInfoResult userInfo(GeneralEnter enter);
}
