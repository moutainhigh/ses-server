package com.redescooter.ses.web.ros.service.sellsy.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.redescooter.ses.api.common.constant.JedisConstant;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.starter.redis.enums.RedisExpireEnum;
import com.redescooter.ses.tool.utils.DateUtil;
import com.redescooter.ses.web.ros.config.SellsyConfig;
import com.redescooter.ses.web.ros.constant.SellsyConstant;
import com.redescooter.ses.web.ros.constant.SellsyMethodConstant;
import com.redescooter.ses.web.ros.dm.*;
import com.redescooter.ses.web.ros.enums.sellsy.*;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.exception.ThirdExceptionCodeEnums;
import com.redescooter.ses.web.ros.service.base.*;
import com.redescooter.ses.web.ros.service.excel.ExcelService;
import com.redescooter.ses.web.ros.service.sellsy.*;
import com.redescooter.ses.web.ros.vo.sellsy.enter.SellsyClientServiceCreateDocumentEnter;
import com.redescooter.ses.web.ros.vo.sellsy.enter.SellsyExecutionEnter;
import com.redescooter.ses.web.ros.vo.sellsy.enter.SellsyIdEnter;
import com.redescooter.ses.web.ros.vo.sellsy.enter.SellsyImportExcelResult;
import com.redescooter.ses.web.ros.vo.sellsy.enter.catalogue.*;
import com.redescooter.ses.web.ros.vo.sellsy.enter.client.*;
import com.redescooter.ses.web.ros.vo.sellsy.enter.document.*;
import com.redescooter.ses.web.ros.vo.sellsy.result.SellsyExcleData;
import com.redescooter.ses.web.ros.vo.sellsy.result.SellsyGeneralResult;
import com.redescooter.ses.web.ros.vo.sellsy.result.SellsyIdResult;
import com.redescooter.ses.web.ros.vo.sellsy.result.account.*;
import com.redescooter.ses.web.ros.vo.sellsy.result.catalogue.SellsyCatalogueResult;
import com.redescooter.ses.web.ros.vo.sellsy.result.client.SellsyClientResult;
import com.redescooter.ses.web.ros.vo.sellsy.result.document.SellsyDocumentListResult;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisCluster;

import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName:DocumentServiceImpl
 * @description: DocumentServiceImpl
 * @author: Alex @Version：1.3
 * @create: 2020/08/26 14:34
 */
@Log4j2
@Service
public class SellsyDocumentServiceImpl implements SellsyDocumentService {

    @Autowired
    private SellsyService sellsyService;

    @Autowired
    private SellsyClientService sellsyClientService;

    @Autowired
    private SellsyAccountSettingService sellsyAccountSettingService;

    @Autowired
    private ExcelService excelService;

    @Autowired
    private SellsyInvoiceService sellsyInvoiceService;

    @Autowired
    private SellsyInvoiceBService sellsyInvoiceBService;

    @Autowired
    private SellsyCatalogueService sellsyCatalogueService;

    @Autowired
    private SellsyConfig sellsyConfig;

    @Autowired
    private JedisCluster jedisCluster;

    @Autowired
    private SellsyProductService sellsyProductService;

    @Autowired
    private SellsyCustomerService sellsyCustomerService;

    @Autowired
    private SellsyInvoiceTotalService sellsyInvoiceTotalService;

    @Reference
    private IdAppService idAppService;

    /**
     * 单据列表
     *
     * @param enter
     */
    @Override
    public List<SellsyDocumentListResult> queryDocumentList(SellsyDocumentListEnter enter) {
        SellsyExecutionEnter sellsyExecutionEnter =
            SellsyExecutionEnter.builder().method(SellsyMethodConstant.Document_GetList).params(enter)
                .SellsyMethodType(SellsyMethodTypeEnums.QUERY.getValue()).build();

        SellsyGeneralResult sellsyGeneralResult = sellsyService.sellsyExecution(sellsyExecutionEnter);

        return sellsyService.jsonArrayFormattingByPage(sellsyGeneralResult, new SellsyDocumentListResult());
    }

    /**
     * 查询指定单据
     *
     * @param enter
     */
    @Override
    public JSONObject queryDocumentOne(SellsyDocumentOneEnter enter) {
        SellsyExecutionEnter sellsyExecutionEnter =
            SellsyExecutionEnter.builder().method(SellsyMethodConstant.Document_GetOne).params(enter)
                .SellsyMethodType(SellsyMethodTypeEnums.ADD.getValue()).build();
        SellsyGeneralResult sellsyGeneralResult = sellsyService.sellsyExecution(sellsyExecutionEnter);
        log.info("-------------result结果返回值{}--------------------", sellsyGeneralResult.getResult().toString());

        return JSONObject.parseObject(sellsyGeneralResult.getResult().toString());
    }

