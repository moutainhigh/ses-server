package com.redescooter.ses.api.hub.job;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.jiguang.JobResult;

public interface RunPoductionTaskExecutorServiceJob {

    /**
     * 组合批量发布
     *
     * @param enter
     * @return
     */
    public JobResult poductionCombinationTask(GeneralEnter enter);

    /**
     * 车辆批量发布
     *
     * @param enter
     * @return
     */
    public JobResult productionScooterTask(GeneralEnter enter);

}
