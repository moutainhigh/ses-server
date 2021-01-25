package com.redescooter.ses.web.website.service;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.web.website.vo.customer.AddCustomerEnter;
import com.redescooter.ses.web.website.vo.customer.CustomerDetailsResult;
import com.redescooter.ses.web.website.vo.customer.EditSiteCustomerEnter;

/**
 * @Author jerry
 * @Date 2021/1/6 3:29 上午
 * @Description 客户服务
 **/
public interface WebSiteCustomerService {

    /**
     * 创建客户
     *
     * @param enter
     * @return
     */
    GeneralResult addCustomer(AddCustomerEnter enter);

    /**
     * 获取客户详情
     *
     * @param enter
     */
    CustomerDetailsResult getCustomerDetails(GeneralEnter enter);

    /**
     * 客户编辑
     *
     * @param enter
     * @return
     */
    GeneralResult editCustomer(EditSiteCustomerEnter enter);
}