    /**
     * 发票创建
     *
     * @param enter
     */
    @Override
    @Transactional
    public SellsyIdResult createDocument(SellsyClientServiceCreateDocumentEnter enter) {

        // 校验客户
        SellsyClientResult sellsyClientResult =
            sellsyClientService.queryClientOne(new SellsyQueryClientOneEnter(enter.getThirdid()));
        if (sellsyClientResult == null) {
            throw new SesWebRosException(ThirdExceptionCodeEnums.SELLSY_CLIENT_IS_NOT_EXIST.getCode(),
                ThirdExceptionCodeEnums.SELLSY_CLIENT_IS_NOT_EXIST.getMessage());
        }
        // 如果传入发票单号 ，则进行校验
        if (StringUtils.isNotBlank(enter.getIdent())) {
            SellsyDocumentListEnter sellsyDocumentListEnter = SellsyDocumentListEnter.builder()
                .doctype(SellsyDocmentTypeEnums.invoice).ident(enter.getIdent()).build();
            List<SellsyDocumentListResult> sellsyDocumentListResultPageResult =
                queryDocumentList(sellsyDocumentListEnter);
            if (CollectionUtils.isNotEmpty(sellsyDocumentListResultPageResult) && sellsyDocumentListResultPageResult
                .stream().filter(item -> StringUtils.equals(item.getIdent(), enter.getIdent())).findFirst()
                .orElse(null) != null) {
                throw new SesWebRosException(ThirdExceptionCodeEnums.SELLSY_DOCUMENT_IS_ALREADY_EXIST.getCode(),
                    ThirdExceptionCodeEnums.SELLSY_DOCUMENT_IS_ALREADY_EXIST.getMessage());
            }
        }

        // 查询税率 是税前发票 还是税后发票
        if (enter.getRateCategory() != null && enter.getRateCategory() != 0) {
            // 从缓存中取发票类型
            SellsyRateCategoryResult sellsyRateCategoryResult =
                getJedisDate(JedisConstant.SELLSY_DOCUEMNT_RATECATEGORY, new SellsyRateCategoryResult());
            if (sellsyRateCategoryResult == null) {

                // 缓存没有 查询sellsy
                sellsyRateCategoryResult =
                    sellsyAccountSettingService.queryateCategoryOne(new SellsyIdEnter(enter.getRateCategory()));
                if (sellsyRateCategoryResult == null) {
                    throw new SesWebRosException(ThirdExceptionCodeEnums.SELLSY_RATE_CATEGORY_IS_NOT_EXIST.getCode(),
                        ThirdExceptionCodeEnums.SELLSY_RATE_CATEGORY_IS_NOT_EXIST.getMessage());
                }
                // 更新 缓存
                setJedisDate(JedisConstant.SELLSY_DOCUEMNT_RATECATEGORY, sellsyRateCategoryResult);
            }
        }

        if (enter.getCurrency() != null && enter.getCurrency() != 0) {
            // 货币单位校验
            SellsyCurrencyResult sellsyCurrencyResult = getJedisDate(
                JedisConstant.SELLSY_DOCUEMNT_RATECATEGORY + enter.getCurrency(), new SellsyCurrencyResult());

            if (sellsyCurrencyResult == null) {
                sellsyCurrencyResult =
                    sellsyAccountSettingService.queryCurrencyOne(new SellsyIdEnter(enter.getCurrency()));
                if (sellsyCurrencyResult == null) {
                    throw new SesWebRosException(
                        ThirdExceptionCodeEnums.SELLSY_DOCUMENT_CURRENCY_IS_NOT_EXIST.getCode(),
                        ThirdExceptionCodeEnums.SELLSY_DOCUMENT_CURRENCY_IS_NOT_EXIST.getMessage());
                }
                // 更新黄村
                setJedisDate(JedisConstant.SELLSY_DOCUEMNT_RATECATEGORY + enter.getCurrency(), sellsyCurrencyResult);
            }
        }

        // 地址解析
        /*if (enter.getThirdaddress()!=null){
            SellsyClientAddressDetailResult sellsyClientAddressDetailResult = sellsyClientService.queryClientAddress(new QueryClientAddressEnter(enter.getThirdid(), enter.getThirdaddress().getId()));
            if (sellsyClientAddressDetailResult==null){
                throw new SesWebRosException(ThirdExceptionCodeEnums.SELLSY_ADDRESS_IS_NOT_EXIST.getCode(),ThirdExceptionCodeEnums.SELLSY_ADDRESS_IS_NOT_EXIST.getMessage());
            }
        }
        if (enter.getShipaddress()!=null){
            SellsyClientAddressDetailResult sellsyClientAddressDetailResult = sellsyClientService.queryClientAddress(new QueryClientAddressEnter(enter.getThirdid(), enter.getThirdaddress().getId()));
            if (sellsyClientAddressDetailResult==null){
                throw new SesWebRosException(ThirdExceptionCodeEnums.SELLSY_ADDRESS_IS_NOT_EXIST.getCode(),ThirdExceptionCodeEnums.SELLSY_ADDRESS_IS_NOT_EXIST.getMessage());
            }
        }*/

        /* //布局Id校验
        List<SellsyLayoutResult> sellsyLayoutResultList = sellsyAccountSettingService.queryDocLayoutList();
        if (CollectionUtils.isEmpty(sellsyLayoutResultList)) {
         throw new SesWebRosException(ThirdExceptionCodeEnums.SELLSY_LAYOUT_IS_NOT_EXIST.getCode(), ThirdExceptionCodeEnums.SELLSY_LAYOUT_IS_NOT_EXIST.getMessage());
        }
        SellsyLayoutResult sellsyLayoutResult = sellsyLayoutResultList.stream().filter(item -> StringUtils.equals(item.getId(), String.valueOf(enter.getDoclayout()))).findFirst().orElse(null);
        if (sellsyLayoutResult == null) {
         throw new SesWebRosException(ThirdExceptionCodeEnums.SELLSY_LAYOUT_IS_NOT_EXIST.getCode(), ThirdExceptionCodeEnums.SELLSY_LAYOUT_IS_NOT_EXIST.getMessage());
        }
        
        if (enter.getDoclang() != null || enter.getDoclang() != 0) {
         //语言Id校验
         List<SellsyTranslationLanguageResult> sellsyTranslationLanguageResultList = sellsyAccountSettingService.queryTranslationLanguages();
         if (CollectionUtils.isEmpty(sellsyTranslationLanguageResultList)) {
             throw new SesWebRosException(ThirdExceptionCodeEnums.SELLSY_LAYOUT_IS_NOT_EXIST.getCode(), ThirdExceptionCodeEnums.SELLSY_LAYOUT_IS_NOT_EXIST.getMessage());
         }
         SellsyTranslationLanguageResult sellsyTranslationLanguageResult =
                 sellsyTranslationLanguageResultList.stream().filter(item -> StringUtils.equals(item.getId(), String.valueOf(enter.getDoclang()))).findFirst().orElse(null);
         if (sellsyTranslationLanguageResult == null) {
             throw new SesWebRosException(ThirdExceptionCodeEnums.SELLSY_TRANSLATION_LANG_IS_NOT_EXIST.getCode(), ThirdExceptionCodeEnums.SELLSY_TRANSLATION_LANG_IS_NOT_EXIST.getMessage());
         }
        }*/

        // 封装 入参属性
        CreateDocumentAttributesEnter createDocumentAttributesEnter = buildCreateDocumentAttributesEnter(enter);
        // 数字格式 封装
        SellsyNumFormatEnter numFormatEnter = new SellsyNumFormatEnter();

        if (enter.getCurrency() != null && enter.getCurrency() != 0) {
            numFormatEnter.setCurrencyid(enter.getCurrency());
        } else {
            numFormatEnter.setCurrencyid(sellsyConfig.getCurrencyUnit());
        }
        numFormatEnter.setPrecision(sellsyConfig.getDecimalPlaces());

        Map<String, SellsyRowEnter> rosMap = new HashMap<>();
        // 第三行
        String key = null;
        for (int i = 0; i < enter.getSellsellEnterList().size(); i++) {
            SellsyRowEnter item = enter.getSellsellEnterList().get(i);
            key = String.valueOf(Integer.valueOf(SellsyDocumentRosTypeEnums.ITEM.getCode()) + i);
            // 构建产品行
            buildProductItemRow(rosMap, key, item);

        }

        // 第五行
        rosMap.put(String.valueOf(Integer.parseInt(key) + 1),
            SellsyRowEnter.builder().row_type(SellsyDocumentRosTypeEnums.SUM.getValue()).build());

        /* //第六行
        rosMap.put(SellsyDocumentRosTypeEnums.TITLE.getCode(), SellsyRowEnter
                .builder()
                .row_type(SellsyDocumentRosTypeEnums.TITLE.getValue())
                .row_title(enter.getSellsellEnter().getRow_title())
                .build());
        
        //第七行
        rosMap.put(SellsyDocumentRosTypeEnums.COMMENT.getCode(), SellsyRowEnter
                .builder()
                .row_type(SellsyDocumentRosTypeEnums.COMMENT.getValue())
                .row_comment(enter.getSellsellEnter().getRow_comment())
                .build());
        //第8行
        rosMap.put(SellsyDocumentRosTypeEnums.BREAK.getCode(), SellsyRowEnter.builder().row_type(SellsyDocumentRosTypeEnums.BREAK.getValue()).build());
        */
        SellsyCreateDocumentEnter sellsyCreateDocumentEnter = new SellsyCreateDocumentEnter();
        sellsyCreateDocumentEnter.setDocument(createDocumentAttributesEnter);
        sellsyCreateDocumentEnter.setPaydate(null);
        sellsyCreateDocumentEnter.setShipping(null);
        sellsyCreateDocumentEnter.setNum_format(numFormatEnter);
        sellsyCreateDocumentEnter.setThirdaddress(enter.getThirdaddress());
        sellsyCreateDocumentEnter.setShipaddress(enter.getShipaddress());
        sellsyCreateDocumentEnter.setRow(rosMap);

        SellsyExecutionEnter sellsyExecutionEnter =
            SellsyExecutionEnter.builder().method(SellsyMethodConstant.Document_Create)
                .params(sellsyCreateDocumentEnter).SellsyMethodType(SellsyMethodTypeEnums.ADD.getValue()).build();
        SellsyGeneralResult sellsyGeneralResult = sellsyService.sellsyExecution(sellsyExecutionEnter);

        return sellsyService.jsonCreateResut(sellsyGeneralResult);
    }

