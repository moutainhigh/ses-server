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
import com.redescooter.ses.tool.utils.date.DateUtil;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.constant.StringManaConstant;
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
import com.redescooter.ses.web.ros.utils.NumberUtil;
import com.redescooter.ses.web.ros.vo.bo.PartDetailDto;
import com.redescooter.ses.web.ros.vo.restproductionorder.AssociatedOrderResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.SupplierListResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.SupplierPrincipaleResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.number.OrderNumberEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.optrace.ListByBussIdEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.optrace.SaveOpTraceEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.orderflow.OrderStatusFlowEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.purchaseorder.CancelOrderEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.purchass.ProductionPurchasDetailResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.purchass.ProductionPurchasListEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.purchass.ProductionPurchasListResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.purchass.PurchasDetailProductListResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.purchass.PurchasPartListEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.purchass.PurchasPartListResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.purchass.SaveProductionPurchasEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.purchass.SavePurchasPaymentEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.purchass.SavePurchasProductEnter;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
 * @Version???1.3
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

    @DubboReference
    private IdAppService idAppService;

    /**
     * @param enter
     * @Description
     * @Author: alex
     * @Date: 2020/11/10 2:52 ??????
     * @Param: enter
     * @Return: ProductionPurchasListResult
     * @desc: ??????
     */
    @Override
    public PageResult<ProductionPurchasListResult> list(ProductionPurchasListEnter enter) {
        int count = productionPurchasServiceMapper.listCount(enter);
        if (NumberUtil.eqZero(count)) {
            return PageResult.createZeroRowResult(enter);
        }
        return PageResult.create(enter, count, productionPurchasServiceMapper.list(enter));
    }

    /**
     * @param enter
     * @Description
     * @Author: alex
     * @Date: 2020/11/10 3:06 ??????
     * @Param: enter
     * @Return: ProductionPurchasDetailResult
     * @desc: ??????
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
     * @Date: 2020/11/11 4:18 ??????
     * @Param: enter
     * @Return: List<PurchasDetailProductListResult>
     * @desc: ????????????
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
     * @Date: 2020/11/11 5:02 ??????
     * @Param: enter
     * @Return: List<AssociatedOrderResult>
     * @desc: ????????????
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
     * @Date: 2020/11/10 4:08 ??????
     * @Param: enter
     * @Return: GeneralResult
     * @desc: ???????????????
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult save(SaveProductionPurchasEnter enter) {
        // ????????????
        List<SavePurchasProductEnter> productList = new ArrayList<>();
        // ????????????
        SavePurchasPaymentEnter paymentEnter;
        try {
            // ?????????????????????????????????
            paymentEnter = JSON.parseObject(enter.getPaymentDate(), SavePurchasPaymentEnter.class);
            productList.addAll(JSON.parseArray(enter.getSt(), SavePurchasProductEnter.class));
        } catch (Exception e) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }
        if (CollectionUtils.isEmpty(productList) || Objects.isNull(paymentEnter)) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }

        //??????json??????
        List<PartDetailDto> PartDetailDtoList = checkJsonData(productList, paymentEnter);
        Long purchasId = idAppService.getId(SequenceName.OPE_PRODUCTION_PURCHASE_ORDER);
        BigDecimal totalPrice = BigDecimal.ZERO;

        //???????????????
        List<OpeProductionPurchasePartsB> savePartsBList = new ArrayList<>();
        for (SavePurchasProductEnter item : productList) {
            PartDetailDto dto = PartDetailDtoList.stream().filter(part -> item.getId().equals(part.getPartId())).findFirst().orElse(null);
            //???????????????
            OpeProductionPurchasePartsB partsB = buildOpeProductionPurchasePartsB(enter, (enter.getId() == null || enter.getId() == 0) ? purchasId : enter.getId(), item, dto);
            savePartsBList.add(partsB);
            /*Long price = dto.getPrice().multiply(new BigDecimal(item.getQty())).longValue();
            totalPrice += price;*/
            BigDecimal price = item.getUnitPrice().multiply(new BigDecimal(item.getQty()));
            totalPrice = totalPrice.add(price);
        }

        SaveOpTraceEnter saveOpTraceEnter;
        // ?????????????????????
        OpeProductionPurchaseOrder opeProductionPurchaseOrder = buildOpeProductionPurchaseOrder(enter, productList, paymentEnter, totalPrice);
        if (StringManaConstant.entityIsNull(enter.getId()) || 0 == enter.getId()) {
            opeProductionPurchaseOrder.setId(purchasId);
            opeProductionPurchaseOrder.setCreatedBy(enter.getUserId());
            opeProductionPurchaseOrder.setCreatedTime(new Date());
            opeProductionPurchaseOrder.setPurchaseNo(orderNumberService.generateOrderNo(new OrderNumberEnter(OrderTypeEnums.FACTORY_PURCHAS.getValue())));

            //????????????
            OrderStatusFlowEnter orderStatusFlowEnter = new OrderStatusFlowEnter(null, opeProductionPurchaseOrder.getPurchaseStatus(), OrderTypeEnums.FACTORY_PURCHAS.getValue(), opeProductionPurchaseOrder.getId(), null);
            BeanUtils.copyProperties(enter, orderStatusFlowEnter);
            orderStatusFlowEnter.setId(null);
            orderStatusFlowService.save(orderStatusFlowEnter);

            //????????????
            saveOpTraceEnter = new SaveOpTraceEnter(null, opeProductionPurchaseOrder.getId(), OrderTypeEnums.FACTORY_PURCHAS.getValue(), OrderOperationTypeEnums.CREATE.getValue(), enter.getRemark());
        } else {
            //???????????????
            opeProductionPurchasePartsBService.remove(new LambdaQueryWrapper<OpeProductionPurchasePartsB>().eq(OpeProductionPurchasePartsB::getProductionPurchaseId, enter.getId()));
            //????????????
            saveOpTraceEnter = new SaveOpTraceEnter(null, opeProductionPurchaseOrder.getId(), OrderTypeEnums.FACTORY_PURCHAS.getValue(), OrderOperationTypeEnums.EDIT.getValue(), enter.getRemark());
        }
        opeProductionPurchaseOrder.setUpdatedBy(enter.getUserId());
        opeProductionPurchaseOrder.setUpdatedTime(new Date());
        // ?????????????????????
        opeProductionPurchaseOrderService.saveOrUpdate(opeProductionPurchaseOrder);
        // ???????????????????????????
        opeProductionPurchasePartsBService.saveBatch(savePartsBList);

        //????????????
        BeanUtils.copyProperties(enter, saveOpTraceEnter);
        saveOpTraceEnter.setId(null);
        productionOrderTraceService.save(saveOpTraceEnter);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * ??????????????????????????????
     */
    private OpeProductionPurchasePartsB buildOpeProductionPurchasePartsB(SaveProductionPurchasEnter enter, Long purchasId, SavePurchasProductEnter item, PartDetailDto partDetailDto) {
        OpeProductionPurchasePartsB result = new OpeProductionPurchasePartsB();
        result.setId(idAppService.getId(SequenceName.OPE_PRODUCTION_PURCHASE_PARTS_B));
        result.setDr(0);
        result.setProductionPurchaseId(purchasId);
        result.setPartsId(partDetailDto.getPartId());
        result.setPartsName(partDetailDto.getPartName());
        result.setPartsNo(partDetailDto.getPartN());
        result.setPartsType(Integer.valueOf(partDetailDto.getProductType()));
        result.setQty(item.getQty());
        result.setUnitPrice(partDetailDto.getPrice());
        result.setRemark(enter.getRemark());
        result.setCreatedBy(enter.getUserId());
        result.setCreatedTime(new Date());
        result.setUpdatedBy(enter.getUserId());
        result.setUpdatedTime(new Date());
        return result;
    }

    /**
     * ?????????????????????
     */
    private OpeProductionPurchaseOrder buildOpeProductionPurchaseOrder(SaveProductionPurchasEnter enter, List<SavePurchasProductEnter> productList, SavePurchasPaymentEnter paymentEnter, BigDecimal totalPrice) {
        if (StringUtils.isEmpty(enter.getFactoryPrincipalName()) && StringManaConstant.entityIsNotNull(enter.getFactoryId()) && enter.getFactoryId() != 0) {
            OpeSupplier opeSupplier = opeSupplierService.getById(enter.getFactoryId());
            if (Objects.isNull(opeSupplier)) {
                throw new SesWebRosException(ExceptionCodeEnums.FACTORY_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.FACTORY_IS_NOT_EXIST.getMessage());
            }
            enter.setFactoryPrincipalName(opeSupplier.getContactFullName());
        }
        if (StringUtils.isEmpty(enter.getDockingUserName()) && StringManaConstant.entityIsNotNull(enter.getDockingUser()) && enter.getDockingUser() != 0) {
            OpeSysStaff opeSysStaff = opeSysStaffService.getById(enter.getDockingUser());
            if (StringManaConstant.entityIsNull(opeSysStaff)) {
                throw new SesWebRosException(ExceptionCodeEnums.EMPLOYEE_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.EMPLOYEE_IS_NOT_EXIST.getMessage());
            }
            enter.setDockingUserName(opeSysStaff.getFirstName() + " " + opeSysStaff.getLastName());
        }
        if (StringUtils.isEmpty(enter.getConsigneeUserName()) && StringManaConstant.entityIsNotNull(enter.getConsigneeUser()) && enter.getConsigneeUser() != 0) {
            OpeSysStaff opeSysStaff = opeSysStaffService.getById(enter.getConsigneeUser());
            if (StringManaConstant.entityIsNull(opeSysStaff)) {
                throw new SesWebRosException(ExceptionCodeEnums.EMPLOYEE_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.EMPLOYEE_IS_NOT_EXIST.getMessage());
            }
            enter.setConsigneeUserName(opeSysStaff.getFirstName() + " " + opeSysStaff.getLastName());
        }

        OpeProductionPurchaseOrder result = new OpeProductionPurchaseOrder();
        BeanUtils.copyProperties(enter, result);
        result.setDr(0);
        result.setPurchaseStatus(NewProductionPurchasEnums.DRAFT.getValue());
        result.setPurchaseQty(productList.stream().mapToInt(SavePurchasProductEnter::getQty).sum());
        result.setPurchaseContract(enter.getContract());
        result.setPaymentType(paymentEnter.getPaymentType());
        result.setPlannedPaymentTime(paymentEnter.getDate());
        result.setPaymentDay(paymentEnter.getDays());
        if (StringManaConstant.entityIsNotNull(paymentEnter.getDays()) && StringManaConstant.entityIsNotNull(paymentEnter.getDate())) {
            result.setPaymentTime(DateUtil.addDays(paymentEnter.getDate(), paymentEnter.getDays()));
        }
        result.setPrePayRatio(new BigDecimal(paymentEnter.getPercentage()));
        result.setPurchaseAmount(totalPrice);
        result.setAmountType(paymentEnter.getAmountType());
        if (PaymentTypeEnums.PREPAYMENTS.getValue().equals(paymentEnter.getPaymentType()) && StringManaConstant.entityIsNotNull(paymentEnter.getAmount())) {
            if (0 > totalPrice.subtract(paymentEnter.getAmount()).longValue()) {
                throw new SesWebRosException(ExceptionCodeEnums.PAY_AMOUNT_IS_FALSE.getCode(), ExceptionCodeEnums.PAY_AMOUNT_IS_FALSE.getMessage());
            }
            result.setPayAmount(paymentEnter.getAmount());
        }
        return result;
    }

    @Override
    public PageResult<PurchasPartListResult> purchasPartList(PurchasPartListEnter enter) {
        int count = productionPurchasServiceMapper.purchasPartListCount(enter);
        if (NumberUtil.eqZero(count)) {
            return PageResult.createZeroRowResult(enter);
        }
        return PageResult.create(enter, count, productionPurchasServiceMapper.purchasPartList(enter));
    }

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/11/11 7:15 ??????
     * @Param: productList, paymentEnter
     * @Return: List<OpeProductionParts>
     * @desc: ??????json ??????
     */
    private List<PartDetailDto> checkJsonData(List<SavePurchasProductEnter> productList, SavePurchasPaymentEnter paymentEnter) {
        List<PartDetailDto> PartDetailDtoList = productionPurchasServiceMapper.partDetailList(productList.stream().map(SavePurchasProductEnter::getId).collect(Collectors.toSet()));
        productList.forEach(item -> {
            PartDetailDto dto = PartDetailDtoList.stream().filter(product -> item.getId().equals(product.getPartId())).findFirst().orElse(null);
            if (Objects.isNull(dto)) {
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
     * @Date: 2020/11/11 3:21 ??????
     * @Param: enter
     * @Return: SupplierListResult
     * @desc: ???????????????
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
     * @Date: 2020/11/12 2:51 ??????
     * @Param: enter
     * @Return: GeneralResult
     * @desc: ????????????
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
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
        //????????????
        SaveOpTraceEnter saveOpTraceEnter = new SaveOpTraceEnter(null, opeProductionPurchaseOrder.getId(), OrderTypeEnums.FACTORY_PURCHAS.getValue(), OrderOperationTypeEnums.CLOSE.getValue(), null);
        BeanUtils.copyProperties(enter, saveOpTraceEnter);
        saveOpTraceEnter.setId(null);
        productionOrderTraceService.save(saveOpTraceEnter);

        //????????????
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
     * @Date: 2020/11/12 2:52 ??????
     * @Param: enter
     * @Return: GeneralResult
     * @desc: ????????????
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult cancel(CancelOrderEnter enter) {
        OpeProductionPurchaseOrder opeProductionPurchaseOrder = opeProductionPurchaseOrderService.getById(enter.getId());
        if (Objects.isNull(opeProductionPurchaseOrder)) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        if (!opeProductionPurchaseOrder.getPurchaseStatus().equals(NewProductionPurchasEnums.PURCHASING.getValue())) {
            throw new SesWebRosException(ExceptionCodeEnums.STATUS_ILLEGAL.getCode(), ExceptionCodeEnums.STATUS_ILLEGAL.getMessage());
        }
        opeProductionPurchaseOrder.setPurchaseStatus(NewProductionPurchasEnums.CANCEL.getValue());
        opeProductionPurchaseOrder.setUpdatedBy(enter.getId());
        opeProductionPurchaseOrder.setUpdatedTime(new Date());
        opeProductionPurchaseOrderService.updateById(opeProductionPurchaseOrder);
        //????????????
        SaveOpTraceEnter saveOpTraceEnter = new SaveOpTraceEnter(null, opeProductionPurchaseOrder.getId(), OrderTypeEnums.FACTORY_PURCHAS.getValue(), OrderOperationTypeEnums.CANCEL.getValue(), enter.getRemark());
        BeanUtils.copyProperties(enter, saveOpTraceEnter);
        saveOpTraceEnter.setId(null);
        productionOrderTraceService.save(saveOpTraceEnter);

        //????????????
        OrderStatusFlowEnter orderStatusFlowEnter = new OrderStatusFlowEnter(null, opeProductionPurchaseOrder.getPurchaseStatus(), OrderTypeEnums.FACTORY_PURCHAS.getValue(), opeProductionPurchaseOrder.getId(), enter.getRemark());
        BeanUtils.copyProperties(enter, orderStatusFlowEnter);
        orderStatusFlowEnter.setId(null);
        orderStatusFlowService.save(orderStatusFlowEnter);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * @param enter
     * @Description
     * @Author: alex
     * @Date: 2020/11/12 2:53 ??????
     * @Param: enter
     * @Return: GeneralResult
     * @desc: ??????
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
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
        //????????????
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
     * @Date: 2020/11/12 2:53 ??????
     * @Param: enter
     * @Return: GeneralResult
     * @desc: ??????
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
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
        //????????????
        SaveOpTraceEnter saveOpTraceEnter = new SaveOpTraceEnter(null, opeProductionPurchaseOrder.getId(), OrderTypeEnums.FACTORY_PURCHAS.getValue(), OrderOperationTypeEnums.PLACE_ORDER.getValue(), null);
        BeanUtils.copyProperties(enter, saveOpTraceEnter);
        saveOpTraceEnter.setId(null);
        productionOrderTraceService.save(saveOpTraceEnter);

        //????????????
        OrderStatusFlowEnter orderStatusFlowEnter = new OrderStatusFlowEnter(null, opeProductionPurchaseOrder.getPurchaseStatus(), OrderTypeEnums.FACTORY_PURCHAS.getValue(), opeProductionPurchaseOrder.getId(), null);
        BeanUtils.copyProperties(enter, orderStatusFlowEnter);
        orderStatusFlowEnter.setId(null);
        orderStatusFlowService.save(orderStatusFlowEnter);
        return new GeneralResult(enter.getRequestId());
    }

    // ????????????????????????????????????????????????????????????????????????????????????
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
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
        //????????????
        OrderStatusFlowEnter orderStatusFlowEnter = new OrderStatusFlowEnter(null, opeProductionPurchaseOrder.getPurchaseStatus(), OrderTypeEnums.FACTORY_PURCHAS.getValue(), opeProductionPurchaseOrder.getId(), null);
        orderStatusFlowEnter.setId(null);
        orderStatusFlowEnter.setUserId(userId);
        orderStatusFlowService.save(orderStatusFlowEnter);
    }


    // ???????????????????????????????????????????????????????????????????????????????????????????????????
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public void statusToPartWhOrAllInWh(Long productionPurchaseId, Long inWhId, Long userId) {
        OpeProductionPurchaseOrder opeProductionPurchaseOrder = opeProductionPurchaseOrderService.getById(productionPurchaseId);
        if (Objects.isNull(opeProductionPurchaseOrder)) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        if (!opeProductionPurchaseOrder.getPurchaseStatus().equals(NewProductionPurchasEnums.PURCHASING.getValue()) && !opeProductionPurchaseOrder.getPurchaseStatus().equals(ProductionPurchasEnums.TO_BE_STORED.getValue()) && !opeProductionPurchaseOrder.getPurchaseStatus().equals(ProductionPurchasEnums.PARTIAL_STORAGE.getValue())) {
            throw new SesWebRosException(ExceptionCodeEnums.STATUS_ILLEGAL.getCode(), ExceptionCodeEnums.STATUS_ILLEGAL.getMessage());
        }
        // ???????????? ????????????????????????????????????
        if (checkStatusChange(productionPurchaseId, inWhId)) {
            // ?????????
            opeProductionPurchaseOrder.setPurchaseStatus(ProductionPurchasEnums.HAS_BEEN_STORED.getValue());
        } else {
            // ????????????
            opeProductionPurchaseOrder.setPurchaseStatus(ProductionPurchasEnums.PARTIAL_STORAGE.getValue());
        }
        opeProductionPurchaseOrder.setUpdatedBy(userId);
        opeProductionPurchaseOrder.setUpdatedTime(new Date());
        opeProductionPurchaseOrderService.saveOrUpdate(opeProductionPurchaseOrder);
        //????????????
        OrderStatusFlowEnter orderStatusFlowEnter = new OrderStatusFlowEnter(null, opeProductionPurchaseOrder.getPurchaseStatus(), OrderTypeEnums.FACTORY_PURCHAS.getValue(), opeProductionPurchaseOrder.getId(), null);
        orderStatusFlowEnter.setId(null);
        orderStatusFlowEnter.setUserId(userId);
        orderStatusFlowService.save(orderStatusFlowEnter);
    }

    /**
     * ???????????????????????????(?????????rps??????)
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult generatorQcOrderByCombin(IdEnter enter) {
        log.info("???????????????????????????(?????????rps??????)????????????:[{}]", enter);
        OpeCombinOrder combinOrder = opeCombinOrderMapper.selectById(enter.getId());
        if (StringManaConstant.entityIsNull(combinOrder)) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }

        Integer combinType = combinOrder.getCombinType();
        // ???????????????????????????
        switch (combinType) {
            case 1:
                // ?????????????????????????????????
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

                // ???????????????id???????????????????????????
                LambdaQueryWrapper<OpeCombinOrderScooterB> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(OpeCombinOrderScooterB::getDr, DelStatusEnum.VALID.getCode());
                wrapper.eq(OpeCombinOrderScooterB::getCombinId, combinOrder.getId());
                List<OpeCombinOrderScooterB> list = opeCombinOrderScooterBMapper.selectList(wrapper);
                if (CollectionUtils.isNotEmpty(list)) {
                    List<OpeQcScooterB> saveList = Lists.newArrayList();
                    for (OpeCombinOrderScooterB scooter : list) {
                        // ????????????????????????????????????
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
            // ??????????????????????????????
            case 2:
                // ????????????????????????????????????
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

                // ???????????????id??????????????????????????????
                LambdaQueryWrapper<OpeCombinOrderCombinB> qw = new LambdaQueryWrapper<>();
                qw.eq(OpeCombinOrderCombinB::getDr, DelStatusEnum.VALID.getCode());
                qw.eq(OpeCombinOrderCombinB::getCombinId, combinOrder.getId());
                List<OpeCombinOrderCombinB> tempList = opeCombinOrderCombinBMapper.selectList(qw);
                if (CollectionUtils.isNotEmpty(tempList)) {
                    List<OpeQcCombinB> saveList = Lists.newArrayList();
                    for (OpeCombinOrderCombinB combin : tempList) {
                        // ???????????????????????????????????????
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
     * ???????????????????????????(?????????rps??????)
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult generatorQcOrderByOutBound(IdEnter enter) {
        log.info("???????????????????????????(?????????rps??????)????????????:[{}]", enter);
        OpeOutWhouseOrder outOrder = opeOutWhouseOrderMapper.selectById(enter.getId());
        if (StringManaConstant.entityIsNull(outOrder)) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }

        // ????????????????????????????????????
        if (StringManaConstant.entityIsNotNull(outOrder) && 3 == outOrder.getRelationType()) {
            Integer outWhType = outOrder.getOutWhType();
            switch (outWhType) {
                case 1:
                    // ?????????????????????????????????
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

                    // ???????????????id???????????????????????????
                    LambdaQueryWrapper<OpeOutWhScooterB> wrapper = new LambdaQueryWrapper<>();
                    wrapper.eq(OpeOutWhScooterB::getDr, DelStatusEnum.VALID.getCode());
                    wrapper.eq(OpeOutWhScooterB::getOutWhId, outOrder.getId());
                    List<OpeOutWhScooterB> list = opeOutWhScooterBMapper.selectList(wrapper);
                    if (CollectionUtils.isNotEmpty(list)) {
                        List<OpeQcScooterB> saveList = Lists.newArrayList();
                        for (OpeOutWhScooterB scooter : list) {
                            // ????????????????????????????????????
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
                    // ????????????????????????????????????
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

                    // ???????????????id??????????????????????????????
                    LambdaQueryWrapper<OpeOutWhCombinB> qw = new LambdaQueryWrapper<>();
                    qw.eq(OpeOutWhCombinB::getDr, DelStatusEnum.VALID.getCode());
                    qw.eq(OpeOutWhCombinB::getOutWhId, outOrder.getId());
                    List<OpeOutWhCombinB> tempList = opeOutWhCombinBMapper.selectList(qw);
                    if (CollectionUtils.isNotEmpty(tempList)) {
                        List<OpeQcCombinB> saveList = Lists.newArrayList();
                        for (OpeOutWhCombinB combin : tempList) {
                            // ???????????????????????????????????????
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
                    // ?????????????????????????????????
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

                    // ???????????????id???????????????????????????
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
     * ????????????
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult arrived(IdEnter enter) {
        OpeProductionPurchaseOrder order = opeProductionPurchaseOrderService.getById(enter.getId());
        if (StringManaConstant.entityIsNull(order)) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }

        order.setPurchaseStatus(NewProductionPurchasEnums.ARRIVED.getValue());
        order.setUpdatedBy(enter.getId());
        order.setUpdatedTime(new Date());
        opeProductionPurchaseOrderService.updateById(order);
        //????????????
        SaveOpTraceEnter saveOpTraceEnter = new SaveOpTraceEnter(null, order.getId(), OrderTypeEnums.FACTORY_PURCHAS.getValue(), OrderOperationTypeEnums.SIGN_FOR.getValue(), null);
        BeanUtils.copyProperties(enter, saveOpTraceEnter);
        saveOpTraceEnter.setId(null);
        productionOrderTraceService.save(saveOpTraceEnter);

        //????????????
        OrderStatusFlowEnter orderStatusFlowEnter = new OrderStatusFlowEnter(null, order.getPurchaseStatus(), OrderTypeEnums.FACTORY_PURCHAS.getValue(), order.getId(), null);
        BeanUtils.copyProperties(enter, orderStatusFlowEnter);
        orderStatusFlowEnter.setId(null);
        orderStatusFlowService.save(orderStatusFlowEnter);

        // ?????????????????????????????????
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
        model.setRelationOrderId(order.getId());
        model.setRelationOrderNo(order.getPurchaseNo());
        model.setRelationOrderType(OrderTypeEnums.FACTORY_PURCHAS.getValue());
        model.setQcType(QcTypeEnums.PURCHASE.getValue());
        model.setQcQty(order.getPurchaseQty());
        model.setCreatedBy(enter.getUserId());
        model.setCreatedTime(new Date());
        opeQcOrderMapper.insert(model);

        // ???????????????id???????????????????????????????????????
        LambdaQueryWrapper<OpeProductionPurchasePartsB> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OpeProductionPurchasePartsB::getDr, DelStatusEnum.VALID.getCode());
        wrapper.eq(OpeProductionPurchasePartsB::getProductionPurchaseId, order.getId());
        List<OpeProductionPurchasePartsB> list = opeProductionPurchasePartsBMapper.selectList(wrapper);
        if (CollectionUtils.isNotEmpty(list)) {
            List<OpeQcPartsB> saveList = Lists.newArrayList();
            for (OpeProductionPurchasePartsB part : list) {
                // ???????????????????????????
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

    /**
     * ??????groupId???colorId????????????bom???id
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
            if (StringManaConstant.entityIsNotNull(bom)) {
                return bom.getId();
            }
        }
        throw new SesWebRosException(ExceptionCodeEnums.BOM_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.BOM_IS_NOT_EXIST.getMessage());
    }

    /**
     * ???????????????id???????????????no
     */
    private String getCombinBomNoById(Long id) {
        OpeProductionCombinBom bom = opeProductionCombinBomService.getById(id);
        if (StringManaConstant.entityIsNotNull(bom)) {
            return bom.getBomNo();
        }
        throw new SesWebRosException(ExceptionCodeEnums.BOM_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.BOM_IS_NOT_EXIST.getMessage());
    }

    // ??????????????????????????????????????????
    public boolean checkStatusChange(Long productionPurchaseId, Long inWhId) {
        boolean flag = true;
        // ??????????????????????????????????????????
        QueryWrapper<OpeProductionPurchasePartsB> qw = new QueryWrapper<>();
        qw.eq(OpeProductionPurchasePartsB.COL_PRODUCTION_PURCHASE_ID, productionPurchaseId);
        List<OpeProductionPurchasePartsB> purchasePartsBs = opeProductionPurchasePartsBService.list(qw);
        if (CollectionUtils.isNotEmpty(purchasePartsBs)) {
            // ???????????????????????????????????????????????? ???????????????????????????????????????  ????????? ???????????????  ??????false
            QueryWrapper<OpeInWhouseOrder> inWhouseOrderQueryWrapper = new QueryWrapper<>();
            inWhouseOrderQueryWrapper.eq(OpeInWhouseOrder.COL_RELATION_ORDER_ID, productionPurchaseId);
            inWhouseOrderQueryWrapper.ne(OpeInWhouseOrder.COL_ID, inWhId);
            inWhouseOrderQueryWrapper.lt(OpeInWhouseOrder.COL_IN_WH_STATUS, NewInWhouseOrderStatusEnum.ALREADY_IN_WHOUSE.getValue());
            int inWhNum = opeInWhouseOrderService.count(inWhouseOrderQueryWrapper);
            if (0 < inWhNum) {
                flag = false;
                return flag;
            }
            // ????????????????????????????????????????????? ?????????????????????????????????????????????????????????????????????  ?????????????????? ???????????????  ??????true
            // 1????????????????????????????????????????????????id????????????
            Map<Long, List<OpeProductionPurchasePartsB>> purchasePartsMap = purchasePartsBs.stream().collect(Collectors.groupingBy(OpeProductionPurchasePartsB::getPartsId));
            // 2?????????????????????????????????????????????????????????
            List<OpeInWhousePartsB> inWhousePartsBList = inWhouseOrderServiceMapper.inWhousePartsList(productionPurchaseId);
            if (CollectionUtils.isEmpty(inWhousePartsBList)) {
                flag = false;
                return flag;
            }
            // 3??????????????????????????????????????????????????? ????????????id????????????
            Map<Long, List<OpeInWhousePartsB>> inWhousePartsMap = inWhousePartsBList.stream().collect(Collectors.groupingBy(OpeInWhousePartsB::getPartsId));
            if (purchasePartsMap.size() > inWhousePartsMap.size()) {
                // ????????????????????????????????????????????????????????????  ?????????????????????????????????????????????????????????????????????????????????
                flag = false;
                return flag;
            }
            for (Long key1 : purchasePartsMap.keySet()) {
                int purchasePartsNum = purchasePartsMap.get(key1).stream().mapToInt(OpeProductionPurchasePartsB::getQty).sum();
                for (Long key2 : inWhousePartsMap.keySet()) {
                    if (key1.equals(key2)) {
                        int inWhousePartsNum = inWhousePartsMap.get(key2).stream().mapToInt(OpeInWhousePartsB::getActInWhQty).sum();
                        if (purchasePartsNum > inWhousePartsNum) {
                            // ??????????????????????????????????????????????????????????????????????????????????????????  ??????false
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
