package com.redescooter.ses.service.hub.source.website.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.hub.service.website.PartsService;
import com.redescooter.ses.api.hub.vo.website.SyncSalePartsDataEnter;
import com.redescooter.ses.service.hub.constant.SequenceName;
import com.redescooter.ses.service.hub.source.website.dm.SiteParts;
import com.redescooter.ses.service.hub.source.website.service.base.SitePartsService;
import com.redescooter.ses.starter.common.service.IdAppService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

/**
 * @Description 销售配件数据同步到官网的实现类
 * @Author Chris
 * @Date 2021/3/16 13:30
 */
@DubboService
public class PartsServiceImpl implements PartsService {

    @Autowired
    private SitePartsService sitePartsService;

    @DubboReference
    private IdAppService idAppService;

    /**
     * 销售配件数据同步到官网
     *
     * @param enter
     */
    @Override
    @Async
    public GeneralResult syncSalePartsData(SyncSalePartsDataEnter enter) {
        // 通过部品号,看之前是否同步过
        LambdaQueryWrapper<SiteParts> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SiteParts::getPartsNumber, enter.getPartsNumber());
        wrapper.last("limit 1");
        SiteParts siteParts = sitePartsService.getOne(wrapper);
        if (null == siteParts) {
            // 没有同步过
            siteParts = new SiteParts();
            BeanUtils.copyProperties(enter, siteParts);
            siteParts.setId(idAppService.getId(SequenceName.SITE_PARTS));
            siteParts.setDr(0);
            sitePartsService.save(siteParts);
        } else {
            // 同步过,只修改状态即可
            siteParts.setStatus(enter.getStatus());
            sitePartsService.updateById(siteParts);
        }
        return new GeneralResult();
    }

}
