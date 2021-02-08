package com.redescooter.ses.service.foundation.job.impl;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.jiguang.JobResult;
import com.redescooter.ses.api.foundation.job.RunSendMailTaskExecutorServiceJob;
import com.redescooter.ses.api.foundation.service.MailMultiTaskService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;

import java.util.Calendar;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 14/10/2019 11:52 上午
 * @ClassName: RunSendMailTaskExecutorServiceJobImpl
 * @Function: TODO
 */
@Slf4j
@Service
@Component
public class RunSendMailTaskExecutorServiceJobImpl implements RunSendMailTaskExecutorServiceJob {

    @DubboReference
    private MailMultiTaskService mailMultiTaskService;

    /**
     * @param enter
     * @return
     */
    @Override
    public JobResult sendMailTask(GeneralEnter enter) {
        //开始时间
        Calendar start = Calendar.getInstance();
        log.info("本次sendMailTask任务开始执行");
        mailMultiTaskService.runAllTask();
        log.info("本次任务执行完毕，耗时：" + (Calendar.getInstance().getTimeInMillis() - start.getTimeInMillis()) + "毫秒。");
        return JobResult.success();
    }
}
