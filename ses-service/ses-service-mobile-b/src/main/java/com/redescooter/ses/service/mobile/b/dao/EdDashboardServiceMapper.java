package com.redescooter.ses.service.mobile.b.dao;

import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;

import java.util.List;

public interface EdDashboardServiceMapper {
    /**
    * @Description
    * @Author:  AlexLi
    * @Date:   2020/2/14 15:56
    * @Param:  enter
    * @Return: CountByStatusResult
    * @desc:  所有订单状态统计
    */
    List<CountByStatusResult> allCountByStatus(GeneralEnter enter);

    CountByStatusResult refuseCount(GeneralEnter enter);
}
