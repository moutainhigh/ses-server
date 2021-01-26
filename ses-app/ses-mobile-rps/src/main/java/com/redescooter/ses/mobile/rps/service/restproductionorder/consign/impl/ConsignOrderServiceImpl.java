package com.redescooter.ses.mobile.rps.service.restproductionorder.consign.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.enums.restproductionorder.OrderOperationTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.OrderTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.ProductTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.consign.ConsignOrderStatusEnums;
import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.mobile.rps.constant.SequenceName;
import com.redescooter.ses.mobile.rps.dao.restproductionorder.consign.ConsignOrderServiceMapper;
import com.redescooter.ses.mobile.rps.dm.*;
import com.redescooter.ses.mobile.rps.exception.ExceptionCodeEnums;
import com.redescooter.ses.mobile.rps.exception.SesMobileRpsException;
import com.redescooter.ses.mobile.rps.service.base.*;
import com.redescooter.ses.mobile.rps.service.restproductionorder.consign.ConsignOrderService;
import com.redescooter.ses.mobile.rps.service.restproductionorder.orderflow.OrderStatusFlowService;
import com.redescooter.ses.mobile.rps.service.restproductionorder.trace.ProductionOrderTraceService;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.consign.*;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.optrace.SaveOpTraceEnter;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.orderflow.OrderStatusFlowEnter;
import com.redescooter.ses.starter.common.service.IdAppService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 *  @author: alex
 *  @Date: 2020/10/22 13:26
 *  @version：V ROS 1.8.3
 *  @Description:
 */
@Slf4j
@Service
public class ConsignOrderServiceImpl implements ConsignOrderService {

    @Autowired
    private ConsignOrderServiceMapper consignOrderServiceMapper;

    @Autowired
    private OpeEntrustOrderService opeEntrustOrderService;

    @Autowired
    private OpeInvoiceProductSerialNumService opeInvoiceProductSerialNumService;

    @Autowired
    private OpeLogisticsOrderService opeLogisticsOrderService;

    @Autowired
    private OrderStatusFlowService orderStatusFlowService;

    @Autowired
    private ProductionOrderTraceService productionOrderTraceService;

    @Autowired
    private OpeEntrustProductSerialNumService opeEntrustProductSerialNumService;

    @Reference
    private IdAppService idAppService;

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/11/2 12:04 下午
     * @Param: enter
     * @Return: Map
     * @desc: countByProductType
     * @param enter
     */
    @Override
    public Map<Integer, Integer> countByProductType(GeneralEnter enter) {
        Map<Integer, Integer> map = new HashMap<>();
        List<CountByStatusResult> countByStatusResultList = consignOrderServiceMapper.countByProductType(enter);
        map = countByStatusResultList.stream().collect(Collectors.toMap(item -> {
            return Integer.valueOf(item.getStatus());
        }, CountByStatusResult::getTotalCount));

        for (ProductTypeEnums item : ProductTypeEnums.values()) {
            if (!map.containsKey(item.getValue())) {
                map.put(item.getValue(), 0);
            }
        }
        return map;
    }

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/11/3 6:06 下午
     * @Param: enter
     * @Return: ConsignOrderListResult
     * @desc: 委托单列表
     * @param enter
     */
    @Override
    public PageResult<ConsignOrderListResult> list(ConsignOrderListEnter enter) {
        int count = consignOrderServiceMapper.listCount(enter);
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        return PageResult.create(enter, count, consignOrderServiceMapper.list(enter));
    }

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/11/3 7:24 下午
     * @Param: enter
     * @Return: ConsignDetailResult
     * @desc: 单据详情
     * @param enter
     */
    @Override
    public ConsignDetailResult detail(IdEnter enter) {
        ConsignDetailResult detail = consignOrderServiceMapper.detail(enter);
        if (detail == null) {
            throw new SesMobileRpsException(ExceptionCodeEnums.ORDER_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_IS_NOT_EXIST.getMessage());
        }
        detail.setConsignProductSerialResult(this.detailProductSerialList(enter));
        return detail;
    }


