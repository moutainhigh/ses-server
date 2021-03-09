package com.redescooter.ses.web.website.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.web.website.constant.SequenceName;
import com.redescooter.ses.web.website.dm.SiteProductPrice;
import com.redescooter.ses.web.website.enums.CommonStatusEnums;
import com.redescooter.ses.web.website.enums.PaymentTypeEnums;
import com.redescooter.ses.web.website.service.ProductPriceService;
import com.redescooter.ses.web.website.service.base.SiteProductPriceService;
import com.redescooter.ses.web.website.vo.product.AddProductPriceEnter;
import com.redescooter.ses.web.website.vo.product.ProductPriceDetailsResult;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @DubboReference
    private IdAppService idAppService;

    @Autowired
    private SiteProductPriceService siteProductPriceService;

    /**
     * 创建产品价格
     *
     * @param enter
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult addProductPrice(AddProductPriceEnter enter) {
        SiteProductPrice addProductPriceVO = new SiteProductPrice();
        addProductPriceVO.setId(idAppService.getId(SequenceName.SITE_PRODUCT_PRICE));
        addProductPriceVO.setDr(Constant.DR_FALSE);
        addProductPriceVO.setStatus(CommonStatusEnums.NORMAL.getValue());
        addProductPriceVO.setProductModelId(enter.getProductModelId());
        addProductPriceVO.setStartPrice(enter.getStartPrice());
        addProductPriceVO.setPriceType(PaymentTypeEnums.checkValue(enter.getPriceType()));
        if (enter.getPriceType() == PaymentTypeEnums.BY_STAGES.getValue()) {
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
        addProductPriceVO.setCreatedBy(enter.getUserId());
        addProductPriceVO.setCreatedTime(new Date());
        addProductPriceVO.setUpdatedBy(enter.getUserId());
        addProductPriceVO.setUpdatedTime(new Date());
        siteProductPriceService.save(addProductPriceVO);
        return new GeneralResult(enter.getRequestId());
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
     * 移除产品价格详情
     *
     * @param enter
     */
    @Override
    public GeneralResult removeProductPrice(IdEnter enter) {
        siteProductPriceService.removeById(enter.getId());
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 获取产品价格列表
     *
     * @param enter
     */
    @Override
    public List<ProductPriceDetailsResult> getProductPriceList(GeneralEnter enter) {

        List<ProductPriceDetailsResult> resultList = new ArrayList<>();
        List<SiteProductPrice> list = siteProductPriceService.list(new QueryWrapper<SiteProductPrice>().eq(SiteProductPrice.COL_DR, 0));

        if (list.size() > 0) {
            list.forEach(pc -> {
                ProductPriceDetailsResult result = new ProductPriceDetailsResult();
                BeanUtils.copyProperties(pc, result);
                resultList.add(result);
            });
        }
        return resultList;
    }
}