    /**
     * 产品行 封装
     * 
     * @param rosMap
     * @param key
     * @param item
     */
    private void buildProductItemRow(Map<String, SellsyRowEnter> rosMap, String key, SellsyRowEnter item) {
        rosMap.put(key, SellsyRowEnter.builder().row_type(SellsyDocumentRosTypeEnums.ITEM.getValue())
            .row_linkedid(item.getRow_linkedid()).row_declid(0).row_name(item.getRow_name())
            .row_notes(item.getRow_notes()).row_unit(item.getRow_unit()).row_unitAmount(item.getRow_unitAmount())
            .row_taxid(item.getRow_taxid()).row_tax2id(null).row_qt(item.getRow_qt()).row_whid(null)
            .row_isOption(SellsyBooleanEnums.N).row_purchaseAmount(item.getRow_purchaseAmount())
            .row_discount(item.getRow_discount() == null || item.getRow_discount() == 0 ? 0 : item.getRow_discount())
            .row_discountUnit(SellsyGlobalDiscountUnitEnums.percent).row_serial(item.getRow_serial()).row_barcode(null)
            .row_accountingCode(item.getRow_accountingCode()).build());
    }

    /**
     * 发票保存入参
     * 
     * @param enter
     * @return
     */
    private CreateDocumentAttributesEnter
        buildCreateDocumentAttributesEnter(SellsyClientServiceCreateDocumentEnter enter) {
        return CreateDocumentAttributesEnter.builder()
                .doctype(SellsyDocmentTypeEnums.invoice)
                .thirdid(enter.getThirdid())
                .thirdident(SellsyConstant.NO_PARAMETER)
                .ident(enter.getIdent())
            .displayedDate(enter.getDisplayedDate())
                .expireDate(null)
                .subject(enter.getSubject())
                .notes(enter.getNotes())
                .tags(null)
                .displayShipAddress(SellsyBooleanEnums.Y)
                .rateCategory(enter.getRateCategory())
                .globalDiscount(enter.getGlobalDiscount())
                .globalDiscountUnit(SellsyGlobalDiscountUnitEnums.percent)
                .hasDoubleVat(SellsyBooleanEnums.N)
                .hasTvaLawText(SellsyBooleanEnums.N)
                .currency(enter.getCurrency() == null || enter.getCurrency() == 0 ? null : enter.getCurrency())
                .doclayout(enter.getDoclayout() == null || enter.getDoclayout() == 0 ? null : enter.getDoclayout())
                .doclang(enter.getDoclang() == null || enter.getDoclang() == 0 ? null : enter.getDoclang())
                .payMediums(null)
                .docspeakerStaffId(Integer.valueOf(165489))
                .useServiceDates(SellsyBooleanEnums.N)
                .serviceDateStart(null)
                .serviceDateStop(null)
                .showContactOnPdf(SellsyBooleanEnums.Y)
                .showParentOnPdf(SellsyBooleanEnums.N)
                .conditionDocShow(SellsyBooleanEnums.N)
                .corpAddressId(enter.getCorpAddressId())
            // .enabledPaymentGateways(null)
            // .directDebitPaymentGateway(SellsyDirectDebitPaymentGatewayEnums.N)
                .build();
    }

    /**
     * 更新单据状态
     * 
     * @param enter
     */
    @Transactional
    @Override
    public void upateDocumentStatus(SellsyUpdateDocumentStatusEnter enter) {
        SellsyExecutionEnter sellsyExecutionEnter =
            SellsyExecutionEnter.builder().method(SellsyMethodConstant.Document_UpdateStep).params(enter)
                .SellsyMethodType(SellsyMethodTypeEnums.UPDATE.getValue()).build();
        SellsyGeneralResult sellsyGeneralResult = sellsyService.sellsyExecution(sellsyExecutionEnter);
    }

    /**
     * 导入订单数据
     *
     * @param file
     * @return
     */
    @Transactional
    @Override
    public SellsyImportExcelResult importSellsyDocument(MultipartFile file) {
        return excelService.readExcelDataBySellsy(file);
    }

