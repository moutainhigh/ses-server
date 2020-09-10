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
import com.redescooter.ses.web.ros.dm.SellsyInvoice;
import com.redescooter.ses.web.ros.dm.SellsyInvoiceB;
import com.redescooter.ses.web.ros.dm.SellsyProduct;
import com.redescooter.ses.web.ros.enums.sellsy.*;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.exception.ThirdExceptionCodeEnums;
import com.redescooter.ses.web.ros.service.base.SellsyInvoiceBService;
import com.redescooter.ses.web.ros.service.base.SellsyInvoiceService;
import com.redescooter.ses.web.ros.service.base.SellsyProductService;
import com.redescooter.ses.web.ros.service.excel.ExcelService;
import com.redescooter.ses.web.ros.service.sellsy.*;
import com.redescooter.ses.web.ros.vo.sellsy.enter.SellsyClientServiceCreateDocumentEnter;
import com.redescooter.ses.web.ros.vo.sellsy.enter.SellsyExecutionEnter;
import com.redescooter.ses.web.ros.vo.sellsy.enter.SellsyIdEnter;
import com.redescooter.ses.web.ros.vo.sellsy.enter.SellsyImportExcelResult;
import com.redescooter.ses.web.ros.vo.sellsy.enter.catalogue.SellsyCatalogueListEnter;
import com.redescooter.ses.web.ros.vo.sellsy.enter.catalogue.SellsyCatalogueListSearchEnter;
import com.redescooter.ses.web.ros.vo.sellsy.enter.client.SellsyClientListEnter;
import com.redescooter.ses.web.ros.vo.sellsy.enter.client.SellsyClientListSearchEnter;
import com.redescooter.ses.web.ros.vo.sellsy.enter.client.SellsyQueryClientOneEnter;
import com.redescooter.ses.web.ros.vo.sellsy.enter.document.*;
import com.redescooter.ses.web.ros.vo.sellsy.result.SellsyExcleData;
import com.redescooter.ses.web.ros.vo.sellsy.result.SellsyGeneralResult;
import com.redescooter.ses.web.ros.vo.sellsy.result.SellsyIdResut;
import com.redescooter.ses.web.ros.vo.sellsy.result.account.SellsyCorpInfoResult;
import com.redescooter.ses.web.ros.vo.sellsy.result.account.SellsyCurrencyResult;
import com.redescooter.ses.web.ros.vo.sellsy.result.account.SellsyRateCategoryResult;
import com.redescooter.ses.web.ros.vo.sellsy.result.account.SellsyTaxeResult;
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
import java.math.BigDecimal;
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
    public SellsyIdResut createDocument(SellsyClientServiceCreateDocumentEnter enter) {

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
                .docspeakerStaffId(null)
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

        List<String> invoiceNumList = successList.stream().filter(item -> StringUtils.equals(item.getIsParent(), "1"))
            .map(SellsyExcleData::getInvoice_num).collect(Collectors.toList());

        // 校验发票单号是否已经存在
        List<SellsyInvoice> sellsyInvoiceList = sellsyInvoiceService
            .list(new LambdaQueryWrapper<SellsyInvoice>().in(SellsyInvoice::getInvoiceNum, invoiceNumList));
        if (CollectionUtils.isNotEmpty(sellsyInvoiceList)) {
            throw new SesWebRosException(ThirdExceptionCodeEnums.SELLSY_DOCUMENT_INDENT_IS_ALREADY_EXIST.getCode(),
                ThirdExceptionCodeEnums.SELLSY_DOCUMENT_INDENT_IS_ALREADY_EXIST.getMessage());
        }

        List<SellsyInvoice> saveInvoiceList = new ArrayList<>();
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
    public List<SellsyIdResut> createDcumentList() {
        // 查询发票数据
         List<SellsyInvoice> sellsyInvoiceList = sellsyInvoiceService.list(new
         LambdaQueryWrapper<SellsyInvoice>().eq(SellsyInvoice::getDef1, SellsyBooleanEnums.N.getValue()));
         if (CollectionUtils.isEmpty(sellsyInvoiceList)) {
            return new ArrayList<>();
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

        List<SellsyIdResut> result = new ArrayList<>();

        // 封装 发票数据
        sellsyInvoiceList.forEach(item -> {
            // 先从缓存取，发现没有客户的缓存数据，再调用Sellsy
            SellsyClientResult sellsyClientResult =
                getJedisDate(JedisConstant.SESSY_DOCUMENT_CLIENT + item.getClientName(), new SellsyClientResult());

            if (sellsyClientResult == null) {
                // 客户校验
                SellsyClientListEnter sellsyClientListEnter = new SellsyClientListEnter();
                SellsyClientListSearchEnter sellsyClientSerach = new SellsyClientListSearchEnter();
                sellsyClientSerach.setName(item.getClientName());
                sellsyClientListEnter.setSearch(sellsyClientSerach);
                List<SellsyClientResult> sellsyClientResults =
                    sellsyClientService.queryClientList(sellsyClientListEnter);
                if (CollectionUtils.isEmpty(sellsyClientResults)) {
                    throw new SesWebRosException(ThirdExceptionCodeEnums.SELLSY_CLIENT_IS_NOT_EXIST.getCode(),
                        ThirdExceptionCodeEnums.SELLSY_CLIENT_IS_NOT_EXIST.getMessage());
                }
                sellsyClientResult = sellsyClientResults.stream()
                    .filter(client -> StringUtils.equals(client.getName(), item.getClientName())).findFirst()
                    .orElse(null);
                if (sellsyClientResult == null) {
                    throw new SesWebRosException(ThirdExceptionCodeEnums.SELLSY_CLIENT_IS_NOT_EXIST.getCode(),
                        ThirdExceptionCodeEnums.SELLSY_CLIENT_IS_NOT_EXIST.getMessage());
                }
                // 更新缓存数据
                setJedisDate(JedisConstant.SESSY_DOCUMENT_CLIENT + item.getClientName(), sellsyClientResult);
            }

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
                .filter(invoiceb -> item.getId().equals(invoiceb.getInvoiceId())).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(invoiceBList)) {
                throw new SesWebRosException(ThirdExceptionCodeEnums.SELLSY_INVOICE_B_IS_NOT_EXIST.getCode(),
                    ThirdExceptionCodeEnums.SELLSY_INVOICE_B_IS_NOT_EXIST.getMessage());
            }

            //需要替换的产品编号
            List<SellsyProduct> sellsyProductList = sellsyProductService.list();

            List<SellsyRowEnter> sellsyRowEnterList = new ArrayList<>();
            invoiceBList.forEach(invoiceB -> {

                //确认是否需要替换 产品编号
                if (CollectionUtils.isNotEmpty(sellsyProductList)){
                    for (SellsyProduct product : sellsyProductList) {
                        if (StringUtils.equals(invoiceB.getProductNum(), product.getProductCode())) {
                            invoiceB.setProductNote(invoiceB.getProductNote() + "   " + invoiceB.getProductNote() + "Changer en" + product.getReplaceProductCode() + "Le numéro de produit est " +
                                    "différent, mais se réfère au même produit");
                            invoiceB.setProductNum(product.getReplaceProductCode());
                            break;
                        }
                    }
                }

                // 产品校验
                SellsyCatalogueResult sellsyCatalogueResult = getJedisDate(
                    JedisConstant.SELSY_DOCUMENT_PRODUCT + invoiceB.getProductNum(), new SellsyCatalogueResult());
                if (sellsyCatalogueResult == null) {

                    SellsyCatalogueListEnter sellsyCatalogueListEnter = new SellsyCatalogueListEnter();
                    SellsyCatalogueListSearchEnter sellsyCatalogueListSearchEnter =
                        new SellsyCatalogueListSearchEnter();
                    sellsyCatalogueListSearchEnter.setName(invoiceB.getProductNum());
                    sellsyCatalogueListEnter.setType(SellsyCatalogueTypeEnums.item);
                    sellsyCatalogueListEnter.setSearch(sellsyCatalogueListSearchEnter);
                    List<SellsyCatalogueResult> sellsyCatalogueResults =
                        sellsyCatalogueService.queryCatalogueList(sellsyCatalogueListEnter);
                    if (CollectionUtils.isEmpty(sellsyCatalogueResults)) {
                        throw new SesWebRosException(ThirdExceptionCodeEnums.SELLSY_PRODUCT_IS_NOT_EXIST.getCode(),
                            ThirdExceptionCodeEnums.SELLSY_PRODUCT_IS_NOT_EXIST.getMessage());
                    }
                    //过滤
                    for (SellsyCatalogueResult catalogue : sellsyCatalogueResults) {
                        if (StringUtils.equals(catalogue.getName(), invoiceB.getProductNum().trim())) {
                            sellsyCatalogueResult = catalogue;
                            break;
                        }
                    }
                    if (sellsyCatalogueResult == null) {
                        throw new SesWebRosException(ThirdExceptionCodeEnums.SELLSY_PRODUCT_IS_NOT_EXIST.getCode(),
                            ThirdExceptionCodeEnums.SELLSY_PRODUCT_IS_NOT_EXIST.getMessage());
                    }
                    // 更新缓存数据
                    setJedisDate(JedisConstant.SELSY_DOCUMENT_PRODUCT + invoiceB.getProductNum(),
                        sellsyCatalogueResult);
                }

                Integer productTaxId=null;
                if (invoiceB.getTva()!=null){
                    SellsyTaxeResult sellsyTaxeResult = sellsyTaxeResults.stream().filter(tax -> StringUtils.equals(tax.getValue(), String.valueOf(invoiceB.getTva()))).findFirst().orElse(null);
                    if (sellsyTaxeResult==null){
                        productTaxId=Integer.valueOf(sellsyCatalogueResult.getTaxid());
                    }else {
                        productTaxId=Integer.valueOf(sellsyTaxeResult.getId());
                    }

                }

                SellsyRowEnter sellsyRowEnter = SellsyRowEnter.builder()
                    .row_type(SellsyDocumentRosTypeEnums.ITEM.getValue()).row_name(sellsyCatalogueResult.getTradename())
                    .row_taxid(productTaxId)
                        .row_tax2id(null)
                    .row_qt(invoiceB.getQty()).row_isOption(SellsyBooleanEnums.N)
                    .row_unitAmount(sellsyCatalogueResult.getUnitAmount())
                    .row_discount(Float.valueOf(invoiceB.getRem()))
                    .row_discountUnit(SellsyGlobalDiscountUnitEnums.percent)
                    .row_linkedid(Integer.valueOf(sellsyCatalogueResult.getId()))
                    .row_declid(Integer.valueOf(sellsyCatalogueResult.getDeclid()))
                        .row_notes(invoiceB.getProductNote())
                    .row_whid(null)
                    .row_purchaseAmount(
                        String.valueOf(Double.valueOf(sellsyCatalogueResult.getUnitAmount()) * invoiceB.getQty()))
                    .row_serial(null).row_barcode(null).row_title(null).row_comment(null).row_unit(null).build();
                sellsyRowEnterList.add(sellsyRowEnter);
            });

            SellsyClientServiceCreateDocumentEnter sellsyClientServiceCreateDocumentEnter =
                new SellsyClientServiceCreateDocumentEnter();
            sellsyClientServiceCreateDocumentEnter.setDoctype(SellsyDocmentTypeEnums.invoice);
            sellsyClientServiceCreateDocumentEnter.setThirdid(Integer.valueOf(sellsyClientResult.getId()));
            sellsyClientServiceCreateDocumentEnter.setIdent(item.getInvoiceNum());
            sellsyClientServiceCreateDocumentEnter.setSubject(sellsyConfig.getSubject());
            sellsyClientServiceCreateDocumentEnter.setNotes(item.getNotes());
            sellsyClientServiceCreateDocumentEnter.setDisplayShipAddress(SellsyBooleanEnums.Y);
            sellsyClientServiceCreateDocumentEnter.setShowContactOnPdf(SellsyBooleanEnums.Y);
            sellsyClientServiceCreateDocumentEnter
                .setCorpAddressId(Integer.parseInt(sellsyCorpInfoResult.getMainaddressid()));
            sellsyClientServiceCreateDocumentEnter
                .setThirdaddress(new SellsyIdEnter(Integer.valueOf(sellsyClientResult.getMainaddressid())));
            sellsyClientServiceCreateDocumentEnter.setSellsellEnterList(sellsyRowEnterList);
            // java --》 php Timestamp 需要精确到秒 java默认精确到毫秒
            sellsyClientServiceCreateDocumentEnter
                .setDisplayedDate(new Timestamp(item.getInvoiceTime().getTime() / 1000));
            SellsyIdResut sellsyIdResut = createDocument(sellsyClientServiceCreateDocumentEnter);

            // 更新数据库 数据数据状态
            item.setDef1(SellsyBooleanEnums.Y.getValue());
            item.setDef2(String.valueOf(sellsyIdResut.getId()));
            sellsyInvoiceService.updateById(item);

            result.add(sellsyIdResut);

        });

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

    private SellsyInvoice buildSellsyInvoice(SellsyExcleData item) {
        return SellsyInvoice.builder()
                .id(idAppService.getId("sellsy_invoice"))
                .dr(0)
                .clientName(item.getClient().trim())
                .status(item.getEtat())
                .invoiceNum(item.getInvoice_num())
                .invoiceTime(DateUtil.formatDate(item.getInvoice_date()))
            .notes(item.getInvoice_notes().replace("/", "-"))
                .ht(item.getHt())
                .ttc(item.getTtc())
                .receivePayment(item.getReceivePayment().trim())
                .uncollected(item.getRemainingPayment().trim())
                .clientAddress(item.getClientAddress())
                .payerNote(item.getBuyerNotes())
                .pdfUrl(item.getPdfUrl())
                .createdBy(0L)
                .createdTime(new Date())
                .updatedTime(new Date())
                .updatedBy(0L)
            .def1(SellsyBooleanEnums.N.getValue())
                .build();
    }

    private SellsyInvoiceB buildSellsyInvoiceB(SellsyExcleData item, Long parentId) {
        return SellsyInvoiceB.builder()
                .id(idAppService.getId("sellsy_invoice_b"))
                .dr(0)
                .invoiceId(parentId)
                .productNum(item.getProductNum())
                .productName(item.getProductName())
            .productNote(item.getProductNodes())
            // 税率
                .tva(new BigDecimal(item.getTva()).multiply(new BigDecimal(100)).intValue())
                .qty(Integer.valueOf(item.getProductQty()))
                .unit(null)
            // 折扣
                .rem(new BigDecimal(item.getDiscount()).multiply(new BigDecimal(100)).intValue())
                .unitPrice(StringUtils.trim(item.getUnitPrice()))
                .createdBy(0L)
                .createdTime(new Date())
                .updatedBy(0L)
            .updatedTime(new Date()).build();
    }
}
