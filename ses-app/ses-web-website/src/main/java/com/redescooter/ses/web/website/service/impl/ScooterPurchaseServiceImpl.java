package com.redescooter.ses.web.website.service.impl;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.StringEnter;
import com.redescooter.ses.web.website.constant.SiteCacheConstants;
import com.redescooter.ses.web.website.dao.ScooterPurchaseMapper;
import com.redescooter.ses.web.website.service.ScooterPurchaseService;
import com.redescooter.ses.web.website.vo.product.ModelPriceResult;
import com.redescooter.ses.web.website.vo.parts.PartsDetailsResult;
import com.redescooter.ses.web.website.vo.product.ProductPartsDetailsResult;
import com.redescooter.ses.web.website.vo.product.ProductsResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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

    /**
     * 车辆价格列表
     *
     * @param enter
     * @return
     */
    @Cacheable(value = SiteCacheConstants.SCOOTER_MODEL_DETAILS)
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
    @Cacheable(value = SiteCacheConstants.PARTS_DETAILS)
    @Override
    public List<PartsDetailsResult> getPartsList(StringEnter enter) {
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

}
