package com.redescooter.ses.api.mobile.c.job;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.jiguang.JobResult;

/**
 * @Description
 * @Author Chris
 * @Date 2021/6/3 11:53
 */
public interface RunDriverRideTaskExecutorServiceJob {

    JobResult countDriverRideTasks(GeneralEnter enter);

}
