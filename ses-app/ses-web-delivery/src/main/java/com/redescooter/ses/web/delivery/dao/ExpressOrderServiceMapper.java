package com.redescooter.ses.web.delivery.dao;

import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.delivery.vo.QueryExpressOrderByPageEnter;
import com.redescooter.ses.web.delivery.vo.QueryExpressOrderByPageResult;

import java.util.List;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 2/2/2020 11:09 上午
 * @ClassName: ExpressOrderServiceMapper
 * @Function: TODO
 */
public interface ExpressOrderServiceMapper {

    /**
     * 订单状态统计
     *
     * @param enter
     * @return
     */
    List<CountByStatusResult> countByStatus(GeneralEnter enter);

    /**
     * 快递订单列表分页总数查询
     *
     * @param enter
     * @return
     */
    int listCount(QueryExpressOrderByPageEnter enter);

    /**
     * 快递订单列表查询
     *
     * @param enter
     * @return
     */
    List<QueryExpressOrderByPageResult> list(QueryExpressOrderByPageEnter enter);
}
