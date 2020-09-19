package com.redescooter.ses.web.ros.service.sellsy.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.redescooter.ses.app.common.service.FileAppService;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.web.ros.config.SellsyConfig;
import com.redescooter.ses.web.ros.constant.SellsyConstant;
import com.redescooter.ses.web.ros.dm.SellsyException;
import com.redescooter.ses.web.ros.enums.sellsy.DefultFiledEnums;
import com.redescooter.ses.web.ros.enums.sellsy.SellsyBooleanEnums;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.exception.ThirdExceptionCodeEnums;
import com.redescooter.ses.web.ros.service.base.SellsyExceptionService;
import com.redescooter.ses.web.ros.service.sellsy.SellsyService;
import com.redescooter.ses.web.ros.vo.sellsy.enter.SellsyExecutionEnter;
import com.redescooter.ses.web.ros.vo.sellsy.result.SellsyGeneralResult;
import com.redescooter.ses.web.ros.vo.sellsy.result.SellsyIdResult;
import com.sellsy.coreConnector.SellsyApiException;
import com.sellsy.coreConnector.SellsyApiRequest;
import com.sellsy.coreConnector.SellsyApiResponse;
import com.sellsy.coreConnector.SellsySpringRestExecutor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @ClassName:SellsyServiceImpl
 * @description: SellsyServiceImpl
 * @author: Alex @Version：1.3
 * @create: 2020/08/18 19:44
 */
@Log4j2
@Service
public class SellsyServiceImpl implements SellsyService {

    @Autowired
    private SellsyConfig sellsyConfig;

    @Autowired
    private SellsyExceptionService sellsyExceptionService;

    @Autowired
    private FileAppService fileAppService;

    @Reference
    private IdAppService idAppService;

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
        // 连接器 请求头配置
        SellsySpringRestExecutor sellsySpringRestExecutor =
            new SellsySpringRestExecutor(sellsyConfig.getConsumerToken(), sellsyConfig.getConsumerSecret(),
                sellsyConfig.getUserToken(), sellsyConfig.getUserSecret());

        // 配置 请求参数
        SellsyApiRequest request = new SellsyApiRequest(enter.getMethod(), enter.getParams());

        SellsyGeneralResult sellsyGeneralResult = new SellsyGeneralResult();

