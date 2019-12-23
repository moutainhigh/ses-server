package com.redescooter.ses.service.hub.source.consumer.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.hub.service.customer.ConsumerAccountProService;
import com.redescooter.ses.api.hub.vo.UserProfileHubEnter;
import com.redescooter.ses.api.mobile.c.service.UserProfileProService;
import com.redescooter.ses.api.mobile.c.vo.SaveUserPofileEnter;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;

/**
 * @ClassName:CorporateAccountProServiceImpl
 * @description: CorporateAccountProServiceImpl
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/23 16:31
 */
@DS("consumer")
@Service
public class ConsumerAccountProServiceImpl implements ConsumerAccountProService {

    @Reference
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
