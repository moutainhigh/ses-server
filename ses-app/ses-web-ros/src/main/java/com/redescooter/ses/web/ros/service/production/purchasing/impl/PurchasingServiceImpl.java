package com.redescooter.ses.web.ros.service.production.purchasing.impl;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.enums.bom.BomCommonTypeEnums;
import com.redescooter.ses.api.common.enums.production.InOutWhEnums;
import com.redescooter.ses.api.common.enums.production.PurchasingTypeEnums;
import com.redescooter.ses.api.common.enums.production.SourceTypeEnums;
import com.redescooter.ses.api.common.enums.production.StockBillStatusEnums;
import com.redescooter.ses.api.common.enums.production.purchasing.PayStatusEnums;
import com.redescooter.ses.api.common.enums.production.purchasing.PaymentTypeEnums;
import com.redescooter.ses.api.common.enums.production.purchasing.PurchasingEventEnums;
import com.redescooter.ses.api.common.enums.production.purchasing.PurchasingStatusEnums;
import com.redescooter.ses.api.common.enums.production.purchasing.QcStatusEnums;
import com.redescooter.ses.api.common.enums.production.purchasing.WhseTypeEnums;
import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.dao.production.PurchasingServiceMapper;
import com.redescooter.ses.web.ros.dm.OpeFactory;
import com.redescooter.ses.web.ros.dm.OpeParts;
import com.redescooter.ses.web.ros.dm.OpePartsProduct;
import com.redescooter.ses.web.ros.dm.OpePartsProductB;
import com.redescooter.ses.web.ros.dm.OpePurchas;
import com.redescooter.ses.web.ros.dm.OpePurchasB;
import com.redescooter.ses.web.ros.dm.OpePurchasBQc;
import com.redescooter.ses.web.ros.dm.OpePurchasPayment;
import com.redescooter.ses.web.ros.dm.OpePurchasProduct;
import com.redescooter.ses.web.ros.dm.OpePurchasTrace;
import com.redescooter.ses.web.ros.dm.OpeStock;
import com.redescooter.ses.web.ros.dm.OpeStockBill;
import com.redescooter.ses.web.ros.dm.OpeSupplier;
import com.redescooter.ses.web.ros.dm.OpeSysUserProfile;
import com.redescooter.ses.web.ros.dm.OpeWhse;
import com.redescooter.ses.web.ros.dm.PartDetailDto;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpeFactoryService;
import com.redescooter.ses.web.ros.service.base.OpePartsProductBService;
import com.redescooter.ses.web.ros.service.base.OpePartsProductService;
import com.redescooter.ses.web.ros.service.base.OpePartsService;
import com.redescooter.ses.web.ros.service.base.OpePurchasBQcService;
import com.redescooter.ses.web.ros.service.base.OpePurchasBService;
import com.redescooter.ses.web.ros.service.base.OpePurchasPaymentService;
import com.redescooter.ses.web.ros.service.base.OpePurchasProductService;
import com.redescooter.ses.web.ros.service.base.OpePurchasService;
import com.redescooter.ses.web.ros.service.base.OpePurchasTraceService;
import com.redescooter.ses.web.ros.service.base.OpeStockBillService;
import com.redescooter.ses.web.ros.service.base.OpeStockService;
import com.redescooter.ses.web.ros.service.base.OpeSupplierService;
import com.redescooter.ses.web.ros.service.base.OpeSysUserProfileService;
import com.redescooter.ses.web.ros.service.base.OpeWhseService;
import com.redescooter.ses.web.ros.service.production.purchasing.PurchasingService;
import com.redescooter.ses.web.ros.vo.production.ConsigneeResult;
import com.redescooter.ses.web.ros.vo.production.FactoryCommonResult;
import com.redescooter.ses.web.ros.vo.production.ProductionPartsEnter;
import com.redescooter.ses.web.ros.vo.production.SaveSupplierAnnexEnter;
import com.redescooter.ses.web.ros.vo.production.purchasing.PayEnter;
import com.redescooter.ses.web.ros.vo.production.purchasing.PaymentDetailResullt;
import com.redescooter.ses.web.ros.vo.production.purchasing.PaymentItemDetailResult;
import com.redescooter.ses.web.ros.vo.production.purchasing.PruchasingItemListEnter;
import com.redescooter.ses.web.ros.vo.production.purchasing.PruchasingItemResult;
import com.redescooter.ses.web.ros.vo.production.purchasing.PurchasSupplierResult;
import com.redescooter.ses.web.ros.vo.production.purchasing.PurchasingListEnter;
import com.redescooter.ses.web.ros.vo.production.purchasing.PurchasingNodeResult;
import com.redescooter.ses.web.ros.vo.production.purchasing.PurchasingResult;
import com.redescooter.ses.web.ros.vo.production.purchasing.QcInfoResult;
import com.redescooter.ses.web.ros.vo.production.purchasing.QcItemDetailResult;
import com.redescooter.ses.web.ros.vo.production.purchasing.QcItemListEnter;
import com.redescooter.ses.web.ros.vo.production.purchasing.QueryFactorySupplierResult;
import com.redescooter.ses.web.ros.vo.production.purchasing.SaveFactoryAnnexEnter;
import com.redescooter.ses.web.ros.vo.production.purchasing.SavePurchasingEnter;
import com.redescooter.ses.web.ros.vo.production.purchasing.SavePurchasingNodeEnter;
import com.redescooter.ses.web.ros.vo.production.purchasing.SavePurchasingPaymentEnter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName:PurchasingServiceImpl
 * @description: PurchasingServiceImpl
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/19 11:29
 */
@Slf4j
@Service
public class PurchasingServiceImpl implements PurchasingService {

    @Autowired
    private PurchasingServiceMapper purchasingServiceMapper;

    @Autowired
    private OpeSysUserProfileService opeSysUserProfileService;

    @Autowired
    private OpeFactoryService opeFactoryService;

    @Autowired
    private OpeSupplierService opeSupplierService;

    @Autowired
    private OpePartsService opePartsService;

    @Autowired
    private OpePurchasService opePurchasService;

    @Autowired
    private OpePartsProductService opePartsProductService;

    @Autowired
    private OpePartsProductBService opePartsProductBService;

    @Autowired
    private OpePurchasBService opePurchasBService;

    @Autowired
    private OpePurchasTraceService opePurchasTraceService;

    @Autowired
    private OpePurchasPaymentService opePurchasPaymentService;

    @Autowired
    private OpePurchasProductService opePurchasProductService;

    @Reference
    private IdAppService idAppService;

    @Autowired
    private OpePurchasBQcService opePurchasBQcService;

