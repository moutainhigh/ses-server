package com.redescooter.ses.mobile.rps.dao.restproductionorder.invoice;

import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.ProductQcTempleteItemResult;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.QcTempleteEnter;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.orderflow.ProductOutWhDetailEnter;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.outbound.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName:InvoiceOrderSrviceMapper
 * @description: InvoiceOrderSrviceMapper
 * @author: Alex
 * @Version：1.3
 * @create: 2020/11/02 15:49 
 */

public interface OutBoundOrderSrviceMapper {
    /**
     * @Description
     * @Author: alex
     * @Date: 2020/11/2 3:51 下午
     * @Param: enter
     * @Return: CountByStatusResult
     * @desc: 产品类型统计
     */
    List<CountByStatusResult> countByProductType(GeneralEnter enter);

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/11/2 4:25 下午
     * @Param: enter
     * @Return: int
     * @desc: 单据列表
     */
    int listCount(OutboundOrderEnter enter);

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/11/2 4:26 下午
     * @Param: enter
     * @Return: OutboundOrderResult
     * @desc: 列表
     */
    List<OutboundOrderResult> list(OutboundOrderEnter enter);

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/11/2 5:30 下午
     * @Param: enter
     * @Return: OutboundDetailResult
     * @desc: 出库单详情
     */
    OutboundDetailResult detail(IdEnter enter);

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/11/2 5:43 下午
     * @Param: enter
     * @Return: OutboundDetailProductResult
     * @desc: 详情 产品列表
     */
    List<OutboundDetailProductResult> detailProductListByPart(IdEnter enter);

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/11/2 5:44 下午
     * @Param: enter
     * @Return: OutboundDetailProductResult
     * @desc: 出库单详情组合产品列表
     */
    List<OutboundDetailProductResult> detailProductListByCombin(IdEnter enter);

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/11/2 5:45 下午
     * @Param: enter
     * @Return: OutboundDetailProductResult
     * @desc: 出库单详情组合车辆列表
     */
    List<OutboundDetailProductResult> detailProductListByScooter(IdEnter enter);

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/11/2 6:27 下午
     * @Param: enter
     * @Return: ProductDetailResult
     * @desc: 车辆出库详情
     */
    ProductDetailResult productOutWhDetailByScooter(ProductOutWhDetailEnter enter);

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/11/2 6:27 下午
     * @Param: enter
     * @Return: ProductDetailResult
     * @desc: 组合出库详情
     */
    ProductDetailResult productOutWhDetailByCombin(ProductOutWhDetailEnter enter);

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/11/2 6:27 下午
     * @Param: enter
     * @Return: ProductDetailResult
     * @desc: 部件出库详情
     */
    ProductDetailResult productOutWhDetailByPart(ProductOutWhDetailEnter enter);

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/11/2 7:29 下午
     * @Param: enter
     * @Return: ProductQcTempleteItemResult
     * @desc: 质检模版查询
     */
    List<ProductQcTempleteItemResult> qcTemplete(@Param("enter") QcTempleteEnter enter, @Param("produtionType") Integer produtionType);

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/11/3 1:35 下午
     * @Param: enter
     * @Return: ProductSnResult
     * @desc: 产品已出库列表
     */
    List<ProductSnResult> productOutWhDetailByScooterSnList(ProductOutWhDetailEnter enter);

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/11/3 1:35 下午
     * @Param: enter
     * @Return: ProductSnResult
     * @desc: 组合出库列表
     */
    List<ProductSnResult> productOutWhDetailByCombinSnList(ProductOutWhDetailEnter enter);

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/11/3 1:36 下午
     * @Param: enter
     * @Return: ProductSnResult
     * @desc: 产品部件列表
     */
    List<ProductSnResult> productOutWhDetailByPartSnList(ProductOutWhDetailEnter enter);

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/11/3 2:29 下午
     * @Param: enter
     * @Return: CountByStatusResult
     * @desc: 订单类型统计
     */
    List<CountByStatusResult> countByOrderType(GeneralEnter enter);
}
