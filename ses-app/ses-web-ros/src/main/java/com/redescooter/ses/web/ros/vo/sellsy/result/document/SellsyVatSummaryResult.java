package com.redescooter.ses.web.ros.vo.sellsy.result.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @ClassName:SellsyVatSummaryResult
 * @description: SellsyVatSummaryResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/08/25 16:20
 */
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class SellsyVatSummaryResult {
    
    private String label;
    
    private String invoice;
    
    private int deposit;
    
    private String due;
}
