package com.redescooter.ses.web.ros.service.assign;

import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.vo.assign.doing.result.AssigningDetailResult;
import com.redescooter.ses.web.ros.vo.assign.doing.result.AssigningListResult;
import com.redescooter.ses.web.ros.vo.assign.tobe.enter.CustomerIdEnter;
import com.redescooter.ses.web.ros.vo.assign.tobe.enter.ToBeAssignListEnter;

/**
 * @Description
 * @Author Chris
 * @Date 2021/5/11 12:49
 */
public interface AssigningService {

    /**
     * 处理中列表
     */
    PageResult<AssigningListResult> getList(ToBeAssignListEnter enter);

    /**
     * 处理中查看详情
     */
    AssigningDetailResult getDetail(CustomerIdEnter enter);

}
