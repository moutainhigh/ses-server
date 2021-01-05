package com.redescooter.ses.web.website.service;

import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.web.website.vo.customer.AddCustomerEnter;
import com.redescooter.ses.web.website.vo.customer.CustomerDetailsResult;

/**
 * @Author jerry
 * @Date 2021/1/6 3:29 上午
 * @Description 客户服务
 **/
public interface CustomerService {
    
    /**
     * 创建客户
     *
     * @param enter
     * @return
     */
    Boolean addCustomer(AddCustomerEnter enter);

    /**
     * 获取客户详情
     *
     * @param enter
     */
    CustomerDetailsResult getCustomerDetails(IdEnter enter);
}
