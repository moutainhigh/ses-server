package com.redescooter.ses.web.ros.service.sellsy.impl;

import com.alibaba.fastjson.JSON;
import com.redescooter.ses.api.common.enums.sellsy.SellsyMethodTypeEnums;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.ros.config.SellsyConfig;
import com.redescooter.ses.web.ros.constant.SellsyConstant;
import com.redescooter.ses.web.ros.constant.SellsyMethodConstant;
import com.redescooter.ses.web.ros.service.sellsy.SellsyService;
import com.redescooter.ses.web.ros.vo.sellsy.enter.SellsyCreateClientEnter;
import com.redescooter.ses.web.ros.vo.sellsy.enter.SellsyExecutionEnter;
import com.redescooter.ses.web.ros.vo.sellsy.result.SellsyClientResult;
import com.redescooter.ses.web.ros.vo.sellsy.result.SellsyGeneralResult;
import com.sellsy.apientities.SellsyResponseInfo;
import com.sellsy.coreConnector.SellsyApiRequest;
import com.sellsy.coreConnector.SellsyApiResponse;
import com.sellsy.coreConnector.SellsySpringRestExecutor;
import lombok.SneakyThrows;
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
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public SellsyGeneralResult sellsyExecution(SellsyExecutionEnter enter) {
        
        log.info("---------------------调用方法{}---------------------",enter.getMethod());
        log.info("-----------------------------方法参数{}---------------",enter.getParams());
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
            
            //返回值赋值 sellsyGeneralResult
            sellsyGeneralResult.setResult(result.extractResponseList());
            sellsyGeneralResult.setSellsyResponseInfo(result.getInfos());
            sellsyGeneralResult.setStatus(result.getResponseAttribute(SellsyConstant.status));
            
            log.info("-------------result结果返回值{}--------------------", result.getAttributeValueAsResponse(SellsyConstant.result));
        } catch (Exception e) {
            System.out.println("--------------调用出现问题-----------------");
        }
        return sellsyGeneralResult;
    }
    
    /**
     * 查询客户列表
     *
     * @param enter
     */
    @SneakyThrows
    @Override
    public List<SellsyClientResult> queryClientList(GeneralEnter enter) {
        
        SellsyExecutionEnter sellsyExecutionEnter = SellsyExecutionEnter.builder()
                .method(SellsyMethodConstant.Client_List)
                .params(new ArrayList<>())
                .SellsyMethodType(SellsyMethodTypeEnums.QUERY.getValue())
                .build();
        
        SellsyGeneralResult sellsyGeneralResult = sellsyExecution(sellsyExecutionEnter);
    
        return JSON.parseArray(sellsyGeneralResult.getResult().toString(), SellsyClientResult.class);
    }
    
    /**
     * 客户创建
     *
     * @param enter
     */
    @Override
    public void createClient(SellsyCreateClientEnter enter) {
    
    }
}
