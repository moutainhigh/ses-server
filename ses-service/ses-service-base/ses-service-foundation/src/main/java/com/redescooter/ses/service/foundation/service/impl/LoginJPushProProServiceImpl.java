package com.redescooter.ses.service.foundation.service.impl;


import com.redescooter.ses.api.common.enums.account.LoginPushStatusEnums;
import com.redescooter.ses.api.common.enums.base.ClientTypeEnums;
import com.redescooter.ses.api.common.enums.proxy.jiguang.PlatformTypeEnums;
import com.redescooter.ses.api.common.enums.proxy.jiguang.PushTypeEnums;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.foundation.service.JpushUserService;
import com.redescooter.ses.api.foundation.service.LoginJPushProService;
import com.redescooter.ses.api.foundation.vo.message.JpushUserEnter;
import com.redescooter.ses.api.foundation.vo.message.LoginPushEnter;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;

/**
 * description: LoginJPushServiceImpl
 * author: jerry.li
 * create: 2019-05-22 15:09
 */
@Slf4j
@DubboService
public class LoginJPushProProServiceImpl implements LoginJPushProService {

    @DubboReference
    private JpushUserService jpushUserService;

    /**
     * 极光用户关系绑定
     *
     * @param enter
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult logInPush(LoginPushEnter enter) {

        JpushUserEnter jpushUserEnter = new JpushUserEnter();

        BeanUtils.copyProperties(enter, jpushUserEnter);

        //由于是做登录操作，故设置值为0
        jpushUserEnter.setStatus(LoginPushStatusEnums.LOGIN_IN.getValue());
        if (enter.getClientType().equals(ClientTypeEnums.APP_ANDROID.getValue())) {
            jpushUserEnter.setPlatformType(PlatformTypeEnums.ANDROID.getValue());
        }
        if (StringUtils.equals(enter.getClientType(),ClientTypeEnums.APP_IOS.getValue())) {
            jpushUserEnter.setPlatformType(PlatformTypeEnums.IOS.getValue());
        }
        jpushUserEnter.setAudienceType(PushTypeEnums.REGISTRATION_ID.getCode());

        jpushUserService.save(jpushUserEnter);

        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 极光用户关系解绑
     *
     * @param enter
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult logOutPush(LoginPushEnter enter) {

        JpushUserEnter jpushUserEnter = new JpushUserEnter();
        BeanUtils.copyProperties(enter, jpushUserEnter);
        //由于是做登出注销操作，故设置值为1
        jpushUserEnter.setStatus(LoginPushStatusEnums.LOGIN_OUT.getValue());
        jpushUserService.save(jpushUserEnter);
        return new GeneralResult(enter.getRequestId());
    }
}
