package com.redescooter.ses.mobile.rps.filter;


import com.redescooter.ses.api.common.enums.base.AppIDEnums;
import com.redescooter.ses.app.common.filter.AbstractRequestFilter;

/**
 * @description: RequestFilter
 * @author: Darren
 * @create: 2019/08/21 14:19
 */
public class RequestFilter extends AbstractRequestFilter {
    @Override
    protected AppIDEnums getAppId() {
        return AppIDEnums.SES_DEV;
    }
}
