package com.redescooter.ses.web.ros.service.restproductionorder;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.consignorder.ConsignOrderDetailResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.consignorder.ConsignOrderListEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.consignorder.ConsignOrderListResult;

import java.util.Map;

/**
 *  @author: alex
 *  @Date: 2020/10/22 13:26
 *  @version：V ROS 1.8.3
 *  @Description:
 */
public interface ConsignOrderService {
    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/22 13:29
     * @Param: enter
     * @Return: Map
     * @desc: 出库单产品类型统计
     */
    Map<String, Integer> countByType(GeneralEnter enter);

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/22 13:42
     * @Param: enter
     * @Return: Map
     * @desc: 状态列表
     */
    Map<String, Integer> statusList(GeneralEnter enter);

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/22 13:59
     * @Param: enter
     * @Return: ConsignOrderListResult
     * @desc: 出库单列表
     */
    PageResult<ConsignOrderListResult> list(ConsignOrderListEnter enter);

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/22 14:02
     * @Param: enter
     * @Return: ConsignOrderDetailResult
     * @desc: 详情
     */
    ConsignOrderDetailResult detail(IdEnter enter);

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/22 14:04
     * @Param: enter
     * @Return: GeneralResult
     * @desc: 委托单签收
     */
    GeneralResult signFor(IdEnter enter);
}
