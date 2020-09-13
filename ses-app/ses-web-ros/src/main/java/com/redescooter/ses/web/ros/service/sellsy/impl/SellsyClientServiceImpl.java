package com.redescooter.ses.web.ros.service.sellsy.impl;

import com.redescooter.ses.web.ros.constant.SellsyMethodConstant;
import com.redescooter.ses.web.ros.enums.sellsy.SellsyMethodTypeEnums;
import com.redescooter.ses.web.ros.service.sellsy.SellsyClientService;
import com.redescooter.ses.web.ros.service.sellsy.SellsyService;
import com.redescooter.ses.web.ros.vo.sellsy.enter.SellsyExecutionEnter;
import com.redescooter.ses.web.ros.vo.sellsy.enter.client.*;
import com.redescooter.ses.web.ros.vo.sellsy.result.SellsyGeneralResult;
import com.redescooter.ses.web.ros.vo.sellsy.result.SellsyIdResult;
import com.redescooter.ses.web.ros.vo.sellsy.result.client.SellsyClientAddressDetailResult;
import com.redescooter.ses.web.ros.vo.sellsy.result.client.SellsyClientResult;
import lombok.extern.log4j.Log4j2;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

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
     */
    @Override
    public List<SellsyClientResult> queryClientList(SellsyClientListEnter enter) {

        SellsyExecutionEnter sellsyExecutionEnter = SellsyExecutionEnter.builder()
                .method(SellsyMethodConstant.Client_List)
                .params(enter)
                .SellsyMethodType(SellsyMethodTypeEnums.QUERY.getValue())
                .build();

        SellsyGeneralResult sellsyGeneralResult = sellsyService.sellsyExecution(sellsyExecutionEnter);
        return sellsyService.jsonArrayFormattingByPage(sellsyGeneralResult, new SellsyClientResult());
    }
    
    /**
     * 客户查询
     *
     * @param enter
     * @return
     */
    @Override
    public SellsyClientResult queryClientOne(SellsyQueryClientOneEnter enter) {
        SellsyExecutionEnter sellsyExecutionEnter = SellsyExecutionEnter.builder()
                .method(SellsyMethodConstant.Client_GetOne)
                .params(enter)
                .SellsyMethodType(SellsyMethodTypeEnums.QUERY.getValue())
                .build();

        SellsyGeneralResult sellsyGeneralResult = sellsyService.sellsyExecution(sellsyExecutionEnter);
        return (SellsyClientResult)sellsyService.jsontoJavaObj(sellsyGeneralResult, new SellsyClientResult());
    }
    
    /**
     * 客户创建
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
        return (SellsyIdResult) sellsyService.jsontoJavaObj(sellsyGeneralResult,new SellsyIdResult());
    }

    /**
     * 删除客户
     * @param enter
     */
    @Override
    public void deleteClient(SellsyDeleteClientEnter enter) {
        SellsyExecutionEnter sellsyExecutionEnter = SellsyExecutionEnter.builder()
                .method(SellsyMethodConstant.Client_Delete)
                .params(enter)
                .SellsyMethodType(SellsyMethodTypeEnums.DELETE.getValue())
                .build();
        SellsyGeneralResult sellsyGeneralResult = sellsyService.sellsyExecution(sellsyExecutionEnter);
    }

    /**
     * 获取客户地址详情
     * @param enter
     */
    @Override
    public SellsyClientAddressDetailResult queryClientAddress(SellsyClientAddressEnter enter) {
        SellsyExecutionEnter sellsyExecutionEnter = SellsyExecutionEnter.builder()
                .method(SellsyMethodConstant.Client_AddAddress)
                .params(enter)
                .SellsyMethodType(SellsyMethodTypeEnums.QUERY.getValue())
                .build();

        SellsyGeneralResult sellsyGeneralResult = sellsyService.sellsyExecution(sellsyExecutionEnter);

        return (SellsyClientAddressDetailResult) sellsyService.jsontoJavaObj(sellsyGeneralResult, new SellsyClientAddressDetailResult());
    }
}
