package com.redescooter.ses.web.ros.service.restproductionorder.assembly.impl;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.enums.bom.ProductionPartsRelationTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.OrderOperationTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.OrderTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.ProductTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.assembly.CombinOrderStatusEnums;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.dao.restproductionorder.ProductionAssemblyOrderServiceMapper;
import com.redescooter.ses.web.ros.dm.*;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.*;
import com.redescooter.ses.web.ros.service.restproductionorder.assembly.ProductionAssemblyOrderService;
import com.redescooter.ses.web.ros.service.restproductionorder.orderflow.OrderStatusFlowService;
import com.redescooter.ses.web.ros.service.restproductionorder.trace.ProductionOrderTraceService;
import com.redescooter.ses.web.ros.vo.production.allocate.SaveAssemblyProductEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.AssociatedOrderResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.assembly.*;
import com.redescooter.ses.web.ros.vo.restproductionorder.optrace.ListByBussIdEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.optrace.SaveOpTraceEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.orderflow.OrderStatusFlowEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.purchass.PurchasDetailProductListResult;
import org.apache.commons.collections.CollectionUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName:ProductionAssemblyOrderService
 * @description: ProductionAssemblyOrderService
 * @author: Alex
 * @Version：1.3
 * @create: 2020/11/12 11:53
 */
@Service
public class ProductionAssemblyOrderServiceImpl implements ProductionAssemblyOrderService {

    @Autowired
    private ProductionAssemblyOrderServiceMapper productionAssemblyOrderServiceMapper;

    @Autowired
    private OpeInWhouseOrderService opeInWhouseOrderService;

    @Autowired
    private ProductionOrderTraceService productionOrderTraceService;

    @Autowired
    private OpeCombinOrderScooterBService opeCombinOrderScooterBService;

    @Autowired
    private OpeCombinOrderService opeCombinOrderService;

    @Autowired
    private OpeCombinOrderCombinBService opeCombinOrderCombinBService;

    @Autowired
    private OpeProductionPartsRelationService opeProductionPartsRelationService;

    @Autowired
    private OpeProductionCombinBomService opeProductionCombinBomService;

    @Autowired
    private OrderStatusFlowService orderStatusFlowService;

