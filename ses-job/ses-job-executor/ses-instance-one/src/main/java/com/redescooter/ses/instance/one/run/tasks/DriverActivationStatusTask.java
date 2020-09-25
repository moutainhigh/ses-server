package com.redescooter.ses.instance.one.run.tasks;

import com.redescooter.ses.api.foundation.job.RunDriverActivationStatusTaskExecutorServiceJob;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 6/1/2020 11:22 上午
 * @ClassName: DriverActivationStatusTask
 * @Function: TODO
 */
@Slf4j
@Component
public class DriverActivationStatusTask {

    @Reference
    private RunDriverActivationStatusTaskExecutorServiceJob runDriverActivationStatusTaskExecutorServiceJob;

    @XxlJob("driverActivationStatusTask")
    public ReturnT<String> driverActivationStatusTask(String param) throws Exception {

        // //开始时间
        // Calendar start = Calendar.getInstance();
        // JobResult jobResult = runDriverActivationStatusTaskExecutorServiceJob.DriverActivationStatusTask(new
        // GeneralEnter());
        //
        // if (jobResult.getStatus().name().equals("SUCCEED")) {
        // XxlJobLogger.log("本次[driverActivationStatusTask]任务执行完毕，等待下次执行！");
        // XxlJobLogger.log("本次任务执行完毕，耗时{}毫秒。", +(Calendar.getInstance().getTimeInMillis() - start.getTimeInMillis()));
        // //该返回值是简单返回，可根据自身进行设置
        // return new ReturnT(ReturnT.SUCCESS_CODE, "【driverActivationStatusTask】执行成功，耗时" +
        // (Calendar.getInstance().getTimeInMillis() - start.getTimeInMillis()) + "毫秒.");
        // }
        // return new ReturnT<String>(IJobHandler.FAIL.getCode(), jobResult.getErrorMessage() == null ?
        // JobDefaultError.RJOB_SYSTEM_INTERNAL_ERROR.getErrorMessage() : jobResult.getErrorMessage());

return null;
    }
}
