package com.redescooter.ses.api.hub.service.customer;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.hub.vo.QueryDriverScooterResult;
import com.redescooter.ses.api.hub.vo.HubSaveScooterEnter;

import java.util.List;

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

    /**
     * 保存车辆信息
     * @return
     */
    GeneralResult saveScooter(List<HubSaveScooterEnter> enter);

}
