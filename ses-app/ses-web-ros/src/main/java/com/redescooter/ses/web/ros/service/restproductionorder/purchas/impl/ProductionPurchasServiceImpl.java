package com.redescooter.ses.web.ros.service.restproductionorder.purchas.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.redescooter.ses.api.common.enums.restproductionorder.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.enums.restproductionorder.InWhouseOrderStatusEnum;
import com.redescooter.ses.api.common.enums.restproductionorder.OrderOperationTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.OrderTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.PaymentTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.productionpurchas.ProductionPurchasEnums;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.DateUtil;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.dao.restproductionorder.InWhouseOrderServiceMapper;
import com.redescooter.ses.web.ros.dao.restproductionorder.ProductionPurchasServiceMapper;
import com.redescooter.ses.web.ros.dm.*;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.*;
import com.redescooter.ses.web.ros.service.restproductionorder.inwhouse.InWhouseService;
import com.redescooter.ses.web.ros.service.restproductionorder.inwhouse.InWhouseService;
import com.redescooter.ses.web.ros.service.restproductionorder.number.OrderNumberService;
import com.redescooter.ses.web.ros.service.restproductionorder.orderflow.OrderStatusFlowService;
import com.redescooter.ses.web.ros.service.restproductionorder.purchas.ProductionPurchasService;
import com.redescooter.ses.web.ros.service.restproductionorder.trace.ProductionOrderTraceService;
import com.redescooter.ses.web.ros.service.sys.StaffService;
import com.redescooter.ses.web.ros.vo.bo.PartDetailDto;
import com.redescooter.ses.web.ros.vo.restproductionorder.AssociatedOrderResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.SupplierListResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.SupplierPrincipaleResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.inwhouse.InWhouseSaveOrUpdateEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.inwhouse.SaveOrUpdatePartsBEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.inwhouse.InWhouseSaveOrUpdateEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.inwhouse.SaveOrUpdatePartsBEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.number.OrderNumberEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.optrace.ListByBussIdEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.optrace.SaveOpTraceEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.orderflow.OrderStatusFlowEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.purchass.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName:ProductionPurchasServiceImpl
 * @description: ProductionPurchasServiceImpl
 * @author: Alex
 * @Version：1.3
 * @create: 2020/11/11 11:43 
 */
@Service
@Slf4j
public class ProductionPurchasServiceImpl implements ProductionPurchasService {

    @Autowired
    private OpeSupplierService opeSupplierService;

    @Autowired
    private OpeProductionPurchaseOrderService opeProductionPurchaseOrderService;

    @Autowired
    private ProductionPurchasServiceMapper productionPurchasServiceMapper;

    @Autowired
    private OrderStatusFlowService orderStatusFlowService;

    @Autowired
    private ProductionOrderTraceService productionOrderTraceService;

    @Autowired
    private OpeInWhouseOrderService opeInWhouseOrderService;

    @Autowired
    private OpeProductionPartsService opeProductionPartsService;

    @Autowired
    private OrderNumberService orderNumberService;

    @Autowired
    private OpeProductionPurchasePartsBService opeProductionPurchasePartsBService;

    @Autowired
    private InWhouseService inWhouseService;

    @Autowired
    private OpeSysStaffService opeSysStaffService;

    @Autowired
    private InWhouseOrderServiceMapper inWhouseOrderServiceMapper;

