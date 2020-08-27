package com.redescooter.ses.web.ros.vo.sellsy.enter.document;

import com.redescooter.ses.web.ros.vo.sellsy.enter.SellsyIdEnter;
import lombok.*;

import java.util.Map;

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
    
    //发票属性
    private CreateDocumentAttributesEnter document;
    
    //付款周期
    private SellsyPaydateAttributesEnter paydate;
    
    // 产品信息
    private SellsyDocumentShippingEnter shipping;
    
    //货币信息
    private SellsyNumFormatEnter num_format;
    
    //客户地址 ID
    private SellsyIdEnter thirdaddress;
    
    //交付客户地址 ID
    private SellsyIdEnter shipaddress;
    
    //行信息
    private Map<String,SellsyRowEnter> row;
}

