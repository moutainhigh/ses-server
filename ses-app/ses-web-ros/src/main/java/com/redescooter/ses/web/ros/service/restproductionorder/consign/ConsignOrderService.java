package com.redescooter.ses.web.ros.service.restproductionorder.consign;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.AssociatedOrderResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.consignorder.ConsignOrderDetailResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.consignorder.ConsignOrderListEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.consignorder.ConsignOrderListResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.consignorder.SaveConsignEnter;

import java.util.List;
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
    Map<Integer, Integer> countByType(GeneralEnter enter);

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/22 13:42
     * @Param: enter
     * @Return: Map
     * @desc: 状态列表
     */
    Map<Integer, String> statusList(GeneralEnter enter);

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
     * @Date: 2020/10/29 13:46
     * @Param: enter
     * @Return: AssociatedOrderResult
     * @desc: 关联订单
     */
    List<AssociatedOrderResult> associatedOrderList(IdEnter enter);

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/22 14:04
     * @Param: enter
     * @Return: GeneralResult
     * @desc: 委托单签收
     */
    GeneralResult signFor(IdEnter enter);

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/26 16:54
     * @Param: enter
     * @Return: GeneralResult
     * @desc: 保存出库单
     */
    GeneralResult save(SaveConsignEnter enter);


    /**
     * @Author Aleks
     * @Description  状态变待签收
     * @Date  2020/11/5 11:27
     * @Param [enter]
     * @return
     **/
    GeneralResult waitSign(IdEnter enter);
}
