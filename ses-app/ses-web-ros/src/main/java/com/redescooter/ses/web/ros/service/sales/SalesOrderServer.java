package com.redescooter.ses.web.ros.service.sales;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.vo.sales.ColorCountResult;
import com.redescooter.ses.web.ros.vo.sales.SalesOrderDetailsResult;
import com.redescooter.ses.web.ros.vo.sales.SalesOrderEnter;
import com.redescooter.ses.web.ros.vo.sales.SalesOrderListResult;

import java.util.List;
import java.util.Map;

/**
 * @program: ses-server
 * @description: 销售订单接口服务
 * @author: Jerry
 * @created: 2020/09/29 20:26
 */
public interface SalesOrderServer {

    /**
     * 销售订单状态统计
     *
     * @param enter
     * @return
     */
    Map<String, Integer> countStatus(GeneralEnter enter);

    /**
     * 颜色统计
     *
     * @param enter
     * @return
     */
    List<ColorCountResult> colorCount(GeneralEnter enter);

    /**
     * 支付状态统计
     *
     * @param enter
     * @return
     */
    Map<String, Integer> payStatusCount(GeneralEnter enter);

    /**
     * 销售单列表
     *
     * @param enter
     * @return
     */
    PageResult<SalesOrderListResult> list(SalesOrderEnter enter);

    /**
     * 销售订单详情
     *
     * @param enter
     * @param id
     * @return
     */
    SalesOrderDetailsResult details(IdEnter enter);

    /**
     * 销售订单添加或者删除标签
     *
     * @param enter
     * @param id
     * @return
     */
    GeneralResult labels(IdEnter enter);

    /**
     * 取消销售订单提醒
     *
     * @param enter
     * @param id
     * @return
     */
    GeneralResult cancelWarn(IdEnter enter);

}
