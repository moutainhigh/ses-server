package com.redescooter.ses.web.ros.service.sellsy.impl;

import com.alibaba.fastjson.JSON;
import com.redescooter.ses.web.ros.config.SellsyConfig;
import com.redescooter.ses.web.ros.service.sellsy.SellsyService;
import com.redescooter.ses.web.ros.vo.sellsy.enter.SellsyExecutionEnter;
import com.redescooter.ses.web.ros.vo.sellsy.result.SellsyGeneralResult;
import com.sellsy.coreConnector.SellsyApiException;
import com.sellsy.coreConnector.SellsyApiRequest;
import com.sellsy.coreConnector.SellsyApiResponse;
import com.sellsy.coreConnector.SellsySpringRestExecutor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import sun.rmi.runtime.Log;

import java.util.HashMap;


/**
 * @ClassName:SellsyServiceImpl
 * @description: SellsyServiceImpl
 * @author: Alex
 * @Version：1.3
 * @create: 2020/08/18 19:44
 */
@Log4j
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
  /*          //保存到数据库 如果是创建类型的接口出错 保存到数据库 其他操作 掠过
            if (e.getCause().getClass().equals(HttpClientErrorException.class)){
                //通知负责人 发票系统调用失败
            }
            if (e.getCause().getClass().equals(SellsyApiException.class)){
//                if (){
//                设置数据库插入
//                }
            }*/
            log.info("-------------------调用出现问题，请及时处理--------------------");
        }
        
    
        //进行反参处理
//        JSON.parseObject(result.getResponseAttribute());
        
        return new SellsyGeneralResult();
    }
   
}
