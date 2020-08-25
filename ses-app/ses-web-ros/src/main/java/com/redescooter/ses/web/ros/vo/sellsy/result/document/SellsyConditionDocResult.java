package com.redescooter.ses.web.ros.vo.sellsy.result.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @ClassName:SellsyConditionDocResult
 * @description: SellsyConditionDocResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/08/25 16:13
 */
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class SellsyConditionDocResult {
    
    private String langId;
    
    private String langName;
    
    private String docLabel;
    
    private int fileId;
    
    private String fileName;
}
