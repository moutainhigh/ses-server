package com.redescooter.ses.web.ros.service;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.vo.account.OpenAccountEnter;
import com.redescooter.ses.web.ros.vo.customer.*;

import java.util.Map;

/**
 * @ClassName:CustomerService
 * @description: CustomerService
 * @author: Alex
 * @Version：1.0
 * @create: 2019/12/18 10:06
 */
public interface CustomerRosService {

    /**
     * 创建客户
     *
     * @param enter
     * @return
     */
    GeneralResult save(CreateCustomerEnter enter);

    /**
     * 编辑更新客户
     *
     * @param enter
     * @return
     */
    GeneralResult edit(EditCustomerEnter enter);

    /**
     * 客户分页列表
     *
     * @param enter
     * @return
     */
    PageResult<CustomerDetailsResult> list(ListCustomerEnter enter);

    /**
     * 客户详情查询
     *
     * @param enter
     * @return
     */
    CustomerDetailsResult details(IdEnter enter);

    /**
     * 客户进入垃圾箱
     *
     * @param enter
     * @return
     */
    GeneralResult delete(DeleteCustomerEnter enter);

    /**
     * @desc: 潜在客户转正式客户
     * @param: enter
     * @return: GeneralResult
     * @auther: alex
     * @date: 2019/12/18 16:31
     * @Version: ROS 1.0
     */
    GeneralResult change(IdEnter enter);

    /**
     * 账号开通
     *
     * @param enter
     * @return
     */
    GeneralResult openAccount(OpenAccountEnter enter);

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
     * @desc: 客户账户列表状态
     * @param: enter
     * @return: enter
     * @auther: alex
     * @date: 2019/12/18 16:43
     * @Version: ROS 1.0
     */
    Map<String, Integer> countByCustomerAccountStatus(GeneralEnter enter);

}
