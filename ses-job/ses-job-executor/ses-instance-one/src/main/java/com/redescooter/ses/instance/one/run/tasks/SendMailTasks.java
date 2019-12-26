package com.redescooter.ses.instance.one.run.tasks;

import com.redescooter.ses.api.common.enums.job.JobDefaultError;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.jiguang.JobResult;
import com.redescooter.ses.api.foundation.job.RunSendMailTaskExecutorServiceJob;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.log.XxlJobLogger;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;

import java.util.Calendar;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 26/12/2019 11:03 上午
 * @ClassName: SendMailTasks
 * @Function: TODO
 */
@Slf4j
@Component
public class SendMailTasks {

    @Reference
    private RunSendMailTaskExecutorServiceJob runSendMailTaskExecutorServiceJob;

    @XxlJob("sendMails")
    public ReturnT<String> sendMails(String param) throws Exception {

        //开始时间
        Calendar start = Calendar.getInstance();
        JobResult jobResult = runSendMailTaskExecutorServiceJob.sendMailTask(new GeneralEnter());

        if (jobResult.getStatus().name().equals("SUCCEED")) {
            XxlJobLogger.log("本次[SendMailTaskExecutor]任务执行完毕，等待下次执行！");
            XxlJobLogger.log("本次任务执行完毕，耗时{}毫秒。", +(Calendar.getInstance().getTimeInMillis() - start.getTimeInMillis()));
            //该返回值是简单返回，可根据自身进行设置
            return new ReturnT(ReturnT.SUCCESS_CODE, "【SendMailTaskExecutor】执行成功，耗时" + (Calendar.getInstance().getTimeInMillis() - start.getTimeInMillis()) + "毫秒.");
        }
        return new ReturnT<String>(IJobHandler.FAIL.getCode(), jobResult.getErrorMessage() == null ? JobDefaultError.RJOB_SYSTEM_INTERNAL_ERROR.getErrorMessage() : jobResult.getErrorMessage());
    }
}
