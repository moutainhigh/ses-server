package com.redescooter.ses.mobile.rps.service.restproductionorder.trace;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.optrace.ListByBussIdEnter;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.optrace.OpTraceResult;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.optrace.SaveOpTraceEnter;

import java.util.List;

public interface ProductionOrderTraceService {
    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/27 14:25
     * @Param: enter
     * @Return:
     * @desc:
     */
    List<OpTraceResult> list(GeneralEnter enter);

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/27 14:48
     * @Param: enter
     * @Return: OpeOpTrace
     * @desc: 根据业务查询操作记录
     */
    List<OpTraceResult> listByBussId(ListByBussIdEnter enter);

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/27 14:49
     * @Param: enter
     * @Return: OpeOpTrace
     * @desc: 详情
     */
    OpTraceResult detail(IdEnter enter);

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/27 14:49
     * @Param: enter
     * @Return: OpeOpTrace
     * @desc: 保存操作记录
     */
    GeneralResult save(SaveOpTraceEnter enter);

}
