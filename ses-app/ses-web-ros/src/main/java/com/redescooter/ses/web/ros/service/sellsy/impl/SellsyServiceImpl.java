package com.redescooter.ses.web.ros.service.sellsy.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.redescooter.ses.web.ros.config.SellsyConfig;
import com.redescooter.ses.web.ros.constant.SellsyConstant;
import com.redescooter.ses.web.ros.service.sellsy.SellsyService;
import com.redescooter.ses.web.ros.vo.sellsy.enter.SellsyExecutionEnter;
import com.redescooter.ses.web.ros.vo.sellsy.result.SellsyGeneralResult;
import com.sellsy.coreConnector.SellsyApiRequest;
import com.sellsy.coreConnector.SellsyApiResponse;
import com.sellsy.coreConnector.SellsySpringRestExecutor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
            log.info("----------------返回值{}---------------", result);
            sellsyGeneralResult.setResult(result);
        } catch (Exception e) {
            e.printStackTrace();
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
    public List<T> jsonArrayFormattingByPage(SellsyGeneralResult sellsyGeneralResult, T t) {
        List<T> resultList = new ArrayList<>();

        if (sellsyGeneralResult == null || sellsyGeneralResult.getResult() == null) {
            return resultList;
        }

        try {
            // 当返回值是多个值是可以解析出来 返回值为单个值 无法解析
            sellsyGeneralResult.getResult().extractResponseList().forEach(item -> {
                resultList.add((T) JSON.parseObject(item.toString(), t.getClass()));
            });
            //resultList = (List<T>) JSON.parseArray(sellsyGeneralResult.getResult().extractResponseList().toString(), t.getClass().getClass());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultList;
    }

    @Override
    public List<T> jsonArrayFormatting(SellsyGeneralResult sellsyGeneralResult, T t) {
        List<T> resultList = new ArrayList<>();
        if (sellsyGeneralResult == null || sellsyGeneralResult.getResult() == null) {
            return resultList;
        }
        try {
            Map<String, T> objMap = JSON.parseObject(sellsyGeneralResult.getResult().toString(), Map.class);
            resultList.addAll(objMap.values());
        } catch (Exception e) {

        }
        return resultList;
    }

    /**
     * 处理 以下格式数据 { "dateResult":{ "id":{ 目标对象 } }, "default": "3497458" }
     *
     * @param sellsyGeneralResult
     * @param t
     * @return
     */
    @Override
    public List<Object> jsonChildFormatting(SellsyGeneralResult sellsyGeneralResult, T t) {
        List<Object> resultList = new ArrayList<>();
        if (sellsyGeneralResult == null || sellsyGeneralResult.getResult() == null) {
            return resultList;
        }
        try {
            Map<String, Map<String, T>> objMap = JSON.parseObject(sellsyGeneralResult.getResult().toString(), Map.class);
            if (CollectionUtil.isEmpty(objMap)) {
                return resultList;
            }
            objMap.keySet().forEach(item -> {
                if (!StringUtils.equals(SellsyConstant.DEFAULT, item)) {
                    //Map<String, T> targetMap =JSONObject.parseObject(objMap.get(item), new TypeReference<Map<String, T>>() {});
                    resultList.addAll(objMap.get(item).values());
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultList;
    }

    /**
     * json 处理单个对象
     * 
     * @param sellsyGeneralResult
     * @param t
     * @return
     */
    @Override
    public Object jsontoJavaObj(SellsyGeneralResult sellsyGeneralResult, Class t) {
        if (sellsyGeneralResult.getResult() == null) {
            return null;
        }
        Object result = null;
        try {
            result = JSON.parseObject(sellsyGeneralResult.getResult().toString(), t);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
