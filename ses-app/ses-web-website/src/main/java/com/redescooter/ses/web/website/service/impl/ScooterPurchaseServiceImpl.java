package com.redescooter.ses.web.website.service.impl;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.web.website.dao.ScooterPurchaseMapper;
import com.redescooter.ses.web.website.service.ScooterPurchaseService;
import com.redescooter.ses.web.website.vo.product.ModelPriceResult;
import com.redescooter.ses.web.website.vo.product.ProductsResult;
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

    /**
     * 车辆价格列表
     *
     * @param enter
     * @return
     */
    @Override
    public List<ModelPriceResult> modelPriceList(GeneralEnter enter) {
        return scooterPurchaseMapper.modelPriceList(enter);
    }

    /**
     * 产品详情
     *
     * @param enter
     * @return
     */
    @Override
    public List<ProductsResult> getProductDetails(IdEnter enter) {
        return scooterPurchaseMapper.getProductDetails(enter);
    }


}
