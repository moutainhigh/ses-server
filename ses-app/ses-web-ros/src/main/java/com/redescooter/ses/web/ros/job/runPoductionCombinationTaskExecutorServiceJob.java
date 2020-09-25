package com.redescooter.ses.web.ros.job;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.jiguang.JobResult;

public interface runPoductionCombinationTaskExecutorServiceJob {

    /**
     * 组合批量发布
     * 
     * @param enter
     * @return
     */
    public JobResult poductionCombinationTask(GeneralEnter enter);

}
