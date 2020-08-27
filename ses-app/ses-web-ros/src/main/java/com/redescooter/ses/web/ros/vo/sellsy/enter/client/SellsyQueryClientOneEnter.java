package com.redescooter.ses.web.ros.vo.sellsy.enter.client;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @ClassName:SellsyQueryCleintOneEnter
 * @description: SellsyQueryCleintOneEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/08/25 12:10
 */
@Data //生成getter,setter等函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class SellsyQueryClientOneEnter {

    private int clientid;
    
    public SellsyQueryClientOneEnter(int clientid) {
        this.clientid = clientid;
    }
}
