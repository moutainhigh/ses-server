package com.redescooter.ses.service.hub.source.website.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.redescooter.ses.api.common.constant.Constant;
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

import java.util.Date;

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
     */
    @Override
    @DS("website")
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
            siteParts.setDr(Constant.DR_FALSE);
            sitePartsService.save(siteParts);
        } else {
            // 同步过,修改其他属性
            SiteParts model = new SiteParts();
            BeanUtils.copyProperties(enter, model);
            model.setId(siteParts.getId());
            model.setUpdatedBy(enter.getUserId());
            model.setUpdatedTime(new Date());
            sitePartsService.updateById(model);
        }
        return new GeneralResult();
    }

    /**
     * ros删除销售配件数据,官网已同步的配件同样删除
     */
    @Override
    @DS("website")
    public GeneralResult syncDeleteData(String productCode) {
        LambdaQueryWrapper<SiteParts> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SiteParts::getPartsNumber, productCode);
        wrapper.last("limit 1");
        SiteParts siteParts = sitePartsService.getOne(wrapper);
        if (null != siteParts) {
            sitePartsService.removeById(siteParts.getId());
        }
        return new GeneralResult();
    }

}