    /**
     * @Description
     * @Author: alex
     * @Date: 2020/11/5 2:01 下午
     * @Param: enter
     * @Return: detailProductList
     * @desc: 产品的序列号
     * @param enter
     */
    @Override
    public List<ConsignProductSerialResult> detailProductSerialList(IdEnter enter) {
        OpeEntrustOrder opeEntrustOrder = opeEntrustOrderService.getById(enter.getId());
        if (opeEntrustOrder == null) {
            throw new SesMobileRpsException(ExceptionCodeEnums.ORDER_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_IS_NOT_EXIST.getMessage());
        }

        List<ConsignProductSerialResult> resultList=new ArrayList<>();
        switch (opeEntrustOrder.getEntrustType()) {
            case 1:
                resultList=consignOrderServiceMapper.detailProductScooterList(enter.getId());
                if (CollectionUtils.isEmpty(resultList)){
                    throw new SesMobileRpsException(ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getCode());
                }
                break;
            case 2:
                resultList=consignOrderServiceMapper.detailProductCombinList(enter.getId());
                break;
            default:
                resultList=consignOrderServiceMapper.detailProductPartList(enter.getId());
                break;
        }
        return resultList;
    }

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/11/3 7:56 下午
     * @Param: enter
     * @Return: BooleanResult
     * @desc: 序列号校验
     * @param enter
     */
    @Override
    public BooleanResult checkSerial(StringEnter enter) {
        return null;
    }

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/11/3 7:34 下午
     * @Param: alex
     * @Return: GeneralResult
     * @desc: 出库
     * @param enter
     */
    @Override
    public GeneralResult ship(ConsignShipEnter enter) {

        List<ConsignShipProductEnter> consignShipProductEnters = new ArrayList<>();
        try {
            consignShipProductEnters= JSON.parseArray(enter.getSt(),ConsignShipProductEnter.class);
        }catch (Exception e) {
            throw new SesMobileRpsException(ExceptionCodeEnums.ILLEGAL_DATA.getCode(),ExceptionCodeEnums.ILLEGAL_DATA.getMessage());
        }
        if(CollectionUtils.isEmpty(consignShipProductEnters)){
            throw new SesMobileRpsException(ExceptionCodeEnums.ILLEGAL_DATA.getCode(),ExceptionCodeEnums.ILLEGAL_DATA.getMessage());
        }

        //查询
        OpeEntrustOrder opeEntrustOrder = opeEntrustOrderService.getById(enter.getId());
        if (opeEntrustOrder==null){
            throw new SesMobileRpsException(ExceptionCodeEnums.ORDER_IS_NOT_EXIST.getCode(),ExceptionCodeEnums.ORDER_IS_NOT_EXIST.getMessage());
        }

        //查询 产品序列号 并进行归类
        QueryWrapper<OpeInvoiceProductSerialNum> operatorInvoiceProductSerialNumQuery=new QueryWrapper<>();
        operatorInvoiceProductSerialNumQuery.in(OpeInvoiceProductSerialNum.COL_RELATION_ID,consignShipProductEnters.stream().map(ConsignShipProductEnter::getId).collect(Collectors.toSet()));
        operatorInvoiceProductSerialNumQuery.gt(OpeInvoiceProductSerialNum.COL_QTY, 0);
        List<OpeInvoiceProductSerialNum> orderQcItemList = opeInvoiceProductSerialNumService.list(operatorInvoiceProductSerialNumQuery);
        if (CollectionUtils.isEmpty(orderQcItemList)){
            throw new SesMobileRpsException(ExceptionCodeEnums.QC_INFO_IS_EMPTY.getCode(),ExceptionCodeEnums.QC_INFO_IS_EMPTY.getMessage());
        }

        Map<Long,List<OpeInvoiceProductSerialNum>> serialMap=new HashMap<>();
        orderQcItemList.forEach(item->{
            if (serialMap.containsKey(item.getRelationId())){
                List<OpeInvoiceProductSerialNum> opeInvoiceProductSerialNumList = serialMap.get(item.getRelationId());
                opeInvoiceProductSerialNumList.add(item);
                serialMap.put(item.getRelationId(),opeInvoiceProductSerialNumList);
            }
        });

        //保存委托单记录
        List<OpeEntrustProductSerialNum> saveOpeEntrustProductSerialNumList=new ArrayList<> ();

        //校验序列号
        for (ConsignShipProductEnter item : consignShipProductEnters) {
            Boolean checkSerial = Boolean.TRUE;
            for (Map.Entry<Long, List<OpeInvoiceProductSerialNum>> entry : serialMap.entrySet()) {
                Long key = entry.getKey();
                List<OpeInvoiceProductSerialNum> value = entry.getValue();
                if (item.getIdclass()) {
                    //有序列号的 直接保存数据
                    List<String> serialList = value.stream().map(OpeInvoiceProductSerialNum::getSerialNum).collect(Collectors.toList());
                    if (!serialList.contains(item.getSerialN())) {
                        checkSerial = Boolean.FALSE;
                        break;
                    }else {
                        OpeEntrustProductSerialNum opeEntrustProductSerialNum = buildOpeEntrustProductSerialNum(enter, item, value);
                        saveOpeEntrustProductSerialNumList.add(opeEntrustProductSerialNum);
                    }
                } else {
                    //无序列号的 数量必须匹配 不匹配 报错
                    List<Integer> qtyList = value.stream().map(OpeInvoiceProductSerialNum::getQty).collect(Collectors.toList());
                    if (!qtyList.contains(item.getQty())) {
                        checkSerial = Boolean.FALSE;
                        break;
                    }else {
                        OpeEntrustProductSerialNum opeEntrustProductSerialNum = buildOpeEntrustProductSerialNum(enter, item, value);
                        saveOpeEntrustProductSerialNumList.add(opeEntrustProductSerialNum);
                    }
                }

            }
            if (!checkSerial) {
                throw new SesMobileRpsException(ExceptionCodeEnums.ILLEGAL_DATA.getCode(), ExceptionCodeEnums.ILLEGAL_DATA.getMessage());
            }
        }

        if (CollectionUtils.isNotEmpty(saveOpeEntrustProductSerialNumList)){
            opeEntrustProductSerialNumService.saveBatch(saveOpeEntrustProductSerialNumList);
        }

        //注定单修改状态
        UpdateConsignStatusEnter updateConsignStatusEnter = new UpdateConsignStatusEnter(opeEntrustOrder.getId(),ConsignOrderStatusEnums.BE_SIGNED.getValue(),OrderOperationTypeEnums.SHIPMENT.getValue());
        updateConsignStatusEnter.setUserId(enter.getUserId());
        updateStatus(updateConsignStatusEnter);

        //形成发货单
        SaveLogisticsOrderEnter saveLogisticsOrderEnter = new SaveLogisticsOrderEnter(opeEntrustOrder.getId(),enter.getShipmentN(),enter.getLogisticsCompany(),opeEntrustOrder.getRemark());
        saveLogisticsOrderEnter.setUserId(enter.getUserId());
        saveLogisticsOrder(saveLogisticsOrderEnter);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/11/6 2:32 下午
     * @Param: enter
     * @Return: GeneralResult
     * @desc: 更新状态
     * @param enter
     */
    @Transactional
    @Override
    public GeneralResult updateStatus(UpdateConsignStatusEnter enter) {
        OpeEntrustOrder opeEntrustOrder = opeEntrustOrderService.getById(enter.getId());
        if (opeEntrustOrder==null){
            throw new SesMobileRpsException(ExceptionCodeEnums.ORDER_IS_NOT_EXIST.getCode(),ExceptionCodeEnums.ORDER_IS_NOT_EXIST.getMessage());
        }
        //修改主订单状态
        opeEntrustOrder.setEntrustStatus(enter.getStatus());
        opeEntrustOrder.setCreatedBy(enter.getId());
        opeEntrustOrder.setCreatedTime(new Date());
        opeEntrustOrderService.updateById(opeEntrustOrder);

        //操作动态
        SaveOpTraceEnter saveOpTraceEnter = new SaveOpTraceEnter(null, opeEntrustOrder.getId(), OrderTypeEnums.ORDER.getValue(), enter.getOperatingDynamics(),
                opeEntrustOrder.getRemark());
        saveOpTraceEnter.setUserId(enter.getUserId());
        productionOrderTraceService.save(saveOpTraceEnter);

        //订单节点
        OrderStatusFlowEnter orderStatusFlowEnter = new OrderStatusFlowEnter(null,opeEntrustOrder.getEntrustStatus(), OrderTypeEnums.ORDER.getValue(), opeEntrustOrder.getId(),
                opeEntrustOrder.getRemark());
        orderStatusFlowEnter.setUserId(enter.getUserId());
        orderStatusFlowService.save(orderStatusFlowEnter);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/11/6 2:33 下午
     * @Param: enter
     * @Return: GeneralResult
     * @desc: 保存物流运输单
     * @param enter
     */
    @Transactional
    @Override
    public GeneralResult saveLogisticsOrder(SaveLogisticsOrderEnter enter) {
        OpeLogisticsOrder opeLogisticsOrder = new OpeLogisticsOrder();
        BeanUtils.copyProperties(enter,opeLogisticsOrder);
        opeLogisticsOrder.setId(idAppService.getId(SequenceName.OPE_LOGISTICS_ORDER));
        opeLogisticsOrder.setDr(0);
        opeLogisticsOrder.setCreatedBy(enter.getUserId());
        opeLogisticsOrder.setCreatedTime(new Date());
        opeLogisticsOrder.setUpdatedBy(enter.getUserId());
        opeLogisticsOrder.setUpdatedTime(new Date());
        opeLogisticsOrderService.save(opeLogisticsOrder);

//        //操作动态
//        SaveOpTraceEnter saveOpTraceEnter = new SaveOpTraceEnter(null, opeLogisticsOrder.getId(), OrderTypeEnums..getValue(), OrderOperationTypeEnums.CREATE.getValue(),
//                opeLogisticsOrder.getRemark());
//        saveOpTraceEnter.setUserId(enter.getUserId());
//        productionOrderTraceService.save(saveOpTraceEnter);
//
//        //订单节点
//        OrderStatusFlowEnter orderStatusFlowEnter = new OrderStatusFlowEnter(null,opeEntrustOrder.getEntrustStatus(), OrderTypeEnums.ORDER.getValue(), opeEntrustOrder.getId(),
//                opeEntrustOrder.getRemark());
//        orderStatusFlowEnter.setUserId(enter.getUserId());
//        orderStatusFlowService.save(orderStatusFlowEnter);
        return new GeneralResult(enter.getRequestId());
    }

    private OpeEntrustProductSerialNum buildOpeEntrustProductSerialNum(ConsignShipEnter enter, ConsignShipProductEnter item, List<OpeInvoiceProductSerialNum> value) {
        OpeInvoiceProductSerialNum opeInvoiceProductSerialNum = value.stream().filter(serial -> StringUtils.equals(serial.getSerialNum(), item.getSerialN())).findFirst().orElse(null);

        OpeEntrustProductSerialNum opeEntrustProductSerialNum = new OpeEntrustProductSerialNum();
        opeEntrustProductSerialNum.setId(idAppService.getId(SequenceName.OPE_ENTRUST_PRODUCT_SERIAL_NUM));
        opeEntrustProductSerialNum.setDr(0);
        opeEntrustProductSerialNum.setRelationId(item.getId());
        opeEntrustProductSerialNum.setRelationType(opeInvoiceProductSerialNum.getRelationType());
        opeEntrustProductSerialNum.setLot(opeInvoiceProductSerialNum.getLot());
        opeEntrustProductSerialNum.setIdClass(item.getIdclass()?1:0);
        opeEntrustProductSerialNum.setSerialNum(item.getSerialN());
        opeEntrustProductSerialNum.setQty(item.getQty());
        opeEntrustProductSerialNum.setVersion(opeEntrustProductSerialNum.getVersion());
        opeEntrustProductSerialNum.setRemark(opeEntrustProductSerialNum.getRemark());
        opeEntrustProductSerialNum.setCreatedBy(enter.getUserId());
        opeEntrustProductSerialNum.setCreatedTime(new Date());
        opeEntrustProductSerialNum.setUpdatedBy(enter.getUserId());
        opeEntrustProductSerialNum.setUpdatedTime(new Date());
        return opeEntrustProductSerialNum;
    }
}
