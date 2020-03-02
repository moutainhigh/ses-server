package com.redescooter.ses.web.ros.service.impl;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.dao.SalseRosServiceMapper;
import com.redescooter.ses.web.ros.service.SalseRosService;
import com.redescooter.ses.web.ros.vo.sales.ProductListEnter;
import com.redescooter.ses.web.ros.vo.sales.ProductListResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName:SalseRosServiceImpl
 * @description: SalseRosServiceImpl
 * @author: Alex
 * @Version：1.3
 * @create: 2020/02/28 13:04
 */
@Service
@Slf4j
public class SalseRosServiceImpl implements SalseRosService {

    @Autowired
    private SalseRosServiceMapper salseRosServiceMapper;

    /**
     * @param enter
     * @desc: 服务类型统计
     * @param: enter
     * @retrn: map
     * @auther: alex
     * @date: 2020/2/25 18:08
     * @Version: Ros 1.2
     */
    @Override
    public Map<String, Integer> countByServiceType(GeneralEnter enter) {
        Map<String,Integer> map=new HashMap<>();
        // 产品数量
        map.put("product",salseRosServiceMapper.productCount(enter));
        // 售后产品数量
        map.put("afterSale",salseRosServiceMapper.afterSaleCount(enter));
        // 增值服务数量
        map.put("service",0);
        return map;
    }

    /**
     * @param enter
     * @desc: 产品列表
     * @param: enter
     * @retrn: ProductListResult
     * @auther: alex
     * @date: 2020/2/25 18:08
     * @Version: Ros 1.2
     */
    @Override
    public PageResult<ProductListResult> productList(ProductListEnter enter) {
        int count=salseRosServiceMapper.productListCount(enter);
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        return PageResult.create(enter,count,salseRosServiceMapper.productList(enter));
    }

    /**
     * @param enter
     * @desc: 售后产品列表
     * @param: enter
     * @retrn: ProductListResult
     * @auther: alex
     * @date: 2020/2/25 18:08
     * @Version: Ros 1.2
     */
    @Override
    public PageResult<ProductListResult> afterSaleList(ProductListEnter enter) {
        int count=salseRosServiceMapper.afterSaleListCount(enter);
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        return PageResult.create(enter,count,salseRosServiceMapper.afterSaleList(enter));
    }
}
