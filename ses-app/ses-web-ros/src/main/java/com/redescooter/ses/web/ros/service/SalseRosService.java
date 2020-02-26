package com.redescooter.ses.web.ros.service;

import java.util.List;
import java.util.Map;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.web.ros.vo.sales.ProductListEnter;
import com.redescooter.ses.web.ros.vo.sales.ProductListResult;
import com.redescooter.ses.web.ros.vo.sales.SccProductPriceEnter;
import com.redescooter.ses.web.ros.vo.sales.SccProductPriceResult;

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
    ProductListResult productList(ProductListEnter enter);

    /**
     * @desc: 产品报价
     * @param: enter
     * @retrn: SccProductPriceResult
     * @auther: alex
     * @date: 2020/2/25 18:16
     * @Version: Ros 1.2
     */
    SccProductPriceResult sccProductPrice(IdEnter enter);

    /**
     * @desc: 产品报价历史
     * @param: enter
     * @retrn: List<SccProductPriceResult>
     * @auther: alex
     * @date: 2020/2/25 18:18
     * @Version: Ros 1.2
     */
    List<SccProductPriceResult> sccProductPriceHistroy(GeneralEnter enter);

    GeneralResult saveSccProductPrice(SccProductPriceEnter enter);
}
