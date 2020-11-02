package com.redescooter.ses.mobile.rps.service.restproductionorder.invoice;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.ProductQcTempleteResult;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.QcTempleteEnter;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.SaveQcResultEnter;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.outbound.OutboundDetailResult;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.outbound.OutboundOrderEnter;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.outbound.OutboundOrderResult;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.outbound.ProductDetailResult;

import java.util.Map;

/**
 *  @author: alex
 *  @Date: 2020/10/23 12:12
 *  @version：V ROS 1.8.3
 *  @Description:
 */
public interface InvoiceOrderService {
    /**
    * @Description
    * @Author: alex
    * @Date:   2020/11/2 12:04 下午
    * @Param:  enter
    * @Return: Map
     * @desc: countByProductType
     */
    Map<Integer,Integer> countByProductType(GeneralEnter enter);
    /**
    * @Description
    * @Author: alex
    * @Date:   2020/11/2 1:12 下午
    * @Param:  enter
    * @Return: OutboundOrderResult
    * @desc: 出库单列表
    */
    PageResult<OutboundOrderResult> list(OutboundOrderEnter enter);

    /**
    * @Description
    * @Author: alex
    * @Date:   2020/11/2 1:25 下午
    * @Param:  enter
    * @Return: GeneralResult
    * @desc: 开始质检
    */
    GeneralResult startQc(IdEnter enter);

    /**
    * @Description
    * @Author: alex
    * @Date:   2020/11/2 1:37 下午
    * @Param:  enter
    * @Return: OutboundDetailResult
    * @desc: 单据详情
    */
    OutboundDetailResult detail(IdEnter enter);
    /**
    * @Description
    * @Author: alex
    * @Date:   2020/11/2 2:48 下午
    * @Param:  enter
    * @Return: ProductDetailResult
    * @desc: 产品详情
    */
    ProductDetailResult productDetail(IdEnter enter);
    /**
    * @Description
    * @Author: alex
    * @Date:   2020/11/2 2:51 下午
    * @Param:  enter
    * @Return: ProductQcTempleteResult
    * @desc: 质检模版
    */
    ProductQcTempleteResult qcTemplete(QcTempleteEnter enter);
    /**
    * @Description
    * @Author: alex
    * @Date:   2020/11/2 3:28 下午
    * @Param:  SaveQcResultEnter
    * @Return: GeneralResult
    * @desc:  保存质检结果
    */
    GeneralResult saveQcResult(SaveQcResultEnter enter);
}
