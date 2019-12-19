package com.redescooter.ses.web.ros.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.CustomerRosService;
import com.redescooter.ses.web.ros.vo.account.OpenAccountEnter;
import com.redescooter.ses.web.ros.vo.customer.*;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
     * @param page
     * @desc: 客户列表分页
     * @param: enter
     * @return: CustomerListByPageResult
     * @auther: alex
     * @date: 2019/12/18 11:51
     * @Version: CRM 1.3 进销存
     */
    @Override
    public PageResult<CustomerDetailsResult> list(ListCustomerEnter page) {

        List<CustomerDetailsResult> resultList = new ArrayList<>();
        CustomerDetailsResult detailsResult = null;

        QueryWrapper<OpeCustomer> wrapper = new QueryWrapper<>();

        if (page.getDr() != null) {
            wrapper.eq(OpeCustomer.COL_DR, page.getDr());
        } else {
            wrapper.eq(OpeCustomer.COL_DR, 0);
        }
        if (page.getStatus() != null) {
            wrapper.eq(OpeCustomer.COL_STATUS, page.getStatus());
        }
        if (page.getOneCityiD() != null && page.getTwoCityiD() != null) {
            wrapper.eq(OpeCustomer.COL_CITY, page.getOneCityiD());
            wrapper.eq(OpeCustomer.COL_DISTRUST, page.getTwoCityiD());
        }
        if (page.getCustomerType() != null) {
            wrapper.eq(OpeCustomer.COL_CUSTOMER_TYPE, page.getCustomerType());
        }
        if (page.getCustomerIndustry() != null) {
            wrapper.eq(OpeCustomer.COL_INDUSTRY_TYPE, page.getCustomerIndustry());
        }
        if (page.getCustomerSource() != null) {
            wrapper.eq(OpeCustomer.COL_CUSTOMER_SOURCE, page.getCustomerSource());
        }
        if (page.getCreateStartDateTime() != null && page.getCreateEndDateTime() != null) {
            wrapper.between(OpeCustomer.COL_CREATED_TIME, page.getCreateStartDateTime(), page.getCreateEndDateTime());
        }
        if (page.getKeyword() != null) {
            wrapper.like(OpeCustomer.COL_NAME, page.getKeyword()).or().like(OpeCustomer.COL_EMAIL, page.getKeyword());
        }

        Page<OpeCustomer> customerPage = new Page<>(page.getPageNo(), page.getPageSize());
        IPage<OpeCustomer> opeCustomerIPage = opeCustomerMapper.selectPage(customerPage, wrapper);
        List<OpeCustomer> pageRecords = opeCustomerIPage.getRecords();

        for (OpeCustomer customer : pageRecords) {
            detailsResult = new CustomerDetailsResult();
            BeanUtils.copyProperties(customer, detailsResult);
            resultList.add(detailsResult);
        }

        return PageResult.create(page, (int) opeCustomerIPage.getTotal(), resultList);
    }

    /**
     * 客户详情查询
     *
     * @param enter
     * @return
     */
    @Override
    public CustomerDetailsResult details(IdEnter enter) {

        OpeCustomer opeCustomer = opeCustomerMapper.selectById(enter.getId());
        CustomerDetailsResult result = new CustomerDetailsResult();
        BeanUtils.copyProperties(opeCustomer, result);
        result.setRequestId(enter.getRequestId());

        return result;
    }

    /**
     * 客户进入垃圾箱
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult delete(DeleteCustomerEnter enter) {

        UpdateWrapper<OpeCustomer> deleteWrapper = new UpdateWrapper<>();
        deleteWrapper.eq("id", enter.getId());
        deleteWrapper.eq(OpeCustomer.COL_MEMO, enter.getReason());

        opeCustomerMapper.delete(deleteWrapper);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 编辑更新客户
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult edit(EditCustomerEnter enter) {

        OpeCustomer update = new OpeCustomer();
        if (update.getId() == null) {
            throw new SesWebRosException(ExceptionCodeEnums.PRIMARY_KEY_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.PRIMARY_KEY_CANNOT_EMPTY.getMessage());
        }
        BeanUtils.copyProperties(enter, update);
        opeCustomerMapper.updateById(update);

        return new GeneralResult(enter.getRequestId());
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
    public GeneralResult change(IdEnter enter) {
        OpeCustomer customer = opeCustomerMapper.selectById(enter.getId());
        if (customer == null) {
            throw new SesWebRosException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(), ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }
        customer.setStatus(CustomerStatusEnum.OFFICIAL_CUSTOMER.getCode());
        customer.setUpdatedBy(enter.getUserId());
        customer.setUpdatedTime(new Date());
        opeCustomerMapper.updateById(customer);
        return new GeneralResult(enter.getRequestId());
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
    public GeneralResult openAccount(OpenAccountEnter enter) {

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
}
