package com.redescooter.ses.web.ros.service.sellsy;

import com.redescooter.ses.web.ros.vo.sellsy.enter.SellsyExecutionEnter;
import com.redescooter.ses.web.ros.vo.sellsy.result.SellsyGeneralResult;

import java.util.List;

/**
 * @ClassName:SellsyService
 * @description: SellsyService
 * @author: Alex
 * @Version：1.3
 * @create: 2020/08/18 19:38
 */
public interface SellsyService<T> {
    
    /**
     * sellsy 具体执行器
     *
     * @param enter
     * @return
     */
    public SellsyGeneralResult sellsyExecution(SellsyExecutionEnter enter);

    /**
     * extractResponseList 会抛出异常所以进行统一的格式处理
     * 
     * @param sellsyGeneralResult
     * @param t
     * @return
     */
    public List<T> jsonArrayFormatting(SellsyGeneralResult sellsyGeneralResult, T t);

}
