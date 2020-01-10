package com.redescooter.ses.api.hub.service.operation;

import com.redescooter.ses.api.common.vo.base.BaseCustomerResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;

/**
 * @ClassName:CustomerService
 * @description: CustomerService
 * @author: Alex
 * @Version：1.3
 * @create: 2020/01/10 12:01
 */
public interface CustomerService {
    /**
     * id 为 租户Id
     *
     * @param enter
     * @return
     */
    BaseCustomerResult customerInfo(IdEnter enter);
}
