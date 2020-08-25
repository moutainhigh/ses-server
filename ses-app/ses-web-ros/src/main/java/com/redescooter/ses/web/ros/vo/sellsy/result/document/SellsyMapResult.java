package com.redescooter.ses.web.ros.vo.sellsy.result.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.formula.functions.Rows;

/**
 * @ClassName:SellsyMapResult
 * @description: SellsyMapResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/08/25 16:22
 */
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class SellsyMapResult {
    
    private String docid;
    
    private String id;
    
    private Rows rows;
}
