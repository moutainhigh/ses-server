package com.redescooter.ses.web.ros.vo.sellsy.enter.document;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.web.ros.enums.sellsy.*;
import com.redescooter.ses.web.ros.exception.ThirdValidationExceptionCode;
import lombok.*;

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
    
    //发票类型
    @NotNull(code = ThirdValidationExceptionCode.SELLSY_DOCTYPE_IS_EMPTY,message = "单据为空")
    private SellsyDocmentTypeEnums doctype;
    
    // 发票父级Id
    private int parentId;
    
    //客户Id
    @NotNull(code = ThirdValidationExceptionCode.SELLSY_CLIENT_ID_IS_EMPTY,message = "客户Id 为空")
    private int thirdid;
    
    // 客户引用 不必填
    private String thirdident;
    
    // 发票编号 支持自定义 如果 发票编号和已存在发票编号 重复sellsy 会自动生成新发票
    private String ident;
    
    // 期望文件时间 开具发票时间
    @NotNull(code = ThirdValidationExceptionCode.SELLSY_DOCUMENT_DISPLAYEDDATE_IS_EMPTY, message = "开具发票时间为空")
    private Timestamp displayedDate;
    
    // 文件到期时间 选填
    private Timestamp  expireDate;
    
    //文档对象主题
    @NotNull(code = ThirdValidationExceptionCode.SELLSY_DOCUMENT_OBJECT_IS_EMPTY, message = "单据主题为空")
    private String subject;
    
    // 文档注释字段 可以为空
    private String notes;
    
    // 文档标签 为空
    private String tags;

    // 是否显示收获地址 默认为N
    @NotNull(code = ThirdValidationExceptionCode.SELLSY_DOCUMENT_DISPLAYSHIPADDRESS_IS_EMPTY, message = "收货人地址")
    private SellsyBooleanEnums displayShipAddress = SellsyBooleanEnums.N;
    
    // 影响文档的给定类别 （Accountdatas.getRateCategories） 可以获取文档级别
    // 主要指 含税 HT不含税价格 和 TTC 关税类别 系统默认的
    private Integer rateCategory;
    
    // 期望整体折扣 折扣 默认为0
    private float  globalDiscount;
    
    // 期望的单位总体折扣(百分比/金额) 默认为百分比
    private SellsyGlobalDiscountUnitEnums globalDiscountUnit = SellsyGlobalDiscountUnitEnums.percent;

    // 是否使用双倍增值税 默认为N 如果是Y 则 row_taxid row_taxid2 则需要必填
    private SellsyBooleanEnums hasDoubleVat = SellsyBooleanEnums.N;

    // 是否使用与增值税号码相关的法律术语 默认为N
    private SellsyBooleanEnums hasTvaLawText = SellsyBooleanEnums.N;
    
    // Current document currency ID 当前文件货币 ID 查询具体的Account 接口
    @NotNull(code = ThirdValidationExceptionCode.SELLSY_DOCUMENT_CURRENCY_IS_EMPTY, message = "货币单位为空")
    private int currency;
    
    // 布局Id 默认布局 有默认值 发票外观
    @NotNull(code = ThirdValidationExceptionCode.SELLSY_DOCUMENT_DOCLAYOUT_IS_EMPTY, message = "发票布局为空")
    private int doclayout;
    
    // 翻译语言 有系统设置
    @NotNull(code = ThirdValidationExceptionCode.SELLSY_DOCUMENT_LANG_IS_EMPTY, message = "发票语言为空")
    private int doclang;
    
    //Pay mean(s) array of IDs 支付平均值 id 数组
    // 支付方式 不填写的话 为账户默认值
    @NotNull(code = ThirdValidationExceptionCode.SELLSY_DOCUMENT_PAY_TYPE_IS_EMPTY, message = "支付方式为空")
    private List<Integer> payMediums;
    
    // 员工Id 默认为文档创建者
    private int docspeakerStaffId;
    
    // 是否使用当前时间 指 当前时间
    private SellsyBooleanEnums useServiceDates = SellsyBooleanEnums.N;
    
    // 服务开始时间 如果上面 是Y 开始结束时间必须传值
    private Timestamp serviceDateStart;
    
    //服务结束时间
    private Timestamp serviceDateStop;
    
    //是否再PDF 上显示 联系方式
    private SellsyBooleanEnums showContactOnPdf = SellsyBooleanEnums.N;
    
    //显示 PDF 格式的父文件
    private SellsyBooleanEnums showParentOnPdf = SellsyBooleanEnums.N;
    
    //需方确认及接受条件
    private SellsyBooleanEnums conditionDocShow = SellsyBooleanEnums.N;
    
    // 公司地址 无默认值
    @NotNull(code = ThirdValidationExceptionCode.SELLSY_DOCUMENT_COMPANY_ADDRESS_IS_EMPTY, message = "公司地址为空")
    private int corpAddressId;
    
    // 是否启用网关支付
    private SellsyPayTypeEnums enabledPaymentGateways;
    
    // 直接网关使用网关支付
    private SellsyDirectDebitPaymentGatewayEnums directDebitPaymentGateway;
    
}