    @Autowired
    private IdAppService idAppService;

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/11/10 2:34 下午
     * @Param: enter
     * @Return: ProductionAssemblyOrderListResult
     * @desc: 列表
     * @param enter
     */
    @Override
    public PageResult<ProductionAssemblyOrderListResult> list(ProductionAssemblyOrderListEnter enter) {
        int count = productionAssemblyOrderServiceMapper.listCount(enter);
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        return PageResult.create(enter, count, productionAssemblyOrderServiceMapper.list(enter));
    }

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/11/11 2:45 下午
     * @Param: AssemblyOrderDetailEnter
     * @Return: ProductionAssemblyOrderDetailResult
     * @desc: 详情
     * @param enter
     */
    @Override
    public ProductionAssemblyOrderDetailResult detail(AssemblyOrderDetailEnter enter) {
        ProductionAssemblyOrderDetailResult detail = productionAssemblyOrderServiceMapper.detail(enter);
        if (detail == null) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        detail.setProductList(this.detailProductList(enter));
        detail.setOperatingDynamicsList(productionOrderTraceService.listByBussId(new ListByBussIdEnter(enter.getId(), OrderTypeEnums.FACTORY_PURCHAS.getValue())));
        detail.setAssociatedOrderResultList(this.associatedOrder(new IdEnter(enter.getId())));
        return detail;
    }

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/11/11 4:18 下午
     * @Param: enter
     * @Return: List<PurchasDetailProductListResult>
     * @desc: 产品详情
     * @param enter
     */
    @Override
    public List<AssemblyDetailProductListResult> detailProductList(AssemblyOrderDetailEnter enter) {
        List<AssemblyDetailProductListResult> result = new ArrayList<>();
        if (enter.getClassType().equals(ProductTypeEnums.SCOOTER.getValue())) {
            result.addAll(productionAssemblyOrderServiceMapper.productScooterList(enter));
        }
        if (enter.getClassType().equals(ProductTypeEnums.COMBINATION.getValue())) {
            result.addAll(productionAssemblyOrderServiceMapper.productCombinList(enter));
        }
        return result;
    }

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/11/11 5:02 下午
     * @Param: enter
     * @Return: List<AssociatedOrderResult>
     * @desc: 关联订单
     * @param enter
     */
    @Override
    public List<AssociatedOrderResult> associatedOrder(IdEnter enter) {
        List<AssociatedOrderResult> resultList = new ArrayList<>();
        List<OpeInWhouseOrder> opeInWhouseOrderList = opeInWhouseOrderService.list(new LambdaQueryWrapper<OpeInWhouseOrder>()
                .eq(OpeInWhouseOrder::getRelationOrderId, enter.getId())
                .eq(OpeInWhouseOrder::getRelationOrderType, OrderTypeEnums.COMBIN_ORDER.getValue()));
        if (CollectionUtils.isNotEmpty(opeInWhouseOrderList)) {
            opeInWhouseOrderList.forEach(item -> {
                resultList.add(new AssociatedOrderResult(item.getId(), item.getInWhNo(), item.getOrderType(), item.getCreatedTime(), null));
            });
        }
        return resultList;
    }

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/11/11 2:47 下午
     * @Param: enter
     * @Return:
     * @desc:
     * @param enter
     */
    @Override
    public List<PurchasDetailProductListResult> productPartDetail(AssemblyOrderDetailEnter enter) {
        List<PurchasDetailProductListResult> result = new ArrayList<>();

        //部件关联关系
        QueryWrapper<OpeProductionPartsRelation> opeProductionPartsRelation = new QueryWrapper<>();
        Integer qty = 0;
        if (enter.getClassType().equals(ProductTypeEnums.SCOOTER.getValue())) {
            OpeCombinOrderScooterB opeCombinOrderScooterB = opeCombinOrderScooterBService.getById(enter.getId());
            if (Objects.isNull(opeCombinOrderScooterB)) {
                throw new SesWebRosException(ExceptionCodeEnums.PRODUCT_HAS_NO_PARTS.getCode(), ExceptionCodeEnums.PRODUCT_HAS_NO_PARTS.getMessage());
            }
            qty = opeCombinOrderScooterB.getQty();
            opeProductionPartsRelation.eq(OpeProductionPartsRelation.COL_PRODUCTION_TYPE, ProductionPartsRelationTypeEnums.SCOOTER_BOM.getValue());
            opeProductionPartsRelation.eq(OpeProductionPartsRelation.COL_PRODUCTION_TYPE, opeCombinOrderScooterB.getScooterBomId());
        }
        if (enter.getClassType().equals(ProductTypeEnums.COMBINATION.getValue())) {
            OpeCombinOrderCombinB opeCombinOrderCombinB = opeCombinOrderCombinBService.getById(enter.getId());
            if (Objects.isNull(opeCombinOrderCombinB)) {
                throw new SesWebRosException(ExceptionCodeEnums.PRODUCT_HAS_NO_PARTS.getCode(), ExceptionCodeEnums.PRODUCT_HAS_NO_PARTS.getMessage());
            }
            qty = opeCombinOrderCombinB.getQty();
            opeProductionPartsRelation.eq(OpeProductionPartsRelation.COL_PRODUCTION_TYPE, ProductionPartsRelationTypeEnums.COMBINATION_BOM.getValue());
            opeProductionPartsRelation.eq(OpeProductionPartsRelation.COL_PRODUCTION_ID, opeCombinOrderCombinB.getProductionCombinBomId());
        }

        //查询零部件信息
        List<OpeProductionPartsRelation> productionPartsRelationList = opeProductionPartsRelationService.list(opeProductionPartsRelation);
        if (CollectionUtils.isNotEmpty(productionPartsRelationList)) {
            Map<Long, Integer> partMap = productionPartsRelationList.stream().collect(Collectors.toMap(OpeProductionPartsRelation::getPartsId, OpeProductionPartsRelation::getPartsQty));
            List<PurchasDetailProductListResult> purchasDetailProductListResults = productionAssemblyOrderServiceMapper.partsDetail(partMap.keySet());
            if (CollectionUtils.isEmpty(purchasDetailProductListResults)) {
                throw new SesWebRosException(ExceptionCodeEnums.PART_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PART_IS_NOT_EXIST.getMessage());
            }
            for (PurchasDetailProductListResult part : purchasDetailProductListResults) {
                part.setQty(partMap.get(part.getId()) * qty);
            }
            result.addAll(purchasDetailProductListResults);
        }
        return result;
    }

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/11/11 3:10 下午
     * @Param: enter
     * @Return: GeneralResult
     * @desc: 保存组装单
     * @param enter
     */
    @Transactional
    @Override
    public GeneralResult save(SaveAssemblyOrderEnter enter) {
        List<SaveAssemblyProductEnter> productEnterList = new ArrayList<>();
        List<OpeCombinOrderScooterB> saveOpeCombinOrderScooterBList = new ArrayList<>();
        List<OpeCombinOrderCombinB> saveOpeCombinOrderCominBList = new ArrayList<>();
        try {
            productEnterList.addAll(JSON.parseArray(enter.getSt(), SaveAssemblyProductEnter.class));
        } catch (Exception e) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }
        if (CollectionUtils.isEmpty(productEnterList)) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }

        int productQty = Long.valueOf(productEnterList.stream().map(SaveAssemblyProductEnter::getQty).count()).intValue();
        //主单据
        OpeCombinOrder opeCombinOrder = buildOpeCombinOrder(enter, productQty);

        SaveOpTraceEnter saveOpTraceEnter =null;
        if (enter.getId() == null || enter.getId() == 0) {
            Long assemblyProductId = idAppService.getId(SequenceName.OPE_COMBIN_ORDER);
            opeCombinOrder.setCreatedBy(enter.getUserId());
            opeCombinOrder.setCreatedTime(new Date());

            //子单据
            if (enter.getCombinType().equals(ProductTypeEnums.SCOOTER.getValue())) {
                buildOpeCombinOrderScooterB(enter, productEnterList, assemblyProductId, saveOpeCombinOrderScooterBList);
            }
            if (enter.getCombinType().equals(ProductTypeEnums.COMBINATION.getValue())) {
                buildOpeCombinOrderCombinB(enter, productEnterList, assemblyProductId, saveOpeCombinOrderCominBList);
            }
            //订单节点
            OrderStatusFlowEnter orderStatusFlowEnter = new OrderStatusFlowEnter(null, opeCombinOrder.getCombinStatus(), OrderTypeEnums.COMBIN_ORDER.getValue(), opeCombinOrder.getId(),
                    enter.getRemark());
            BeanUtils.copyProperties(enter, orderStatusFlowEnter);
            orderStatusFlowEnter.setId(null);
            orderStatusFlowService.save(orderStatusFlowEnter);
            //订单日志
            saveOpTraceEnter = new SaveOpTraceEnter(null, opeCombinOrder.getId(), OrderTypeEnums.COMBIN_ORDER.getValue(), OrderOperationTypeEnums.CREATE.getValue(), enter.getRemark());
        } else {
            //封装子单据
            if (enter.getCombinType().equals(ProductTypeEnums.SCOOTER.getValue())) {
                //删除子订单
                opeCombinOrderScooterBService.remove(new LambdaQueryWrapper<OpeCombinOrderScooterB>().eq(OpeCombinOrderScooterB::getCombinId,enter.getId()));
                buildOpeCombinOrderScooterB(enter, productEnterList, enter.getId(), saveOpeCombinOrderScooterBList);
            }
            if (enter.getCombinType().equals(ProductTypeEnums.COMBINATION.getValue())) {
                opeCombinOrderCombinBService.remove(new LambdaQueryWrapper<OpeCombinOrderCombinB>().eq(OpeCombinOrderCombinB::getCombinId,enter.getId()));
                buildOpeCombinOrderCombinB(enter, productEnterList, enter.getId(), saveOpeCombinOrderCominBList);
            }
            //订单日志
            saveOpTraceEnter = new SaveOpTraceEnter(null, opeCombinOrder.getId(), OrderTypeEnums.COMBIN_ORDER.getValue(), OrderOperationTypeEnums.EDIT.getValue(), enter.getRemark());
        }

        //操作动态
        BeanUtils.copyProperties(enter, saveOpTraceEnter);
        saveOpTraceEnter.setId(null);
        productionOrderTraceService.save(saveOpTraceEnter);

        opeCombinOrderService.saveOrUpdate(opeCombinOrder);
        if (CollectionUtils.isNotEmpty(saveOpeCombinOrderScooterBList)){
            opeCombinOrderScooterBService.saveBatch(saveOpeCombinOrderScooterBList);
        }
        if (CollectionUtils.isNotEmpty(saveOpeCombinOrderCominBList)){
            opeCombinOrderCombinBService.saveBatch(saveOpeCombinOrderCominBList);
        }
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/11/12 3:46 下午
     * @Param: enter
     * @Return: GeneralResult
     * @desc: 备料
     * @param enter
     */
    @Transactional
    @Override
    public GeneralResult materialPreparation(IdEnter enter) {
        OpeCombinOrder opeCombinOrder = opeCombinOrderService.getById(enter.getId());
        if (opeCombinOrder==null){
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(),ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        if (!opeCombinOrder.getCombinStatus().equals(CombinOrderStatusEnums.DRAF.getValue())){
            throw new SesWebRosException(ExceptionCodeEnums.STATUS_ILLEGAL.getCode(),ExceptionCodeEnums.STATUS_ILLEGAL.getMessage());
        }
        opeCombinOrder.setCombinStatus(CombinOrderStatusEnums.PREPARED.getValue());
        opeCombinOrder.setUpdatedBy(enter.getUserId());
        opeCombinOrder.setUpdatedTime(new Date());
        opeCombinOrderService.updateById(opeCombinOrder);
        //操作动态
        SaveOpTraceEnter saveOpTraceEnter = new SaveOpTraceEnter(null, opeCombinOrder.getId(), OrderTypeEnums.COMBIN_ORDER.getValue(), OrderOperationTypeEnums.STOCK_UP.getValue(), opeCombinOrder.getRemark());
        BeanUtils.copyProperties(enter, saveOpTraceEnter);
        saveOpTraceEnter.setId(null);
        productionOrderTraceService.save(saveOpTraceEnter);

        //订单节点
        OrderStatusFlowEnter orderStatusFlowEnter = new OrderStatusFlowEnter(null, opeCombinOrder.getCombinStatus(), OrderTypeEnums.COMBIN_ORDER.getValue(), opeCombinOrder.getId(),
                opeCombinOrder.getRemark());
        BeanUtils.copyProperties(enter, orderStatusFlowEnter);
        orderStatusFlowEnter.setId(null);
        orderStatusFlowService.save(orderStatusFlowEnter);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/11/12 3:46 下午
     * @Param: enter
     * @Return: GeneralResult
     * @desc: 组装
     * @param enter
     */
    @Transactional
    @Override
    public GeneralResult assembly(IdEnter enter) {
        OpeCombinOrder opeCombinOrder = opeCombinOrderService.getById(enter.getId());
        if (opeCombinOrder==null){
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(),ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        if (!opeCombinOrder.getCombinStatus().equals(CombinOrderStatusEnums.PREPARATION_COMPLETED.getValue())){
            throw new SesWebRosException(ExceptionCodeEnums.STATUS_ILLEGAL.getCode(),ExceptionCodeEnums.STATUS_ILLEGAL.getMessage());
        }
        opeCombinOrder.setCombinStatus(CombinOrderStatusEnums.TO_BE_ASSEMBLED.getValue());
        opeCombinOrder.setUpdatedBy(enter.getUserId());
        opeCombinOrder.setUpdatedTime(new Date());
        opeCombinOrderService.updateById(opeCombinOrder);
        //操作动态
        SaveOpTraceEnter saveOpTraceEnter = new SaveOpTraceEnter(null, opeCombinOrder.getId(), OrderTypeEnums.COMBIN_ORDER.getValue(), OrderOperationTypeEnums.COMBIN.getValue(),
                opeCombinOrder.getRemark());
        BeanUtils.copyProperties(enter, saveOpTraceEnter);
        saveOpTraceEnter.setId(null);
        productionOrderTraceService.save(saveOpTraceEnter);

        //订单节点
        OrderStatusFlowEnter orderStatusFlowEnter = new OrderStatusFlowEnter(null, opeCombinOrder.getCombinStatus(), OrderTypeEnums.COMBIN_ORDER.getValue(), opeCombinOrder.getId(),
                opeCombinOrder.getRemark());
        BeanUtils.copyProperties(enter, orderStatusFlowEnter);
        orderStatusFlowEnter.setId(null);
        orderStatusFlowService.save(orderStatusFlowEnter);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/11/12
     * @Param: enter
     * @Return: GeneralResult
     * @desc: 删除
     * @param enter
     */
    @Transactional
    @Override
    public GeneralResult delete(IdEnter enter) {
        OpeCombinOrder opeCombinOrder = opeCombinOrderService.getById(enter.getId());
        if (opeCombinOrder==null){
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(),ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        if (!opeCombinOrder.getCombinStatus().equals(CombinOrderStatusEnums.DRAF.getValue())){
            throw new SesWebRosException(ExceptionCodeEnums.STATUS_ILLEGAL.getCode(),ExceptionCodeEnums.STATUS_ILLEGAL.getMessage());
        }
        opeCombinOrder.setUpdatedBy(enter.getUserId());
        opeCombinOrder.setUpdatedTime(new Date());
        opeCombinOrderService.removeById(opeCombinOrder.getId());
        //操作动态
        SaveOpTraceEnter saveOpTraceEnter = new SaveOpTraceEnter(null, opeCombinOrder.getId(), OrderTypeEnums.COMBIN_ORDER.getValue(), OrderOperationTypeEnums.DELETE.getValue(),
                opeCombinOrder.getRemark());
        BeanUtils.copyProperties(enter, saveOpTraceEnter);
        saveOpTraceEnter.setId(null);
        productionOrderTraceService.save(saveOpTraceEnter);
        return new GeneralResult(enter.getRequestId());
    }


    private List<OpeCombinOrderCombinB> buildOpeCombinOrderCombinB(SaveAssemblyOrderEnter enter, List<SaveAssemblyProductEnter> productEnterList, Long assemblyProductId,
                                                                   List<OpeCombinOrderCombinB> saveOpeCombinOrderCominBList) {
        List<OpeProductionCombinBom> opeProductionCombinBomList = opeProductionCombinBomService.listByIds(productEnterList.stream().map(SaveAssemblyProductEnter::getId).collect(Collectors.toSet()));
        if (CollectionUtils.isEmpty(opeProductionCombinBomList)) {
            throw new SesWebRosException(ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getMessage());
        }
        productEnterList.forEach(item -> {

            OpeProductionCombinBom opeProductionCombinBom = opeProductionCombinBomList.stream().filter(combin -> combin.getId().equals(item.getId())).findFirst().orElse(null);
            if (Objects.isNull(opeProductionCombinBom)) {
                throw new SesWebRosException(ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getMessage());
            }
            OpeCombinOrderCombinB opeCombinOrderCombinB = new OpeCombinOrderCombinB();

            opeCombinOrderCombinB.setId(idAppService.getId(SequenceName.OPE_COMBIN_ORDER_SCOOTER_B));
            opeCombinOrderCombinB.setDr(0);
            opeCombinOrderCombinB.setCombinId(assemblyProductId);
            opeCombinOrderCombinB.setProductionCombinBomId(opeProductionCombinBom.getId());
            opeCombinOrderCombinB.setQty(item.getQty());
            opeCombinOrderCombinB.setWaitQcQty(0);
            opeCombinOrderCombinB.setWaitQcQty(0);
            opeCombinOrderCombinB.setRemark(null);
            opeCombinOrderCombinB.setCreatedBy(enter.getUserId());
            opeCombinOrderCombinB.setCreatedTime(new Date());
            opeCombinOrderCombinB.setUpdatedBy(enter.getUserId());
            opeCombinOrderCombinB.setUpdatedTime(new Date());
            saveOpeCombinOrderCominBList.add(opeCombinOrderCombinB);

        });
        return saveOpeCombinOrderCominBList;
    }

    private List<OpeCombinOrderScooterB> buildOpeCombinOrderScooterB(SaveAssemblyOrderEnter enter, List<SaveAssemblyProductEnter> productEnterList, Long assemblyProductId,
                                                                     List<OpeCombinOrderScooterB> saveOpeCombinOrderScooterBList) {
        List<Map<String, Object>> listMap = new ArrayList<>();
        for (SaveAssemblyProductEnter scooterB : productEnterList) {
            Map<String, Object> map = new HashMap<>();
            map.put(OpeProductionScooterBom.COL_GROUP_ID, scooterB.getGroupId());
            map.put(OpeProductionScooterBom.COL_COLOR_ID, scooterB.getColorId());
            listMap.add(map);
        }
        //查询整车Bom
        List<OpeProductionScooterBom> byGroupAndColorIds = productionAssemblyOrderServiceMapper.getByGroupAndColorIds(listMap);
        if (CollectionUtils.isEmpty(byGroupAndColorIds)) {
            throw new SesWebRosException(ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getMessage());
        }
        productEnterList.forEach(item -> {

            OpeProductionScooterBom opeProductionScooterBom = byGroupAndColorIds.stream().filter(scooter -> {
                scooter.getColorId().equals(item.getColorId());
                scooter.getGroupId().equals(item.getGroupId());
                return true;
            }).findFirst().orElse(null);
            if (Objects.isNull(opeProductionScooterBom)) {
                throw new SesWebRosException(ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getMessage());
            }
            OpeCombinOrderScooterB opeCombinOrderScooterB = new OpeCombinOrderScooterB();
            opeCombinOrderScooterB.setId(idAppService.getId(SequenceName.OPE_COMBIN_ORDER_SCOOTER_B));
            opeCombinOrderScooterB.setDr(0);
            opeCombinOrderScooterB.setCombinId(assemblyProductId);
            opeCombinOrderScooterB.setGroupId(item.getGroupId());
            opeCombinOrderScooterB.setColorId(item.getColorId());
            opeCombinOrderScooterB.setScooterBomId(opeProductionScooterBom.getId());
            opeCombinOrderScooterB.setQty(item.getQty());
            opeCombinOrderScooterB.setWaitQcQty(0);
            opeCombinOrderScooterB.setWaitQcQty(0);
            opeCombinOrderScooterB.setRemark(null);
            opeCombinOrderScooterB.setCreatedBy(enter.getUserId());
            opeCombinOrderScooterB.setCreatedTime(new Date());
            opeCombinOrderScooterB.setUpdatedBy(enter.getUserId());
            opeCombinOrderScooterB.setUpdatedTime(new Date());
            saveOpeCombinOrderScooterBList.add(opeCombinOrderScooterB);

        });
        return saveOpeCombinOrderScooterBList;
    }

    private OpeCombinOrder buildOpeCombinOrder(SaveAssemblyOrderEnter enter, Integer productQty) {
        //构建主订单
        OpeCombinOrder opeCombinOrder = new OpeCombinOrder();
        BeanUtils.copyProperties(enter, opeCombinOrder);
        opeCombinOrder.setDr(0);
        opeCombinOrder.setCombinNo(RandomUtil.randomString(RandomUtil.BASE_CHAR, 10));
        opeCombinOrder.setCombinStatus(CombinOrderStatusEnums.DRAF.getValue());
        opeCombinOrder.setCombinQty(productQty);
        opeCombinOrder.setUpdatedBy(enter.getUserId());
        opeCombinOrder.setUpdatedTime(new Date());
        return opeCombinOrder;
    }
}
