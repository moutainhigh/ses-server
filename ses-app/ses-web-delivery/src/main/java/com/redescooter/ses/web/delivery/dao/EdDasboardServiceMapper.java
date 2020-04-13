package com.redescooter.ses.web.delivery.dao;

import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.delivery.vo.ScooterRideDataResult;
import com.redescooter.ses.web.delivery.vo.TopTenEnter;
import com.redescooter.ses.web.delivery.vo.TopTenResult;

import java.util.List;

/**
 *  @author: alex
 *  @Date: 2020/2/3 17:00
 *  @version：V 1.2
 *  @Description: 仪表盘 数据统计
 */

public interface EdDasboardServiceMapper {

    /**
    * @Description
    * @Author:  AlexLi
    * @Date:   2020/2/3 17:11
    * @Param:  enter
    * @Return: List<CountByStatusResult>
    * @desc: 今日订单统计
    */
    List<CountByStatusResult> todayCountByStatus(GeneralEnter enter);
    /**
    * @Description
    * @Author:  AlexLi
    * @Date:   2020/2/3 17:11
    * @Param:  enter
    * @Return: TopTenResult
    * @desc: 排行榜
    */
    List<TopTenResult> topTen(TopTenEnter enter);
    /**
    * @Description
    * @Author:  AlexLi
    * @Date:   2020/2/4 12:06
    * @Param:  enter
    * @Return: ScooterRideDataResult
    * @desc: 仪表盘车辆统计数据
    */
    ScooterRideDataResult scooterRideData(GeneralEnter enter);
}
