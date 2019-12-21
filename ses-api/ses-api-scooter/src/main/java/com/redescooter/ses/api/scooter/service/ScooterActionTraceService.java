package com.redescooter.ses.api.scooter.service;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.scooter.vo.QueryScooterActionTracesEnter;
import com.redescooter.ses.api.scooter.vo.QueryScooterActionTracesResult;
import com.redescooter.ses.api.scooter.vo.SaveScooterActionTraceEnter;

/**
 * @description: ScooterActionTraceService
 * @author: Darren
 * @create: 2019/02/20 10:30
 */
public interface ScooterActionTraceService {

    /**
     * scooter 操作日志
     *
     * @param enter
     * @return
     */
    GeneralResult saveScooterActionTrace(SaveScooterActionTraceEnter enter);

    /**
     * scooter 操作日志
     *
     * @param enter
     * @return
     */
    PageResult<QueryScooterActionTracesResult> queryScooterActionTraces(QueryScooterActionTracesEnter enter);

}
