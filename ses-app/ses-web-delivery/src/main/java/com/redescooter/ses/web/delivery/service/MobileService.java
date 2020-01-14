package com.redescooter.ses.web.delivery.service;

import cn.hutool.db.PageResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.web.delivery.vo.mobile.MobileHistroyEnter;
import com.redescooter.ses.web.delivery.vo.mobile.MobileDetailResult;
import com.redescooter.ses.web.delivery.vo.mobile.MobileHistroyResult;
import com.redescooter.ses.web.delivery.vo.mobile.MobileListEnter;
import com.redescooter.ses.web.delivery.vo.mobile.MobileListResult;

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
    Map<String, Integer> statusByCount(GeneralEnter enter);

    /**
     * 列表
     *
     * @param enter
     * @return
     */
    MobileListResult list(MobileListEnter enter);

    /**
     * 车辆详情
     *
     * @param enter
     * @return
     */
    MobileDetailResult detail(IdEnter enter);

    /**
     * 分配记录
     *
     * @param enter
     * @return
     */
    PageResult<MobileHistroyResult> assignMobileHistroy(MobileHistroyEnter enter);

    /**
     * 维修记录
     *
     * @param enter
     * @return
     */
    PageResult<MobileHistroyResult> repairnMobileHistroy(MobileHistroyEnter enter);


}