        SellsyApiResponse result = null;
        try {
             //执行请求
             result = sellsySpringRestExecutor.process(request);
             log.info("----------------返回值{}---------------", result);
             sellsyGeneralResult.setResult(result);
        } catch (Exception e) {
            // 异常处理
            sellsyExceptionService(enter, e);
            throw new SesWebRosException(ThirdExceptionCodeEnums.SELLSY_CALL_FAILED.getCode(),
                    ThirdExceptionCodeEnums.SELLSY_CALL_FAILED.getMessage());
        }
        return sellsyGeneralResult;
    }

    /**
     * 支持各类文件上传
     * 动态生成文件后缀
     * @param enter
     * @param file
     * @return
     */
    @Transactional
    @Override
    public SellsyGeneralResult sellsyExecutionByFile(SellsyExecutionEnter enter, MultipartFile file) {

        log.info("---------------------调用方法{}---------------------", enter.getMethod());
        log.info("-----------------------------方法参数{}---------------", enter.getParams());
        // 连接器 请求头配置
        SellsySpringRestExecutor sellsySpringRestExecutor =
                new SellsySpringRestExecutor(sellsyConfig.getConsumerToken(), sellsyConfig.getConsumerSecret(),
                        sellsyConfig.getUserToken(), sellsyConfig.getUserSecret());

        // 配置 请求参数
        SellsyApiRequest request = new SellsyApiRequest(enter.getMethod(), enter.getParams());

        SellsyGeneralResult sellsyGeneralResult = new SellsyGeneralResult();

        SellsyApiResponse result = null;

        //获得文件后缀名
        //String fileSuffixName = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."), file.getOriginalFilename().length());
        String fileSuffixName = ".pdf";
        //保存文件
        File newFile = new File(sellsyConfig.getDownloadUrl() + String.valueOf(System.currentTimeMillis()) + fileSuffixName);
        try {
            log.info("-------------------生成临时文件,文件名{},文件路径{}-------------------------", newFile.getName(), newFile.getPath());
            file.transferTo(newFile);
            //执行请求
            result = sellsySpringRestExecutor.processByFile(request, newFile);
            log.info("----------------返回值{}---------------", result);
            sellsyGeneralResult.setResult(result);
        } catch (Exception e) {
            // 异常处理
            sellsyExceptionService(enter, e);
            throw new SesWebRosException(ThirdExceptionCodeEnums.SELLSY_CALL_FAILED.getCode(),
                    ThirdExceptionCodeEnums.SELLSY_CALL_FAILED.getMessage());
        } finally {
            //删除文件
            if (newFile != null) {
                newFile.delete();
            }
        }
        return sellsyGeneralResult;
    }

    /**
     * 出现异常后发送邮件
     * @param enter
     * @param e
     */
    private void sellsyExceptionService(SellsyExecutionEnter enter, Exception e) {
        // 保存数据库
        if (e instanceof SellsyApiException || e instanceof HttpClientErrorException) {
            SellsyException sellsyException = sellsyExceptionService
                    .getOne(
                            new LambdaQueryWrapper<SellsyException>().eq(SellsyException::getMethodName, enter.getMethod()));
            if (sellsyException == null) {
                // 保存
                sellsyException = buildSellsyException(enter, e);
            } else {
                sellsyException.setRequestCount(sellsyException.getRequestCount() + 1);
            }
            sellsyExceptionService.saveOrUpdate(sellsyException);
            // 异常出现参数相同的异常出现三次后 发送邮件通知
            if (sellsyException.getRequestCount() > 2) {
                // todo 发送邮件
            }
        }
        e.printStackTrace();
    }

    /**
     * extractResponseList 会抛出异常所以进行统一的格式处理
     *
     * @param sellsyGeneralResult
     * @param t
     * @return
     */
    @Override
    public <T> List<T> jsonArrayFormattingByPage(SellsyGeneralResult sellsyGeneralResult, T t) {
        List<T> resultList = new ArrayList<>();
        if (sellsyGeneralResult == null || sellsyGeneralResult.getResult() == null) {
            return resultList;
        }
        try {
            resultList = (List<T>)JSON.parseArray(sellsyGeneralResult.getResult().extractResponseList().toString(),
                t.getClass());
        } catch (Exception e) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(),
                ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }
        return resultList;
    }

    /**
     * 主要处理 存在默认值的返回值（无返回值 也可以处理） { "8189455_0": { } "defaultShippingId": "8189455" }
     * 
     * @param sellsyGeneralResult
     * @param t
     * @param <T>
     * @return
     */
    @Override
    public <T> List<T> jsonArrayFormatting(SellsyGeneralResult sellsyGeneralResult, T t) {
        List<T> resultList = new ArrayList<>();
        if (sellsyGeneralResult == null || sellsyGeneralResult.getResult() == null) {
            return resultList;
        }
        try {
            Map<String, String> objMap = JSON.parseObject(sellsyGeneralResult.getResult().toString(),
                new TypeReference<Map<String, String>>() {});

            // 移除默认值字段
            for (DefultFiledEnums item : DefultFiledEnums.values()) {
                if (objMap.keySet().contains(item.getValue())) {
                    objMap.remove(item.getValue());
                }
            }
            resultList.addAll((List<T>)JSON.parseArray(objMap.values().toString(), t.getClass()));
        } catch (Exception e) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(),
                ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
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
    public <T> List<T> jsonChildFormatting(SellsyGeneralResult sellsyGeneralResult, T t) {
        List<T> resultList = new ArrayList<>();
        if (sellsyGeneralResult == null || sellsyGeneralResult.getResult() == null) {
            return resultList;
        }
        try {
            Map<String, Map<String, T>> objMap =
                JSON.parseObject(sellsyGeneralResult.getResult().toString(), Map.class);
            if (CollectionUtil.isEmpty(objMap)) {
                return resultList;
            }
            objMap.keySet().forEach(item -> {
                if (!StringUtils.equals(SellsyConstant.DEFAULT, item)) {

                    List<T> targetMap = (List<T>)JSON.parseArray(objMap.get(item).values().toString(), t.getClass());
                    resultList.addAll(targetMap);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(),
                ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }
        return resultList;
    }

    /**
     * json 转Map
     * 
     * @param sellsyGeneralResult
     * @param t
     * @return
     */
    @Override
    public <T> List<T> jsonMaptoList(SellsyGeneralResult sellsyGeneralResult, T t) {
        List<T> resultList = new ArrayList<>();
        if (sellsyGeneralResult == null || sellsyGeneralResult.getResult() == null) {
            return resultList;
        }

        try {

            List<Map<String, T>> mapList =
                (List<Map<String, T>>)JSONArray.parse(sellsyGeneralResult.getResult().extractResponseList().toString());
            for (Map<String, T> item : mapList) {
                resultList.addAll((List<T>)JSON.parseArray(item.values().toString(), t.getClass()));
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(),
                ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
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
    public <T> T jsontoJavaObj(SellsyGeneralResult sellsyGeneralResult, T t) {
        if (sellsyGeneralResult.getResult() == null) {
            return null;
        }
        try {
            if (t instanceof SellsyIdResult){
                Map<String,String> map = JSON.parseObject(sellsyGeneralResult.getResult().toString(), Map.class);

             Field f = t.getClass().getDeclaredField("id");
             f.setAccessible(true);
             f.set(t, ((Integer)map.values().toArray()[0]).intValue());
             return t;
            }
            t = JSON.parseObject(sellsyGeneralResult.getResult().toString(), (Type) t.getClass());
        } catch (Exception e) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(),
                ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }
        return t;
    }

    /**
     * 格式化 创建业务 返回的Id 问题
     * 
     * @param sellsyGeneralResult
     * @return
     */
    @Override
    public SellsyIdResult jsonCreateResut(SellsyGeneralResult sellsyGeneralResult) {
        if (sellsyGeneralResult.getResult() == null) {
            return null;
        }
        Map<String, Integer> result = null;
        try {
            result = JSON.parseObject(sellsyGeneralResult.getResult().toString(), Map.class);
        } catch (Exception e) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(),
                ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }
        return new SellsyIdResult(new ArrayList<>(result.values()).get(0));
    }

    /**
     * 构建异常对象
     * 
     * @param enter
     * @param e
     * @return
     */
    private SellsyException buildSellsyException(SellsyExecutionEnter enter, Exception e) {
        SellsyException sellsyException;
        sellsyException = SellsyException.builder().id(idAppService.getId("sellsy_exception")).dr(0)
            .parameter(enter.toString()).status(SellsyBooleanEnums.N.getValue()).exceptionCase(e.getMessage())
            .methodType(enter.getSellsyMethodType()).methodName(enter.getMethod()).requestCount(1).createdBy(0L)
            .createdTime(new Date()).updatedBy(0L).updatedTime(new Date()).build();
        return sellsyException;
    }
}
