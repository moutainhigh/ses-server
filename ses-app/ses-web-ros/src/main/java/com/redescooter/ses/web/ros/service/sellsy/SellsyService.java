package com.redescooter.ses.web.ros.service.sellsy;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.ros.vo.sellsy.enter.SellsyCreateClientEnter;
import com.redescooter.ses.web.ros.vo.sellsy.enter.SellsyExecutionEnter;
import com.redescooter.ses.web.ros.vo.sellsy.result.SellsyClientResult;
import com.redescooter.ses.web.ros.vo.sellsy.result.SellsyGeneralResult;

import java.util.List;

/**
 * @ClassName:SellsyService
 * @description: SellsyService
 * @author: Alex
 * @Version：1.3
 * @create: 2020/08/18 19:38
 */
public interface SellsyService {
    
    /**
     * sellsy 具体执行器
     *
     * @param enter
     * @return
     */
    public SellsyGeneralResult sellsyExecution(SellsyExecutionEnter enter);
    
    /**
     * 查询客户列表
     *
     * @param enter
     */
    public List<SellsyClientResult> queryClientList(GeneralEnter enter);
    
    /**
     * 客户创建
     * @param enter
     */
    public void createClient(SellsyCreateClientEnter enter);
    
}
