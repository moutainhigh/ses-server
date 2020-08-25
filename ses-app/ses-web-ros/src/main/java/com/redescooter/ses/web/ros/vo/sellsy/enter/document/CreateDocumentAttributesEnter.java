package com.redescooter.ses.web.ros.vo.sellsy.enter.document;

import com.redescooter.ses.web.ros.enums.sellsy.SellsyBooleanEnums;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @ClassName:CreateDocumentAttributesEnter
 * @description: CreateDocumentAttributesEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/08/25 19:02
 */
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class CreateDocumentAttributesEnter {
    private String doctype;
    
    private int parentId;
    
    private int thirdid;
    
    private String thirdident;
    
    private String ident;
    
    private Timestamp displayedDate;
    
    private Timestamp  expireDate;
    
    //文档对象主题
    private String document_subject;
    
    //文档注释字段
    private String document_notes;
    
    //文档标签
    private String document_tags;
    
    private SellsyBooleanEnums displayShipAddress;
    
    // 影响文档的给定类别
    private int rateCategory;
    
    //期望整体折扣
    private float  globalDiscount;
    
    //期望的单位总体折扣(百分比/金额)
    private String  globalDiscountUnit;
    
    private String hasDoubleVat;
    
    private String  hasTvaLawText;
    


            'hasTvaLawText'     => {{hasTvaLawText}},
            'currency'          => {{currency}},
            'doclayout'         => {{doclayout}},
            'doclang'           => {{doclang}},
            'payMediums'        => {{payMediums}},
            'docspeakerStaffId'	=> {{docspeakerStaffId}},
            'useServiceDates'	=> {{useServiceDates}},
            'serviceDateStart'	=> {{serviceDateStart}},
            'serviceDateStop'	=> {{serviceDateStop}},
            'showContactOnPdf'	=> {{showContactOnPdf}},
            'showParentOnPdf'	=> {{showParentOnPdf}},
            'conditionDocShow'  => {{conditionDocShow}}
            'corpAddressId'     => {{corpAddressId}},
            'enabledPaymentGateways' => {{enabledPaymentGateways}}
            'directDebitPaymentGateway' => {{directDebitPaymentGateway}}
}
