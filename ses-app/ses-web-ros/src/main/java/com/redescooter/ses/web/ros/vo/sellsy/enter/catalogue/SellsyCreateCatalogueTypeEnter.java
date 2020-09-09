package com.redescooter.ses.web.ros.vo.sellsy.enter.catalogue;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.web.ros.enums.sellsy.SellsyBooleanEnums;
import com.redescooter.ses.web.ros.exception.ThirdValidationExceptionCode;

import lombok.*;

@Data // 生成getter,setter等函数
@AllArgsConstructor // 生成全参数构造函数
@NoArgsConstructor // 生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class SellsyCreateCatalogueTypeEnter {

    //产品Id 更新时需要
    private Integer id;

    //产品名称
    private String name;

    //成品名称
    private String tradename;

    //是否在描述中展产品名称
    private SellsyBooleanEnums tradenametonote=SellsyBooleanEnums.Y;

    //备注
    private String notes;

    //标签
    private String tags;

    //单价
    private float unitAmount;

    //采购价格
    private float  purchaseAmount;

    //单位
    @NotNull(code = ThirdValidationExceptionCode.SELLSY_PRODUCT_UNIT_IS_EMPTY,message = "产品价格单位为空")
    private String unit;

    //数量
    private int qty=1;

    //单价是否含税
    private SellsyBooleanEnums unitAmountIsTaxesFree=SellsyBooleanEnums.N;

    //税率
    private Integer taxid;

    //销售账户
    private Integer sellcode;

    //采购账户
    private Integer purchasecode;

    //Hourly cost (services only) 服务每小时费用
    private Integer costPerHour;

    //是否在收银机上显示
    private SellsyBooleanEnums inPos;

    //产品分类Id
    private Integer categoryid;

    //产品是否有效
    private SellsyBooleanEnums actif=SellsyBooleanEnums.Y;

    //是否启用生态税
    private SellsyBooleanEnums useEcoTax=SellsyBooleanEnums.N;
    //生态税类型
    //private enum('inc', 'exc') ecoTaxType;

    //生态税率
    private float ecoTax;

    //增值税
    private float taxrate;

    //宽度
    private float width;

    //深度
    private float deepth;

    //长度
    private float length;

    //高度
    private float height;

    //重量
    private float weight;

    //每包产品数
    private float packing;
}
