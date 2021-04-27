package com.redescooter.ses.web.website.service.impl;

import com.google.common.collect.Lists;
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
import com.redescooter.ses.web.website.vo.product.ScooterPriceDetailResult;
import com.redescooter.ses.web.website.vo.product.ScooterPriceListResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
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
        // 返回结果
        List<ScooterPriceListResult> resultList = Lists.newArrayList();
        // 返回结果的详情
        List<ScooterPriceDetailResult> detailList = Lists.newArrayList();

        List<ScooterPriceListResult> list = scooterPurchaseMapper.getScooterPriceList();
        if (CollectionUtils.isNotEmpty(list)) {
            for (int i = 0; i < list.size(); i++) {
                for (int j = i + 1; j < list.size(); j++) {
                    ScooterPriceListResult first = list.get(i);
                    ScooterPriceListResult second = list.get(j);
                    // list对象的车型字段两两对比
                    if (first.getScooterBattery().equals(second.getScooterBattery())) {
                        ScooterPriceListResult result = new ScooterPriceListResult();

                        ScooterPriceDetailResult firstDetail = new ScooterPriceDetailResult();
                        firstDetail.setInstallmentTime(first.getList().get(0).getInstallmentTime());
                        firstDetail.setShouldPayPeriod(first.getList().get(0).getShouldPayPeriod());
                        detailList.add(firstDetail);

                        ScooterPriceDetailResult secondDetail = new ScooterPriceDetailResult();
                        secondDetail.setInstallmentTime(second.getList().get(0).getInstallmentTime());
                        secondDetail.setShouldPayPeriod(second.getList().get(0).getShouldPayPeriod());
                        detailList.add(secondDetail);

                        result.setScooterBattery(first.getScooterBattery());
                        result.setList(detailList);
                        result.setTax(first.getTax());
                        result.setPrepaidDeposit(first.getPrepaidDeposit());
                        resultList.add(result);
                    }
                }
            }
        }
        return resultList;
    }

}
