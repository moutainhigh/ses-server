package com.redescooter.ses.web.website.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.code.MainCode;
import com.redescooter.ses.web.website.constant.SequenceName;
import com.redescooter.ses.web.website.dm.SitePaymentType;
import com.redescooter.ses.web.website.enums.CommonStatusEnums;
import com.redescooter.ses.web.website.service.PaymentTypeService;
import com.redescooter.ses.web.website.service.base.SitePaymentTypeService;
import com.redescooter.ses.web.website.vo.payment.AddPaymentTypeEnter;
import com.redescooter.ses.web.website.vo.payment.PaymentTypeDetailsResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    @DubboReference
    private IdAppService idAppService;

    /**
     * 创建支付方式
     *
     * @param enter
     * @return
     */
    @Transactional
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
    public List<PaymentTypeDetailsResult> getPaymentTypeList(GeneralEnter enter) {

        List<PaymentTypeDetailsResult> listResult = new ArrayList<>();
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
        return listResult;
    }
}
