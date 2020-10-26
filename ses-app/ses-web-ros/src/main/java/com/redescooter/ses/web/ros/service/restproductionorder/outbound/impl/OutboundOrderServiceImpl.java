package com.redescooter.ses.web.ros.service.restproductionorder.outbound.impl;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.service.restproductionorder.outbound.OutboundOrderService;
import com.redescooter.ses.web.ros.vo.restproductionorder.outboundorder.OutboundOrderDetailResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.outboundorder.OutboundOrderListEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.outboundorder.OutboundOrderListResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 *  @author: alex
 *  @Date: 2020/10/22 13:27
 *  @version：V ROS 1.8.3
 *  @Description:
 */
@Slf4j
@Service
public class OutboundOrderServiceImpl implements OutboundOrderService {
    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/22 13:29
     * @Param: enter
     * @Return: Map
     * @desc: 出库单产品类型统计
     * @param enter
     */
    @Override
    public Map<String, Integer> countByType(GeneralEnter enter) {
        return null;
    }

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/22 13:42
     * @Param: enter
     * @Return: Map
     * @desc: 状态列表
     * @param enter
     */
    @Override
    public Map<String, Integer> statusList(GeneralEnter enter) {
        return null;
    }

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/22 13:50
     * @Param: enter
     * @Return: OutboundOrderListResult
     * @desc: 出库单列表
     * @param enter
     */
    @Override
    public PageResult<OutboundOrderListResult> list(OutboundOrderListEnter enter) {
        return null;
    }

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/22 13:52
     * @Param: enter
     * @Return: OutboundOrderDetailResult
     * @desc: 出库单列表
     * @param enter
     */
    @Override
    public OutboundOrderDetailResult detail(IdEnter enter) {
        return null;
    }
}
