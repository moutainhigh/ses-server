package com.redescooter.ses.web.ros.service;

import com.redescooter.ses.api.common.vo.base.BooleanResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.vo.account.OpenAccountEnter;
import com.redescooter.ses.web.ros.vo.customer.AccountListEnter;
import com.redescooter.ses.web.ros.vo.customer.AccountListResult;
import com.redescooter.ses.web.ros.vo.customer.CreateCustomerEnter;
import com.redescooter.ses.web.ros.vo.customer.DetailsCustomerResult;
import com.redescooter.ses.web.ros.vo.customer.EditCustomerEnter;
import com.redescooter.ses.web.ros.vo.customer.ListCustomerEnter;
import com.redescooter.ses.web.ros.vo.customer.TrashCustomerEnter;

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
     * 邮箱验证
     *
     * @param mail
     * @return
     */
    BooleanResult checkMail(String mail);

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
    PageResult<DetailsCustomerResult> list(ListCustomerEnter enter);

    /**
     * 客户详情查询
     *
     * @param enter
     * @return
     */
    DetailsCustomerResult details(IdEnter enter);

    /**
     * 客户逻辑
     *
     * @param enter
     * @return
     */
    GeneralResult delete(IdEnter enter);

    /**
     * 客户垃圾
     *
     * @param enter
     * @return
     */
    GeneralResult trash(TrashCustomerEnter enter);

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
     * 状态统计
     *
     * @param enter
     * @return
     */
    Map<String, Integer> countStatus(GeneralEnter enter);

    /**
     * 账户列表
     * @param enter
     * @return
     */
    PageResult<AccountListResult> accountList(AccountListEnter enter);

}
