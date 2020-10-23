package com.redescooter.ses.mobile.rps.service.printentry.impl;

import com.redescooter.ses.api.common.enums.rps.ModerTypeEnums;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.mobile.rps.dao.printentry.PrintEntryServiceMapper;
import com.redescooter.ses.mobile.rps.dm.OpePurchas;
import com.redescooter.ses.mobile.rps.exception.ExceptionCodeEnums;
import com.redescooter.ses.mobile.rps.exception.SesMobileRpsException;
import com.redescooter.ses.mobile.rps.service.base.OpePurchasService;
import com.redescooter.ses.mobile.rps.service.printentry.PrintEntryService;
import com.redescooter.ses.mobile.rps.vo.printentry.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PrintEntryServiceImpl implements PrintEntryService {

    @Autowired
    private PrintEntryServiceMapper printEntryServiceMapper;

    @Autowired
    private OpePurchasService opePurchasService;

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/23 14:33
     * @Param: enter
     * @Return: PrintEntryResult
     * @desc: 条码打印单据列表
     * @param enter
     */
    @Override
    public PageResult<PrintEntryResult> list(PrintEnteyEnter enter) {
        //来料质检
        if (StringUtils.equals(enter.getBusinessModule(), ModerTypeEnums.METERIAL.getValue())) {
            int count = printEntryServiceMapper.meterialCount(enter);
            if (count == 0) {
                return PageResult.createZeroRowResult(enter);
            }
            return PageResult.create(enter, count, printEntryServiceMapper.meterialList(enter));
        }
        //生产入库
//        if (StringUtils.equals(enter.getBusinessModule(), ModerTypeEnums.PURCHASE_WH.getValue())) {
//            int count=printEntryServiceMapper.purchasCount(enter);
//            if (count==0){
//                return PageResult.createZeroRowResult(enter);
//            }
//            return PageResult.create(enter,count,printEntryServiceMapper.purchasList(enter));
//        }
        return null;
    }

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/23 14:35
     * @Param: enter
     * @Return: PrintEntryOrderResult
     * @desc: 订单列表
     * @param enter
     */
    @Override
    public PageResult<PrintEntryOrderResult> orderDetailList(PrintEntryOrderEnter enter) {
        if (StringUtils.equalsAny(enter.getBusinessModule(), ModerTypeEnums.METERIAL.getValue(), ModerTypeEnums.PURCHASE_WH.getValue())) {
            OpePurchas opePurchas = opePurchasService.getById(enter.getId());
            if (opePurchas == null) {
                throw new SesMobileRpsException(ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getMessage());
            }
            int count = printEntryServiceMapper.orderCount(enter);
            if (count == 0) {
                return PageResult.createZeroRowResult(enter);
            }
            List<PrintEntryOrderResult> printEntryOrderResultList = printEntryServiceMapper.orderList(enter);
            List<PrintEntryOrderDetailResult> partQcDetailList =
                    printEntryServiceMapper.orderPartDetail(printEntryOrderResultList.stream().map(PrintEntryOrderResult::getId).collect(Collectors.toList()));

            if (CollectionUtils.isNotEmpty(partQcDetailList)) {
                printEntryOrderResultList.forEach(item -> {
                    List<PrintEntryOrderDetailResult> qcDetailList = new ArrayList<>();
                    partQcDetailList.forEach(qcdetail -> {
                        if (item.getId().equals(qcdetail.getPurchasbId())) {
                            qcDetailList.add(qcdetail);
                        }
                    });
                    item.setPartQcDetailList(qcDetailList);
                });
            }
            return PageResult.create(enter, count, printEntryOrderResultList);
        }
        return null;
    }
}
