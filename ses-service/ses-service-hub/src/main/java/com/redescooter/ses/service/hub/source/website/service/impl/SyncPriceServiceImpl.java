package com.redescooter.ses.service.hub.source.website.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.hub.service.website.SyncPriceService;
import com.redescooter.ses.api.hub.vo.website.SyncSalePriceDataEnter;
import com.redescooter.ses.service.hub.constant.SequenceName;
import com.redescooter.ses.service.hub.source.website.dm.SiteProductModel;
import com.redescooter.ses.service.hub.source.website.dm.SiteProductPrice;
import com.redescooter.ses.service.hub.source.website.service.base.SiteProductModelService;
import com.redescooter.ses.service.hub.source.website.service.base.SiteProductPriceService;
import com.redescooter.ses.starter.common.service.IdAppService;
import io.seata.common.util.CollectionUtils;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Description
 * @Author Chris
 * @Date 2021/4/27 11:17
 */
@DubboService
@Slf4j
public class SyncPriceServiceImpl implements SyncPriceService {

    @Autowired
    private SiteProductPriceService siteProductPriceService;
    
    @Autowired
    private SiteProductModelService siteProductModelService;

    @DubboReference
    private IdAppService idAppService;

    /**
     * 同步销售价格,关闭的时候和删除的时候调
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    @DS("website")
    public GeneralResult syncDeleteSalePrice(String scooterBattery, Integer type, Integer period) {
        log.info("进入hub开始同步价格删除");
        Long modelId = null;
        LambdaQueryWrapper<SiteProductModel> qw = new LambdaQueryWrapper<>();
        qw.eq(SiteProductModel::getDr, Constant.DR_FALSE);
        qw.eq(SiteProductModel::getStatus, 1);
        List<SiteProductModel> list = siteProductModelService.list(qw);
        if (CollectionUtils.isNotEmpty(list)) {
            for (SiteProductModel o : list) {
                if (scooterBattery.contains(o.getProductModelName())) {
                    log.info("关闭时,通过modelName找到了modelId");
                    modelId = o.getId();
                    break;
                }
            }
        }

        if (null != modelId) {
            LambdaQueryWrapper<SiteProductPrice> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(SiteProductPrice::getDr, Constant.DR_FALSE);
            wrapper.eq(SiteProductPrice::getProductModelId, modelId);
            wrapper.eq(SiteProductPrice::getPriceType, type);
            if (null != period) {
                wrapper.eq(SiteProductPrice::getInstallmentTime, String.valueOf(period));
            }
            wrapper.last("limit 1");
            SiteProductPrice productPrice = siteProductPriceService.getOne(wrapper);
            if (null != productPrice) {
                siteProductPriceService.removeById(productPrice.getId());
            }
        }
        return new GeneralResult();
    }

    /**
     * 同步销售价格,开启的时候调
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    @DS("website")
    public GeneralResult syncSalePrice(SyncSalePriceDataEnter enter) {
        log.info("进入hub开始同步价格");
        String scooterBattery = enter.getScooterBattery();
        Long modelId = null;

        LambdaQueryWrapper<SiteProductModel> qw = new LambdaQueryWrapper<>();
        qw.eq(SiteProductModel::getDr, Constant.DR_FALSE);
        qw.eq(SiteProductModel::getStatus, 1);
        List<SiteProductModel> list = siteProductModelService.list(qw);
        if (CollectionUtils.isNotEmpty(list)) {
            for (SiteProductModel o : list) {
                if (scooterBattery.contains(o.getProductModelName())) {
                    log.info("开启时,通过modelName找到了modelId");
                    modelId = o.getId();
                    break;
                }
            }
        }

        if (null != modelId) {
            SiteProductPrice model = new SiteProductPrice();
            model.setId(idAppService.getId(SequenceName.SITE_PRODUCT_PRICE));
            model.setDr(Constant.DR_FALSE);
            model.setStatus(1);
            model.setProductModelId(modelId);
            model.setBattery(scooterBattery.substring(scooterBattery.indexOf("+") + 1));
            model.setPriceType(enter.getType());

            // 租借车辆和分期支付
            if (enter.getType() == 1 || enter.getType() == 3) {
                model.setInstallmentTime(String.valueOf(enter.getPeriod()));
                model.setShouldPayPeriod(enter.getShouldPayPeriod());

                // 定金
                BigDecimal deposit = enter.getDeposit();
                // 期数(期数-定金的1个月)
                Integer period = enter.getPeriod() - 1;
                // 每期应付*期数
                BigDecimal balance = enter.getShouldPayPeriod().multiply(new BigDecimal(String.valueOf(period)));
                BigDecimal price = deposit.add(balance);
                model.setPrice(price);
            } else if (enter.getType() == 2) {
                // 全款支付
                BigDecimal deposit = enter.getDeposit();
                BigDecimal balance = enter.getBalance();
                BigDecimal price = deposit.add(balance);
                model.setPrice(price);
            }
            model.setTax(enter.getTax());
            model.setEffectiveTime(new Date());
            model.setCurrencyType("欧元");
            model.setCurrencyUnit("€");
            model.setPrepaidDeposit(enter.getDeposit());
            model.setCountryCode("33");
            model.setCountryCity("fr");
            model.setCountryLanguage("fr");
            model.setCreatedBy(0L);
            model.setCreatedTime(new Date());
            siteProductPriceService.save(model);
        }
        return new GeneralResult();
    }

}
