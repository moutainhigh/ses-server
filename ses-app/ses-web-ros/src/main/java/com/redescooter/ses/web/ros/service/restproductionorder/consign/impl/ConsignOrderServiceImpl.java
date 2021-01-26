package com.redescooter.ses.web.ros.service.restproductionorder.consign.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.redescooter.ses.api.common.enums.base.AppIDEnums;
import com.redescooter.ses.api.common.enums.base.SystemIDEnums;
import com.redescooter.ses.api.common.enums.proxy.mail.MailTemplateEventEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.OrderOperationTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.OrderTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.ProductTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.consign.ConsignOrderStatusEnums;
import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.BaseMailTaskEnter;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.foundation.service.MailMultiTaskService;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.dao.base.OpeEntrustCombinBMapper;
import com.redescooter.ses.web.ros.dao.base.OpeEntrustPartsBMapper;
import com.redescooter.ses.web.ros.dao.base.OpeEntrustScooterBMapper;
import com.redescooter.ses.web.ros.dao.base.OpeWmsCombinStockMapper;
import com.redescooter.ses.web.ros.dao.base.OpeWmsPartsStockMapper;
import com.redescooter.ses.web.ros.dao.base.OpeWmsScooterStockMapper;
import com.redescooter.ses.web.ros.dao.restproductionorder.ConsignOrderServiceMapper;
import com.redescooter.ses.web.ros.dm.OpeEntrustCombinB;
import com.redescooter.ses.web.ros.dm.OpeEntrustOrder;
import com.redescooter.ses.web.ros.dm.OpeEntrustPartsB;
import com.redescooter.ses.web.ros.dm.OpeEntrustScooterB;
import com.redescooter.ses.web.ros.dm.OpeInvoiceCombinB;
import com.redescooter.ses.web.ros.dm.OpeInvoiceOrder;
import com.redescooter.ses.web.ros.dm.OpeInvoicePartsB;
import com.redescooter.ses.web.ros.dm.OpeInvoiceScooterB;
import com.redescooter.ses.web.ros.dm.OpeLogisticsOrder;
import com.redescooter.ses.web.ros.dm.OpePurchaseOrder;
import com.redescooter.ses.web.ros.dm.OpeSysStaff;
import com.redescooter.ses.web.ros.dm.OpeWmsCombinStock;
import com.redescooter.ses.web.ros.dm.OpeWmsPartsStock;
import com.redescooter.ses.web.ros.dm.OpeWmsScooterStock;
import com.redescooter.ses.web.ros.dm.OpeWmsStockSerialNumber;
import com.redescooter.ses.web.ros.enums.distributor.DelStatusEnum;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpeEntrustCombinBService;
import com.redescooter.ses.web.ros.service.base.OpeEntrustOrderService;
import com.redescooter.ses.web.ros.service.base.OpeEntrustPartsBService;
import com.redescooter.ses.web.ros.service.base.OpeEntrustScooterBService;
import com.redescooter.ses.web.ros.service.base.OpeInvoiceCombinBService;
import com.redescooter.ses.web.ros.service.base.OpeInvoiceOrderService;
import com.redescooter.ses.web.ros.service.base.OpeInvoicePartsBService;
import com.redescooter.ses.web.ros.service.base.OpeInvoiceScooterBService;
import com.redescooter.ses.web.ros.service.base.OpeLogisticsOrderService;
import com.redescooter.ses.web.ros.service.base.OpePurchaseOrderService;
import com.redescooter.ses.web.ros.service.base.OpeSysStaffService;
import com.redescooter.ses.web.ros.service.base.OpeWmsStockSerialNumberService;
import com.redescooter.ses.web.ros.service.restproductionorder.allocateorder.AllocateOrderService;
import com.redescooter.ses.web.ros.service.restproductionorder.consign.ConsignOrderService;
import com.redescooter.ses.web.ros.service.restproductionorder.invoice.InvoiceOrderService;
import com.redescooter.ses.web.ros.service.restproductionorder.number.OrderNumberService;
import com.redescooter.ses.web.ros.service.restproductionorder.orderflow.OrderStatusFlowService;
import com.redescooter.ses.web.ros.service.restproductionorder.purchaseorder.PurchaseOrderService;
import com.redescooter.ses.web.ros.service.restproductionorder.trace.ProductionOrderTraceService;
import com.redescooter.ses.web.ros.service.wms.cn.china.WmsMaterialStockService;
import com.redescooter.ses.web.ros.vo.restproductionorder.AssociatedOrderResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.OrderProductDetailResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.consignorder.ConsignOrderDetailResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.consignorder.ConsignOrderListEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.consignorder.ConsignOrderListResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.consignorder.SaveConsignEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.number.OrderNumberEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.optrace.ListByBussIdEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.optrace.OpTraceResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.optrace.SaveOpTraceEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.orderflow.OrderStatusFlowEnter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private ProductionOrderTraceService productionOrderTraceService;

    @Autowired
    private OpeEntrustOrderService opeEntrustOrderService;

    @Autowired
    private OpeInvoiceOrderService opeInvoiceOrderService;

    @Autowired
    private InvoiceOrderService invoiceOrderService;

    @Autowired
    private OrderStatusFlowService orderStatusFlowService;

    @Autowired
    private OrderNumberService orderNumberService;

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @Autowired
    private AllocateOrderService allocateOrderService;

    @Autowired
    private OpePurchaseOrderService opePurchaseOrderService;

    @Autowired
    private OpeInvoiceScooterBService opeInvoiceScooterBService;

    @Autowired
    private OpeInvoiceCombinBService opeInvoiceCombinBService;

    @Autowired
    private OpeInvoicePartsBService opeInvoicePartsBService;

    @Autowired
    private OpeEntrustScooterBService opeEntrustScooterBService;

    @Autowired
    private OpeEntrustCombinBService opeEntrustCombinBService;

    @Autowired
    private OpeEntrustPartsBService opeEntrustPartsBService;

    @Autowired
    private OpeLogisticsOrderService opeLogisticsOrderService;

    @Autowired
    private OpeSysStaffService opeSysStaffService;

    @Reference
    private MailMultiTaskService mailMultiTaskService;

    @Autowired
    private WmsMaterialStockService wmsMaterialStockService;

    @Autowired
    private OpeEntrustScooterBMapper opeEntrustScooterBMapper;

    @Autowired
    private OpeEntrustCombinBMapper opeEntrustCombinBMapper;

    @Autowired
    private OpeEntrustPartsBMapper opeEntrustPartsBMapper;

    @Autowired
    private OpeWmsStockSerialNumberService opeWmsStockSerialNumberService;

    @Autowired
    private OpeWmsScooterStockMapper opeWmsScooterStockMapper;

    @Autowired
    private OpeWmsCombinStockMapper opeWmsCombinStockMapper;

    @Autowired
    private OpeWmsPartsStockMapper opeWmsPartsStockMapper;

    @Reference
    private IdAppService idAppService;

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/22 13:29
     * @Param: enter
     * @Return: Map
     * @desc: 出库单产品类型统计
     * @param enter
     */
    @Override
    public Map<Integer, Integer> countByType(GeneralEnter enter) {
        Map<Integer, Integer> result = new HashMap<>();
        List<CountByStatusResult> mapList = consignOrderServiceMapper.countByType(enter);
        result = mapList.stream().collect(Collectors.toMap(item -> {
            return Integer.valueOf(item.getStatus());
        }, CountByStatusResult::getTotalCount));
        for (ProductTypeEnums item : ProductTypeEnums.values()) {
            if (!result.containsKey(item.getValue())) {
                result.put(item.getValue(), 0);
            }
        }
        return result;
    }

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/22 13:42
     * @Param: enter
     * @Return: Map
     * @desc: 状态列表
     * @param enter
     */
    @Override
    public  Map<Integer, String> statusList(GeneralEnter enter) {
        Map<Integer, String> result = new HashMap<>();
        for (ConsignOrderStatusEnums item : ConsignOrderStatusEnums.values()) {
            result.put(item.getValue(),item.getMessage());
        }
        return result;
    }

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/22 13:59
     * @Param: enter
     * @Return: ConsignOrderListResult
     * @desc: 出库单列表
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
     * @Date: 2020/10/22 14:02
     * @Param: enter
     * @Return: ConsignOrderDetailResult
     * @desc: 详情
     * @param enter
     */
    @Override
    public ConsignOrderDetailResult detail(IdEnter enter) {
        ConsignOrderDetailResult detail = consignOrderServiceMapper.detail(enter);
        //产品详情
        List<OrderProductDetailResult> orderProductDetailResultList = invoiceOrderService.productListById(new IdEnter(detail.getInvoiceId()));
        detail.setProductList(CollectionUtils.isEmpty(orderProductDetailResultList) ? new ArrayList<>() : orderProductDetailResultList);
        //关联订单
        List<AssociatedOrderResult> associatedOrderResultList = this.associatedOrderList(enter);
        detail.setAssociatedOrderList(CollectionUtils.isEmpty(associatedOrderResultList) ? new ArrayList<>() : associatedOrderResultList);
        //操作动态
        List<OpTraceResult> opTraceResultList = productionOrderTraceService.listByBussId(new ListByBussIdEnter(enter.getId(), OrderTypeEnums.ORDER.getValue()));
        detail.setOrderOperatingList(CollectionUtils.isEmpty(opTraceResultList) ? new ArrayList<>() : opTraceResultList);
        return detail;
    }

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/29 13:46
     * @Param: enter
     * @Return: AssociatedOrderResult
     * @desc: 关联订单
     * @param enter
     */
    @Override
    public List<AssociatedOrderResult> associatedOrderList(IdEnter enter) {
        List<AssociatedOrderResult> result=new ArrayList<>();
        OpeEntrustOrder opeEntrustOrder = opeEntrustOrderService.getById(enter.getId());
        if (opeEntrustOrder==null){
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(),ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        OpeInvoiceOrder opeInvoiceOrder = opeInvoiceOrderService.getById(opeEntrustOrder.getInvoiceId());
        if (opeInvoiceOrder != null){
            result.add(new AssociatedOrderResult(opeInvoiceOrder.getId(),opeInvoiceOrder.getInvoiceNo(),OrderTypeEnums.INVOICE.getValue(),opeInvoiceOrder.getCreatedTime(),""));
        }
        QueryWrapper<OpeLogisticsOrder>  qw = new QueryWrapper<>();
        qw.eq(OpeLogisticsOrder.COL_ENTRUST_ID,opeEntrustOrder.getId());
        qw.last("limit 1");
        OpeLogisticsOrder opeLogisticsOrder = opeLogisticsOrderService.getOne(qw);
        if (opeLogisticsOrder != null){
            result.add(new AssociatedOrderResult(opeLogisticsOrder.getId(),opeLogisticsOrder.getLogisticsNo(),OrderTypeEnums.LOGISTICS.getValue(),opeLogisticsOrder.getCreatedTime(),opeLogisticsOrder.getLogisticsCompany()));
        }
        return result;
    }

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/22 14:04
     * @Param: enter
     * @Return: GeneralResult
     * @desc: 委托单签收
     * @param enter
     */
    @Transactional
    @Override
    public GeneralResult signFor(IdEnter enter) {
        OpeEntrustOrder opeEntrustOrder = opeEntrustOrderService.getById(enter.getId());
        if (opeEntrustOrder == null) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }

        OpeInvoiceOrder opeInvoiceOrder = opeInvoiceOrderService.getById(opeEntrustOrder.getInvoiceId());
        if (opeInvoiceOrder == null) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }

        OpePurchaseOrder opePurchaseOrder = opePurchaseOrderService.getById(opeInvoiceOrder.getPurchaseId());
        if (opePurchaseOrder == null) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }

        opeEntrustOrder.setEntrustStatus(ConsignOrderStatusEnums.RECEIVED.getValue());
        opeEntrustOrder.setUpdatedTime(new Date());
        opeEntrustOrder.setUpdatedBy(enter.getId());
        opeEntrustOrderService.updateById(opeEntrustOrder);

        //操作动态
        SaveOpTraceEnter saveOpTraceEnter = new SaveOpTraceEnter(null, opeEntrustOrder.getId(), OrderTypeEnums.ORDER.getValue(), OrderOperationTypeEnums.SIGN_FOR.getValue(),
                opeEntrustOrder.getRemark());
        saveOpTraceEnter.setUserId(enter.getUserId());
        productionOrderTraceService.save(saveOpTraceEnter);

        //订单节点
        OrderStatusFlowEnter orderStatusFlowEnter = new OrderStatusFlowEnter(null, opeEntrustOrder.getEntrustStatus(), OrderTypeEnums.ORDER.getValue(), opeEntrustOrder.getId(),
                opeEntrustOrder.getRemark());
        orderStatusFlowEnter.setUserId(enter.getUserId());
        orderStatusFlowService.save(orderStatusFlowEnter);

        //发货单签收
        IdEnter idEnter = new IdEnter(opeEntrustOrder.getInvoiceId());
        idEnter.setUserId(enter.getUserId());
        invoiceOrderService.signFor(idEnter);
        // 委托单签收的时候  给收货人、发货人发邮件
        try {
            // 发邮件不能影响主流程，且发邮件的方法必须是异步的
            entrustSignToEmail(opeEntrustOrder,enter.getRequestId());
            // 委托单签收时 对于法国仓库时入库操作
//            wmsMaterialStockService.inStock(opeEntrustOrder.getEntrustType(),opeEntrustOrder.getId(),2,enter.getUserId(),1);
            wmsMaterialStockService.frInStock(opeEntrustOrder.getEntrustType(),opeEntrustOrder.getId(),2,enter.getUserId(),1);
        }catch (Exception e){

        }

        /**
         * 签收时,新增库存产品序列号表
         */
        // 根据委托单类型查询子表
        Integer entrustType = opeEntrustOrder.getEntrustType();
        Long id = null;
        String rsn = null;
        Integer relationType = null;
        if (ProductTypeEnums.SCOOTER.getValue().equals(entrustType)) {
            LambdaQueryWrapper<OpeEntrustScooterB> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(OpeEntrustScooterB::getDr, DelStatusEnum.VALID.getCode());
            wrapper.eq(OpeEntrustScooterB::getEntrustId, opeEntrustOrder.getId());
            wrapper.orderByDesc(OpeEntrustScooterB::getCreatedTime);
            List<OpeEntrustScooterB> list = opeEntrustScooterBMapper.selectList(wrapper);
            if (CollectionUtils.isNotEmpty(list)) {
                OpeEntrustScooterB model = list.get(0);
                rsn = model.getDef1();
                relationType = ProductTypeEnums.SCOOTER.getValue();

                // 获得成品库车辆库存表id
                Long groupId = model.getGroupId();
                Long colorId = model.getColorId();
                LambdaQueryWrapper<OpeWmsScooterStock> qw = new LambdaQueryWrapper<>();
                qw.eq(OpeWmsScooterStock::getDr, DelStatusEnum.VALID.getCode());
                qw.eq(OpeWmsScooterStock::getGroupId, groupId);
                qw.eq(OpeWmsScooterStock::getColorId, colorId);
                qw.eq(OpeWmsScooterStock::getStockType, 2);
                qw.orderByDesc(OpeWmsScooterStock::getCreatedTime);
                List<OpeWmsScooterStock> stockList = opeWmsScooterStockMapper.selectList(qw);
                if (CollectionUtils.isNotEmpty(stockList)) {
                    OpeWmsScooterStock stock = stockList.get(0);
                    if (null != stock) {
                        id = stock.getId();
                    }
                }
            }
        } else if (ProductTypeEnums.COMBINATION.getValue().equals(entrustType)) {
            LambdaQueryWrapper<OpeEntrustCombinB> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(OpeEntrustCombinB::getDr, DelStatusEnum.VALID.getCode());
            wrapper.eq(OpeEntrustCombinB::getEntrustId, opeEntrustOrder.getId());
            wrapper.orderByDesc(OpeEntrustCombinB::getCreatedTime);
            List<OpeEntrustCombinB> list = opeEntrustCombinBMapper.selectList(wrapper);
            if (CollectionUtils.isNotEmpty(list)) {
                OpeEntrustCombinB model = list.get(0);
                rsn = model.getDef1();
                relationType = ProductTypeEnums.COMBINATION.getValue();

                // 获得成品库组装件库存表id
                Long bomId = model.getProductionCombinBomId();
                LambdaQueryWrapper<OpeWmsCombinStock> qw = new LambdaQueryWrapper<>();
                qw.eq(OpeWmsCombinStock::getDr, DelStatusEnum.VALID.getCode());
                qw.eq(OpeWmsCombinStock::getProductionCombinBomId, bomId);
                qw.eq(OpeWmsCombinStock::getStockType, 2);
                qw.orderByDesc(OpeWmsCombinStock::getCreatedTime);
                List<OpeWmsCombinStock> stockList = opeWmsCombinStockMapper.selectList(qw);
                if (CollectionUtils.isNotEmpty(stockList)) {
                    OpeWmsCombinStock stock = stockList.get(0);
                    if (null != stock) {
                        id = stock.getId();
                    }
                }
            }
        } else if (ProductTypeEnums.PARTS.getValue().equals(entrustType)) {
            LambdaQueryWrapper<OpeEntrustPartsB> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(OpeEntrustPartsB::getDr, DelStatusEnum.VALID.getCode());
            wrapper.eq(OpeEntrustPartsB::getEntrustId, opeEntrustOrder.getId());
            wrapper.orderByDesc(OpeEntrustPartsB::getCreatedTime);
            List<OpeEntrustPartsB> list = opeEntrustPartsBMapper.selectList(wrapper);
            if (CollectionUtils.isNotEmpty(list)) {
                OpeEntrustPartsB model = list.get(0);
                rsn = model.getDef1();
                relationType = ProductTypeEnums.PARTS.getValue();

                // 获得原料库部件库存表id
                Long partsId = model.getPartsId();
                LambdaQueryWrapper<OpeWmsPartsStock> qw = new LambdaQueryWrapper<>();
                qw.eq(OpeWmsPartsStock::getDr, DelStatusEnum.VALID.getCode());
                qw.eq(OpeWmsPartsStock::getPartsId, partsId);
                qw.eq(OpeWmsPartsStock::getStockType, 2);
                qw.orderByDesc(OpeWmsPartsStock::getCreatedTime);
                List<OpeWmsPartsStock> stockList = opeWmsPartsStockMapper.selectList(qw);
                if (CollectionUtils.isNotEmpty(stockList)) {
                    OpeWmsPartsStock stock = stockList.get(0);
                    if (null != stock) {
                        id = stock.getId();
                    }
                }
            }
        }

        // 新增库存产品序列号表
        List<OpeWmsStockSerialNumber> saveList = Lists.newArrayList();
        String[] split = rsn.split(",");
        for (String s : split) {
            OpeWmsStockSerialNumber param = new OpeWmsStockSerialNumber();
            param.setId(idAppService.getId(SequenceName.OPE_WMS_STOCK_SERIAL_NUMBER));
            param.setDr(DelStatusEnum.VALID.getCode());
            param.setTenantId(enter.getTenantId());
            param.setDeptId(enter.getOpeDeptId());
            param.setRelationType(relationType);
            param.setRelationId(id);
            param.setStockType(1);
            param.setRsn(s);
            param.setStockStatus(1);
            param.setCreatedBy(enter.getUserId());
            param.setCreatedTime(new Date());
            saveList.add(param);
        }
        opeWmsStockSerialNumberService.saveBatch(saveList);
        return new GeneralResult(enter.getRequestId());
    }


    // 委托单签收的时候  给收货人、发货人各发一封邮件
    @Async
    public void entrustSignToEmail(OpeEntrustOrder entrustOrder,String requestId){
        // 给发货人发
        // 找到发货人
        OpeSysStaff consignorUser = opeSysStaffService.getById(entrustOrder.getConsignorUser());
        if (consignorUser != null){
            BaseMailTaskEnter consignorEnter = new BaseMailTaskEnter();
            consignorEnter.setName(consignorUser.getFullName());
            consignorEnter.setEvent(MailTemplateEventEnums.ENTRUST_SIGN_TO_CONSIGNOR.getEvent());
            consignorEnter.setSystemId(SystemIDEnums.REDE_SES.getSystemId());
            consignorEnter.setAppId(AppIDEnums.SES_ROS.getValue());
            consignorEnter.setEmail(entrustOrder.getConsignorMail());
            consignorEnter.setRequestId(requestId);
            consignorEnter.setUserId(consignorUser.getId());
            consignorEnter.setEntrustNo(entrustOrder.getEntrustNo());
            mailMultiTaskService.addCreateEmployeeMailTask(consignorEnter);
        }

        // 再给收货人发
        // 找到收货人
        OpeSysStaff consigneeUser = opeSysStaffService.getById(entrustOrder.getConsignorUser());
        if (consigneeUser != null){
            BaseMailTaskEnter consigneeEnter = new BaseMailTaskEnter();
            consigneeEnter.setName(consigneeUser.getFullName());
            consigneeEnter.setEvent(MailTemplateEventEnums.ENTRUST_SIGN_TO_CONSIGNEE.getEvent());
            consigneeEnter.setSystemId(SystemIDEnums.REDE_SES.getSystemId());
            consigneeEnter.setAppId(AppIDEnums.SES_ROS.getValue());
            consigneeEnter.setEmail(entrustOrder.getConsigneeUserMail());
            consigneeEnter.setRequestId(requestId);
            consigneeEnter.setUserId(consigneeUser.getId());
            consigneeEnter.setEntrustNo(entrustOrder.getEntrustNo());
            mailMultiTaskService.addCreateEmployeeMailTask(consigneeEnter);
        }
    }

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/26 16:54
     * @Param: enter
     * @Return: GeneralResult
     * @desc: 保存出库单
     * @param enter
     */
    @Transactional
    @Override
    public GeneralResult save(SaveConsignEnter enter) {
        OpeEntrustOrder opeEntrustOrder = new OpeEntrustOrder();
        OpeInvoiceOrder opeInvoiceOrder = opeInvoiceOrderService.getById(enter.getInvoiceId());
        if (opeInvoiceOrder == null) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        BeanUtils.copyProperties(enter, opeEntrustOrder);
        SaveOpTraceEnter saveOpTraceEnter = null;
        if (enter.getInvoiceId() == null || null == enter.getId() || 0 == enter.getId())  {
            opeEntrustOrder.setId(idAppService.getId(SequenceName.OPE_ENTRUST_ORDER));
            opeEntrustOrder.setDr(0);
            opeEntrustOrder.setEntrustNo(orderNumberService.orderNumber(new OrderNumberEnter(OrderTypeEnums.ORDER.getValue())).getValue());
            opeEntrustOrder.setEntrustStatus(ConsignOrderStatusEnums.BE_DELIVERED.getValue());
            opeEntrustOrder.setCreatedBy(enter.getUserId());
            opeEntrustOrder.setCreatedTime(new Date());

            //操作动态
            saveOpTraceEnter = new SaveOpTraceEnter(null, opeEntrustOrder.getId(), OrderTypeEnums.ORDER.getValue(), OrderOperationTypeEnums.CREATE.getValue(),
                    opeEntrustOrder.getRemark());
            //订单节点
            OrderStatusFlowEnter orderStatusFlowEnter = new OrderStatusFlowEnter(null, opeEntrustOrder.getEntrustStatus(), OrderTypeEnums.ORDER.getValue(), opeEntrustOrder.getId(),
                    opeInvoiceOrder.getRemark());
            orderStatusFlowEnter.setUserId(enter.getUserId());
            orderStatusFlowService.save(orderStatusFlowEnter);
        } else {
            //操作动态
            saveOpTraceEnter = new SaveOpTraceEnter(null, opeEntrustOrder.getId(), OrderTypeEnums.ORDER.getValue(), OrderOperationTypeEnums.EDIT.getValue(),
                    opeEntrustOrder.getRemark());
        }
        //操作动态
        saveOpTraceEnter.setUserId(enter.getUserId());
        productionOrderTraceService.save(saveOpTraceEnter);

        opeEntrustOrder.setUpdatedBy(enter.getUserId());
        opeEntrustOrder.setUpdatedTime(new Date());
        opeEntrustOrderService.saveOrUpdate(opeEntrustOrder);
        // 处理委托单的子表
        entrustBs(enter, opeEntrustOrder);
        return new GeneralResult(enter.getRequestId());
    }


    private void entrustBs(SaveConsignEnter enter, OpeEntrustOrder opeEntrustOrder) {
        switch (opeEntrustOrder.getEntrustType()){
            case 1:
                // scooter
                QueryWrapper<OpeInvoiceScooterB> scooterBQueryWrapper = new QueryWrapper<>();
                scooterBQueryWrapper.eq(OpeInvoiceScooterB.COL_INVOICE_ID,opeEntrustOrder.getInvoiceId());
                List<OpeInvoiceScooterB> scooterBS = opeInvoiceScooterBService.list(scooterBQueryWrapper);
                if (CollectionUtils.isNotEmpty(scooterBS)){
                    List<OpeEntrustScooterB> scooterBList = new ArrayList<>();
                    for (OpeInvoiceScooterB scooterB : scooterBS) {
                        OpeEntrustScooterB entrustScooterB = new OpeEntrustScooterB();
                        BeanUtils.copyProperties(scooterB,entrustScooterB);
                        entrustScooterB.setEntrustId(opeEntrustOrder.getId());
                        entrustScooterB.setCreatedBy(enter.getUserId());
                        entrustScooterB.setUpdatedBy(enter.getUserId());
                        entrustScooterB.setCreatedTime(new Date());
                        entrustScooterB.setUpdatedTime(new Date());
                        entrustScooterB.setId(idAppService.getId(SequenceName.OPE_ENTRUST_SCOOTER_B));
                        scooterBList.add(entrustScooterB);
                    }
                    opeEntrustScooterBService.saveOrUpdateBatch(scooterBList);
                }
            default:
                    break;
            case 2:
                // combin
                QueryWrapper<OpeInvoiceCombinB> combinBQueryWrapper = new QueryWrapper<>();
                combinBQueryWrapper.eq(OpeInvoiceCombinB.COL_INVOICE_ID,opeEntrustOrder.getInvoiceId());
                List<OpeInvoiceCombinB> combinBS = opeInvoiceCombinBService.list(combinBQueryWrapper);
                if (CollectionUtils.isNotEmpty(combinBS)){
                    List<OpeEntrustCombinB> combinBList = new ArrayList<>();
                    for (OpeInvoiceCombinB combinB : combinBS) {
                        OpeEntrustCombinB entrustCombinB = new OpeEntrustCombinB();
                        BeanUtils.copyProperties(combinB,entrustCombinB);
                        entrustCombinB.setEntrustId(opeEntrustOrder.getId());
                        entrustCombinB.setId(idAppService.getId(SequenceName.OPE_ENTRUST_COMBIN_B));
                        entrustCombinB.setCreatedBy(enter.getUserId());
                        entrustCombinB.setCreatedTime(new Date());
                        entrustCombinB.setUpdatedBy(enter.getUserId());
                        entrustCombinB.setUpdatedTime(new Date());
                        combinBList.add(entrustCombinB);
                    }
                    opeEntrustCombinBService.saveOrUpdateBatch(combinBList);
                }
                break;
            case 3:
                // parts
                QueryWrapper<OpeInvoicePartsB> partsBQueryWrapper = new QueryWrapper<>();
                partsBQueryWrapper.eq(OpeInvoicePartsB.COL_INVOICE_ID,opeEntrustOrder.getInvoiceId());
                List<OpeInvoicePartsB> partsBS = opeInvoicePartsBService.list(partsBQueryWrapper);
                if (CollectionUtils.isNotEmpty(partsBS)){
                    List<OpeEntrustPartsB> partsBList = new ArrayList<>();
                    for (OpeInvoicePartsB partsB : partsBS) {
                        OpeEntrustPartsB entrustPartsB = new OpeEntrustPartsB();
                        BeanUtils.copyProperties(partsB,entrustPartsB);
                        entrustPartsB.setEntrustId(opeEntrustOrder.getId());
                        entrustPartsB.setId(idAppService.getId(SequenceName.OPE_ENTRUST_PARTS_B));
                        entrustPartsB.setCreatedBy(enter.getUserId());
                        entrustPartsB.setCreatedTime(new Date());
                        entrustPartsB.setUpdatedBy(enter.getUserId());
                        entrustPartsB.setUpdatedTime(new Date());
                        partsBList.add(entrustPartsB);
                    }
                    opeEntrustPartsBService.saveOrUpdateBatch(partsBList);
                }
                break;

        }
    }


    // 委托单状态变待签收
    @Override
    @Transactional
    public GeneralResult waitSign(IdEnter enter) {
        OpeEntrustOrder opeEntrustOrder = opeEntrustOrderService.getById(enter.getId());
        if (opeEntrustOrder == null) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        if (!opeEntrustOrder.getEntrustStatus().equals(ConsignOrderStatusEnums.BE_DELIVERED.getValue())){
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_STATUS_ERROR.getCode(), ExceptionCodeEnums.ORDER_STATUS_ERROR.getMessage());
        }
        opeEntrustOrder.setEntrustStatus(ConsignOrderStatusEnums.BE_SIGNED.getValue());
        opeEntrustOrderService.saveOrUpdate(opeEntrustOrder);
        //操作动态
        SaveOpTraceEnter saveOpTraceEnter = new SaveOpTraceEnter(null, opeEntrustOrder.getId(), OrderTypeEnums.ORDER.getValue(), OrderOperationTypeEnums.SHIPMENT.getValue(),
                opeEntrustOrder.getRemark());
        saveOpTraceEnter.setUserId(enter.getUserId());
        productionOrderTraceService.save(saveOpTraceEnter);

        //订单节点
        OrderStatusFlowEnter orderStatusFlowEnter = new OrderStatusFlowEnter(null, opeEntrustOrder.getEntrustStatus(), OrderTypeEnums.ORDER.getValue(), opeEntrustOrder.getId(),
                opeEntrustOrder.getRemark());
        orderStatusFlowEnter.setUserId(enter.getUserId());
        orderStatusFlowService.save(orderStatusFlowEnter);
        // 委托单对应的发货单状态变为待签收
        invoiceOrderService.invoiceWaitSign(opeEntrustOrder.getInvoiceId(),enter.getUserId());
        // 委托单点击发货的时候 给收货人发邮件
        try {
            // 发邮件不能影响主流程，且发邮件的方法必须是异步的
            entrustDeliveryToEmail(opeEntrustOrder,enter.getRequestId());
            try {
                // 委托单发货时  对于法国仓库来说是待入库
                //法国仓库 执行待入库操作
//                wmsMaterialStockService.waitInStock(opeEntrustOrder.getEntrustType(),opeEntrustOrder.getId(),2,enter.getUserId());
                wmsMaterialStockService.FrWaitInStock(opeEntrustOrder.getEntrustType(),opeEntrustOrder.getId(),2,enter.getUserId());
            }catch (Exception e) {

            }
        }catch (Exception e){}
        return new GeneralResult(enter.getRequestId());
    }


    // 委托单发货的时候 给收货人发邮件
    @Async
    public void entrustDeliveryToEmail(OpeEntrustOrder entrustOrder,String requestId){
        // 找到收货人
        OpeSysStaff consigneeUser = opeSysStaffService.getById(entrustOrder.getConsignorUser());
        if (consigneeUser != null){
            BaseMailTaskEnter consigneeEnter = new BaseMailTaskEnter();
            consigneeEnter.setName(consigneeUser.getFullName());
            consigneeEnter.setEvent(MailTemplateEventEnums.ENTRUST_DELIVERY_TO_CONSIGNEE.getEvent());
            consigneeEnter.setSystemId(SystemIDEnums.REDE_SES.getSystemId());
            consigneeEnter.setAppId(AppIDEnums.SES_ROS.getValue());
            consigneeEnter.setEmail(entrustOrder.getConsigneeUserMail());
            consigneeEnter.setRequestId(requestId);
            consigneeEnter.setUserId(consigneeUser.getId());
            consigneeEnter.setEntrustNo(entrustOrder.getEntrustNo());
            mailMultiTaskService.addCreateEmployeeMailTask(consigneeEnter);
        }
    }
}
