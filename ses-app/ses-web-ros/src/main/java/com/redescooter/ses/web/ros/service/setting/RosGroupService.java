package com.redescooter.ses.web.ros.service.setting;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.foundation.vo.setting.GroupResult;
import com.redescooter.ses.web.ros.vo.setting.RosGroupListEnter;
import com.redescooter.ses.web.ros.vo.setting.RosSaveGroupEnter;

import javax.servlet.http.HttpServletResponse;

public interface RosGroupService {
    /**
     * 分组列表
     * @param enter
     * @return
     */
    PageResult<GroupResult> list(RosGroupListEnter enter);

    /**
     * 详情
     * @param enter
     * @return
     */
    GroupResult detail(IdEnter enter);

    /**
     * 保存分组
     * @param enter
     * @return
     */
    GeneralResult save(RosSaveGroupEnter enter);

    /**
     * 删除分组
     * @param enter
     * @return
     */
    GeneralResult delete(IdEnter enter);

    /**
     * 导出
     * @param
     * @return
     */
    GeneralResult export(String id, HttpServletResponse response);
}
