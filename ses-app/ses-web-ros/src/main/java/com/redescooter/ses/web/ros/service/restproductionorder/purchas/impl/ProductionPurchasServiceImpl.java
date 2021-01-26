package com.redescooter.ses.web.ros.service.restproductionorder.purchas.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.redescooter.ses.api.common.enums.restproductionorder.NewInWhouseOrderStatusEnum;
import com.redescooter.ses.api.common.enums.restproductionorder.OrderOperationTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.OrderTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.PaymentTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.ProductTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.productionpurchas.NewProductionPurchasEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.productionpurchas.ProductionPurchasEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.qc.QcOrderStatusEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.qc.QcTypeEnums;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.DateUtil;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.dao.base.OpeCombinOrderCombinBMapper;
import com.redescooter.ses.web.ros.dao.base.OpeCombinOrderMapper;
import com.redescooter.ses.web.ros.dao.base.OpeCombinOrderScooterBMapper;
import com.redescooter.ses.web.ros.dao.base.OpeOutWhCombinBMapper;
import com.redescooter.ses.web.ros.dao.base.OpeOutWhPartsBMapper;
import com.redescooter.ses.web.ros.dao.base.OpeOutWhScooterBMapper;
import com.redescooter.ses.web.ros.dao.base.OpeOutWhouseOrderMapper;
import com.redescooter.ses.web.ros.dao.base.OpeProductionPurchasePartsBMapper;
import com.redescooter.ses.web.ros.dao.base.OpeProductionScooterBomMapper;
import com.redescooter.ses.web.ros.dao.qc.OpeQcOrderMapper;
import com.redescooter.ses.web.ros.dao.restproductionorder.InWhouseOrderServiceMapper;
import com.redescooter.ses.web.ros.dao.restproductionorder.ProductionPurchasServiceMapper;
import com.redescooter.ses.web.ros.dm.OpeCombinOrder;
import com.redescooter.ses.web.ros.dm.OpeCombinOrderCombinB;
import com.redescooter.ses.web.ros.dm.OpeCombinOrderScooterB;
import com.redescooter.ses.web.ros.dm.OpeInWhouseOrder;
import com.redescooter.ses.web.ros.dm.OpeInWhousePartsB;
import com.redescooter.ses.web.ros.dm.OpeOutWhCombinB;
import com.redescooter.ses.web.ros.dm.OpeOutWhPartsB;
import com.redescooter.ses.web.ros.dm.OpeOutWhScooterB;
import com.redescooter.ses.web.ros.dm.OpeOutWhouseOrder;
import com.redescooter.ses.web.ros.dm.OpeProductionCombinBom;
import com.redescooter.ses.web.ros.dm.OpeProductionPurchaseOrder;
import com.redescooter.ses.web.ros.dm.OpeProductionPurchasePartsB;
import com.redescooter.ses.web.ros.dm.OpeProductionScooterBom;
import com.redescooter.ses.web.ros.dm.OpeQcCombinB;
import com.redescooter.ses.web.ros.dm.OpeQcOrder;
import com.redescooter.ses.web.ros.dm.OpeQcPartsB;
import com.redescooter.ses.web.ros.dm.OpeQcScooterB;
import com.redescooter.ses.web.ros.dm.OpeSupplier;
import com.redescooter.ses.web.ros.dm.OpeSysStaff;
import com.redescooter.ses.web.ros.enums.distributor.DelStatusEnum;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpeInWhouseOrderService;
import com.redescooter.ses.web.ros.service.base.OpeProductionCombinBomService;
import com.redescooter.ses.web.ros.service.base.OpeProductionPurchaseOrderService;
import com.redescooter.ses.web.ros.service.base.OpeProductionPurchasePartsBService;
import com.redescooter.ses.web.ros.service.base.OpeQcCombinBService;
import com.redescooter.ses.web.ros.service.base.OpeQcPartsBService;
import com.redescooter.ses.web.ros.service.base.OpeQcScooterBService;
import com.redescooter.ses.web.ros.service.base.OpeSupplierService;
import com.redescooter.ses.web.ros.service.base.OpeSysStaffService;
import com.redescooter.ses.web.ros.service.restproductionorder.number.OrderNumberService;
import com.redescooter.ses.web.ros.service.restproductionorder.orderflow.OrderStatusFlowService;
import com.redescooter.ses.web.ros.service.restproductionorder.purchas.ProductionPurchasService;
import com.redescooter.ses.web.ros.service.restproductionorder.trace.ProductionOrderTraceService;
import com.redescooter.ses.web.ros.vo.bo.PartDetailDto;
import com.redescooter.ses.web.ros.vo.restproductionorder.AssociatedOrderResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.SupplierListResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.SupplierPrincipaleResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.number.OrderNumberEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.optrace.ListByBussIdEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.optrace.SaveOpTraceEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.orderflow.OrderStatusFlowEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.purchass.ProductionPurchasDetailResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.purchass.ProductionPurchasListEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.purchass.ProductionPurchasListResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.purchass.PurchasDetailProductListResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.purchass.PurchasPartListEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.purchass.PurchasPartListResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.purchass.SaveProductionPurchasEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.purchass.SavePurchasPaymentEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.purchass.SavePurchasProductEnter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @ClassName:ProductionPurchasServiceImpl
 * @description: ProductionPurchasServiceImpl
 * @author: Alex
 * @Version：1.3
 * @create: 2020/11/11 11:43
 */
@Slf4j
@Service
public class ProductionPurchasServiceImpl implements ProductionPurchasService {

    @Autowired
    private OpeSupplierService opeSupplierService;

    @Autowired
    private OpeProductionPurchaseOrderService opeProductionPurchaseOrderService;

    @Autowired
    private OpeProductionPurchasePartsBMapper opeProductionPurchasePartsBMapper;

    @Autowired
    private ProductionPurchasServiceMapper productionPurchasServiceMapper;

    @Autowired
    private OrderStatusFlowService orderStatusFlowService;

    @Autowired
    private ProductionOrderTraceService productionOrderTraceService;

    @Autowired
    private OpeInWhouseOrderService opeInWhouseOrderService;

    @Autowired
    private OrderNumberService orderNumberService;

    @Autowired
    private OpeProductionPurchasePartsBService opeProductionPurchasePartsBService;

    @Autowired
    private OpeSysStaffService opeSysStaffService;

    @Autowired
    private InWhouseOrderServiceMapper inWhouseOrderServiceMapper;

    @Autowired
    private OpeQcOrderMapper opeQcOrderMapper;

    @Autowired
    private OpeQcPartsBService opeQcPartsBService;

    @Autowired
    private OpeQcScooterBService opeQcScooterBService;

    @Autowired
    private OpeQcCombinBService opeQcCombinBService;

    @Autowired
    private OpeCombinOrderMapper opeCombinOrderMapper;

    @Autowired
    private OpeCombinOrderScooterBMapper opeCombinOrderScooterBMapper;

    @Autowired
    private OpeCombinOrderCombinBMapper opeCombinOrderCombinBMapper;

    @Autowired
    private OpeOutWhouseOrderMapper opeOutWhouseOrderMapper;

