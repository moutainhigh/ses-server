package com.redescooter.ses.service.hub.source.website.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.hub.service.website.SyncPriceService;
import com.redescooter.ses.api.hub.vo.website.SyncSalePriceDataEnter;
import com.redescooter.ses.service.hub.constant.SequenceName;
import com.redescooter.ses.service.hub.source.website.dao.SiteProductPriceMapper;
import com.redescooter.ses.service.hub.source.website.dm.SitePaymentType;
import com.redescooter.ses.service.hub.source.website.dm.SiteProductModel;
import com.redescooter.ses.service.hub.source.website.dm.SiteProductPrice;
import com.redescooter.ses.service.hub.source.website.service.base.SitePaymentTypeService;
import com.redescooter.ses.service.hub.source.website.service.base.SiteProductModelService;
import com.redescooter.ses.service.hub.source.website.service.base.SiteProductPriceService;
import com.redescooter.ses.starter.common.service.IdAppService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
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

    @Autowired
    private SitePaymentTypeService sitePaymentTypeService;

    @Autowired
    private SiteProductPriceMapper siteProductPriceMapper;

    @DubboReference
    private IdAppService idAppService;

    /**
     * ??????????????????,????????????????????????????????????
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    @DS("website")
    public GeneralResult syncDeleteSalePrice(String scooterBattery, Integer type, Integer period) {
        log.info("??????hub????????????????????????");
        String modelName = scooterBattery.substring(0, scooterBattery.indexOf("+"));
        Long modelId;

        LambdaQueryWrapper<SiteProductModel> qw = new LambdaQueryWrapper<>();
        qw.eq(SiteProductModel::getDr, Constant.DR_FALSE);
        qw.eq(SiteProductModel::getStatus, 1);
        qw.eq(SiteProductModel::getProductModelName, modelName);
        qw.orderByDesc(SiteProductModel::getCreatedTime);
        qw.last("limit 1");
        SiteProductModel productModel = siteProductModelService.getOne(qw);
        if (null != productModel) {
            log.info("?????????,??????modelName?????????modelId");
            modelId = productModel.getId();
            log.info("modelId???:[{}]", modelId);

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
     * ??????????????????,??????????????????
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    @DS("website")
    public GeneralResult syncSalePrice(SyncSalePriceDataEnter enter) {
        log.info("??????hub??????????????????");
        String scooterBattery = enter.getScooterBattery();
        String modelName = scooterBattery.substring(0, scooterBattery.indexOf("+"));
        Long modelId = null;

        LambdaQueryWrapper<SiteProductModel> qw = new LambdaQueryWrapper<>();
        qw.eq(SiteProductModel::getDr, Constant.DR_FALSE);
        qw.eq(SiteProductModel::getStatus, 1);
        qw.eq(SiteProductModel::getProductModelName, modelName);
        qw.orderByDesc(SiteProductModel::getCreatedTime);
        qw.last("limit 1");
        SiteProductModel productModel = siteProductModelService.getOne(qw);
        if (null != productModel) {
            log.info("?????????,??????modelName?????????modelId");
            modelId = productModel.getId();
        }
        log.info("modelId???:[{}]", modelId);

        if (null != modelId) {
            SiteProductPrice model = new SiteProductPrice();
            model.setId(idAppService.getId(SequenceName.SITE_PRODUCT_PRICE));
            model.setDr(Constant.DR_FALSE);
            model.setStatus(1);
            model.setProductModelId(modelId);
            model.setBattery(scooterBattery.substring(scooterBattery.indexOf("+") + 1));
            model.setPriceType(enter.getType());

            // ???????????????????????????
            if (enter.getType() == 1 || enter.getType() == 3) {
                model.setInstallmentTime(String.valueOf(enter.getPeriod()));
                model.setShouldPayPeriod(enter.getShouldPayPeriod());

                // ??????
                BigDecimal deposit = enter.getDeposit();
                // ??????(??????-?????????1??????)
                //Integer period = enter.getPeriod() - 1;
                // ??????
                Integer period = enter.getPeriod();
                // ????????????*??????
                BigDecimal balance = enter.getShouldPayPeriod().multiply(new BigDecimal(String.valueOf(period)));
                BigDecimal price = deposit.add(balance);
                model.setPrice(price);
            } else if (enter.getType() == 2) {
                // ????????????
                BigDecimal deposit = enter.getDeposit();
                BigDecimal balance = enter.getBalance();
                BigDecimal price = balance;
                model.setPrice(price);
            }
            model.setTax(enter.getTax());
            model.setEffectiveTime(new Date());
            model.setCurrencyType("??????");
            model.setCurrencyUnit("???");
            model.setPrepaidDeposit(enter.getDeposit());
            model.setCountryCode("33");
            model.setCountryCity("fr");
            model.setCountryLanguage("fr");
            model.setCreatedBy(0L);
            model.setCreatedTime(new Date());
            siteProductPriceService.save(model);
        }

        // ??????????????????
        LambdaQueryWrapper<SitePaymentType> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SitePaymentType::getDr, Constant.DR_FALSE);
        wrapper.eq(SitePaymentType::getStatus, 1);
        List<SitePaymentType> paymentTypeList = sitePaymentTypeService.list(wrapper);
        if (CollectionUtils.isEmpty(paymentTypeList)) {

            List<SitePaymentType> modelList = Lists.newArrayList();

            // ??????3???
            for (int i = 1; i <= 3; i++) {
                SitePaymentType model = new SitePaymentType();
                model.setId(idAppService.getId(SequenceName.SITE_PAYMENT_TYPE));
                model.setDr(Constant.DR_FALSE);
                model.setStatus(1);

                if (i == 1) {
                    model.setPaymentName("????????????");
                } else if (i == 2) {
                    model.setPaymentName("????????????");
                } else if (i == 3) {
                    model.setPaymentName("????????????");
                }

                model.setPaymentCode(String.valueOf(i));
                model.setRemark(model.getPaymentName());
                model.setRevision(0);
                model.setCreatedBy(0L);
                model.setCreatedTime(new Date());
                modelList.add(model);
            }
            sitePaymentTypeService.saveBatch(modelList);
        }
        return new GeneralResult();
    }

    @GlobalTransactional(rollbackFor = Exception.class)
    @DS("website")
    @Override
    public GeneralResult synchronizeDeposit(SyncSalePriceDataEnter enter) {
        SiteProductPrice siteProductPrice = new SiteProductPrice();
        siteProductPrice.setPrepaidDeposit(enter.getDeposit());
        siteProductPriceMapper.synchronizeDeposit(siteProductPrice);
        return new GeneralResult(enter.getRequestId());
    }

}
