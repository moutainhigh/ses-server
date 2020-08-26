package com.redescooter.ses.web.ros.service.sellsy.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.constant.SellsyMethodConstant;
import com.redescooter.ses.web.ros.enums.sellsy.DirectDebitPaymentGatewayEnums;
import com.redescooter.ses.web.ros.enums.sellsy.DocmentTypeEnums;
import com.redescooter.ses.web.ros.enums.sellsy.SellsyBooleanEnums;
import com.redescooter.ses.web.ros.enums.sellsy.SellsyDocumentRosTypeEnums;
import com.redescooter.ses.web.ros.enums.sellsy.SellsyGlobalDiscountUnitEnums;
import com.redescooter.ses.web.ros.enums.sellsy.SellsyMethodTypeEnums;
import com.redescooter.ses.web.ros.enums.sellsy.SellsyPayTypeEnums;
import com.redescooter.ses.web.ros.enums.sellsy.SellsyRowUnitEnums;
import com.redescooter.ses.web.ros.service.sellsy.SellsyDocumentService;
import com.redescooter.ses.web.ros.service.sellsy.SellsyService;
import com.redescooter.ses.web.ros.vo.sellsy.enter.SellsyExecutionEnter;
import com.redescooter.ses.web.ros.vo.sellsy.enter.SellsyIdEnter;
import com.redescooter.ses.web.ros.vo.sellsy.enter.document.CreateDocumentAttributesEnter;
import com.redescooter.ses.web.ros.vo.sellsy.enter.document.SellsyCreateDocumentEnter;
import com.redescooter.ses.web.ros.vo.sellsy.enter.document.SellsyDocumentByIdEnter;
import com.redescooter.ses.web.ros.vo.sellsy.enter.document.SellsyDocumentListEnter;
import com.redescooter.ses.web.ros.vo.sellsy.enter.document.SellsyDocumentShippingEnter;
import com.redescooter.ses.web.ros.vo.sellsy.enter.document.SellsyNumFormatEnter;
import com.redescooter.ses.web.ros.vo.sellsy.enter.document.SellsyPaydateAttributesEnter;
import com.redescooter.ses.web.ros.vo.sellsy.enter.document.SellsyRowEnter;
import com.redescooter.ses.web.ros.vo.sellsy.result.SellsyGeneralResult;
import com.redescooter.ses.web.ros.vo.sellsy.result.document.SellsyDocumentListResult;
import com.sellsy.apientities.SellsyResponseInfo;
import lombok.extern.log4j.Log4j2;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName:DocumentServiceImpl
 * @description: DocumentServiceImpl
 * @author: Alex
 * @Version：1.3
 * @create: 2020/08/26 14:34
 */
@Log4j2
@Service
public class SellsyDocumentServiceImpl implements SellsyDocumentService {
    
    @Autowired
    private SellsyService sellsyService;
    
    /**
     * 单据列表
     *
     * @param enter
     */
    @Override
    public PageResult<SellsyDocumentListResult> queryDocumentList(SellsyDocumentListEnter enter) {
        SellsyExecutionEnter sellsyExecutionEnter = SellsyExecutionEnter.builder()
                .method(SellsyMethodConstant.Document_GetList)
                .params(enter)
                .SellsyMethodType(SellsyMethodTypeEnums.QUERY.getValue())
                .build();
        
        SellsyGeneralResult sellsyGeneralResult = sellsyService.sellsyExecution(sellsyExecutionEnter);
        
        //返回值赋值 sellsyGeneralResult
        List<SellsyDocumentListResult> resultList = null;
        SellsyResponseInfo infos = null;
        try {
            resultList = JSON.parseArray(sellsyGeneralResult.getResult().extractResponseList().toString(), SellsyDocumentListResult.class);
            infos = sellsyGeneralResult.getResult().getInfos();
        } catch (Exception e) {
        
        }
        if (infos == null) {
            return PageResult.createZeroRowResult(enter);
        }
        return PageResult.create(enter, infos.getNbtotal(), resultList);
    }
    
    /**
     * 查询指定单据
     *
     * @param enter
     */
    @Override
    public JSONObject queryDocumentById(SellsyDocumentByIdEnter enter) {
        SellsyExecutionEnter sellsyExecutionEnter = SellsyExecutionEnter.builder()
                .method(SellsyMethodConstant.Document_GetOne)
                .params(enter)
                .SellsyMethodType(SellsyMethodTypeEnums.ADD.getValue())
                .build();
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
    @Override
    public void createDocument(SellsyCreateDocumentEnter enter) {
        
        CreateDocumentAttributesEnter createDocumentAttributesEnter = CreateDocumentAttributesEnter
                .builder()
                .doctype(DocmentTypeEnums.INVOICE.getCode())
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
                .globalDiscountUnit(SellsyGlobalDiscountUnitEnums.PERCENT.getValue())
                .hasDoubleVat(SellsyBooleanEnums.N.getValue())
                .hasTvaLawText(SellsyBooleanEnums.N.getValue())
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
                .directDebitPaymentGateway(DirectDebitPaymentGatewayEnums.N)
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
                .row_isOption(SellsyBooleanEnums.N.getValue())
                .row_discount(20)
                .row_discountUnit(SellsyGlobalDiscountUnitEnums.PERCENT.getValue())
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
                .row_isOption(SellsyBooleanEnums.N.getValue())
                .row_discount(20)
                .row_discountUnit(SellsyGlobalDiscountUnitEnums.PERCENT.getValue())
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
                .row_isOption(SellsyBooleanEnums.N.getValue())
                .row_purchaseAmount("0")
                .row_discount(20)
                .row_discountUnit(SellsyGlobalDiscountUnitEnums.PERCENT.getValue())
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
                .row_isOption(SellsyBooleanEnums.N.getValue())
                .row_discount(20)
                .row_discountUnit(SellsyGlobalDiscountUnitEnums.PERCENT.getValue())
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
        SellsyGeneralResult sellsyGeneralResult = sellsyService.sellsyExecution(sellsyExecutionEnter);
    }
}
