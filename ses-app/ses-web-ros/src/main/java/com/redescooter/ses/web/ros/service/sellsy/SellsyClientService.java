package com.redescooter.ses.web.ros.service.sellsy;
import com.redescooter.ses.web.ros.vo.sellsy.enter.client.SellsyCreateClientEnter;
import com.redescooter.ses.web.ros.vo.sellsy.enter.client.SellsyQueryClientOneEnter;
import com.redescooter.ses.web.ros.vo.sellsy.result.client.SellsyClientResult;
import com.redescooter.ses.web.ros.vo.sellsy.result.document.SellsyIdResult;

import java.util.List;

/**
 * @ClassName:SellsyClientService
 * @description: SellsyClientService
 * @author: Alex
 * @Version：1.3
 * @create: 2020/08/26 14:42
 */
public interface SellsyClientService {
    /**
     * 查询客户列表
     *
     *
     */
    public List<SellsyClientResult> queryClientList();
    
    /**
     * 客户查询
     *
     * @param enter
     * @return
     */
    public SellsyClientResult queryClientById(SellsyQueryClientOneEnter enter);
    
    /**
     * 客户创建
     *
     * @param enter
     */
    public SellsyIdResult createClient(SellsyCreateClientEnter enter);
}
