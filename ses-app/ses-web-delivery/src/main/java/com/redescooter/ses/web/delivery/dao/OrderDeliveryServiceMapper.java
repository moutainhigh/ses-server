package com.redescooter.ses.web.delivery.dao;

import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.web.delivery.vo.DeliveryDetailsResult;
import com.redescooter.ses.web.delivery.vo.ListDeliveryPage;
import com.redescooter.ses.web.delivery.vo.ListDeliveryResult;
import com.redescooter.ses.web.delivery.vo.SelectDriverResult;

import java.util.List;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 3/1/2020 4:52 下午
 * @ClassName: OrderDeliveryServiceMapper
 * @Function: TODO
 */
public interface OrderDeliveryServiceMapper {
    /**
     * 配送单状态统计
     *
     * @param enter
     * @return
     */
    List<CountByStatusResult> countStatus(GeneralEnter enter);


    /**
     * 配送单总数
     *
     * @return
     */
    int listCount(ListDeliveryPage page);

    /**
     * 配送单分页列表
     *
     * @return
     */
    List<ListDeliveryResult> list(ListDeliveryPage page);

    /**
     * 订单详情
     *
     * @param enter
     * @return
     */
    DeliveryDetailsResult details(IdEnter enter);

    List<SelectDriverResult> selectDriverList(GeneralEnter enter);
}
