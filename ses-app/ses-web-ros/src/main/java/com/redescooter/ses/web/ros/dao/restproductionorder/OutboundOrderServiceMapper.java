package com.redescooter.ses.web.ros.dao.restproductionorder;

import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.outboundorder.OutboundOrderDetailResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.outboundorder.OutboundOrderListEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.outboundorder.OutboundOrderListResult;

import java.util.List;

public interface OutboundOrderServiceMapper {
    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/28 15:25
     * @Param: enter
     * @Return: CountByStatusResult
     * @desc: 状态分组
     */
    List<CountByStatusResult> countByType(GeneralEnter enter);

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/28 15:27
     * @Param: enter
     * @Return: countByType
     * @desc: 列表统计
     */
    int listCount(OutboundOrderListEnter enter);

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/28 15:28
     * @Param: enter
     * @Return: OutboundOrderListResult
     * @desc: 列表
     */
    List<OutboundOrderListResult> list(OutboundOrderListEnter enter);

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/28 15:29
     * @Param: enter
     * @Return: OutboundOrderDetailResult
     * @desc: 详情
     */
    OutboundOrderDetailResult detail(IdEnter enter);

}
