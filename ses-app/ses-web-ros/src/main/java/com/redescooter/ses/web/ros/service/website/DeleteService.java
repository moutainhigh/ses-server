package com.redescooter.ses.web.ros.service.website;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.web.ros.vo.website.StorageEamilEnter;

/**
 * @ClassName:DeleteService
 * @description: DeleteService
 * @author: Alex
 * @Version：1.3
 * @create: 2020/07/07 13:11
 */

public interface DeleteService {
    /**
     * 删除客户
     *
     * @param email
     * @return
     */
    GeneralResult deleteCustomer(StorageEamilEnter email);
}
