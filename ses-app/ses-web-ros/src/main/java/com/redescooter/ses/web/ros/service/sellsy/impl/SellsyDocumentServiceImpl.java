package com.redescooter.ses.web.ros.service.sellsy.impl;

import com.alibaba.fastjson.JSONObject;
import com.redescooter.ses.web.ros.constant.SellsyMethodConstant;
import com.redescooter.ses.web.ros.enums.sellsy.SellsyBooleanEnums;
import com.redescooter.ses.web.ros.enums.sellsy.SellsyDocmentTypeEnums;
import com.redescooter.ses.web.ros.enums.sellsy.SellsyMethodTypeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.exception.ThirdExceptionCodeEnums;
import com.redescooter.ses.web.ros.service.sellsy.SellsyAccountSettingService;
import com.redescooter.ses.web.ros.service.sellsy.SellsyClientService;
import com.redescooter.ses.web.ros.service.sellsy.SellsyDocumentService;
import com.redescooter.ses.web.ros.service.sellsy.SellsyService;
import com.redescooter.ses.web.ros.vo.sellsy.enter.SellsyExecutionEnter;
import com.redescooter.ses.web.ros.vo.sellsy.enter.SellsyIdEnter;
import com.redescooter.ses.web.ros.vo.sellsy.enter.client.SellsyQueryClientOneEnter;
import com.redescooter.ses.web.ros.vo.sellsy.enter.document.SellsyCreateDocumentEnter;
import com.redescooter.ses.web.ros.vo.sellsy.enter.document.SellsyDocumentListEnter;
import com.redescooter.ses.web.ros.vo.sellsy.enter.document.SellsyDocumentOneEnter;
import com.redescooter.ses.web.ros.vo.sellsy.result.SellsyGeneralResult;
import com.redescooter.ses.web.ros.vo.sellsy.result.account.SellsyCurrencyResult;
import com.redescooter.ses.web.ros.vo.sellsy.result.account.SellsyRateCategoryResult;
import com.redescooter.ses.web.ros.vo.sellsy.result.client.SellsyClientResult;
import com.redescooter.ses.web.ros.vo.sellsy.result.document.SellsyDocumentListResult;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

        return sellsyService.jsonArrayFormattingByPage(sellsyGeneralResult, SellsyDocumentListResult.class);
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
    @Transactional
    public void createDocument(SellsyCreateDocumentEnter enter) {

        // 校验客户
        SellsyClientResult sellsyClientResult =
            sellsyClientService.queryClientOne(new SellsyQueryClientOneEnter(enter.getDocument().getThirdid()));
        if (sellsyClientResult == null) {
            throw new SesWebRosException(ThirdExceptionCodeEnums.SELLSY_CLIENT_IS_NOT_EXIST.getCode(),
                ThirdExceptionCodeEnums.SELLSY_CLIENT_IS_NOT_EXIST.getMessage());
        }
        // 如果传入发票单号 ，则进行校验
        if (StringUtils.isNotBlank(enter.getDocument().getIdent())) {
            SellsyDocumentListEnter sellsyDocumentListEnter = SellsyDocumentListEnter.builder()
                .doctype(SellsyDocmentTypeEnums.invoice.getCode()).ident(enter.getDocument().getIdent()).build();
            List<SellsyDocumentListResult> sellsyDocumentListResultPageResult =
                queryDocumentList(sellsyDocumentListEnter);
            if (CollectionUtils.isNotEmpty(sellsyDocumentListResultPageResult)) {
                throw new SesWebRosException(ThirdExceptionCodeEnums.SELLSY_DOCUMENT_IS_ALREADY_EXIST.getCode(),
                    ThirdExceptionCodeEnums.SELLSY_DOCUMENT_IS_ALREADY_EXIST.getMessage());
            }
        }

        // 查询税率 是税前发票 还是税后发票
        SellsyRateCategoryResult sellsyRateCategoryResult =
            sellsyAccountSettingService.queryateCategoryOne(new SellsyIdEnter(enter.getDocument().getRateCategory()));
        if (sellsyRateCategoryResult == null) {
            throw new SesWebRosException(ThirdExceptionCodeEnums.SELLSY_DOCUMNT_RATECATEGORY_IS_EMPTY.getCode(),
                ThirdExceptionCodeEnums.SELLSY_DOCUMNT_RATECATEGORY_IS_EMPTY.getMessage());
        }

        // 货币单位校验
        SellsyCurrencyResult sellsyCurrencyResult =
            sellsyAccountSettingService.queryCurrencyOne(new SellsyIdEnter(enter.getDocument().getCurrency()));
        if (sellsyCurrencyResult == null) {
            throw new SesWebRosException(ThirdExceptionCodeEnums.SELLSY_DOCUMENT_CURRENCY_IS_NOT_EXIST.getCode(),
                ThirdExceptionCodeEnums.SELLSY_DOCUMENT_CURRENCY_IS_NOT_EXIST.getMessage());
        }

        // 服务时间 校验
        if (enter.getDocument().getUseServiceDates().equals(SellsyBooleanEnums.Y)) {
            if (enter.getDocument().getServiceDateStart() != null || enter.getDocument().getServiceDateStop() != null) {
                throw new SesWebRosException(ThirdExceptionCodeEnums.SELLSY_DOCUMENT_USESERVICEDATES_IS_EMPTY.getCode(),
                    ThirdExceptionCodeEnums.SELLSY_DOCUMENT_USESERVICEDATES_IS_EMPTY.getMessage());
            }
        }

        /*CreateDocumentAttributesEnter createDocumentAttributesEnter = CreateDocumentAttributesEnter
                .builder()
            .doctype(SellsyDocmentTypeEnums.invoice)
                .thirdid("25630126")
                .thirdident(null)
                .ident("Alex001")
                .displayedDate(null)
                .expireDate(null)
                .subject("测试发票")
                .notes("测试")
                .tags(null)
                .displayShipAddress(SellsyBooleanEnums.N)
                .rateCategory(null)
                .globalDiscount(0)
            .globalDiscountUnit(SellsyGlobalDiscountUnitEnums.percent).hasDoubleVat(SellsyBooleanEnums.N)
            .hasTvaLawText(SellsyBooleanEnums.N)
                .currency(1)
                .doclayout(93787)
                .doclang(0)
                .payMediums(null)
                .docspeakerStaffId(163567)
                .useServiceDates(SellsyBooleanEnums.N)
                .serviceDateStart(null)
                .serviceDateStop(null)
                .showContactOnPdf(SellsyBooleanEnums.Y)
                .showParentOnPdf(SellsyBooleanEnums.N)
                .conditionDocShow(SellsyBooleanEnums.N)
                .corpAddressId(86382253)
                .enabledPaymentGateways(SellsyPayTypeEnums.adyen)
            .directDebitPaymentGateway(SellsyDirectDebitPaymentGatewayEnums.N)
                .build();
        
        //Yes, if 'paydate' array exists
        SellsyPaydateAttributesEnter sellsyPaydateAttributesEnter = null;
        
        //
        SellsyDocumentShippingEnter sellsyDocumentShippingEnter = null;
        
        //AccountPrefs.setNumberFormat 方法可以查出
        SellsyNumFormatEnter numFormatEnter = null;
        
        SellsyIdEnter thirdaddress = new SellsyIdEnter(86382254);
        
        SellsyIdEnter shipaddress = new SellsyIdEnter(86382255);
        
        Map<String, SellsyRowEnter> rosMap = new HashMap<>();
        
        //第一行
        rosMap.put(SellsyDocumentRosTypeEnums.PACKAGING.getCode(), SellsyRowEnter.builder()
                .row_type(SellsyDocumentRosTypeEnums.PACKAGING.getValue())
                .row_packaging(8189454)
                .row_name("第一行")
                .row_unitAmount("0")
                .row_taxid(3497473)
                .row_tax2id(null)
                .row_qt(1)
            .row_isOption(SellsyBooleanEnums.N)
                .row_discount(20)
            .row_discountUnit(SellsyGlobalDiscountUnitEnums.percent)
                .row_accountingCode(1051763)
                .build());
        
        //第二行
        rosMap.put(SellsyDocumentRosTypeEnums.SHIPPING.getCode(), SellsyRowEnter.builder()
                .row_type(SellsyDocumentRosTypeEnums.EMPTY.getValue())
                .row_shipping(8189455)
                .row_name("第二行")
                .row_unitAmount("0")
                .row_taxid(3497473)
                .row_tax2id(null)
                .row_qt(1)
            .row_isOption(SellsyBooleanEnums.N)
                .row_discount(20)
            .row_discountUnit(SellsyGlobalDiscountUnitEnums.percent)
                .row_accountingCode(1051763)
                .build());
        
        //第三行
        rosMap.put(SellsyDocumentRosTypeEnums.ITEM.getCode(), SellsyRowEnter.builder()
                .row_type(SellsyDocumentRosTypeEnums.ITEM.getValue())
                .row_linkedid(8189454)
                .row_declid(0)
                .row_name("第三行")
                .row_notes("测试")
                .row_unit(SellsyRowUnitEnums.KG.getValue())
                .row_unitAmount("0")
                .row_taxid(3497473)
                .row_tax2id(null)
                .row_qt(1)
                .row_whid(null)
            .row_isOption(SellsyBooleanEnums.N)
                .row_purchaseAmount("0")
                .row_discount(20)
            .row_discountUnit(SellsyGlobalDiscountUnitEnums.percent)
                .row_serial("0")
                .row_barcode("123")
                .row_accountingCode(1051763)
                .build());
        
        
        //第四行
        rosMap.put(SellsyDocumentRosTypeEnums.ONCE.getCode(), SellsyRowEnter.builder()
                .row_type(SellsyDocumentRosTypeEnums.ONCE.getValue())
                .row_name("第四行")
                .row_notes("测试")
                .row_unit(SellsyRowUnitEnums.KG.getValue())
                .row_unitAmount("0")
                .row_taxid(3497473)
                .row_tax2id(null)
                .row_qt(1)
            .row_isOption(SellsyBooleanEnums.N)
                .row_discount(20)
            .row_discountUnit(SellsyGlobalDiscountUnitEnums.percent)
                .row_accountingCode(1051763)
                .build());
        
        //第五行
        rosMap.put(SellsyDocumentRosTypeEnums.SUM.getCode(), SellsyRowEnter.builder().row_type(SellsyDocumentRosTypeEnums.SUM.getValue()).build());
        
        //第六行
        rosMap.put(SellsyDocumentRosTypeEnums.TITLE.getCode(), SellsyRowEnter
                .builder()
                .row_type(SellsyDocumentRosTypeEnums.TITLE.getValue())
                .row_title("发票测试")
                .build());
        
        //第七行
        rosMap.put(SellsyDocumentRosTypeEnums.COMMENT.getCode(), SellsyRowEnter
                .builder()
                .row_type(SellsyDocumentRosTypeEnums.COMMENT.getValue())
                .row_comment("test")
                .build());
        
        //第8行
        rosMap.put(SellsyDocumentRosTypeEnums.BREAK.getCode(), SellsyRowEnter.builder().row_type(SellsyDocumentRosTypeEnums.BREAK.getValue()).build());
        //第9行
        rosMap.put(SellsyDocumentRosTypeEnums.EMPTY.getCode(), SellsyRowEnter.builder().row_type(SellsyDocumentRosTypeEnums.EMPTY.getValue()).build());
        
        enter.setDocument(createDocumentAttributesEnter);
        enter.setPaydate(sellsyPaydateAttributesEnter);
        enter.setShipping(sellsyDocumentShippingEnter);
        enter.setNum_format(numFormatEnter);
        enter.setThirdaddress(thirdaddress);
        enter.setShipaddress(shipaddress);
        enter.setRow(rosMap);
        
        
        SellsyExecutionEnter sellsyExecutionEnter = SellsyExecutionEnter.builder()
                .method(SellsyMethodConstant.Document_Create)
                .params(enter)
                .SellsyMethodType(SellsyMethodTypeEnums.ADD.getValue())
                .build();
        SellsyGeneralResult sellsyGeneralResult = sellsyService.sellsyExecution(sellsyExecutionEnter);*/
    }
}
