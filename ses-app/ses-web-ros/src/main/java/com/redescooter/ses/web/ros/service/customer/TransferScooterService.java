package com.redescooter.ses.web.ros.service.customer;

import com.redescooter.ses.api.common.vo.base.PageEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.vo.customer.ScooterCustomerResult;

/**
 * @ClassName:TransferScooterService
 * @description: TransferScooterService
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/24 16:32
 */
public interface TransferScooterService {



    /**
     * 车辆用户分配信息
     *
     * @param enter
     */
    PageResult<ScooterCustomerResult> scooterCustomerResult (PageEnter enter);

}
