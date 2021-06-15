package com.redescooter.ses.service.mobile.c.job.impl;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.jiguang.JobResult;
import com.redescooter.ses.api.mobile.c.job.RunDriverRideTaskExecutorServiceJob;
import com.redescooter.ses.api.mobile.c.service.RideStatCService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.log4j.Log4j2;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;

/**
 * @Description
 * @Author Chris
 * @Date 2021/6/3 11:53
 */
@Log4j2
@DubboService
public class RunDriverRideTaskExecutorServiceJobImpl implements RunDriverRideTaskExecutorServiceJob {

    @Autowired
    private RideStatCService rideStatCService;

    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public JobResult countDriverRideTasks(GeneralEnter enter) {
        Calendar start = Calendar.getInstance();
        log.info("本次countDelivery任务开始执行");

        // 保存司机骑行数据
        //rideStatCService.saveDriverRideStat(saveList);
        //// 车辆统计数据
        //rideStatCService.saveScooterRideStat(saveList);

        log.info("本次任务执行完毕，耗时：" + (Calendar.getInstance().getTimeInMillis() - start.getTimeInMillis()) + "毫秒。");
        return JobResult.success();
    }

}
