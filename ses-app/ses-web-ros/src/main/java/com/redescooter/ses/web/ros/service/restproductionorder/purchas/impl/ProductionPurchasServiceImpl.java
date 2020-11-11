package com.redescooter.ses.web.ros.service.restproductionorder.purchas.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.redescooter.ses.api.common.enums.production.PaymentTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.OrderTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.productionpurchas.ProductionPurchasEnums;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.dao.restproductionorder.ProductionPurchasServiceMapper;
import com.redescooter.ses.web.ros.dm.*;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.*;
import com.redescooter.ses.web.ros.service.restproductionorder.purchas.ProductionPurchasService;
import com.redescooter.ses.web.ros.service.restproductionorder.trace.ProductionOrderTraceService;
import com.redescooter.ses.web.ros.vo.restproductionorder.AssociatedOrderResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.SupplierListResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.SupplierPrincipaleResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.optrace.ListByBussIdEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.purchass.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
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
    private ProductionOrderTraceService productionOrderTraceService;

    @Autowired
    private OpeInWhouseOrderService opeInWhouseOrderService;

    @Autowired
    private OpeProductionPartsService opeProductionPartsService;

    @Autowired
    private OpeProductionPurchasePartsBService opeProductionPurchasePartsBService;


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
        OpeProductionPurchaseOrder opeProductionPurchaseOrder = opeProductionPurchaseOrderService.getById(enter.getId());
        if (opeProductionPurchaseOrder==null){
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(),ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        ProductionPurchasDetailResult productionPurchasDetailResult = new ProductionPurchasDetailResult();
        BeanUtils.copyProperties(enter,productionPurchasDetailResult);

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
                .eq(OpeInWhouseOrder::getRelationOrderType, OrderTypeEnums.FACTORY_INBOUND));
        if (CollectionUtils.isNotEmpty(opeInWhouseOrderList)){
            opeInWhouseOrderList.forEach(item->{
                resultList.add(new AssociatedOrderResult(item.getId(),item.getInWhNo(),item.getOrderType(),item.getCreatedTime(),null));
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
        List<OpeProductionParts> opeProductionPartList = checkJsonDate(productList, paymentEnter);

        OpeProductionPurchaseOrder opeProductionPurchaseOrder = new OpeProductionPurchaseOrder();
        BeanUtils.copyProperties(enter,opeProductionPurchaseOrder);
        opeProductionPurchaseOrder.setId(idAppService.getId(SequenceName.OPE_PRODUCTION_PURCHASE_ORDER));
        opeProductionPurchaseOrder.setDr(0);
        opeProductionPurchaseOrder.setPurchaseNo("42342442");
        opeProductionPurchaseOrder.setPurchaseStatus(ProductionPurchasEnums.DRAFT.getValue());
        opeProductionPurchaseOrder.setPurchaseQty(Long.valueOf(productList.stream().map(SavePurchasProductEnter::getQty).count()).intValue());
        opeProductionPurchaseOrder.setPurchaseContract(enter.getContract());
        opeProductionPurchaseOrder.setCreatedBy(enter.getUserId());
        opeProductionPurchaseOrder.setCreatedTime(new Date());
        opeProductionPurchaseOrder.setUpdatedBy(enter.getUserId());
        opeProductionPurchaseOrder.setUpdatedTime(new Date());
        opeProductionPurchaseOrderService.save(opeProductionPurchaseOrder);

        //子订单保存
        List<OpeProductionPurchasePartsB> saveOpeProductionPurchasePartsBList = new ArrayList<>();
        productList.forEach(item->{
            OpeProductionParts opeProductionParts = opeProductionPartList.stream().filter(part -> item.getId().equals(part.getId())).findFirst().orElse(null);
            OpeProductionPurchasePartsB opeProductionPurchasePartsB = new OpeProductionPurchasePartsB();
            opeProductionPurchasePartsB.setId(idAppService.getId(SequenceName.OPE_PRODUCTION_PURCHASE_PARTS_B));
            opeProductionPurchasePartsB.setDr(0);
            opeProductionPurchasePartsB.setPartsId(opeProductionParts.getId());
            opeProductionPurchasePartsB.setPartsName(opeProductionParts.getEnName());
            opeProductionPurchasePartsB.setPartsNo(opeProductionParts.getPartsNo());
            opeProductionPurchasePartsB.setPartsType(opeProductionParts.getPartsType());
            opeProductionPurchasePartsB.setQty(item.getQty());
            opeProductionPurchasePartsB.setUnitPrice(null);
            opeProductionPurchasePartsB.setRemark(null);
            opeProductionPurchasePartsB.setCreatedBy(enter.getUserId());
            opeProductionPurchasePartsB.setCreatedTime(new Date());
            opeProductionPurchasePartsB.setUpdatedBy(enter.getUserId());
            opeProductionPurchasePartsB.setUpdatedTime(new Date());
            saveOpeProductionPurchasePartsBList.add(opeProductionPurchasePartsB);
        });
        opeProductionPurchasePartsBService.saveBatch(saveOpeProductionPurchasePartsBList);
        return new GeneralResult(enter.getRequestId());
    }
    /**
    * @Description
    * @Author: alex
    * @Date:   2020/11/11 7:15 下午
    * @Param:  productList,paymentEnter
    * @Return: List<OpeProductionParts>
    * @desc: 校验json 数据
    */
    private List<OpeProductionParts> checkJsonDate(List<SavePurchasProductEnter> productList, SavePurchasPaymentEnter paymentEnter) {
        List<OpeProductionParts> opeProductionParts = opeProductionPartsService.listByIds(productList.stream().map(SavePurchasProductEnter::getId).collect(Collectors.toList()));
        productList.forEach(item->{
            OpeProductionParts opeProductionPart = opeProductionParts.stream().filter(product -> item.getId().equals(product.getId())).findFirst().orElse(null);
            if (Objects.isNull(opeProductionPart)){
                throw new SesWebRosException(ExceptionCodeEnums.PART_IS_NOT_EXIST.getCode(),ExceptionCodeEnums.PART_IS_NOT_EXIST.getMessage());
            }
            if (Objects.isNull(item.getQty()) || item.getQty().equals(0)){
                throw new SesWebRosException(ExceptionCodeEnums.QTY_IS_ILLEGAL.getCode(),ExceptionCodeEnums.QTY_IS_ILLEGAL.getMessage());
            }
        });

        switch (paymentEnter.getPaymentType()){
            case 1:
                if (StringUtils.isBlank(paymentEnter.getDate())){
                    throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(),ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
                }
                break;
            case 2:
                if (paymentEnter.getPercentage()==0){
                    throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(),ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
                }
                break;
            default :
                if (StringUtils.isBlank(paymentEnter.getAmount())){
                    throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(),ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
                }
                break;
        }
        return opeProductionParts;
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
                supplierList.add(new SupplierPrincipaleResult(item.getContactFirstName(),item.getContactLastName(),item.getContactPhoneCountryCode(),item.getContactPhone()));
                resultList.add(new SupplierListResult(item.getId(),item.getSupplierName(),supplierList));
            });
        }
        return resultList;
    }
}
