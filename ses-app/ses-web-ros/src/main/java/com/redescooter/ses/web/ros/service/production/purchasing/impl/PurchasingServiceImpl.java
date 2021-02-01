package com.redescooter.ses.web.ros.service.production.purchasing.impl;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.enums.bom.BomCommonTypeEnums;
import com.redescooter.ses.api.common.enums.bom.ProductionBomStatusEnums;
import com.redescooter.ses.api.common.enums.bom.ProductionPartsRelationTypeEnums;
import com.redescooter.ses.api.common.enums.production.PaymentTypeEnums;
import com.redescooter.ses.api.common.enums.production.ProductionTypeEnums;
import com.redescooter.ses.api.common.enums.production.purchasing.PayStatusEnums;
import com.redescooter.ses.api.common.enums.production.purchasing.PurchasingEventEnums;
import com.redescooter.ses.api.common.enums.production.purchasing.PurchasingStatusEnums;
import com.redescooter.ses.api.common.enums.production.purchasing.QcStatusEnums;
import com.redescooter.ses.api.common.enums.whse.WhseTypeEnums;
import com.redescooter.ses.api.common.vo.CommonNodeResult;
import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.SaveNodeEnter;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.DateUtil;
import com.redescooter.ses.tool.utils.SesStringUtils;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.dao.production.PurchasingServiceMapper;
import com.redescooter.ses.web.ros.dm.*;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.*;
import com.redescooter.ses.web.ros.service.production.purchasing.PurchasingService;
import com.redescooter.ses.web.ros.vo.bo.PartDetailDto;
import com.redescooter.ses.web.ros.vo.production.ConsigneeResult;
import com.redescooter.ses.web.ros.vo.production.FactoryCommonResult;
import com.redescooter.ses.web.ros.vo.production.PayEnter;
import com.redescooter.ses.web.ros.vo.production.PaymentDetailResullt;
import com.redescooter.ses.web.ros.vo.production.PaymentItemDetailResult;
import com.redescooter.ses.web.ros.vo.production.ProductionPartsEnter;
import com.redescooter.ses.web.ros.vo.production.SaveSupplierAnnexEnter;
import com.redescooter.ses.web.ros.vo.production.StagingPaymentEnter;
import com.redescooter.ses.web.ros.vo.production.purchasing.PruchasingDetailProductEnter;
import com.redescooter.ses.web.ros.vo.production.purchasing.PruchasingItemListEnter;
import com.redescooter.ses.web.ros.vo.production.purchasing.PruchasingItemResult;
import com.redescooter.ses.web.ros.vo.production.purchasing.PurchasSupplierResult;
import com.redescooter.ses.web.ros.vo.production.purchasing.PurchasingExportResult;
import com.redescooter.ses.web.ros.vo.production.purchasing.PurchasingListEnter;
import com.redescooter.ses.web.ros.vo.production.purchasing.PurchasingResult;
import com.redescooter.ses.web.ros.vo.production.purchasing.QcInfoResult;
import com.redescooter.ses.web.ros.vo.production.purchasing.QcItemDetailResult;
import com.redescooter.ses.web.ros.vo.production.purchasing.QcItemListEnter;
import com.redescooter.ses.web.ros.vo.production.purchasing.QueryFactorySupplierResult;
import com.redescooter.ses.web.ros.vo.production.purchasing.SaveFactoryAnnexEnter;
import com.redescooter.ses.web.ros.vo.production.purchasing.SavePurchasingEnter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName:PurchasingServiceImpl
 * @description: PurchasingServiceImpl
 * @author: Alex @Version：1.3
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
    private OpePurchasBService opePurchasBService;

    @Autowired
    private OpePurchasTraceService opePurchasTraceService;

    @Autowired
    private OpePurchasPaymentService opePurchasPaymentService;

    @Autowired
    private OpePurchasProductService opePurchasProductService;

    @Autowired
    private OpeProductionPartsService opeProductionPartsService;

    @Autowired
    private OpeProductionScooterBomService opeProductionScooterBomService;

    @Autowired
    private OpeProductionPartsRelationService opeProductionPartsRelationService;

    @Autowired
    private OpeProductionQualityTempateService opeProductionQualityTempateService;

    @Reference
    private IdAppService idAppService;

    @Autowired
    private OpeWhseService opeWhseService;

    @Autowired
    private OpeStockService opeStockService;

    @Autowired
    private OpeStockBillService opeStockBillService;

    @Autowired
    private OpeWmsPartsStockService opeWmsPartsStockService;

    @Autowired
    private OpeWmsQualifiedPartsStockService opeWmsQualifiedPartsStockService;



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
        for (ProductionTypeEnums item : ProductionTypeEnums.values()) {
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
        if (enter.getKeyword() != null && enter.getKeyword().length() > 50) {
            return PageResult.createZeroRowResult(enter);
        }
        List<String> statusList = Lists.newArrayList();
        if (StringUtils.equals(ProductionTypeEnums.checkCode(enter.getClassType()),
                ProductionTypeEnums.TODO.getValue())) {
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
                item.setPaymentItemDetailResultList(paymentItemDetailResultList);
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
     * 保存采购单 1 收货人过滤 2、代工程过滤 3、配件过滤 4、支付信息过滤
     *
     * @param savePurchasingEnter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult save(SavePurchasingEnter savePurchasingEnter) {
        // savePurchasingEnter参数值去空格
        SavePurchasingEnter enter = SesStringUtils.objStringTrim(savePurchasingEnter);
        // 配件、付款信息转换
        List<ProductionPartsEnter> productsList = null;
        List<StagingPaymentEnter> paymentList = null;
        try {
            productsList = JSONArray.parseArray(enter.getPartList(), ProductionPartsEnter.class);
            if (StringUtils.equals(PaymentTypeEnums.STAGING.getValue(), enter.getPaymentType())) {
                paymentList = JSONArray.parseArray(enter.getPaymentInfoList(), StagingPaymentEnter.class);
            }
        } catch (Exception e) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(),
                    ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }
        if (CollectionUtils.isEmpty(productsList)) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(),
                    ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }

        Long purchasId = idAppService.getId(SequenceName.OPE_PURCHAS);
        // 子表保存集合
        List<OpePurchasB> opePurchasBList = Lists.newArrayList();
        // 采购产品表保存集合
        List<OpePurchasProduct> opePurchasProductList = Lists.newArrayList();
        // 主表保存
        OpePurchas opePurchas = null;
        // 支付信息保存结合
        List<OpePurchasPayment> opePurchasPaymentList = Lists.newArrayList();

        // 收货人过滤
        QueryWrapper<OpeSysUserProfile> opeSysUserProfileQueryWrapper = new QueryWrapper<>();
        opeSysUserProfileQueryWrapper.eq(OpeSysUserProfile.COL_DR, 0);
        opeSysUserProfileQueryWrapper.eq(OpeSysUserProfile.COL_SYS_USER_ID, enter.getConsigneeId());
        opeSysUserProfileQueryWrapper.last("limit 1");
        OpeSysUserProfile opeSysUserProfile = opeSysUserProfileService.getOne(opeSysUserProfileQueryWrapper);
        if (opeSysUserProfile == null) {
            throw new SesWebRosException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(),
                    ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }
        // 代工厂过滤
        QueryWrapper<OpeFactory> opeFactoryQueryWrapper = new QueryWrapper<>();
        opeFactoryQueryWrapper.eq(OpeFactory.COL_DR, 0);
        opeFactoryQueryWrapper.eq(OpeFactory.COL_ID, enter.getFactoryId());
        opeFactoryQueryWrapper.last("limit 1");
        OpeFactory opeFactory = opeFactoryService.getOne(opeFactoryQueryWrapper);
        if (opeFactory == null) {
            throw new SesWebRosException(ExceptionCodeEnums.FACTORY_IS_NOT_EXIST.getCode(),
                    ExceptionCodeEnums.FACTORY_IS_NOT_EXIST.getMessage());
        }
        // 采购单 过滤 数据封装
        opePurchas = buildPurchas(enter, productsList, purchasId, opePurchasBList, opePurchasProductList);
        // 采购单 付款信息 数据封装
        buildSavePurchasPaymentInfo(enter, paymentList, purchasId, opePurchasPaymentList);

        // 订单节点
        SaveNodeEnter saveNodeEnter = new SaveNodeEnter();
        BeanUtils.copyProperties(enter, saveNodeEnter);
        saveNodeEnter.setId(purchasId);
        saveNodeEnter.setStatus(PurchasingStatusEnums.PENDING.getValue());
        saveNodeEnter.setEvent(PurchasingEventEnums.PENDING.getValue());
        saveNodeEnter.setMemo(null);
        this.savePurchasingNode(saveNodeEnter);

        // 数据保存
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
        opeSysUserProfileQueryWrapper.ne(OpeSysUserProfile.COL_FIRST_NAME, Constant.ADMIN_USER_NAME);
        List<OpeSysUserProfile> opeSysUserProfileList = opeSysUserProfileService.list(opeSysUserProfileQueryWrapper);
        opeSysUserProfileList.forEach(item -> {
            consigneeResultlist.add(ConsigneeResult.builder().id(item.getSysUserId()).firstName(item.getFirstName())
                    .lastName(item.getLastName()).phoneCountryCode(item.getCountryCode()).phone(item.getTelNumber())
                    .email(item.getEmail()).build());
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
            result.add(FactoryCommonResult.builder().id(item.getId()).factoryName(item.getFactoryName())
                    .contactFullName(item.getContactFullName()).contactEmail(item.getContactEmail())
                    .contactPhone(item.getContactPhone()).contactPhoneCode(item.getContactPhoneCountryCode()).build());
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
            throw new SesWebRosException(ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getCode(),
                    ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getMessage());
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
    public List<CommonNodeResult> purchasingNode(IdEnter enter) {
        OpePurchas opePurchas = opePurchasService.getById(enter.getId());
        if (opePurchas == null) {
            throw new SesWebRosException(ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getCode(),
                    ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getMessage());
        }
        List<CommonNodeResult> resultList = purchasingServiceMapper.purchasingNode(enter);
        if (CollectionUtils.isEmpty(resultList)) {
            return new ArrayList<>();
        }
        return resultList;
    }

    @Override
    public GeneralResult purchasingExport(Long id, HttpServletResponse response) {
        IdEnter enter = new IdEnter();
        enter.setId(id);
        PurchasingResult purchasingResult = this.detail(enter);
        PurchasingExportResult exportResult = new PurchasingExportResult();
        BeanUtil.copyProperties(purchasingResult, exportResult);
        List<PurchasingExportResult> list = new ArrayList<>();
        list.add(exportResult);
        try {
            // 设置响应输出的头类型
            response.setHeader("content-Type", "application/vnd.ms-excel");
            // 下载文件的默认名称
            response.setHeader("Content-Disposition", "attachment;filename=" + System.currentTimeMillis() + ".xls");
            // =========easypoi部分
            ExportParams exportParams = new ExportParams();
            // exportParams.setDataHanlder(null);//和导入一样可以设置一个handler来处理特殊数据
            Workbook workbook = ExcelExportUtil.exportExcel(exportParams, PurchasingExportResult.class, list);
            workbook.write(response.getOutputStream());
        } catch (Exception e) {
            System.out.println("+++++++++++++++++++");
        }
        return new GeneralResult();
    }

    /**
     * @param enter
     * @return
     */
    // @Override
    // public GeneralResult export(IdEnter enter) {
    //
    // String path = "src/main/resources/template/";
    //
    // PurchasingResult purchasingResult = this.detail(enter);
    //
    // PurchasingResultTest purchasingResultTest = new PurchasingResultTest();
    // BeanUtil.copyProperties(purchasingResult,purchasingResultTest);
    // List<PurchasingResultTest> list = new ArrayList<>();
    // list.add(purchasingResultTest);
    //// try {
    //// Workbook workbook = EasyPoiUtils.exportExcel(PurchasingResultTest.class, list, path,
    // purchasingResultTest.getContractN(),response);
    //// EasyPoiUtils.downLoadExcel(purchasingResultTest.getContractN(), workbook, response);
    // try{
    // // 设置响应输出的头类型
    // response.setCharacterEncoding("UTF-8");
    // response.setHeader("content-Type", "application/vnd.ms-excel");
    // // 下载文件的默认名称
    // response.setHeader("Content-Disposition", "attachment;filename=user.xls");
    // // =========easypoi部分
    // ExportParams exportParams = new ExportParams();
    //
    //
    // // exportParams.setDataHanlder(null);//和导入一样可以设置一个handler来处理特殊数据
    //// Workbook workbook = ExcelExportUtil.exportExcel(exportParams, PurchasingResultTest.class, list);
    //// workbook.write(response.getOutputStream());
    // EasyPoiNewUtils.exportExcel(list,"导出","导出1",PurchasingResultTest.class,"导出2",true,response);
    // } catch (Exception e) {
    // System.out.println("+++++++++++++++++++");
    // }
    //// } catch (IOException e) {
    //// e.printStackTrace();
    //// }
    //
    // return new GeneralResult();
    // }

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
            throw new SesWebRosException(ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getCode(),
                    ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getMessage());
        }
        PaymentDetailResullt resullt = PaymentDetailResullt.builder().id(opePurchas.getId())
                .totalPrice(opePurchas.getTotalPrice()).status(opePurchas.getStatus()).build();
        // 查询支付的具体条目
        List<PaymentItemDetailResult> paymentItemList =
                purchasingServiceMapper.paymentItemList(Lists.newArrayList(enter.getId()));
        resullt.setPaymentItemList(paymentItemList);
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
            throw new SesWebRosException(ExceptionCodeEnums.OPEPURCHAS_PAYMENT_IS_NOT_EXIST.getCode(),
                    ExceptionCodeEnums.OPEPURCHAS_PAYMENT_IS_NOT_EXIST.getMessage());
        }
        if (StringUtils.equals(opePurchasPayment.getPaymentStatus(), PayStatusEnums.PAID.getValue())) {
            throw new SesWebRosException(ExceptionCodeEnums.STATUS_ILLEGAL.getCode(),
                    ExceptionCodeEnums.STATUS_ILLEGAL.getMessage());
        }
        // 验证是否该支付的是当前分期
        QueryWrapper<OpePurchasPayment> opePurchasPaymentQueryWrapper = new QueryWrapper<>();
        opePurchasPaymentQueryWrapper.eq(OpePurchasPayment.COL_PURCHAS_ID, opePurchasPayment.getPurchasId());
        opePurchasPaymentQueryWrapper.eq(OpePurchasPayment.COL_DR, 0);
        opePurchasPaymentQueryWrapper.eq(OpePurchasPayment.COL_PAYMENT_STATUS, PayStatusEnums.UNPAID.getValue());
        opePurchasPaymentQueryWrapper.lt(OpePurchasPayment.COL_PAYMENT_PRIORITY,
                opePurchasPayment.getPaymentPriority());
        if (opePurchasPaymentService.count(opePurchasPaymentQueryWrapper) > 0) {
            throw new SesWebRosException(ExceptionCodeEnums.PAY_IN_INSTALLMENTS.getCode(),
                    (ExceptionCodeEnums.PAY_IN_INSTALLMENTS.getMessage()));
        }
        // 支付金额过滤
        if (opePurchasPayment.getAmount().subtract(enter.getAmount()).intValue() != 0) {
            throw new SesWebRosException(ExceptionCodeEnums.PAY_AMOUNT_IS_FALSE.getCode(),
                    (ExceptionCodeEnums.PAY_AMOUNT_IS_FALSE.getMessage()));
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
            list.add(FactoryCommonResult.builder().id(item.getId()).factoryName(item.getSupplierName())
                    // .contactFirstName(item.getContactFirstName())
                    // .contactLastName(item.getContactLastName())
                    .contactFullName(item.getContactFullName()).contactEmail(item.getContactEmail())
                    .contactPhoneCode(item.getContactPhoneCountryCode()).contactPhone(item.getContactPhone())
                    .annexPicture(null).build());
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
        List<PruchasingItemResult> resultList = new ArrayList<>();

        List<String> productTypeList = new ArrayList<>();
        if (StringUtils.isNotBlank(enter.getProductType())) {
            String[] split = enter.getProductType().split(",");
            for (String s : split) {
                productTypeList.add(s);
            }
            productTypeList.add("6");
            productTypeList.add("1");
        } else {
            for (BomCommonTypeEnums item : BomCommonTypeEnums.values()) {
                if (!item.getValue().equals(BomCommonTypeEnums.COMBINATION.getValue()) && !item.getValue().equals(BomCommonTypeEnums.SCOOTER.getValue())) {
                    productTypeList.add(item.getValue());
                }
            }
        }

        if (StringUtils.isEmpty(enter.getProductType()) || StringUtils.equals(enter.getProductType(), BomCommonTypeEnums.SCOOTER.getValue())) {
            // 整车产品查询列表
            List<PruchasingItemResult> scooterProductList = purchasingServiceMapper.queryPurchasScooter(enter, Lists.newArrayList(BomCommonTypeEnums.SCOOTER.getValue()));
            // 查询产品中包含的所有的部件
            if (CollectionUtils.isNotEmpty(scooterProductList)) {
                List<Long> productIds = scooterProductList.stream()
                        .filter(item -> StringUtils.equals(item.getProductType(), BomCommonTypeEnums.SCOOTER.getValue()))
                        .map(PruchasingItemResult::getId).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(productIds)) {
                    // 查询产品所有部件
                    List<PruchasingItemResult> partList = purchasingServiceMapper.queryProductPartItemByProductIds(productIds);
                    if (CollectionUtils.isNotEmpty(partList)) {
                        for (PruchasingItemResult scooter : scooterProductList) {
                            BigDecimal totalPrice = BigDecimal.ZERO;
                            List<PruchasingItemResult> scooterPartList = Lists.newArrayList();

                            for (PruchasingItemResult item : partList) {
                                if (item.getId().equals(scooter.getId())) {
                                    item.setProductType(Objects.requireNonNull(BomCommonTypeEnums.getEnumsByValue(item.getProductType())).getCode());
                                    totalPrice = totalPrice.add(item.getPrice());
                                    scooterPartList.add(item);
                                }
                            }
                            scooter.setPrice(totalPrice);
//                            if (scooter.getProductType().equals(BomCommonTypeEnums.SCOOTER.getValue())) {
                            if (BomCommonTypeEnums.SCOOTER.getValue().equals(scooter.getProductType())) {
                                scooter.setPruchasingItemResultList(scooterPartList);
                            }
                        }
                    }
                    resultList.addAll(scooterProductList);
                }
            }
            if (CollectionUtils.isNotEmpty(scooterProductList)) {
                //查询质检模板
                QueryWrapper<OpeProductionQualityTempate> opeProductionQualityTempateQueryWrapper = new QueryWrapper<>();
                opeProductionQualityTempateQueryWrapper.eq(OpeProductionQualityTempate.COL_PRODUCTION_TYPE, BomCommonTypeEnums.SCOOTER.getValue());
                opeProductionQualityTempateQueryWrapper.in(OpeProductionQualityTempate.COL_PRODUCTION_ID,
                        scooterProductList.stream().map(PruchasingItemResult::getId).collect(Collectors.toList()));
                List<OpeProductionQualityTempate> opeProductionQualityTempateList = opeProductionQualityTempateService.list(opeProductionQualityTempateQueryWrapper);

                if (CollectionUtils.isNotEmpty(opeProductionQualityTempateList)) {
                    scooterProductList.removeIf(item -> {
                        for (OpeProductionQualityTempate tempate : opeProductionQualityTempateList) {
                            if (tempate.getProductionId().equals(item.getId())) {
                                return false;
                            }
                        }
                        return true;
                    });
                    // 移除没有零部件的整车
                    scooterProductList.removeIf(scooter -> CollectionUtils.isEmpty(scooter.getPruchasingItemResultList()));
                }
            }
        }

        // 入参的productType不为空
        List<PruchasingItemResult> partProductList = purchasingServiceMapper.queryPurchasProductList(enter, productTypeList);
        if (CollectionUtils.isNotEmpty(partProductList)) {
            //查询质检模板
            QueryWrapper<OpeProductionQualityTempate> wrapper = new QueryWrapper<>();
            wrapper.in(OpeProductionQualityTempate.COL_PRODUCTION_ID, partProductList.stream().map(PruchasingItemResult::getId).collect(Collectors.toList()));
            wrapper.in(OpeProductionQualityTempate.COL_PRODUCTION_TYPE, productTypeList);
            List<OpeProductionQualityTempate> list = opeProductionQualityTempateService.list(wrapper);
            if (CollectionUtils.isNotEmpty(list)) {
                partProductList.removeIf(item -> {
                    return list.stream().noneMatch(templete -> item.getId().equals(templete.getProductionId()));
                });
            } else {
                return resultList;
            }
            resultList.addAll(partProductList);
        }
        // 加上库存的数量
        if (CollectionUtils.isNotEmpty(resultList)) {
            List<Long> partsIds = resultList.stream().map(PruchasingItemResult::getId).collect(Collectors.toList());
            switch (enter.getSource()) {
                case 0:
                    if (CollectionUtils.isNotEmpty(partsIds)) {
                        QueryWrapper<OpeWmsPartsStock> qw = new QueryWrapper<>();
                        qw.in(OpeWmsPartsStock.COL_PARTS_ID, partsIds);
                        qw.eq(OpeWmsPartsStock.COL_STOCK_TYPE, enter.getStockType());
                        List<OpeWmsPartsStock> partsList = opeWmsPartsStockService.list(qw);
                        if (CollectionUtils.isNotEmpty(partsList)) {
                            for (PruchasingItemResult result : resultList) {
                                for (OpeWmsPartsStock stock : partsList) {
                                    if (result.getId().equals(stock.getPartsId())) {
                                        result.setAbleQty(stock.getAbleStockQty());
                                    }
                                }
                            }
                        }
                    }
                default:
                    break;
                case 1:
                    // 这里表示从不合格品库创建出库 可出库的数量 需要从中国仓库不合格品库部件找数据
                    if (CollectionUtils.isNotEmpty(partsIds)) {
                        QueryWrapper<OpeWmsQualifiedPartsStock> qw = new QueryWrapper<>();
                        qw.in(OpeWmsQualifiedPartsStock.COL_PARTS_ID, partsIds);
                        List<OpeWmsQualifiedPartsStock> qualifiedPartsStocks = opeWmsQualifiedPartsStockService.list(qw);
                        if (CollectionUtils.isNotEmpty(qualifiedPartsStocks)) {
                            for (PruchasingItemResult result : resultList) {
                                for (OpeWmsQualifiedPartsStock qualifiedPartsStock : qualifiedPartsStocks) {
                                    if (result.getId().equals(qualifiedPartsStock.getPartsId())) {
                                        result.setAbleQty(qualifiedPartsStock.getQty());
                                    }
                                }
                            }
                        }
                    }
                    break;
            }

        }
        List<PruchasingItemResult> result = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(resultList) && null != enter.getSource() && (enter.getSource() == 0 || enter.getSource() == 1)) {
            for (PruchasingItemResult itemResult : resultList) {
                if (itemResult.getAbleQty() > 0) {
                    result.add(itemResult);
                }
            }
            return result;
        } else {
            return resultList;
        }
    }

    /**
     * 采购单详情商品列表
     *
     * @param enter
     * @return
     */
    @Override
    public List<PruchasingItemResult> pruchasingDetailProductList(PruchasingDetailProductEnter enter) {
        if (StringUtils.isNotEmpty(enter.getType())) {
            enter.setType(BomCommonTypeEnums.getCodeByValue(enter.getType()));
        }
        OpePurchas opePurchas = opePurchasService.getById(enter.getId());
        if (opePurchas == null) {
            throw new SesWebRosException(ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getCode(),
                    ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getMessage());
        }
        // 采购单商品列表
        List<PruchasingItemResult> result = purchasingServiceMapper.pruchasingDetailProductList(enter);

        // 查询来料质检节点 计算应交货时间
        QueryWrapper<OpePurchasTrace> opePurchasTraceQueryWrapper = new QueryWrapper<>();
        opePurchasTraceQueryWrapper.eq(OpePurchasTrace.COL_DR, 0);
        opePurchasTraceQueryWrapper.in(OpePurchasTrace.COL_PURCHAS_ID, enter.getId());
        opePurchasTraceQueryWrapper.eq(OpePurchasTrace.COL_STATUS, PurchasingStatusEnums.MATERIALS_QC.getValue());
        opePurchasTraceQueryWrapper.last("limit 1");
        OpePurchasTrace opePurchasTrace = opePurchasTraceService.getOne(opePurchasTraceQueryWrapper);
        if (opePurchasTrace == null) {
            return result;
        }
        // 交货时间计算
        result.forEach(item -> {
            item.setDueTime(DateUtil.addDays(opePurchasTrace.getCreateTime(), item.getLeadTime()));
        });
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

        List<PurchasSupplierResult> purchasSupplierResultList =
                purchasingServiceMapper.purchasSupplierListByPurchasId(enter);
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
            saveSupplierAnnexList =
                    JSONArray.parseArray(enter.getPurchasSupplierResultList(), SaveSupplierAnnexEnter.class);
        } catch (Exception e) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(),
                    ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }
        OpePurchas opePurchas = opePurchasService.getById(enter.getId());
        if (opePurchas == null) {
            throw new SesWebRosException(ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getCode(),
                    ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getMessage());
        }
        OpeFactory opeFactory = opeFactoryService.getById(enter.getFactoryId());
        if (opeFactory == null) {
            throw new SesWebRosException(ExceptionCodeEnums.FACTORY_IS_NOT_EXIST.getCode(),
                    ExceptionCodeEnums.FACTORY_IS_NOT_EXIST.getMessage());
        }
        opePurchas.setFactoryAnnex(enter.getFactoryAnnexPicture());
        opePurchas.setUpdatedBy(enter.getUserId());
        opePurchas.setUpdatedTime(new Date());
        opePurchasService.updateById(opePurchas);

        // 供应商 附件上传
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
                    throw new SesWebRosException(
                            ExceptionCodeEnums.SUPPLIER_IS_NOT_PART_OF_THE_CURRENT_DOCUMENT.getCode(),
                            ExceptionCodeEnums.SUPPLIER_IS_NOT_PART_OF_THE_CURRENT_DOCUMENT.getMessage());
                }
            });

            for (OpePurchasB item : purchasBList) {
                for (SaveSupplierAnnexEnter supplier : saveSupplierAnnexList) {
                    if (item.getPartId().equals(supplier.getPartsId())
                            && item.getSupplierId().equals(supplier.getId())) {
                        item.setSupplierAnnex(supplier.getAnnex());
                        item.setUpdatedBy(enter.getUserId());
                        item.setUpdatedTime(new Date());
                    }
                }
            }
        }
        opePurchasBService.updateBatchById(purchasBList);

        // 开始qc质检
        IdEnter idEnter = new IdEnter();
        BeanUtils.copyProperties(enter, idEnter);
        idEnter.setId(enter.getId());
        this.startQc(idEnter);
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
        OpePurchas opePurchas = checkPurchasRepeatedly(enter.getId(), PurchasingStatusEnums.PENDING);
        if (StringUtils.isEmpty(opePurchas.getFactoryAnnex())) {
            throw new SesWebRosException(ExceptionCodeEnums.FACTORY_ANNEX_IS_PERFECT.getCode(),
                    ExceptionCodeEnums.FACTORY_ANNEX_IS_PERFECT.getMessage());
        }
        // 供应商 附件上传
        QueryWrapper<OpePurchasB> opePurchasBQueryWrapper = new QueryWrapper<>();
        opePurchasBQueryWrapper.eq(OpePurchasB.COL_DR, 0);
        opePurchasBQueryWrapper.eq(OpePurchasB.COL_PURCHAS_ID, opePurchas.getId());
        List<OpePurchasB> purchasBList = opePurchasBService.list(opePurchasBQueryWrapper);
        if (CollectionUtils.isNotEmpty(purchasBList)) {
            purchasBList.forEach(item -> {
                if (StringUtils.isEmpty(item.getSupplierAnnex())) {
                    throw new SesWebRosException(ExceptionCodeEnums.FACTORY_ANNEX_IS_PERFECT.getCode(),
                            ExceptionCodeEnums.FACTORY_ANNEX_IS_PERFECT.getMessage());
                }
            });
        }
        opePurchas.setStatus(PurchasingStatusEnums.MATERIALS_QC.getValue());
        opePurchas.setUpdatedTime(new Date());
        opePurchas.setUpdatedBy(enter.getUserId());
        opePurchasService.updateById(opePurchas);

        // 节点保存
        SaveNodeEnter saveNodeEnter = new SaveNodeEnter();
        BeanUtils.copyProperties(enter, saveNodeEnter);
        saveNodeEnter.setId(opePurchas.getId());
        saveNodeEnter.setStatus(PurchasingStatusEnums.MATERIALS_QC.getValue());
        saveNodeEnter.setEvent(PurchasingEventEnums.MATERIALS_QC.getValue());
        saveNodeEnter.setMemo(null);
        this.savePurchasingNode(saveNodeEnter);

        // 采购条目qc 状态修改
        purchasBList.forEach(item -> {
            item.setQcStatus(QcStatusEnums.QUALITY_INSPECTION.getValue());
            item.setUpdatedBy(enter.getUserId());
            item.setUpdatedTime(new Date());
        });
        opePurchasBService.updateBatchById(purchasBList);

        // 待生产 库存埋点
        stockToBeProduced(purchasBList);
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
        Map<String, Integer> map = Maps.newHashMap();
        List<OpePurchasBQc> opePurchasBQcList = purchasingServiceMapper.qcCountByStatus(enter);
        if (CollectionUtils.isNotEmpty(opePurchasBQcList)) {
            int passTotal = 0;
            int failTotal = 0;
            for (OpePurchasBQc item : opePurchasBQcList) {
                if (item.getPassCount() != 0) {
                    passTotal++;
                }
                if (item.getFailCount() != 0) {
                    failTotal++;
                }
            }
            map.put(QcStatusEnums.PASS.getValue(), passTotal);
            map.put(QcStatusEnums.FAIL.getValue(), failTotal);
        }

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
        checkPurchasRepeatedly(enter.getId(), null);
        List<QcInfoResult> qcPartList = purchasingServiceMapper.qcPartListByPurchasId(enter);

        if (CollectionUtils.isEmpty(qcPartList)) {
            return null;
        }

        List<Long> purshasBIds = Lists.newArrayList();
        qcPartList.forEach(item -> {
            purshasBIds.add(item.getId());
        });
        // 查询质检记录
        List<QcItemDetailResult> qcItemList = purchasingServiceMapper.qcItemListByPurchasBIds(purshasBIds, enter);
        if (CollectionUtils.isEmpty(qcItemList)) {
            return null;
        }

        for (QcInfoResult item : qcPartList) {

            List<QcItemDetailResult> qcItemResultList = Lists.newArrayList();

            for (QcItemDetailResult qc : qcItemList) {

                if (item.getId().equals(qc.getPruchasBId())) {
                    if (StringUtils.equals(enter.getStatus(), QcStatusEnums.PASS.getValue()) && qc.getPassQty() != 0) {
                        qcItemResultList.add(qc);
                    }
                    if (StringUtils.equals(enter.getStatus(), QcStatusEnums.FAIL.getValue()) && qc.getFailQty() != 0) {
                        qcItemResultList.add(qc);
                    }
                }
            }
            item.setQcItemDetailResultList(qcItemResultList);
        }
        // todo 需修改
        qcPartList.removeIf(item -> CollectionUtils.isEmpty(item.getQcItemDetailResultList()));

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
    public GeneralResult savePurchasingNode(SaveNodeEnter enter) {
        opePurchasTraceService.save(OpePurchasTrace.builder().id(idAppService.getId(SequenceName.OPE_PURCHAS_TRACE))
                .dr(0).tenantId(0L).userId(enter.getUserId()).purchasId(enter.getId()).status(enter.getStatus())
                .event(enter.getEvent()).eventTime(new Date())
                .memo(StringUtils.isBlank(enter.getMemo()) == true ? null : enter.getMemo()).createBy(enter.getUserId())
                .createTime(new Date()).updateBy(enter.getUserId()).updateTime(new Date()).build());
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 取消 采购单
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult cancel(IdEnter enter) {
        OpePurchas opePurchas = checkPurchasRepeatedly(enter.getId(), PurchasingStatusEnums.PENDING);

        opePurchas.setStatus(PurchasingStatusEnums.CANCELLED.getValue());
        opePurchas.setUpdatedBy(enter.getUserId());
        opePurchas.setUpdatedTime(new Date());
        opePurchasService.updateById(opePurchas);

        // 节点
        SaveNodeEnter saveNodeEnter = new SaveNodeEnter();
        BeanUtils.copyProperties(enter, saveNodeEnter);
        saveNodeEnter.setId(opePurchas.getId());
        saveNodeEnter.setStatus(PurchasingStatusEnums.CANCELLED.getValue());
        saveNodeEnter.setEvent(PurchasingEventEnums.CANCELLED.getValue());
        saveNodeEnter.setMemo(null);
        this.savePurchasingNode(saveNodeEnter);
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
    private void buildSavePurchasPaymentInfo(SavePurchasingEnter enter, List<StagingPaymentEnter> paymentList,
                                             Long purchasId, List<OpePurchasPayment> opePurchasPaymentList) {
        // 付款数据封装
        BigDecimal totalPrice = BigDecimal.ZERO;
        if (StringUtils.equals(enter.getPaymentType(), PaymentTypeEnums.MONTHLY_PAY.getValue())) {
            // 月结
            // 参数过滤
            if (enter.getDays() == null || enter.getStatementdate() == null) {
                throw new SesWebRosException(ExceptionCodeEnums.PAYMENT_INFO_IS_WRONG.getCode(),
                        ExceptionCodeEnums.PAYMENT_INFO_IS_WRONG.getMessage());
            }
            opePurchasPaymentList
                    .add(OpePurchasPayment.builder().id(idAppService.getId(SequenceName.OPE_PURCHAS_PAYMENT)).dr(0)
                            .tenantId(0L).userId(enter.getUserId()).purchasId(purchasId).paymentType(enter.getPaymentType())
                            .plannedPaymentTime(enter.getStatementdate()).paymentDay(enter.getDays()).paymentTime(null)
                            .paymentStatus(PayStatusEnums.UNPAID.getValue()).description(enter.getRemark())
                            .amount(enter.getTotalPrice()).amountProportion(Constant.AMOUNTP_ROPORTION).invoiceNum(null)
                            .invoicePicture(null).paymentPriority(1).revision(0).createdBy(enter.getUserId())
                            .createdTime(new Date()).updatedBy(enter.getUserId()).updatedTime(new Date()).build());
            totalPrice = totalPrice.add(enter.getTotalPrice());
        } else {
            // 分期
            int amountProportion = 0;
            for (int i = 0; i < paymentList.size(); i++) {
                // 参数过滤
                if (paymentList.get(i).getEstimatedPaymentDate() == null || paymentList.get(i).getRatio() == null
                        || paymentList.get(i).getPrice() == null) {
                    throw new SesWebRosException(ExceptionCodeEnums.PAYMENT_INFO_IS_WRONG.getCode(),
                            ExceptionCodeEnums.PAYMENT_INFO_IS_WRONG.getMessage());
                }

                opePurchasPaymentList.add(OpePurchasPayment.builder()
                        .id(idAppService.getId(SequenceName.OPE_PURCHAS_PAYMENT)).dr(0).tenantId(0L)
                        .userId(enter.getUserId()).purchasId(purchasId).paymentType(enter.getPaymentType())
                        .plannedPaymentTime(paymentList.get(i).getEstimatedPaymentDate()).paymentDay(null).paymentTime(null)
                        .paymentStatus(PayStatusEnums.UNPAID.getValue()).description(paymentList.get(i).getRemark())
                        .amount(paymentList.get(i).getPrice()).amountProportion(paymentList.get(i).getRatio())
                        .invoiceNum(null).invoicePicture(null).paymentPriority(i).revision(0).createdBy(enter.getUserId())
                        .createdTime(new Date()).updatedBy(enter.getUserId()).updatedTime(new Date()).build());
                totalPrice = totalPrice.add(paymentList.get(i).getPrice());
                amountProportion = amountProportion + paymentList.get(i).getRatio();
            }
            if (amountProportion != Constant.AMOUNTP_ROPORTION) {
                throw new SesWebRosException(ExceptionCodeEnums.PAYMENT_INFO_IS_WRONG.getCode(),
                        ExceptionCodeEnums.PAYMENT_INFO_IS_WRONG.getMessage());
            }
        }
        if (totalPrice.doubleValue() != enter.getTotalPrice().doubleValue()) {
            throw new SesWebRosException(ExceptionCodeEnums.PAYMENT_INFO_IS_WRONG.getCode(),
                    ExceptionCodeEnums.PAYMENT_INFO_IS_WRONG.getMessage());
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
    private OpePurchas buildPurchas(SavePurchasingEnter enter, List<ProductionPartsEnter> productsList, Long purchasId,
                                    List<OpePurchasB> opePurchasBList, List<OpePurchasProduct> opePurchasProductList) {

        Map<Long, String> productMap = null;

        // 整车集合
        List<OpeProductionScooterBom> opeProductionScooterBomList = null;

        // 部件集合
        List<OpeProductionParts> partsProductList = null;
        // 部件校验
        // 部件集合
        Set<Long> partIdList = productsList.stream()
                .filter(item -> !StringUtils.equalsAny(item.getProductionProductType(),
                        BomCommonTypeEnums.COMBINATION.getValue(), BomCommonTypeEnums.SCOOTER.getValue()))
                .map(ProductionPartsEnter::getId).collect(Collectors.toSet());

        Set<Long> scooterIdList = productsList.stream()
                .filter(
                        item -> StringUtils.equalsAny(item.getProductionProductType(), BomCommonTypeEnums.SCOOTER.getValue()))
                .map(ProductionPartsEnter::getId).collect(Collectors.toSet());
        if (CollectionUtils.isNotEmpty(partIdList)) {
            QueryWrapper<OpeProductionParts> productionPartsQueryWrapper = new QueryWrapper<>();
            productionPartsQueryWrapper.in(OpeProductionParts.COL_ID, partIdList);
            partsProductList = opeProductionPartsService.list(productionPartsQueryWrapper);
            if (CollectionUtils.isEmpty(partsProductList)) {
                throw new SesWebRosException(ExceptionCodeEnums.PART_PRODUCT_IS_NOT_EXIST.getCode(),
                        ExceptionCodeEnums.PART_PRODUCT_IS_NOT_EXIST.getMessage());
            }
            productMap = partsProductList.stream()
                    .collect(Collectors.toMap(OpeProductionParts::getId, OpeProductionParts::getPartsNo));
        }
        if (CollectionUtils.isNotEmpty(scooterIdList)) {
            QueryWrapper<OpeProductionScooterBom> opeProductionScooterBomQueryWrapper = new QueryWrapper<>();
            opeProductionScooterBomQueryWrapper.in(OpeProductionScooterBom.COL_ID, scooterIdList);
            opeProductionScooterBomQueryWrapper.eq(OpeProductionScooterBom.COL_BOM_STATUS,
                    ProductionBomStatusEnums.ACTIVE.getValue());
            opeProductionScooterBomList = opeProductionScooterBomService.list(opeProductionScooterBomQueryWrapper);
            if (CollectionUtils.isEmpty(opeProductionScooterBomList)) {
                throw new SesWebRosException(ExceptionCodeEnums.PART_PRODUCT_IS_NOT_EXIST.getCode(),
                        ExceptionCodeEnums.PART_PRODUCT_IS_NOT_EXIST.getMessage());
            }
            productMap = opeProductionScooterBomList.stream()
                    .collect(Collectors.toMap(OpeProductionScooterBom::getId, OpeProductionScooterBom::getBomNo));
        }
        //订单表数据组装
        productMap.forEach((key, value) -> {
            opePurchasProductList.add(
                    OpePurchasProduct.builder()
                            .id(idAppService.getId(SequenceName.OPE_PURCHAS_PRODUCT))
                            .dr(0)
                            .tenantId(0L)
                            .userId(enter.getUserId())
                            .purchasId(purchasId)
                            .productId(key)
                            .idClass(null)
                            .productType(productsList.stream().filter(product -> product.getId().equals(key)).findFirst()
                                    .orElse(null).getProductionProductType())
                            .productNum(value)
                            .revision(0)
                            .createdBy(enter.getUserId())
                            .createdTime(new Date())
                            .updatedBy(enter.getUserId())
                            .updatedTime(new Date())
                            .build()
            );
        });

        //进行部品归类
        HashMap<Long, Integer> partMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(partsProductList)) {
            partsProductList.forEach(item -> {
                ProductionPartsEnter productionPartsEnter = productsList.stream().filter(partQty -> (item.getId().equals(partQty.getId())) && StringUtils.equals(String.valueOf(item.getPartsType()),
                        partQty.getProductionProductType())).findFirst().orElse(null);
                if (productionPartsEnter != null) {
                    if (!partMap.containsKey(item.getId())) {
                        // 不存在部品id 就重新放入
                        partMap.put(item.getId(), productionPartsEnter.getQty());
                    } else {
                        // 存在部品id 对数量进行维护
                        partMap.put(item.getId(), partMap.get(item.getId()) + productionPartsEnter.getQty());
                    }
                }

            });
        }
        if (CollectionUtils.isNotEmpty(opeProductionScooterBomList)) {
            // 查询 整车部件
            QueryWrapper<OpeProductionPartsRelation> opeProductionPartsRelationQueryWrapper = new QueryWrapper<>();
            opeProductionPartsRelationQueryWrapper.in(OpeProductionPartsRelation.COL_PRODUCTION_ID, scooterIdList);
            opeProductionPartsRelationQueryWrapper.eq(OpeProductionPartsRelation.COL_PRODUCTION_TYPE,
                    ProductionPartsRelationTypeEnums.SCOOTER_BOM.getValue());
            List<OpeProductionPartsRelation> opeProductionPartsRelationList =
                    opeProductionPartsRelationService.list(opeProductionPartsRelationQueryWrapper);
            if (CollectionUtils.isEmpty(opeProductionPartsRelationList)) {
                throw new SesWebRosException(ExceptionCodeEnums.PART_PRODUCT_IS_NOT_EXIST.getCode(),
                        ExceptionCodeEnums.PART_PRODUCT_IS_NOT_EXIST.getMessage());
            }
            opeProductionPartsRelationList.forEach(item -> {
                // 获取整车数量
                Integer scooterQty = productsList.stream()
                        .filter(product -> (StringUtils.equalsAny(product.getProductionProductType(),
                                BomCommonTypeEnums.SCOOTER.getValue()) && product.getId().equals(item.getProductionId())))
                        .findFirst().orElse(null).getQty();

                if (!partMap.containsKey(item.getPartsId())) {
                    // 不存在部品id 就重新放入
                    partMap.put(item.getPartsId(), item.getPartsQty() * scooterQty);
                } else {
                    // 存在部品id 对数量进行维护
                    partMap.put(item.getPartsId(), partMap.get(item.getPartsId()) + item.getPartsQty() * scooterQty);
                }
            });

        }

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
                            .price(item.getPrice())
                            .totalPrice(item.getPrice().equals(BigDecimal.ZERO) ? BigDecimal.ZERO : item.getPrice().multiply(new BigDecimal(v)))
                            .totalCount(v)
                            .laveWaitQcQty(v)
                            .inWaitWhQty(v)
                            .createdBy(enter.getUserId())
                            .createdTime(new Date())
                            .updatedTime(new Date())
                            .updatedBy(enter.getUserId())
                            .build();
                    opePurchasBList.add(purchasB);
                    totalPrice = totalPrice.add(purchasB.getTotalPrice());
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
                .contractNo("RED" + new Random().nextInt(10000))
                .status(PurchasingStatusEnums.PENDING.getValue())
                .paymentType(enter.getPaymentType())
                .factoryId(enter.getFactoryId())
                .totalPrice(totalPrice)
                .totalQty(totalCount)
                .laveWaitQcTotal(totalCount)
                .inWaitWhTotal(totalCount)
                .revision(0)
                .createdBy(enter.getUserId())
                .createdTime(new Date())
                .updatedTime(new Date())
                .updatedBy(enter.getUserId())
                .build();
    }

    private OpePurchas checkPurchasRepeatedly(Long id, PurchasingStatusEnums status) {
        OpePurchas opePurchas = opePurchasService.getById(id);
        if (opePurchas == null) {
            throw new SesWebRosException(ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getCode(),
                    ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getMessage());
        }
        if (status != null) {
            if (!StringUtils.equals(opePurchas.getStatus(), status.getValue())) {
                throw new SesWebRosException(ExceptionCodeEnums.STATUS_ILLEGAL.getCode(),
                        ExceptionCodeEnums.STATUS_ILLEGAL.getMessage());
            }
        }
        return opePurchas;
    }

    /**
     * 待生产 库存埋点
     *
     * @param purchasBList
     */
    private void stockToBeProduced(List<OpePurchasB> purchasBList) {
        // 查询仓库
        OpeWhse whse = opeWhseService
                .getOne(new LambdaQueryWrapper<OpeWhse>().eq(OpeWhse::getType, WhseTypeEnums.PURCHAS.getValue()));
        if (whse == null) {
            throw new SesWebRosException(ExceptionCodeEnums.WAREHOUSE_IS_NOT_EXIST.getCode(),
                    ExceptionCodeEnums.WAREHOUSE_IS_NOT_EXIST.getMessage());
        }

        // 查询部件
        List<OpeProductionParts> opeProductionPartsList =
                opeProductionPartsService.list(new LambdaQueryWrapper<OpeProductionParts>().in(OpeProductionParts::getId,
                        purchasBList.stream().map(OpePurchasB::getPartId).collect(Collectors.toList())));
        if (CollectionUtils.isEmpty(opeProductionPartsList) || opeProductionPartsList.size() != purchasBList.size()) {
            throw new SesWebRosException(ExceptionCodeEnums.PART_IS_NOT_EXIST.getCode(),
                    ExceptionCodeEnums.PART_IS_NOT_EXIST.getMessage());
        }

        List<Long> partIds =
                opeProductionPartsList.stream().map(OpeProductionParts::getId).collect(Collectors.toList());

        List<OpeStock> opeStockList =
                opeStockService.list(new LambdaQueryWrapper<OpeStock>().in(OpeStock::getMaterielProductId, partIds)
                        .in(OpeStock::getMaterielProductType, BomCommonTypeEnums.ACCESSORY.getValue(),
                                BomCommonTypeEnums.PARTS.getValue(), BomCommonTypeEnums.BATTERY.getValue())
                        .eq(OpeStock::getWhseId, whse.getId()));

        List<OpeStock> saveOpeStockList = new ArrayList<>();

        if (CollectionUtils.isEmpty(opeStockList)) {
            for (OpePurchasB item : purchasBList) {
                OpeProductionParts parts = opeProductionPartsList.stream()
                        .filter(part -> item.getPartId().equals(part.getId())).findFirst().orElse(null);
                // 创建库存
                saveOpeStockList.add(buildStock(whse, parts, item));
            }
        } else {
            OpeStock opeStock = null;
            for (OpePurchasB item : purchasBList) {
                opeStock = opeStockList.stream().filter(stock -> stock.getMaterielProductId().equals(item.getPartId()))
                        .findFirst().orElse(null);
                if (opeStock != null) {
                    // 更新库存
                    opeStock.setWaitProductTotal(item.getInWaitWhQty() + opeStock.getWaitProductTotal());
                    saveOpeStockList.add(opeStock);
                } else {
                    OpeProductionParts parts = opeProductionPartsList.stream()
                            .filter(part -> item.getPartId().equals(part.getId())).findFirst().orElse(null);
                    saveOpeStockList.add(buildStock(whse, parts, item));
                }
            }
        }

        // 更新库存
        if (CollectionUtils.isNotEmpty(saveOpeStockList)) {
            opeStockService.saveOrUpdateBatch(saveOpeStockList);
        }
    }

    /**
     * 构建 stock 对象
     *
     * @param whse
     * @param parts
     * @param item
     * @return
     */
    private OpeStock buildStock(OpeWhse whse, OpeProductionParts parts, OpePurchasB item) {
        OpeStock opeStock = OpeStock.builder().id(idAppService.getId(SequenceName.OPE_STOCK)).dr(0).userId(0L)
                .tenantId(0L).whseId(whse.getId()).intTotal(0).availableTotal(0).outTotal(0).wornTotal(0).lockTotal(0)
                .waitProductTotal(item.getTotalCount()).waitStoredTotal(0).materielProductId(item.getPartId())
                .materielProductName(parts.getCnName())
                .materielProductType(String.valueOf(parts.getPartsType())).revision(0)
                .updatedBy(0L).updatedTime(new Date()).createdBy(0L).createdTime(new Date()).build();
        return opeStock;
    }

}
