package com.redescooter.ses.mobile.rps.service.restproductionorder.orderflow.impl;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.mobile.rps.constant.SequenceName;
import com.redescooter.ses.mobile.rps.dm.OpeOrderStatusFlow;
import com.redescooter.ses.mobile.rps.service.base.OpeOrderStatusFlowService;
import com.redescooter.ses.mobile.rps.service.restproductionorder.orderflow.OrderStatusFlowService;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.orderflow.OrderStatusFlowEnter;
import com.redescooter.ses.starter.common.service.IdAppService;
import jdk.nashorn.internal.ir.annotations.Reference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 *  @author: alex
 *  @Date: 2020/10/27 19:00
 *  @version：V ROS 1.8.3
 *  @Description:
 */
@Service
@Slf4j
public class OrderStatusFlowServiceImpl implements OrderStatusFlowService {

    @Autowired
    private OpeOrderStatusFlowService opeOrderStatusFlowService;

    @Reference
    private IdAppService idAppService;

    @Transactional
    @Override
    public GeneralResult save(OrderStatusFlowEnter enter) {
        OpeOrderStatusFlow opeOrderStatusFlow = new OpeOrderStatusFlow();
        BeanUtils.copyProperties(enter, opeOrderStatusFlow);
        if (enter.getId() == null || enter.getId() == 0) {
            opeOrderStatusFlow.setId(idAppService.getId(SequenceName.OPE_ORDER_STATUS_FLOW));
            opeOrderStatusFlow.setDr(0);
            opeOrderStatusFlow.setCreatedBy(enter.getUserId());
            opeOrderStatusFlow.setCreatedTime(new Date());
        }
        opeOrderStatusFlow.setCreatedBy(enter.getUserId());
        opeOrderStatusFlow.setCreatedTime(new Date());
        opeOrderStatusFlow.setUpdatedBy(enter.getUserId());
        opeOrderStatusFlow.setUpdatedTime(new Date());
        opeOrderStatusFlowService.saveOrUpdate(opeOrderStatusFlow);
        return new GeneralResult(enter.getRequestId());
    }
}
