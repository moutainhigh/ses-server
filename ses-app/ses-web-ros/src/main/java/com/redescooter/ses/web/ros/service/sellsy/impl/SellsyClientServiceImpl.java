package com.redescooter.ses.web.ros.service.sellsy.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.web.ros.constant.SellsyMethodConstant;
import com.redescooter.ses.web.ros.dm.SellsyCustomer;
import com.redescooter.ses.web.ros.dm.SellsyInvoiceTotal;
import com.redescooter.ses.web.ros.enums.sellsy.SellsyClientTypeEnums;
import com.redescooter.ses.web.ros.enums.sellsy.SellsyMethodTypeEnums;
import com.redescooter.ses.web.ros.service.base.SellsyCustomerService;
import com.redescooter.ses.web.ros.service.base.SellsyInvoiceTotalService;
import com.redescooter.ses.web.ros.service.sellsy.SellsyClientService;
import com.redescooter.ses.web.ros.service.sellsy.SellsyService;
import com.redescooter.ses.web.ros.vo.sellsy.enter.SellsyExecutionEnter;
import com.redescooter.ses.web.ros.vo.sellsy.enter.client.*;
import com.redescooter.ses.web.ros.vo.sellsy.result.SellsyGeneralResult;
import com.redescooter.ses.web.ros.vo.sellsy.result.SellsyIdResult;
import com.redescooter.ses.web.ros.vo.sellsy.result.client.SellsyClientAddressDetailResult;
import com.redescooter.ses.web.ros.vo.sellsy.result.client.SellsyClientResult;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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

    @Reference
    private IdAppService idAppService;

    @Autowired
    private SellsyInvoiceTotalService sellsyInvoiceTotalService;

    @Autowired
    private SellsyCustomerService sellsyCustomerService;


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
        return sellsyService.jsontoJavaObj(sellsyGeneralResult, new SellsyClientResult());
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
        return sellsyService.jsontoJavaObj(sellsyGeneralResult, new SellsyIdResult());
    }

    @Override
    public void clientChick(IdEnter enter) {
        List<SellsyInvoiceTotal> sellsyInvoiceTotalList =
                sellsyInvoiceTotalService.list(new LambdaQueryWrapper<SellsyInvoiceTotal>()
                        .eq(SellsyInvoiceTotal::getUpdatedBy, 0)
                        .eq(SellsyInvoiceTotal::getType, "2")
                        .orderByDesc(SellsyInvoiceTotal::getCreatedTime)
                        .last(" limit " + enter.getId()));
        sellsyInvoiceTotalList.forEach(item -> {

            // 客户校验
            SellsyClientListEnter sellsyClientListEnter = new SellsyClientListEnter();
            SellsyClientListSearchEnter sellsyClientSerach = new SellsyClientListSearchEnter();
            sellsyClientSerach.setName(item.getClientName());
            sellsyClientListEnter.setSearch(sellsyClientSerach);
            List<SellsyClientResult> sellsyClientResults = queryClientList(sellsyClientListEnter);
            SellsyClientResult sellsyClientResult = sellsyClientResults.stream()
                    .filter(client -> StringUtils.equals(client.getName(), item.getClientName().trim())).findFirst()
                    .orElse(null);
            if (sellsyClientResult == null) {
                SellsyCreateClientThirdEnter third = new SellsyCreateClientThirdEnter();
                third.setName(item.getClientName());
                third.setType(SellsyClientTypeEnums.corporation.getValue());
                SellsyCreateClientEnter sellsyCreateClientEnter = SellsyCreateClientEnter.builder()
                        .third(third)
                        .address(null)
                        .contact(null)
                        .build();
                SellsyIdResult client = createClient(sellsyCreateClientEnter);
                log.info("-----------取到返回值{}---------", client.getId());
                //保存客户
                SellsyCustomer sellsyCustomer = SellsyCustomer
                        .builder()
                        .id(idAppService.getId("SELLSY_CUSTOMER"))
                        .dr(0)
                        .name(item.getClientName())
                        .address(null)
                        .status(String.valueOf(Boolean.TRUE))
                        .remark(null)
                        .createdBy(0L)
                        .createdTime(new Date())
                        .updatedBy(0L)
                        .updatedTime(new Date())
                        .def1(String.valueOf(client.getId()))
                        .build();
                sellsyCustomerService.save(sellsyCustomer);
            }
            item.setUpdatedBy(Long.valueOf(1));
            sellsyInvoiceTotalService.updateById(item);
        });
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

        return sellsyService.jsontoJavaObj(sellsyGeneralResult, new SellsyClientAddressDetailResult());
    }
}
