package com.redescooter.ses.web.ros.service.impl;

import com.redescooter.ses.api.common.enums.factory.FactoryEventEnum;
import com.redescooter.ses.api.common.enums.factory.FactoryStatusEnum;
import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.dao.FactoryServiceMapper;
import com.redescooter.ses.web.ros.dm.OpeFactory;
import com.redescooter.ses.web.ros.dm.OpeFactoryTrace;
import com.redescooter.ses.web.ros.service.FactoryRosService;
import com.redescooter.ses.web.ros.service.base.OpeFactoryService;
import com.redescooter.ses.web.ros.service.base.OpeFactoryTraceService;
import com.redescooter.ses.web.ros.vo.factory.FactoryEditEnter;
import com.redescooter.ses.web.ros.vo.factory.FactoryPage;
import com.redescooter.ses.web.ros.vo.factory.FactoryResult;
import com.redescooter.ses.web.ros.vo.factory.FactorySaveEnter;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class FactoryRosServiceImpl implements FactoryRosService {

    @Autowired
    private OpeFactoryService factoryService;
    @Autowired
    private FactoryServiceMapper factoryServiceMapper;
    @Autowired
    private OpeFactoryTraceService factoryTraceService;

    @Reference
    private IdAppService idAppService;

    @Override
    public Map<String, Integer> countStatus(GeneralEnter enter) {

        List<CountByStatusResult> statusResults = factoryServiceMapper.countStatus(enter);

        Map<String, Integer> map = new HashMap<>();
        for (CountByStatusResult item : statusResults) {
            map.put(item.getStatus(), item.getTotalCount());
        }
        for (FactoryStatusEnum status : FactoryStatusEnum.values()) {
            if (!map.containsKey(status.getValue())) {
                map.put(status.getValue(), 0);
            }
        }
        return map;
    }

    @Transactional
    @Override
    public GeneralResult save(FactorySaveEnter enter) {

        OpeFactory factorySave = new OpeFactory();

        BeanUtils.copyProperties(enter, factorySave);

        factorySave.setId(idAppService.getId(SequenceName.OPE_FACTORY));
        factorySave.setDr(0);
        factorySave.setStatus(FactoryStatusEnum.NORMAL.getValue());
        factorySave.setTenantId(enter.getTenantId());
        factorySave.setUserId(enter.getUserId());
        factorySave.setOverdueFlag(0);
        factorySave.setUpdatedBy(enter.getUserId());
        factorySave.setUpdatedTime(new Date());
        factorySave.setUpdatedBy(enter.getUserId());
        factorySave.setUpdatedTime(new Date());

        boolean save = factoryService.save(factorySave);
        if (save) {
            saveFactoryTrace(FactoryEventEnum.CREATE.getValue(), factorySave);
        }
        return new GeneralResult(enter.getRequestId());
    }

    @Transactional
    @Override
    public GeneralResult edit(FactoryEditEnter enter) {

        OpeFactory factoryEdit = new OpeFactory();
        BeanUtils.copyProperties(enter, factoryEdit);
        factoryEdit.setUpdatedBy(enter.getUserId());
        factoryEdit.setUpdatedTime(new Date());

        boolean update = factoryService.updateById(factoryEdit);
        if (update) {
            saveFactoryTrace(FactoryEventEnum.MODIFY.getValue(), factoryEdit);
        }

        return new GeneralResult(enter.getRequestId());
    }

    @Override
    public FactoryResult details(IdEnter enter) {

        OpeFactory factory = factoryService.getById(enter.getId());

        FactoryResult factoryResult = new FactoryResult();
        Optional.ofNullable(factory).ifPresent(f -> {
            BeanUtils.copyProperties(f, factoryResult);
            factoryResult.setRequestId(enter.getRequestId());
        });

        return factoryResult;
    }

    @Override
    public PageResult<FactoryResult> list(FactoryPage page) {

        int count = factoryServiceMapper.listCount(page);
        if (count == 0) {
            return PageResult.createZeroRowResult(page);
        }

        List<FactoryResult> list = factoryServiceMapper.list(page);

        return PageResult.create(page, count, list);
    }

    @Transactional
    @Override
    public GeneralResult saveFactoryTrace(String event, OpeFactory factory) {
        OpeFactoryTrace trace = new OpeFactoryTrace();
        trace.setId(idAppService.getId(SequenceName.OPE_FACTORY_TRACE));
        trace.setDr(0);
        trace.setFactoryId(factory.getId());
        trace.setTenantId(factory.getTenantId());
        trace.setUserId(factory.getUserId());
        trace.setStatus(factory.getStatus());
        trace.setEvent(event);
        trace.setEventTime(new Date());
        trace.setReason(factory.getFactoryMemo());
        trace.setCreatedBy(factory.getUpdatedBy());
        trace.setCreatedTime(new Date());
        trace.setUpdatedBy(factory.getUpdatedBy());
        trace.setUpdatedTime(new Date());

        factoryTraceService.save(trace);

        return new GeneralResult();
    }

}
