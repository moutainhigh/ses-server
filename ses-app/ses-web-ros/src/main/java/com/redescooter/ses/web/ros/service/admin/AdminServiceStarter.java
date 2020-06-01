package com.redescooter.ses.web.ros.service.admin;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;

/**
 * @ClassName:AdminServiceStarter
 * @description: AdminServiceStarter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/06/01 11:19
 */
public interface AdminServiceStarter {
    /**
     * 检查admin 是否存在
     *
     * @return
     */
    Boolean checkAdmin();

    /**
     * 保存admin 用户
     *
     * @return
     */
    GeneralResult saveAdmin();

    /**
     * 校验admin 的数据是否完整
     * @return
     */
    GeneralResult checkAdminDate();
}
