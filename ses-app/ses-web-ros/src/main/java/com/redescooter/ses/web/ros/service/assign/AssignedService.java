package com.redescooter.ses.web.ros.service.assign;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.vo.assign.done.enter.AssignedListEnter;
import com.redescooter.ses.web.ros.vo.assign.done.result.AssignedDetailResult;
import com.redescooter.ses.web.ros.vo.assign.done.result.AssignedListResult;

/**
 * @Description
 * @Author Chris
 * @Date 2020/12/27 16:37
 */
public interface AssignedService {

    /**
     * 已分配列表
     */
    PageResult<AssignedListResult> getAssignedList(AssignedListEnter enter);

    /**
     * 已分配列表查看详情
     */
    AssignedDetailResult getAssignedDetail(IdEnter enter);

    /**
     * 已分配列表生成PDF
     */
    GeneralResult generatePDF(IdEnter enter);

}
