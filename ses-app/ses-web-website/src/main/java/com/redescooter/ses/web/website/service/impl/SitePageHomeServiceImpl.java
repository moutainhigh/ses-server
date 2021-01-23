package com.redescooter.ses.web.website.service.impl;

import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.web.website.constant.SequenceName;
import com.redescooter.ses.web.website.dm.SiteContactUs;
import com.redescooter.ses.web.website.enums.CommonStatusEnums;
import com.redescooter.ses.web.website.service.SitePageHomeService;
import com.redescooter.ses.web.website.service.base.SiteContactUsService;
import com.redescooter.ses.web.website.vo.aboutus.SiteSaveAboutUsEnter;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @Author jerry
 * @Date 2021/1/24 6:22 上午
 * @Description 官网首页实现类
 **/
@Slf4j
@Service
public class SitePageHomeServiceImpl implements SitePageHomeService {

    @Autowired
    private SiteContactUsService siteContactUsService;

    @DubboReference
    private IdAppService idAppService;

    /**
     * 官网首页联系我们
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult saveAboutUs(SiteSaveAboutUsEnter enter) {

        SiteContactUs save = new SiteContactUs();
        save.setId(idAppService.getId(SequenceName.SITE_CONTACT_US));
        save.setDr(Constant.DR_FALSE);
        save.setStatus(CommonStatusEnums.NORMAL.getValue());
        save.setEmail(enter.getEmail());
        save.setFirstName(enter.getFirstName());
        save.setLastName(enter.getLastName());
        save.setRemark(enter.getMessage());
        save.setRevision(0);
        save.setCreatedBy(enter.getUserId());
        save.setCreatedTime(new Date());
        save.setUpdatedBy(enter.getUserId());
        save.setUpdatedTime(new Date());

        siteContactUsService.save(save);

        return new GeneralResult(enter.getRequestId());
    }
}
