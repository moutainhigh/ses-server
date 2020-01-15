package com.redescooter.ses.web.delivery.service;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.delivery.vo.mobile.MobileHistroyEnter;
import com.redescooter.ses.web.delivery.vo.mobile.MobileHistroyResult;
import com.redescooter.ses.web.delivery.vo.mobile.MobileListEnter;
import com.redescooter.ses.web.delivery.vo.mobile.MobileResult;

import java.util.Map;

/**
 * @ClassName:MobileService
 * @description: MobileService
 * @author: Alex
 * @Version：1.3
 * @create: 2020/01/13 18:24
 */
public interface MobileService {
    /**
     * 状态分组
     *
     * @param enter
     * @return
     */
    Map<String, Integer> countStatus(GeneralEnter enter);

    /**
     * 列表
     *
     * @param enter
     * @return
     */
    PageResult<MobileResult> list(MobileListEnter enter);

    /**
     * 车辆详情
     *
     * @param enter
     * @return
     */
    MobileResult detail(IdEnter enter);

    /**
     * 分配记录
     *
     * @param enter
     * @return
     */
    PageResult<MobileHistroyResult> assignMobileHistroy(MobileHistroyEnter enter);
}
