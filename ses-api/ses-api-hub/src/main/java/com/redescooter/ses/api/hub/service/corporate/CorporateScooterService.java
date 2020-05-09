package com.redescooter.ses.api.hub.service.corporate;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.hub.vo.HubSaveScooterEnter;

import java.util.List;

/**
 * @ClassName:CorporateScooterService
 * @description: CorporateScooterService
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/26 11:47
 */
public interface CorporateScooterService {

    /**
     * 保存整车
     * @param enter
     * @return
     */
    GeneralResult saveScooter(List<HubSaveScooterEnter> enter);
}
