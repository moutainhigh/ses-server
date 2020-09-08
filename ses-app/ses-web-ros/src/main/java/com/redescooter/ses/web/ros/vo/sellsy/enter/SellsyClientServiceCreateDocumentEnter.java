package com.redescooter.ses.web.ros.vo.sellsy.enter;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.web.ros.enums.sellsy.SellsyBooleanEnums;
import com.redescooter.ses.web.ros.enums.sellsy.SellsyDocmentTypeEnums;
import com.redescooter.ses.web.ros.enums.sellsy.SellsyGlobalDiscountUnitEnums;
import com.redescooter.ses.web.ros.exception.ThirdValidationExceptionCode;
import com.redescooter.ses.web.ros.vo.sellsy.enter.document.SellsyRowEnter;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class SellsyClientServiceCreateDocumentEnter {
    //发票类型
    @NotNull(code = ThirdValidationExceptionCode.SELLSY_DOCTYPE_IS_EMPTY, message = "单据为空")
    private SellsyDocmentTypeEnums doctype;

    //客户Id
    @NotNull(code = ThirdValidationExceptionCode.SELLSY_CLIENT_ID_IS_EMPTY, message = "客户Id 为空")
    private int thirdid;

    // 发票编号 支持自定义 如果 发票编号和已存在发票编号 重复sellsy 会自动生成新发票
    private String ident;

    //文档对象主题
    @NotNull(code = ThirdValidationExceptionCode.SELLSY_DOCUMENT_OBJECT_IS_EMPTY, message = "单据主题为空")
    private String subject;

    // 文档注释字段 可以为空
    private String notes;

    // 是否显示收获地址 默认为N
    @NotNull(code = ThirdValidationExceptionCode.SELLSY_DOCUMENT_DISPLAYSHIPADDRESS_IS_EMPTY, message = "收货人地址")
    private SellsyBooleanEnums displayShipAddress = SellsyBooleanEnums.N;

    // 影响文档的给定类别 （Accountdatas.getRateCategories） 可以获取文档级别
    // 主要指 含税 HT不含税价格 和 TTC 关税类别 系统默认的
    private Integer rateCategory;

    // 期望整体折扣 折扣 默认为0
    private float globalDiscount = 0;

    // 期望的单位总体折扣(百分比/金额) 默认为百分比
    private SellsyGlobalDiscountUnitEnums globalDiscountUnit = SellsyGlobalDiscountUnitEnums.percent;

    // Current document currency ID 当前文件货币 ID 查询具体的Account 接口
    @NotNull(code = ThirdValidationExceptionCode.SELLSY_DOCUMENT_CURRENCY_IS_EMPTY, message = "货币单位为空")
    private Integer currency;

    // 布局Id 默认布局 有默认值 发票外观
    //@NotNull(code = ThirdValidationExceptionCode.SELLSY_DOCUMENT_DOCLAYOUT_IS_EMPTY, message = "发票布局为空")
    private Integer doclayout;

    // 翻译语言 有系统设置
    //@NotNull(code = ThirdValidationExceptionCode.SELLSY_DOCUMENT_LANG_IS_EMPTY, message = "发票语言为空")
    private Integer doclang;

    //是否再PDF 上显示 联系方式
    private SellsyBooleanEnums showContactOnPdf = SellsyBooleanEnums.Y;

    // 公司地址 无默认值
    @NotNull(code = ThirdValidationExceptionCode.SELLSY_DOCUMENT_COMPANY_ADDRESS_IS_EMPTY, message = "公司地址为空")
    private int corpAddressId;

    //产品行  即第三行
    private List<SellsyRowEnter> sellsellEnterList;

    //客户地址 ID 可以不填写
    private SellsyIdEnter thirdaddress;

    //交付客户地址 ID 送货地址 可以都填写
    private SellsyIdEnter shipaddress;

    // 期望时间
    private Timestamp displayedDate;
}
