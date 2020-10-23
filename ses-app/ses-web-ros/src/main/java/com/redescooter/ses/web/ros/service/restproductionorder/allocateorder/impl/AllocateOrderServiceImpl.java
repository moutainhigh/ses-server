package com.redescooter.ses.web.ros.service.restproductionorder.allocateorder.impl;

import com.alibaba.fastjson.JSONArray;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.tool.utils.SesStringUtils;
import com.redescooter.ses.web.ros.service.restproductionorder.allocateorder.AllocateOrderService;
import com.redescooter.ses.web.ros.vo.restproductionorder.allocateorder.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassNameAllocateOrderServiceImpl
 * @Description
 * @Author Aleks
 * @Date2020/10/23 11:34
 * @Version V1.0
 **/
@Service
public class AllocateOrderServiceImpl implements AllocateOrderService {


    @Override
    public PageResult<AllocateOrderListResult> allocateList(AllocateOrderListEnter enter) {
        int totalNum = 0;
        List<AllocateOrderListResult> list = new ArrayList<>();
        return PageResult.create(enter, totalNum, list);
    }


    @Override
    @Transactional
    public GeneralResult allocateSave(AllocateOrderOrUpdateSaveEnter enter) {
        enter = SesStringUtils.objStringTrim(enter);
        // todo 操作动态表
        // todo 状态流转表
        // 传参里面有classType 判断是哪个类型 对应的调拨产品为 车辆  组装件  部件
        List<AllocateOrderScooterEnter> scooterEnters = new ArrayList<>();
        scooterEnters = JSONArray.parseArray(enter.getSt(), AllocateOrderScooterEnter.class);
        return new GeneralResult(enter.getRequestId());
    }


    @Override
    @Transactional
    public GeneralResult allocateEdit(AllocateOrderOrUpdateSaveEnter enter) {
        enter = SesStringUtils.objStringTrim(enter);
        // todo 操作动态表
        // 传参里面有classType 判断是哪个类型 对应的调拨产品为 车辆  组装件  部件
        return new GeneralResult(enter.getRequestId());
    }


    @Override
    public AllocateOrderDetailResult allocateDetail(IdEnter enter) {
        AllocateOrderDetailResult result = new AllocateOrderDetailResult();
        return result;
    }


    @Override
    public GeneralResult allocateConfirmOrder(IdEnter enter) {
        return new GeneralResult(enter.getRequestId());
    }


    @Override
    public GeneralResult allocateCancelOrder(IdEnter enter) {
        return new GeneralResult(enter.getRequestId());
    }


    @Override
    public List<UserDataResult> userData(UserDataEnter enter) {
        List<UserDataResult> resultList = new ArrayList<>();
        return resultList;
    }


}
