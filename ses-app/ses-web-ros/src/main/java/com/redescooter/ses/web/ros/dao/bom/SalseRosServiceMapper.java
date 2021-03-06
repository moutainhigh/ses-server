package com.redescooter.ses.web.ros.dao.bom;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.web.ros.dm.OpeRegionalPriceSheet;
import com.redescooter.ses.web.ros.vo.bom.ProductPriceHistroyListEnter;
import com.redescooter.ses.web.ros.vo.bom.sales.*;

import java.util.List;

/**
 * @ClassName:SalseRosServiceMapper
 * @description: SalseRosServiceMapper
 * @author: Alex
 * @Version：1.3
 * @create: 2020/02/28 13:05
 */
public interface SalseRosServiceMapper {

    /**
     * 销售产品统计
     *
     * @param enter
     * @return
     */
    int productCount(GeneralEnter enter);

    /**
     * 销售售后产品统计
     *
     * @param enter
     * @return
     */
    int afterProductCount(GeneralEnter enter);

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
     *
     * @param enter
     * @return
     */
    Integer afterSaleListCount(ProductListEnter enter);

    /**
     * 售后产品列表
     *
     * @param enter
     * @return
     */
    List<ProductListResult> afterSaleList(ProductListEnter enter);

    /**
     * 产品子条目
     * 根据产品主键
     *
     * @param enter
     * @return
     */
    List<SubentryProductResult> productItems(IdEnter enter);

    /**
     * 查询产品的报价
     *
     * @param enter
     * @return
     */
    List<OpeRegionalPriceSheet> productPriceList(SccPriceEnter enter);

    /**
     * 产品价格历史列表
     *
     * @param enter
     * @return
     */
    int sccPriceHistroyCount(ProductPriceHistroyListEnter enter);

    /**
     * 产品价格历史列表
     *
     * @param enter
     * @return
     */
    List<SccPriceResult> sccPriceHistroyList(ProductPriceHistroyListEnter enter);
}
