package com.redescooter.ses.web.ros.dao;

import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.web.ros.vo.sales.SccPriceResult;
import com.redescooter.ses.web.ros.vo.supplierChaim.ProductPriceChartResult;
import com.redescooter.ses.web.ros.vo.supplierChaim.ProductPriceHistroyListEnter;
import com.redescooter.ses.web.ros.vo.supplierChaim.SupplierChaimListEnter;
import com.redescooter.ses.web.ros.vo.supplierChaim.SupplierChaimListResult;

import java.util.List;

/**
 * @ClassName:SupplierChaimRosService
 * @description: SupplierChaimRosService
 * @author: Alex
 * @Version：1.3
 * @create: 2020/02/28 10:08
 */
public interface SupplierChaimRosServiceMapper {

    /**
     * @desc: 产品类型分类
     * @param: enter
     * @retrn: CountByStatusResult
     * @auther: alex
     * @date: 2020/2/28 10:12
     * @Version: Ros 1.2
     */
    List<CountByStatusResult> countByPartType(GeneralEnter enter);

    /**
     * @desc: 供应链列表
     * @param: enter
     * @retrn: int
     * @auther: alex
     * @date: 2020/2/28 10:49
     * @Version: Ros 1.2
     */
    int supplierChaimListCount(SupplierChaimListEnter enter);

    /**
     * @desc: 供应链列表
     * @param: enter
     * @retrn: SupplierChaimListResult
     * @auther: alex
     * @date: 2020/2/28 10:51
     * @Version: Ros 1.2
     */
    List<SupplierChaimListResult> supplierChaimList(SupplierChaimListEnter enter);

    /**
     * @desc: 供应链产品价格历史
     * @param: enter
     * @retrn: enter
     * @auther: alex
     * @date: 2020/3/2 15:40
     * @Version: Ros 1.2
     */
    int scPriceHistroyCount(ProductPriceHistroyListEnter enter);

    /**
     * @desc: 供应链产品价格历史
     * @param: enter
     * @retrn: List<SccPriceResult>
     * @auther: alex
     * @date: 2020/3/2 15:41
     * @Version: Ros 1.2
     */
    List<SccPriceResult> scPriceHistroyList(ProductPriceHistroyListEnter enter);

    /**
     * @desc: 销售产品产品价格历史
     * @param: enter
     * @retrn: int
     * @auther: alex
     * @date: 2020/3/2 15:49
     * @Version: Ros 1.2
     */
    int sccPriceHistroyCount(ProductPriceHistroyListEnter enter);

    /**
     * @desc: 销售产品产品价格历史
     * @param: enter
     * @retrn: List<SccPriceResult>
     * @auther: alex
     * @date: 2020/3/2 15:49
     * @Version: Ros 1.2
     */
    List<SccPriceResult> sccPriceHistroyList(ProductPriceHistroyListEnter enter);

    /**
     * @desc: 获取图表最值
     * @param: enter
     * @retrn: ProductPriceChartResult
     * @auther: alex
     * @date: 2020/3/2 16:20
     * @Version: Ros 1.2
     */
    ProductPriceChartResult productPriceHistroyChart(IdEnter enter);

    /**
     * @desc: 图表记录
     * @param: enter
     * @retrn: List<SccPriceResult>
     * @auther: alex
     * @date: 2020/3/2 16:24
     * @Version: Ros 1.2
     */
    List<SccPriceResult> productPriceHistroyChartList(IdEnter enter);
}
