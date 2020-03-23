package com.redescooter.ses.web.ros.service.production.purchasing.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.redescooter.ses.api.common.enums.bom.BomCommonTypeEnums;
import com.redescooter.ses.api.common.enums.production.PurchasingTypeEnums;
import com.redescooter.ses.api.common.enums.production.purchasing.PayStatusEnums;
import com.redescooter.ses.api.common.enums.production.purchasing.PaymentTypeEnums;
import com.redescooter.ses.api.common.enums.production.purchasing.PurchasingStatusEnums;
import com.redescooter.ses.api.common.enums.production.purchasing.QcStatusEnums;
import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.dao.production.PurchasingServiceMapper;
import com.redescooter.ses.web.ros.dm.OpeFactory;
import com.redescooter.ses.web.ros.dm.OpePurchas;
import com.redescooter.ses.web.ros.dm.OpeSupplier;
import com.redescooter.ses.web.ros.dm.OpeSysUserProfile;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpeFactoryService;
import com.redescooter.ses.web.ros.service.base.OpePurchasService;
import com.redescooter.ses.web.ros.service.base.OpeSupplierService;
import com.redescooter.ses.web.ros.service.base.OpeSysUserProfileService;
import com.redescooter.ses.web.ros.service.production.purchasing.PurchasingService;
import com.redescooter.ses.web.ros.vo.production.ConsigneeResult;
import com.redescooter.ses.web.ros.vo.production.FactoryCommonResult;
import com.redescooter.ses.web.ros.vo.production.purchasing.PayEnter;
import com.redescooter.ses.web.ros.vo.production.purchasing.PaymentDetailResullt;
import com.redescooter.ses.web.ros.vo.production.purchasing.PaymentItemDetailResult;
import com.redescooter.ses.web.ros.vo.production.purchasing.PruchasingItemListEnter;
import com.redescooter.ses.web.ros.vo.production.purchasing.PruchasingItemResult;
import com.redescooter.ses.web.ros.vo.production.purchasing.PurchasingListEnter;
import com.redescooter.ses.web.ros.vo.production.purchasing.PurchasingNodeResult;
import com.redescooter.ses.web.ros.vo.production.purchasing.PurchasingResult;
import com.redescooter.ses.web.ros.vo.production.purchasing.QcInfoResult;
import com.redescooter.ses.web.ros.vo.production.purchasing.QcItemDetailResult;
import com.redescooter.ses.web.ros.vo.production.purchasing.QcItemListEnter;
import com.redescooter.ses.web.ros.vo.production.purchasing.SaveFactoryAnnexEnter;
import com.redescooter.ses.web.ros.vo.production.purchasing.SavePurchasingEnter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName:PurchasingServiceImpl
 * @description: PurchasingServiceImpl
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/19 11:29
 */
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
    private OpePurchasService opePurchasService;

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
            map.put(item.getValue(), 0);
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
        if (StringUtils.equals(PurchasingTypeEnums.checkCode(enter.getType()), PurchasingTypeEnums.TODO.getValue())) {
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
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult save(SavePurchasingEnter enter) {
        return null;
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
        List<OpeSysUserProfile> opeSysUserProfileList = opeSysUserProfileService.list(opeSysUserProfileQueryWrapper);
        opeSysUserProfileList.forEach(item -> {
            consigneeResultlist.add(ConsigneeResult.builder()
                    .id(item.getSysUserId())
                    .firstName(item.getFirstName())
                    .lastName(item.getLastName())
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

        List<PaymentItemDetailResult> list = new ArrayList<>();
        list.add(PaymentItemDetailResult.builder()
                .id(1000000000L)
                .status(PayStatusEnums.UNPAID.getValue())
                .amount("100000000")
                .paymentRatio("19.56")
                .invoiceNum("23423432432")
                .invoicePicture("eqweq23423432432")
                .dayNum(20)
                .statementDate(new Date())
                .actualPaymentDate(new Date())
                .remark("4234324324234")
                .build());

        return PaymentDetailResullt.builder()
                .id(10000000L)
                .status(PayStatusEnums.UNPAID.getValue())
                .totalPrice("1000000.156")
                .paymentItemList(list)
                .build();
    }

    /**
     * 支付入参
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult pay(PayEnter enter) {
        return null;
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
    public List<PruchasingItemResult> queryProductList(PruchasingItemListEnter enter) {
        List<String> productTypeList = new ArrayList<String>();
        for (BomCommonTypeEnums item : BomCommonTypeEnums.values()) {
            if (!item.getValue().equals(BomCommonTypeEnums.COMBINATION.getValue())) {
                productTypeList.add(item.getValue());
            }
        }
        List<PruchasingItemResult> result = purchasingServiceMapper.pruchasingProductList(enter, productTypeList);
        if (CollectionUtils.isEmpty(result)) {
            return new ArrayList<>();
        }
        return result;
    }

    /**
     * 采购单详情商品列表
     *
     * @param enter
     * @return
     */
    @Override
    public List<PruchasingItemResult> pruchasingDetailProductList(IdEnter enter) {
//        List<String> productTypeList = new ArrayList<String>();
//        for (BomCommonTypeEnums item : BomCommonTypeEnums.values()) {
//            if (!item.getValue().equals(BomCommonTypeEnums.COMBINATION.getValue())) {
//                productTypeList.add(item.getValue());
//            }
//        }
//        List<PruchasingItemResult> result = purchasingServiceMapper.pruchasingProductList(enter, productTypeList);
//        if (CollectionUtils.isEmpty(result)) {
//            return new ArrayList<>();
//        }
        return null;
    }

    /**
     * 保存 工厂附件
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult saveFactoryAnnex(SaveFactoryAnnexEnter enter) {
        return null;
    }

    /**
     * 开始采购
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult startPurchasing(IdEnter enter) {
        return null;
    }

    /**
     * 开始qc 质检
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult startQc(IdEnter enter) {
        return null;
    }

    /**
     * 再次qc 质检
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult againQc(IdEnter enter) {
        return null;
    }

    /**
     * qc 完成
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult completeQc(IdEnter enter) {
        return null;
    }

    /**
     * 入库
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult purchasingInWh(IdEnter enter) {
        return null;
    }

    /**
     * qc 状态
     *
     * @param enter
     * @return
     */
    @Override
    public Map<String, Integer> qcCountByStatus(IdEnter enter) {

        Map<String, Integer> map = new HashMap<>();
        for (QcStatusEnums item : QcStatusEnums.values()) {
            map.put(item.getValue(), 0);
        }
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
        List<QcInfoResult> list = new ArrayList<>();
        List<QcItemDetailResult> qcItemDetailResultList = new ArrayList<>();
        qcItemDetailResultList.add(QcItemDetailResult.builder()
                .batchN("34324234234")
                .passQty(20)
                .qcDate(new Date())
                .totalQty(1000)
                .build());

        list.add(QcInfoResult.builder()
                .id(100000L)
                .partsN("44243432432")
                .enName("eqw")
                .cnName("不知道")
                .type(BomCommonTypeEnums.ACCESSORY.getValue())
                .passQty(100)
                .totalQty(200)
                .batchN("4324342")
                .qcDate(new Date())
                .qcItemDetailResultList(qcItemDetailResultList)
                .build());

        list.add(QcInfoResult.builder()
                .id(100000L)
                .partsN("44243432432")
                .enName("eqw")
                .cnName("不知道")
                .type(BomCommonTypeEnums.ACCESSORY.getValue())
                .passQty(100)
                .totalQty(200)
                .batchN("4324342")
                .qcDate(new Date())
                .qcItemDetailResultList(qcItemDetailResultList)
                .build());
        return list;
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

}
