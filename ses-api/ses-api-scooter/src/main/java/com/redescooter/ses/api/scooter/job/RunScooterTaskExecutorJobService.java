package com.redescooter.ses.api.scooter.job;

import com.redescooter.ses.api.common.vo.jiguang.JobResult;

/**
 * 车辆相关定时任务业务接口
 * @author assert
 * @date 2020/12/27 15:22
 */
public interface RunScooterTaskExecutorJobService {

    /**
     * 车载平板升级失败重试
     * @param
     * @return com.redescooter.ses.api.common.vo.jiguang.JobResult
     * @author assert
     * @date 2020/12/27
    */
    JobResult scooterUpdateFailRetry();

    /**
     * 车载平板升级状态同步
     * @param
     * @return com.redescooter.ses.api.common.vo.jiguang.JobResult
     * @author assert
     * @date 2020/12/27
    */
    JobResult scooterUpdateStatusSync();

}
