package com.redescooter.ses.web.ros.dao;

import java.util.List;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.ros.vo.sales.ProductListEnter;
import com.redescooter.ses.web.ros.vo.sales.ProductListResult;

/**
 * @ClassName:SalseRosServiceMapper
 * @description: SalseRosServiceMapper
 * @author: Alex
 * @Version：1.3
 * @create: 2020/02/28 13:05
 */
public interface SalseRosServiceMapper {

    /**
     * @desc: 产品列表
     * @param: enter
     * @retrn: int
     * @auther: alex
     * @date: 2020/2/28 14:55
     * @Version: Ros 1.2
     */
    int productListCount(ProductListEnter enter);

    /**
     * @desc: 产品列表
     * @paam: enter
     * @retrn: List<ProductListResult>
     * @auther: alex
     * @date: 2020/2/28 14:55
     * @Version: Ros 1.2
     */
    List<ProductListResult> productList(ProductListEnter enter);

    /**
     * 售后产品列表
     * @param enter
     * @return
     */
    Integer afterSaleListCount(ProductListEnter enter);

    List<ProductListResult> afterSaleList(ProductListEnter enter);
}
