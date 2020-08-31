package com.redescooter.ses.web.ros.vo.sellsy.enter.document;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.web.ros.enums.sellsy.SellsyBooleanEnums;
import com.redescooter.ses.web.ros.enums.sellsy.SellsyGlobalDiscountUnitEnums;
import com.redescooter.ses.web.ros.exception.ThirdValidationExceptionCode;
import lombok.*;

/**
 * @ClassName:SellsyRowEnter
 * @description: SellsyRowEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/08/26 11:48
 */
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class SellsyRowEnter {
    
    // 行类型 由SellsyDocumentRosTypeEnums给出默认值
    @NotNull(code = ThirdValidationExceptionCode.ROS_TYPE_IS_EMTPY, message = "行类型为空")
    private String row_type;
    
    //行包装
    private int row_packaging;
    
    //行名
    private String row_name;
    
    //行金额 行单价 必填 int 或者字符串 “0”
    private String row_unitAmount;
    
    // 税率编号Id 必填 默认为20的税率 以提供相关的税率列表
    private Integer row_taxid;
    
    // 税率 id (如果有双列 = y 需要)
    private Integer row_tax2id;
    
    //数量
    private Integer row_qt;
    
    //行选项  Y, N 默认为N
    private SellsyBooleanEnums row_isOption;
    
    // 行折扣金额 默认0 如果是 百分数 必须是 1-100的整数
    private float row_discount;
    
    //单位 amount, percent
    private SellsyGlobalDiscountUnitEnums row_discountUnit;
    
    // 账户Id 暂定为String 暂时未知 需要测试
    private Integer row_accountingCode;
    
    // 发货名称
    private int row_shipping;
    
    // 产品Id
    private int row_linkedid;
    
    // 产品的decli ID
    private int row_declid;
    
    // 行备注
    private String row_notes;
    
    //仓库Id
    private Integer row_whid;
    
    //购买金额 int, if 0 use string (ex : "0")
    private String row_purchaseAmount;
    
    //项目序列号。如果您的项目使用序列化库存，以及如果您的文件影响库存，则必须使用序列号
    private String row_serial;
    
    //商品条码 如果您的产品只使用一个条形码，则默认情况下将使用该条形码，否则将不使用该条形码
    private String row_barcode;
    
    //行标题 必填
    @NotNull(code = ThirdValidationExceptionCode.SELLSY_DOCUMENT_ROW_TITLE_IS_EMPTY, message = "行标题为空")
    private String row_title;
    
    //备注
    private String row_comment;
    
    //行单位 默认账户单位Id 必填
    private String row_unit;
}
