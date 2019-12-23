package com.redescooter.ses.api.foundation.job;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.jiguang.JobResult;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 14/10/2019 11:47 上午
 * @ClassName: RunSendMailTaskExecutorServiceJob
 * @Function: TODO
 */
public interface RunSendMailTaskExecutorServiceJob {

    /**
     * @param enter
     * @return
     */
    JobResult sendMailTask(GeneralEnter enter);
}
