package com.redescooter.ses.web.ros.service.sellsy.impl;

import com.alibaba.fastjson.JSON;
import com.redescooter.ses.web.ros.config.SellsyConfig;
import com.redescooter.ses.web.ros.service.sellsy.SellsyService;
import com.redescooter.ses.web.ros.vo.sellsy.enter.SellsyExecutionEnter;
import com.redescooter.ses.web.ros.vo.sellsy.result.SellsyGeneralResult;
import com.sellsy.coreConnector.SellsyApiRequest;
import com.sellsy.coreConnector.SellsyApiResponse;
import com.sellsy.coreConnector.SellsySpringRestExecutor;
import lombok.extern.log4j.Log4j2;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName:SellsyServiceImpl
 * @description: SellsyServiceImpl
 * @author: Alex
 * @Version：1.3
 * @create: 2020/08/18 19:44
 */
@Log4j2
@Service
public class SellsyServiceImpl<T> implements SellsyService<T> {
    
    @Autowired
    private SellsyConfig sellsyConfig;
    
    /**
     * sellsy 具体执行器
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public SellsyGeneralResult sellsyExecution(SellsyExecutionEnter enter) {
        
        log.info("---------------------调用方法{}---------------------", enter.getMethod());
        log.info("-----------------------------方法参数{}---------------", enter.getParams());
        //连接器 请求头配置
        SellsySpringRestExecutor sellsySpringRestExecutor = new SellsySpringRestExecutor(sellsyConfig.getConsumerToken(), sellsyConfig.getConsumerSecret(), sellsyConfig.getUserToken(),
                sellsyConfig.getUserSecret());
        
        //配置 请求参数
        SellsyApiRequest request = new SellsyApiRequest(enter.getMethod(), enter.getParams());
        
        SellsyGeneralResult sellsyGeneralResult = new SellsyGeneralResult();
        
        SellsyApiResponse result = null;
        try {
            //执行请求
            result = sellsySpringRestExecutor.process(request);
            sellsyGeneralResult.setResult(result);
        } catch (Exception e) {
            System.out.println("--------------调用出现问题-----------------");
        }
        return sellsyGeneralResult;
    }

    /**
     * extractResponseList 会抛出异常所以进行统一的格式处理
     * 
     * @param sellsyGeneralResult
     * @param t
     * @return
     */
    @Override
    public List<T> jsonArrayFormatting(SellsyGeneralResult sellsyGeneralResult, T t) {
        List<T> resultList = new ArrayList<>();

        if (sellsyGeneralResult == null || sellsyGeneralResult.getResult() == null) {
            return resultList;
        }

        try {
            resultList = (List<T>)JSON.parseArray(sellsyGeneralResult.getResult().extractResponseList().toString(),
                t.getClass());
        } catch (Exception e) {

        }
        return resultList;
    }
}
