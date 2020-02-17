package com.redescooter.ses.web.delivery.service.express;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.delivery.vo.QueryExpressOrderByPageEnter;
import com.redescooter.ses.web.delivery.vo.QueryExpressOrderByPageResult;
import com.redescooter.ses.web.delivery.vo.QueryOrderDetailResult;
import com.redescooter.ses.web.delivery.vo.edorder.*;
import com.redescooter.ses.web.delivery.vo.excel.ExpressOrderExcleData;
import com.redescooter.ses.web.delivery.vo.excel.ImportExcelOrderEnter;
import com.redescooter.ses.web.delivery.vo.excel.ImportExcelOrderResult;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 14/1/2020 4:26 下午
 * @ClassName: EdOrderService
 * @Function: TODO
 */
public interface EdOrderService {


    /**
     * 快递订单模板下载
     *
     * @return
     */
    void download(HttpServletResponse response);

    /**
     * 表格订单导入
     *
     * @param enter
     * @return
     */
    ImportExcelOrderResult importOrders(ImportExcelOrderEnter enter);

    /**
     * 保存快递导入订单
     *
     * @param orderExcleDataList
     */
    void saveOrders(List<ExpressOrderExcleData> orderExcleDataList, GeneralEnter enter);

    /**
     * 快递订单状态统计
     *
     * @param enter
     * @return
     */
    Map<String, Integer> countStatus(GeneralEnter enter);

    /**
     * 快递订单列表分页查询
     *
     * @param enter
     * @return
     */
    PageResult<QueryExpressOrderByPageResult> list(QueryExpressOrderByPageEnter enter);

    /**
     * 订单详情查询
     *
     * @param enter
     * @return
     */
    QueryOrderDetailResult details(IdEnter enter);

    /**
     * @Description
     * @Author AlexLi
     * @Date 2020/2/3 13:46
     * @Param ExpressOrderMapEnter
     * @Return ExpressOrderMapResult
     * @method EdOrderService
     */
    ExpressOrderMapResult expressOrderMap(ExpressOrderMapEnter enter);

    /**
     * @Description
     * @Author: AlexLi
     * @Date: 2020/2/3 15:28
     * @Param: enter
     * @Return: QueryOrderDetailResult
     * @method: diverOrderInfor
     */
    DiverOrderInforResult diverOrderlistById(IdEnter enter);

    /**
     * @Description
     * @Author: AlexLi
     * @Date: 2020/2/13 22:42
     * @Param: enter
     * @Return: RefuseOrderDetailResult
     * @desc: 拒绝订单详情
     */
    RefuseOrderDetailResult refuseOrderDetail(IdEnter enter);

    /**
     * @Description
     * @Author: AlexLi
     * @Date: 2020/2/13 23:47
     * @Param: enter
     * @Return: generalResult
     * @desc: 修改订单状态
     */
    GeneralResult chanageExpressOrder(ChanageExpressOrderEnter enter);

    /**
     * 取消订单
     *
     * @param enter
     * @return
     */
    GeneralResult cancelOrder(IdEnter enter);
}