    @Autowired
    private OpeOutWhScooterBMapper opeOutWhScooterBMapper;

    @Autowired
    private OpeOutWhCombinBMapper opeOutWhCombinBMapper;

    @Autowired
    private OpeOutWhPartsBMapper opeOutWhPartsBMapper;

    @Autowired
    private OpeProductionScooterBomMapper opeProductionScooterBomMapper;

    @Autowired
    private OpeProductionCombinBomService opeProductionCombinBomService;

    @Reference
    private IdAppService idAppService;

    /**
     * @param enter
     * @Description
     * @Author: alex
     * @Date: 2020/11/10 2:52 下午
     * @Param: enter
     * @Return: ProductionPurchasListResult
     * @desc: 列表
     */
    @Override
    public PageResult<ProductionPurchasListResult> list(ProductionPurchasListEnter enter) {
        int count = productionPurchasServiceMapper.listCount(enter);
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        return PageResult.create(enter, count, productionPurchasServiceMapper.list(enter));
    }

    /**
     * @param enter
     * @Description
     * @Author: alex
     * @Date: 2020/11/10 3:06 下午
     * @Param: enter
     * @Return: ProductionPurchasDetailResult
     * @desc: 详情
     */
    @Override
    public ProductionPurchasDetailResult detail(IdEnter enter) {
        ProductionPurchasDetailResult productionPurchasDetailResult = productionPurchasServiceMapper.detail(enter);
        if (Objects.isNull(productionPurchasDetailResult)) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        OpeProductionPurchaseOrder opeProductionPurchaseOrder = opeProductionPurchaseOrderService.getById(enter.getId());
        productionPurchasDetailResult.setPaymentDate(new SavePurchasPaymentEnter(opeProductionPurchaseOrder.getPaymentType(), opeProductionPurchaseOrder.getPlannedPaymentTime(),
                opeProductionPurchaseOrder.getAmountType(), opeProductionPurchaseOrder.getPaymentDay(), opeProductionPurchaseOrder.getPrePayRatio().intValue(), opeProductionPurchaseOrder.getPayAmount(), opeProductionPurchaseOrder.getPaymentTime()));
        productionPurchasDetailResult.setProductList(this.detailProductList(enter));
        productionPurchasDetailResult.setOperatingDynamicsList(productionOrderTraceService.listByBussId(new ListByBussIdEnter(enter.getId(), OrderTypeEnums.FACTORY_PURCHAS.getValue())));
        productionPurchasDetailResult.setAssociatedOrderResultList(this.associatedOrder(enter));
        return productionPurchasDetailResult;
    }

    /**
     * @param enter
     * @Description
     * @Author: alex
     * @Date: 2020/11/11 4:18 下午
     * @Param: enter
     * @Return: List<PurchasDetailProductListResult>
     * @desc: 产品详情
     */
    @Override
    public List<PurchasDetailProductListResult> detailProductList(IdEnter enter) {
        List<PurchasDetailProductListResult> resultList = productionPurchasServiceMapper.detailProductList(enter);
        return CollectionUtils.isEmpty(resultList) ? new ArrayList<>() : resultList;
    }

    /**
     * @param enter
     * @Description
     * @Author: alex
     * @Date: 2020/11/11 5:02 下午
     * @Param: enter
     * @Return: List<AssociatedOrderResult>
     * @desc: 关联订单
     */
    @Override
    public List<AssociatedOrderResult> associatedOrder(IdEnter enter) {
        List<AssociatedOrderResult> resultList = new ArrayList<>();
        List<OpeInWhouseOrder> opeInWhouseOrderList = opeInWhouseOrderService.list(new LambdaQueryWrapper<OpeInWhouseOrder>()
                .eq(OpeInWhouseOrder::getRelationOrderId, enter.getId())
                .eq(OpeInWhouseOrder::getRelationOrderType, OrderTypeEnums.FACTORY_PURCHAS.getValue()));
        if (CollectionUtils.isNotEmpty(opeInWhouseOrderList)) {
            opeInWhouseOrderList.forEach(item -> {
                resultList.add(new AssociatedOrderResult(item.getId(), item.getInWhNo(), OrderTypeEnums.FACTORY_INBOUND.getValue(), item.getCreatedTime(), null));
            });
            return resultList;
        }
        return new ArrayList<>();
    }

