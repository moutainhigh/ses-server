package com.redescooter.ses.api.hub.job;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.jiguang.JobResult;

/**
 * @ClassName:RunRtDeliveryTaskExecutorServiceJob
 * @description: RunRtDeliveryTaskExecutorServiceJob
 * @author: Alex
 * @Version：1.3
 * @create: 2020/01/16 14:56
 */
public interface RunRtDeliveryTaskExecutorServiceJob {
    /**
     * 订单超时
     *
     * @param enter
     * @return
     */
    JobResult deliveryTimeOut(GeneralEnter enter);
}
