package com.redescooter.ses.web.ros.service.impl;

import com.redescooter.ses.api.common.enums.bom.BomCommonTypeEnums;
import com.redescooter.ses.api.common.enums.bom.CurrencyUnitEnums;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.PageEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.tool.utils.StringUtils;
import com.redescooter.ses.web.ros.dao.SalseRosServiceMapper;
import com.redescooter.ses.web.ros.service.SalseRosService;
import com.redescooter.ses.web.ros.vo.sales.ProductListEnter;
import com.redescooter.ses.web.ros.vo.sales.ProductListResult;
import com.redescooter.ses.web.ros.vo.sales.SalesServiceResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
        Map<String, Integer> map = new HashMap<>();
        // 产品数量
        map.put("product", 0);
        // 售后产品数量
        map.put("afterSale", 0);
        // 增值服务数量
        map.put("service", 0);
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
        int count = salseRosServiceMapper.productListCount(enter);
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        return PageResult.create(enter, count, salseRosServiceMapper.productList(enter));
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
        int count = salseRosServiceMapper.afterSaleListCount(enter);
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        return PageResult.create(enter, count, salseRosServiceMapper.afterSaleList(enter));
    }

    /**
     * @param enter
     * @desc: 销售产品描述
     * @param: enter
     * @retrn: SalesServiceResult
     * @auther: alex
     * @date: 2020/3/3 17:41
     * @Version: Ros 1.2
     */
    @Override
    public PageResult<SalesServiceResult> salesServiceList(PageEnter enter) {
        int count = 1;
        List<SalesServiceResult> result = new ArrayList<>();
        result.add(SalesServiceResult.builder()
                .id(1000000L)
                .service("License")
                .desc("车辆上牌")
                .productFrPrice(BigDecimal.ONE)
                .productFrUnit(CurrencyUnitEnums.FR.getValue())
                .productEnPrice(BigDecimal.ONE)
                .productEnUnit(CurrencyUnitEnums.EN.getValue())
                .refuseTime(new Date())
                .build());

        return PageResult.create(enter, count, result);
    }

    /**
     * 产品类型
     *
     * @param enter
     * @return
     */
    @Override
    public List<String> productTypeList(GeneralEnter enter) {
        List<String> result = new ArrayList<>();
        for (BomCommonTypeEnums item : BomCommonTypeEnums.values()) {
            if (!StringUtils.equals(item.getCode(), BomCommonTypeEnums.PARTS.getCode())) {
                result.add(item.getCode());
            }
        }
        return result;
    }
}
