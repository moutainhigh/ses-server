package com.redescooter.ses.web.ros.service.bom;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.vo.bom.ProductPriceHistroyListEnter;
import com.redescooter.ses.web.ros.vo.bom.sales.PriceUnitResult;
import com.redescooter.ses.web.ros.vo.bom.sales.SccPriceResult;
import com.redescooter.ses.web.ros.vo.bom.supplierChaim.EditProductPriceEnter;
import com.redescooter.ses.web.ros.vo.bom.supplierChaim.ProductPriceChartResult;
import com.redescooter.ses.web.ros.vo.bom.supplierChaim.SupplierChaimListEnter;
import com.redescooter.ses.web.ros.vo.bom.supplierChaim.SupplierChaimListResult;

import java.util.List;
import java.util.Map;

/**
 * @ClassName:SupplierChaimRosService
 * @description: SupplierChaimRosService
 * @author: Alex
 * @Version：1.3
 * @create: 2020/02/25 10:20
 */
public interface SupplierChaimRosService {

    /**
     * @desc: 类型统计
     * @param: enter
     * @retrn: map
     * @auther: alex
     * @date: 2020/2/25 14:41
     * @Version: Ros 1.2
     */
    Map<String, Integer> countByPartType(GeneralEnter enter);

    /**
     * @desc: 供应链列表
     * @param: enter
     * @retrn: SupplierChaimListResult
     * @auther: alex
     * @date: 2020/2/25 15:13
     * @Version: Ros 1.2
     */
    PageResult<SupplierChaimListResult> supplierChaimList(SupplierChaimListEnter enter);

    /**
     * @desc: 修改报价
     * @param: enter
     * @retrn: GeneralResult
     * @auther: alex
     * @date: 2020/2/25 15:42
     * @Version: Ros 1.2
     */
    GeneralResult editProductPrice(EditProductPriceEnter enter);

    /**
     * 产品报价详情
     *
     * @param id
     * @return
     */
    SccPriceResult productPriceDetail(IdEnter id);

    /**
     * 货币单位
     *
     * @param enter
     * @return
     */
    List<PriceUnitResult> currencyUnit(GeneralEnter enter);

    /**
     * @desc: 产品价格列表
     * @paam: enter
     * @retrn: ProductPriceResult
     * @auther: alex
     * @date: 2020/2/25 15:31
     * @Version: Ros 1.2
     */
    PageResult<SccPriceResult> productPriceHistroyList(ProductPriceHistroyListEnter enter);

    /**
     * @desc: 价格图表
     * @param: enter
     * @retrn: ProductPriceResult
     * @auther: alex
     * @date: 2020/2/25 15:37
     * @Version: Ros 1.2
     */
    ProductPriceChartResult productPriceHistroyChart(IdEnter enter);
}