    /**
     * 解析到的发票数据保存到数据库
     * 
     * @param successList
     * @return
     */
    @Transactional
    @Override
    public SellsyImportExcelResult saveSellsyInvoid(List<SellsyExcleData> successList) {
        SellsyImportExcelResult result = new SellsyImportExcelResult();

        /*List<SellsyInvoice> saveInvoiceList = new ArrayList<>();
        List<SellsyInvoiceB> sellsyInvoiceBList = new ArrayList<>();

        List<SellsyExcleData> parentList = successList.stream()
            .filter(item -> StringUtils.equals(item.getIsParent(), "1")).collect(Collectors.toList());
        List<SellsyExcleData> childList = successList.stream()
            .filter(item -> StringUtils.equals(item.getIsParent(), "0")).collect(Collectors.toList());
        parentList.forEach(item -> {
            SellsyInvoice sellsyInvoice = buildSellsyInvoice(item);
            sellsyInvoiceBList.add(buildSellsyInvoiceB(item, sellsyInvoice.getId()));
            saveInvoiceList.add(sellsyInvoice);
        });
        childList.forEach(item -> {
            Long parentId = saveInvoiceList.stream()
                .filter(parent -> StringUtils.equals(parent.getInvoiceNum().trim(), item.getInvoice_num().trim()))
                .map(SellsyInvoice::getId).findFirst().orElse(null);
            sellsyInvoiceBList.add(buildSellsyInvoiceB(item, parentId));
        });

        if (CollectionUtils.isNotEmpty(saveInvoiceList)) {
            sellsyInvoiceService.saveBatch(saveInvoiceList);
        }
        if (CollectionUtils.isNotEmpty(sellsyInvoiceBList)) {
            sellsyInvoiceBService.saveBatch(sellsyInvoiceBList);
        }

        log.info("-----------数据已经解析成功，并保存到数据库--------------");
*/

        List<SellsyInvoiceTotal> saveSellsyInvoiceTotal = new ArrayList<>();
        successList.forEach(item -> {
            saveSellsyInvoiceTotal.add(SellsyInvoiceTotal
                    .builder()
                    .id(idAppService.getId("SellsyInvoiceTotal"))
                    .dr(0)
                    .clientName(item.getClient_name())
                    .status(item.getStatus())
                    .invoiceNum(item.getInvoice_num())
                    .invoiceTime(DateUtil.formatDate(item.getInvoice_date()))
                    .ht(item.getHt())
                    .ttc(item.getTtc())
                    .tva(item.getTva())
                    .payStatus(item.getPayStatus())
                    .payType(item.getPayType())
                    .payTime(StringUtils.isBlank(item.getPayTime()) ? null : DateUtil.formatDate(item.getPayTime()))
                    .def1(SellsyBooleanEnums.N.getValue())
                    .def5(item.getUrl())
                    .build());
        });
        sellsyInvoiceTotalService.saveBatch(saveSellsyInvoiceTotal);


        result.setSuccess(Boolean.TRUE);
        result.setSuccessNum(successList.size());
        result.setFailNum(0);
        return result;
    }

