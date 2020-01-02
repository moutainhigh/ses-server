package com.redescooter.ses.api.hub.service.customer;

import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.hub.vo.QueryUserProfileResult;

/**
 * @ClassName:AccountProService
 * @description: AccountProService
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/23 15:15
 */
public interface ConsumerUserProfileService {
    /**
     * 查询个人信息
     *
     * @param enter id为 userProfile Id
     * @return
     */
    QueryUserProfileResult queryUserProfile(IdEnter enter);

}
