package com.redescooter.ses.web.ros.service.sellsy.impl;

import com.redescooter.ses.web.ros.config.SellsyConfig;
import com.redescooter.ses.web.ros.service.sellsy.SellsyService;
import com.redescooter.ses.web.ros.vo.sellsy.enter.SellsyExecutionEnter;
import com.redescooter.ses.web.ros.vo.sellsy.result.SellsyGeneralResult;
import com.sellsy.coreConnector.SellsyApiException;
import com.sellsy.coreConnector.SellsyApiRequest;
import com.sellsy.coreConnector.SellsyApiResponse;
import com.sellsy.coreConnector.SellsySpringRestExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.util.HashMap;


/**
 * @ClassName:SellsyServiceImpl
 * @description: SellsyServiceImpl
 * @author: Alex
 * @Version：1.3
 * @create: 2020/08/18 19:44
 */

public class SellsyServiceImpl implements SellsyService {
    
    @Autowired
    private SellsyConfig sellsyConfig;
    
    /**
     * sellsy 具体执行器
     * @param method
     * @param params 值
     * @return
     */
    @Transactional
    @Override
    public SellsyGeneralResult sellsyExecution(SellsyExecutionEnter enter) {
        SellsySpringRestExecutor underTest = new SellsySpringRestExecutor(sellsyConfig.consumerToken, sellsyConfig.consumerSecret, sellsyConfig.userToken, sellsyConfig.userSecret);
        SellsyApiRequest request = new SellsyApiRequest(enter.getMethdod(), enter.getParams());
        SellsyApiResponse result=null;
        try {
            SellsyApiResponse result = underTest.process(request);
        }catch (Exception e){
            //保存到数据库 如果是创建类型的接口出错 保存到数据库 其他操作 掠过
            if (e.getCause().getClass().equals(HttpClientErrorException.class)){
            
            }
            if (e.getCause().getClass().equals(SellsyApiException.class)){
//                if (){
//                设置数据库插入
//                }
            }
        }
        
        return null;
    }
}
