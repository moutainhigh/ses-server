package com.redescooter.ses.web.ros.service.sellsy.impl;

import com.redescooter.ses.api.common.enums.sellsy.SellsyMethodTypeEnums;
import com.redescooter.ses.web.ros.config.SellsyConfig;
import com.redescooter.ses.web.ros.service.sellsy.SellsyService;
import com.redescooter.ses.web.ros.vo.sellsy.enter.SellsyExecutionEnter;
import com.redescooter.ses.web.ros.vo.sellsy.result.SellsyGeneralResult;
import com.sellsy.apientities.SellsyResponseInfo;
import com.sellsy.coreConnector.SellsyApiRequest;
import com.sellsy.coreConnector.SellsyApiResponse;
import com.sellsy.coreConnector.SellsySpringRestExecutor;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @ClassName:SellsyServiceImpl
 * @description: SellsyServiceImpl
 * @author: Alex
 * @Version：1.3
 * @create: 2020/08/18 19:44
 */
@Log4j2
@Service
public class SellsyServiceImpl implements SellsyService {
    
    @Autowired
    private SellsyConfig sellsyConfig;
    
    /**
     * sellsy 具体执行器
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public SellsyGeneralResult sellsyExecution(SellsyExecutionEnter enter) {
        SellsySpringRestExecutor underTest = new SellsySpringRestExecutor(sellsyConfig.getConsumerToken(), sellsyConfig.getConsumerSecret(), sellsyConfig.getUserToken(), sellsyConfig.getUserSecret());
        SellsyApiRequest request = new SellsyApiRequest(enter.getMethod(), enter.getParams());
        SellsyApiResponse result=null;
        try {
            result = underTest.process(request);
        }catch (Exception e){
            System.out.println("--------------调用出现问题-----------------");
        }
        return new SellsyGeneralResult();
    }
    
    @Override
    public void test() {
        SellsyExecutionEnter sellsyExecutionEnter=new SellsyExecutionEnter();
        sellsyExecutionEnter.setMethod("Document.getList");
        sellsyExecutionEnter.setParams("");
        SellsyGeneralResult sellsyGeneralResult = sellsyExecution(sellsyExecutionEnter);
        System.out.println(sellsyGeneralResult);
    }
}
