package com.redescooter.ses.web.ros.service.admin;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.StringEnter;

public interface DeleteRosLoginDateService {
    /**
     * 删除和当前 邮箱相关的所有数据 包含：sysUser、customer、inquiry
     *
     * @param enter
     */
    void deleteRosLoginDate(StringEnter enter);

    /**
     * 测试分布式事务
     */
    GeneralResult testGlobalTransactional(GeneralEnter enter);

}