    /**
     * 根据数据库数据批量创建发票
     * 
     * @return
     */
    @Override
    public List<SellsyIdResult> createDcumentList() {
        // 查询发票数据
         List<SellsyInvoice> sellsyInvoiceList = sellsyInvoiceService.list(new
         LambdaQueryWrapper<SellsyInvoice>().eq(SellsyInvoice::getDef1, SellsyBooleanEnums.N.getValue()));
         if (CollectionUtils.isEmpty(sellsyInvoiceList)) {
            return new ArrayList<>();
         }
        List<SellsyUnitResult> sellsyUnitResultList = sellsyAccountSettingService.queryUnitList();
        if (CollectionUtils.isEmpty(sellsyUnitResultList)){
            throw new SesWebRosException();
        }
        SellsyUnitResult sellsyUnitResult = sellsyUnitResultList.stream().filter(item -> StringUtils.equals(item.getId(), String.valueOf(sellsyConfig.getUnit()))).findFirst().orElse(null);
        if (sellsyUnitResult==null){
            throw new SesWebRosException();
        }
//        List<SellsyInvoice> sellsyInvoiceList = new ArrayList<>();
//        sellsyInvoiceList.add(sellsyInvoiceService.getById(1006219L));
        List<SellsyInvoiceB> sellsyInvoiceBList =
            sellsyInvoiceBService.list(new LambdaQueryWrapper<SellsyInvoiceB>().in(SellsyInvoiceB::getInvoiceId,
                sellsyInvoiceList.stream().map(SellsyInvoice::getId).collect(Collectors.toList())));
        if (CollectionUtils.isEmpty(sellsyInvoiceBList)) {
            throw new SesWebRosException(ThirdExceptionCodeEnums.SELLSY_INVOICE_B_IS_NOT_EXIST.getCode(),
                ThirdExceptionCodeEnums.SELLSY_INVOICE_B_IS_NOT_EXIST.getMessage());
        }

        //查询税率
        List<SellsyTaxeResult> sellsyTaxeResults = sellsyAccountSettingService.queryTaxeList();
        if (CollectionUtils.isEmpty(sellsyTaxeResults)){
            throw  new SesWebRosException(ThirdExceptionCodeEnums.SELLSY_TAX_NOT_EXIST.getCode(),ThirdExceptionCodeEnums.SELLSY_TAX_NOT_EXIST.getMessage());
        }

        List<SellsyIdResult> result = new ArrayList<>();

        // 封装 发票数据

        for (SellsyInvoice sellsyInvoice : sellsyInvoiceList) {
            // 先从缓存取，发现没有客户的缓存数据，再调用Sellsy
            SellsyClientResult sellsyClientResult = createDocumentCheckClient(null);

            // 查询当前用户的信息（即当前使用谁的token）
            SellsyCorpInfoResult sellsyCorpInfoResult = sellsyAccountSettingService.queryCorpInfos();
            if (sellsyCorpInfoResult == null) {
                throw new SesWebRosException(ThirdExceptionCodeEnums.SELLSY_CORPINFO_IS_NOT_EXIST.getCode(),
                        ThirdExceptionCodeEnums.SELLSY_CORPINFO_IS_NOT_EXIST.getMessage());
            }

            // 查询增值税类型 确认是税前发票 还是税后发票 默认为 税后的发票
            SellsyRateCategoryResult sellsyRateCategoryResult =
                    getJedisDate(JedisConstant.SELLSY_DOCUEMNT_RATECATEGORY, new SellsyRateCategoryResult());
            if (sellsyRateCategoryResult == null) {
                List<SellsyRateCategoryResult> sellsyRateCategoryResults =
                        sellsyAccountSettingService.queryRateCategoryList();
                if (CollectionUtils.isEmpty(sellsyRateCategoryResults)) {
                    throw new SesWebRosException(ThirdExceptionCodeEnums.SELLSY_RATE_CATEGORY_IS_NOT_EXIST.getCode(),
                            ThirdExceptionCodeEnums.SELLSY_RATE_CATEGORY_IS_NOT_EXIST.getMessage());
                }
                sellsyRateCategoryResult = sellsyRateCategoryResults.stream()
                        .filter(rate -> StringUtils.equals(sellsyConfig.getRateCategory(), rate.getName())).findFirst()
                        .orElse(null);
                if (sellsyRateCategoryResult == null) {
                    throw new SesWebRosException(ThirdExceptionCodeEnums.SELLSY_RATE_CATEGORY_IS_NOT_EXIST.getCode(),
                            ThirdExceptionCodeEnums.SELLSY_RATE_CATEGORY_IS_NOT_EXIST.getMessage());
                }
                // 更新缓存数据
                setJedisDate(JedisConstant.SELLSY_DOCUEMNT_RATECATEGORY, sellsyRateCategoryResult);
            }

            List<SellsyInvoiceB> invoiceBList = sellsyInvoiceBList.stream()
                    .filter(invoiceb -> sellsyInvoice.getId().equals(invoiceb.getInvoiceId())).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(invoiceBList)) {
                throw new SesWebRosException(ThirdExceptionCodeEnums.SELLSY_INVOICE_B_IS_NOT_EXIST.getCode(),
                        ThirdExceptionCodeEnums.SELLSY_INVOICE_B_IS_NOT_EXIST.getMessage());
            }

            //需要替换的产品编号
            List<SellsyProduct> sellsyProductList = sellsyProductService.list();

            List<SellsyRowEnter> sellsyRowEnterList = new ArrayList<>();
            for (SellsyInvoiceB invoiceB : invoiceBList) {//确认是否需要替换 产品编号
                if (CollectionUtils.isNotEmpty(sellsyProductList)) {
                    for (SellsyProduct product : sellsyProductList) {
                        if (StringUtils.equals(invoiceB.getProductNum(), product.getProductCode())) {
                            if (StringUtils.isEmpty(product.getReplaceProductCode())){
                                invoiceB.setProductNum(product.getProductCode());
                            } else {
                                invoiceB.setProductNum(product.getReplaceProductCode());
                            }
                            continue;
                        }
                    }
                }

                //校验产品
                SellsyCatalogueResult sellsyCatalogueResult = null;
                //SellsyCatalogueResult sellsyCatalogueResult = createDocumentCheckProduct(invoiceB, sellsyUnitResult,sellsyTaxeResults);

                Integer productTaxId = null;
                if (invoiceB.getTva() != null) {
                    SellsyTaxeResult sellsyTaxeResult = sellsyTaxeResults.stream().filter(tax -> Float.valueOf(tax.getValue()).equals(Float.valueOf(invoiceB.getTva()))).findFirst().orElse(null);
                    if (sellsyTaxeResult == null) {
                        productTaxId = Integer.valueOf(sellsyCatalogueResult.getTaxid());
                    } else {
                        productTaxId = Integer.valueOf(sellsyTaxeResult.getId());
                    }

                }

                SellsyRowEnter sellsyRowEnter = SellsyRowEnter.builder()
                        .row_type(SellsyDocumentRosTypeEnums.ITEM.getValue())
                        .row_name(invoiceB.getProductNote())
                        .row_taxid(productTaxId)
                        .row_tax2id(null)
                        .row_qt(invoiceB.getQty()).row_isOption(SellsyBooleanEnums.N)
                        //使用发票中的产品单价
                        .row_unitAmount(invoiceB.getUnitPrice())
                        .row_discount(Float.valueOf(invoiceB.getRem()))
                        .row_discountUnit(SellsyGlobalDiscountUnitEnums.percent)
                        .row_linkedid(Integer.valueOf(sellsyCatalogueResult.getId()))
                        .row_declid(Integer.valueOf(sellsyCatalogueResult.getDeclid()))
                        .row_notes(sellsyCatalogueResult.getTradename())
                        .row_whid(null)
                        .row_purchaseAmount(
                                String.valueOf(Double.valueOf(sellsyCatalogueResult.getUnitAmount()) * invoiceB.getQty()))
                        .row_serial(null).row_barcode(null).row_title(null).row_comment(null).row_unit(null).build();
                sellsyRowEnterList.add(sellsyRowEnter);
            }

            SellsyClientServiceCreateDocumentEnter sellsyClientServiceCreateDocumentEnter =
                    new SellsyClientServiceCreateDocumentEnter();
            sellsyClientServiceCreateDocumentEnter.setDoctype(SellsyDocmentTypeEnums.invoice);
            sellsyClientServiceCreateDocumentEnter.setThirdid(Integer.valueOf(sellsyClientResult.getId()));
            sellsyClientServiceCreateDocumentEnter.setIdent(sellsyInvoice.getInvoiceNum());
            sellsyClientServiceCreateDocumentEnter.setSubject(sellsyConfig.getSubject());
            sellsyClientServiceCreateDocumentEnter.setNotes(sellsyInvoice.getNotes());
            sellsyClientServiceCreateDocumentEnter.setDisplayShipAddress(SellsyBooleanEnums.Y);
            sellsyClientServiceCreateDocumentEnter.setShowContactOnPdf(SellsyBooleanEnums.Y);
            sellsyClientServiceCreateDocumentEnter
                    .setCorpAddressId(Integer.parseInt(sellsyCorpInfoResult.getMainaddressid()));
            sellsyClientServiceCreateDocumentEnter
                    .setThirdaddress(new SellsyIdEnter(Integer.valueOf(sellsyClientResult.getMainaddressid())));
            sellsyClientServiceCreateDocumentEnter.setSellsellEnterList(sellsyRowEnterList);
            // java --》 php Timestamp 需要精确到秒 java默认精确到毫秒
            sellsyClientServiceCreateDocumentEnter
                    .setDisplayedDate(new Timestamp(sellsyInvoice.getInvoiceTime().getTime() / 1000));
            SellsyIdResult sellsyIdResult = createDocument(sellsyClientServiceCreateDocumentEnter);

            // 更新数据库 数据数据状态
            sellsyInvoice.setDef1(SellsyBooleanEnums.Y.getValue());
            sellsyInvoice.setDef2(String.valueOf(sellsyIdResult.getId()));
            sellsyInvoiceService.updateById(sellsyInvoice);

            result.add(sellsyIdResult);

        }

