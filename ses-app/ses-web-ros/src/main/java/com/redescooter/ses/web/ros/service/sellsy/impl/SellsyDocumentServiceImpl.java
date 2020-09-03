package com.redescooter.ses.web.ros.service.sellsy.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.DateUtil;
import com.redescooter.ses.web.ros.constant.SellsyConstant;
import com.redescooter.ses.web.ros.constant.SellsyMethodConstant;
import com.redescooter.ses.web.ros.dm.SellsyInvoice;
import com.redescooter.ses.web.ros.dm.SellsyInvoiceB;
import com.redescooter.ses.web.ros.enums.sellsy.*;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.exception.ThirdExceptionCodeEnums;
import com.redescooter.ses.web.ros.service.base.SellsyInvoiceBService;
import com.redescooter.ses.web.ros.service.base.SellsyInvoiceService;
import com.redescooter.ses.web.ros.service.excel.ExcelService;
import com.redescooter.ses.web.ros.service.sellsy.*;
import com.redescooter.ses.web.ros.vo.sellsy.enter.SellsyClientServiceCreateDocumentEnter;
import com.redescooter.ses.web.ros.vo.sellsy.enter.SellsyExecutionEnter;
import com.redescooter.ses.web.ros.vo.sellsy.enter.SellsyIdEnter;
import com.redescooter.ses.web.ros.vo.sellsy.enter.SellsyImportExcelResult;
import com.redescooter.ses.web.ros.vo.sellsy.enter.catalogue.SellsyCatalogueListEnter;
import com.redescooter.ses.web.ros.vo.sellsy.enter.client.SellsyQueryClientOneEnter;
import com.redescooter.ses.web.ros.vo.sellsy.enter.document.*;
import com.redescooter.ses.web.ros.vo.sellsy.result.SellsyExcleData;
import com.redescooter.ses.web.ros.vo.sellsy.result.SellsyGeneralResult;
import com.redescooter.ses.web.ros.vo.sellsy.result.SellsyIdResut;
import com.redescooter.ses.web.ros.vo.sellsy.result.account.*;
import com.redescooter.ses.web.ros.vo.sellsy.result.catalogue.SellsyCatalogueResult;
import com.redescooter.ses.web.ros.vo.sellsy.result.client.SellsyClientResult;
import com.redescooter.ses.web.ros.vo.sellsy.result.document.SellsyDocumentListResult;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
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
            List<SellsyDocumentListResult> sellsyDocumentListResultPageResult = queryDocumentList(sellsyDocumentListEnter);
            if (CollectionUtils.isEmpty(sellsyDocumentListResultPageResult)) {
                throw new SesWebRosException(ThirdExceptionCodeEnums.SELLSY_DOCUMENT_IS_ALREADY_EXIST.getCode(),
                        ThirdExceptionCodeEnums.SELLSY_DOCUMENT_IS_ALREADY_EXIST.getMessage());
            }
            if (sellsyDocumentListResultPageResult.stream().filter(item -> StringUtils.equals(item.getIdent(), enter.getIdent())).findFirst().orElse(null) == null) {
                throw new SesWebRosException(ThirdExceptionCodeEnums.SELLSY_DOCUMENT_IS_ALREADY_EXIST.getCode(),
                        ThirdExceptionCodeEnums.SELLSY_DOCUMENT_IS_ALREADY_EXIST.getMessage());
            }
        }

        // 查询税率 是税前发票 还是税后发票
        if (enter.getRateCategory() != null || enter.getRateCategory() != 0) {
            SellsyRateCategoryResult sellsyRateCategoryResult =
                    sellsyAccountSettingService.queryateCategoryOne(new SellsyIdEnter(enter.getRateCategory()));
            if (sellsyRateCategoryResult == null) {
                throw new SesWebRosException(ThirdExceptionCodeEnums.SELLSY_DOCUMNT_RATECATEGORY_IS_EMPTY.getCode(),
                        ThirdExceptionCodeEnums.SELLSY_DOCUMNT_RATECATEGORY_IS_EMPTY.getMessage());
            }
        }

        if (enter.getCurrency() != null || enter.getCurrency() != 0) {
            // 货币单位校验
            SellsyCurrencyResult sellsyCurrencyResult =
                    sellsyAccountSettingService.queryCurrencyOne(new SellsyIdEnter(enter.getCurrency()));
            if (sellsyCurrencyResult == null) {
                throw new SesWebRosException(ThirdExceptionCodeEnums.SELLSY_DOCUMENT_CURRENCY_IS_NOT_EXIST.getCode(),
                        ThirdExceptionCodeEnums.SELLSY_DOCUMENT_CURRENCY_IS_NOT_EXIST.getMessage());
            }
        }

        //地址解析
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
        }*/

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
        }
        CreateDocumentAttributesEnter createDocumentAttributesEnter = CreateDocumentAttributesEnter
                .builder()
                .doctype(SellsyDocmentTypeEnums.invoice)
                .thirdid(enter.getThirdid())
                .thirdident(SellsyConstant.NO_PARAMETER)
                .ident(enter.getIdent())
                .displayedDate(null)
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
//              .enabledPaymentGateways(null)
//              .directDebitPaymentGateway(SellsyDirectDebitPaymentGatewayEnums.N)
                .build();


        SellsyNumFormatEnter numFormatEnter = new SellsyNumFormatEnter();
        numFormatEnter.setCurrencyid(enter.getCurrency());


        Map<String, SellsyRowEnter> rosMap = new HashMap<>();
        //第三行
        enter.getSellsellEnterList().forEach(item -> {
            rosMap.put(SellsyDocumentRosTypeEnums.ITEM.getCode(), SellsyRowEnter.builder()
                    .row_type(SellsyDocumentRosTypeEnums.ITEM.getValue())
                    .row_linkedid(item.getRow_linkedid())
                    .row_declid(0)
                    .row_name(item.getRow_name())
                    .row_notes(item.getRow_notes())
                    .row_unit(item.getRow_unit())
                    .row_unitAmount(item.getRow_unitAmount())
                    .row_taxid(item.getRow_taxid())
                    .row_tax2id(null)
                    .row_qt(item.getRow_qt())
                    .row_whid(null)
                    .row_isOption(SellsyBooleanEnums.N)
                    .row_purchaseAmount(item.getRow_purchaseAmount())
                    .row_discount(item.getRow_discount())
                    .row_discountUnit(SellsyGlobalDiscountUnitEnums.percent)
                    .row_serial(item.getRow_serial())
                    .row_barcode(null)
                    .row_accountingCode(item.getRow_accountingCode())
                    .build());
        });

        //第五行
        rosMap.put(SellsyDocumentRosTypeEnums.SUM.getCode(), SellsyRowEnter.builder().row_type(SellsyDocumentRosTypeEnums.SUM.getValue()).build());

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

        SellsyExecutionEnter sellsyExecutionEnter = SellsyExecutionEnter.builder()
                .method(SellsyMethodConstant.Document_Create)
                .params(sellsyCreateDocumentEnter)
                .SellsyMethodType(SellsyMethodTypeEnums.ADD.getValue())
                .build();
        SellsyGeneralResult sellsyGeneralResult = sellsyService.sellsyExecution(sellsyExecutionEnter);

        return sellsyService.jsonCreateResut(sellsyGeneralResult);
    }

    /**
     * 更新单据状态
     * @param enter
     */
    @Transactional
    @Override
    public void upateDocumentStatus(SellsyUpdateDocumentStatusEnter enter) {
        SellsyExecutionEnter sellsyExecutionEnter = SellsyExecutionEnter.builder()
                .method(SellsyMethodConstant.Document_UpdateStep)
                .params(enter)
                .SellsyMethodType(SellsyMethodTypeEnums.UPDATE.getValue())
                .build();
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
     * @param successList
     * @return
     */
    @Transactional
    @Override
    public SellsyImportExcelResult saveSellsyInvoid(List<SellsyExcleData> successList) {
        SellsyImportExcelResult result = new SellsyImportExcelResult();

        List<String> invoiceNumList = successList.stream().filter(item -> StringUtils.equals(item.getIsParent(), "1")).map(SellsyExcleData::getInvoice_num).collect(Collectors.toList());

        //校验发票单号是否已经存在
        List<SellsyInvoice> sellsyInvoiceList = sellsyInvoiceService.list(new LambdaQueryWrapper<SellsyInvoice>().in(SellsyInvoice::getInvoiceNum, invoiceNumList));
        if (CollectionUtils.isNotEmpty(sellsyInvoiceList)) {
            throw new SesWebRosException(ThirdExceptionCodeEnums.SELLSY_DOCUMENT_INDENT_IS_ALREADY_EXIST.getCode(), ThirdExceptionCodeEnums.SELLSY_DOCUMENT_INDENT_IS_ALREADY_EXIST.getMessage());
        }

        List<SellsyInvoice> saveInvoiceList = new ArrayList<>();
        List<SellsyInvoiceB> sellsyInvoiceBList = new ArrayList<>();


        List<SellsyExcleData> parentList = successList.stream().filter(item -> StringUtils.equals(item.getIsParent(), "1")).collect(Collectors.toList());
        List<SellsyExcleData> childList = successList.stream().filter(item -> StringUtils.equals(item.getIsParent(), "0")).collect(Collectors.toList());
        parentList.forEach(item -> {
            SellsyInvoice sellsyInvoice = buildSellsyInvoice(item);
            sellsyInvoiceBList.add(buildSellsyInvoiceB(item, sellsyInvoice.getId()));
            saveInvoiceList.add(sellsyInvoice);
        });
        childList.forEach(item -> {
            Long parentId =
                    saveInvoiceList.stream().filter(parent -> StringUtils.equals(parent.getInvoiceNum().trim(), item.getInvoice_num().trim())).map(SellsyInvoice::getId).findFirst().orElse(null);
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
     * @return
     */
    @Override
    public List<SellsyIdResut> createDcumentList() {
        //查询发票数据
        List<SellsyInvoice> sellsyInvoiceList = sellsyInvoiceService.list(new LambdaQueryWrapper<SellsyInvoice>().eq(SellsyInvoice::getDef1, SellsyBooleanEnums.N.getCode()));
        if (CollectionUtils.isEmpty(sellsyInvoiceList)) {
            return new ArrayList<>();
        }
        List<SellsyInvoiceB> sellsyInvoiceBList = sellsyInvoiceBService.list(new LambdaQueryWrapper<SellsyInvoiceB>().in(SellsyInvoiceB::getInvoiceId,
                sellsyInvoiceList.stream().map(SellsyInvoice::getId).collect(Collectors.toList())));
        if (CollectionUtils.isNotEmpty(sellsyInvoiceBList)) {
            throw new SesWebRosException(ThirdExceptionCodeEnums.SELLSY_INVOICE_B_IS_NOT_EXIST.getCode(), ThirdExceptionCodeEnums.SELLSY_INVOICE_B_IS_NOT_EXIST.getMessage());
        }

        //封装 发票数据
        sellsyInvoiceList.forEach(item -> {
            //查询发票排版
            /*List<SellsyLayoutResult> sellsyLayoutResults = sellsyAccountSettingService.queryDocLayoutList();
            if (CollectionUtils.isEmpty(sellsyLayoutResults)) {
                log.info("----------------布局出错------------");
                throw new RuntimeException();
            }*/
            /*List<SellsyTranslationLanguageResult> sellsyTranslationLanguageResults = sellsyAccountSettingService.queryTranslationLanguages();
            if (CollectionUtils.isEmpty(sellsyTranslationLanguageResults)) {
                log.info("----------------语言出错------------");
                throw new RuntimeException();
            }*/

            List<SellsyCurrencyResult> sellsyCurrencyResults = sellsyAccountSettingService.queryCurrencyList();
            if (CollectionUtils.isEmpty(sellsyCurrencyResults)) {
                log.info("----------------货币单位出错------------");
                throw new RuntimeException();
            }
            sellsyCurrencyResults.stream().filter(item -> StringUtils.equals(item.getLongname()));

            List<SellsyClientResult> sellsyClientResults = sellsyClientService.queryClientList();
            if (CollectionUtils.isEmpty(sellsyClientResults)) {
                log.info("----------------客户出错------------");
                throw new RuntimeException();
            }

            //查询个人信息
            SellsyCorpInfoResult sellsyCorpInfoResult = sellsyAccountSettingService.queryCorpInfos();
            if (sellsyCorpInfoResult == null) {
                log.info("----------------公司出错------------");
                throw new RuntimeException();
            }

            //查询税率
            List<SellsyRateCategoryResult> sellsyRateCategoryResults = sellsyAccountSettingService.queryRateCategoryList();
            if (CollectionUtils.isEmpty(sellsyRateCategoryResults)) {
                log.info("----------------税局出错------------");
                throw new RuntimeException();
            }
            //查询 税率
            List<SellsyTaxeResult> sellsyTaxeResults = sellsyAccountSettingService.queryTaxeList();
            if (CollectionUtils.isEmpty(sellsyTaxeResults)) {
                log.info("----------------税率出错------------");
                throw new RuntimeException();
            }

            List<SellsyCatalogueResult> sellsyCatalogueResultList = sellsyCatalogueService.queryCatalogueList(new SellsyCatalogueListEnter(SellsyCatalogueEnums.item));
            if (CollectionUtils.isEmpty(sellsyCatalogueResultList)) {
                log.info("----------------产品出错------------");
                throw new RuntimeException();
            }

            SellsyRowEnter sellsyRowEnter = SellsyRowEnter.builder()
                    .row_type(SellsyDocumentRosTypeEnums.ITEM.getValue())
                    .row_name(sellsyCatalogueResultList.get(0).getName())
                    .row_taxid(Integer.valueOf(sellsyTaxeResults.get(0).getId()))
                    .row_tax2id(null)
                    .row_qt(10)
                    .row_isOption(SellsyBooleanEnums.N)
                    .row_unitAmount(sellsyCatalogueResultList.get(0).getUnitAmount())
                    .row_discount(0)
                    .row_discountUnit(SellsyGlobalDiscountUnitEnums.percent)
                    .row_linkedid(Integer.valueOf(sellsyCatalogueResultList.get(0).getId()))
                    .row_declid(Integer.valueOf(sellsyCatalogueResultList.get(0).getDeclid()))
                    .row_notes("")
                    .row_whid(null)
                    .row_purchaseAmount(String.valueOf(Double.valueOf(sellsyCatalogueResultList.get(0).getUnitAmount()) * 10))
                    .row_serial(null)
                    .row_barcode(null)
                    .row_title("Invoice notes")
                    .row_comment(null)
                    .row_unit(null)
                    .build();


            SellsyClientServiceCreateDocumentEnter sellsyClientServiceCreateDocumentEnter = new SellsyClientServiceCreateDocumentEnter();
            sellsyClientServiceCreateDocumentEnter.setDoctype(SellsyDocmentTypeEnums.invoice);
            sellsyClientServiceCreateDocumentEnter.setThirdid(Integer.valueOf(sellsyClientResults.get(1).getId()));
            sellsyClientServiceCreateDocumentEnter.setIdent("BRO00001831");
            sellsyClientServiceCreateDocumentEnter.setSubject("RedE Group Scooter");
            sellsyClientServiceCreateDocumentEnter.setNotes(null);
            sellsyClientServiceCreateDocumentEnter.setDisplayShipAddress(SellsyBooleanEnums.Y);
            sellsyClientServiceCreateDocumentEnter.setRateCategory(Integer.valueOf(sellsyRateCategoryResults.get(0).getId()));
            sellsyClientServiceCreateDocumentEnter.setGlobalDiscount(0);
            sellsyClientServiceCreateDocumentEnter.setGlobalDiscountUnit(SellsyGlobalDiscountUnitEnums.percent);
            //sellsyClientServiceCreateDocumentEnter.setCurrency(1);
            //sellsyClientServiceCreateDocumentEnter.setDoclayout(Integer.valueOf(sellsyLayoutResults.get(1).getId()));
            //sellsyClientServiceCreateDocumentEnter.setDoclang(Integer.valueOf(sellsyTranslationLanguageResults.get(0).getId()));
            sellsyClientServiceCreateDocumentEnter.setShowContactOnPdf(SellsyBooleanEnums.Y);
            sellsyClientServiceCreateDocumentEnter.setCorpAddressId(Integer.parseInt(sellsyCorpInfoResult.getMainaddressid()));
            sellsyClientServiceCreateDocumentEnter.setThirdaddress(new SellsyIdEnter(Integer.valueOf(sellsyCorpInfoResult.getMainaddressid())));
            sellsyClientServiceCreateDocumentEnter.setSellsellEnter(sellsyRowEnter);
            SellsyIdResut document = createDocument(sellsyClientServiceCreateDocumentEnter);


        });

        return null;
    }

    private SellsyInvoice buildSellsyInvoice(SellsyExcleData item) {
        SellsyInvoice sellsyInvoice = SellsyInvoice
                .builder()
                .id(idAppService.getId("sellsy_invoice"))
                .dr(0)
                .status(item.getEtat())
                .invoiceNum(item.getInvoice_num())
                .invoiceTime(DateUtil.formatDate(item.getInvoice_date()))
                .notes(item.getInvoice_notes())
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
                .def1(SellsyBooleanEnums.N.getCode())
                .build();
        return sellsyInvoice;
    }

    private SellsyInvoiceB buildSellsyInvoiceB(SellsyExcleData item, Long parentId) {
        return SellsyInvoiceB
                .builder()
                .id(idAppService.getId("sellsy_invoice_b"))
                .dr(0)
                .invoiceId(parentId)
                .productNum(item.getProductNum())
                .productName(item.getProductName())
                .productNote(item.getProductNodes())
                .tva(new BigDecimal(item.getTva()).multiply(new BigDecimal(100)).intValue())
                .qty(Integer.valueOf(item.getProductQty()))
                .unit(null)
                .rem(new BigDecimal(item.getDiscount()).multiply(new BigDecimal(100)).intValue())
                .unitPrice(StringUtils.trim(item.getUnitPrice()))
                .createdBy(0L)
                .createdTime(new Date())
                .updatedBy(0L)
                .updatedTime(new Date())
                .build();
    }
}
