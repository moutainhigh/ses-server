package com.redescooter.ses.web.ros.dao.restproductionorder;

import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.consignorder.ConsignOrderDetailResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.consignorder.ConsignOrderListEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.consignorder.ConsignOrderListResult;

import java.util.List;

public interface ConsignOrderServiceMapper {
    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/29 12:50
     * @Param: enter
     * @Return: CountByStatusResult
     * @desc: 类型统计
     */
    List<CountByStatusResult> countByType(GeneralEnter enter);

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/29 12:55
     * @Param: enter
     * @Return: int
     * @desc: 列表统计
     */
    int listCount(ConsignOrderListEnter enter);

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/29 12:55
     * @Param: enter
     * @Return: ConsignOrderListResult
     * @desc: 列表
     */
    List<ConsignOrderListResult> list(ConsignOrderListEnter enter);

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/29 13:31
     * @Param: enter
     * @Return: ConsignOrderDetailResult
     * @desc: 详情
     */
    ConsignOrderDetailResult detail(IdEnter enter);
}
