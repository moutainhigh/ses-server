package com.redescooter.ses.web.delivery.dao;

import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.web.delivery.vo.mobile.MobileListEnter;
import com.redescooter.ses.web.delivery.vo.mobile.MobileResult;

import java.util.List;

/**
 * @ClassName:MobileServiceMapper
 * @description: MobileServiceMapper
 * @author: Alex
 * @Version：1.3
 * @create: 2020/01/14 16:46
 */
public interface MobileServiceMapper {

    /**
     * 状态列表
     *
     * @param enter
     * @return
     */
    List<CountByStatusResult> statusByCount(GeneralEnter enter);

    /**
     * 车辆列表统计
     *
     * @param enter
     */
    Integer listCount(MobileListEnter enter);

    /**
     * 车辆列表数据
     *
     * @param enter
     * @return
     */
    List<MobileResult> list(MobileListEnter enter);

    /**
     * 车辆详情
     *
     * @param enter
     * @return
     */
    MobileResult detail(IdEnter enter);
}
