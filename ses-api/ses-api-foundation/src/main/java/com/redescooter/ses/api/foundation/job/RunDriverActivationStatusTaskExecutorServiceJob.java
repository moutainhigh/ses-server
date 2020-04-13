package com.redescooter.ses.api.foundation.job;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.jiguang.JobResult;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 6/1/2020 9:52 上午
 * @ClassName: RunDriverActivationStatusTaskExecutorServiceJob
 * @Function: TODO  RunDriverActivationStatusTaskExecutorServiceJob
 */
public interface RunDriverActivationStatusTaskExecutorServiceJob {
    /**
     * @param enter
     * @return
     */
    JobResult DriverActivationStatusTask(GeneralEnter enter);
}
