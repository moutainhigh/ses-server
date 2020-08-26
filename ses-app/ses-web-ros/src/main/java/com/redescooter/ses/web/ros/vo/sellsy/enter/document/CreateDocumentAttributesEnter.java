package com.redescooter.ses.web.ros.vo.sellsy.enter.document;

import com.redescooter.ses.web.ros.enums.sellsy.DirectDebitPaymentGatewayEnums;
import com.redescooter.ses.web.ros.enums.sellsy.SellsyBooleanEnums;
import com.redescooter.ses.web.ros.enums.sellsy.SellsyPayTypeEnums;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

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
    
    //Current document currency ID 当前文件货币 ID
    private int currency;
    
    //布局Id
    private int doclayout;
    
    //翻译语言
    private int doclang;
    
    //Pay mean(s) array of IDs 支付平均值 id 数组
    private List<Integer> payMediums;
    
    //参考人员
    private int docspeakerStaffId;
    
    //是否使用当前时间
    private SellsyBooleanEnums useServiceDates;
    
    //服务开始时间
    private Timestamp serviceDateStart;
    
    //服务结束时间
    private Timestamp serviceDateStop;
    
    //是否再PDF 上显示 联系方式
    private SellsyBooleanEnums showContactOnPdf;
    
    //显示 PDF 格式的父文件
    private SellsyBooleanEnums showParentOnPdf;
    
    //需方确认及接受条件
    private SellsyBooleanEnums conditionDocShow;
    
    //公司地址
    private int corpAddressId;
    
    //支付方式
    private SellsyPayTypeEnums enabledPaymentGateways;
    
    //是否直接付款
    private DirectDebitPaymentGatewayEnums directDebitPaymentGateway;
    
}