        // 更新发票状态
        result.forEach(item -> {
            SellsyUpdateDocumentInvoidSatusEnter sellsyUpdateDocumentInvoidSatusEnter =
                    SellsyUpdateDocumentInvoidSatusEnter.builder().doctype(SellsyDocmentTypeEnums.invoice)
                            // todo 暂时都定为取消 等老板确认后 设置所有状态
                            .step(SellsyDocumentInvoiceStatusEnums.cancelled).build();
            SellsyUpdateDocumentStatusEnter sellsyUpdateDocumentStatusEnter = SellsyUpdateDocumentStatusEnter.builder()
                    .docid(item.getId()).document(sellsyUpdateDocumentInvoidSatusEnter).build();
            upateDocumentStatus(sellsyUpdateDocumentStatusEnter);
        });
        return result;
    }

    @Override
    public List<SellsyIdResult> createDcumentTotalList() {
        // 查询发票数据
        List<SellsyInvoiceTotal> sellsyInvoiceList = sellsyInvoiceTotalService.list(new
                LambdaQueryWrapper<SellsyInvoiceTotal>().eq(SellsyInvoiceTotal::getDef1, SellsyBooleanEnums.N.getValue()));
        if (CollectionUtils.isEmpty(sellsyInvoiceList)) {
            return new ArrayList<>();
        }
        List<SellsyUnitResult> sellsyUnitResultList = sellsyAccountSettingService.queryUnitList();
        if (CollectionUtils.isEmpty(sellsyUnitResultList)) {
            throw new SesWebRosException();
        }
        SellsyUnitResult sellsyUnitResult = sellsyUnitResultList.stream().filter(item -> StringUtils.equals(item.getId(), String.valueOf(sellsyConfig.getUnit()))).findFirst().orElse(null);
        if (sellsyUnitResult == null) {
            throw new SesWebRosException();
        }
        //查询税率
        List<SellsyTaxeResult> sellsyTaxeResults = sellsyAccountSettingService.queryTaxeList();
        if (CollectionUtils.isEmpty(sellsyTaxeResults)) {
            throw new SesWebRosException(ThirdExceptionCodeEnums.SELLSY_TAX_NOT_EXIST.getCode(), ThirdExceptionCodeEnums.SELLSY_TAX_NOT_EXIST.getMessage());
        }
        sellsyInvoiceList.forEach(item -> {

            // 查询当前用户的信息（即当前使用谁的token）
            SellsyCorpInfoResult sellsyCorpInfoResult = sellsyAccountSettingService.queryCorpInfos();
            if (sellsyCorpInfoResult == null) {
                throw new SesWebRosException(ThirdExceptionCodeEnums.SELLSY_CORPINFO_IS_NOT_EXIST.getCode(),
                        ThirdExceptionCodeEnums.SELLSY_CORPINFO_IS_NOT_EXIST.getMessage());
            }
            //客户信息
            SellsyClientResult sellsyClientResult = createDocumentCheckClient(item.getClientName());


            //校验产品
            SellsyCatalogueResult sellsyCatalogueResult = createDocumentCheckProduct(item, sellsyUnitResult, sellsyTaxeResults);

            Integer productTaxId = null;
            if (item.getTva() != null) {
                SellsyTaxeResult sellsyTaxeResult = sellsyTaxeResults.stream().filter(tax -> Float.valueOf(tax.getValue()).equals(Float.valueOf(item.getTva()))).findFirst().orElse(null);
                if (sellsyTaxeResult == null) {
                    productTaxId = Integer.valueOf(sellsyCatalogueResult.getTaxid());
                } else {
                    productTaxId = Integer.valueOf(sellsyTaxeResult.getId());
                }
                if (productTaxId == null) {
                    productTaxId = Integer.valueOf(sellsyConfig.getTaxId());
                }

            }


            SellsyRowEnter sellsyRowEnter = SellsyRowEnter.builder()
                    .row_type(SellsyDocumentRosTypeEnums.ITEM.getValue())
                    .row_name(item.getProductName())
                    .row_taxid(productTaxId)
                    .row_tax2id(null)
                    .row_qt(1).row_isOption(SellsyBooleanEnums.N)
                    //使用发票中的产品单价
                    .row_unitAmount(item.getHt())
                    .row_discount(Float.valueOf("0"))
                    .row_discountUnit(SellsyGlobalDiscountUnitEnums.percent)
                    .row_linkedid(Integer.valueOf(sellsyCatalogueResult.getId()))
                    .row_declid(Integer.valueOf(sellsyCatalogueResult.getDeclid()))
                    .row_notes(sellsyCatalogueResult.getTradename())
                    .row_whid(null)
                    .row_purchaseAmount(
                            String.valueOf(Double.valueOf(item.getHt())))
                    .row_serial(null).row_barcode(null).row_title(null).row_comment(null).row_unit(null).build();

            List<SellsyRowEnter> rowList = new ArrayList<>();
            rowList.add(sellsyRowEnter);

            SellsyClientServiceCreateDocumentEnter sellsyClientServiceCreateDocumentEnter =
                    new SellsyClientServiceCreateDocumentEnter();
            sellsyClientServiceCreateDocumentEnter.setDoctype(SellsyDocmentTypeEnums.invoice);
            sellsyClientServiceCreateDocumentEnter.setThirdid(Integer.valueOf(sellsyClientResult.getId()));
            sellsyClientServiceCreateDocumentEnter.setIdent(item.getInvoiceNum());
            sellsyClientServiceCreateDocumentEnter.setSubject(sellsyConfig.getSubject());
            sellsyClientServiceCreateDocumentEnter.setNotes("Statut de paiement:" + item.getPayStatus() + ".  " + "Payment Types:" + item.getPayType() + ". " + "Payment time:" + (item.getPayTime() == null ? " " :
                    DateUtil.getTimeStr(item.getPayTime(), DateUtil.DEFAULT_DATE_FORMAT2)));
            sellsyClientServiceCreateDocumentEnter.setDisplayShipAddress(SellsyBooleanEnums.Y);
            sellsyClientServiceCreateDocumentEnter.setShowContactOnPdf(SellsyBooleanEnums.Y);
            sellsyClientServiceCreateDocumentEnter
                    .setCorpAddressId(Integer.parseInt(sellsyCorpInfoResult.getMainaddressid()));
            sellsyClientServiceCreateDocumentEnter
                    .setThirdaddress(new SellsyIdEnter(Integer.valueOf(sellsyClientResult.getMainaddressid())));
            sellsyClientServiceCreateDocumentEnter.setSellsellEnterList(rowList);
            // java --》 php Timestamp 需要精确到秒 java默认精确到毫秒
            sellsyClientServiceCreateDocumentEnter
                    .setDisplayedDate(new Timestamp(item.getInvoiceTime().getTime() / 1000));
            SellsyIdResult sellsyIdResult = createDocument(sellsyClientServiceCreateDocumentEnter);


            // 更新数据库 数据数据状态
            item.setDef1(SellsyBooleanEnums.Y.getValue());
            item.setDef2(String.valueOf(sellsyIdResult.getId()));
            sellsyInvoiceTotalService.updateById(item);
        });
        return null;
    }

    private SellsyClientResult createDocumentCheckClient(String name) {
        SellsyClientResult sellsyClientResult =
                getJedisDate(JedisConstant.SESSY_DOCUMENT_CLIENT + name, new SellsyClientResult());

        //是否创建账户
        Boolean createClient = Boolean.FALSE;
        if (sellsyClientResult == null) {
            // 客户校验
            SellsyClientListEnter sellsyClientListEnter = new SellsyClientListEnter();
            SellsyClientListSearchEnter sellsyClientSerach = new SellsyClientListSearchEnter();
            sellsyClientSerach.setName(name);
            sellsyClientListEnter.setSearch(sellsyClientSerach);
            List<SellsyClientResult> sellsyClientResults =
                    sellsyClientService.queryClientList(sellsyClientListEnter);

            if (CollectionUtils.isEmpty(sellsyClientResults)) {
                //客户不存在
                createClient=Boolean.TRUE;
                log.info("----------客户不存在---------");
            }
            sellsyClientResult = sellsyClientResults.stream()
                    .filter(client -> StringUtils.equals(client.getName(), name)).findFirst()
                    .orElse(null);
            if (sellsyClientResult == null) {
                //客户存在
                createClient=Boolean.TRUE;
                log.info("----------客户不存在---------");
            }
        }

        if (createClient){
            SellsyCreateClientThirdEnter third = new SellsyCreateClientThirdEnter();
            third.setName(name);
            third.setType(SellsyClientTypeEnums.corporation.getValue());

            SellsyCreateClientAddressEnter address = SellsyCreateClientAddressEnter
                    .builder()
//                    .name(sellsyInvoice.getClientAddress())
//                    .part1(sellsyInvoice.getClientAddress())
                    .name(null)
                    .part1(null)
                    .build();
            SellsyCreateClientEnter sellsyCreateClientEnter = SellsyCreateClientEnter.builder()
                    .third(third)
                    .address(address)
                    .contact(null)
                    .build();
            SellsyIdResult client = sellsyClientService.createClient(sellsyCreateClientEnter);

            //保存客户
            SellsyCustomer sellsyCustomer = SellsyCustomer
                    .builder()
                    .id(idAppService.getId("SELLSY_CUSTOMER"))
                    .dr(0)
                    .name(name)
                    .address(null)
                    .status(String.valueOf(Boolean.TRUE))
                    .remark(null)
                    .createdBy(0L)
                    .createdTime(new Date())
                    .updatedBy(0L)
                    .updatedTime(new Date())
                    .def1(String.valueOf(client.getId()))
                    .build();
            sellsyCustomerService.save(sellsyCustomer);

            //客户查询
            SellsyQueryClientOneEnter queryClientOneEnter = SellsyQueryClientOneEnter
                    .builder()
                    .clientid(client.getId())
                    .build();

            sellsyClientResult=sellsyClientService.queryClientOne(queryClientOneEnter);
        }
        // 更新缓存数据
        setJedisDate(JedisConstant.SESSY_DOCUMENT_CLIENT + name, sellsyClientResult);
        return sellsyClientResult;
    }

    /**
     *
     * @param sellsyUnitResult
     * @return
     */
    private SellsyCatalogueResult createDocumentCheckProduct(SellsyInvoiceTotal sellsyInvoiceTotal, SellsyUnitResult sellsyUnitResult, List<SellsyTaxeResult> sellsyTaxeResults) {
        //    private SellsyCatalogueResult createDocumentCheckProduct(SellsyInvoiceB invoiceB, SellsyUnitResult sellsyUnitResult,List<SellsyTaxeResult> sellsyTaxeResults){
        // 产品校验
        SellsyCatalogueResult sellsyCatalogueResult = getJedisDate(
                JedisConstant.SELSY_DOCUMENT_PRODUCT + "RED1000001", new SellsyCatalogueResult());

        Boolean createProduct = Boolean.FALSE;
        if (sellsyCatalogueResult == null) {

            SellsyCatalogueListEnter sellsyCatalogueListEnter = new SellsyCatalogueListEnter();
            SellsyCatalogueListSearchEnter sellsyCatalogueListSearchEnter =
                    new SellsyCatalogueListSearchEnter();
            //sellsyCatalogueListSearchEnter.setName(invoiceB.getProductNum());
            sellsyCatalogueListSearchEnter.setName("RED1000001");
            sellsyCatalogueListEnter.setType(SellsyCatalogueTypeEnums.item);
            sellsyCatalogueListEnter.setSearch(sellsyCatalogueListSearchEnter);
            List<SellsyCatalogueResult> sellsyCatalogueResults =
                    sellsyCatalogueService.queryCatalogueList(sellsyCatalogueListEnter);
            if (CollectionUtils.isEmpty(sellsyCatalogueResults)) {
                log.info("---------产品不存在------{}-------------", "RED1000001");
                createProduct = Boolean.TRUE;
                //创建产品
            }
            //过滤
            for (SellsyCatalogueResult catalogue : sellsyCatalogueResults) {
                if (StringUtils.equals(catalogue.getName(), "RED1000001")) {
                    sellsyCatalogueResult = catalogue;
                    break;
                }
            }
            if (sellsyCatalogueResult == null) {
                log.info("---------产品不存在------{}-------------", "RED1000001");
                //创建产品
                createProduct=Boolean.TRUE;

            }
        }

        SellsyTaxeResult sellsyTaxeResult = sellsyTaxeResults.stream().filter(tax -> Float.valueOf(tax.getValue()).equals(Float.valueOf(sellsyInvoiceTotal.getTva()))).findFirst().orElse(null);
        if (sellsyTaxeResult==null){
            log.info("----------税率为空------------");
            throw new SesWebRosException(ThirdExceptionCodeEnums.SELLSY_TAX_NOT_EXIST.getCode(),ThirdExceptionCodeEnums.SELLSY_TAX_NOT_EXIST.getMessage());
        }

        if (createProduct){
            //数据封装
            SellsyCreateCatalogueEnter sellsyCreateCatalogueEnter = new SellsyCreateCatalogueEnter();
            SellsyCreateCatalogueTypeEnter catalogueTypeEnter = new SellsyCreateCatalogueTypeEnter();
            /*catalogueTypeEnter.setName("RED1000001");
            catalogueTypeEnter.setTradename(invoiceB.getProductName());
            catalogueTypeEnter.setTradenametonote(SellsyBooleanEnums.Y);
            catalogueTypeEnter.setNotes("自定义添加的产品可以删除");
            catalogueTypeEnter.setTags(null);
            catalogueTypeEnter.setUnitAmount(Float.valueOf(invoiceB.getUnitPrice()));
            catalogueTypeEnter.setUnit(sellsyUnitResult.getValue());
            catalogueTypeEnter.setQty(1);
            catalogueTypeEnter.setUnitAmountIsTaxesFree(SellsyBooleanEnums.Y);
            catalogueTypeEnter.setTaxid(Integer.valueOf(sellsyTaxeResult.getId()));
            catalogueTypeEnter.setTaxrate(Float.valueOf(sellsyTaxeResult.getValue()));
            sellsyCreateCatalogueEnter.setType(SellsyCatalogueTypeEnums.item);
            sellsyCreateCatalogueEnter.setItem(catalogueTypeEnter);*/
            catalogueTypeEnter.setName("RED1000001");
            catalogueTypeEnter.setTradename("Electric motorcycles and accessories");
            catalogueTypeEnter.setTradenametonote(SellsyBooleanEnums.Y);
            catalogueTypeEnter.setNotes("自定义添加可删除");
            catalogueTypeEnter.setTags(null);
            catalogueTypeEnter.setUnitAmount(0);
            //catalogueTypeEnter.setUnit(sellsyUnitResult.);
            catalogueTypeEnter.setQty(1);
            catalogueTypeEnter.setUnitAmountIsTaxesFree(SellsyBooleanEnums.Y);
            catalogueTypeEnter.setTaxid(Integer.valueOf(sellsyTaxeResult.getId()));
            catalogueTypeEnter.setTaxrate(Float.valueOf(sellsyTaxeResult.getValue()));
            sellsyCreateCatalogueEnter.setType(SellsyCatalogueTypeEnums.item);
            sellsyCreateCatalogueEnter.setItem(catalogueTypeEnter);

            //产品创建
            SellsyIdResult catalogue = sellsyCatalogueService.createCatalogue(sellsyCreateCatalogueEnter);

            //sellsy 产品 保存到本地数据库
//            sellsyProductService.save(SellsyProduct.builder()
//                    .id(idAppService.getId("sellsy_product"))
//                    .dr(0)
//                    .status(SellsyProductEnums.MYSELY_CREATE.getValue())
//                    .productCode(invoiceB.getProductNum())
//                    .productName(invoiceB.getProductName())
//                    .productPrice(invoiceB.getUnitPrice())
//                    .type(SellsyCatalogueTypeEnums.item.getValue())
//                    .createdBy(0L)
//                    .createdTime(new Date())
//                    .updatedBy(0L)
//                    .updatedTime(new Date())
//                    .def1(String.valueOf(catalogue.getId()))
//                    .build());
            sellsyProductService.save(SellsyProduct
                    .builder()
                    .id(idAppService.getId("SellsyProduct"))
                    .dr(0)
                    .productName(catalogueTypeEnter.getTradename())
                    .productCode(catalogueTypeEnter.getName())
                    .status("4")
                    .productPrice("0")
                    .remark(catalogueTypeEnter.getNotes())
                    .def1(String.valueOf(catalogue.getId()))
                    .createdBy(0L)
                    .createdTime(new Date())
                    .updatedBy(0L)
                    .updatedTime(new Date())
                    .build());

//            SellsyQueryCatalogueOneEnter sellsyQueryCatalogueOneEnter = SellsyQueryCatalogueOneEnter.builder()
//                    .id(catalogue.getId())
//                    .type(SellsyCatalogueTypeEnums.item)
//                    .build();
//            sellsyCatalogueResult = sellsyCatalogueService.queryCatalogueOne(sellsyQueryCatalogueOneEnter);
        }
         // 更新缓存数据
        setJedisDate(JedisConstant.SELSY_DOCUMENT_PRODUCT + "RED1000001",
                sellsyCatalogueResult);
        return sellsyCatalogueResult;
    }


    /**
     * jedis 放入数据
     * 
     * @param key
     * @param t
     * @param <T>
     */
    private <T> void setJedisDate(String key, T t) {
        // 删除原始key 对应的数据
        Map<String, String> checkKeyResult = jedisCluster.hgetAll(key);
        if (checkKeyResult != null) {
            jedisCluster.del(key);
        }
        try {
            // 移除掉 value 值为null 的key 值
            Map<String, String> map = BeanUtils.describe(t);
            Set<String> removeKey = new HashSet<>();
            for (String item : map.keySet()) {
                if (StringUtils.isEmpty(map.get(item))) {
                    removeKey.add(item);
                }
            }
            removeKey.forEach(item -> {
                map.remove(item);
            });

            jedisCluster.hmset(key, map);
            jedisCluster.expire(key, Long.valueOf(RedisExpireEnum.DAY_1.getSeconds()).intValue());
        } catch (IllegalAccessException e) {
            log.error("sellsy Error in putting data:" + t, e);
        } catch (NoSuchMethodException e) {
            log.error("sellsy Error in putting data:" + t, e);
        } catch (InvocationTargetException e) {
            log.error("sellsy Error in putting data:", e);
        }
    }

    /**
     * 获取 jedis 数据
     * 
     * @param key
     * @param t
     * @param <T>
     * @return
     */
    private <T> T getJedisDate(String key, T t) {
        if (jedisCluster.exists(key)) {
            return null;
        }

        Map<String, String> map = jedisCluster.hgetAll(key);
        if (org.springframework.util.CollectionUtils.isEmpty(map)) {
            return null;
        }
        try {
            org.apache.commons.beanutils.BeanUtils.populate(t, map);
        } catch (IllegalAccessException e) {
            log.error("Get Sellsy redis IllegalAccessException sessionMap:" + map, e);
        } catch (InvocationTargetException e) {
            log.error("Get Sellsy redis IllegalAccessException sessionMap:" + map, e);
        }
        return t;
    }

