package com.redescooter.ses.web.ros.service.restproductionorder.inwhouse.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.enums.restproductionorder.InWhouseOrderEnum;
import com.redescooter.ses.api.common.enums.restproductionorder.OrderOperationTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.OrderTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.ProductTypeEnums;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.SesStringUtils;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.dao.restproductionorder.InWhouseOrderServiceMapper;
import com.redescooter.ses.web.ros.dm.OpeInWhouseCombinB;
import com.redescooter.ses.web.ros.dm.OpeInWhouseOrder;
import com.redescooter.ses.web.ros.dm.OpeInWhousePartsB;
import com.redescooter.ses.web.ros.dm.OpeInWhouseScooterB;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpeInWhouseCombinBService;
import com.redescooter.ses.web.ros.service.base.OpeInWhouseOrderService;
import com.redescooter.ses.web.ros.service.base.OpeInWhousePartsBService;
import com.redescooter.ses.web.ros.service.base.OpeInWhouseScooterBService;
import com.redescooter.ses.web.ros.service.restproductionorder.inwhouse.InWhouseService;
import com.redescooter.ses.web.ros.service.restproductionorder.number.OrderNumberService;
import com.redescooter.ses.web.ros.service.restproductionorder.orderflow.OrderStatusFlowService;
import com.redescooter.ses.web.ros.service.restproductionorder.trace.ProductionOrderTraceService;
import com.redescooter.ses.web.ros.vo.restproductionorder.inwhouse.*;
import com.redescooter.ses.web.ros.vo.restproductionorder.number.OrderNumberEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.optrace.SaveOpTraceEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.orderflow.OrderStatusFlowEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.purchaseorder.KeywordEnter;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @ClassNameInWhouseServiceImpl
 * @Description
 * @Author Aleks
 * @Date2020/11/11 10:20
 * @Version V1.0
 **/
@Service
public class InWhouseServiceImpl implements InWhouseService {

    @Autowired
    private OpeInWhouseOrderService opeInWhouseOrderService;

    @Autowired
    private InWhouseOrderServiceMapper inWhouseOrderServiceMapper;

    @Autowired
    private OrderNumberService orderNumberService;

    @Autowired
    private OpeInWhousePartsBService opeInWhousePartsBService;

    @Autowired
    private OpeInWhouseCombinBService opeInWhouseCombinBService;

    @Autowired
    private OpeInWhouseScooterBService opeInWhouseScooterBService;

    @Autowired
    private ProductionOrderTraceService productionOrderTraceService;

    @Autowired
    private OrderStatusFlowService orderStatusFlowService;

    @Reference
    private IdAppService idAppService;


