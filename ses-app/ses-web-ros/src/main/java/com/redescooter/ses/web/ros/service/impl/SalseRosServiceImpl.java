package com.redescooter.ses.web.ros.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.dao.SalseRosServiceMapper;
import com.redescooter.ses.web.ros.service.SalseRosService;
import com.redescooter.ses.web.ros.vo.sales.ProductListEnter;
import com.redescooter.ses.web.ros.vo.sales.ProductListResult;
import com.redescooter.ses.web.ros.vo.sales.SccPriceEnter;
import com.redescooter.ses.web.ros.vo.sales.SccPriceResult;
import com.redescooter.ses.web.ros.vo.sales.ServiceListResult;

import lombok.extern.slf4j.Slf4j;

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
        return null;
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
     * @desc: 产品报价
     * @param: enter
     * @retrn: SccProductPriceResult
     * @auther: alex
     * @date: 2020/2/25 18:16
     * @Version: Ros 1.2
     */
    @Override
    public SccPriceResult productPriceDetail(IdEnter enter) {
        return null;
    }

    /**
     * @param enter
     * @desc: 产品报价历史
     * @param: enter
     * @retrn: List<SccProductPriceResult>
     * @auther: alex
     * @date: 2020/2/25 18:18
     * @Version: Ros 1.2
     */
    @Override
    public List<SccPriceResult> productPriceHistroy(IdEnter enter) {
        return null;
    }

    /**
     * @param enter
     * @desc: 保存产品报价
     * @param: enter
     * @retrn: GeneralResult
     * @auther: alex
     * @date: 2020/2/26 9:52
     * @Version: Ros 1.2
     */
    @Override
    public GeneralResult saveProductPrice(SccPriceEnter enter) {
        return null;
    }

    /**
     * @param enter
     * @desc: 服务列表
     * @paam: enter
     * @retrn: ServiceListResult
     * @auther: alex
     * @date: 2020/2/26 10:01
     * @Version: Ros 1.2
     */
    @Override
    public PageResult<ServiceListResult> serviceList(PageEnter enter) {
        return null;
    }

    /**
     * @param enter
     * @desc: 产品报价历史
     * @param: enter
     * @retrn: List<SccProductPriceResult>
     * @auther: alex
     * @date: 2020/2/25 18:18
     * @Version: Ros 1.2
     */
    @Override
    public List<SccPriceResult> servicePriceHistroy(IdEnter enter) {
        return null;
    }

    /**
     * @param enter
     * @desc: 保存产品报价
     * @param: enter
     * @retrn: GeneralResult
     * @auther: alex
     * @date: 2020/2/26 9:52
     * @Version: Ros 1.2
     */
    @Override
    public GeneralResult saveServicePrice(SccPriceEnter enter) {
        return null;
    }
}
