package com.redescooter.ses.api.hub.service.customer;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.hub.vo.QueryDriverScooterResult;

/**
 * @ClassName:ScooterService
 * @description: ScooterService
 * @author: Alex
 * @Version：1.3
 * @create: 2020/01/01 19:13
 */
public interface CusotmerScooterService {
    /**
     * 查询车辆分配信息
     *
     * @param enter
     * @return
     */
    QueryDriverScooterResult queryDriverScooter(GeneralEnter enter);
}
