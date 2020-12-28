package com.redescooter.ses.web.ros.service.assign;

import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.vo.assign.tobe.enter.ToBeAssignListEnter;
import com.redescooter.ses.web.ros.vo.assign.tobe.result.ToBeAssignListResult;

/**
 * @Description
 * @Author Chris
 * @Date 2020/12/27 14:50
 */
public interface ToBeAssignService {

    /**
     * 待分配列表
     */
    PageResult<ToBeAssignListResult> getToBeAssignList(ToBeAssignListEnter enter);




}
