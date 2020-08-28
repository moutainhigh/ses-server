package com.redescooter.ses.web.ros.vo.sellsy.enter.document;

import com.redescooter.ses.web.ros.enums.sellsy.SellsyWeightUnitEnums;
import lombok.*;

import java.sql.Timestamp;

/**
 * @ClassName:SellsyDocumentShippingEnter
 * @description: 订单参数
 * @author: Alex
 * @Version：1.3
 * @create: 2020/08/26 11:26
 */
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class SellsyDocumentShippingEnter {

    //包裹数量 默认为0
    // @NotNull(code = ThirdValidationExceptionCode.SELLSY_DOCUMENT_PRODUCT_QTY_IS_EMPTY,message = "产品数量为空")
    private int nbParcels;
    
    //总重量  默认为0
    // @NotNull(code = ThirdValidationExceptionCode.SELLSY_DOCUMENT_PRODUCT_WIGHT_IS_EMPTY,message = "产品重量为空")
    private float weight;
    
    //重量单位 'g', 'kg
    private SellsyWeightUnitEnums weightUnit;
    
    // 体积
    private float volume;
    
    // 物流单号
    private String trackingNumber;
    
    // 物流查询网页地址
    private String trackingUrl;
    
    // 预计送达时间
    private Timestamp date;
}
