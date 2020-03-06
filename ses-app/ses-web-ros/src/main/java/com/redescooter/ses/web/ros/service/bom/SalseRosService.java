package com.redescooter.ses.web.ros.service.bom;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.vo.bom.sales.ProductListEnter;
import com.redescooter.ses.web.ros.vo.bom.sales.ProductListResult;
import com.redescooter.ses.web.ros.vo.bom.sales.SalesServiceResult;
import com.redescooter.ses.web.ros.vo.bom.sales.SccPriceEnter;
import com.redescooter.ses.web.ros.vo.bom.sales.SccPriceResult;
import com.redescooter.ses.web.ros.vo.bom.ProductPriceHistroyListEnter;

import java.util.List;
import java.util.Map;

/**
 * @ClassName:SalseRosService
 * @description: SalseRosService
 * @author: Alex
 * @Version：1.2
 * @create: 2020/02/25 10:18
 */
public interface SalseRosService {

    /**
     * @desc: 服务类型统计
     * @param: enter
     * @retrn: map
     * @auther: alex
     * @date: 2020/2/25 18:08
     * @Version: Ros 1.2
     */
    Map<String,Integer> countByServiceType(GeneralEnter enter);

    /**
     * @desc: 产品列表
     * @param: enter
     * @retrn: ProductListResult
     * @auther: alex
     * @date: 2020/2/25 18:08
     * @Version: Ros 1.2
     */
    PageResult<ProductListResult> productList(ProductListEnter enter);

    /**
     * @desc: 售后产品列表
     * @param: enter
     * @retrn: ProductListResult
     * @auther: alex
     * @date: 2020/2/25 18:08
     * @Version: Ros 1.2
     */
    PageResult<ProductListResult> afterSaleList(ProductListEnter enter);

    /**
     * @desc: 销售产品描述
     * @param: enter
     * @retrn: SalesServiceResult
     * @auther: alex
     * @date: 2020/3/3 17:41
     * @Version: Ros 1.2
     */
    PageResult<SalesServiceResult> salesServiceList(PageEnter enter);

    /**
     * 产品类型
     *
     * @param enter
     * @return
     */
    List<String> productTypeList(GeneralEnter enter);

    /**
     * 销售产品报价
     *
     * @param enter
     * @return
     */
    GeneralResult editSalesProductPrice(SccPriceEnter enter);

    /**
     * 销售产品报价
     *
     * @param enter
     * @return
     */
    GeneralResult editAfterProductPrice(SccPriceEnter enter);

    /**
     * 销售产品报价
     *
     * @param enter
     * @return
     */
    GeneralResult editServiceProductPrice(SccPriceEnter enter);

    /**
     * 产品价格详情
     *
     * @param enter
     * @return
     */
    SccPriceResult priceDetail(IdEnter enter);

    /**
     * 价格列表
     *
     * @param enter
     * @return
     */
    PageResult<SccPriceResult> priceList(ProductPriceHistroyListEnter enter);
}
