package com.redescooter.ses.instance.one.run.scooter;

import com.redescooter.ses.api.common.enums.job.JobDefaultError;
import com.redescooter.ses.api.common.vo.jiguang.JobResult;
import com.redescooter.ses.api.scooter.job.RunScooterTaskExecutorJobService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.log.XxlJobLogger;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;

import java.util.Calendar;

/**
 * 车载平板升级状态同步定时任务 -- 每2分钟同步一次升级的状态
 * @author assert
 * @date 2020/12/27 15:04
 */
@Slf4j
@Component
public class ScooterUpdateStatusSyncTasks {

    @Reference
    private RunScooterTaskExecutorJobService runScooterTaskExecutorJobService;


    @XxlJob("scooterUpdateStatusSyncTasks")
    public ReturnT<String> scooterUpdateStatusSyncTasks(String param) throws Exception {

        //开始时间
        Calendar start = Calendar.getInstance();

        JobResult jobResult = runScooterTaskExecutorJobService.scooterUpdateStatusSync();

        if ("SUCCEED".equals(jobResult.getStatus().name())) {
            XxlJobLogger.log("本次[scooterUpdateStatusSyncTasks]任务执行完毕，等待下次执行！");
            XxlJobLogger.log("本次任务执行完毕，耗时{}毫秒。", +(Calendar.getInstance().getTimeInMillis() - start.getTimeInMillis()));
            //该返回值是简单返回，可根据自身进行设置
            return new ReturnT(ReturnT.SUCCESS_CODE, "【scooterUpdateStatusSyncTasks】执行成功，耗时" + (Calendar.getInstance().getTimeInMillis() - start.getTimeInMillis()) + "毫秒.");
        }
        return new ReturnT<String>(
                IJobHandler.FAIL.getCode(), jobResult.getErrorMessage() == null ? JobDefaultError.RJOB_SYSTEM_INTERNAL_ERROR.getErrorMessage() : jobResult.getErrorMessage()
        );
    }

}