    @Autowired
    private OpeWhseService opeWhseService;

    @Autowired
    private OpeStockService opeStockService;

    @Autowired
    private OpeStockBillService opeStockBillService;

    /**
     * 采购单状态统计
     *
     * @param enter
     * @retrn
     */
    @Override
    public Map<String, Integer> countByType(GeneralEnter enter) {
        Map<String, Integer> map = new HashMap<>();

        List<CountByStatusResult> typeResultList = purchasingServiceMapper.countByType(enter);
        if (CollectionUtils.isNotEmpty(typeResultList)) {
            typeResultList.forEach(item -> {
                map.put(item.getStatus(), item.getTotalCount());
            });
        }
        for (PurchasingTypeEnums item : PurchasingTypeEnums.values()) {
            if (!map.containsKey(item.getValue())) {
                map.put(item.getValue(), 0);
            }
        }
        return map;
    }

    /**
     * 状态集合
     *
     * @param enter
     * @return
     */
    @Override
    public Map<String, String> statusList(GeneralEnter enter) {
        Map<String, String> map = new HashMap<>();
        for (PurchasingStatusEnums item : PurchasingStatusEnums.values()) {
            map.put(item.getValue(), item.getCode());
        }
        return map;
    }

    /**
     * 采购单列表
     *
     * @param enter
     * @return
     */
    @Override
    public PageResult<PurchasingResult> list(PurchasingListEnter enter) {
        List<String> statusList = Lists.newArrayList();
        if (StringUtils.equals(PurchasingTypeEnums.checkCode(enter.getClassType()), PurchasingTypeEnums.TODO.getValue())) {
            for (PurchasingStatusEnums item : PurchasingStatusEnums.values()) {
                statusList.add(item.getValue());
            }
            statusList.remove(PurchasingStatusEnums.IN_PURCHASING_WH.getValue());
            statusList.remove(PurchasingStatusEnums.CANCELLED.getValue());
        } else {
            statusList.add(PurchasingStatusEnums.IN_PURCHASING_WH.getValue());
            statusList.add(PurchasingStatusEnums.CANCELLED.getValue());
        }

        int count = purchasingServiceMapper.purchasingListCount(enter, statusList);
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        List<PurchasingResult> purchasingResultList = purchasingServiceMapper.purchasingList(enter, statusList);

        List<Long> ids = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(purchasingResultList)) {
            purchasingResultList.forEach(item -> {
                ids.add(item.getId());
            });
        }
        // 查询所有采购单的支付记录
        List<PaymentItemDetailResult> paymentItemDetailResultList = purchasingServiceMapper.paymentItemList(ids);
        if (CollectionUtils.isNotEmpty(paymentItemDetailResultList)) {
            purchasingResultList.forEach(item -> {
                List<PaymentItemDetailResult> paymentItemDetailList = Lists.newArrayList();
                paymentItemDetailResultList.forEach(detail -> {
                    if (item.getId().equals(detail.getPurchasingId())) {
                        //对付款类型进行处理返回
                        if (StringUtils.equals(detail.getPaymentType(), PaymentTypeEnums.MONTHLY_PAY.getValue())) {
                            detail.setStatementDate(detail.getEstimatedPaymentDate());
                            detail.setEstimatedPaymentDate(null);
                        }
                        paymentItemDetailList.add(detail);
                    }
                });
                item.setPaymentItemDetailResultList(paymentItemDetailList);
            });
        }
        return PageResult.create(enter, count, purchasingResultList);
    }

    /**
     * 付款方式
     *
     * @param enter
     * @return
     */
    @Override
    public Map<String, String> paymentType(GeneralEnter enter) {
        Map<String, String> map = new HashMap<>();
        for (PaymentTypeEnums item : PaymentTypeEnums.values()) {
            map.put(item.getValue(), item.getCode());
        }
        return map;
    }

    /**
     * 保存采购单
     * 1 收货人过滤
     * 2、代工程过滤
     * 3、配件过滤
     * 4、支付信息过滤
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult save(SavePurchasingEnter enter) {
        //配件、付款信息转换
        List<ProductionPartsEnter> productsList = null;
        List<SavePurchasingPaymentEnter> paymentList = null;
        try {
            productsList = JSONArray.parseArray(enter.getPartList(), ProductionPartsEnter.class);
            paymentList = JSONArray.parseArray(enter.getPaymentInfoList(), SavePurchasingPaymentEnter.class);
        } catch (Exception e) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }
        if (CollectionUtils.isEmpty(productsList)) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }
        if (CollectionUtils.isEmpty(paymentList)) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }

        Long purchasId = idAppService.getId(SequenceName.OPE_PURCHAS);
        //子表保存集合
        List<OpePurchasB> opePurchasBList = Lists.newArrayList();
        //采购产品表保存集合
        List<OpePurchasProduct> opePurchasProductList = Lists.newArrayList();
        // 主表保存
        OpePurchas opePurchas = null;
        //支付信息保存结合
        List<OpePurchasPayment> opePurchasPaymentList = Lists.newArrayList();

        //收货人过滤
        QueryWrapper<OpeSysUserProfile> opeSysUserProfileQueryWrapper = new QueryWrapper<>();
        opeSysUserProfileQueryWrapper.eq(OpeSysUserProfile.COL_DR, 0);
        opeSysUserProfileQueryWrapper.eq(OpeSysUserProfile.COL_SYS_USER_ID, enter.getConsigneeId());
        OpeSysUserProfile opeSysUserProfile = opeSysUserProfileService.getOne(opeSysUserProfileQueryWrapper);
        if (opeSysUserProfile == null) {
            throw new SesWebRosException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(), ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }
        //代工厂过滤
        QueryWrapper<OpeFactory> opeFactoryQueryWrapper = new QueryWrapper<>();
        opeFactoryQueryWrapper.eq(OpeFactory.COL_DR, 0);
        opeFactoryQueryWrapper.eq(OpeFactory.COL_ID, enter.getFactoryId());
        OpeFactory opeFactory = opeFactoryService.getOne(opeFactoryQueryWrapper);
        if (opeFactory == null) {
            throw new SesWebRosException(ExceptionCodeEnums.FACTORY_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.FACTORY_IS_NOT_EXIST.getMessage());
        }
        //采购单 过滤 数据封装
        opePurchas = buildPurchas(opePurchas, enter, productsList, purchasId, opePurchasBList, opePurchasProductList);
        //采购单 付款信息 数据封装
        buildSavePurchasPaymentInfo(enter, paymentList, purchasId, opePurchasPaymentList);

        //订单节点
        SavePurchasingNodeEnter savePurchasingNodeEnter = new SavePurchasingNodeEnter();
        BeanUtils.copyProperties(enter, savePurchasingNodeEnter);
        savePurchasingNodeEnter.setPurchasId(purchasId);
        savePurchasingNodeEnter.setStatus(PurchasingStatusEnums.PENDING.getValue());
        savePurchasingNodeEnter.setEvent(PurchasingEventEnums.PENDING.getValue());
        savePurchasingNodeEnter.setMemo(null);
        this.savePurchasingNode(savePurchasingNodeEnter);

        //数据保存
        if (CollectionUtils.isNotEmpty(opePurchasBList)) {
            opePurchasBService.batchInsert(opePurchasBList);
        }
        if (CollectionUtils.isNotEmpty(opePurchasProductList)) {
            opePurchasProductService.batchInsert(opePurchasProductList);
        }
        if (opePurchas != null) {
            opePurchasService.save(opePurchas);
        }
        if (CollectionUtils.isNotEmpty(opePurchasPaymentList)) {
            opePurchasPaymentService.batchInsert(opePurchasPaymentList);
        }
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 收件人列表
     *
     * @param enter
     * @return
     */
    @Override
    public List<ConsigneeResult> consigneeList(GeneralEnter enter) {
        List<ConsigneeResult> consigneeResultlist = new ArrayList<>();
        QueryWrapper<OpeSysUserProfile> opeSysUserProfileQueryWrapper = new QueryWrapper<>();
        opeSysUserProfileQueryWrapper.eq(OpeSysUserProfile.COL_DR, 0);
        opeSysUserProfileQueryWrapper.ne(OpeSysUserProfile.COL_SYS_USER_ID, Constant.ADMINUSERID);
        List<OpeSysUserProfile> opeSysUserProfileList = opeSysUserProfileService.list(opeSysUserProfileQueryWrapper);
        opeSysUserProfileList.forEach(item -> {
            consigneeResultlist.add(ConsigneeResult.builder()
                    .id(item.getSysUserId())
                    .firstName(item.getFirstName())
                    .lastName(item.getLastName())
                    .phoneCountryCode(item.getCountryCode())
                    .phone(item.getTelNumber())
                    .build());
        });
        return consigneeResultlist;
    }

    /**
     * 工厂列表
     *
     * @param enter
     * @return
     */
    @Override
    public List<FactoryCommonResult> factoryList(GeneralEnter enter) {
        List<FactoryCommonResult> result = new ArrayList<>();
        QueryWrapper<OpeFactory> opeFactoryQueryWrapper = new QueryWrapper();
        opeFactoryQueryWrapper.eq(OpeFactory.COL_DR, 0);
        List<OpeFactory> opeFactoryList = opeFactoryService.list(opeFactoryQueryWrapper);
        opeFactoryList.forEach(item -> {
            result.add(FactoryCommonResult.builder()
                    .id(item.getId())
                    .factoryName(item.getFactoryName())
                    .contactFullName(item.getContactFullName())
                    .contactEmail(item.getContactEmail())
                    .contactPhone(item.getContactPhone())
                    .contactPhoneCode(item.getContactPhoneCountryCode())
                    .build());
        });
        return result;
    }

    /**
     * 采购单详情
     *
     * @param enter
     * @return
     */
    @Override
    public PurchasingResult detail(IdEnter enter) {
        PurchasingResult result = purchasingServiceMapper.detail(enter);
        if (result == null) {
            throw new SesWebRosException(ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getMessage());
        }
        return result;
    }

    /**
     * 采购单节点
     *
     * @param enter
     * @return
     */
    @Override
    public List<PurchasingNodeResult> purchasingNode(IdEnter enter) {
        OpePurchas opePurchas = opePurchasService.getById(enter.getId());
        if (opePurchas == null) {
            throw new SesWebRosException(ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getMessage());
        }
        List<PurchasingNodeResult> resultList = purchasingServiceMapper.purchasingNode(enter);
        if (CollectionUtils.isEmpty(resultList)) {
            return new ArrayList<>();
        }
        return resultList;
    }

    /**
     * @param enter
     * @return
     */
    @Override
    public GeneralResult export(IdEnter enter) {
        return null;
    }

    /**
     * 付款详情
     *
     * @param enter
     * @return
     */
    @Override
    public PaymentDetailResullt paymentDetail(IdEnter enter) {
        OpePurchas opePurchas = opePurchasService.getById(enter.getId());
        if (opePurchas == null) {
            throw new SesWebRosException(ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getMessage());
        }
        PaymentDetailResullt resullt = PaymentDetailResullt.builder()
                .id(opePurchas.getId())
                .totalPrice(opePurchas.getTotalPrice())
                .status(opePurchas.getStatus())
                .build();
        // 查询支付的具体条目
        List<PaymentItemDetailResult> paymentItemList = purchasingServiceMapper.paymentItemList(Lists.newArrayList(enter.getId()));
        if (CollectionUtils.isNotEmpty(paymentItemList)) {
            paymentItemList.forEach(item -> {
                //对付款类型进行处理返回
                if (StringUtils.equals(item.getPaymentType(), PaymentTypeEnums.MONTHLY_PAY.getValue())) {
                    item.setStatementDate(item.getEstimatedPaymentDate());
                    item.setEstimatedPaymentDate(null);
                }
            });
            resullt.setPaymentItemList(paymentItemList);
        }
        return resullt;
    }

    /**
     * 支付入参
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult pay(PayEnter enter) {
        OpePurchasPayment opePurchasPayment = opePurchasPaymentService.getById(enter.getId());
        if (opePurchasPayment == null) {
            throw new SesWebRosException(ExceptionCodeEnums.OPEPURCHAS_PAYMENT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.OPEPURCHAS_PAYMENT_IS_NOT_EXIST.getMessage());
        }
        if (StringUtils.equals(opePurchasPayment.getPaymentStatus(), PayStatusEnums.PAID.getValue())) {
            throw new SesWebRosException(ExceptionCodeEnums.STATUS_ILLEGAL.getCode(), ExceptionCodeEnums.STATUS_ILLEGAL.getMessage());
        }
        //验证是否该支付的是当前分期
        QueryWrapper<OpePurchasPayment> opePurchasPaymentQueryWrapper = new QueryWrapper<>();
        opePurchasPaymentQueryWrapper.eq(OpePurchasPayment.COL_PURCHAS_ID, opePurchasPayment.getPurchasId());
        opePurchasPaymentQueryWrapper.eq(OpePurchasPayment.COL_DR, 0);
        opePurchasPaymentQueryWrapper.eq(OpePurchasPayment.COL_PAYMENT_STATUS, PayStatusEnums.UNPAID.getValue());
        opePurchasPaymentQueryWrapper.lt(OpePurchasPayment.COL_PAYMENT_PRIORITY, opePurchasPayment.getPaymentPriority());
        if (opePurchasPaymentService.count(opePurchasPaymentQueryWrapper) > 0) {
            throw new SesWebRosException(ExceptionCodeEnums.PAY_IN_INSTALLMENTS.getCode(), (ExceptionCodeEnums.PAY_IN_INSTALLMENTS.getMessage()));
        }
        //支付金额过滤
        if (opePurchasPayment.getAmount().subtract(enter.getAmount()).intValue() != 0) {
            throw new SesWebRosException(ExceptionCodeEnums.PAY_AMOUNT_IS_FALSE.getCode(), (ExceptionCodeEnums.PAY_AMOUNT_IS_FALSE.getMessage()));
        }

        opePurchasPayment.setPaymentStatus(PayStatusEnums.PAID.getValue());
        opePurchasPayment.setInvoiceNum(enter.getInvoiceNum());
        opePurchasPayment.setInvoicePicture(enter.getInvoicePicture());
        opePurchasPayment.setPaymentTime(enter.getActualPaymentDate());
        opePurchasPayment.setUpdatedBy(enter.getUserId());
        opePurchasPayment.setUpdatedTime(new Date());
        opePurchasPaymentService.updateById(opePurchasPayment);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 供应商列表
     *
     * @param enter
     * @return
     */
    @Override
    public List<FactoryCommonResult> supplierList(GeneralEnter enter) {
        List<FactoryCommonResult> list = new ArrayList<>();

        QueryWrapper<OpeSupplier> opeSupplierQueryWrapper = new QueryWrapper<>();
        opeSupplierQueryWrapper.eq(OpeSupplier.COL_DR, 0);
        List<OpeSupplier> opeSupplierList = opeSupplierService.list(opeSupplierQueryWrapper);
        opeSupplierList.forEach(item -> {
            list.add(FactoryCommonResult.builder()
                    .id(item.getId())
                    .factoryName(item.getSupplierName())
//                    .contactFirstName(item.getContactFirstName())
//                    .contactLastName(item.getContactLastName())
                    .contactFullName(item.getContactFullName())
                    .contactEmail(item.getContactEmail())
                    .contactPhoneCode(item.getContactPhoneCountryCode())
                    .contactPhone(item.getContactPhone())
                    .annexPicture(null)
                    .build());
        });
        return list;
    }

    /**
     * 产品类型
     *
     * @param enter
     * @return
     */
    @Override
    public Map<String, String> productType(GeneralEnter enter) {
        Map<String, String> map = new HashMap<>();
        for (BomCommonTypeEnums item : BomCommonTypeEnums.values()) {
            map.put(item.getValue(), item.getCode());
        }
        map.remove(BomCommonTypeEnums.COMBINATION.getValue());
        return map;
    }

    /**
     * 查询可采购的商品列表
     *
     * @param enter
     * @return
     */
    @Override
    public List<PruchasingItemResult> queryPurchasProductList(PruchasingItemListEnter enter) {
        List<String> productTypeList = new ArrayList<String>();
        for (BomCommonTypeEnums item : BomCommonTypeEnums.values()) {
            if (!item.getValue().equals(BomCommonTypeEnums.COMBINATION.getValue()) && !item.getValue().equals(BomCommonTypeEnums.SCOOTER.getValue())) {
                productTypeList.add(item.getValue());
            }
        }
        // 查询零部件
        List<PruchasingItemResult> partProductList = purchasingServiceMapper.queryPurchasProductList(enter, productTypeList);

        //整车查询
        List<PruchasingItemResult> scooterProductList = purchasingServiceMapper.queryPurchasScooter(enter, BomCommonTypeEnums.SCOOTER.getValue());
        //查询整车所有部品
        if (CollectionUtils.isNotEmpty(scooterProductList)) {
            List<Long> partIds = Lists.newArrayList();
            scooterProductList.forEach(item -> {
                partIds.add(item.getId());
            });
            List<PruchasingItemResult> partList = purchasingServiceMapper.queryProductPartItemByProductIds(partIds);
            if (CollectionUtils.isNotEmpty(partList)) {
                for (PruchasingItemResult scooter : scooterProductList) {
                    BigDecimal totalPrice = BigDecimal.ZERO;
                    List<PruchasingItemResult> scooterPartList = Lists.newArrayList();
                    for (PruchasingItemResult item : partList) {
                        if (item.getId().equals(scooter.getId())) {
                            totalPrice = totalPrice.add(item.getPrice());
                            scooterPartList.add(item);
                        }
                    }
                    scooter.setPrice(totalPrice);
                    scooter.setPruchasingItemResultList(scooterPartList);

                }
            }
        }
        partProductList.addAll(scooterProductList);

        if (CollectionUtils.isEmpty(partProductList)) {
            return new ArrayList<>();
        }
        return partProductList;
    }

    /**
     * 采购单详情商品列表
     *
     * @param enter
     * @return
     */
    @Override
    public List<PruchasingItemResult> pruchasingDetailProductList(IdEnter enter) {
        OpePurchas opePurchas = opePurchasService.getById(enter.getId());
        if (opePurchas == null) {
            throw new SesWebRosException(ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getMessage());
        }
        //采购单商品列表
        List<PruchasingItemResult> result = purchasingServiceMapper.pruchasingDetailProductList(enter);
        return result;
    }

    /**
     * 查询采购单代工厂供应商
     *
     * @param enter
     * @return
     */
    @Override
    public QueryFactorySupplierResult queryFactorySupplier(IdEnter enter) {
        QueryFactorySupplierResult result = purchasingServiceMapper.queryFactoryByPurchasId(enter);
        if (result == null) {
            return new QueryFactorySupplierResult();
        }

        List<PurchasSupplierResult> purchasSupplierResultList = purchasingServiceMapper.purchasSupplierListByPurchasId(enter);
        result.setPurchasSupplierResultList(purchasSupplierResultList);
        return result;
    }

    /**
     * 保存 工厂附件
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult saveFactoryAnnex(SaveFactoryAnnexEnter enter) {
        List<SaveSupplierAnnexEnter> saveSupplierAnnexList = null;
        try {
            saveSupplierAnnexList = JSONArray.parseArray(enter.getSupplierAnnexPictureList(), SaveSupplierAnnexEnter.class);
        } catch (Exception e) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }
        OpePurchas opePurchas = opePurchasService.getById(enter.getId());
        if (opePurchas == null) {
            throw new SesWebRosException(ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getMessage());
        }
        OpeFactory opeFactory = opeFactoryService.getById(enter.getFactoryId());
        if (opeFactory == null) {
            throw new SesWebRosException(ExceptionCodeEnums.FACTORY_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.FACTORY_IS_NOT_EXIST.getMessage());
        }
        opePurchas.setFactoryAnnex(enter.getFactoryAnnexPicture());
        opePurchas.setUpdatedBy(enter.getUserId());
        opePurchas.setUpdatedTime(new Date());
        opePurchasService.updateById(opePurchas);

        //供应商 附件上传
        QueryWrapper<OpePurchasB> opePurchasBQueryWrapper = new QueryWrapper<>();
        opePurchasBQueryWrapper.eq(OpePurchasB.COL_DR, 0);
        opePurchasBQueryWrapper.eq(OpePurchasB.COL_PURCHAS_ID, opePurchas.getId());
        List<OpePurchasB> purchasBList = opePurchasBService.list(opePurchasBQueryWrapper);

        if (CollectionUtils.isNotEmpty(purchasBList)) {

            List<Long> supplierIds = Lists.newArrayList();
            purchasBList.forEach(item -> {
                supplierIds.add(item.getSupplierId());
            });
            saveSupplierAnnexList.forEach(item -> {
                if (!supplierIds.contains(item.getId())) {
                    throw new SesWebRosException(ExceptionCodeEnums.SUPPLIER_IS_NOT_PART_OF_THE_CURRENT_DOCUMENT.getCode(),
                            ExceptionCodeEnums.SUPPLIER_IS_NOT_PART_OF_THE_CURRENT_DOCUMENT.getMessage());
                }
            });

            for (OpePurchasB item : purchasBList) {
                for (SaveSupplierAnnexEnter supplier : saveSupplierAnnexList) {
                    if (item.getPartId().equals(supplier.getPartsId()) && item.getSupplierId().equals(supplier.getId())) {
                        item.setSupplierAnnex(supplier.getPicture());
                        item.setUpdatedBy(enter.getUserId());
                        item.setUpdatedTime(new Date());
                    }
                }
            }
        }
        opePurchasBService.updateBatchById(purchasBList);
        return new GeneralResult(enter.getRequestId());
    }


    /**
     * 开始qc 质检
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult startQc(IdEnter enter) {
        OpePurchas opePurchas = checkPurchas(enter.getId(), PurchasingStatusEnums.PENDING);
        if (StringUtils.isEmpty(opePurchas.getFactoryAnnex())) {
            throw new SesWebRosException(ExceptionCodeEnums.FACTORY_ANNEX_IS_PERFECT.getCode(), ExceptionCodeEnums.FACTORY_ANNEX_IS_PERFECT.getMessage());
        }
        //供应商 附件上传
        QueryWrapper<OpePurchasB> opePurchasBQueryWrapper = new QueryWrapper<>();
        opePurchasBQueryWrapper.eq(OpePurchasB.COL_DR, 0);
        opePurchasBQueryWrapper.eq(OpePurchasB.COL_PURCHAS_ID, opePurchas.getId());
        List<OpePurchasB> purchasBList = opePurchasBService.list(opePurchasBQueryWrapper);
        if (CollectionUtils.isNotEmpty(purchasBList)) {
            purchasBList.forEach(item -> {
                if (StringUtils.isEmpty(item.getSupplierAnnex())) {
                    throw new SesWebRosException(ExceptionCodeEnums.FACTORY_ANNEX_IS_PERFECT.getCode(), ExceptionCodeEnums.FACTORY_ANNEX_IS_PERFECT.getMessage());
                }
            });
        }
        opePurchas.setStatus(PurchasingStatusEnums.MATERIALS_QC.getValue());
        opePurchas.setUpdatedTime(new Date());
        opePurchas.setUpdatedBy(enter.getUserId());
        opePurchasService.updateById(opePurchas);

        //节点保存
        SavePurchasingNodeEnter savePurchasingNodeEnter = new SavePurchasingNodeEnter();
        BeanUtils.copyProperties(enter, savePurchasingNodeEnter);
        savePurchasingNodeEnter.setPurchasId(opePurchas.getId());
        savePurchasingNodeEnter.setStatus(PurchasingStatusEnums.MATERIALS_QC.getValue());
        savePurchasingNodeEnter.setEvent(PurchasingEventEnums.MATERIALS_QC.getValue());
        savePurchasingNodeEnter.setMemo(null);
        this.savePurchasingNode(savePurchasingNodeEnter);

        //采购条目qc 状态修改
        purchasBList.forEach(item -> {
            item.setQcStatus(QcStatusEnums.QUALITY_INSPECTION.getValue());
            item.setUpdatedBy(enter.getUserId());
            item.setUpdatedTime(new Date());
        });
        opePurchasBService.updateBatchById(purchasBList);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 再次qc 质检
     * //todo 为了走流程临时这样做
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult againQc(IdEnter enter) {
        OpePurchas opePurchas = checkPurchas(enter.getId(), PurchasingStatusEnums.MATERIALS_QC);

        //qc 质检表数据获取
        List<OpePurchasBQc> opePurchasBQcList = purchasingServiceMapper.opePurchasBQcListByPurductId(enter);
        if (CollectionUtils.isNotEmpty(opePurchasBQcList)) {
            opePurchasBQcList.forEach(item -> {
                item.setPassCount(item.getPassCount() + item.getFailCount());
                item.setFailCount(0);
                item.setUpdatedBy(enter.getUserId());
                item.setStatus(QcStatusEnums.PASS.getValue());
                item.setUpdatedTime(new Date());
            });
        }
        opePurchasBQcService.updateBatchById(opePurchasBQcList);

        //todo 判断是否全部QC 通过 通过的话修改子表状态

        opePurchasBQcList.removeIf(item -> StringUtils.equals(item.getStatus(), QcStatusEnums.PASS.getValue()));
        if (opePurchasBQcList.size() == 0) {
            purchasingServiceMapper.updatePurchasBQcStatus(enter.getId(), QcStatusEnums.PASS.getValue());
        }
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * qc 完成
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult completeQc(IdEnter enter) {
        OpePurchas opePurchas = checkPurchas(enter.getId(), PurchasingStatusEnums.MATERIALS_QC);

        QueryWrapper<OpePurchasB> opePurchasBQueryWrapper = new QueryWrapper<>();
        opePurchasBQueryWrapper.eq(OpePurchasB.COL_DR, 0);
        opePurchasBQueryWrapper.eq(OpePurchasB.COL_PURCHAS_ID, opePurchas.getId());
        List<OpePurchasB> purchasBList = opePurchasBService.list(opePurchasBQueryWrapper);

        purchasBList.removeIf(item -> StringUtils.equals(item.getQcStatus(), QcStatusEnums.PASS.getValue()));
        if (CollectionUtils.isNotEmpty(purchasBList)) {
            throw new SesWebRosException(ExceptionCodeEnums.PARTS_ARE_NOT_QC_PASS.getCode(), ExceptionCodeEnums.PARTS_ARE_NOT_QC_PASS.getMessage());
        }

        opePurchas.setStatus(PurchasingStatusEnums.QC_COMPLETED.getValue());
        opePurchas.setUpdatedBy(enter.getUserId());
        opePurchas.setUpdatedTime(new Date());
        opePurchasService.updateById(opePurchas);

        //节点数据保存
        SavePurchasingNodeEnter savePurchasingNodeEnter = new SavePurchasingNodeEnter();
        BeanUtils.copyProperties(enter, savePurchasingNodeEnter);
        savePurchasingNodeEnter.setPurchasId(opePurchas.getId());
        savePurchasingNodeEnter.setStatus(PurchasingStatusEnums.QC_COMPLETED.getValue());
        savePurchasingNodeEnter.setEvent(PurchasingEventEnums.QC_COMPLETED.getValue());
        savePurchasingNodeEnter.setMemo(null);
        this.savePurchasingNode(savePurchasingNodeEnter);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 入库
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult purchasingInWh(IdEnter enter) {
        //库存数据更新
        List<OpeStock> saveStockList = Lists.newArrayList();
        //入库单数据保存
        List<OpeStockBill> saveOpeStockBillList = Lists.newArrayList();

        //采购单状态更新
        OpePurchas opePurchas = checkPurchas(enter.getId(), PurchasingStatusEnums.QC_COMPLETED);
        opePurchas.setStatus(PurchasingStatusEnums.IN_PURCHASING_WH.getValue());
        opePurchas.setUpdatedBy(enter.getUserId());
        opePurchas.setUpdatedTime(new Date());
        opePurchasService.updateById(opePurchas);


        //采购条目
        QueryWrapper<OpePurchasB> opePurchasBQueryWrapper = new QueryWrapper<>();
        opePurchasBQueryWrapper.eq(OpePurchasB.COL_PURCHAS_ID, opePurchas.getId());
        opePurchasBQueryWrapper.eq(OpePurchasB.COL_DR, 0);
        List<OpePurchasB> purchasBList = opePurchasBService.list(opePurchasBQueryWrapper);

        List<Long> partIds = Lists.newArrayList();
        purchasBList.forEach(item -> {
            partIds.add(item.getPartId());
        });

        Collection<OpeParts> partsList = opePartsService.listByIds(partIds);

        //查询采购仓库
        QueryWrapper<OpeWhse> opeWhseQueryWrapper = new QueryWrapper<>();
        opeWhseQueryWrapper.eq(OpeWhse.COL_DR, 0);
        opeWhseQueryWrapper.eq(OpeWhse.COL_TYPE, WhseTypeEnums.PURCHAS.getValue());
        OpeWhse opeWhse = opeWhseService.getOne(opeWhseQueryWrapper);
        if (opeWhse == null) {
            throw new SesWebRosException(ExceptionCodeEnums.WAREHOUSE_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.WAREHOUSE_IS_NOT_EXIST.getMessage());
        }

        QueryWrapper<OpeStock> opeStockQueryWrapper = new QueryWrapper<>();
        opeStockQueryWrapper.eq(OpeStock.COL_DR, 0);
        opeStockQueryWrapper.eq(OpeStock.COL_WHSE_ID, opeWhse.getId());
        opeStockQueryWrapper.in(OpeStock.COL_MATERIEL_PRODUCT_ID, partIds);
        List<OpeStock> opeStockList = opeStockService.list(opeStockQueryWrapper);

        if (CollectionUtils.isNotEmpty(opeStockList)) {
            for (OpePurchasB item : purchasBList) {
                Boolean stockExist = Boolean.FALSE;
                for (OpeStock stock : opeStockList) {
                    if (item.getPartId().equals(stock.getMaterielProductId())) {
                        stockExist = Boolean.TRUE;
                        //有库存 库存累计
                        stock.setAvailableTotal(stock.getAvailableTotal() + item.getTotalCount());
                        stock.setIntTotal(stock.getIntTotal() + item.getTotalCount());
                        stock.setUpdatedBy(enter.getUserId());
                        stock.setUpdatedTime(new Date());
                        saveStockList.add(stock);
                    }
                }
                if (!stockExist) {
                    //无库存
                    saveStockList.add(OpeStock.builder()
                            .id(idAppService.getId(SequenceName.OPE_STOCK))
                            .dr(0)
                            .userId(0L)
                            .tenantId(0L)
                            .whseId(opeWhse.getId())
                            .intTotal(item.getTotalCount())
                            .availableTotal(item.getTotalCount())
                            .outTotal(0)
                            .wornTotal(0)
                            .materielProductId(item.getPartId())
                            .materielProductName(null)
                            .materielProductType(null)
                            .revision(0)
                            .updatedBy(enter.getUserId())
                            .updatedTime(new Date())
                            .createdBy(enter.getUserId())
                            .createdTime(new Date())
                            .build());
                }
            }

            //入库单 形成


        }

        if (CollectionUtils.isNotEmpty(saveStockList)) {
            saveStockList.forEach(item -> {
                partsList.forEach(part -> {
                    if (item.getMaterielProductId().equals(part.getId())) {
                        item.setMaterielProductName(part.getCnName());
                        item.setMaterielProductType(part.getPartsType());
                    }
                });
            });
        }

        // todo 入库单生成
        saveStockList.forEach(item -> {

        });

        //todo 入库条目数据保存 暂无

        //节点
        SavePurchasingNodeEnter savePurchasingNodeEnter = new SavePurchasingNodeEnter();
        BeanUtils.copyProperties(enter, savePurchasingNodeEnter);
        savePurchasingNodeEnter.setPurchasId(opePurchas.getId());
        savePurchasingNodeEnter.setStatus(PurchasingStatusEnums.IN_PURCHASING_WH.getValue());
        savePurchasingNodeEnter.setEvent(PurchasingEventEnums.IN_PURCHASING_WH.getValue());
        savePurchasingNodeEnter.setMemo(null);
        this.savePurchasingNode(savePurchasingNodeEnter);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * qc 状态
     *
     * @param enter
     * @return
     */
    @Override
    public Map<String, Integer> qcCountByStatus(IdEnter enter) {
        List<CountByStatusResult> countByStatus = purchasingServiceMapper.qcCountByStatus(enter);
        Map<String, Integer> map = new HashMap<>();
        countByStatus.forEach(item -> {
            map.put(item.getStatus(), item.getTotalCount());
        });
        for (QcStatusEnums item : QcStatusEnums.values()) {
            if (!map.containsKey(item.getValue())) {
                map.put(item.getValue(), 0);
            }
        }
        map.remove(QcStatusEnums.QUALITY_INSPECTION.getValue());
        return map;
    }

    /**
     * QC 条目list
     *
     * @param enter
     * @return
     */
    @Override
    public List<QcInfoResult> qcList(QcItemListEnter enter) {
        checkPurchas(enter.getId(), null);
        List<QcInfoResult> qcPartList = purchasingServiceMapper.qcPartListByPurchasId(enter);

        if (CollectionUtils.isEmpty(qcPartList)) {
            return null;
        }

        List<Long> purshasBIds = Lists.newArrayList();
        qcPartList.forEach(item -> {
            purshasBIds.add(item.getId());
        });
        //查询质检记录
        List<QcItemDetailResult> qcItemList = purchasingServiceMapper.qcItemListByPurchasBIds(purshasBIds);
        if (CollectionUtils.isEmpty(qcItemList)) {
            return null;
        }

        qcPartList.forEach(item -> {
            List<QcItemDetailResult> qcItemResultList = Lists.newArrayList();
            qcItemList.forEach(qc -> {
                if (item.getId().equals(qc.getPruchasBId())) {
                    if (StringUtils.equals(enter.getStatus(), QcStatusEnums.PASS.getValue()) && qc.getPassQty() != 0) {
                        qcItemResultList.add(qc);
                    }
                    if (StringUtils.equals(enter.getStatus(), QcStatusEnums.FAIL.getValue()) && qc.getFailQty() != 0) {
                        qcItemResultList.add(qc);
                    }
                }
            });
            if (qcItemResultList.size() == 1) {
                item.setPassQty(qcItemResultList.get(0).getPassQty());
                item.setFailQty(qcItemResultList.get(0).getFailQty());
                item.setBatchN(qcItemResultList.get(0).getBatchN());
                item.setQcDate(qcItemResultList.get(0).getQcDate());
                item.setQcItemDetailResultList(new ArrayList<>());
            } else {
                item.setQcItemDetailResultList(qcItemResultList);
            }
        });

        return qcPartList;
    }

    /**
     * QC 未通过数据导出
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult qcFailExport(IdEnter enter) {
        return null;
    }

    /**
     * 保存采购单节点
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult savePurchasingNode(SavePurchasingNodeEnter enter) {
        opePurchasTraceService.save(OpePurchasTrace.builder()
                .id(idAppService.getId(SequenceName.OPE_PURCHAS_TRACE))
                .dr(0)
                .tenantId(0L)
                .userId(enter.getUserId())
                .purchasId(enter.getPurchasId())
                .status(enter.getStatus())
                .event(enter.getEvent())
                .eventTime(new Date())
                .memo(StringUtils.isBlank(enter.getMemo()) == true ? null : enter.getMemo())
                .createBy(enter.getUserId())
                .createTime(new Date())
                .updateBy(enter.getUserId())
                .updateTime(new Date())
                .build());
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 退货 （退掉质检未通过的）
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult returnedPurchase(IdEnter enter) {
        OpePurchas opePurchas = checkPurchas(enter.getId(), PurchasingStatusEnums.MATERIALS_QC);

        //查询qc
        return null;
    }


    /**
     * //采购单 付款信息 数据封装
     *
     * @param enter
     * @param paymentList
     * @param purchasId
     * @param opePurchasPaymentList
     */
    private void buildSavePurchasPaymentInfo(SavePurchasingEnter enter, List<SavePurchasingPaymentEnter> paymentList, Long purchasId, List<OpePurchasPayment> opePurchasPaymentList) {
        //付款数据封装
        BigDecimal totalPrice = BigDecimal.ZERO;
        if (StringUtils.equals(enter.getPaymentType(), PaymentTypeEnums.MONTHLY_PAY.getValue())) {
            //月结
            if (paymentList.size() != 1) {
                throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
            }
            opePurchasPaymentList.add(OpePurchasPayment.builder()
                    .id(idAppService.getId(SequenceName.OPE_PURCHAS_PAYMENT))
                    .dr(0)
                    .tenantId(0L)
                    .userId(enter.getUserId())
                    .purchasId(purchasId)
                    .paymentType(enter.getPaymentType())
                    .plannedPaymentTime(enter.getStatementdate())
                    .paymentDay(enter.getDays())
                    .paymentTime(null)
                    .paymentStatus(PayStatusEnums.UNPAID.getValue())
                    .description(enter.getRemark())
                    .amount(enter.getTotalPrice())
                    .amountProportion(Constant.AMOUNTP_ROPORTION)
                    .invoiceNum(null)
                    .invoicePicture(null)
                    .paymentPriority(1)
                    .revision(0)
                    .createdBy(enter.getUserId())
                    .createdTime(new Date())
                    .updatedBy(enter.getUserId())
                    .updatedTime(new Date())
                    .build());
            totalPrice.add(enter.getTotalPrice());
        } else {
            //分期
            int amountProportion = 0;
            for (int i = 0; i < paymentList.size(); i++) {
                opePurchasPaymentList.add(OpePurchasPayment.builder()
                        .id(idAppService.getId(SequenceName.OPE_PURCHAS_PAYMENT))
                        .dr(0)
                        .tenantId(0L)
                        .userId(enter.getUserId())
                        .purchasId(purchasId)
                        .paymentType(enter.getPaymentType())
                        .plannedPaymentTime(paymentList.get(i).getEstimatedPaymentDate())
                        .paymentDay(null)
                        .paymentTime(null)
                        .paymentStatus(PayStatusEnums.UNPAID.getValue())
                        .description(paymentList.get(i).getRemark())
                        .amount(paymentList.get(i).getPrice())
                        .amountProportion(paymentList.get(i).getRatio())
                        .invoiceNum(null)
                        .invoicePicture(null)
                        .paymentPriority(i)
                        .revision(0)
                        .createdBy(enter.getUserId())
                        .createdTime(new Date())
                        .updatedBy(enter.getUserId())
                        .updatedTime(new Date())
                        .build());
                totalPrice = totalPrice.add(paymentList.get(i).getPrice());
                amountProportion = amountProportion + paymentList.get(i).getRatio();
            }
            if (amountProportion != Constant.AMOUNTP_ROPORTION) {
                throw new SesWebRosException(ExceptionCodeEnums.PAYMENT_INFO_IS_WRONG.getCode(), ExceptionCodeEnums.PAYMENT_INFO_IS_WRONG.getMessage());
            }
        }
        if (totalPrice.doubleValue() != enter.getTotalPrice().doubleValue()) {
            throw new SesWebRosException(ExceptionCodeEnums.PAYMENT_INFO_IS_WRONG.getCode(), ExceptionCodeEnums.PAYMENT_INFO_IS_WRONG.getMessage());
        }
    }

    /**
     * 采购单 过滤 数据封装
     *
     * @param enter
     * @param productsList
     * @param purchasId
     * @param opePurchasBList
     * @param opePurchasProductList
     */
    private OpePurchas buildPurchas(OpePurchas opePurchas, SavePurchasingEnter enter, List<ProductionPartsEnter> productsList, Long purchasId, List<OpePurchasB> opePurchasBList,
                                    List<OpePurchasProduct> opePurchasProductList) {
        Set<Long> productIds = Sets.newHashSet();
        Integer totalqty = 0;
        for (ProductionPartsEnter item : productsList) {
            productIds.add(item.getId());
            totalqty += item.getQty();
        }
//        if (!enter.getTotalQty().equals(totalqty)) {
//            throw new SesWebRosException(ExceptionCodeEnums.PART_TOTAL_QTY_IS_NOT_MATCH.getCode(), ExceptionCodeEnums.PART_TOTAL_QTY_IS_NOT_MATCH.getMessage());
//        }
        QueryWrapper<OpePartsProduct> opePartsProductQueryWrapper = new QueryWrapper<>();
        opePartsProductQueryWrapper.eq(OpePartsProduct.COL_DR, 0);
        opePartsProductQueryWrapper.in(OpePartsProduct.COL_ID, new ArrayList<>(productIds));
        List<OpePartsProduct> partsProductList = opePartsProductService.list(opePartsProductQueryWrapper);
        if (CollectionUtils.isEmpty(partsProductList)) {
            throw new SesWebRosException(ExceptionCodeEnums.PART_PRODUCT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PART_PRODUCT_IS_NOT_EXIST.getMessage());
        }
        if (partsProductList.size() != productIds.size()) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }

        //订单表数据组装
        partsProductList.forEach(item -> {
            opePurchasProductList.add(
                    OpePurchasProduct.builder()
                            .id(idAppService.getId(SequenceName.OPE_PURCHAS_PRODUCT))
                            .dr(0)
                            .tenantId(0L)
                            .userId(enter.getUserId())
                            .purchasId(purchasId)
                            .productId(item.getId())
                            .idClass(null)
                            .productType(String.valueOf(item.getProductType()))
                            .productNum(item.getProductNumber())
                            .revision(0)
                            .createdBy(enter.getUserId())
                            .createdTime(new Date())
                            .updatedBy(enter.getUserId())
                            .updatedTime(new Date())
                            .build()
            );
        });

        //查询 组合产品部件集合
        QueryWrapper<OpePartsProductB> opePartsProductBQueryWrapper = new QueryWrapper<>();
        opePartsProductBQueryWrapper.eq(OpePartsProductB.COL_DR, 0);
        opePartsProductBQueryWrapper.in(OpePartsProductB.COL_PARTS_PRODUCT_ID, new ArrayList<>(productIds));
        List<OpePartsProductB> partsProductBList = opePartsProductBService.list(opePartsProductBQueryWrapper);

        //进行部品归类
        HashMap<Long, Integer> partMap = new HashMap<>();
        productsList.forEach(product -> {
            partsProductBList.forEach(item -> {
                if (product.getId().equals(item.getPartsProductId())) {
                    if (!partMap.containsKey(item.getPartsId())) {
                        // 不存在部品id 就重新放入
                        partMap.put(item.getPartsId(), item.getPartsQty() * product.getQty());
                    } else {
                        // 存在部品id 对数量进行维护
                        partMap.put(item.getPartsId(), partMap.get(item.getPartsId()) + item.getPartsQty() * product.getQty());
                    }
                }
            });
        });

        List<Long> partsIds = new ArrayList<>();
        partMap.forEach((k, v) -> {
            partsIds.add(k);
        });
        List<PartDetailDto> partDetailDtoList = purchasingServiceMapper.partDetailById(partsIds);

        BigDecimal totalPrice = BigDecimal.ZERO;
        Integer totalCount = 0;

        for (Map.Entry<Long, Integer> entry : partMap.entrySet()) {
            Long k = entry.getKey();
            Integer v = entry.getValue();
            for (PartDetailDto item : partDetailDtoList) {
                if (k.equals(item.getPartId())) {
                    OpePurchasB purchasB = OpePurchasB.builder()
                            .id(idAppService.getId(SequenceName.OPE_PURCHAS_B))
                            .dr(0)
                            .tenantId(0L)
                            .userId(enter.getUserId())
                            .purchasId(purchasId)
                            .partId(k)
                            .supplierId(item.getSupplierId())
                            .price(item.getPrice().equals(BigDecimal.ZERO) ? BigDecimal.ZERO : item.getPrice().multiply(new BigDecimal(v)))
                            .totalCount(v)
                            .createdBy(enter.getUserId())
                            .createdTime(new Date())
                            .updatedTime(new Date())
                            .updatedBy(enter.getUserId())
                            .build();
                    opePurchasBList.add(purchasB);
                    totalPrice = totalPrice.add(purchasB.getPrice());
                    totalCount += purchasB.getTotalCount();
                }
            }
        }
        //封装主表数据
        return OpePurchas.builder()
                .id(purchasId)
                .dr(0)
                .userId(enter.getUserId())
                .tenantId(0L)
                .consigneeId(enter.getConsigneeId())
                .contractNo("REDE" + RandomUtil.getRandom())
                .status(PurchasingStatusEnums.PENDING.getValue())
                .paymentType(enter.getPaymentType())
                .factoryId(enter.getFactoryId())
                .totalPrice(totalPrice)
                .totalQty(totalCount)
                .revision(0)
                .createdBy(enter.getUserId())
                .createdTime(new Date())
                .updatedTime(new Date())
                .updatedBy(enter.getUserId())
                .build();
    }

    private OpePurchas checkPurchas(Long id, PurchasingStatusEnums status) {
        OpePurchas opePurchas = opePurchasService.getById(id);
        if (opePurchas == null) {
            throw new SesWebRosException(ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getMessage());
        }
        if (status != null) {
            if (!StringUtils.equals(opePurchas.getStatus(), status.getValue())) {
                throw new SesWebRosException(ExceptionCodeEnums.STATUS_ILLEGAL.getCode(), ExceptionCodeEnums.STATUS_ILLEGAL.getMessage());
            }
        }
        return opePurchas;
    }
}
