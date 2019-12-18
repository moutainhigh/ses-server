package com.redescooter.ses.web.ros.service;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.ros.vo.CustomerListByPageEnter;
import com.redescooter.ses.web.ros.vo.CustomerListByPageResult;

import java.util.Map;

/**
 * @ClassName:CustomerService
 * @description: CustomerService
 * @author: Alex
 * @Version：1.0
 * @create: 2019/12/18 10:06
 */
public interface CustomerProService {
    /**
     * @desc: 客户状态分类
     * @param: enter
     * @return: countByCustomerStatus
     * @auther: alex
     * @date: 2019/12/18 11:17
     * @Version: ros 1.0
     */
    Map<String, Integer> countByCustomerStatus(GeneralEnter enter);

    /**
     * @desc: 客户列表分页
     * @param: enter
     * @return: CustomerListByPageResult
     * @auther: alex
     * @date: 2019/12/18 11:51
     * @Version: CRM 1.3 进销存
     */
    CustomerListByPageResult customerListByPage(CustomerListByPageEnter enter);
}
