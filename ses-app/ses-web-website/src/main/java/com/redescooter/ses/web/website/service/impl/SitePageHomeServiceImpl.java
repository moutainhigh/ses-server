package com.redescooter.ses.web.website.service.impl;

import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.hub.service.operation.CustomerService;
import com.redescooter.ses.api.hub.vo.operation.SyncContactUsDataEnter;
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
import org.springframework.scheduling.annotation.Async;
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

    @DubboReference
    private CustomerService customerService;

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

        // 官网创建客户数据同步到ros
        syncData(enter);
        return new GeneralResult(enter.getRequestId());
    }

    @Async
    public void syncData(SiteSaveAboutUsEnter enter) {
        SyncContactUsDataEnter model = new SyncContactUsDataEnter();
        model.setDr(Constant.DR_FALSE);
        model.setEmail(enter.getEmail());
        model.setFirstName(enter.getFirstName());
        model.setLastName(enter.getLastName());
        if (StringUtils.isNoneBlank(enter.getFirstName(), enter.getLastName())) {
            model.setFullName(new StringBuffer().append(enter.getFirstName()).append(" ").append(enter.getLastName()).toString());
        }
        model.setRemark(enter.getMessage());
        model.setRevision(0);
        model.setCreatedBy(0L);
        model.setCreatedTime(new Date());
        model.setUpdatedBy(0L);
        model.setUpdatedTime(new Date());
        customerService.syncContactUsData(model);
    }

}
