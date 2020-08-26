package com.redescooter.ses.web.ros.vo.sellsy.enter.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
/**
 * @ClassName:SellsyCreateOrderEnter
 * @description: SellsyCreateOrderEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/08/24 11:56
 */
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class SellsyCreateDocumentEnter {
    
    private CreateDocumentAttributesEnter document;
    
    
    /*private String doctype;
    
    private int parentId;
    
    //客户id 必填
    private int clientid;
    
    //Client reference 客户参考资料
    private String thirdident;
    
    private int contactId;
    
    private String contactName;
    
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
    
    ///文件编号
    private String ident;
    
    //期望文件时间
    private Timestamp displayedDate;

    //文件有效日期(文件开发和表格修订日期)
    private Timestamp  expireDate;
    
    //在文件上显示送货地址 boolean
    private String displayshippdashenum;

    // 影响文档的给定类别
    private int rateCategory;
    
    //期望整体折扣
    private float  globalDiscount;
    
    //期望的单位总体折扣(百分比/金额)
//    private enum  globalDiscountUnit;

    // use double VAT or not 是否使用双倍增值税
//    private enum  hasdoubleat;

    //是否使用与增值税号码相关的法律术语
//    private enum  hasTvaLawText;

    //Current document currency ID 当前文件货币 ID
    private int currency;
    
    //ID of the desired layout 所需布局的 ID
    private int dojoout;
    
    //翻译语言
    private int doclang;
    
    //Pay mean(s) array of IDs 支付平均值 id 数组
    private List<Integer> payMediums;
    
    //支付方式
    private SellsyPayTypeEnums enabledPaymentGateways;
    
    //是否直接付款
    private DirectDebitPaymentGatewayEnums directDebitPaymentGateway;
   
    //文档对象主题
    private String document_subject;

    //文档注释字段
    private Text document_notes;
    
    //文档标签
    private Text document_tags;

    //客户地址
    private int thirdaddress_id;

    //交付客户地址ID
    private int shipaddress_id;

    //公司地址
    private int corpAddressId;

    //包装名称 必填
    private Text packagin_name;
    
    //发货名称
    private Text shipping_name;
    
    //行名
    private Text row_name;

    //行备注
    private Text row_notes;

    //行单位 必填
    private Text row_unit;

    //行单位总数 可以是"0" 必填
    private int row_unit_amount;
    
    //行金额 int 或者字符串 必填
    private String row_purchaseAmount;
    
    //行税率Id 必填
    private int row_taxid;
    
    //Tax rate id (required if hasDoubleVat = Y) 税率 id (如果有双列 = y 需要)
    private int row_tax2id;
    
    //使用或不使用生态税
    private SellsyBooleanEnums row_useEcoTax;

    //生态税类型
    private String row_ecoTaxType;
    
    //税额
    private float  row_ecoTax;
    
    //数量
    private int row_quantity;
    
    //折扣的金额/百分比。如果是百分比，值应该是0到100之间的整数
    private float row_discount;
    
    //金额，百分比 单位
    private String row_discountUnit;
    
    //库存影响用仓库 帐户默认仓库
    private int row_whid;
    
    //项目序列号。如果您的项目使用序列化库存，以及如果您的文件影响库存，则必须使用序列号
    private String row_serial;
    
    //商品条形码 如果您的产品只使用一个条形码，则默认情况下将使用该条形码，否则将不使用该条形码
    private String row_barcode;
    
    //注释 备注
    private String row_comment;

    // Title  标题
    private String row_title;

    //	Settlement ID 结算编号
//    private int paydate_id;
    
    private int paydate_xdays;
    
    //月底迟交发票
    private SellsyBooleanEnums paydate_endmonth;
    
    // 结算细节 ，如果结算期选择了系统代码“ scaled” 有账户默认值
    private String paydate_scaledDetail;
    
    //发票必须迟开的日期 Account default value 帐户默认值
    private Timestamp  paydate_custom;
    
    
     //Setting a payment deadline, mandatory if the document uses multiple deadlines (paydate syscode : deadlines 设置付款截止日期，如果文档使用多个截止日期(付款日期 syscode: 截止日期) ，则必须设置
     
    */
    
    
}

