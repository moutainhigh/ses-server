package com.redescooter.ses.api.hub.service.customer;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.hub.vo.SaveUserProfileHubEnter;

/**
 * @ClassName:AccountProService
 * @description: AccountProService
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/23 15:15
 */
public interface ConsumerAccountProService {
    /**
     * 保存个人信息
     * @param enter
     * @return
     */
    GeneralResult saveUserProfileHub(SaveUserProfileHubEnter enter);
}
