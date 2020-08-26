package com.redescooter.ses.web.ros.service.sellsy.impl;

import com.alibaba.fastjson.JSON;
import com.redescooter.ses.api.common.vo.base.PageEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.constant.SellsyMethodConstant;
import com.redescooter.ses.web.ros.enums.sellsy.SellsyMethodTypeEnums;
import com.redescooter.ses.web.ros.service.sellsy.SellsyClientService;
import com.redescooter.ses.web.ros.service.sellsy.SellsyService;
import com.redescooter.ses.web.ros.vo.sellsy.enter.SellsyCreateClientEnter;
import com.redescooter.ses.web.ros.vo.sellsy.enter.SellsyExecutionEnter;
import com.redescooter.ses.web.ros.vo.sellsy.enter.SellsyQueryClientOneEnter;
import com.redescooter.ses.web.ros.vo.sellsy.result.SellsyClientResult;
import com.redescooter.ses.web.ros.vo.sellsy.result.SellsyGeneralResult;
import com.redescooter.ses.web.ros.vo.sellsy.result.document.SellsyIdResult;
import com.sellsy.apientities.SellsyResponseInfo;
import lombok.extern.log4j.Log4j2;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName:SellsyClientServiceImpl
 * @description: SellsyClientServiceImpl
 * @author: Alex
 * @Version：1.3
 * @create: 2020/08/26 14:42
 */
@Log4j2
@Service
public class SellsyClientServiceImpl implements SellsyClientService {
    
    @Autowired
    private SellsyService sellsyService;
    
    
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
        
        SellsyGeneralResult sellsyGeneralResult = sellsyService.sellsyExecution(sellsyExecutionEnter);
        
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
        
        SellsyGeneralResult sellsyGeneralResult = sellsyService.sellsyExecution(sellsyExecutionEnter);
        
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
        SellsyGeneralResult sellsyGeneralResult = sellsyService.sellsyExecution(sellsyExecutionEnter);
        return null;
    }
    
}
