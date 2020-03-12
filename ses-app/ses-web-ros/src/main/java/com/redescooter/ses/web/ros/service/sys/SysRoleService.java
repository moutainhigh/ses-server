package com.redescooter.ses.web.ros.service.sys;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.web.ros.vo.sys.role.SaveRoleEnter;

/**
 * @ClassName SysRoleService
 * @Author Jerry
 * @date 2020/03/12 14:10
 * @Description:
 */
public interface SysRoleService {

    /**
     * 岗位创建
     *
     * @param enter
     * @return
     */
    GeneralResult save(SaveRoleEnter enter);
}
