package com.redescooter.ses.web.ros.service;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.web.ros.vo.CreateCustomerAccountEnter;
import com.redescooter.ses.web.ros.vo.CustomerAccountListEnter;
import com.redescooter.ses.web.ros.vo.CustomerAccountListResult;
import com.redescooter.ses.web.ros.vo.CustomerDetailResult;
import com.redescooter.ses.web.ros.vo.CustomerListByPageEnter;
import com.redescooter.ses.web.ros.vo.CustomerListByPageResult;
import com.redescooter.ses.web.ros.vo.DeleteCustomerEnter;
import com.redescooter.ses.web.ros.vo.SaveCustomerEnter;

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

    /**
     * @desc: saveCustomer
     * @param: enter
      @return: GeneralResult
      @auther: alex
     * @date: 2019/12/18 16:29
     * @Version: ROS 1.0
     */
    GeneralResult saveCustomer(SaveCustomerEnter enter);

    /**
     * @desc: 潜在客户转正式客户
     * @param: enter
     *@return: GeneralResult
      @auther: alex
     * @date: 2019/12/18 16:31
     * @Version: ROS 1.0
     */
    GeneralResult convertCustomer(IdEnter enter);

    /**
     * @desc: 删除客户
     * @param: enter
     *@return: GeneralResult
      @auther: alex
     * @date: 2019/12/18 16:35
     * @Version: ROS 1.0
     */
    GeneralResult deleteCustomer(DeleteCustomerEnter enter);

    /**
     * @desc: 客户详情
     * @param: enter
     * @return: GeneralResult
     * @auther: alex
     * @date: 2019/12/18 16:36
     * @Version: ROS 1.0
     */
    CustomerDetailResult customerDetail(IdEnter enter);

    /**
     * @desc: 客户开通账户
     * @param: enter
     *@return: GeneralResult
      *@auther: alex
     * @date: 2019/12/18 17:39
     * @Version: ROS 1.0
     */
    GeneralResult createCustomerAccount(CreateCustomerAccountEnter enter);

    /**
     * @desc: 客户账户列表状态
     * @param: enter
     *@return: enter
     *@auther: alex
     * @date: 2019/12/18 16:43
     * @Version: ROS 1.0
     */
    Map<String, Integer> countByCustomerAccountStatus(GeneralEnter enter);

    /**
     * @desc: 客户账户列表
     * @param: enter
     *@return: CustomerAccountListResult
      *@auther: alex
     * @date: 2019/12/18 17:33
     * @Version: ROS 1.0
     */
    CustomerAccountListResult customerAccountList(CustomerAccountListEnter enter);
}
