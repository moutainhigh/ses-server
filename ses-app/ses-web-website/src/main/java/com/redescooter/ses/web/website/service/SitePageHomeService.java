package com.redescooter.ses.web.website.service;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.web.website.vo.aboutus.SiteSaveAboutUsEnter;

/**
 * @Author jerry
 * @Date 2021/1/24 6:20 上午
 * @Description 官网首页服务
 **/
public interface SitePageHomeService {

    /**
     * 官网首页联系我们
     *
     * @param enter
     * @return
     */
    GeneralResult saveAboutUs(SiteSaveAboutUsEnter enter);
}
