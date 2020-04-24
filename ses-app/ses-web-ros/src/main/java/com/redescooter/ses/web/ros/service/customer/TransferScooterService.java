package com.redescooter.ses.web.ros.service.customer;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdsEnter;
import com.redescooter.ses.web.ros.vo.customer.TransferScooterEnter;

/**
 * @ClassName:TransferScooterService
 * @description: TransferScooterService
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/24 16:32
 */
public interface TransferScooterService {

    /**
     * 车辆分配
     *
     * @param enter
     * @return
     */
    GeneralResult transferScooter(TransferScooterEnter enter);

}
