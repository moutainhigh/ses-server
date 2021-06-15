package com.redescooter.ses.service.mobile.c.dao;

import com.redescooter.ses.service.mobile.c.dm.base.ConUserScooter;
import org.apache.ibatis.annotations.Param;

public interface UserProfileMapper {

    /**
     * 删除
     * @return com.redescooter.ses.service.mobile.c.dm.base.ConUserScooter
     * @author assert
     * @date 2020/11/27
    */
    int deleteUserProfile(String email);

}
