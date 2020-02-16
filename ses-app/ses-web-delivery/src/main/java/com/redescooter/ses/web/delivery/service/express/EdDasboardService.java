package com.redescooter.ses.web.delivery.service.express;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.delivery.vo.ScooterRideDataResult;
import com.redescooter.ses.web.delivery.vo.TopTenEnter;
import com.redescooter.ses.web.delivery.vo.TopTenResult;
import com.redescooter.ses.web.delivery.vo.edorder.ExpressOrderMapResult;

import java.util.List;
import java.util.Map;

/**
 *  @author: alex
 *  @Date: 2020/2/3 16:47
 *  @version：V 1.2
 *  @Description: 仪表盘统计
 */
public interface EdDasboardService {

    /**
    * @Description
    * @Author:  AlexLi
    * @Date:   2020/2/3 16:48
    * @Param:  enter
    * @Return: Map
    * @desc: todayCountByStatus
    */
    Map<String, Integer> todayCountByStatus(GeneralEnter enter);
    /**
    * @Description
    * @Author:  AlexLi
    * @Date:   2020/2/3 16:49
    * @Param:  enter
    * @Return: TopTenResult
    * @desc:  司机排行榜
    */
    List<TopTenResult> topTen(TopTenEnter enter);
    /**
    * @Description
    * @Author:  AlexLi
    * @Date:   2020/2/3 16:51
    * @Param:  enter
    * @Return: ScooterRideDataResult
    * @desc: 车辆骑行数据
    */
    ScooterRideDataResult scooterRideData(GeneralEnter enter);
    /**
    * @Description
    * @Author:  AlexLi
    * @Date:   2020/2/3 16:52
    * @Param:  enter
    * @Return: ExpressOrderMapResult
    * @desc: 仪表盘地图接口
    */
    ExpressOrderMapResult map(GeneralEnter enter);
}
