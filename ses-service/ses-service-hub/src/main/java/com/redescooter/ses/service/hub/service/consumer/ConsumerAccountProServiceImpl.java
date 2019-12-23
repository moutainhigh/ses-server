package com.redescooter.ses.service.hub.service.consumer;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.hub.service.customer.ConsumerAccountProService;
import com.redescooter.ses.api.hub.vo.UserProfileHubEnter;
import com.redescooter.ses.api.mobile.c.service.UserProfileProService;
import com.redescooter.ses.api.mobile.c.vo.SaveUserPofileEnter;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName:CorporateAccountProServiceImpl
 * @description: CorporateAccountProServiceImpl
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/23 16:31
 */
@Service
public class ConsumerAccountProServiceImpl implements ConsumerAccountProService {

    @Autowired
    private UserProfileProService userProfileProService;

    /**
     * 保存个人信息
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult saveUserProfileHub(UserProfileHubEnter enter) {
        SaveUserPofileEnter saveUserPofileEnter = new SaveUserPofileEnter();
        BeanUtils.copyProperties(enter,saveUserPofileEnter);

        return userProfileProService.saveUserPofile(saveUserPofileEnter);
    }
}
