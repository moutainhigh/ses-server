package com.redescooter.ses.web.delivery.service.express;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.delivery.vo.QueryExpressOrderByPageEnter;
import com.redescooter.ses.web.delivery.vo.QueryExpressOrderByPageResult;
import com.redescooter.ses.web.delivery.vo.excel.ExpressOrderExcleData;
import com.redescooter.ses.web.delivery.vo.excel.ImportExcelOrderEnter;
import com.redescooter.ses.web.delivery.vo.excel.ImportExcelOrderResult;

import javax.servlet.http.HttpServletRequest;
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
    void saveOrders(List<ExpressOrderExcleData> orderExcleDataList,GeneralEnter enter);

    /**
     * 快递订单状态统计
     * @param enter
     * @return
     */
    Map<String, Integer> countStatus(GeneralEnter enter);

    /**
     * 快递订单列表分页查询
     * @param enter
     * @return
     */
    PageResult<QueryExpressOrderByPageResult> list(QueryExpressOrderByPageEnter enter);



}
