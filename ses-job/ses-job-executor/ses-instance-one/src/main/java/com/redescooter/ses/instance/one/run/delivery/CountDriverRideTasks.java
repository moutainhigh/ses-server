package com.redescooter.ses.instance.one.run.delivery;

import com.redescooter.ses.api.common.enums.job.JobDefaultError;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.jiguang.JobResult;
import com.redescooter.ses.api.mobile.c.job.RunDriverRideTaskExecutorServiceJob;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.log.XxlJobLogger;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Component;

import java.util.Calendar;

/**
 * @Description
 * @Author Chris
 * @Date 2021/6/3 11:51
 */
@Slf4j
@Component
public class CountDriverRideTasks {

    @DubboReference
    private RunDriverRideTaskExecutorServiceJob runDriverRideTaskExecutorServiceJob;

    @XxlJob("countDriverRideTasks")
    public ReturnT<String> countDriverRideTasks(String param) throws Exception {
        //开始时间
        Calendar start = Calendar.getInstance();
        JobResult jobResult = runDriverRideTaskExecutorServiceJob.countDriverRideTasks(new GeneralEnter());

        if (jobResult.getStatus().name().equals("SUCCEED")) {
            XxlJobLogger.log("本次[countDriverRideTasks]任务执行完毕，等待下次执行！");
            XxlJobLogger.log("本次任务执行完毕，耗时{}毫秒。", +(Calendar.getInstance().getTimeInMillis() - start.getTimeInMillis()));
            //该返回值是简单返回，可根据自身进行设置
            return new ReturnT(ReturnT.SUCCESS_CODE, "【countDriverRideTasks】执行成功，耗时" + (Calendar.getInstance().getTimeInMillis() - start.getTimeInMillis()) + "毫秒.");
        }
        return new ReturnT<>(IJobHandler.FAIL.getCode(), jobResult.getErrorMessage() == null ? JobDefaultError.RJOB_SYSTEM_INTERNAL_ERROR.getErrorMessage() : jobResult.getErrorMessage());
    }


}
