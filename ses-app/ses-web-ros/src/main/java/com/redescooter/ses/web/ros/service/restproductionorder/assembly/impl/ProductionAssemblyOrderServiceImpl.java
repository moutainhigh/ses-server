package com.redescooter.ses.web.ros.service.restproductionorder.assembly.impl;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.enums.bom.ProductionPartsRelationTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.NewInWhouseOrderStatusEnum;
import com.redescooter.ses.api.common.enums.restproductionorder.OrderOperationTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.OrderTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.ProductTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.assembly.CombinOrderStatusEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.assembly.NewCombinOrderStatusEnums;
import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.SesStringUtils;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.constant.StringManaConstant;
import com.redescooter.ses.web.ros.dao.restproductionorder.InWhouseOrderServiceMapper;
import com.redescooter.ses.web.ros.dao.restproductionorder.ProductionAssemblyOrderServiceMapper;
import com.redescooter.ses.web.ros.dm.OpeCombinOrder;
import com.redescooter.ses.web.ros.dm.OpeCombinOrderCombinB;
import com.redescooter.ses.web.ros.dm.OpeCombinOrderScooterB;
import com.redescooter.ses.web.ros.dm.OpeInWhouseCombinB;
import com.redescooter.ses.web.ros.dm.OpeInWhouseOrder;
import com.redescooter.ses.web.ros.dm.OpeInWhouseScooterB;
import com.redescooter.ses.web.ros.dm.OpeOutWhouseOrder;
import com.redescooter.ses.web.ros.dm.OpeProductionCombinBom;
import com.redescooter.ses.web.ros.dm.OpeProductionPartsRelation;
import com.redescooter.ses.web.ros.dm.OpeProductionScooterBom;
import com.redescooter.ses.web.ros.dm.OpeWmsPartsStock;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpeCombinOrderCombinBService;
import com.redescooter.ses.web.ros.service.base.OpeCombinOrderScooterBService;
import com.redescooter.ses.web.ros.service.base.OpeCombinOrderService;
import com.redescooter.ses.web.ros.service.base.OpeInWhouseOrderService;
import com.redescooter.ses.web.ros.service.base.OpeOutWhouseOrderService;
import com.redescooter.ses.web.ros.service.base.OpeProductionCombinBomService;
import com.redescooter.ses.web.ros.service.base.OpeProductionPartsRelationService;
import com.redescooter.ses.web.ros.service.base.OpeProductionPartsService;
import com.redescooter.ses.web.ros.service.base.OpeProductionScooterBomService;
import com.redescooter.ses.web.ros.service.base.OpeWmsPartsStockService;
import com.redescooter.ses.web.ros.service.restproductionorder.assembly.ProductionAssemblyOrderService;
import com.redescooter.ses.web.ros.service.restproductionorder.number.OrderNumberService;
import com.redescooter.ses.web.ros.service.restproductionorder.orderflow.OrderStatusFlowService;
import com.redescooter.ses.web.ros.service.restproductionorder.outbound.OutboundOrderService;
import com.redescooter.ses.web.ros.service.restproductionorder.trace.ProductionOrderTraceService;
import com.redescooter.ses.web.ros.utils.NumberUtil;
import com.redescooter.ses.web.ros.vo.restproduct.BomNameData;
import com.redescooter.ses.web.ros.vo.restproduct.BomNoEnter;
import com.redescooter.ses.web.ros.vo.restproduct.CombinNameData;
import com.redescooter.ses.web.ros.vo.restproduct.CombinNameEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.AssociatedOrderResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.Invoiceorder.ProductEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.assembly.AssemblyDetailProductListResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.assembly.AssemblyOrderDetailEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.assembly.ProductionAssemblyOrderDetailResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.assembly.ProductionAssemblyOrderListEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.assembly.ProductionAssemblyOrderListResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.assembly.ProductionCombinAssemblyExport;
import com.redescooter.ses.web.ros.vo.restproductionorder.assembly.ProductionCombinScooterExport;
import com.redescooter.ses.web.ros.vo.restproductionorder.assembly.SaveAssemblyOrderEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.assembly.SaveAssemblyProductListEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.number.OrderNumberEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.optrace.ListByBussIdEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.optrace.SaveOpTraceEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.orderflow.OrderStatusFlowEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.purchass.PurchasDetailProductListResult;
import com.redescooter.ses.web.ros.vo.specificat.ColorDataResult;
import com.redescooter.ses.web.ros.vo.specificat.SpecificatGroupDataResult;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @ClassName:ProductionAssemblyOrderService
 * @description: ProductionAssemblyOrderService
 * @author: Alex
 * @Version???1.3
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
    private OpeProductionScooterBomService opeProductionScooterBomService;

    @Autowired
    private OpeProductionCombinBomService opeProductioncombinBomService;

    @Autowired
    private OrderNumberService orderNumberService;

    @Autowired
    private OutboundOrderService outboundOrderService;

    @Autowired
    private OpeOutWhouseOrderService opeOutWhouseOrderService;

    @Autowired
    private InWhouseOrderServiceMapper inWhouseOrderServiceMapper;

    @Autowired
    private OpeCombinOrderScooterBService opeCombinOrderScooterBomService;

    @Autowired
    private OpeWmsPartsStockService opeWmsPartsStockService;

    @Autowired
    private OpeProductionPartsService opeProductionPartsService;

    @Autowired
    private IdAppService idAppService;


    /**
     * @param enter
     * @Description
     * @Author: alex
     * @Date: 2020/11/12 7:15 ??????
     * @Param: enter
     * @Return: map
     * @desc: ????????????
     */
    @Override
    public Map<Integer, Integer> countByType(GeneralEnter enter) {
        List<CountByStatusResult> countByStatusResultList = productionAssemblyOrderServiceMapper.countByType(enter);
        Map<Integer, Integer> result = countByStatusResultList.stream().collect(Collectors.toMap(item -> {
            return Integer.valueOf(item.getStatus());
        }, CountByStatusResult::getTotalCount));
        if (!result.containsKey(ProductTypeEnums.SCOOTER.getValue())) {
            result.put(ProductTypeEnums.SCOOTER.getValue(), 0);
        }
        if (!result.containsKey(ProductTypeEnums.COMBINATION.getValue())) {
            result.put(ProductTypeEnums.COMBINATION.getValue(), 0);
        }
        return result;
    }

    /**
     * @param enter
     * @Description
     * @Author: alex
     * @Date: 2020/11/10 2:34 ??????
     * @Param: enter
     * @Return: ProductionAssemblyOrderListResult
     * @desc: ??????
     */
    @Override
    public PageResult<ProductionAssemblyOrderListResult> list(ProductionAssemblyOrderListEnter enter) {
        SesStringUtils.objStringTrim(enter);
        int count = productionAssemblyOrderServiceMapper.listCount(enter);
        if (NumberUtil.eqZero(count)) {
            return PageResult.createZeroRowResult(enter);
        }
        return PageResult.create(enter, count, productionAssemblyOrderServiceMapper.list(enter));
    }

    /**
     * @param enter
     * @Description
     * @Author: alex
     * @Date: 2020/11/11 2:45 ??????
     * @Param: AssemblyOrderDetailEnter
     * @Return: ProductionAssemblyOrderDetailResult
     * @desc: ??????
     */
    @Override
    public ProductionAssemblyOrderDetailResult detail(AssemblyOrderDetailEnter enter) {
        ProductionAssemblyOrderDetailResult detail = productionAssemblyOrderServiceMapper.detail(enter);
        if (StringManaConstant.entityIsNull(detail)) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        detail.setProductList(this.detailProductList(enter));
        detail.setOperatingDynamicsList(productionOrderTraceService.listByBussId(new ListByBussIdEnter(enter.getId(), OrderTypeEnums.COMBIN_ORDER.getValue())));
        detail.setAssociatedOrderResultList(this.associatedOrder(new IdEnter(enter.getId())));
        return detail;
    }

    /**
     * @param enter
     * @Description
     * @Author: alex
     * @Date: 2020/11/11 4:18 ??????
     * @Param: enter
     * @Return: List<PurchasDetailProductListResult>
     * @desc: ????????????
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
     * @param enter
     * @Description
     * @Author: alex
     * @Date: 2020/11/11 5:02 ??????
     * @Param: enter
     * @Return: List<AssociatedOrderResult>
     * @desc: ????????????
     */
    @Override
    public List<AssociatedOrderResult> associatedOrder(IdEnter enter) {
        List<AssociatedOrderResult> resultList = new ArrayList<>();
        //?????????
        List<OpeInWhouseOrder> opeInWhouseOrderList = opeInWhouseOrderService.list(new LambdaQueryWrapper<OpeInWhouseOrder>()
                .eq(OpeInWhouseOrder::getRelationOrderId, enter.getId())
                .eq(OpeInWhouseOrder::getRelationOrderType, OrderTypeEnums.COMBIN_ORDER.getValue()));
        if (CollectionUtils.isNotEmpty(opeInWhouseOrderList)) {
            opeInWhouseOrderList.forEach(item -> {
                resultList.add(new AssociatedOrderResult(item.getId(), item.getInWhNo(), OrderTypeEnums.FACTORY_INBOUND.getValue(), item.getCreatedTime(), null));
            });
        }
        //?????????
        List<OpeOutWhouseOrder> opeOutWhouseOrderList = opeOutWhouseOrderService.list(new LambdaQueryWrapper<OpeOutWhouseOrder>()
                .eq(OpeOutWhouseOrder::getRelationId, enter.getId())
                .eq(OpeOutWhouseOrder::getRelationType, OrderTypeEnums.COMBIN_ORDER.getValue()));
        if (CollectionUtils.isNotEmpty(opeOutWhouseOrderList)) {
            opeOutWhouseOrderList.forEach(item -> {
                resultList.add(new AssociatedOrderResult(item.getId(), item.getOutWhNo(), OrderTypeEnums.OUTBOUND.getValue(), item.getCreatedTime(), null));
            });
        }
        return resultList;
    }

    /**
     * @param enter
     * @Description
     * @Author: alex
     * @Date: 2020/11/11 2:47 ??????
     * @Param: enter
     * @Return:
     * @desc:
     */
    @Override
    public List<PurchasDetailProductListResult> productPartDetail(AssemblyOrderDetailEnter enter) {
        List<PurchasDetailProductListResult> result = new ArrayList<>();

        //??????????????????
        QueryWrapper<OpeProductionPartsRelation> opeProductionPartsRelation = new QueryWrapper<>();
        Integer qty = 0;
        if (enter.getClassType().equals(ProductTypeEnums.SCOOTER.getValue())) {
            OpeCombinOrderScooterB opeCombinOrderScooterB = opeCombinOrderScooterBService.getById(enter.getId());
            if (Objects.isNull(opeCombinOrderScooterB)) {
                throw new SesWebRosException(ExceptionCodeEnums.PRODUCT_HAS_NO_PARTS.getCode(), ExceptionCodeEnums.PRODUCT_HAS_NO_PARTS.getMessage());
            }
            qty = opeCombinOrderScooterB.getQty();
            opeProductionPartsRelation.eq(OpeProductionPartsRelation.COL_PRODUCTION_TYPE, ProductionPartsRelationTypeEnums.SCOOTER_BOM.getValue());
            opeProductionPartsRelation.eq(OpeProductionPartsRelation.COL_PRODUCTION_ID, opeCombinOrderScooterB.getScooterBomId());
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

        //?????????????????????
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
     * @param enter
     * @Description
     * @Author: alex
     * @Date: 2020/11/11 3:10 ??????
     * @Param: enter
     * @Return: GeneralResult
     * @desc: ???????????????
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult save(SaveAssemblyOrderEnter enter) {
        List<SaveAssemblyProductListEnter> productEnterList = new ArrayList<>();
        List<OpeCombinOrderScooterB> saveOpeCombinOrderScooterBList = new ArrayList<>();
        List<OpeCombinOrderCombinB> saveOpeCombinOrderCominBList = new ArrayList<>();
        try {
            productEnterList.addAll(JSON.parseArray(enter.getSt(), SaveAssemblyProductListEnter.class));
        } catch (Exception e) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }
        if (CollectionUtils.isEmpty(productEnterList)) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }

//        int productQty = Long.valueOf(productEnterList.stream().map(SaveAssemblyProductListEnter::getQty).count()).intValue();
        int productQty = productEnterList.stream().mapToInt(SaveAssemblyProductListEnter::getQty).sum();
        //?????????
        OpeCombinOrder opeCombinOrder = buildOpeCombinOrder(enter, productQty);

        SaveOpTraceEnter saveOpTraceEnter = null;
        if (StringManaConstant.entityIsNull(enter.getId()) || 0 == enter.getId()) {
            Long assemblyProductId = idAppService.getId(SequenceName.OPE_COMBIN_ORDER);
            opeCombinOrder.setId(assemblyProductId);
            opeCombinOrder.setCreatedBy(enter.getUserId());
            opeCombinOrder.setCreatedTime(new Date());
            opeCombinOrder.setCombinNo(orderNumberService.generateOrderNo(new OrderNumberEnter(OrderTypeEnums.COMBIN_ORDER.getValue())));

            //?????????
            if (enter.getCombinType().equals(ProductTypeEnums.SCOOTER.getValue())) {
                buildOpeCombinOrderScooterB(enter, productEnterList, assemblyProductId, saveOpeCombinOrderScooterBList);
            }
            if (enter.getCombinType().equals(ProductTypeEnums.COMBINATION.getValue())) {
                buildOpeCombinOrderCombinB(enter, productEnterList, assemblyProductId, saveOpeCombinOrderCominBList);
            }
            //????????????
            OrderStatusFlowEnter orderStatusFlowEnter = new OrderStatusFlowEnter(null, opeCombinOrder.getCombinStatus(), OrderTypeEnums.COMBIN_ORDER.getValue(), opeCombinOrder.getId(), enter.getRemark());
            BeanUtils.copyProperties(enter, orderStatusFlowEnter);
            orderStatusFlowEnter.setId(null);
            orderStatusFlowService.save(orderStatusFlowEnter);
            //????????????
            saveOpTraceEnter = new SaveOpTraceEnter(null, opeCombinOrder.getId(), OrderTypeEnums.COMBIN_ORDER.getValue(), OrderOperationTypeEnums.CREATE.getValue(), enter.getRemark());
        } else {
            //???????????????
            if (enter.getCombinType().equals(ProductTypeEnums.SCOOTER.getValue())) {
                //???????????????
                opeCombinOrderScooterBService.remove(new LambdaQueryWrapper<OpeCombinOrderScooterB>().eq(OpeCombinOrderScooterB::getCombinId, enter.getId()));
                buildOpeCombinOrderScooterB(enter, productEnterList, enter.getId(), saveOpeCombinOrderScooterBList);
            }
            if (enter.getCombinType().equals(ProductTypeEnums.COMBINATION.getValue())) {
                opeCombinOrderCombinBService.remove(new LambdaQueryWrapper<OpeCombinOrderCombinB>().eq(OpeCombinOrderCombinB::getCombinId, enter.getId()));
                buildOpeCombinOrderCombinB(enter, productEnterList, enter.getId(), saveOpeCombinOrderCominBList);
            }
            //????????????
            saveOpTraceEnter = new SaveOpTraceEnter(null, opeCombinOrder.getId(), OrderTypeEnums.COMBIN_ORDER.getValue(), OrderOperationTypeEnums.EDIT.getValue(), enter.getRemark());
        }

        //????????????
        BeanUtils.copyProperties(enter, saveOpTraceEnter);
        saveOpTraceEnter.setId(null);
        productionOrderTraceService.save(saveOpTraceEnter);

        opeCombinOrderService.saveOrUpdate(opeCombinOrder);
        if (CollectionUtils.isNotEmpty(saveOpeCombinOrderScooterBList)) {
            opeCombinOrderScooterBService.saveBatch(saveOpeCombinOrderScooterBList);
        }
        if (CollectionUtils.isNotEmpty(saveOpeCombinOrderCominBList)) {
            opeCombinOrderCombinBService.saveBatch(saveOpeCombinOrderCominBList);
        }
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * @param enter
     * @Description
     * @Author: alex
     * @Date: 2020/11/12 3:46 ??????
     * @Param: enter
     * @Return: GeneralResult
     * @desc: ??????
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult materialPreparation(IdEnter enter) {
        OpeCombinOrder opeCombinOrder = opeCombinOrderService.getById(enter.getId());
        if (StringManaConstant.entityIsNull(opeCombinOrder)) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        if (!opeCombinOrder.getCombinStatus().equals(NewCombinOrderStatusEnums.DRAF.getValue())) {
            throw new SesWebRosException(ExceptionCodeEnums.STATUS_ILLEGAL.getCode(), ExceptionCodeEnums.STATUS_ILLEGAL.getMessage());
        }
        // ?????????????????? ???????????????????????? ????????????????????????????????????????????????
        checkPartsStock(opeCombinOrder);
        opeCombinOrder.setCombinStatus(NewCombinOrderStatusEnums.PREPARED.getValue());
        opeCombinOrder.setUpdatedBy(enter.getUserId());
        opeCombinOrder.setUpdatedTime(new Date());
        opeCombinOrderService.updateById(opeCombinOrder);
        //????????????
        SaveOpTraceEnter saveOpTraceEnter = new SaveOpTraceEnter(null, opeCombinOrder.getId(), OrderTypeEnums.COMBIN_ORDER.getValue(), OrderOperationTypeEnums.STOCK_UP.getValue(), opeCombinOrder.getRemark());
        BeanUtils.copyProperties(enter, saveOpTraceEnter);
        saveOpTraceEnter.setId(null);
        productionOrderTraceService.save(saveOpTraceEnter);

        //????????????
        OrderStatusFlowEnter orderStatusFlowEnter = new OrderStatusFlowEnter(null, opeCombinOrder.getCombinStatus(), OrderTypeEnums.COMBIN_ORDER.getValue(), opeCombinOrder.getId(), opeCombinOrder.getRemark());
        BeanUtils.copyProperties(enter, orderStatusFlowEnter);
        orderStatusFlowEnter.setId(null);
        orderStatusFlowService.save(orderStatusFlowEnter);

        //???????????????
        List<ProductEnter> productEnterList = new ArrayList<>();
        if (opeCombinOrder.getCombinType().equals(ProductTypeEnums.SCOOTER.getValue())) {
            List<OpeCombinOrderScooterB> opeCombinOrderScooterBList = opeCombinOrderScooterBService.list(new LambdaQueryWrapper<OpeCombinOrderScooterB>().eq(OpeCombinOrderScooterB::getCombinId, opeCombinOrder.getId()));
            if (CollectionUtils.isEmpty(opeCombinOrderScooterBList)) {
                throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
            }
            productEnterList.addAll(opeCombinOrderScooterBList.stream().map(item -> {
                return new ProductEnter(item.getScooterBomId(), item.getColorId(), item.getGroupId(), item.getQty(), item.getRemark(), item.getQty());
            }).collect(Collectors.toList()));
        } else {
            List<OpeCombinOrderCombinB> opeCombinOrderCombinBList = opeCombinOrderCombinBService.list(new LambdaQueryWrapper<OpeCombinOrderCombinB>().eq(OpeCombinOrderCombinB::getCombinId, opeCombinOrder.getId()));
            if (CollectionUtils.isEmpty(opeCombinOrderCombinBList)) {
                throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
            }
            productEnterList.addAll(opeCombinOrderCombinBList.stream().map(item -> {
                return new ProductEnter(item.getProductionCombinBomId(), null, null, item.getQty(), item.getRemark(), item.getQty());
            }).collect(Collectors.toList()));
        }

//        //????????? ???????????? ?????????
//        SaveOutboundOrderEnter saveOutboundOrderEnter = new SaveOutboundOrderEnter(null,opeCombinOrder.getId(),opeCombinOrder.getCombinNo(),OrderTypeEnums.COMBIN_ORDER.getValue(),
//                orderNumberService.generateOrderNo(new OrderNumberEnter(OrderTypeEnums.OUTBOUND.getValue())),
//                opeCombinOrder.getCombinType(),OutBoundOrderTypeEnums.PRODUCT.getValue(), opeCombinOrder.getCombinQty(),opeCombinOrder.getRemark(),productEnterList);
//        saveOutboundOrderEnter.setUserId(enter.getUserId());
//        outboundOrderService.save(saveOutboundOrderEnter);
        // ??????  ???????????????????????????????????????
        outboundOrderService.createOutWhByCombin(opeCombinOrder.getId(), enter.getUserId());
        return new GeneralResult(enter.getRequestId());
    }


    // ????????????????????????(????????????????????????)
    public void checkPartsStock(OpeCombinOrder opeCombinOrder) {
        switch (opeCombinOrder.getCombinType()) {
            case 1:
                // scooter
                // ???????????????????????????
                QueryWrapper<OpeCombinOrderScooterB> scooterBs = new QueryWrapper<>();
                scooterBs.eq(OpeCombinOrderScooterB.COL_COMBIN_ID, opeCombinOrder.getId());
                List<OpeCombinOrderScooterB> combinOrderScooterBS = opeCombinOrderScooterBomService.list(scooterBs);
                if (CollectionUtils.isNotEmpty(combinOrderScooterBS)) {
                    // ??????????????????????????????????????????????????????????????????
                    for (OpeCombinOrderScooterB scooterB : combinOrderScooterBS) {
                        // ?????? ????????????/???????????? ??????????????????????????? ???????????????
                        QueryWrapper<OpeProductionPartsRelation> partsRelation = new QueryWrapper<>();
                        partsRelation.eq(OpeProductionPartsRelation.COL_PRODUCTION_ID, scooterB.getScooterBomId());
                        partsRelation.eq(OpeProductionPartsRelation.COL_PRODUCTION_TYPE, 2);
                        List<OpeProductionPartsRelation> partsRelations = opeProductionPartsRelationService.list(partsRelation);
                        if (CollectionUtils.isNotEmpty(partsRelations)) {
                            // ???????????????????????????????????????/???????????????????????????????????????  ???????????????????????????????????????
                            Map<Long, Integer> scootermap = new HashMap<>();
                            for (OpeProductionPartsRelation relation : partsRelations) {
                                scootermap.put(relation.getPartsId(), relation.getPartsQty() * scooterB.getQty());
                            }
                            // ????????????????????????????????????????????????  ????????????????????????
                            for (Long partsId : scootermap.keySet()) {
                                // ??????ID ???????????????????????????????????????????????????
                                QueryWrapper<OpeWmsPartsStock> partStockQw = new QueryWrapper<>();
                                partStockQw.eq(OpeWmsPartsStock.COL_PARTS_ID, partsId);
                                partStockQw.eq(OpeWmsPartsStock.COL_STOCK_TYPE, 1);
                                partStockQw.last("limit 1");
                                OpeWmsPartsStock partStock = opeWmsPartsStockService.getOne(partStockQw);
                                if (StringManaConstant.entityIsNull(partStock) || (partStock.getAbleStockQty() < scootermap.get(partsId))) {
                                    // ????????????????????????????????????
                                    throw new SesWebRosException(ExceptionCodeEnums.STOCK_IS_SHORTAGE.getCode(), ExceptionCodeEnums.STOCK_IS_SHORTAGE.getMessage());
                                }
                            }
                        }
                    }
                }

            default:
                break;
            case 2:
                // combin
                QueryWrapper<OpeCombinOrderCombinB> combinBs = new QueryWrapper<>();
                combinBs.eq(OpeCombinOrderCombinB.COL_COMBIN_ID, opeCombinOrder.getId());
                List<OpeCombinOrderCombinB> combinOrderCombinBS = opeCombinOrderCombinBService.list(combinBs);
                if (CollectionUtils.isNotEmpty(combinOrderCombinBS)) {
                    for (OpeCombinOrderCombinB combinB : combinOrderCombinBS) {
                        // ???????????????????????????????????????
                        QueryWrapper<OpeProductionPartsRelation> partsRelation = new QueryWrapper<>();
                        partsRelation.eq(OpeProductionPartsRelation.COL_PRODUCTION_ID, combinB.getProductionCombinBomId());
                        partsRelation.eq(OpeProductionPartsRelation.COL_PRODUCTION_TYPE, 4);
                        List<OpeProductionPartsRelation> partsComRelations = opeProductionPartsRelationService.list(partsRelation);
                        if (CollectionUtils.isNotEmpty(partsComRelations)) {
                            Map<Long, Integer> scootercombinMap = new HashMap<>();
                            for (OpeProductionPartsRelation combinRelation : partsComRelations) {
                                scootercombinMap.put(combinRelation.getPartsId(), combinRelation.getPartsQty() * combinB.getQty());
                            }
                            // ????????????????????????????????????????????????  ????????????????????????
                            for (Long combinPartsId : scootercombinMap.keySet()) {
                                // ??????ID ???????????????????????????????????????????????????
                                QueryWrapper<OpeWmsPartsStock> partStockQw = new QueryWrapper<>();
                                partStockQw.eq(OpeWmsPartsStock.COL_PARTS_ID, combinPartsId);
                                partStockQw.eq(OpeWmsPartsStock.COL_STOCK_TYPE, 1);
                                partStockQw.last("limit 1");
                                OpeWmsPartsStock partStock = opeWmsPartsStockService.getOne(partStockQw);
                                if (StringManaConstant.entityIsNull(partStock) || (partStock.getAbleStockQty() < scootercombinMap.get(combinPartsId))) {
                                    // ????????????????????????????????????
                                    throw new SesWebRosException(ExceptionCodeEnums.STOCK_IS_SHORTAGE.getCode(), ExceptionCodeEnums.STOCK_IS_SHORTAGE.getMessage());
                                }
                            }
                        }
                    }
                }
                break;
        }
    }


    /**
     * @param enter
     * @Description
     * @Author: alex
     * @Date: 2020/11/12 3:46 ??????
     * @Param: enter
     * @Return: GeneralResult
     * @desc: ??????
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult assembly(IdEnter enter) {
        OpeCombinOrder opeCombinOrder = opeCombinOrderService.getById(enter.getId());
        if (StringManaConstant.entityIsNull(opeCombinOrder)) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        if (!opeCombinOrder.getCombinStatus().equals(NewCombinOrderStatusEnums.PREPARATION_COMPLETED.getValue())) {
            throw new SesWebRosException(ExceptionCodeEnums.STATUS_ILLEGAL.getCode(), ExceptionCodeEnums.STATUS_ILLEGAL.getMessage());
        }
        opeCombinOrder.setCombinStatus(NewCombinOrderStatusEnums.ASSEMBLING.getValue());
        opeCombinOrder.setUpdatedBy(enter.getUserId());
        opeCombinOrder.setUpdatedTime(new Date());
        opeCombinOrderService.updateById(opeCombinOrder);
        //????????????
        SaveOpTraceEnter saveOpTraceEnter = new SaveOpTraceEnter(null, opeCombinOrder.getId(), OrderTypeEnums.COMBIN_ORDER.getValue(), OrderOperationTypeEnums.COMBIN.getValue(), opeCombinOrder.getRemark());
        BeanUtils.copyProperties(enter, saveOpTraceEnter);
        saveOpTraceEnter.setId(null);
        productionOrderTraceService.save(saveOpTraceEnter);

        //????????????
        OrderStatusFlowEnter orderStatusFlowEnter = new OrderStatusFlowEnter(null, opeCombinOrder.getCombinStatus(), OrderTypeEnums.COMBIN_ORDER.getValue(), opeCombinOrder.getId(), opeCombinOrder.getRemark());
        BeanUtils.copyProperties(enter, orderStatusFlowEnter);
        orderStatusFlowEnter.setId(null);
        orderStatusFlowService.save(orderStatusFlowEnter);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * @param enter
     * @Description
     * @Author: alex
     * @Date: 2020/11/12
     * @Param: enter
     * @Return: GeneralResult
     * @desc: ??????
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult delete(IdEnter enter) {
        OpeCombinOrder opeCombinOrder = opeCombinOrderService.getById(enter.getId());
        if (StringManaConstant.entityIsNull(opeCombinOrder)) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        if (!opeCombinOrder.getCombinStatus().equals(NewCombinOrderStatusEnums.DRAF.getValue())) {
            throw new SesWebRosException(ExceptionCodeEnums.STATUS_ILLEGAL.getCode(), ExceptionCodeEnums.STATUS_ILLEGAL.getMessage());
        }
        opeCombinOrder.setUpdatedBy(enter.getUserId());
        opeCombinOrder.setUpdatedTime(new Date());
        opeCombinOrderService.removeById(opeCombinOrder.getId());
        //????????????
        SaveOpTraceEnter saveOpTraceEnter = new SaveOpTraceEnter(null, opeCombinOrder.getId(), OrderTypeEnums.COMBIN_ORDER.getValue(), OrderOperationTypeEnums.DELETE.getValue(), opeCombinOrder.getRemark());
        BeanUtils.copyProperties(enter, saveOpTraceEnter);
        saveOpTraceEnter.setId(null);
        productionOrderTraceService.save(saveOpTraceEnter);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * @param enter
     * @Description
     * @Author: alex
     * @Date: 2020/11/13 10:15 ??????
     * @Param: enter
     * @Return: ????????????
     * @desc: ????????????
     */
    @Override
    public List<SpecificatGroupDataResult> scooterGroupData(GeneralEnter enter) {
        List<SpecificatGroupDataResult> result = productionAssemblyOrderServiceMapper.scooterGroupData();
        return CollectionUtils.isEmpty(result) ? new ArrayList<>() : result;
    }

    /**
     * @param enter
     * @Description
     * @Author: alex
     * @Date: 2020/11/13 10:16 ??????
     * @Param: enter
     * @Return: ColorDataResult
     * @desc: ????????????????????????
     */
    @Override
    public List<ColorDataResult> colorData(IdEnter enter) {
        List<ColorDataResult> result = productionAssemblyOrderServiceMapper.colorData(enter);
        return CollectionUtils.isEmpty(result) ? new ArrayList<>() : result;
    }

    /**
     * @param enter
     * @Description
     * @Author: alex
     * @Date: 2020/11/13 10:17 ??????
     * @Param: enter
     * @Return: CombinNameData
     * @desc: ?????????????????????
     */
    @Override
    public List<CombinNameData> combinNameData(CombinNameEnter enter) {
        List<CombinNameData> result = productionAssemblyOrderServiceMapper.combinNameData(enter);
        return CollectionUtils.isEmpty(result) ? new ArrayList<>() : result;
    }

    /**
     * @param enter
     * @return
     * @Author Aleks
     * @Description ????????????????????????????????????
     * @Date 2020/10/20 13:19
     * @Param [enter]
     */
    @Override
    public List<BomNameData> bomNoData(BomNoEnter enter) {
        List<BomNameData> result = productionAssemblyOrderServiceMapper.bomNoData(enter);
        return CollectionUtils.isEmpty(result) ? new ArrayList<>() : result;
    }

    private List<OpeCombinOrderCombinB> buildOpeCombinOrderCombinB(SaveAssemblyOrderEnter enter, List<SaveAssemblyProductListEnter> productEnterList, Long assemblyProductId,
                                                                   List<OpeCombinOrderCombinB> saveOpeCombinOrderCominBList) {
        List<OpeProductionCombinBom> opeProductionCombinBomList = opeProductionCombinBomService.listByIds(productEnterList.stream().map(SaveAssemblyProductListEnter::getId).collect(Collectors.toSet()));
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
            opeCombinOrderCombinB.setCombinName(opeProductionCombinBom.getEnName());
            opeCombinOrderCombinB.setCombinNo(opeProductionCombinBom.getBomNo());
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

    /**
     * @param id
     * @param response
     * @Description
     * @Author: alex
     * @Date: 2020/11/17 11:23 ??????
     * @Param: id, response
     * @Return: GeneralResult
     * @desc: ????????????
     */
    @Override
    public GeneralResult export(Long id, HttpServletResponse response) {
        OpeCombinOrder opeCombinOrder = opeCombinOrderService.getById(id);
        if (StringManaConstant.entityIsNull(opeCombinOrder)) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        List<ProductionCombinScooterExport> scooterExportList = new ArrayList<>();
        List<ProductionCombinAssemblyExport> combinExportList = new ArrayList<>();
        switch (opeCombinOrder.getCombinType()) {
            case 1:
                // scooter
                // ??????????????????????????????
                scooterExportList = productionAssemblyOrderServiceMapper.scooterExportList(id);
                if (CollectionUtils.isNotEmpty(scooterExportList)) {
                    for (ProductionCombinScooterExport scooterExport : scooterExportList) {
                        scooterExport.setOrderNo(opeCombinOrder.getCombinNo());
                        scooterExport.setProductType(ProductTypeEnums.SCOOTER.getMessage());
                    }
                }
            default:
                break;
            case 2:
                // combin
                combinExportList = productionAssemblyOrderServiceMapper.combinExportList(id);
                if (CollectionUtils.isNotEmpty(combinExportList)) {
                    for (ProductionCombinAssemblyExport combin : combinExportList) {
                        combin.setOrderNo(opeCombinOrder.getCombinNo());
                        combin.setProductType(ProductTypeEnums.COMBINATION.getMessage());
                    }
                }
                break;
        }
        try {
            // ??????????????????????????????
            response.setHeader("content-Type", "application/vnd.ms-excel");
            // ???????????????????????????
            response.setHeader("Content-Disposition", "attachment;filename=" + System.currentTimeMillis() + ".xls");
            // =========easypoi??????
            ExportParams exportParams = new ExportParams();
            Workbook workbook = null;
            switch (opeCombinOrder.getCombinType()) {
                case 1:
                    exportParams.setSheetName("??????");
                    workbook = ExcelExportUtil.exportExcel(exportParams, ProductionCombinScooterExport.class, scooterExportList);
                default:
                    break;
                case 2:
                    exportParams.setSheetName("?????????");
                    workbook = ExcelExportUtil.exportExcel(exportParams, ProductionCombinAssemblyExport.class, combinExportList);
            }
            workbook.write(response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("+++++++++++++++++++");
        }
        return new GeneralResult();
    }

    private List<OpeCombinOrderScooterB> buildOpeCombinOrderScooterB(SaveAssemblyOrderEnter enter, List<SaveAssemblyProductListEnter> productEnterList, Long assemblyProductId,
                                                                     List<OpeCombinOrderScooterB> saveOpeCombinOrderScooterBList) {
        List<Map<String, Object>> listMap = new ArrayList<>();
        for (SaveAssemblyProductListEnter scooterB : productEnterList) {
            Map<String, Object> map = new HashMap<>();
            map.put(OpeProductionScooterBom.COL_GROUP_ID, scooterB.getGroupId());
            map.put(OpeProductionScooterBom.COL_COLOR_ID, scooterB.getColorId());
            listMap.add(map);
        }
        //????????????Bom
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
        //???????????????
        OpeCombinOrder opeCombinOrder = new OpeCombinOrder();
        BeanUtils.copyProperties(enter, opeCombinOrder);
        opeCombinOrder.setDr(0);
        opeCombinOrder.setCombinStatus(NewCombinOrderStatusEnums.DRAF.getValue());
        opeCombinOrder.setCombinQty(productQty);
        opeCombinOrder.setUpdatedBy(enter.getUserId());
        opeCombinOrder.setUpdatedTime(new Date());
        return opeCombinOrder;
    }

    // ????????????
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public void materialPreparationFinish(Long combinOrderId, Long userId) {
        OpeCombinOrder opeCombinOrder = opeCombinOrderService.getById(combinOrderId);
        if (StringManaConstant.entityIsNull(opeCombinOrder)) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        if (opeCombinOrder.getCombinStatus().equals(NewCombinOrderStatusEnums.PREPARATION_COMPLETED.getValue())) {
            return;
        }
        if (!opeCombinOrder.getCombinStatus().equals(NewCombinOrderStatusEnums.PREPARED.getValue())) {
            throw new SesWebRosException(ExceptionCodeEnums.STATUS_ILLEGAL.getCode(), ExceptionCodeEnums.STATUS_ILLEGAL.getMessage());
        }
        opeCombinOrder.setCombinStatus(NewCombinOrderStatusEnums.PREPARATION_COMPLETED.getValue());
        opeCombinOrder.setUpdatedBy(userId);
        opeCombinOrder.setUpdatedTime(new Date());
        opeCombinOrderService.saveOrUpdate(opeCombinOrder);
        //????????????
        OrderStatusFlowEnter orderStatusFlowEnter = new OrderStatusFlowEnter(null, opeCombinOrder.getCombinStatus(), OrderTypeEnums.COMBIN_ORDER.getValue(), opeCombinOrder.getId(), opeCombinOrder.getRemark());
        orderStatusFlowEnter.setUserId(userId);
        orderStatusFlowEnter.setId(null);
        orderStatusFlowService.save(orderStatusFlowEnter);
    }

    /**
     * @return
     * @Author Aleks
     * @Description ??????RPS???????????????
     * @Date 2020/11/17 14:05
     * @Param [enter]
     **/
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult startCombin(IdEnter enter) {
        OpeCombinOrder opeCombinOrder = opeCombinOrderService.getById(enter.getId());
        if (StringManaConstant.entityIsNull(opeCombinOrder)) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        if (opeCombinOrder.getCombinStatus().equals(NewCombinOrderStatusEnums.ASSEMBLING.getValue())) {
            return new GeneralResult(enter.getRequestId());
        }
        /*if (!opeCombinOrder.getCombinStatus().equals(CombinOrderStatusEnums.TO_BE_ASSEMBLED.getValue())) {
            throw new SesWebRosException(ExceptionCodeEnums.STATUS_ILLEGAL.getCode(), ExceptionCodeEnums.STATUS_ILLEGAL.getMessage());
        }*/
        opeCombinOrder.setCombinStatus(NewCombinOrderStatusEnums.ASSEMBLING.getValue());
        opeCombinOrder.setUpdatedBy(enter.getUserId());
        opeCombinOrder.setUpdatedTime(new Date());
        opeCombinOrderService.saveOrUpdate(opeCombinOrder);
        //????????????
        OrderStatusFlowEnter orderStatusFlowEnter = new OrderStatusFlowEnter(null, opeCombinOrder.getCombinStatus(), OrderTypeEnums.COMBIN_ORDER.getValue(), opeCombinOrder.getId(), opeCombinOrder.getRemark());
        orderStatusFlowEnter.setUserId(enter.getUserId());
        orderStatusFlowEnter.setId(null);
        orderStatusFlowService.save(orderStatusFlowEnter);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * @return
     * @Author Aleks
     * @Description ??????RPS???????????????
     * @Date 2020/11/17 14:06
     * @Param [enter]
     **/
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult endCombin(IdEnter enter) {
        OpeCombinOrder opeCombinOrder = opeCombinOrderService.getById(enter.getId());
        if (StringManaConstant.entityIsNull(opeCombinOrder)) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        if (opeCombinOrder.getCombinStatus().equals(CombinOrderStatusEnums.WAIT_QC.getValue())) {
            return new GeneralResult(enter.getRequestId());
        }
        if (!opeCombinOrder.getCombinStatus().equals(CombinOrderStatusEnums.ASSEMBLING.getValue())) {
            throw new SesWebRosException(ExceptionCodeEnums.STATUS_ILLEGAL.getCode(), ExceptionCodeEnums.STATUS_ILLEGAL.getMessage());
        }
        opeCombinOrder.setCombinStatus(CombinOrderStatusEnums.WAIT_QC.getValue());
        opeCombinOrder.setUpdatedBy(enter.getUserId());
        opeCombinOrder.setUpdatedTime(new Date());
        opeCombinOrderService.saveOrUpdate(opeCombinOrder);
        //????????????
        OrderStatusFlowEnter orderStatusFlowEnter = new OrderStatusFlowEnter(null, opeCombinOrder.getCombinStatus(), OrderTypeEnums.COMBIN_ORDER.getValue(), opeCombinOrder.getId(), opeCombinOrder.getRemark());
        orderStatusFlowEnter.setUserId(enter.getUserId());
        orderStatusFlowEnter.setId(null);
        orderStatusFlowService.save(orderStatusFlowEnter);
        return new GeneralResult(enter.getRequestId());
    }


    /**
     * @return
     * @Author Aleks
     * @Description ??????RPS????????????????????????????????????
     * @Date 2020/11/17 14:23
     * @Param [enter]
     **/
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult startQc(IdEnter enter) {
        OpeCombinOrder opeCombinOrder = opeCombinOrderService.getById(enter.getId());
        if (StringManaConstant.entityIsNull(opeCombinOrder)) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        if (opeCombinOrder.getCombinStatus().equals(CombinOrderStatusEnums.INSPECTING.getValue())) {
            return new GeneralResult(enter.getRequestId());
        }
        if (!opeCombinOrder.getCombinStatus().equals(CombinOrderStatusEnums.WAIT_QC.getValue())) {
            throw new SesWebRosException(ExceptionCodeEnums.STATUS_ILLEGAL.getCode(), ExceptionCodeEnums.STATUS_ILLEGAL.getMessage());
        }
        opeCombinOrder.setCombinStatus(CombinOrderStatusEnums.INSPECTING.getValue());
        opeCombinOrder.setUpdatedBy(enter.getUserId());
        opeCombinOrder.setUpdatedTime(new Date());
        opeCombinOrderService.saveOrUpdate(opeCombinOrder);
        //????????????
        OrderStatusFlowEnter orderStatusFlowEnter = new OrderStatusFlowEnter(null, opeCombinOrder.getCombinStatus(), OrderTypeEnums.COMBIN_ORDER.getValue(), opeCombinOrder.getId(), opeCombinOrder.getRemark());
        orderStatusFlowEnter.setUserId(enter.getUserId());
        orderStatusFlowEnter.setId(null);
        orderStatusFlowService.save(orderStatusFlowEnter);
        return new GeneralResult(enter.getRequestId());
    }


    /**
     * @return
     * @Author Aleks
     * @Description ??????RPS????????????????????????????????????
     * @Date 2020/11/17 14:23
     * @Param [enter]
     **/
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult endQc(IdEnter enter) {
        OpeCombinOrder opeCombinOrder = opeCombinOrderService.getById(enter.getId());
        if (StringManaConstant.entityIsNull(opeCombinOrder)) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        if (opeCombinOrder.getCombinStatus().equals(CombinOrderStatusEnums.WAIT_IN_WH.getValue())) {
            return new GeneralResult(enter.getRequestId());
        }
        if (!opeCombinOrder.getCombinStatus().equals(CombinOrderStatusEnums.INSPECTING.getValue())) {
            throw new SesWebRosException(ExceptionCodeEnums.STATUS_ILLEGAL.getCode(), ExceptionCodeEnums.STATUS_ILLEGAL.getMessage());
        }
        opeCombinOrder.setCombinStatus(CombinOrderStatusEnums.WAIT_IN_WH.getValue());
        opeCombinOrder.setUpdatedBy(enter.getUserId());
        opeCombinOrder.setUpdatedTime(new Date());
        opeCombinOrderService.saveOrUpdate(opeCombinOrder);
        //????????????
        OrderStatusFlowEnter orderStatusFlowEnter = new OrderStatusFlowEnter(null, opeCombinOrder.getCombinStatus(), OrderTypeEnums.COMBIN_ORDER.getValue(), opeCombinOrder.getId(), opeCombinOrder.getRemark());
        orderStatusFlowEnter.setUserId(enter.getUserId());
        orderStatusFlowEnter.setId(null);
        orderStatusFlowService.save(orderStatusFlowEnter);
        return new GeneralResult(enter.getRequestId());
    }


    /**
     * @return
     * @Author Aleks
     * @Description ?????????????????????????????? ???????????????????????????  ??????????????????????????????
     * @Date 2020/11/17 14:38
     * @Param [productionPurchaseId, inWhId, userId]
     **/
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public void statusToPartWhOrAllInWh(Long combinId, Long inWhId, Long userId) {
        OpeCombinOrder opeCombinOrder = opeCombinOrderService.getById(combinId);
        if (StringManaConstant.entityIsNull(opeCombinOrder)) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        if (!opeCombinOrder.getCombinStatus().equals(CombinOrderStatusEnums.WAIT_IN_WH.getValue()) && !opeCombinOrder.getCombinStatus().equals(CombinOrderStatusEnums.PART_IN_WH.getValue())) {
            throw new SesWebRosException(ExceptionCodeEnums.STATUS_ILLEGAL.getCode(), ExceptionCodeEnums.STATUS_ILLEGAL.getMessage());
        }
        if (checkCombinStatusChange(opeCombinOrder, inWhId)) {
            // ?????????????????????
            opeCombinOrder.setCombinStatus(CombinOrderStatusEnums.ALREADY_IN_WHOUSE.getValue());
        } else {
            // ????????????????????????
            opeCombinOrder.setCombinStatus(CombinOrderStatusEnums.PART_IN_WH.getValue());
        }
        opeCombinOrder.setUpdatedBy(userId);
        opeCombinOrder.setUpdatedTime(new Date());
        opeCombinOrderService.saveOrUpdate(opeCombinOrder);
        //????????????
        OrderStatusFlowEnter orderStatusFlowEnter = new OrderStatusFlowEnter(null, opeCombinOrder.getCombinStatus(), OrderTypeEnums.COMBIN_ORDER.getValue(), opeCombinOrder.getId(), opeCombinOrder.getRemark());
        orderStatusFlowEnter.setUserId(userId);
        orderStatusFlowEnter.setId(null);
        orderStatusFlowService.save(orderStatusFlowEnter);
    }


    // ??????????????????????????????????????????
    public boolean checkCombinStatusChange(OpeCombinOrder opeCombinOrder, Long inWhId) {
        boolean flag = true;
        // 1??????????????????????????????????????????????????? ?????????????????????????????????????????????
        QueryWrapper<OpeInWhouseOrder> inWhouseOrderQueryWrapper = new QueryWrapper<>();
        inWhouseOrderQueryWrapper.eq(OpeInWhouseOrder.COL_RELATION_ORDER_ID, opeCombinOrder.getId());
        inWhouseOrderQueryWrapper.ne(OpeInWhouseOrder.COL_ID, inWhId);
        inWhouseOrderQueryWrapper.lt(OpeInWhouseOrder.COL_IN_WH_STATUS, NewInWhouseOrderStatusEnum.ALREADY_IN_WHOUSE.getValue());
        int inWhNum = opeInWhouseOrderService.count(inWhouseOrderQueryWrapper);
        if (inWhNum > 0) {
            flag = false;
            return flag;
        }
        // ????????????????????????????????? ??????????????????
        switch (opeCombinOrder.getCombinType()) {
            case 1:
                // scooter
                QueryWrapper<OpeCombinOrderScooterB> scooterBQueryWrapper = new QueryWrapper<>();
                scooterBQueryWrapper.eq(OpeCombinOrderScooterB.COL_COMBIN_ID, opeCombinOrder.getId());
                List<OpeCombinOrderScooterB> scooterBList = opeCombinOrderScooterBService.list(scooterBQueryWrapper);
                if (CollectionUtils.isNotEmpty(scooterBList)) {
                    // scooterBList?????????????????????????????????
                    Map<String, List<OpeCombinOrderScooterB>> allScooterMap = new HashMap<>();
                    for (OpeCombinOrderScooterB scooterB : scooterBList) {
                        String key = scooterB.getGroupId() + scooterB.getColorId() + "";
                        List<OpeCombinOrderScooterB> orderScooterBS = allScooterMap.get(key);
                        if (CollectionUtils.isEmpty(orderScooterBS)) {
                            orderScooterBS = new ArrayList<>();
                            allScooterMap.put(key, orderScooterBS);
                        }
                        orderScooterBS.add(scooterB);
                    }
                    // ???????????????????????????????????????????????????????????????????????????????????????
                    List<OpeInWhouseScooterB> inWhouseScooterBList = inWhouseOrderServiceMapper.inWhouseScooterList(opeCombinOrder.getId());
                    if (CollectionUtils.isEmpty(inWhouseScooterBList)) {
                        flag = false;
                        return flag;
                    }
                    // inWhouseScooterBList?????????????????????????????????
                    Map<String, List<OpeInWhouseScooterB>> inWhScooterMap = new HashMap<>();
                    for (OpeInWhouseScooterB scooter : inWhouseScooterBList) {
                        String key = scooter.getGroupId() + scooter.getColorId() + "";
                        List<OpeInWhouseScooterB> orderScooterS = inWhScooterMap.get(key);
                        if (CollectionUtils.isEmpty(orderScooterS)) {
                            orderScooterS = new ArrayList<>();
                            inWhScooterMap.put(key, orderScooterS);
                        }
                        orderScooterS.add(scooter);
                    }
                    if (allScooterMap.size() > inWhScooterMap.size()) {
                        flag = false;
                        return flag;
                    }
                    for (String scooterKey1 : allScooterMap.keySet()) {
                        int combinScooterNum = allScooterMap.get(scooterKey1).stream().mapToInt(OpeCombinOrderScooterB::getQty).sum();
                        for (String scooterKey2 : inWhScooterMap.keySet()) {
                            if (scooterKey1.equals(scooterKey2)) {
                                int inWhScooterNum = inWhScooterMap.get(scooterKey2).stream().mapToInt(OpeInWhouseScooterB::getInWhQty).sum();
                                if (combinScooterNum > inWhScooterNum) {
                                    flag = false;
                                    return flag;
                                }
                            }
                        }
                    }
                }
            default:
                break;
            case 2:
                // combin
                // 2?????????????????????????????????
                QueryWrapper<OpeCombinOrderCombinB> combinBQueryWrapper = new QueryWrapper<>();
                combinBQueryWrapper.eq(OpeCombinOrderCombinB.COL_COMBIN_ID, opeCombinOrder.getId());
                List<OpeCombinOrderCombinB> combinBList = opeCombinOrderCombinBService.list(combinBQueryWrapper);
                if (CollectionUtils.isNotEmpty(combinBList)) {
                    // combinBList??????????????????id????????????
                    Map<Long, List<OpeCombinOrderCombinB>> allCombinBMap = combinBList.stream().collect(Collectors.groupingBy(OpeCombinOrderCombinB::getProductionCombinBomId));
                    // ???????????????????????????????????????????????????????????????????????????????????????
                    List<OpeInWhouseCombinB> inWhouseCombinBList = inWhouseOrderServiceMapper.inWhouseCombinList(opeCombinOrder.getId());
                    if (CollectionUtils.isEmpty(inWhouseCombinBList)) {
                        flag = false;
                        return flag;
                    }
                    // inWhouseCombinBMap??????????????????id????????????
                    Map<Long, List<OpeInWhouseCombinB>> inWhouseCombinBMap = inWhouseCombinBList.stream().collect(Collectors.groupingBy(OpeInWhouseCombinB::getProductionCombinBomId));
                    for (Long key1 : allCombinBMap.keySet()) {
                        int combinNum = allCombinBMap.get(key1).stream().mapToInt(OpeCombinOrderCombinB::getQty).sum();
                        for (Long key2 : inWhouseCombinBMap.keySet()) {
                            if (key1.equals(key2)) {
                                int inWhCombinNum = inWhouseCombinBMap.get(key2).stream().mapToInt(OpeInWhouseCombinB::getInWhQty).sum();
                                if (combinNum > inWhCombinNum) {
                                    flag = false;
                                    return flag;
                                }
                            }
                        }
                    }
                }
                break;
        }
        return flag;
    }
}
