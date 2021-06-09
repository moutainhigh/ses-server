package com.redescooter.ses.web.ros.service.delete;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;

/**
 * @Description
 * @Author Chris
 * @Date 2021/6/9 11:46
 */
public interface DeleteService {

    /**
     * 删除客户对应关系
     * @param idEnter
     * @return
     */
    GeneralResult deleteCustomer(IdEnter idEnter);






}
