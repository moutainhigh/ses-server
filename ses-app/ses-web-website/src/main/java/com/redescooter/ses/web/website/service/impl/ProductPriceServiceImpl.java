package com.redescooter.ses.web.website.service.impl;

import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.web.website.constant.SequenceName;
import com.redescooter.ses.web.website.dm.SiteProduct;
import com.redescooter.ses.web.website.dm.SiteProductPrice;
import com.redescooter.ses.web.website.enums.CommonStatusEnums;
import com.redescooter.ses.web.website.enums.PaymentTypeEnums;
import com.redescooter.ses.web.website.service.ProductPriceService;
import com.redescooter.ses.web.website.service.base.SiteProductPriceService;
import com.redescooter.ses.web.website.vo.product.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author jerry
 * @Date 2021/1/6 4:47 上午
 * @Description 产品价格服务
 **/
@Slf4j
@Service
public class ProductPriceServiceImpl implements ProductPriceService {

    @Reference
    private IdAppService idAppService;

    @Autowired(required = true)
    private SiteProductPriceService siteProductPriceService;


    /**
     * 创建产品价格
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public Boolean addProductPrice(AddProductPriceEnter enter) {
        SiteProductPrice addProductPriceVO = new SiteProductPrice();
        addProductPriceVO.setId(idAppService.getId(SequenceName.SITE_PRODUCT_PRICE));
        addProductPriceVO.setDr(Constant.DR_FALSE);
        addProductPriceVO.setStatus(String.valueOf(CommonStatusEnums.NORMAL.getValue()));
        addProductPriceVO.setProductId(enter.getProductId());
        addProductPriceVO.setPriceType(enter.getPriceType());
        if (enter.getPriceType().endsWith(String.valueOf(PaymentTypeEnums.BY_STAGES.getValue()))) {
            addProductPriceVO.setInstallmentTime(enter.getInstallmentTime());
        } else {
            addProductPriceVO.setInstallmentTime("0");
        }
        addProductPriceVO.setPrice(enter.getPrice());
        addProductPriceVO.setEffectiveTime(new Date());
        addProductPriceVO.setCurrencyType("欧元");
        addProductPriceVO.setCurrencyUnit("€");
        addProductPriceVO.setStandardCurrency("€");
        addProductPriceVO.setPrepaidDeposit(enter.getPrepaidDeposit());
        addProductPriceVO.setAmountDiscount(enter.getAmountDiscount());
        addProductPriceVO.setCountryCode("+33");
        addProductPriceVO.setCountryCity("France");
        addProductPriceVO.setCountryLanguage("Fr");
        addProductPriceVO.setSynchronizeFlag(false);
        addProductPriceVO.setRevision(0);
        addProductPriceVO.setCreatedBy(0L);
        addProductPriceVO.setCreatedTime(new Date());
        addProductPriceVO.setUpdatedBy(0L);
        addProductPriceVO.setUpdatedTime(new Date());
        return siteProductPriceService.save(addProductPriceVO);
    }

    /**
     * 获取产品价格详情
     *
     * @param enter
     */
    @Override
    public ProductPriceDetailsResult getProductPriceDetails(IdEnter enter) {
        SiteProductPrice byId = siteProductPriceService.getById(enter.getId());
        ProductPriceDetailsResult result = new ProductPriceDetailsResult();
        if (byId != null) {
            BeanUtils.copyProperties(byId, result);
            result.setRequestId(enter.getRequestId());
        }
        return result;
    }

    /**
     * 获取产品价格列表
     *
     * @param enter
     */
    @Override
    public List<ProductPriceDetailsResult> getProductPriceList(GeneralEnter enter) {
        return null;
    }
}
