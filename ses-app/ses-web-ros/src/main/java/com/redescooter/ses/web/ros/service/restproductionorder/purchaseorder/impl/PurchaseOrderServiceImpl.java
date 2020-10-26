package com.redescooter.ses.web.ros.service.restproductionorder.purchaseorder.impl;

import com.alibaba.fastjson.JSONArray;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.tool.utils.SesStringUtils;
import com.redescooter.ses.web.ros.service.base.OpePurchaseOrderService;
import com.redescooter.ses.web.ros.service.restproductionorder.purchaseorder.PurchaseOrderService;
import com.redescooter.ses.web.ros.vo.restproductionorder.allocateorder.AllocateNoDataResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.purchaseorder.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassNamePurchaseOrderServiceImpl
 * @Description
 * @Author Aleks
 * @Date2020/10/23 18:21
 * @Version V1.0
 **/
@Service
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    @Autowired
    private OpePurchaseOrderService opePurchaseOrderService;

    @Override
    @Transactional
    public GeneralResult purchaseSave(PurchaseSaveOrUpdateEnter enter) {
        enter = SesStringUtils.objStringTrim(enter);
        // todo 操作动态表
        // todo 状态流转表
        // 传参里面有classType 判断是哪个类型 对应的调拨产品为 车辆  组装件  部件
        List<PurchaseScooterEnter> scooterEnters = new ArrayList<>();
        scooterEnters = JSONArray.parseArray(enter.getSt(), PurchaseScooterEnter.class);
        return new GeneralResult(enter.getRequestId());
    }


    @Override
    @Transactional
    public GeneralResult purchaseEdit(PurchaseSaveOrUpdateEnter enter) {
        return new GeneralResult(enter.getRequestId());
    }


    @Override
    public PageResult<PuraseListResult> purchaseList(PuraseListEnter enter) {
        int totalNum = 0;
        List<PuraseListResult> list = new ArrayList<>();
        return PageResult.create(enter, totalNum, list);
    }

    @Override
    public PurchaseDetailResult purchaseDetail(IdEnter enter) {
        PurchaseDetailResult result = new PurchaseDetailResult();
        return result;
    }


    @Override
    @Transactional
    public GeneralResult confirmOrder(IdEnter enter) {
        return new GeneralResult(enter.getRequestId());
    }


    @Override
    @Transactional
    public GeneralResult cancelOrder(IdEnter enter) {
        return new GeneralResult(enter.getRequestId());
    }


    @Override
    @Transactional
    public GeneralResult closeOrder(IdEnter enter) {
        return new GeneralResult(enter.getRequestId());
    }


    @Override
    public List<AllocateNoDataResult> allocateNoData(GeneralEnter enter) {
        List<AllocateNoDataResult> resultList = new ArrayList<>();
        return resultList;
    }


    @Override
    public Map<String, Integer> listCount(GeneralEnter enter) {
        Map<String, Integer> map = new HashMap<>();
        // 1 2 3分别对应整车、组装件、部件
        map.put("1",0);
        map.put("2",0);
        map.put("3",0);
        return map;
    }
}
