package com.redescooter.ses.web.website.service.impl;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.web.website.dao.ScooterPurchaseMapper;
import com.redescooter.ses.web.website.service.ScooterPurchaseService;
import com.redescooter.ses.web.website.service.base.SiteProductModelService;
import com.redescooter.ses.web.website.service.base.SiteProductPriceService;
import com.redescooter.ses.web.website.vo.parts.PartsDetailsResult;
import com.redescooter.ses.web.website.vo.product.ModelPriceResult;
import com.redescooter.ses.web.website.vo.product.ProductPartsDetailsResult;
import com.redescooter.ses.web.website.vo.product.ProductsResult;
import com.redescooter.ses.web.website.vo.product.ScooterPriceListResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author jerry
 * @Date 2021/1/6 9:40 上午
 * @Description 官网车辆选购服务实现类
 **/
@Slf4j
@Service
public class ScooterPurchaseServiceImpl implements ScooterPurchaseService {

    @Autowired
    private ScooterPurchaseMapper scooterPurchaseMapper;

    @Autowired
    private SiteProductPriceService siteProductPriceService;

    @Autowired
    private SiteProductModelService siteProductModelService;

    /**
     * 车辆价格列表
     *
     * @param enter
     * @return
     */
    @Override
    public List<ModelPriceResult> modelAndPriceList(GeneralEnter enter) {
        return scooterPurchaseMapper.modelAndPriceList(enter);
    }

    /**
     * 产品详情
     *
     * @param enter
     * @return
     */

    @Override
    public List<ProductsResult> getProductDetailByModel(IdEnter enter) {
        return scooterPurchaseMapper.getProductDetailByModel(enter);
    }

    /**
     * 配件列表
     *
     * @param enter
     * @return
     */
    @Override
    public List<PartsDetailsResult> getPartsList(IdEnter enter) {
        return scooterPurchaseMapper.getPartsList(enter);
    }

    /**
     * 车辆参数配置
     *
     * @param enter
     * @return
     */
    @Override
    public List<ProductPartsDetailsResult> getScooterBatterysByProductId(IdEnter enter) {
        return scooterPurchaseMapper.getScooterConfigList(enter);
    }

    /**
     * 官网车型价格列表
     */
    @Override
    public List<ScooterPriceListResult> getScooterPriceList(GeneralEnter enter) {
        return scooterPurchaseMapper.getScooterPriceList();
        /*List<ScooterPriceListResult> resultList = Lists.newArrayList();
        LambdaQueryWrapper<SiteProductPrice> qw = new LambdaQueryWrapper<>();
        qw.eq(SiteProductPrice::getDr, Constant.DR_FALSE);
        qw.eq(SiteProductPrice::getStatus, 1);
        qw.eq(SiteProductPrice::getPriceType, 1);
        List<SiteProductPrice> list = siteProductPriceService.list(qw);
        if (CollectionUtils.isNotEmpty(list)) {
            for (SiteProductPrice price : list) {
                // 根据产品型号id获得产品型号name
                SiteProductModel productModel = siteProductModelService.getById(price.getProductModelId());
                String modelName = productModel.getProductModelName();
                ScooterPriceListResult model = new ScooterPriceListResult();
                model.setScooterBattery(modelName + "-" + price.getBattery());
                model.setInstallmentTime(price.getInstallmentTime());
                model.setShouldPayPeriod(price.getShouldPayPeriod());
                model.setTax(price.getTax());
                model.setPrepaidDeposit(price.getPrepaidDeposit());
                resultList.add(model);
            }
        }
        return resultList;*/
    }

}
