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

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        List<ModelPriceResult> resultList = Lists.newArrayList();

        List<ModelPriceResult> modelPriceList = scooterPurchaseMapper.modelAndPriceList(enter);
        if (CollectionUtils.isNotEmpty(modelPriceList)) {
            Map<Long, List<ModelPriceResult>> collect = modelPriceList.stream().collect(Collectors.groupingBy(o -> o.getModelid()));
            for (Map.Entry<Long, List<ModelPriceResult>> map : collect.entrySet()) {
                Long modelId = map.getKey();
                List<ModelPriceResult> list = map.getValue();

                for (ModelPriceResult item : list) {
                    if (modelId.equals(item.getModelid())) {
                        resultList.add(item);
                        break;
                    }
                }
            }
        }
        return resultList;
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

        List<ScooterPriceListResult> priceList = scooterPurchaseMapper.getScooterPriceList();
        if (CollectionUtils.isNotEmpty(priceList)) {
            // 根据车型进行分组
            Map<String, List<ScooterPriceListResult>> collect = priceList.stream().collect(Collectors.groupingBy(o -> o.getScooterBattery()));
            for (Map.Entry<String, List<ScooterPriceListResult>> map : collect.entrySet()) {
                String key = map.getKey();
                List<ScooterPriceListResult> list = map.getValue();

                ScooterPriceListResult result = new ScooterPriceListResult();
                // 返回结果的详情
                List<ScooterPriceDetailResult> detailList = Lists.newArrayList();

                for (ScooterPriceListResult item : list) {
                    if (key.equals(item.getScooterBattery())) {
                        ScooterPriceDetailResult detail = new ScooterPriceDetailResult();
                        detail.setInstallmentTime(item.getList().get(0).getInstallmentTime() == null ? null : item.getList().get(0).getInstallmentTime());
                        detail.setShouldPayPeriod(item.getList().get(0).getShouldPayPeriod() == null ? null : item.getList().get(0).getShouldPayPeriod());
                        detailList.add(detail);
                    }
                }

                result.setTax(list.get(0).getTax() == null ? null : list.get(0).getTax());
                result.setPrepaidDeposit(list.get(0).getPrepaidDeposit() == null ? null : list.get(0).getPrepaidDeposit());
                result.setScooterBattery(key);
                result.setList(detailList);
                resultList.add(result);
            }
        }

        // 列表根据车型和电池数排序
        if (CollectionUtils.isNotEmpty(resultList)) {
            Collections.sort(resultList, (o1, o2) -> {
                int i = o1.getScooterBattery().compareTo(o2.getScooterBattery());
                return i;
            });
        }
        return resultList;
    }

}
