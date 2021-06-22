package com.redescooter.ses.service.hub.source.operation.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.redescooter.ses.api.hub.service.operation.CodebaseService;
import com.redescooter.ses.api.hub.vo.operation.OpeSimInformation;
import com.redescooter.ses.service.hub.source.operation.service.base.OpeSimInformationService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description
 * @Author Chris
 * @Date 2021/6/21 20:21
 */
@DubboService
@Slf4j
@DS("operation")
public class CodebaseServiceImpl implements CodebaseService {

    @Autowired
    private OpeSimInformationService opeSimInformationService;

    /**
     * 保存sim卡信息
     */
    @Override
    @DS("operation")
    public void saveSim(OpeSimInformation enter) {
        opeSimInformationService.save(enter);
    }

}
