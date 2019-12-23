package com.redescooter.ses.api.hub.service.corporate;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.hub.vo.UserProfileHubEnter;

/**
 * @ClassName:CorPorateAccountProService
 * @description: CorPorateAccountProService
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/23 15:15
 */
public interface CorporateAccountProService {
    /**
     * 保存个人信息
     * @param enter
     * @return
     */
    GeneralResult saveUserProfileHub(UserProfileHubEnter enter);
}
