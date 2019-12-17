package com.redescooter.ses.service.common.service.impl;


import com.redescooter.ses.api.foundation.service.SequenceService;
import com.redescooter.ses.starter.common.service.IdAppService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

/**
 * @description: IdAppServiceImpl
 * @author: Darren
 * @create: 2019/08/16 14:39
 */
@Slf4j
@Service
public class IdAppServiceImpl implements IdAppService {

    @Reference
    private SequenceService sequenceService;

    @Override
    public long getId(String tableName) {
        try {
            return sequenceService.get(tableName);
        } catch (Exception e) {
            log.error("Get id  failure, tableName:" + tableName, e);
            throw new NullPointerException("Get id  failure, tableName:" + tableName);
        }
    }

}