    @Override
    public PageResult<InWhouseListResult> inWhouseList(InWhouseListEnter enter) {
        enter = SesStringUtils.objStringTrim(enter);
        int totalNum = inWhouseOrderServiceMapper.totalNum(enter);
        if (totalNum == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        List<InWhouseListResult> list = inWhouseOrderServiceMapper.inWhList(enter);
        return PageResult.create(enter, totalNum, list);
    }


    @Override
    @Transactional
    public GeneralResult inWhouseSave(InWhouseSaveOrUpdateEnter enter) {
        enter = SesStringUtils.objStringTrim(enter);
        OpeInWhouseOrder inWhouseOrder = new OpeInWhouseOrder();
        BeanUtils.copyProperties(enter,inWhouseOrder);
        inWhouseOrder.setId(idAppService.getId(SequenceName.OPE_IN_WHOUSE_ORDER));
        inWhouseOrder.setInWhStatus(InWhouseOrderEnum.DRAFT.getValue());
        inWhouseOrder.setCreatedBy(enter.getUserId());
        inWhouseOrder.setCreatedTime(new Date());
        inWhouseOrder.setUpdatedBy(enter.getUserId());
        inWhouseOrder.setUpdatedTime(new Date());
        inWhouseOrder.setInWhStatus(InWhouseOrderEnum.DRAFT.getValue());
        // 单据号
        inWhouseOrder.setInWhNo(orderNumberService.generateOrderNo(new OrderNumberEnter(OrderTypeEnums.FACTORY_INBOUND.getValue())));
        // 统计入库数量
        countQty(inWhouseOrder,enter.getSt());
        opeInWhouseOrderService.saveOrUpdate(inWhouseOrder);
        // 处理子表
        createInWhouseB(inWhouseOrder,enter);
        // 操作记录
        SaveOpTraceEnter opTraceEnter = new SaveOpTraceEnter(null, inWhouseOrder.getId(), OrderTypeEnums.FACTORY_INBOUND.getValue(), OrderOperationTypeEnums.CREATE.getValue(),
                inWhouseOrder.getRemark());
        opTraceEnter.setUserId(enter.getUserId());
        productionOrderTraceService.save(opTraceEnter);
        // 状态流转
        OrderStatusFlowEnter orderStatusFlowEnter = new OrderStatusFlowEnter(null, inWhouseOrder.getInWhStatus(), OrderTypeEnums.FACTORY_INBOUND.getValue(), inWhouseOrder.getId(),
                inWhouseOrder.getRemark());
        orderStatusFlowEnter.setUserId(enter.getUserId());
        orderStatusFlowService.save(orderStatusFlowEnter);
        return new GeneralResult(enter.getRequestId());
    }



    public void createInWhouseB(OpeInWhouseOrder inWhouseOrder,InWhouseSaveOrUpdateEnter enter){
        switch (inWhouseOrder.getOrderType()){
            case 1:
                // scooter
                List<SaveOrUpdateScooterBEnter> scooterEnters = new ArrayList<>();
                try {
                    scooterEnters = JSONArray.parseArray(enter.getSt(), SaveOrUpdateScooterBEnter.class);
                } catch (Exception e) {
                    throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
                }
                List<OpeInWhouseScooterB> scooterBList = new ArrayList<>();
                for (SaveOrUpdateScooterBEnter scooterEnter : scooterEnters) {
                    OpeInWhouseScooterB scooterB = new OpeInWhouseScooterB();
                    BeanUtils.copyProperties(scooterEnter,scooterB);
                    scooterB.setInWhId(inWhouseOrder.getId());
                    scooterB.setId(idAppService.getId(SequenceName.OPE_IN_WHOUSE_SCOOTER_B));
                    scooterB.setCreatedBy(enter.getUserId());
                    scooterB.setCreatedTime(new Date());
                    scooterB.setUpdatedBy(enter.getUserId());
                    scooterB.setUpdatedTime(new Date());
                    scooterBList.add(scooterB);
                }
                opeInWhouseScooterBService.saveOrUpdateBatch(scooterBList);
            default:
                break;
            case 2:
                // combin
                List<SaveOrUpdateCombinBEnter> combinBEnters = new ArrayList<>();
                try {
                    combinBEnters = JSONArray.parseArray(enter.getSt(), SaveOrUpdateCombinBEnter.class);
                } catch (Exception e) {
                    throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
                }
                List<OpeInWhouseCombinB> combinBList = new ArrayList<>();
                for (SaveOrUpdateCombinBEnter combinBEnter : combinBEnters) {
                    OpeInWhouseCombinB combinB = new OpeInWhouseCombinB();
                    BeanUtils.copyProperties(combinBEnter,combinB);
                    combinB.setInWhId(inWhouseOrder.getId());
                    combinB.setId(idAppService.getId(SequenceName.OPE_IN_WHOUSE_COMBIN_B));
                    combinB.setCreatedBy(enter.getUserId());
                    combinB.setCreatedTime(new Date());
                    combinB.setUpdatedBy(enter.getUserId());
                    combinB.setUpdatedTime(new Date());
                    combinBList.add(combinB);
                }
                opeInWhouseCombinBService.saveOrUpdateBatch(combinBList);
                break;
            case 3:
                // parts
                List<SaveOrUpdatePartsBEnter> partsBEnters = new ArrayList<>();
                try {
                    partsBEnters = JSONArray.parseArray(enter.getSt(), SaveOrUpdatePartsBEnter.class);
                } catch (Exception e) {
                    throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
                }
                List<OpeInWhousePartsB> partsBList = new ArrayList<>();
                for (SaveOrUpdatePartsBEnter partsBEnter : partsBEnters) {
                    OpeInWhousePartsB partsB = new OpeInWhousePartsB();
                    BeanUtils.copyProperties(partsBEnter,partsB);
                    partsB.setInWhId(inWhouseOrder.getId());
                    partsB.setId(idAppService.getId(SequenceName.OPE_IN_WHOUSE_PARTS_B));
                    partsB.setCreatedBy(enter.getUserId());
                    partsB.setCreatedTime(new Date());
                    partsB.setUpdatedBy(enter.getUserId());
                    partsB.setUpdatedTime(new Date());
                    partsBList.add(partsB);
                }
                opeInWhousePartsBService.saveOrUpdateBatch(partsBList);
                break;
        }
    }



    public void countQty(OpeInWhouseOrder inWhouseOrder,String st){
        switch (inWhouseOrder.getOrderType()){
            case 1:
                // scooter
                List<SaveOrUpdateScooterBEnter> scooterEnters = new ArrayList<>();
                try {
                    scooterEnters = JSONArray.parseArray(st, SaveOrUpdateScooterBEnter.class);
                } catch (Exception e) {
                    throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
                }
                inWhouseOrder.setInWhQty(scooterEnters.stream().mapToInt(SaveOrUpdateScooterBEnter::getInWhQty).sum());
                inWhouseOrder.setRelationOrderType(OrderTypeEnums.FACTORY_PURCHAS.getValue());
            default:
                break;
            case 2:
                // combin
                List<SaveOrUpdateCombinBEnter> combinBEnters = new ArrayList<>();
                try {
                    combinBEnters = JSONArray.parseArray(st, SaveOrUpdateCombinBEnter.class);
                } catch (Exception e) {
                    throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
                }
                inWhouseOrder.setInWhQty(combinBEnters.stream().mapToInt(SaveOrUpdateCombinBEnter::getInWhQty).sum());
                inWhouseOrder.setRelationOrderType(OrderTypeEnums.COMBIN_ORDER.getValue());
                break;
            case 3:
                // parts
                List<SaveOrUpdatePartsBEnter> partsBEnters = new ArrayList<>();
                try {
                    partsBEnters = JSONArray.parseArray(st, SaveOrUpdatePartsBEnter.class);
                } catch (Exception e) {
                    throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
                }
                inWhouseOrder.setInWhQty(partsBEnters.stream().mapToInt(SaveOrUpdatePartsBEnter::getInWhQty).sum());
                inWhouseOrder.setRelationOrderType(OrderTypeEnums.COMBIN_ORDER.getValue());
                break;
        }
    }


    @Override
    @Transactional
    public GeneralResult inWhouseEdit(InWhouseSaveOrUpdateEnter enter) {
        return new GeneralResult(enter.getRequestId());
    }

    @Override
    public Map<String, Integer> listCount(GeneralEnter enter) {
        Map<String, Integer> map = new HashMap<>();

        QueryWrapper<OpeInWhouseOrder> scooter = new QueryWrapper<>();
        scooter.eq(OpeInWhouseOrder.COL_ORDER_TYPE, ProductTypeEnums.SCOOTER.getValue());
        map.put("1",opeInWhouseOrderService.count(scooter));

        QueryWrapper<OpeInWhouseOrder> combin = new QueryWrapper<>();
        combin.eq(OpeInWhouseOrder.COL_ORDER_TYPE, ProductTypeEnums.COMBINATION.getValue());
        map.put("2",opeInWhouseOrderService.count(combin));

        QueryWrapper<OpeInWhouseOrder> parts = new QueryWrapper<>();
        parts.eq(OpeInWhouseOrder.COL_ORDER_TYPE, ProductTypeEnums.PARTS.getValue());
        map.put("3",opeInWhouseOrderService.count(parts));
        return map;
    }

    @Override
    @Transactional
    public GeneralResult inWhouseDelete(IdEnter enter) {
        opeInWhouseOrderService.removeById(enter.getId());
        return new GeneralResult(enter.getRequestId());
    }

    @Override
    public InWhouseDetailResult inWhouseDetail(IdEnter enter) {
        InWhouseDetailResult result = new InWhouseDetailResult();
        return result;
    }

    @Override
    @Transactional
    public GeneralResult inWhConfirm(IdEnter enter) {
        return new GeneralResult(enter.getRequestId());
    }

    @Override
    @Transactional
    public GeneralResult inWhReadyQc(IdEnter enter) {
        return new GeneralResult(enter.getRequestId());
    }

    @Override
    public List<InWhRelationOrderResult> relationPurchaseOrderData(KeywordEnter enter) {
        List<InWhRelationOrderResult> list = new ArrayList<>();

        return list;
    }

    @Override
    public List<SaveOrUpdatePartsBEnter> relationPurchaseOrderPartsData(IdEnter enter) {
        List<SaveOrUpdatePartsBEnter> list = new ArrayList<>();
        return list;
    }

    @Override
    public List<InWhRelationOrderResult> relationCombinOrderData(KeywordEnter enter) {
        List<InWhRelationOrderResult> list = new ArrayList<>();
        return list;
    }

    @Override
    public List<SaveOrUpdateCombinBEnter> relationCombinOrderCombinData(IdEnter enter) {
        List<SaveOrUpdateCombinBEnter> list = new ArrayList<>();
        return list;
    }

    @Override
    public List<SaveOrUpdateScooterBEnter> relationCombinOrderScooterData(IdEnter enter) {
        List<SaveOrUpdateScooterBEnter> list = new ArrayList<>();
        return list;
    }
}
