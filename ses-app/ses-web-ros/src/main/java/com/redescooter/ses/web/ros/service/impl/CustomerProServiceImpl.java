package com.redescooter.ses.web.ros.service.impl;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.ros.service.CustomerProService;
import com.redescooter.ses.web.ros.vo.CustomerListByPageEnter;
import com.redescooter.ses.web.ros.vo.CustomerListByPageResult;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @ClassName:CustomerImpl
 * @description: CustomerImpl
 * @author: Alex
 * @Version：1.0
 * @create: 2019/12/18 10:06
 */
@Service
public class CustomerProServiceImpl implements CustomerProService {

    /**
     * @param enter
     * @desc: 客户状态分类
     * @param: enter
     * @return: countByCustomerStatus
     * @auther: alex
     * @date: 2019/12/18 11:17
     * @Version: ros 1.0
     */
    @Override
    public Map<String, Integer> countByCustomerStatus(GeneralEnter enter) {
        return null;
    }

    /**
     * @param enter
     * @desc: 客户列表分页
     * @param: enter
     * @return: CustomerListByPageResult
     * @auther: alex
     * @date: 2019/12/18 11:51
     * @Version: CRM 1.3 进销存
     */
    @Override
    public CustomerListByPageResult customerListByPage(CustomerListByPageEnter enter) {
        return null;
    }
}
