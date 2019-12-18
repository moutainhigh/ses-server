package com.redescooter.ses.web.ros.dao;

import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.web.ros.vo.CustomerListByPageEnter;
import com.redescooter.ses.web.ros.vo.CustomerListByPageResult;

import java.util.List;

/**
 * @ClassName:CustomerProServiceMapper
 * @description: CustomerProServiceMapper
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/18 20:39
 */
public interface CustomerProServiceMapper {
    /**
     * @desc: 统计客户状态
     * @param: void
     * @return: map
     * @auther: alex
     * @date: 2019/12/18 20:41
     * @Version: CRM 1.3 进销存
     */
    List<CountByStatusResult> countByCustomerStatus();

    /**
     * @desc: 客户数量统计
     * @param: entre
     * @return:  int
     * @auther: alex
     * @date: 2019/12/18 20:55
     * @Version: ROs 1.0
     */
    int queryCustomerListCount(CustomerListByPageEnter enter);

    /**
     * @desc: 客户列表
     * @param: enter
     * @return: List<CustomerListByPageResult>
     * @auther: alex
     * @date: 2019/12/18 20:59
     * @Version: ROS 1.0
     */
    List<CustomerListByPageResult> queryCustomerList(CustomerListByPageEnter enter);

    /**
     * @desc:
     * @param: enter
     * @return: List<CountByStatusResult>
     * @auther: alex
     * @date: 2019/12/18 21:29
     * @Version: ROS 1.0
     */
    List<CountByStatusResult> countByCustomerAccountStatus();

}
