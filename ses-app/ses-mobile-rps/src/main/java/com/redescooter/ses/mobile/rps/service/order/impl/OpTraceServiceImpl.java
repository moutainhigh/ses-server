package com.redescooter.ses.mobile.rps.service.order.impl;

import com.redescooter.ses.mobile.rps.constant.SequenceName;
import com.redescooter.ses.mobile.rps.dao.order.OpTraceMapper;
import com.redescooter.ses.mobile.rps.dm.OpeOpTrace;
import com.redescooter.ses.mobile.rps.service.order.OpTraceService;
import com.redescooter.ses.starter.common.service.IdAppService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author assert
 * @date 2021/1/18 11:20
 */
@Slf4j
@Service
public class OpTraceServiceImpl implements OpTraceService {

    @Reference
    private IdAppService idAppService;
    @Resource
    private OpTraceMapper opTraceMapper;


    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insertOpTrace(Long orderId, Integer orderType, Integer opType, String remark, Long userId) {
        OpeOpTrace opeOpTrace = OpeOpTrace.builder()
                .id(idAppService.getId(SequenceName.OPE_OP_TRACE))
                .dr(0)
                .relationId(orderId)
                .orderType(orderType)
                .opType(opType)
                .remark(remark)
                .createdBy(userId)
                .createdTime(new Date())
                .updatedBy(userId)
                .updatedTime(new Date())
                .build();

        return opTraceMapper.insertOpTrace(opeOpTrace);
    }
}
