package com.redescooter.ses.web.website.service.impl;

import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.crypt.RsaUtils;
import com.redescooter.ses.web.website.config.RequestsKeyProperties;
import com.redescooter.ses.web.website.constant.SequenceName;
import com.redescooter.ses.web.website.dm.SiteContactUs;
import com.redescooter.ses.web.website.enums.CommonStatusEnums;
import com.redescooter.ses.web.website.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.website.exception.SesWebsiteException;
import com.redescooter.ses.web.website.service.SitePageHomeService;
import com.redescooter.ses.web.website.service.base.SiteContactUsService;
import com.redescooter.ses.web.website.vo.aboutus.SiteSaveAboutUsEnter;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Autowired
    private RequestsKeyProperties requestsKeyProperties;
    /**
     * 官网首页联系我们
     *
     * @param enter
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult saveAboutUs(SiteSaveAboutUsEnter enter) {
        String decryptEamil = "";
        if (StringUtils.isNotEmpty(enter.getEmail())) {
            try {
                //邮箱解密
                decryptEamil = RsaUtils.decrypt(enter.getEmail(), requestsKeyProperties.getPrivateKey());
            } catch (Exception e) {
                throw new SesWebsiteException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
            }
            enter.setEmail(decryptEamil);
        }
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
