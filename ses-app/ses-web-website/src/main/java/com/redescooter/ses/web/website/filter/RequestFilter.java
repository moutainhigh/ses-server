package com.redescooter.ses.web.website.filter;


import com.redescooter.ses.api.common.enums.base.AppIDEnums;
import com.redescooter.ses.app.common.filter.AbstractRequestFilter;

/**
 * @description: RequestFilter
 * @author: Jerry
 * @create: 03/01/2021 10:50
 */
public class RequestFilter extends AbstractRequestFilter {
    @Override
    protected AppIDEnums getAppId() {
        return AppIDEnums.SES_WEBSITE;
    }
}