    /**
     * @param enter
     * @Description
     * @Author: alex
     * @Date: 2020/11/10 4:08 下午
     * @Param: enter
     * @Return: GeneralResult
     * @desc: 保存采购单
     */
    @Transactional
    @Override
    public GeneralResult save(SaveProductionPurchasEnter enter) {
        List<SavePurchasProductEnter> productList = new ArrayList<>();
        SavePurchasPaymentEnter paymentEnter = null;
        try {
            paymentEnter = JSON.parseObject(enter.getPaymentDate(), SavePurchasPaymentEnter.class);
            productList.addAll(JSON.parseArray(enter.getSt(), SavePurchasProductEnter.class));
        } catch (Exception e) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }
        if (CollectionUtils.isEmpty(productList) || Objects.isNull(paymentEnter)) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }

        //校验json 数据
        List<PartDetailDto> PartDetailDtoList = checkJsonDate(productList, paymentEnter);

        Long purchasId = idAppService.getId(SequenceName.OPE_PRODUCTION_PURCHASE_ORDER);

        Long totalPrice = 0L;

        //子订单保存
        List<OpeProductionPurchasePartsB> saveOpeProductionPurchasePartsBList = new ArrayList<>();
        for (SavePurchasProductEnter item : productList) {
            PartDetailDto partDetailDto = PartDetailDtoList.stream().filter(part -> item.getId().equals(part.getPartId())).findFirst().orElse(null);
            //封装子单据
            OpeProductionPurchasePartsB opeProductionPurchasePartsB = buildOpeProductionPurchasePartsB(enter, (enter.getId() == null || enter.getId() == 0) ? purchasId : enter.getId(), item, partDetailDto);
            saveOpeProductionPurchasePartsBList.add(opeProductionPurchasePartsB);
            Long price = partDetailDto.getPrice().multiply(new BigDecimal(item.getQty())).longValue();
            totalPrice += price;
        }

        SaveOpTraceEnter saveOpTraceEnter = null;
        OpeProductionPurchaseOrder opeProductionPurchaseOrder = buildOpeProductionPurchaseOrder(enter, productList, paymentEnter, new BigDecimal(totalPrice));
        if (enter.getId() == null || enter.getId() == 0) {
            opeProductionPurchaseOrder.setId(purchasId);
            opeProductionPurchaseOrder.setCreatedBy(enter.getUserId());
            opeProductionPurchaseOrder.setCreatedTime(new Date());
            opeProductionPurchaseOrder.setPurchaseNo(orderNumberService.generateOrderNo(new OrderNumberEnter(OrderTypeEnums.FACTORY_PURCHAS.getValue())));

            //订单节点
            OrderStatusFlowEnter orderStatusFlowEnter = new OrderStatusFlowEnter(null, opeProductionPurchaseOrder.getPurchaseStatus(), OrderTypeEnums.FACTORY_PURCHAS.getValue(), opeProductionPurchaseOrder.getId(), null);
            BeanUtils.copyProperties(enter, orderStatusFlowEnter);
            orderStatusFlowEnter.setId(null);
            orderStatusFlowService.save(orderStatusFlowEnter);

            //操作动态
            saveOpTraceEnter = new SaveOpTraceEnter(null, opeProductionPurchaseOrder.getId(), OrderTypeEnums.FACTORY_PURCHAS.getValue(), OrderOperationTypeEnums.CREATE.getValue(), enter.getRemark());
        } else {
            //删除子单据
            opeProductionPurchasePartsBService.remove(new LambdaQueryWrapper<OpeProductionPurchasePartsB>().eq(OpeProductionPurchasePartsB::getProductionPurchaseId, enter.getId()));
            //操作动态
            saveOpTraceEnter = new SaveOpTraceEnter(null, opeProductionPurchaseOrder.getId(), OrderTypeEnums.FACTORY_PURCHAS.getValue(), OrderOperationTypeEnums.EDIT.getValue(), enter.getRemark());
        }
        opeProductionPurchaseOrder.setUpdatedBy(enter.getUserId());
        opeProductionPurchaseOrder.setUpdatedTime(new Date());
        opeProductionPurchaseOrderService.saveOrUpdate(opeProductionPurchaseOrder);
        opeProductionPurchasePartsBService.saveBatch(saveOpeProductionPurchasePartsBList);

        //操作动态
        BeanUtils.copyProperties(enter, saveOpTraceEnter);
        saveOpTraceEnter.setId(null);
        productionOrderTraceService.save(saveOpTraceEnter);
        return new GeneralResult(enter.getRequestId());
    }

    private OpeProductionPurchasePartsB buildOpeProductionPurchasePartsB(SaveProductionPurchasEnter enter, Long purchasId, SavePurchasProductEnter item, PartDetailDto partDetailDto) {
        OpeProductionPurchasePartsB opeProductionPurchasePartsB = new OpeProductionPurchasePartsB();
        opeProductionPurchasePartsB.setId(idAppService.getId(SequenceName.OPE_PRODUCTION_PURCHASE_PARTS_B));
        opeProductionPurchasePartsB.setDr(0);
        opeProductionPurchasePartsB.setProductionPurchaseId(purchasId);
        opeProductionPurchasePartsB.setPartsId(partDetailDto.getPartId());
        opeProductionPurchasePartsB.setPartsName(partDetailDto.getPartName());
        opeProductionPurchasePartsB.setPartsNo(partDetailDto.getPartN());
        opeProductionPurchasePartsB.setPartsType(Integer.valueOf(partDetailDto.getProductType()));
        opeProductionPurchasePartsB.setQty(item.getQty());
        opeProductionPurchasePartsB.setUnitPrice(partDetailDto.getPrice());
        opeProductionPurchasePartsB.setRemark(enter.getRemark());
        opeProductionPurchasePartsB.setCreatedBy(enter.getUserId());
        opeProductionPurchasePartsB.setCreatedTime(new Date());
        opeProductionPurchasePartsB.setUpdatedBy(enter.getUserId());
        opeProductionPurchasePartsB.setUpdatedTime(new Date());
        return opeProductionPurchasePartsB;
    }

    private OpeProductionPurchaseOrder buildOpeProductionPurchaseOrder(SaveProductionPurchasEnter enter, List<SavePurchasProductEnter> productList, SavePurchasPaymentEnter paymentEnter, BigDecimal totalPrice) {
        if (StringUtils.isEmpty(enter.getFactoryPrincipalName()) && enter.getFactoryId() != null && enter.getFactoryId() != 0) {
            OpeSupplier opeSupplier = opeSupplierService.getById(enter.getFactoryId());
            if (Objects.isNull(opeSupplier)) {
                throw new SesWebRosException(ExceptionCodeEnums.FACTORY_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.FACTORY_IS_NOT_EXIST.getMessage());
            }
            enter.setFactoryPrincipalName(opeSupplier.getContactFullName());
        }
        if (StringUtils.isEmpty(enter.getDockingUserName()) && enter.getDockingUser() != null && enter.getDockingUser() != 0) {
            OpeSysStaff opeSysStaff = opeSysStaffService.getById(enter.getDockingUser());
            if (opeSysStaff == null) {
                throw new SesWebRosException(ExceptionCodeEnums.EMPLOYEE_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.EMPLOYEE_IS_NOT_EXIST.getMessage());
            }
            enter.setDockingUserName(opeSysStaff.getFirstName() + " " + opeSysStaff.getLastName());
        }
        if (StringUtils.isEmpty(enter.getConsigneeUserName()) && enter.getConsigneeUser() != null && enter.getConsigneeUser() != 0) {
            OpeSysStaff opeSysStaff = opeSysStaffService.getById(enter.getConsigneeUser());
            if (opeSysStaff == null) {
                throw new SesWebRosException(ExceptionCodeEnums.EMPLOYEE_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.EMPLOYEE_IS_NOT_EXIST.getMessage());
            }
            enter.setConsigneeUserName(opeSysStaff.getFirstName() + " " + opeSysStaff.getLastName());
        }

        OpeProductionPurchaseOrder opeProductionPurchaseOrder = new OpeProductionPurchaseOrder();
        BeanUtils.copyProperties(enter, opeProductionPurchaseOrder);
        opeProductionPurchaseOrder.setDr(0);
        opeProductionPurchaseOrder.setPurchaseStatus(NewProductionPurchasEnums.DRAFT.getValue());
        opeProductionPurchaseOrder.setPurchaseQty(productList.stream().mapToInt(SavePurchasProductEnter::getQty).sum());
        opeProductionPurchaseOrder.setPurchaseContract(enter.getContract());
        opeProductionPurchaseOrder.setPaymentType(paymentEnter.getPaymentType());
        opeProductionPurchaseOrder.setPlannedPaymentTime(paymentEnter.getDate());
        opeProductionPurchaseOrder.setPaymentDay(paymentEnter.getDays());
        if (paymentEnter.getDays() != null && paymentEnter.getDate() != null) {
            opeProductionPurchaseOrder.setPaymentTime(DateUtil.addDays(paymentEnter.getDate(), paymentEnter.getDays()));
        }
        opeProductionPurchaseOrder.setPrePayRatio(new BigDecimal(paymentEnter.getPercentage()));
        opeProductionPurchaseOrder.setPurchaseAmount(totalPrice);
        opeProductionPurchaseOrder.setAmountType(paymentEnter.getAmountType());

        if (PaymentTypeEnums.PREPAYMENTS.getValue().equals(paymentEnter.getPaymentType()) && paymentEnter.getAmount() != null) {
            if (totalPrice.subtract(paymentEnter.getAmount()).longValue() < 0) {
                throw new SesWebRosException(ExceptionCodeEnums.PAY_AMOUNT_IS_FALSE.getCode(), ExceptionCodeEnums.PAY_AMOUNT_IS_FALSE.getMessage());
            }
            opeProductionPurchaseOrder.setPayAmount(paymentEnter.getAmount());
        }
        return opeProductionPurchaseOrder;
    }

    @Override
    public PageResult<PurchasPartListResult> purchasPartList(PurchasPartListEnter enter) {
        int count = productionPurchasServiceMapper.purchasPartListCount(enter);
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        return PageResult.create(enter, count, productionPurchasServiceMapper.purchasPartList(enter));
    }

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/11/11 7:15 下午
     * @Param: productList, paymentEnter
     * @Return: List<OpeProductionParts>
     * @desc: 校验json 数据
     */
    private List<PartDetailDto> checkJsonDate(List<SavePurchasProductEnter> productList, SavePurchasPaymentEnter paymentEnter) {
        List<PartDetailDto> PartDetailDtoList = productionPurchasServiceMapper.partDetailList(productList.stream().map(SavePurchasProductEnter::getId).collect(Collectors.toSet()));
        productList.forEach(item -> {
            PartDetailDto partDetailDto = PartDetailDtoList.stream().filter(product -> item.getId().equals(product.getPartId())).findFirst().orElse(null);
            if (Objects.isNull(partDetailDto)) {
                throw new SesWebRosException(ExceptionCodeEnums.PART_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PART_IS_NOT_EXIST.getMessage());
            }
            if (Objects.isNull(item.getQty()) || item.getQty().equals(0)) {
                throw new SesWebRosException(ExceptionCodeEnums.QTY_IS_ILLEGAL.getCode(), ExceptionCodeEnums.QTY_IS_ILLEGAL.getMessage());
            }
        });
        return PartDetailDtoList;
    }

    /**
     * @param enter
     * @Description
     * @Author: alex
     * @Date: 2020/11/11 3:21 下午
     * @Param: enter
     * @Return: SupplierListResult
     * @desc: 供应商列表
     */
    @Override
    public List<SupplierListResult> supplierList(GeneralEnter enter) {
        List<SupplierListResult> resultList = new ArrayList<>();
        List<OpeSupplier> opeSupplierList = opeSupplierService.list();
        if (CollectionUtils.isNotEmpty(opeSupplierList)) {
            opeSupplierList.forEach(item -> {
                List<SupplierPrincipaleResult> supplierList = new ArrayList<>();
                supplierList.add(new SupplierPrincipaleResult(item.getId(), item.getContactFullName(), item.getContactPhoneCountryCode(), item.getContactPhone()));
                resultList.add(new SupplierListResult(item.getId(), item.getSupplierName(), supplierList));
            });
        }
        return resultList;
    }

    /**
     * @param enter
     * @Description
     * @Author: alexx
     * @Date: 2020/11/12 2:51 下午
     * @Param: enter
     * @Return: GeneralResult
     * @desc: 关闭订单
     */
    @Override
    public GeneralResult close(IdEnter enter) {
        OpeProductionPurchaseOrder opeProductionPurchaseOrder = opeProductionPurchaseOrderService.getById(enter.getId());
        if (Objects.isNull(opeProductionPurchaseOrder)) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        /*if (!opeProductionPurchaseOrder.getPurchaseStatus().equals(ProductionPurchasEnums.HAS_BEEN_STORED.getValue())) {
            throw new SesWebRosException(ExceptionCodeEnums.STATUS_ILLEGAL.getCode(), ExceptionCodeEnums.STATUS_ILLEGAL.getMessage());
        }*/
        opeProductionPurchaseOrder.setPurchaseStatus(NewProductionPurchasEnums.FINISHED.getValue());
        opeProductionPurchaseOrder.setUpdatedBy(enter.getId());
        opeProductionPurchaseOrder.setUpdatedTime(new Date());
        opeProductionPurchaseOrderService.updateById(opeProductionPurchaseOrder);
        //操作动态
        SaveOpTraceEnter saveOpTraceEnter = new SaveOpTraceEnter(null, opeProductionPurchaseOrder.getId(), OrderTypeEnums.FACTORY_PURCHAS.getValue(), OrderOperationTypeEnums.CLOSE.getValue(), null);
        BeanUtils.copyProperties(enter, saveOpTraceEnter);
        saveOpTraceEnter.setId(null);
        productionOrderTraceService.save(saveOpTraceEnter);

        //订单节点
        OrderStatusFlowEnter orderStatusFlowEnter = new OrderStatusFlowEnter(null, opeProductionPurchaseOrder.getPurchaseStatus(), OrderTypeEnums.FACTORY_PURCHAS.getValue(), opeProductionPurchaseOrder.getId(), null);
        BeanUtils.copyProperties(enter, orderStatusFlowEnter);
        orderStatusFlowEnter.setId(null);
        orderStatusFlowService.save(orderStatusFlowEnter);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * @param enter
     * @Description
     * @Author: alex
     * @Date: 2020/11/12 2:52 下午
     * @Param: enter
     * @Return: GeneralResult
     * @desc: 取消订单
     */
    @Override
    public GeneralResult cancel(IdEnter enter) {
        OpeProductionPurchaseOrder opeProductionPurchaseOrder = opeProductionPurchaseOrderService.getById(enter.getId());
        if (Objects.isNull(opeProductionPurchaseOrder)) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        if (!opeProductionPurchaseOrder.getPurchaseStatus().equals(NewProductionPurchasEnums.PURCHASING.getValue())) {
            throw new SesWebRosException(ExceptionCodeEnums.STATUS_ILLEGAL.getCode(), ExceptionCodeEnums.STATUS_ILLEGAL.getMessage());
        }
        opeProductionPurchaseOrder.setPurchaseStatus(NewProductionPurchasEnums.FINISHED.getValue());
        opeProductionPurchaseOrder.setUpdatedBy(enter.getId());
        opeProductionPurchaseOrder.setUpdatedTime(new Date());
        opeProductionPurchaseOrderService.updateById(opeProductionPurchaseOrder);
        //操作动态
        SaveOpTraceEnter saveOpTraceEnter = new SaveOpTraceEnter(null, opeProductionPurchaseOrder.getId(), OrderTypeEnums.FACTORY_PURCHAS.getValue(), OrderOperationTypeEnums.CANCEL.getValue(), null);
        BeanUtils.copyProperties(enter, saveOpTraceEnter);
        saveOpTraceEnter.setId(null);
        productionOrderTraceService.save(saveOpTraceEnter);

        //订单节点
        OrderStatusFlowEnter orderStatusFlowEnter = new OrderStatusFlowEnter(null, opeProductionPurchaseOrder.getPurchaseStatus(), OrderTypeEnums.FACTORY_PURCHAS.getValue(), opeProductionPurchaseOrder.getId(),
                null);
        BeanUtils.copyProperties(enter, orderStatusFlowEnter);
        orderStatusFlowEnter.setId(null);
        orderStatusFlowService.save(orderStatusFlowEnter);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * @param enter
     * @Description
     * @Author: alex
     * @Date: 2020/11/12 2:53 下午
     * @Param: enter
     * @Return: GeneralResult
     * @desc: 删除
     */
    @Override
    public GeneralResult delete(IdEnter enter) {
        OpeProductionPurchaseOrder opeProductionPurchaseOrder = opeProductionPurchaseOrderService.getById(enter.getId());
        if (Objects.isNull(opeProductionPurchaseOrder)) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        if (!opeProductionPurchaseOrder.getPurchaseStatus().equals(NewProductionPurchasEnums.DRAFT.getValue())) {
            throw new SesWebRosException(ExceptionCodeEnums.STATUS_ILLEGAL.getCode(), ExceptionCodeEnums.STATUS_ILLEGAL.getMessage());
        }
        opeProductionPurchaseOrder.setUpdatedBy(enter.getId());
        opeProductionPurchaseOrder.setUpdatedTime(new Date());
        opeProductionPurchaseOrderService.removeById(opeProductionPurchaseOrder.getId());
        //操作动态
        SaveOpTraceEnter saveOpTraceEnter = new SaveOpTraceEnter(null, opeProductionPurchaseOrder.getId(), OrderTypeEnums.FACTORY_PURCHAS.getValue(), OrderOperationTypeEnums.DELETE.getValue(), null);
        BeanUtils.copyProperties(enter, saveOpTraceEnter);
        saveOpTraceEnter.setId(null);
        productionOrderTraceService.save(saveOpTraceEnter);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * @param enter
     * @Description
     * @Author: alex
     * @Date: 2020/11/12 2:53 下午
     * @Param: enter
     * @Return: GeneralResult
     * @desc: 下单
     */
    @Override
    public GeneralResult bookOrder(IdEnter enter) {
        OpeProductionPurchaseOrder opeProductionPurchaseOrder = opeProductionPurchaseOrderService.getById(enter.getId());
        if (Objects.isNull(opeProductionPurchaseOrder)) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        if (!opeProductionPurchaseOrder.getPurchaseStatus().equals(NewProductionPurchasEnums.DRAFT.getValue())) {
            throw new SesWebRosException(ExceptionCodeEnums.STATUS_ILLEGAL.getCode(), ExceptionCodeEnums.STATUS_ILLEGAL.getMessage());
        }
        opeProductionPurchaseOrder.setPurchaseStatus(NewProductionPurchasEnums.PURCHASING.getValue());
        opeProductionPurchaseOrder.setUpdatedBy(enter.getId());
        opeProductionPurchaseOrder.setUpdatedTime(new Date());
        opeProductionPurchaseOrderService.updateById(opeProductionPurchaseOrder);
        //操作动态
        SaveOpTraceEnter saveOpTraceEnter = new SaveOpTraceEnter(null, opeProductionPurchaseOrder.getId(), OrderTypeEnums.FACTORY_PURCHAS.getValue(), OrderOperationTypeEnums.PLACE_ORDER.getValue(), null);
        BeanUtils.copyProperties(enter, saveOpTraceEnter);
        saveOpTraceEnter.setId(null);
        productionOrderTraceService.save(saveOpTraceEnter);

        //订单节点
        OrderStatusFlowEnter orderStatusFlowEnter = new OrderStatusFlowEnter(null, opeProductionPurchaseOrder.getPurchaseStatus(), OrderTypeEnums.FACTORY_PURCHAS.getValue(), opeProductionPurchaseOrder.getId(), null);
        BeanUtils.copyProperties(enter, orderStatusFlowEnter);
        orderStatusFlowEnter.setId(null);
        orderStatusFlowService.save(orderStatusFlowEnter);

        // 生成生产采购单的质检单
        OpeQcOrder model = new OpeQcOrder();
        long qcId = idAppService.getId(SequenceName.OPE_QC_ORDER);
        model.setId(qcId);
        model.setDr(DelStatusEnum.VALID.getCode());
        model.setTenantId(enter.getTenantId());
        model.setDeptId(enter.getOpeDeptId());
        model.setCountryType(1);
        model.setQcNo(null);
        model.setQcStatus(QcOrderStatusEnums.TO_BE_QC.getValue());
        model.setOrderType(ProductTypeEnums.PARTS.getValue());
        model.setRelationOrderId(opeProductionPurchaseOrder.getId());
        model.setRelationOrderNo(opeProductionPurchaseOrder.getPurchaseNo());
        model.setRelationOrderType(OrderTypeEnums.FACTORY_PURCHAS.getValue());
        model.setQcType(QcTypeEnums.PURCHASE.getValue());
        model.setQcQty(opeProductionPurchaseOrder.getPurchaseQty());
        model.setCreatedBy(enter.getUserId());
        model.setCreatedTime(new Date());
        opeQcOrderMapper.insert(model);

        // 根据采购单id获得此次的生产采购部件集合
        LambdaQueryWrapper<OpeProductionPurchasePartsB> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OpeProductionPurchasePartsB::getDr, DelStatusEnum.VALID.getCode());
        wrapper.eq(OpeProductionPurchasePartsB::getProductionPurchaseId, opeProductionPurchaseOrder.getId());
        List<OpeProductionPurchasePartsB> list = opeProductionPurchasePartsBMapper.selectList(wrapper);
        if (CollectionUtils.isNotEmpty(list)) {
            List<OpeQcPartsB> saveList = Lists.newArrayList();
            for (OpeProductionPurchasePartsB part : list) {
                // 生成质检单部件子表
                OpeQcPartsB param = new OpeQcPartsB();
                param.setId(idAppService.getId(SequenceName.OPE_QC_PARTS_B));
                param.setDr(DelStatusEnum.VALID.getCode());
                param.setQcId(qcId);
                param.setPartsId(part.getPartsId());
                param.setPartsName(part.getPartsName());
                param.setPartsNo(part.getPartsNo());
                param.setPartsType(part.getPartsType());
                param.setQty(part.getQty());
                param.setCreatedBy(enter.getUserId());
                param.setCreatedTime(new Date());
                saveList.add(param);
            }
            opeQcPartsBService.saveBatch(saveList);
        }
        return new GeneralResult(enter.getRequestId());
    }

    // 部件入库单准备质检时，将关联的部件入库单的状态变为待入库
    @Override
    @Transactional
    public void statusToBeStored(Long productionPurchaseId, Long userId) {
        OpeProductionPurchaseOrder opeProductionPurchaseOrder = opeProductionPurchaseOrderService.getById(productionPurchaseId);
        if (Objects.isNull(opeProductionPurchaseOrder)) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        /*if(opeProductionPurchaseOrder.getPurchaseStatus().equals(ProductionPurchasEnums.TO_BE_STORED.getValue())
        || opeProductionPurchaseOrder.getPurchaseStatus().equals(ProductionPurchasEnums.PARTIAL_STORAGE.getValue())){
            return;
        }*/
        if (!opeProductionPurchaseOrder.getPurchaseStatus().equals(NewProductionPurchasEnums.PURCHASING.getValue())) {
            throw new SesWebRosException(ExceptionCodeEnums.STATUS_ILLEGAL.getCode(), ExceptionCodeEnums.STATUS_ILLEGAL.getMessage());
        }
        //opeProductionPurchaseOrder.setPurchaseStatus(ProductionPurchasEnums.TO_BE_STORED.getValue());
        opeProductionPurchaseOrder.setUpdatedBy(userId);
        opeProductionPurchaseOrder.setUpdatedTime(new Date());
        opeProductionPurchaseOrderService.saveOrUpdate(opeProductionPurchaseOrder);
        //订单节点
        OrderStatusFlowEnter orderStatusFlowEnter = new OrderStatusFlowEnter(null, opeProductionPurchaseOrder.getPurchaseStatus(), OrderTypeEnums.FACTORY_PURCHAS.getValue(), opeProductionPurchaseOrder.getId(), null);
        orderStatusFlowEnter.setId(null);
        orderStatusFlowEnter.setUserId(userId);
        orderStatusFlowService.save(orderStatusFlowEnter);
    }


    // 部件入库单确认入库时，将关联的部件入库单的状态变为部分入库或已入库
    @Override
    public void statusToPartWhOrAllInWh(Long productionPurchaseId, Long inWhId, Long userId) {
        OpeProductionPurchaseOrder opeProductionPurchaseOrder = opeProductionPurchaseOrderService.getById(productionPurchaseId);
        if (Objects.isNull(opeProductionPurchaseOrder)) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        if (!opeProductionPurchaseOrder.getPurchaseStatus().equals(NewProductionPurchasEnums.PURCHASING.getValue()) && !opeProductionPurchaseOrder.getPurchaseStatus().equals(ProductionPurchasEnums.TO_BE_STORED.getValue()) && !opeProductionPurchaseOrder.getPurchaseStatus().equals(ProductionPurchasEnums.PARTIAL_STORAGE.getValue())) {
            throw new SesWebRosException(ExceptionCodeEnums.STATUS_ILLEGAL.getCode(), ExceptionCodeEnums.STATUS_ILLEGAL.getMessage());
        }
        // 判断一波 是变成部分入库还是已入库
        if (checkStatusChange(productionPurchaseId, inWhId)) {
            // 已入库
            opeProductionPurchaseOrder.setPurchaseStatus(ProductionPurchasEnums.HAS_BEEN_STORED.getValue());
        } else {
            // 部分入库
            opeProductionPurchaseOrder.setPurchaseStatus(ProductionPurchasEnums.PARTIAL_STORAGE.getValue());
        }
        opeProductionPurchaseOrder.setUpdatedBy(userId);
        opeProductionPurchaseOrder.setUpdatedTime(new Date());
        opeProductionPurchaseOrderService.saveOrUpdate(opeProductionPurchaseOrder);
        //订单节点
        OrderStatusFlowEnter orderStatusFlowEnter = new OrderStatusFlowEnter(null, opeProductionPurchaseOrder.getPurchaseStatus(), OrderTypeEnums.FACTORY_PURCHAS.getValue(), opeProductionPurchaseOrder.getId(), null);
        orderStatusFlowEnter.setId(null);
        orderStatusFlowEnter.setUserId(userId);
        orderStatusFlowService.save(orderStatusFlowEnter);
    }

    /**
     * 生成组装单的质检单(提供给rps使用)
     */
    @Override
    public GeneralResult generatorQcOrderByCombin(IdEnter enter) {
        log.info("生成组装单的质检单(提供给rps使用)的入参是:[{}]", enter);
        OpeCombinOrder combinOrder = opeCombinOrderMapper.selectById(enter.getId());
        if (null == combinOrder) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }

        Integer combinType = combinOrder.getCombinType();
        // 组装单的类型是车辆
        switch (combinType) {
            case 1:
                // 生成组装单车辆的质检单
                OpeQcOrder model = new OpeQcOrder();
                long qcId = idAppService.getId(SequenceName.OPE_QC_ORDER);
                model.setId(qcId);
                model.setDr(DelStatusEnum.VALID.getCode());
                model.setTenantId(enter.getTenantId());
                model.setDeptId(enter.getOpeDeptId());
                model.setCountryType(1);
                model.setQcNo(null);
                model.setQcStatus(QcOrderStatusEnums.TO_BE_QC.getValue());
                model.setOrderType(ProductTypeEnums.SCOOTER.getValue());
                model.setRelationOrderId(combinOrder.getId());
                model.setRelationOrderNo(combinOrder.getCombinNo());
                model.setRelationOrderType(OrderTypeEnums.COMBIN_ORDER.getValue());
                model.setQcType(QcTypeEnums.PRODUCTION.getValue());
                model.setQcQty(combinOrder.getCombinQty());
                model.setCreatedBy(enter.getUserId());
                model.setCreatedTime(new Date());
                opeQcOrderMapper.insert(model);

                // 根据组装单id获得车辆组装单集合
                LambdaQueryWrapper<OpeCombinOrderScooterB> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(OpeCombinOrderScooterB::getDr, DelStatusEnum.VALID.getCode());
                wrapper.eq(OpeCombinOrderScooterB::getCombinId, combinOrder.getId());
                List<OpeCombinOrderScooterB> list = opeCombinOrderScooterBMapper.selectList(wrapper);
                if (CollectionUtils.isNotEmpty(list)) {
                    List<OpeQcScooterB> saveList = Lists.newArrayList();
                    for (OpeCombinOrderScooterB scooter : list) {
                        // 生成质检单车辆组装单子表
                        OpeQcScooterB param = new OpeQcScooterB();
                        param.setId(idAppService.getId(SequenceName.OPE_QC_SCOOTER_B));
                        param.setDr(DelStatusEnum.VALID.getCode());
                        param.setQcId(qcId);
                        param.setGroupId(scooter.getGroupId());
                        param.setColorId(scooter.getColorId());
                        param.setScooterBomId(scooter.getScooterBomId());
                        param.setQty(scooter.getQty());
                        param.setCreatedBy(enter.getUserId());
                        param.setCreatedTime(new Date());
                        saveList.add(param);
                    }
                    opeQcScooterBService.saveBatch(saveList);
                }
            default:
                break;
            // 组装单的类型是组装件
            case 2:
                // 生成组装单组装件的质检单
                OpeQcOrder instance = new OpeQcOrder();
                long id = idAppService.getId(SequenceName.OPE_QC_ORDER);
                instance.setId(id);
                instance.setDr(DelStatusEnum.VALID.getCode());
                instance.setTenantId(enter.getTenantId());
                instance.setDeptId(enter.getOpeDeptId());
                instance.setCountryType(1);
                instance.setQcNo(null);
                instance.setQcStatus(QcOrderStatusEnums.TO_BE_QC.getValue());
                instance.setOrderType(ProductTypeEnums.COMBINATION.getValue());
                instance.setRelationOrderId(combinOrder.getId());
                instance.setRelationOrderNo(combinOrder.getCombinNo());
                instance.setRelationOrderType(OrderTypeEnums.COMBIN_ORDER.getValue());
                instance.setQcType(QcTypeEnums.PRODUCTION.getValue());
                instance.setQcQty(combinOrder.getCombinQty());
                instance.setCreatedBy(enter.getUserId());
                instance.setCreatedTime(new Date());
                opeQcOrderMapper.insert(instance);

                // 根据组装单id获得组装件组装单集合
                LambdaQueryWrapper<OpeCombinOrderCombinB> qw = new LambdaQueryWrapper<>();
                qw.eq(OpeCombinOrderCombinB::getDr, DelStatusEnum.VALID.getCode());
                qw.eq(OpeCombinOrderCombinB::getCombinId, combinOrder.getId());
                List<OpeCombinOrderCombinB> tempList = opeCombinOrderCombinBMapper.selectList(qw);
                if (CollectionUtils.isNotEmpty(tempList)) {
                    List<OpeQcCombinB> saveList = Lists.newArrayList();
                    for (OpeCombinOrderCombinB combin : tempList) {
                        // 生成质检单组装件组装单子表
                        OpeQcCombinB param = new OpeQcCombinB();
                        param.setId(idAppService.getId(SequenceName.OPE_QC_COMBIN_B));
                        param.setDr(DelStatusEnum.VALID.getCode());
                        param.setQcId(id);
                        param.setCombinName(combin.getCombinName());
                        param.setCombinNo(combin.getCombinNo());
                        param.setProductionCombinBomId(combin.getProductionCombinBomId());
                        param.setQty(combin.getQty());
                        param.setCreatedBy(enter.getUserId());
                        param.setCreatedTime(new Date());
                        saveList.add(param);
                    }
                    opeQcCombinBService.saveBatch(saveList);
                }
                break;
        }
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 生成出库单的质检单(提供给rps使用)
     */
    @Override
    public GeneralResult generatorQcOrderByOutBound(IdEnter enter) {
        log.info("生成出库单的质检单(提供给rps使用)的入参是:[{}]", enter);
        OpeOutWhouseOrder outOrder = opeOutWhouseOrderMapper.selectById(enter.getId());
        if (null == outOrder) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }

        // 限制是发货单生成的组装单
        if (null != outOrder && outOrder.getRelationType() == 3) {
            Integer outWhType = outOrder.getOutWhType();
            switch (outWhType) {
                case 1:
                    // 生成出库单车辆的质检单
                    OpeQcOrder model = new OpeQcOrder();
                    long qcId = idAppService.getId(SequenceName.OPE_QC_ORDER);
                    model.setId(qcId);
                    model.setDr(DelStatusEnum.VALID.getCode());
                    model.setTenantId(enter.getTenantId());
                    model.setDeptId(enter.getOpeDeptId());
                    model.setCountryType(1);
                    model.setQcNo(null);
                    model.setQcStatus(QcOrderStatusEnums.TO_BE_QC.getValue());
                    model.setOrderType(ProductTypeEnums.SCOOTER.getValue());
                    model.setRelationOrderId(outOrder.getId());
                    model.setRelationOrderNo(outOrder.getOutWhNo());
                    model.setRelationOrderType(OrderTypeEnums.OUTBOUND.getValue());
                    model.setQcType(QcTypeEnums.SEND_GOOD.getValue());
                    model.setQcQty(outOrder.getOutWhQty());
                    model.setCreatedBy(enter.getUserId());
                    model.setCreatedTime(new Date());
                    opeQcOrderMapper.insert(model);

                    // 根据出库单id获得车辆出库单集合
                    LambdaQueryWrapper<OpeOutWhScooterB> wrapper = new LambdaQueryWrapper<>();
                    wrapper.eq(OpeOutWhScooterB::getDr, DelStatusEnum.VALID.getCode());
                    wrapper.eq(OpeOutWhScooterB::getOutWhId, outOrder.getId());
                    List<OpeOutWhScooterB> list = opeOutWhScooterBMapper.selectList(wrapper);
                    if (CollectionUtils.isNotEmpty(list)) {
                        List<OpeQcScooterB> saveList = Lists.newArrayList();
                        for (OpeOutWhScooterB scooter : list) {
                            // 生成质检单车辆出库单子表
                            OpeQcScooterB param = new OpeQcScooterB();
                            param.setId(idAppService.getId(SequenceName.OPE_QC_SCOOTER_B));
                            param.setDr(DelStatusEnum.VALID.getCode());
                            param.setQcId(qcId);
                            param.setGroupId(scooter.getGroupId());
                            param.setColorId(scooter.getColorId());
                            param.setScooterBomId(getBomIdByGroupIdAndColorId(scooter.getGroupId(), scooter.getColorId()));
                            param.setQty(scooter.getQty());
                            param.setCreatedBy(enter.getUserId());
                            param.setCreatedTime(new Date());
                            saveList.add(param);
                        }
                        opeQcScooterBService.saveBatch(saveList);
                    }
                default:
                    break;
                case 2:
                    // 生成出库单组装件的质检单
                    OpeQcOrder instance = new OpeQcOrder();
                    long id = idAppService.getId(SequenceName.OPE_QC_ORDER);
                    instance.setId(id);
                    instance.setDr(DelStatusEnum.VALID.getCode());
                    instance.setTenantId(enter.getTenantId());
                    instance.setDeptId(enter.getOpeDeptId());
                    instance.setCountryType(1);
                    instance.setQcNo(null);
                    instance.setQcStatus(QcOrderStatusEnums.TO_BE_QC.getValue());
                    instance.setOrderType(ProductTypeEnums.COMBINATION.getValue());
                    instance.setRelationOrderId(outOrder.getId());
                    instance.setRelationOrderNo(outOrder.getOutWhNo());
                    instance.setRelationOrderType(OrderTypeEnums.OUTBOUND.getValue());
                    instance.setQcType(QcTypeEnums.SEND_GOOD.getValue());
                    instance.setQcQty(outOrder.getOutWhQty());
                    instance.setCreatedBy(enter.getUserId());
                    instance.setCreatedTime(new Date());
                    opeQcOrderMapper.insert(instance);

                    // 根据出库单id获得组装件出库单集合
                    LambdaQueryWrapper<OpeOutWhCombinB> qw = new LambdaQueryWrapper<>();
                    qw.eq(OpeOutWhCombinB::getDr, DelStatusEnum.VALID.getCode());
                    qw.eq(OpeOutWhCombinB::getOutWhId, outOrder.getId());
                    List<OpeOutWhCombinB> tempList = opeOutWhCombinBMapper.selectList(qw);
                    if (CollectionUtils.isNotEmpty(tempList)) {
                        List<OpeQcCombinB> saveList = Lists.newArrayList();
                        for (OpeOutWhCombinB combin : tempList) {
                            // 生成质检单组装件出库单子表
                            OpeQcCombinB param = new OpeQcCombinB();
                            param.setId(idAppService.getId(SequenceName.OPE_QC_COMBIN_B));
                            param.setDr(DelStatusEnum.VALID.getCode());
                            param.setQcId(id);
                            param.setCombinName(combin.getCombinName());
                            param.setCombinNo(getCombinBomNoById(combin.getProductionCombinBomId()));
                            param.setProductionCombinBomId(combin.getProductionCombinBomId());
                            param.setQty(combin.getQty());
                            param.setCreatedBy(enter.getUserId());
                            param.setCreatedTime(new Date());
                            saveList.add(param);
                        }
                        opeQcCombinBService.saveBatch(saveList);
                    }
                    break;
                case 3:
                    // 生成出库单部件的质检单
                    OpeQcOrder order = new OpeQcOrder();
                    long tempId = idAppService.getId(SequenceName.OPE_QC_ORDER);
                    order.setId(tempId);
                    order.setDr(DelStatusEnum.VALID.getCode());
                    order.setTenantId(enter.getTenantId());
                    order.setDeptId(enter.getOpeDeptId());
                    order.setCountryType(1);
                    order.setQcNo(null);
                    order.setQcStatus(QcOrderStatusEnums.TO_BE_QC.getValue());
                    order.setOrderType(ProductTypeEnums.PARTS.getValue());
                    order.setRelationOrderId(outOrder.getId());
                    order.setRelationOrderNo(outOrder.getOutWhNo());
                    order.setRelationOrderType(OrderTypeEnums.OUTBOUND.getValue());
                    order.setQcType(QcTypeEnums.SEND_GOOD.getValue());
                    order.setQcQty(outOrder.getOutWhQty());
                    order.setCreatedBy(enter.getUserId());
                    order.setCreatedTime(new Date());
                    opeQcOrderMapper.insert(order);

                    // 根据出库单id获得部件出库单集合
                    LambdaQueryWrapper<OpeOutWhPartsB> lqw = new LambdaQueryWrapper<>();
                    lqw.eq(OpeOutWhPartsB::getDr, DelStatusEnum.VALID.getCode());
                    lqw.eq(OpeOutWhPartsB::getOutWhId, outOrder.getId());
                    List<OpeOutWhPartsB> partsList = opeOutWhPartsBMapper.selectList(lqw);
                    if (CollectionUtils.isNotEmpty(partsList)) {
                        List<OpeQcPartsB> saveList = Lists.newArrayList();
                        for (OpeOutWhPartsB parts : partsList) {
                            OpeQcPartsB param = new OpeQcPartsB();
                            param.setId(idAppService.getId(SequenceName.OPE_QC_PARTS_B));
                            param.setDr(DelStatusEnum.VALID.getCode());
                            param.setQcId(tempId);
                            param.setPartsId(parts.getPartsId());
                            param.setPartsName(parts.getPartsName());
                            param.setPartsNo(parts.getPartsNo());
                            param.setPartsType(parts.getPartsType());
                            param.setQty(parts.getQty());
                            param.setCreatedBy(enter.getUserId());
                            param.setCreatedTime(new Date());
                            saveList.add(param);
                        }
                        opeQcPartsBService.saveBatch(saveList);
                    }
                    break;
            }

        }
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 根据groupId和colorId获取整车bom表id
     */
    private Long getBomIdByGroupIdAndColorId(Long groupId, Long colorId) {
        LambdaQueryWrapper<OpeProductionScooterBom> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OpeProductionScooterBom::getDr, DelStatusEnum.VALID.getCode());
        wrapper.eq(OpeProductionScooterBom::getGroupId, groupId);
        wrapper.eq(OpeProductionScooterBom::getColorId, colorId);
        wrapper.orderByDesc(OpeProductionScooterBom::getCreatedTime);
        List<OpeProductionScooterBom> list = opeProductionScooterBomMapper.selectList(wrapper);
        if (CollectionUtils.isNotEmpty(list)) {
            OpeProductionScooterBom bom = list.get(0);
            if (null != bom) {
                return bom.getId();
            }
        }
        throw new SesWebRosException(ExceptionCodeEnums.BOM_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.BOM_IS_NOT_EXIST.getMessage());
    }

    /**
     * 根据组装件id获得组装件no
     */
    private String getCombinBomNoById(Long id) {
        OpeProductionCombinBom bom = opeProductionCombinBomService.getById(id);
        if (null != bom) {
            return bom.getBomNo();
        }
        throw new SesWebRosException(ExceptionCodeEnums.BOM_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.BOM_IS_NOT_EXIST.getMessage());
    }

    // 校验本次是部分入库还是已入库
    public boolean checkStatusChange(Long productionPurchaseId, Long inWhId) {
        boolean flag = true;
        // 找到部件采购单下面的部件明细
        QueryWrapper<OpeProductionPurchasePartsB> qw = new QueryWrapper<>();
        qw.eq(OpeProductionPurchasePartsB.COL_PRODUCTION_PURCHASE_ID, productionPurchaseId);
        List<OpeProductionPurchasePartsB> purchasePartsBs = opeProductionPurchasePartsBService.list(qw);
        if (CollectionUtils.isNotEmpty(purchasePartsBs)) {
            // 先看看这个采购单下面有没有还没有 没有已入库状态之前的入库单  有的话 为部分入库  返回false
            QueryWrapper<OpeInWhouseOrder> inWhouseOrderQueryWrapper = new QueryWrapper<>();
            inWhouseOrderQueryWrapper.eq(OpeInWhouseOrder.COL_RELATION_ORDER_ID, productionPurchaseId);
            inWhouseOrderQueryWrapper.ne(OpeInWhouseOrder.COL_ID, inWhId);
            inWhouseOrderQueryWrapper.lt(OpeInWhouseOrder.COL_IN_WH_STATUS, NewInWhouseOrderStatusEnum.ALREADY_IN_WHOUSE.getValue());
            int inWhNum = opeInWhouseOrderService.count(inWhouseOrderQueryWrapper);
            if (inWhNum > 0) {
                flag = false;
                return flag;
            }
            // 说明采购单下面的入库单都签收了 再判断“实际入库数量”是否大于等于“采购数量”  如果大于等于 则为已入库  返回true
            // 1、对采购单下面的部件信息按照部件id进行分组
            Map<Long, List<OpeProductionPurchasePartsB>> purchasePartsMap = purchasePartsBs.stream().collect(Collectors.groupingBy(OpeProductionPurchasePartsB::getPartsId));
            // 2、找到采购单下面的入库单的所有部件信息
            List<OpeInWhousePartsB> inWhousePartsBList = inWhouseOrderServiceMapper.inWhousePartsList(productionPurchaseId);
            if (CollectionUtils.isEmpty(inWhousePartsBList)) {
                flag = false;
                return flag;
            }
            // 3、采购单下面的入库单的所有部件信息 按照部件id进行分组
            Map<Long, List<OpeInWhousePartsB>> inWhousePartsMap = inWhousePartsBList.stream().collect(Collectors.groupingBy(OpeInWhousePartsB::getPartsId));
            if (purchasePartsMap.size() > inWhousePartsMap.size()) {
                // 如果生产采购单下面的部件种类比入库单的多  说明生产采购单还没有入库单（还需要继续产生入库单才行）
                flag = false;
                return flag;
            }
            for (Long key1 : purchasePartsMap.keySet()) {
                int purchasePartsNum = purchasePartsMap.get(key1).stream().mapToInt(OpeProductionPurchasePartsB::getQty).sum();
                for (Long key2 : inWhousePartsMap.keySet()) {
                    if (key1.equals(key2)) {
                        int inWhousePartsNum = inWhousePartsMap.get(key2).stream().mapToInt(OpeInWhousePartsB::getActInWhQty).sum();
                        if (purchasePartsNum > inWhousePartsNum) {
                            // 某个部件的采购数量大于入库单的实际入库数量，状态变为部分入库  返回false
                            flag = false;
                            return flag;
                        }
                    }
                }
            }
        }
        return flag;
    }
}
