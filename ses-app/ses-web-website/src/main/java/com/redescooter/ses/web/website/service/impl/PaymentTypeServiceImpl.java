package com.redescooter.ses.web.website.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.code.MainCode;
import com.redescooter.ses.web.website.constant.SequenceName;
import com.redescooter.ses.web.website.dm.SitePaymentType;
import com.redescooter.ses.web.website.dm.SiteProductPrice;
import com.redescooter.ses.web.website.enums.CommonStatusEnums;
import com.redescooter.ses.web.website.service.PaymentTypeService;
import com.redescooter.ses.web.website.service.base.SitePaymentTypeService;
import com.redescooter.ses.web.website.service.base.SiteProductPriceService;
import com.redescooter.ses.web.website.vo.payment.AddPaymentTypeEnter;
import com.redescooter.ses.web.website.vo.payment.FullPayResult;
import com.redescooter.ses.web.website.vo.payment.InstallmentPayResult;
import com.redescooter.ses.web.website.vo.payment.LeaseResult;
import com.redescooter.ses.web.website.vo.payment.PaymentTypeDetailsResult;
import com.redescooter.ses.web.website.vo.payment.PaymentTypeEnter;
import com.redescooter.ses.web.website.vo.payment.PaymentTypeResult;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author jerry
 * @Date 2021/1/6 8:36 下午
 * @Description 支付方式配置实现类
 **/
@Slf4j
@Service
public class PaymentTypeServiceImpl implements PaymentTypeService {

    @Autowired
    private SitePaymentTypeService sitePaymentTypeService;

    @Autowired
    private SiteProductPriceService siteProductPriceService;

    @DubboReference
    private IdAppService idAppService;

    /**
     * 创建支付方式
     *
     * @param enter
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult addPaymentType(AddPaymentTypeEnter enter) {
        SitePaymentType addPaymentType = new SitePaymentType();
        addPaymentType.setId(idAppService.getId(SequenceName.SITE_PAYMENT_TYPE));
        addPaymentType.setDr(Constant.DR_FALSE);
        addPaymentType.setStatus(CommonStatusEnums.NORMAL.getValue());
        addPaymentType.setPaymentName(enter.getPaymentName());
        addPaymentType.setPaymentCode(new StringBuffer().append("PT_").append(MainCode.generateByShuffle()).toString());
        if (StringUtils.isNotBlank(enter.getRemark())) {
            addPaymentType.setRemark(enter.getRemark());
        }
        if (StringUtils.isNotBlank(enter.getOtherParam())) {
            addPaymentType.setOtherParam(enter.getOtherParam());
        }
        addPaymentType.setRevision(0);
        addPaymentType.setSynchronizeFlag(false);
        addPaymentType.setCreatedBy(enter.getUserId());
        addPaymentType.setCreatedTime(new Date());
        addPaymentType.setUpdatedBy(enter.getUserId());

        sitePaymentTypeService.save(addPaymentType);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 获取支付方式详情
     *
     * @param enter
     */
    @Override
    public PaymentTypeDetailsResult getPaymentTypeDetails(IdEnter enter) {
        SitePaymentType paymentType = sitePaymentTypeService.getById(enter.getId());
        PaymentTypeDetailsResult result = new PaymentTypeDetailsResult();
        if (paymentType != null) {
            BeanUtils.copyProperties(paymentType, result);
            result.setRequestId(enter.getRequestId());
        }
        return result;
    }

    /**
     * 获取支付方式列表
     *
     * @param enter
     */
    @Override
    public PaymentTypeResult getPaymentTypeList(PaymentTypeEnter enter) {
        PaymentTypeResult result = new PaymentTypeResult();

        // 租赁的集合
        List<LeaseResult> leaseList = Lists.newArrayList();
        LambdaQueryWrapper<SiteProductPrice> qw = new LambdaQueryWrapper<>();
        qw.eq(SiteProductPrice::getDr, Constant.DR_FALSE);
        qw.eq(SiteProductPrice::getStatus, 1);
        qw.eq(SiteProductPrice::getPriceType, 1);
        qw.eq(SiteProductPrice::getProductModelId, enter.getModelId());
        qw.likeLeft(SiteProductPrice::getBattery, enter.getBattery());
        List<SiteProductPrice> list = siteProductPriceService.list(qw);
        if (CollectionUtils.isNotEmpty(list)) {
            for (SiteProductPrice price : list) {
                LeaseResult lease = new LeaseResult();
                lease.setInstallmentTime(price.getInstallmentTime());
                lease.setPrepaidDeposit(price.getPrepaidDeposit());
                lease.setShouldPayPeriod(price.getShouldPayPeriod());
                leaseList.add(lease);
            }
        }

        // 全款支付的对象
        FullPayResult fullPay = new FullPayResult();
        LambdaQueryWrapper<SiteProductPrice> lqw = new LambdaQueryWrapper<>();
        lqw.eq(SiteProductPrice::getDr, Constant.DR_FALSE);
        lqw.eq(SiteProductPrice::getStatus, 1);
        lqw.eq(SiteProductPrice::getPriceType, 2);
        lqw.eq(SiteProductPrice::getProductModelId, enter.getModelId());
        lqw.likeLeft(SiteProductPrice::getBattery, enter.getBattery());
        lqw.last("limit 1");
        SiteProductPrice price = siteProductPriceService.getOne(lqw);
        if (null != price) {
            fullPay.setPrepaidDeposit(price.getPrepaidDeposit());
            BigDecimal balance = price.getPrice().subtract(price.getPrepaidDeposit());
            fullPay.setBalance(balance);
        }

        // 分期支付的集合
        List<InstallmentPayResult> installmentPayList = Lists.newArrayList();
        LambdaQueryWrapper<SiteProductPrice> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SiteProductPrice::getDr, Constant.DR_FALSE);
        wrapper.eq(SiteProductPrice::getStatus, 1);
        wrapper.eq(SiteProductPrice::getPriceType, 3);
        wrapper.eq(SiteProductPrice::getProductModelId, enter.getModelId());
        wrapper.likeLeft(SiteProductPrice::getBattery, enter.getBattery());
        List<SiteProductPrice> priceList = siteProductPriceService.list(wrapper);
        if (CollectionUtils.isNotEmpty(priceList)) {
            for (SiteProductPrice model : priceList) {
                InstallmentPayResult instance = new InstallmentPayResult();
                instance.setInstallmentTime(model.getInstallmentTime());
                instance.setPrepaidDeposit(model.getPrepaidDeposit());
                installmentPayList.add(instance);
            }
        }

        result.setLeaseList(leaseList);
        result.setFullPay(fullPay);
        result.setInstallmentPayList(installmentPayList);
        return result;

        /*List<PaymentTypeDetailsResult> listResult = new ArrayList<>();
        List<SitePaymentType> list = sitePaymentTypeService.list(new QueryWrapper<SitePaymentType>().eq(SitePaymentType.COL_DR, Constant.DR_FALSE));
        if (list.size() > 0) {
            list.forEach(p -> {
                PaymentTypeDetailsResult result = new PaymentTypeDetailsResult();
                result.setPaymentTypeId(p.getId());
                result.setPaymentCode(p.getPaymentCode());
                result.setPaymentName(p.getPaymentName());
                result.setOtherParam(p.getOtherParam());
                listResult.add(result);
            });
        }
        return listResult;*/
    }
}
