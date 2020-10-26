package com.redescooter.ses.web.ros.service.restproductionorder.allocateorder.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.enums.production.allocate.AllocateOrderStatusEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.AllocateOrderStatusEnum;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.SesStringUtils;
import com.redescooter.ses.web.ros.dao.restproductionorder.AllocateOrderServiceMapper;
import com.redescooter.ses.web.ros.dm.*;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.*;
import com.redescooter.ses.web.ros.service.restproductionorder.allocateorder.AllocateOrderService;
import com.redescooter.ses.web.ros.vo.restproductionorder.allocateorder.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.dubbo.config.annotation.Reference;
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

    @Autowired
    private OpeAllocateScooterBService opeAllocateScooterBService;

    @Autowired
    private OpeAllocateCombinBService opeAllocateCombinBService;

    @Autowired
    private OpeAllocatePartsBService opeAllocatePartsBService;

    @Autowired
    private OpeOpTraceService opeOpTraceService;

    @Autowired
    private OpeOrderStatusFlowService opeOrderStatusFlowService;

    @Autowired
    private AllocateOrderServiceMapper allocateOrderServiceMapper;

    @Reference
    private IdAppService idAppService;


    @Override
    public PageResult<AllocateOrderListResult> allocateList(AllocateOrderListEnter enter) {
        enter = SesStringUtils.objStringTrim(enter);
        int totalNum = allocateOrderServiceMapper.allocateTotal(enter);
        if (totalNum == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        List<AllocateOrderListResult> list = allocateOrderServiceMapper.allocateList(enter);
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
        // 传参里面有classType 判断是哪个类型 对应的调拨产品为 1.车辆  2.组装件  3.部件
        // 合计总的调拨数量
        getConuntQty(enter, allocateOrder);
        allocateOrder.setAllocateStatus(AllocateOrderStatusEnum.DRAFT.getValue());
        allocateOrder.setId(idAppService.getId(""));
        opeAllocateOrderService.saveOrUpdate(allocateOrder);
        // 处理调拨单的子表
        createAllocateB(enter, allocateOrder);
        // 操作动态表
        OpeOpTrace opTrace = new OpeOpTrace();
        opTrace.setRelationId(allocateOrder.getId());
        opTrace.setOpType(1);
        opTrace.setCreatedBy(enter.getUserId());
        opTrace.setCreatedTime(new Date());
        opTrace.setUpdatedBy(enter.getUserId());
        opTrace.setUpdatedTime(new Date());
        opTrace.setId(idAppService.getId(""));
        opeOpTraceService.saveOrUpdate(opTrace);
        // 状态流转表
        OpeOrderStatusFlow statusFlow = new OpeOrderStatusFlow();
        statusFlow.setRelationId(allocateOrder.getId());
        statusFlow.setOrderStatus(allocateOrder.getAllocateStatus());
        statusFlow.setId(idAppService.getId(""));
        statusFlow.setCreatedBy(enter.getUserId());
        statusFlow.setCreatedTime(new Date());
        statusFlow.setUpdatedBy(enter.getUserId());
        statusFlow.setUpdatedTime(new Date());
        opeOrderStatusFlowService.saveOrUpdate(statusFlow);
        return new GeneralResult(enter.getRequestId());
    }


    private void createAllocateB(AllocateOrderOrUpdateSaveEnter enter, OpeAllocateOrder allocateOrder) {
        switch (allocateOrder.getAllocateType()) {
            case 1:
                List<AllocateOrderScooterEnter> scooterEnters = new ArrayList<>();
                try {
                    scooterEnters = JSONArray.parseArray(enter.getSt(), AllocateOrderScooterEnter.class);
                } catch (Exception e) {
                    throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
                }
                List<OpeAllocateScooterB> scooterBS = new ArrayList<>();
                for (AllocateOrderScooterEnter scooterEnter : scooterEnters) {
                    OpeAllocateScooterB scooterB = new OpeAllocateScooterB();
                    BeanUtils.copyProperties(scooterEnter, scooterB);
                    scooterB.setAllocateId(allocateOrder.getId());
                    scooterB.setCreatedBy(enter.getUserId());
                    scooterB.setCreatedTime(new Date());
                    scooterB.setUpdatedBy(enter.getUserId());
                    scooterB.setUpdatedTime(new Date());
                    scooterB.setId(idAppService.getId(""));
                    scooterBS.add(scooterB);
                }
                opeAllocateScooterBService.saveOrUpdateBatch(scooterBS);
            default:
                break;
            case 2:
                List<AllocateOrderCombinEnter> combinEnters = new ArrayList<>();
                try {
                    combinEnters = JSONArray.parseArray(enter.getSt(), AllocateOrderCombinEnter.class);
                } catch (Exception e) {
                    throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
                }
                List<OpeAllocateCombinB>  combinBS = new ArrayList<>();
                for (AllocateOrderCombinEnter combinEnter : combinEnters) {
                    OpeAllocateCombinB combinB = new OpeAllocateCombinB();
                    BeanUtils.copyProperties(combinEnter,combinB);
                    combinB.setAllocateId(allocateOrder.getId());
                    combinB.setCreatedBy(enter.getUserId());
                    combinB.setCreatedTime(new Date());
                    combinB.setUpdatedBy(enter.getUserId());
                    combinB.setUpdatedTime(new Date());
                    combinB.setId(idAppService.getId(""));
                    combinBS.add(combinB);
                }
                opeAllocateCombinBService.saveOrUpdateBatch(combinBS);
                break;
            case 3:
                List<AllocateOrderPartsEnter> partsEnters = new ArrayList<>();
                try {
                    partsEnters = JSONArray.parseArray(enter.getSt(), AllocateOrderPartsEnter.class);
                } catch (Exception e) {
                    throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
                }
                List<OpeAllocatePartsB> partsBS = new ArrayList<>();
                for (AllocateOrderPartsEnter partsEnter : partsEnters){
                    OpeAllocatePartsB partsB = new OpeAllocatePartsB();
                    BeanUtils.copyProperties(partsEnter,partsB);
                    partsB.setAllocateId(allocateOrder.getId());
                    partsB.setCreatedBy(enter.getUserId());
                    partsB.setCreatedTime(new Date());
                    partsB.setUpdatedBy(enter.getUserId());
                    partsB.setUpdatedTime(new Date());
                    partsB.setId(idAppService.getId(""));
                    partsBS.add(partsB);
                }
                opeAllocatePartsBService.saveOrUpdateBatch(partsBS);
                break;
        }
    }

    private void getConuntQty(AllocateOrderOrUpdateSaveEnter enter, OpeAllocateOrder allocateOrder) {
        switch (allocateOrder.getAllocateType()) {
            case 1:
                List<AllocateOrderScooterEnter> scooterEnters = new ArrayList<>();
                try {
                    scooterEnters = JSONArray.parseArray(enter.getSt(), AllocateOrderScooterEnter.class);
                } catch (Exception e) {
                    throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
                }
                allocateOrder.setAllocateQty(scooterEnters.stream().mapToInt(AllocateOrderScooterEnter::getQty).sum());
            default:
                break;
            case 2:
                List<AllocateOrderCombinEnter>  combinEnters = new ArrayList<>();
                try {
                    combinEnters = JSONArray.parseArray(enter.getSt(), AllocateOrderCombinEnter.class);
                } catch (Exception e) {
                    throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
                }
                allocateOrder.setAllocateQty(combinEnters.stream().mapToInt(AllocateOrderCombinEnter::getQty).sum());
                break;
            case 3:
                List<AllocateOrderPartsEnter>  partsEnters = new ArrayList<>();
                try {
                    partsEnters = JSONArray.parseArray(enter.getSt(), AllocateOrderPartsEnter.class);
                } catch (Exception e) {
                    throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
                }
                allocateOrder.setAllocateQty(partsEnters.stream().mapToInt(AllocateOrderPartsEnter::getQty).sum());
                break;
        }
    }


    @Override
    @Transactional
    public GeneralResult allocateEdit(AllocateOrderOrUpdateSaveEnter enter) {
        enter = SesStringUtils.objStringTrim(enter);
        OpeAllocateOrder allocateOrder = opeAllocateOrderService.getById(enter.getId());
        if (allocateOrder == null){
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        BeanUtils.copyProperties(enter,allocateOrder);
        getConuntQty(enter, allocateOrder);
        // 编辑的时候 先把下面的产品删除  再重新插入
        deleteOrderB(allocateOrder);
        createAllocateB(enter, allocateOrder);
        // 操作动态表
        OpeOpTrace opTrace = new OpeOpTrace();
        opTrace.setRelationId(allocateOrder.getId());
        opTrace.setOpType(2);
        opTrace.setCreatedBy(enter.getUserId());
        opTrace.setCreatedTime(new Date());
        opTrace.setUpdatedBy(enter.getUserId());
        opTrace.setUpdatedTime(new Date());
        opTrace.setId(idAppService.getId(""));
        opeOpTraceService.saveOrUpdate(opTrace);
        return new GeneralResult(enter.getRequestId());
    }


    // 删除调拨单的产品数据
    public void deleteOrderB(OpeAllocateOrder allocateOrder){
        switch (allocateOrder.getAllocateType()){
            case 1:
                // 车辆
                QueryWrapper<OpeAllocateScooterB> scooterBQueryWrapper = new QueryWrapper<>();
                scooterBQueryWrapper.eq(OpeAllocateScooterB.COL_ALLOCATE_ID,allocateOrder.getId());
                List<OpeAllocateScooterB> scooterBList = opeAllocateScooterBService.list(scooterBQueryWrapper);
                if(CollectionUtils.isNotEmpty(scooterBList)){
                    opeAllocateScooterBService.removeByIds(scooterBList.stream().map(OpeAllocateScooterB::getId).collect(Collectors.toList()));
                }
            default:
                    break;
            case 2:
                // 组装件
                QueryWrapper<OpeAllocateCombinB> combinBQueryWrapper = new QueryWrapper<>();
                combinBQueryWrapper.eq(OpeAllocateCombinB.COL_ALLOCATE_ID,allocateOrder.getId());
                List<OpeAllocateCombinB> combinBList = opeAllocateCombinBService.list(combinBQueryWrapper);
                if(CollectionUtils.isNotEmpty(combinBList)){
                    opeAllocateCombinBService.removeByIds(combinBList.stream().map(OpeAllocateCombinB::getId).collect(Collectors.toList()));
                }
                break;
            case 3:
                // 部件
                QueryWrapper<OpeAllocatePartsB> partsBQueryWrapper = new QueryWrapper<>();
                partsBQueryWrapper.eq(OpeAllocatePartsB.COL_ALLOCATE_ID,allocateOrder.getId());
                List<OpeAllocatePartsB> partsBList = opeAllocatePartsBService.list(partsBQueryWrapper);
                if(CollectionUtils.isNotEmpty(partsBList)){
                    opeAllocatePartsBService.removeByIds(partsBList.stream().map(OpeAllocatePartsB::getId).collect(Collectors.toList()));
                }
                break;
        }
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

    @Override
    public List<WhDataResult> whData(WhDataEnter enter) {
        List<WhDataResult> list = allocateOrderServiceMapper.whData(enter);
        return list;
    }


}
