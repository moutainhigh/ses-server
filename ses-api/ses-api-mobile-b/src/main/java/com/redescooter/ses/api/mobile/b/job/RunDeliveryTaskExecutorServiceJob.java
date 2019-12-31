package com.redescooter.ses.api.mobile.b.job;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.jiguang.JobResult;

/**
 * @ClassName:DeliveryJob
 * @description: DeliveryJob
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/31 13:21
 */
public interface RunDeliveryTaskExecutorServiceJob {

    /**
     * 订单骑行数据 数据
     *
     * @param enter
     * @return
     */
    JobResult countDelivery(GeneralEnter enter);
}