//    private SellsyInvoice buildSellsyInvoice(SellsyExcleData item) {
//        return SellsyInvoice.builder()
//                .id(idAppService.getId("sellsy_invoice"))
//                .dr(0)
//                .clientName(item.getClient().trim())
//                .status(item.getEtat())
//                .invoiceNum(item.getInvoice_num())
//                .invoiceTime(DateUtil.formatDate(item.getInvoice_date()))
//                .notes(item.getInvoice_notes())
//                .ht(item.getHt())
//                .ttc(item.getTtc())
//                .receivePayment(item.getReceivePayment().trim())
//                .uncollected(item.getRemainingPayment().trim())
//                .clientAddress(item.getClientAddress())
//                .payerNote(item.getBuyerNotes())
//                .pdfUrl(item.getPdfUrl())
//                .createdBy(0L)
//                .createdTime(new Date())
//                .updatedTime(new Date())
//                .updatedBy(0L)
//                .def1(SellsyBooleanEnums.N.getValue())
//                .build();
//    }

//    private SellsyInvoiceB buildSellsyInvoiceB(SellsyExcleData item, Long parentId) {
//        return SellsyInvoiceB.builder()
//                .id(idAppService.getId("sellsy_invoice_b"))
//                .dr(0)
//                .invoiceId(parentId)
//                .productNum(item.getProductNum())
//                .productName(item.getProductName())
//                .productNote(item.getProductNodes())
//                // 税率
//                .tva(new BigDecimal(item.getTva()).multiply(new BigDecimal(100)).intValue())
//                .qty(Integer.valueOf(item.getProductQty()))
//                .unit(null)
//                // 折扣
//                .rem(new BigDecimal(item.getDiscount()).multiply(new BigDecimal(100)).intValue())
//                .unitPrice(StringUtils.trim(item.getUnitPrice()))
//                .createdBy(0L)
//                .createdTime(new Date())
//                .updatedBy(0L)
//            .updatedTime(new Date()).build();
//    }
}
