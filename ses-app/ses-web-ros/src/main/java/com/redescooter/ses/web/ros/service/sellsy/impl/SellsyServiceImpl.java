package com.redescooter.ses.web.ros.service.sellsy.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.redescooter.ses.api.common.vo.base.PageEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.config.SellsyConfig;
import com.redescooter.ses.web.ros.constant.SellsyMethodConstant;
import com.redescooter.ses.web.ros.enums.sellsy.SellsyMethodTypeEnums;
import com.redescooter.ses.web.ros.service.sellsy.SellsyService;
import com.redescooter.ses.web.ros.vo.sellsy.enter.SellsyCreateClientEnter;
import com.redescooter.ses.web.ros.vo.sellsy.enter.document.SellsyDocumentByIdEnter;
import com.redescooter.ses.web.ros.vo.sellsy.enter.document.SellsyDocumentListEnter;
import com.redescooter.ses.web.ros.vo.sellsy.enter.SellsyExecutionEnter;
import com.redescooter.ses.web.ros.vo.sellsy.enter.SellsyQueryClientOneEnter;
import com.redescooter.ses.web.ros.vo.sellsy.result.SellsyClientResult;
import com.redescooter.ses.web.ros.vo.sellsy.result.document.SellsyDocumentListResult;
import com.redescooter.ses.web.ros.vo.sellsy.result.SellsyGeneralResult;
import com.redescooter.ses.web.ros.vo.sellsy.result.document.SellsyIdResult;
import com.sellsy.apientities.SellsyResponseInfo;
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
     * 查询客户列表
     *
     * @param enter
     */
    @Override
    public PageResult<SellsyClientResult> queryClientList(PageEnter enter) {
        
        SellsyExecutionEnter sellsyExecutionEnter = SellsyExecutionEnter.builder()
                .method(SellsyMethodConstant.Client_List)
                .params(new ArrayList<>())
                .SellsyMethodType(SellsyMethodTypeEnums.QUERY.getValue())
                .build();
        
        SellsyGeneralResult sellsyGeneralResult = sellsyExecution(sellsyExecutionEnter);
        
        //返回值赋值 sellsyGeneralResult
        List<SellsyClientResult> resultList = null;
        SellsyResponseInfo infos = null;
        try {
            log.info("-------------result结果返回值{}--------------------", sellsyGeneralResult.getResult().extractResponseList().toString());
            resultList = JSON.parseArray(sellsyGeneralResult.getResult().extractResponseList().toString(), SellsyClientResult.class);
            infos = sellsyGeneralResult.getResult().getInfos();
        } catch (Exception e) {
        
        }
        if (infos == null) {
            return PageResult.createZeroRowResult(enter);
        }
        return PageResult.create(enter,infos.getNbtotal(),resultList);
    }
    
    /**
     * 客户查询
     *
     * @param enter
     * @return
     */
    @Override
    public SellsyClientResult queryClientById(SellsyQueryClientOneEnter enter) {
        SellsyExecutionEnter sellsyExecutionEnter = SellsyExecutionEnter.builder()
                .method(SellsyMethodConstant.Client_GetOne)
                .params(enter)
                .SellsyMethodType(SellsyMethodTypeEnums.QUERY.getValue())
                .build();
        
        SellsyGeneralResult sellsyGeneralResult = sellsyExecution(sellsyExecutionEnter);
    
        return JSON.parseObject(sellsyGeneralResult.getResult().getResponseAttribute("client"), SellsyClientResult.class);
    }
    
    /**
     * todo 客户创建
     *
     * @param enter
     */
    @Transactional
    @Override
    public SellsyIdResult createClient(SellsyCreateClientEnter enter) {
        SellsyExecutionEnter sellsyExecutionEnter = SellsyExecutionEnter.builder()
                .method(SellsyMethodConstant.Client_Create)
                .params(enter)
                .SellsyMethodType(SellsyMethodTypeEnums.ADD.getValue())
                .build();
        SellsyGeneralResult sellsyGeneralResult = sellsyExecution(sellsyExecutionEnter);
        return null;
    }
    
    /**
     * 单据列表
     *
     * @param enter
     */
    @Override
    public PageResult<SellsyDocumentListResult> queryDocumentList(SellsyDocumentListEnter enter) {
        SellsyExecutionEnter sellsyExecutionEnter = SellsyExecutionEnter.builder()
                .method(SellsyMethodConstant.Document_GetList)
                .params(enter)
                .SellsyMethodType(SellsyMethodTypeEnums.QUERY.getValue())
                .build();
    
        SellsyGeneralResult sellsyGeneralResult = sellsyExecution(sellsyExecutionEnter);
    
        //返回值赋值 sellsyGeneralResult
        List<SellsyDocumentListResult> resultList = null;
        SellsyResponseInfo infos = null;
        try {
            resultList = JSON.parseArray(sellsyGeneralResult.getResult().extractResponseList().toString(), SellsyDocumentListResult.class);
            infos = sellsyGeneralResult.getResult().getInfos();
        } catch (Exception e) {

        }
        if (infos == null) {
            return PageResult.createZeroRowResult(enter);
        }
        return PageResult.create(enter,infos.getNbtotal(),resultList);
    }
    
    /**
     * 查询指定单据
     *
     * @param enter
     */
    @Override
    public JSONObject queryDocumentById(SellsyDocumentByIdEnter enter) {
        SellsyExecutionEnter sellsyExecutionEnter = SellsyExecutionEnter.builder()
                .method(SellsyMethodConstant.Document_GetOne)
                .params(enter)
                .SellsyMethodType(SellsyMethodTypeEnums.ADD.getValue())
                .build();
        SellsyGeneralResult sellsyGeneralResult = sellsyExecution(sellsyExecutionEnter);
        log.info("-------------result结果返回值{}--------------------", sellsyGeneralResult.getResult().toString());
    
        return JSONObject.parseObject(sellsyGeneralResult.getResult().toString());
    }
    
}
