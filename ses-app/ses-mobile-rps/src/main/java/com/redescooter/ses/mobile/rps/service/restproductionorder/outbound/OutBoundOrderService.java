package com.redescooter.ses.mobile.rps.service.restproductionorder.outbound;

import com.baomidou.mybatisplus.extension.api.R;
import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.*;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.orderflow.ProductOutWhDetailEnter;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.outbound.*;

import java.util.List;
import java.util.Map;

/**
 *  @author: alex
 *  @Date: 2020/10/23 12:12
 *  @version：V ROS 1.8.3
 *  @Description:
 */
public interface OutBoundOrderService {
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
    * @Author: enter
    * @Date:   2020/11/3 2:28 下午
    * @Param:  enter
    * @Return: Map
    * @des： 单据类型统计
    */
    Map<Integer,Integer> countByOrderType(GeneralEnter enter);
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
    * @Date:   2020/11/2 5:40 下午
    * @Param:  enter
    * @Return: List<OutboundDetailProductResult>
    * @desc: 详情中产品信息
    */
    List<OutboundDetailProductResult> detailProductList(IdEnter enter);
    /**
    * @Description
    * @Author: alex
    * @Date:   2020/11/2 2:48 下午
    * @Param:  enter
    * @Return: ProductDetailResult
    * @desc: 产品详情
    */
    ProductDetailResult productOutWhDetail(ProductOutWhDetailEnter enter);
    /**
    * @Description
    * @Author: alex
    * @Date:   2020/11/2 2:51 下午
    * @Param:  enter
    * @Return: ProductQcTempleteResult
    * @desc: 质检模版
    */
    List<ProductQcTempleteItemResult> qcTemplete(QcTempleteEnter enter);
    /**
    * @Description
    * @Author: alex
    * @Date:   2020/11/2 3:28 下午
    * @Param:  SaveQcResultEnter
    * @Return: GeneralResult
    * @desc:  保存质检结果
    */
    BooleanResult saveQcResult(SaveQcResultEnter enter);
}
