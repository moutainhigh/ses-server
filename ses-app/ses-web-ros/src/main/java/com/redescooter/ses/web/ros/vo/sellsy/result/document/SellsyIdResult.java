package com.redescooter.ses.web.ros.vo.sellsy.result.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @ClassName:SellsyIdResult
 * @description: SellsyIdResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/08/25 10:26
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class SellsyIdResult {

    private int id;
    
    public SellsyIdResult() {
    }
    
    
    public SellsyIdResult(int id) {
        this.id = id;
    }
}