    @Reference
    private IdAppService idAppService;

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/11/10 2:52 下午
     * @Param: enter
     * @Return: ProductionPurchasListResult
     * @desc: 列表
     * @param enter
     */
    @Override
    public PageResult<ProductionPurchasListResult> list(ProductionPurchasListEnter enter) {
        int count=productionPurchasServiceMapper.listCount(enter);
        if (count==0){
            return PageResult.createZeroRowResult(enter);
        }
        return PageResult.create(enter,count,productionPurchasServiceMapper.list(enter));
    }

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/11/10 3:06 下午
     * @Param: enter
     * @Return: ProductionPurchasDetailResult
     * @desc: 详情
     * @param enter
     */
    @Override
    public ProductionPurchasDetailResult detail(IdEnter enter) {
        ProductionPurchasDetailResult  productionPurchasDetailResult=productionPurchasServiceMapper.detail(enter);
        if (Objects.isNull(productionPurchasDetailResult)){
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(),ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        OpeProductionPurchaseOrder opeProductionPurchaseOrder = opeProductionPurchaseOrderService.getById(enter.getId());
        productionPurchasDetailResult.setPaymentDate(new SavePurchasPaymentEnter(opeProductionPurchaseOrder.getPaymentType(),opeProductionPurchaseOrder.getPlannedPaymentTime(),
                opeProductionPurchaseOrder.getAmountType(),opeProductionPurchaseOrder.getPaymentDay(),opeProductionPurchaseOrder.getPrePayRatio().intValue(),opeProductionPurchaseOrder.getPayAmount(),opeProductionPurchaseOrder.getPaymentTime()));
        productionPurchasDetailResult.setProductList(this.detailProductList(enter));
        productionPurchasDetailResult.setOperatingDynamicsList(productionOrderTraceService.listByBussId(new ListByBussIdEnter(enter.getId(), OrderTypeEnums.FACTORY_PURCHAS.getValue())));
        productionPurchasDetailResult.setAssociatedOrderResultList(this.associatedOrder(enter));
        return productionPurchasDetailResult;
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
    public List<PurchasDetailProductListResult> detailProductList(IdEnter enter) {
        List<PurchasDetailProductListResult> resultList = productionPurchasServiceMapper.detailProductList(enter);
        return CollectionUtils.isEmpty(resultList) ? new ArrayList<>() : resultList;
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
        List<AssociatedOrderResult> resultList = new ArrayList<> ();
        List<OpeInWhouseOrder> opeInWhouseOrderList = opeInWhouseOrderService.list(new LambdaQueryWrapper<OpeInWhouseOrder>()
                .eq(OpeInWhouseOrder::getRelationOrderId, enter.getId())
                .eq(OpeInWhouseOrder::getRelationOrderType, OrderTypeEnums.FACTORY_PURCHAS.getValue()));
        if (CollectionUtils.isNotEmpty(opeInWhouseOrderList)){
            opeInWhouseOrderList.forEach(item->{
                resultList.add(new AssociatedOrderResult(item.getId(),item.getInWhNo(),OrderTypeEnums.FACTORY_INBOUND.getValue(), item.getCreatedTime(),null));
            });
            return resultList;
        }
        return new ArrayList<>();
    }

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/11/10 4:08 下午
     * @Param: enter
     * @Return: GeneralResult
     * @desc: 保存采购单
     * @param enter
     */
    @Transactional
    @Override
    public GeneralResult save(SaveProductionPurchasEnter enter) {
        List<SavePurchasProductEnter> productList=new ArrayList<> ();
        SavePurchasPaymentEnter paymentEnter=null;
        try {
            paymentEnter=JSON.parseObject(enter.getPaymentDate(),SavePurchasPaymentEnter.class);
            productList.addAll(JSON.parseArray(enter.getSt(),SavePurchasProductEnter.class));
        }catch (Exception e) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(),ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }
        if (CollectionUtils.isEmpty(productList) || Objects.isNull(paymentEnter)){
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(),ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }

        //校验json 数据
        List<PartDetailDto> PartDetailDtoList = checkJsonDate(productList, paymentEnter);

        Long purchasId=idAppService.getId(SequenceName.OPE_PRODUCTION_PURCHASE_ORDER);

        Long totalPrice =0L;

        //子订单保存
        List<OpeProductionPurchasePartsB> saveOpeProductionPurchasePartsBList = new ArrayList<>();
        for (SavePurchasProductEnter item : productList) {
            PartDetailDto partDetailDto = PartDetailDtoList.stream().filter(part -> item.getId().equals(part.getPartId())).findFirst().orElse(null);
            //封装子单据
            OpeProductionPurchasePartsB opeProductionPurchasePartsB = buildOpeProductionPurchasePartsB(enter, (enter.getId()==null||enter.getId()==0)?purchasId:enter.getId(), item, partDetailDto);
            saveOpeProductionPurchasePartsBList.add(opeProductionPurchasePartsB);
            Long price = partDetailDto.getPrice().multiply(new BigDecimal(item.getQty())).longValue();
            totalPrice += price;
        }


        SaveOpTraceEnter saveOpTraceEnter = null;
        OpeProductionPurchaseOrder opeProductionPurchaseOrder = buildOpeProductionPurchaseOrder(enter, productList, paymentEnter, new BigDecimal(totalPrice));
        if (enter.getId()==null || enter.getId() == 0){
            opeProductionPurchaseOrder.setId(purchasId);
            opeProductionPurchaseOrder.setCreatedBy(enter.getUserId());
            opeProductionPurchaseOrder.setCreatedTime(new Date());
            opeProductionPurchaseOrder.setPurchaseNo(orderNumberService.generateOrderNo(new OrderNumberEnter(OrderTypeEnums.FACTORY_PURCHAS.getValue())));

            //订单节点
            OrderStatusFlowEnter orderStatusFlowEnter = new OrderStatusFlowEnter(null, opeProductionPurchaseOrder.getPurchaseStatus(), OrderTypeEnums.FACTORY_PURCHAS.getValue(), opeProductionPurchaseOrder.getId(),
                    null);
            BeanUtils.copyProperties(enter, orderStatusFlowEnter);
            orderStatusFlowEnter.setId(null);
            orderStatusFlowService.save(orderStatusFlowEnter);

            //操作动态
            saveOpTraceEnter = new SaveOpTraceEnter(null, opeProductionPurchaseOrder.getId(), OrderTypeEnums.FACTORY_PURCHAS.getValue(), OrderOperationTypeEnums.CREATE.getValue(), enter.getRemark());

        }else {
            //删除子单据
            opeProductionPurchasePartsBService.remove(new LambdaQueryWrapper<OpeProductionPurchasePartsB>().eq(OpeProductionPurchasePartsB::getProductionPurchaseId,enter.getId()));
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
        if (StringUtils.isEmpty(enter.getFactoryPrincipalName()) && enter.getFactoryId()!=null && enter.getFactoryId()!=0){
            OpeSupplier opeSupplier = opeSupplierService.getById(enter.getFactoryId());
            if (Objects.isNull(opeSupplier)){
                throw new SesWebRosException(ExceptionCodeEnums.FACTORY_IS_NOT_EXIST.getCode(),ExceptionCodeEnums.FACTORY_IS_NOT_EXIST.getMessage());
            }
            enter.setFactoryPrincipalName(opeSupplier.getContactFullName());
        }
        if (StringUtils.isEmpty(enter.getDockingUserName()) && enter.getDockingUser()!=null && enter.getDockingUser()!=0){
            OpeSysStaff opeSysStaff = opeSysStaffService.getById(enter.getDockingUser());
            if (opeSysStaff==null){
                throw new SesWebRosException(ExceptionCodeEnums.EMPLOYEE_IS_NOT_EXIST.getCode(),ExceptionCodeEnums.EMPLOYEE_IS_NOT_EXIST.getMessage());
            }
            enter.setDockingUserName(opeSysStaff.getFirstName()+" "+opeSysStaff.getLastName());

        }
        if (StringUtils.isEmpty(enter.getConsigneeUserName()) && enter.getConsigneeUser()!=null && enter.getConsigneeUser()!=0){
            OpeSysStaff opeSysStaff = opeSysStaffService.getById(enter.getConsigneeUser());
            if (opeSysStaff==null){
                throw new SesWebRosException(ExceptionCodeEnums.EMPLOYEE_IS_NOT_EXIST.getCode(),ExceptionCodeEnums.EMPLOYEE_IS_NOT_EXIST.getMessage());
            }
            enter.setConsigneeUserName(opeSysStaff.getFirstName()+" "+opeSysStaff.getLastName());
        }

        OpeProductionPurchaseOrder opeProductionPurchaseOrder = new OpeProductionPurchaseOrder();
        BeanUtils.copyProperties(enter,opeProductionPurchaseOrder);
        opeProductionPurchaseOrder.setDr(0);
        opeProductionPurchaseOrder.setPurchaseStatus(ProductionPurchasEnums.DRAFT.getValue());
        opeProductionPurchaseOrder.setPurchaseQty(productList.stream().mapToInt(SavePurchasProductEnter::getQty).sum());
        opeProductionPurchaseOrder.setPurchaseContract(enter.getContract());
        opeProductionPurchaseOrder.setPaymentType(paymentEnter.getPaymentType());
        opeProductionPurchaseOrder.setPlannedPaymentTime(paymentEnter.getDate());
        opeProductionPurchaseOrder.setPaymentDay(paymentEnter.getDays());
        if(paymentEnter.getDays() != null && paymentEnter.getDate() != null){
            opeProductionPurchaseOrder.setPaymentTime(DateUtil.addDays(paymentEnter.getDate(),paymentEnter.getDays()));
        }
        opeProductionPurchaseOrder.setPrePayRatio(new BigDecimal(paymentEnter.getPercentage()));
        opeProductionPurchaseOrder.setPurchaseAmount(totalPrice);
        opeProductionPurchaseOrder.setAmountType(paymentEnter.getAmountType());

        if (PaymentTypeEnums.PREPAYMENTS.getValue().equals(paymentEnter.getPaymentType()) && paymentEnter.getAmount()!=null){
            if (totalPrice.subtract(paymentEnter.getAmount()).longValue()<0){
                throw new SesWebRosException(ExceptionCodeEnums.PAY_AMOUNT_IS_FALSE.getCode(),ExceptionCodeEnums.PAY_AMOUNT_IS_FALSE.getMessage());
            }
            opeProductionPurchaseOrder.setPayAmount(paymentEnter.getAmount());
        }
        return opeProductionPurchaseOrder;
    }

    @Override
    public PageResult<PurchasPartListResult> purchasPartList(PurchasPartListEnter enter) {
       int  count=productionPurchasServiceMapper.purchasPartListCount(enter);
       if (count==0){
          return PageResult.createZeroRowResult(enter);
       }
       return PageResult.create(enter,count,productionPurchasServiceMapper.purchasPartList(enter));
    }

    /**
    * @Description
    * @Author: alex
    * @Date:   2020/11/11 7:15 下午
    * @Param:  productList,paymentEnter
    * @Return: List<OpeProductionParts>
    * @desc: 校验json 数据
    */
    private List<PartDetailDto> checkJsonDate(List<SavePurchasProductEnter> productList, SavePurchasPaymentEnter paymentEnter) {
      List<PartDetailDto>  PartDetailDtoList=productionPurchasServiceMapper.partDetailList(productList.stream().map(SavePurchasProductEnter::getId).collect(Collectors.toSet()));
        productList.forEach(item->{
            PartDetailDto partDetailDto = PartDetailDtoList.stream().filter(product -> item.getId().equals(product.getPartId())).findFirst().orElse(null);
            if (Objects.isNull(partDetailDto)){
                throw new SesWebRosException(ExceptionCodeEnums.PART_IS_NOT_EXIST.getCode(),ExceptionCodeEnums.PART_IS_NOT_EXIST.getMessage());
            }
            if (Objects.isNull(item.getQty()) || item.getQty().equals(0)){
                throw new SesWebRosException(ExceptionCodeEnums.QTY_IS_ILLEGAL.getCode(),ExceptionCodeEnums.QTY_IS_ILLEGAL.getMessage());
            }
        });
        return PartDetailDtoList;
    }

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/11/11 3:21 下午
     * @Param: enter
     * @Return: SupplierListResult
     * @desc: 供应商列表
     * @param enter
     */
    @Override
    public List<SupplierListResult> supplierList(GeneralEnter enter) {
        List<SupplierListResult> resultList=new ArrayList<>();
        List<OpeSupplier> opeSupplierList = opeSupplierService.list();
        if (CollectionUtils.isNotEmpty(opeSupplierList)){
            opeSupplierList.forEach(item->{
                List<SupplierPrincipaleResult> supplierList = new ArrayList<>();
                supplierList.add(new SupplierPrincipaleResult(item.getId(),item.getContactFullName(),item.getContactPhoneCountryCode(),item.getContactPhone()));
                resultList.add(new SupplierListResult(item.getId(),item.getSupplierName(),supplierList));
            });
        }
        return resultList;
    }

    /**
     * @Description
     * @Author: alexx
     * @Date: 2020/11/12 2:51 下午
     * @Param: enter
     * @Return: GeneralResult
     * @desc: 删除
     * @param enter
     */
    @Override
    public GeneralResult close(IdEnter enter) {
        OpeProductionPurchaseOrder opeProductionPurchaseOrder = opeProductionPurchaseOrderService.getById(enter.getId());
        if (Objects.isNull(opeProductionPurchaseOrder)){
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(),ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        if (!opeProductionPurchaseOrder.getPurchaseStatus().equals(ProductionPurchasEnums.HAS_BEEN_STORED.getValue())){
            throw new SesWebRosException(ExceptionCodeEnums.STATUS_ILLEGAL.getCode(),ExceptionCodeEnums.STATUS_ILLEGAL.getMessage());
        }
        opeProductionPurchaseOrder.setPurchaseStatus(ProductionPurchasEnums.FINISHED.getValue());
        opeProductionPurchaseOrder.setUpdatedBy(enter.getId());
        opeProductionPurchaseOrder.setUpdatedTime(new Date());
        opeProductionPurchaseOrderService.updateById(opeProductionPurchaseOrder);
        //操作动态
        SaveOpTraceEnter saveOpTraceEnter = new SaveOpTraceEnter(null, opeProductionPurchaseOrder.getId(), OrderTypeEnums.FACTORY_PURCHAS.getValue(), OrderOperationTypeEnums.CLOSE.getValue(), null);
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
     * @Description
     * @Author: alex
     * @Date: 2020/11/12 2:52 下午
     * @Param: enter
     * @Return: GeneralResult
     * @desc: 取消订单
     * @param enter
     */
    @Override
    public GeneralResult cancel(IdEnter enter) {
        OpeProductionPurchaseOrder opeProductionPurchaseOrder = opeProductionPurchaseOrderService.getById(enter.getId());
        if (Objects.isNull(opeProductionPurchaseOrder)){
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(),ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        if (!opeProductionPurchaseOrder.getPurchaseStatus().equals(ProductionPurchasEnums.PURCHASING.getValue())){
            throw new SesWebRosException(ExceptionCodeEnums.STATUS_ILLEGAL.getCode(),ExceptionCodeEnums.STATUS_ILLEGAL.getMessage());
        }
        opeProductionPurchaseOrder.setPurchaseStatus(ProductionPurchasEnums.CANCEL.getValue());
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
     * @Description
     * @Author: alex
     * @Date: 2020/11/12 2:53 下午
     * @Param: enter
     * @Return: GeneralResult
     * @desc: 下单
     * @param enter
     */
    @Override
    public GeneralResult delete(IdEnter enter) {
        OpeProductionPurchaseOrder opeProductionPurchaseOrder = opeProductionPurchaseOrderService.getById(enter.getId());
        if (Objects.isNull(opeProductionPurchaseOrder)){
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(),ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        if (!opeProductionPurchaseOrder.getPurchaseStatus().equals(ProductionPurchasEnums.DRAFT.getValue())){
            throw new SesWebRosException(ExceptionCodeEnums.STATUS_ILLEGAL.getCode(),ExceptionCodeEnums.STATUS_ILLEGAL.getMessage());
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
     * @Description
     * @Author: alex
     * @Date: 2020/11/12 2:53 下午
     * @Param: enter
     * @Return: GeneralResult
     * @desc: 下单
     * @param enter
     */
    @Override
    public GeneralResult bookOrder(IdEnter enter) {
        OpeProductionPurchaseOrder opeProductionPurchaseOrder = opeProductionPurchaseOrderService.getById(enter.getId());
        if (Objects.isNull(opeProductionPurchaseOrder)){
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(),ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        if (!opeProductionPurchaseOrder.getPurchaseStatus().equals(ProductionPurchasEnums.DRAFT.getValue())){
            throw new SesWebRosException(ExceptionCodeEnums.STATUS_ILLEGAL.getCode(),ExceptionCodeEnums.STATUS_ILLEGAL.getMessage());
        }
        opeProductionPurchaseOrder.setPurchaseStatus(ProductionPurchasEnums.PURCHASING.getValue());
        opeProductionPurchaseOrder.setUpdatedBy(enter.getId());
        opeProductionPurchaseOrder.setUpdatedTime(new Date());
        opeProductionPurchaseOrderService.updateById(opeProductionPurchaseOrder);
        //操作动态
        SaveOpTraceEnter saveOpTraceEnter = new SaveOpTraceEnter(null, opeProductionPurchaseOrder.getId(), OrderTypeEnums.FACTORY_PURCHAS.getValue(), OrderOperationTypeEnums.PLACE_ORDER.getValue(), null);
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


    // 部件入库单准备质检时，将关联的部件入库单的状态变为待入库
    @Override
    @Transactional
    public void statusToBeStored(Long productionPurchaseId, Long userId) {
        OpeProductionPurchaseOrder opeProductionPurchaseOrder = opeProductionPurchaseOrderService.getById(productionPurchaseId);
        if (Objects.isNull(opeProductionPurchaseOrder)){
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(),ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        if(opeProductionPurchaseOrder.getPurchaseStatus().equals(ProductionPurchasEnums.TO_BE_STORED.getValue())
        || opeProductionPurchaseOrder.getPurchaseStatus().equals(ProductionPurchasEnums.PARTIAL_STORAGE.getValue())){
            return;
        }
        if (!opeProductionPurchaseOrder.getPurchaseStatus().equals(ProductionPurchasEnums.PURCHASING.getValue())){
            throw new SesWebRosException(ExceptionCodeEnums.STATUS_ILLEGAL.getCode(),ExceptionCodeEnums.STATUS_ILLEGAL.getMessage());
        }
        opeProductionPurchaseOrder.setPurchaseStatus(ProductionPurchasEnums.TO_BE_STORED.getValue());
        opeProductionPurchaseOrder.setUpdatedBy(userId);
        opeProductionPurchaseOrder.setUpdatedTime(new Date());
        opeProductionPurchaseOrderService.saveOrUpdate(opeProductionPurchaseOrder);
        //订单节点
        OrderStatusFlowEnter orderStatusFlowEnter = new OrderStatusFlowEnter(null, opeProductionPurchaseOrder.getPurchaseStatus(), OrderTypeEnums.FACTORY_PURCHAS.getValue(), opeProductionPurchaseOrder.getId(),
                null);
        orderStatusFlowEnter.setId(null);
        orderStatusFlowEnter.setUserId(userId);
        orderStatusFlowService.save(orderStatusFlowEnter);
    }


    // 部件入库单确认入库时，将关联的部件入库单的状态变为部分入库或已入库
    @Override
    public void statusToPartWhOrAllInWh(Long productionPurchaseId,Long inWhId, Long userId) {
        OpeProductionPurchaseOrder opeProductionPurchaseOrder = opeProductionPurchaseOrderService.getById(productionPurchaseId);
        if (Objects.isNull(opeProductionPurchaseOrder)){
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(),ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        if (!opeProductionPurchaseOrder.getPurchaseStatus().equals(ProductionPurchasEnums.TO_BE_STORED.getValue()) && !opeProductionPurchaseOrder.getPurchaseStatus().equals(ProductionPurchasEnums.PARTIAL_STORAGE.getValue())){
            throw new SesWebRosException(ExceptionCodeEnums.STATUS_ILLEGAL.getCode(),ExceptionCodeEnums.STATUS_ILLEGAL.getMessage());
        }
        // 判断一波 是变成部分入库还是已入库
        if (checkStatusChange(productionPurchaseId,inWhId)){
            // 已入库
            opeProductionPurchaseOrder.setPurchaseStatus(ProductionPurchasEnums.HAS_BEEN_STORED.getValue());
        }else {
            // 部分入库
            opeProductionPurchaseOrder.setPurchaseStatus(ProductionPurchasEnums.PARTIAL_STORAGE.getValue());
        }
        opeProductionPurchaseOrder.setUpdatedBy(userId);
        opeProductionPurchaseOrder.setUpdatedTime(new Date());
        opeProductionPurchaseOrderService.saveOrUpdate(opeProductionPurchaseOrder);
        //订单节点
        OrderStatusFlowEnter orderStatusFlowEnter = new OrderStatusFlowEnter(null, opeProductionPurchaseOrder.getPurchaseStatus(), OrderTypeEnums.FACTORY_PURCHAS.getValue(), opeProductionPurchaseOrder.getId(),
                null);
        orderStatusFlowEnter.setId(null);
        orderStatusFlowEnter.setUserId(userId);
        orderStatusFlowService.save(orderStatusFlowEnter);
    }


    // 校验本次是部分入库还是已入库
    public boolean checkStatusChange(Long productionPurchaseId,Long inWhId){
        boolean flag = true;
        // 找到部件采购单下面的部件明细
        QueryWrapper<OpeProductionPurchasePartsB> qw = new QueryWrapper<>();
        qw.eq(OpeProductionPurchasePartsB.COL_PRODUCTION_PURCHASE_ID,productionPurchaseId);
        List<OpeProductionPurchasePartsB>  purchasePartsBs = opeProductionPurchasePartsBService.list(qw);
        if (CollectionUtils.isNotEmpty(purchasePartsBs)){
            // 先看看这个采购单下面有没有还没有 没有已入库状态之前的入库单  有的话 为部分入库  返回false
            QueryWrapper<OpeInWhouseOrder> inWhouseOrderQueryWrapper = new QueryWrapper<>();
            inWhouseOrderQueryWrapper.eq(OpeInWhouseOrder.COL_RELATION_ORDER_ID,productionPurchaseId);
            inWhouseOrderQueryWrapper.ne(OpeInWhouseOrder.COL_ID,inWhId);
            inWhouseOrderQueryWrapper.lt(OpeInWhouseOrder.COL_IN_WH_STATUS, InWhouseOrderStatusEnum.ALREADY_IN_WHOUSE.getValue());
            int inWhNum = opeInWhouseOrderService.count(inWhouseOrderQueryWrapper);
            if (inWhNum > 0){
                flag = false;
                return flag;
            }
            // 说明采购单下面的入库单都签收了 再判断“实际入库数量”是否大于等于“采购数量”  如果大于等于 则为已入库  返回true
            // 1、对采购单下面的部件信息按照部件id进行分组
            Map<Long, List<OpeProductionPurchasePartsB>> purchasePartsMap = purchasePartsBs.stream().collect(Collectors.groupingBy(OpeProductionPurchasePartsB::getPartsId));
            // 2、找到采购单下面的入库单的所有部件信息
            List<OpeInWhousePartsB> inWhousePartsBList = inWhouseOrderServiceMapper.inWhousePartsList(productionPurchaseId);
            if (CollectionUtils.isEmpty(inWhousePartsBList)){
                flag = false;
                return flag;
            }
            // 3、采购单下面的入库单的所有部件信息 按照部件id进行分组
            Map<Long, List<OpeInWhousePartsB>> inWhousePartsMap = inWhousePartsBList.stream().collect(Collectors.groupingBy(OpeInWhousePartsB::getPartsId));
            if (purchasePartsMap.size() > inWhousePartsMap.size()){
                // 如果生产采购单下面的部件种类比入库单的多  说明生产采购单还没有入库单（还需要继续产生入库单才行）
                flag = false;
                return flag;
            }
            for (Long key1 : purchasePartsMap.keySet()) {
                int purchasePartsNum = purchasePartsMap.get(key1).stream().mapToInt(OpeProductionPurchasePartsB::getQty).sum();
                for (Long key2 : inWhousePartsMap.keySet()) {
                    if (key1.equals(key2)){
                        int inWhousePartsNum = inWhousePartsMap.get(key2).stream().mapToInt(OpeInWhousePartsB::getActInWhQty).sum();
                        if (purchasePartsNum > inWhousePartsNum){
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