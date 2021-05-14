package com.redescooter.ses.web.ros.service.codebase;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.StringEnter;
import com.redescooter.ses.web.ros.vo.codebase.RSNDetailResult;
import com.redescooter.ses.web.ros.vo.codebase.RSNListEnter;
import com.redescooter.ses.web.ros.vo.codebase.RSNListResult;

/**
 * @Description
 * @Author Chris
 * @Date 2021/5/14 9:48
 */
public interface RSNService {

    /**
     * 列表
     */
    PageResult<RSNListResult> getList(RSNListEnter enter);

    /**
     * 详情
     */
    RSNDetailResult getDetail(StringEnter enter);

    /**
     * 导出
     */
    GeneralResult export(RSNListEnter enter);

}
