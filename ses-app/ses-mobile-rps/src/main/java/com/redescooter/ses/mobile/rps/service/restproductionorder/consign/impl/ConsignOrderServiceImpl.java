package com.redescooter.ses.mobile.rps.service.restproductionorder.consign.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.enums.restproductionorder.OrderOperationTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.OrderTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.ProductTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.consign.ConsignOrderStatusEnums;
import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.BooleanResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.StringEnter;
import com.redescooter.ses.mobile.rps.constant.SequenceName;
import com.redescooter.ses.mobile.rps.dao.restproductionorder.consign.ConsignOrderServiceMapper;
import com.redescooter.ses.mobile.rps.dm.OpeEntrustOrder;
import com.redescooter.ses.mobile.rps.dm.OpeEntrustProductSerialNum;
import com.redescooter.ses.mobile.rps.dm.OpeInvoiceProductSerialNum;
import com.redescooter.ses.mobile.rps.dm.OpeLogisticsOrder;
import com.redescooter.ses.mobile.rps.exception.ExceptionCodeEnums;
import com.redescooter.ses.mobile.rps.exception.SesMobileRpsException;
import com.redescooter.ses.mobile.rps.service.base.OpeEntrustOrderService;
import com.redescooter.ses.mobile.rps.service.base.OpeEntrustProductSerialNumService;
import com.redescooter.ses.mobile.rps.service.base.OpeInvoiceProductSerialNumService;
import com.redescooter.ses.mobile.rps.service.base.OpeLogisticsOrderService;
import com.redescooter.ses.mobile.rps.service.restproductionorder.consign.ConsignOrderService;
import com.redescooter.ses.mobile.rps.service.restproductionorder.orderflow.OrderStatusFlowService;
import com.redescooter.ses.mobile.rps.service.restproductionorder.trace.ProductionOrderTraceService;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.consign.ConsignDetailResult;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.consign.ConsignOrderListEnter;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.consign.ConsignOrderListResult;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.consign.ConsignProductSerialResult;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.consign.ConsignShipEnter;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.consign.ConsignShipProductEnter;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.consign.SaveLogisticsOrderEnter;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.consign.UpdateConsignStatusEnter;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.optrace.SaveOpTraceEnter;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.orderflow.OrderStatusFlowEnter;
import com.redescooter.ses.starter.common.service.IdAppService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: alex
 * @Date: 2020/10/22 13:26
 * @version???V ROS 1.8.3
 * @Description:
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

    @DubboReference
    private IdAppService idAppService;

    /**
     * @param enter
     * @Description
     * @Author: alex
     * @Date: 2020/11/2 12:04 ??????
     * @Param: enter
     * @Return: Map
     * @desc: countByProductType
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
     * @param enter
     * @Description
     * @Author: alex
     * @Date: 2020/11/3 6:06 ??????
     * @Param: enter
     * @Return: ConsignOrderListResult
     * @desc: ???????????????
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
     * @param enter
     * @Description
     * @Author: alex
     * @Date: 2020/11/3 7:24 ??????
     * @Param: enter
     * @Return: ConsignDetailResult
     * @desc: ????????????
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
     * @param enter
     * @Description
     * @Author: alex
     * @Date: 2020/11/5 2:01 ??????
     * @Param: enter
     * @Return: detailProductList
     * @desc: ??????????????????
     */
    @Override
    public List<ConsignProductSerialResult> detailProductSerialList(IdEnter enter) {
        OpeEntrustOrder opeEntrustOrder = opeEntrustOrderService.getById(enter.getId());
        if (opeEntrustOrder == null) {
            throw new SesMobileRpsException(ExceptionCodeEnums.ORDER_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_IS_NOT_EXIST.getMessage());
        }

        List<ConsignProductSerialResult> resultList = new ArrayList<>();
        switch (opeEntrustOrder.getEntrustType()) {
            case 1:
                resultList = consignOrderServiceMapper.detailProductScooterList(enter.getId());
                if (CollectionUtils.isEmpty(resultList)) {
                    throw new SesMobileRpsException(ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getCode());
                }
                break;
            case 2:
                resultList = consignOrderServiceMapper.detailProductCombinList(enter.getId());
                break;
            default:
                resultList = consignOrderServiceMapper.detailProductPartList(enter.getId());
                break;
        }
        return resultList;
    }

    /**
     * @param enter
     * @Description
     * @Author: alex
     * @Date: 2020/11/3 7:56 ??????
     * @Param: enter
     * @Return: BooleanResult
     * @desc: ???????????????
     */
    @Override
    public BooleanResult checkSerial(StringEnter enter) {
        return null;
    }

    /**
     * @param enter
     * @Description
     * @Author: alex
     * @Date: 2020/11/3 7:34 ??????
     * @Param: alex
     * @Return: GeneralResult
     * @desc: ??????
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult ship(ConsignShipEnter enter) {

        List<ConsignShipProductEnter> consignShipProductEnters = new ArrayList<>();
        try {
            consignShipProductEnters = JSON.parseArray(enter.getSt(), ConsignShipProductEnter.class);
        } catch (Exception e) {
            throw new SesMobileRpsException(ExceptionCodeEnums.ILLEGAL_DATA.getCode(), ExceptionCodeEnums.ILLEGAL_DATA.getMessage());
        }
        if (CollectionUtils.isEmpty(consignShipProductEnters)) {
            throw new SesMobileRpsException(ExceptionCodeEnums.ILLEGAL_DATA.getCode(), ExceptionCodeEnums.ILLEGAL_DATA.getMessage());
        }

        //??????
        OpeEntrustOrder opeEntrustOrder = opeEntrustOrderService.getById(enter.getId());
        if (opeEntrustOrder == null) {
            throw new SesMobileRpsException(ExceptionCodeEnums.ORDER_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_IS_NOT_EXIST.getMessage());
        }

        //?????? ??????????????? ???????????????
        QueryWrapper<OpeInvoiceProductSerialNum> operatorInvoiceProductSerialNumQuery = new QueryWrapper<>();
        operatorInvoiceProductSerialNumQuery.in(OpeInvoiceProductSerialNum.COL_RELATION_ID, consignShipProductEnters.stream().map(ConsignShipProductEnter::getId).collect(Collectors.toSet()));
        operatorInvoiceProductSerialNumQuery.gt(OpeInvoiceProductSerialNum.COL_QTY, 0);
        List<OpeInvoiceProductSerialNum> orderQcItemList = opeInvoiceProductSerialNumService.list(operatorInvoiceProductSerialNumQuery);
        if (CollectionUtils.isEmpty(orderQcItemList)) {
            throw new SesMobileRpsException(ExceptionCodeEnums.QC_INFO_IS_EMPTY.getCode(), ExceptionCodeEnums.QC_INFO_IS_EMPTY.getMessage());
        }

        Map<Long, List<OpeInvoiceProductSerialNum>> serialMap = new HashMap<>();
        orderQcItemList.forEach(item -> {
            if (serialMap.containsKey(item.getRelationId())) {
                List<OpeInvoiceProductSerialNum> opeInvoiceProductSerialNumList = serialMap.get(item.getRelationId());
                opeInvoiceProductSerialNumList.add(item);
                serialMap.put(item.getRelationId(), opeInvoiceProductSerialNumList);
            }
        });

        //?????????????????????
        List<OpeEntrustProductSerialNum> saveOpeEntrustProductSerialNumList = new ArrayList<>();

        //???????????????
        for (ConsignShipProductEnter item : consignShipProductEnters) {
            Boolean checkSerial = Boolean.TRUE;
            for (Map.Entry<Long, List<OpeInvoiceProductSerialNum>> entry : serialMap.entrySet()) {
                Long key = entry.getKey();
                List<OpeInvoiceProductSerialNum> value = entry.getValue();
                if (item.getIdclass()) {
                    //??????????????? ??????????????????
                    List<String> serialList = value.stream().map(OpeInvoiceProductSerialNum::getSerialNum).collect(Collectors.toList());
                    if (!serialList.contains(item.getSerialN())) {
                        checkSerial = Boolean.FALSE;
                        break;
                    } else {
                        OpeEntrustProductSerialNum opeEntrustProductSerialNum = buildOpeEntrustProductSerialNum(enter, item, value);
                        saveOpeEntrustProductSerialNumList.add(opeEntrustProductSerialNum);
                    }
                } else {
                    //??????????????? ?????????????????? ????????? ??????
                    List<Integer> qtyList = value.stream().map(OpeInvoiceProductSerialNum::getQty).collect(Collectors.toList());
                    if (!qtyList.contains(item.getQty())) {
                        checkSerial = Boolean.FALSE;
                        break;
                    } else {
                        OpeEntrustProductSerialNum opeEntrustProductSerialNum = buildOpeEntrustProductSerialNum(enter, item, value);
                        saveOpeEntrustProductSerialNumList.add(opeEntrustProductSerialNum);
                    }
                }

            }
            if (!checkSerial) {
                throw new SesMobileRpsException(ExceptionCodeEnums.ILLEGAL_DATA.getCode(), ExceptionCodeEnums.ILLEGAL_DATA.getMessage());
            }
        }

        if (CollectionUtils.isNotEmpty(saveOpeEntrustProductSerialNumList)) {
            opeEntrustProductSerialNumService.saveBatch(saveOpeEntrustProductSerialNumList);
        }

        //?????????????????????
        UpdateConsignStatusEnter updateConsignStatusEnter = new UpdateConsignStatusEnter(opeEntrustOrder.getId(), ConsignOrderStatusEnums.BE_SIGNED.getValue(), OrderOperationTypeEnums.SHIPMENT.getValue());
        updateConsignStatusEnter.setUserId(enter.getUserId());
        updateStatus(updateConsignStatusEnter);

        //???????????????
        SaveLogisticsOrderEnter saveLogisticsOrderEnter = new SaveLogisticsOrderEnter(opeEntrustOrder.getId(), enter.getShipmentN(), enter.getLogisticsCompany(), opeEntrustOrder.getRemark());
        saveLogisticsOrderEnter.setUserId(enter.getUserId());
        saveLogisticsOrder(saveLogisticsOrderEnter);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * @param enter
     * @Description
     * @Author: alex
     * @Date: 2020/11/6 2:32 ??????
     * @Param: enter
     * @Return: GeneralResult
     * @desc: ????????????
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult updateStatus(UpdateConsignStatusEnter enter) {
        OpeEntrustOrder opeEntrustOrder = opeEntrustOrderService.getById(enter.getId());
        if (opeEntrustOrder == null) {
            throw new SesMobileRpsException(ExceptionCodeEnums.ORDER_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_IS_NOT_EXIST.getMessage());
        }
        //?????????????????????
        opeEntrustOrder.setEntrustStatus(enter.getStatus());
        opeEntrustOrder.setCreatedBy(enter.getId());
        opeEntrustOrder.setCreatedTime(new Date());
        opeEntrustOrderService.updateById(opeEntrustOrder);

        //????????????
        SaveOpTraceEnter saveOpTraceEnter = new SaveOpTraceEnter(null, opeEntrustOrder.getId(), OrderTypeEnums.ORDER.getValue(), enter.getOperatingDynamics(),
                opeEntrustOrder.getRemark());
        saveOpTraceEnter.setUserId(enter.getUserId());
        productionOrderTraceService.save(saveOpTraceEnter);

        //????????????
        OrderStatusFlowEnter orderStatusFlowEnter = new OrderStatusFlowEnter(null, opeEntrustOrder.getEntrustStatus(), OrderTypeEnums.ORDER.getValue(), opeEntrustOrder.getId(),
                opeEntrustOrder.getRemark());
        orderStatusFlowEnter.setUserId(enter.getUserId());
        orderStatusFlowService.save(orderStatusFlowEnter);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * @param enter
     * @Description
     * @Author: alex
     * @Date: 2020/11/6 2:33 ??????
     * @Param: enter
     * @Return: GeneralResult
     * @desc: ?????????????????????
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult saveLogisticsOrder(SaveLogisticsOrderEnter enter) {
        OpeLogisticsOrder opeLogisticsOrder = new OpeLogisticsOrder();
        BeanUtils.copyProperties(enter, opeLogisticsOrder);
        opeLogisticsOrder.setId(idAppService.getId(SequenceName.OPE_LOGISTICS_ORDER));
        opeLogisticsOrder.setDr(0);
        opeLogisticsOrder.setCreatedBy(enter.getUserId());
        opeLogisticsOrder.setCreatedTime(new Date());
        opeLogisticsOrder.setUpdatedBy(enter.getUserId());
        opeLogisticsOrder.setUpdatedTime(new Date());
        opeLogisticsOrderService.save(opeLogisticsOrder);

//        //????????????
//        SaveOpTraceEnter saveOpTraceEnter = new SaveOpTraceEnter(null, opeLogisticsOrder.getId(), OrderTypeEnums..getValue(), OrderOperationTypeEnums.CREATE.getValue(),
//                opeLogisticsOrder.getRemark());
//        saveOpTraceEnter.setUserId(enter.getUserId());
//        productionOrderTraceService.save(saveOpTraceEnter);
//
//        //????????????
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
        opeEntrustProductSerialNum.setIdClass(item.getIdclass() ? 1 : 0);
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
