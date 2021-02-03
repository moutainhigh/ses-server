package com.redescooter.ses.web.website.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.service.WebDistributorService;
import com.redescooter.ses.api.common.vo.distributor.SavaOrUpdateDistributorEnter;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.web.website.constant.SequenceName;
import com.redescooter.ses.web.website.dm.SiteDistributor;
import com.redescooter.ses.web.website.service.base.SiteDistributorService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

/**
 * @description:
 * @author: Aleks
 * @create: 2021/01/25 18:02
 */
@DubboService
public class WebDistributorServiceImpl implements WebDistributorService {

    @Autowired
    private SiteDistributorService siteDistributorService;

    @DubboReference
    private IdAppService idAppService;

    /**
     * ROS经销商数据同步到官网
     * @param enter
     */
    @Override
    @Async
    public void saveOrUpdateDistribut(SavaOrUpdateDistributorEnter enter) {
       // 先通过门店编码 看看有没有同步过
        QueryWrapper<SiteDistributor> qw = new QueryWrapper<>();
        qw.eq(SiteDistributor.COL_CODE,enter.getCode());
        qw.last("limit 1");
        SiteDistributor distributor = siteDistributorService.getOne(qw);
        if (distributor == null) {
            // 说明没有同步过
            distributor = new SiteDistributor();
            BeanUtils.copyProperties(enter,distributor);
            distributor.setId(idAppService.getId(SequenceName.SITE_DISTRIBUTOR));
            siteDistributorService.save(distributor);
        }else {
            distributor.setStatus(enter.getStatus());
            siteDistributorService.updateById(distributor);
        }
    }
}
