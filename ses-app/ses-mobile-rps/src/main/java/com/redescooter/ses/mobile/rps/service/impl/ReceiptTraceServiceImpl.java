package com.redescooter.ses.mobile.rps.service.impl;

import com.redescooter.ses.api.common.vo.SaveNodeEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.mobile.rps.constant.SequenceName;
import com.redescooter.ses.mobile.rps.dm.OpeAllocateTrace;
import com.redescooter.ses.mobile.rps.dm.OpeAssembiyOrderTrace;
import com.redescooter.ses.mobile.rps.dm.OpePurchasTrace;
import com.redescooter.ses.mobile.rps.service.ReceiptTraceService;
import com.redescooter.ses.mobile.rps.service.base.OpeAllocateTraceService;
import com.redescooter.ses.mobile.rps.service.base.OpeAssembiyOrderTraceService;
import com.redescooter.ses.mobile.rps.service.base.OpePurchasTraceService;
import com.redescooter.ses.starter.common.service.IdAppService;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @ClassName:ReceiptTraceServiceImpl
 * @description: ReceiptTraceServiceImpl
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/22 19:16
 */
@Service
public class ReceiptTraceServiceImpl implements ReceiptTraceService {

    @Autowired
    private OpeAssembiyOrderTraceService opeAssembiyOrderTraceService;

    @Autowired
    private OpeAllocateTraceService opeAllocateTraceService;

    @Reference
    private IdAppService idAppService;
    @Autowired
    private OpePurchasTraceService opePurchasTraceService;

    /**
     * 保存节点
     *
     * @return
     */
    @Transactional
    @Override
    public GeneralResult saveAllocateNode(SaveNodeEnter enter) {
        opeAllocateTraceService.save(OpeAllocateTrace.builder()
                .id(idAppService.getId(SequenceName.OPE_ALLOCATE_B_TRACE))
                .dr(0)
                .tenantId(0L)
                .userId(enter.getUserId())
                .allocateId(enter.getId())
                .status(enter.getStatus())
                .event(enter.getEvent())
                .eventTime(new Date())
                .memo(StringUtils.isBlank(enter.getMemo()) == true ? null : enter.getMemo())
                .createBy(enter.getUserId())
                .createTime(new Date())
                .updateBy(enter.getUserId())
                .updateTime(new Date())
                .build());
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 保存组装单节点
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult saveAssemblyNode(SaveNodeEnter enter) {
        opeAssembiyOrderTraceService.save(OpeAssembiyOrderTrace.builder()
                .id(idAppService.getId(SequenceName.OPE_ALLOCATE_B_TRACE))
                .dr(0)
                .userId(enter.getUserId())
                .opeAssembiyOrderId(enter.getId())
                .status(enter.getStatus())
                .event(enter.getEvent())
                .eventTime(new Date())
                .memo(StringUtils.isBlank(enter.getMemo()) == true ? null : enter.getMemo())
                .createdBy(enter.getUserId())
                .createdTime(new Date())
                .updatedBy(enter.getUserId())
                .updatedTime(new Date())
                .build());
        return new GeneralResult(enter.getRequestId());
    }
    /**
     * 保存采购单节点
     *
     * @param enter
     * @return
     */

    @Transactional
    @Override
    public GeneralResult savePurchasingNode(SaveNodeEnter enter) {
        opePurchasTraceService.save(OpePurchasTrace.builder()
                .id(idAppService.getId(SequenceName.OPE_PURCHAS_TRACE))
                .dr(0)
                .tenantId(0L)
                .userId(enter.getUserId())
                .purchasId(enter.getId())
                .status(enter.getStatus())
                .event(enter.getEvent())
                .eventTime(new Date())
                .memo(StringUtils.isBlank(enter.getMemo()) == true ? null : enter.getMemo())
                .createBy(enter.getUserId())
                .createTime(new Date())
                .updateBy(enter.getUserId())
                .updateTime(new Date())
                .build());
        return new GeneralResult(enter.getRequestId());
    }
}
