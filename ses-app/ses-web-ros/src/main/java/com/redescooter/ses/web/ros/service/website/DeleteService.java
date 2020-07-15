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

    /**
     * 删除 联系我们询价单
     * @param enter
     * @return
     */
    GeneralResult deleteInquiry(StorageEamilEnter enter);

}
