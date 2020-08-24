package com.redescooter.ses.web.ros.vo.sellsy.enter;

import com.redescooter.ses.web.ros.enums.sellsy.DirectDebitPaymentGatewayEnums;
import com.redescooter.ses.web.ros.enums.sellsy.SellsyBooleanEnums;
import com.redescooter.ses.web.ros.enums.sellsy.SellsyPayTypeEnums;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.xml.soap.Text;
import java.sql.Timestamp;
import java.util.List;

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
public class SellsyCreateOrderEnter {

    private String doctype;
    
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
    private SellsyBooleanEnums showcontacttonpdf;

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
    
//    private
}

