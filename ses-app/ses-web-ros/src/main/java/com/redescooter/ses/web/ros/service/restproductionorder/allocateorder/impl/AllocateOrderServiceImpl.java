package com.redescooter.ses.web.ros.service.restproductionorder.allocateorder.impl;

import com.alibaba.fastjson.JSONArray;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.tool.utils.SesStringUtils;
import com.redescooter.ses.web.ros.dm.OpeAllocateOrder;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpeAllocateOrderService;
import com.redescooter.ses.web.ros.service.restproductionorder.allocateorder.AllocateOrderService;
import com.redescooter.ses.web.ros.vo.restproductionorder.allocateorder.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassNameAllocateOrderServiceImpl
 * @Description
 * @Author Aleks
 * @Date2020/10/23 11:34
 * @Version V1.0
 **/
@Service
public class AllocateOrderServiceImpl implements AllocateOrderService {


    @Autowired
    private OpeAllocateOrderService opeAllocateOrderService;

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
        // 先处理主表数据
        OpeAllocateOrder allocateOrder = new OpeAllocateOrder();
        BeanUtils.copyProperties(enter, allocateOrder);
        allocateOrder.setUpdatedBy(enter.getUserId());
        allocateOrder.setCreatedTime(new Date());
        allocateOrder.setUpdatedBy(enter.getUserId());
        allocateOrder.setUpdatedTime(new Date());
        // todo 操作动态表
        // todo 状态流转表
        // 传参里面有classType 判断是哪个类型 对应的调拨产品为 1.车辆  2.组装件  3.部件
        switch (allocateOrder.getAllocateType()) {
            case 1:
                List<AllocateOrderScooterEnter> scooterEnters = new ArrayList<>();
                try {
                    scooterEnters = JSONArray.parseArray(enter.getSt(), AllocateOrderScooterEnter.class);
                } catch (Exception e) {
                    throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
                }
                Integer count = scooterEnters.stream().mapToInt(AllocateOrderScooterEnter::getQty).sum();
                allocateOrder.setAllocateQty(count);
            default:

                break;
            case 2:
                break;
            case 3:
                break;
        }

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
        UserDataResult user = new UserDataResult();
        user.setId(1000L);
        user.setMail("邮箱");
        user.setTelephone("电话");
        resultList.add(user);
        return resultList;
    }

    @Override
    public Map<String, Integer> listCount(GeneralEnter enter) {
        Map<String, Integer> map = new HashMap<>();
        // 1 2 3分别对应整车、组装件、部件
        map.put("1", 0);
        map.put("2", 0);
        map.put("3", 0);
        return map;
    }


}
