package com.redescooter.ses.web.ros.service.sellsy;

import com.redescooter.ses.web.ros.vo.sellsy.enter.SellsyExecutionEnter;
import com.redescooter.ses.web.ros.vo.sellsy.result.SellsyGeneralResult;
import com.sellsy.coreConnector.SellsySpringRestExecutor;

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
     * @param method
     * @param params
     * @return
     */
    public SellsyGeneralResult sellsyExecution(SellsyExecutionEnter enter);
}
