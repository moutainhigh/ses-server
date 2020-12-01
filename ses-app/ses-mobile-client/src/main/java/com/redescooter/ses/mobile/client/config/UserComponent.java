package com.redescooter.ses.mobile.client.config;

import com.redescooter.ses.api.common.enums.base.AccountTypeEnums;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.foundation.service.base.UserBaseService;
import com.redescooter.ses.api.foundation.vo.user.QueryUserResult;
import com.redescooter.ses.api.mobile.b.exception.MobileBException;
import com.redescooter.ses.mobile.client.exception.ExceptionCodeEnums;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;

/**
 * 查询用户类型组件(用于区分2C/2B用户进行不同业务分发)
 * @author assert
 * @date 2020/11/18 16:18
 */
@Component
public class UserComponent {

    @Reference
    private UserBaseService userBaseService;

    /**
     * 查询用户类型
     * @param enter
     * @return
     */
    public int getUserServiceTypeById(GeneralEnter enter) {
        /**
         * 用户业务类型 1-B端用户 2-C端用户
         */
        int userServiceType;

        QueryUserResult queryUserResult = userBaseService.queryUserById(enter);
        if (null == queryUserResult) {
            throw new MobileBException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(), ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }

        /**
         * 根据用户类型判断是2C还是2B用户
         */
        if (AccountTypeEnums.APP_RESTAURANT.getAccountType().equals(queryUserResult.getUserType())
                || AccountTypeEnums.APP_EXPRESS.getAccountType().equals(queryUserResult.getUserType())) {
            userServiceType = 1;
        } else {
            userServiceType = 2;
        }

        return userServiceType;
    }

}
