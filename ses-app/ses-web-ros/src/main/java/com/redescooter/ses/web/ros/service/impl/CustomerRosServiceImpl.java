package com.redescooter.ses.web.ros.service.impl;

import com.redescooter.ses.api.common.enums.ros.customer.CustomerStatusEnum;
import com.redescooter.ses.api.common.enums.ros.customer.CustomerTypeEnum;
import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.dao.CustomerProServiceMapper;
import com.redescooter.ses.web.ros.dao.base.OpeCustomerMapper;
import com.redescooter.ses.web.ros.dm.OpeCustomer;
import com.redescooter.ses.web.ros.service.CustomerRosService;
import com.redescooter.ses.web.ros.vo.*;
import com.redescooter.ses.web.ros.vo.customer.CreateCustomerEnter;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName:CustomerImpl
 * @description: CustomerImpl
 * @author: Alex
 * @Version：1.0
 * @create: 2019/12/18 10:06
 */
@Service
public class CustomerRosServiceImpl implements CustomerRosService {

    @Autowired
    private CustomerProServiceMapper customerProServiceMapper;
    @Autowired
    private OpeCustomerMapper opeCustomerMapper;

    @Reference
    private IdAppService idAppService;

    /**
     * 创建客户
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult save(CreateCustomerEnter enter) {

        OpeCustomer saveVo = new OpeCustomer();
        BeanUtils.copyProperties(enter, saveVo);
        if (saveVo.getCustomerType().equals(CustomerTypeEnum.PERSONAL.getValue())) {
            saveVo.setScooterQuantity(1);
        }
        saveVo.setId(idAppService.getId(SequenceName.OPE_CUSTOMER));
        saveVo.setCreatedBy(enter.getUserId());
        saveVo.setCreatedTime(new Date());
        saveVo.setUpdatedBy(enter.getUserId());
        saveVo.setUpdatedTime(new Date());

        opeCustomerMapper.insert(saveVo);
        return new GeneralResult(enter.getRequestId());
    }

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
        // 查询 客户状态
        //todo 补完整mapper
        List<CountByStatusResult> countByCustomerStatus = customerProServiceMapper.countByCustomerStatus();
        Map<String, Integer> map = new HashMap<>();
        for (CountByStatusResult item : countByCustomerStatus) {
            map.put(item.getStatus(), item.getTotalCount());
        }
        for (CustomerStatusEnum status : CustomerStatusEnum.values()) {
            if (!map.containsKey(status.getCode())) {
                map.put(status.getCode(), 0);
            }
        }
        return map;
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
    public PageResult<CustomerListByPageResult> customerListByPage(CustomerListByPageEnter enter) {
        // todo 补mapper
        int count = customerProServiceMapper.queryCustomerListCount(enter);
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        List<CustomerListByPageResult> customerList = customerProServiceMapper.queryCustomerList(enter);
        return PageResult.create(enter, count, customerList);
    }

    /**
     * @param enter
     * @desc: 潜在客户转正式客户
     * @param: enter
     * @return: GeneralResult
     * @auther: alex
     * @date: 2019/12/18 16:31
     * @Version: ROS 1.0
     */
    @Override
    public GeneralResult convertCustomer(IdEnter enter) {
        OpeCustomer customer = opeCustomerMapper.selectById(enter.getId());
        if (customer == null) {
            //todo 异常
        }
        customer.setStatus(CustomerStatusEnum.OFFICIAL_CUSTOMER.getCode());
        customer.setUpdatedBy(enter.getUserId());
        customer.setUpdatedTime(new Date());
        opeCustomerMapper.updateById(customer);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * @param enter
     * @desc: 删除客户
     * @param: enter
     * @return: GeneralResult
     * @auther: alex
     * @date: 2019/12/18 16:35
     * @Version: ROS 1.0
     */
    @Override
    public GeneralResult deleteCustomer(DeleteCustomerEnter enter) {
        OpeCustomer customer = opeCustomerMapper.selectById(enter.getId());
        if (customer == null) {
            // 异常
        }
        //customer.setStatus(CustomerStatusEnum.TRASH.getCode());
        //todo 删除原因 需要改动数据库
        customer.setUpdatedBy(enter.getUserId());
        customer.setUpdatedTime(new Date());

        opeCustomerMapper.updateById(customer);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * @param enter
     * @desc: 客户详情
     * @param: enter
     * @return: GeneralResult
     * @auther: alex
     * @date: 2019/12/18 16:36
     * @Version: ROS 1.0
     */
    @Override
    public CustomerDetailResult customerDetail(IdEnter enter) {
        OpeCustomer customer = opeCustomerMapper.selectById(enter.getId());
        if (customer == null) {
            // 异常
        }
        //todo 封装 返回
        CustomerDetailResult result = new CustomerDetailResult();
        return result;
    }

    /**
     * @param enter
     * @desc: 客户开通账户
     * @param: enter
     * @return: GeneralResult
     * @auther: alex
     * @date: 2019/12/18 17:39
     * @Version: ROS 1.0
     */
    @Override
    public GeneralResult createCustomerAccount(CreateCustomerAccountEnter enter) {

        return null;
    }

    /**
     * @param enter
     * @desc: 客户账户列表状态
     * @param: enter
     * @return: enter
     * @auther: alex
     * @date: 2019/12/18 16:43
     * @Version: ROS 1.0
     */
    @Override
    public Map<String, Integer> countByCustomerAccountStatus(GeneralEnter enter) {
        // 查询 客户状态
        //todo 补mapper 有需要可以掉 platform 注意
        List<CountByStatusResult> countByCustomerAccountStatus = customerProServiceMapper.countByCustomerAccountStatus();
        Map<String, Integer> map = new HashMap<>();
        for (CountByStatusResult item : countByCustomerAccountStatus) {
            map.put(item.getStatus(), item.getTotalCount());
        }
        //todo 换成客户账户enum
        for (CustomerStatusEnum status : CustomerStatusEnum.values()) {
            if (!map.containsKey(status.getCode())) {
                map.put(status.getCode(), 0);
            }
        }
        return map;
    }

    /**
     * @param enter
     * @desc: 客户账户列表
     * @param: enter
     * @return: CustomerAccountListResult
     * @auther: alex
     * @date: 2019/12/18 17:33
     * @Version: ROS 1.0
     */
    @Override
    public CustomerAccountListResult customerAccountList(CustomerAccountListEnter enter) {
        return null;
    }
}
