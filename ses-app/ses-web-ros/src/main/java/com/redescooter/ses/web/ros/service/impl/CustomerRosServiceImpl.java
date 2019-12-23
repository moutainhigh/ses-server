package com.redescooter.ses.web.ros.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.redescooter.ses.api.common.enums.ros.customer.*;
import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.api.foundation.service.base.AccountBaseService;
import com.redescooter.ses.api.foundation.service.base.CityBaseService;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.DateUtil;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.dao.CustomerServiceMapper;
import com.redescooter.ses.web.ros.dao.base.OpeCustomerMapper;
import com.redescooter.ses.web.ros.dm.OpeCustomer;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.CustomerRosService;
import com.redescooter.ses.web.ros.vo.account.OpenAccountEnter;
import com.redescooter.ses.web.ros.vo.customer.*;
import org.apache.commons.lang3.StringUtils;
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
    private OpeCustomerMapper opeCustomerMapper;

    @Autowired
    private CustomerServiceMapper customerServiceMapper;
    @Reference
    private IdAppService idAppService;
    @Reference
    private CityBaseService cityBaseService;
    @Reference
    private AccountBaseService accountBaseService;

    /**
     * 邮箱验证
     *
     * @param mail
     * @return
     */
    @Override
    public BooleanResult checkMail(String mail) {

        QueryWrapper<OpeCustomer> wrapper = new QueryWrapper<>();
        wrapper.eq(OpeCustomer.COL_EMAIL, mail);
        wrapper.eq(OpeCustomer.COL_DR, 0);
        Boolean mailBoolean = opeCustomerMapper.selectCount(wrapper) == 0 ? Boolean.TRUE : Boolean.FALSE;

        return new BooleanResult(mailBoolean);
    }

    /**
     * 创建客户
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult save(CreateCustomerEnter enter) {

        QueryWrapper<OpeCustomer> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(OpeCustomer.COL_EMAIL, enter.getEmail());
        Integer count = opeCustomerMapper.selectCount(queryWrapper);

        if (count > 0) {
            throw new SesWebRosException(ExceptionCodeEnums.ACCOUNT_ALREADY_EXIST.getCode(), ExceptionCodeEnums.ACCOUNT_ALREADY_EXIST.getMessage());
        }

        if (enter.getCustomerType().equals(CustomerTypeEnum.ENTERPRISE.getValue())) {
            if (StringUtils.isBlank(enter.getContactFirstName())) {
                throw new SesWebRosException(ExceptionCodeEnums.FIRST_NAME_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.FIRST_NAME_CANNOT_EMPTY.getMessage());
            }
            if (StringUtils.isBlank(enter.getContactLastName())) {
                throw new SesWebRosException(ExceptionCodeEnums.LAST_NAME_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.LAST_NAME_CANNOT_EMPTY.getMessage());
            }
            if (StringUtils.isBlank(enter.getCompanyName())) {
                throw new SesWebRosException(ExceptionCodeEnums.COMPANY_NAME_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.COMPANY_NAME_CANNOT_EMPTY.getMessage());
            }
        } else {
            if (StringUtils.isBlank(enter.getCustomerFirstName())) {
                throw new SesWebRosException(ExceptionCodeEnums.FIRST_NAME_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.FIRST_NAME_CANNOT_EMPTY.getMessage());
            }
            if (StringUtils.isBlank(enter.getCustomerLastName())) {
                throw new SesWebRosException(ExceptionCodeEnums.LAST_NAME_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.LAST_NAME_CANNOT_EMPTY.getMessage());
            }
            enter.setScooterQuantity(1);
        }
        if (StringUtils.isBlank(enter.getEmail())) {
            throw new SesWebRosException(ExceptionCodeEnums.MAIL_NAME_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.MAIL_NAME_CANNOT_EMPTY.getMessage());
        }
        OpeCustomer saveVo = new OpeCustomer();
        BeanUtils.copyProperties(enter, saveVo);
        saveVo.setId(idAppService.getId(SequenceName.OPE_CUSTOMER));
        saveVo.setStatus(CustomerStatusEnum.POTENTIAL_CUSTOMERS.getValue());
        saveVo.setCustomerSource(CustomerSourceEnum.SYSTEM.getValue());
        if (enter.getCustomerType().equals(CustomerTypeEnum.ENTERPRISE.getValue())) {
            saveVo.setContactFullName(new StringBuffer().append(saveVo.getContactFirstName()).append(" ").append(saveVo.getContactLastName()).toString());
        } else {
            saveVo.setCustomerFullName(new StringBuffer().append(saveVo.getCustomerFirstName()).append(" ").append(saveVo.getCustomerLastName()).toString());
        }
        saveVo.setAccountFlag(Integer.valueOf(CustomerAccountFlagEnum.NORMAL.getValue()));
        saveVo.setCreatedBy(enter.getUserId());
        saveVo.setCreatedTime(new Date());
        saveVo.setUpdatedBy(enter.getUserId());
        saveVo.setUpdatedTime(new Date());

        opeCustomerMapper.insert(saveVo);
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

        OpeCustomer customer = opeCustomerMapper.selectById(enter.getId());
        if (customer.getStatus().equals(CustomerStatusEnum.TRASH_CUSTOMER.getValue())) {
            throw new SesWebRosException(ExceptionCodeEnums.TRASH_CAN_NOT_BE_EDITED.getCode(), ExceptionCodeEnums.TRASH_CAN_NOT_BE_EDITED.getMessage());
        }
        if (customer.getStatus().equals(CustomerStatusEnum.OFFICIAL_CUSTOMER.getValue())) {
            //客户验证
            checkCustomer(enter);
        }
        OpeCustomer update = new OpeCustomer();
        BeanUtils.copyProperties(enter, update);
        opeCustomerMapper.updateById(update);

        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 客户详情查询
     *
     * @param enter
     * @return
     */
    @Override
    public DetailsCustomerResult details(IdEnter enter) {

        OpeCustomer opeCustomer = opeCustomerMapper.selectById(enter.getId());

        if (opeCustomer == null) {
            throw new SesWebRosException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(), ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }
        DetailsCustomerResult result = new DetailsCustomerResult();
        BeanUtils.copyProperties(opeCustomer, result);
        result.setRequestId(enter.getRequestId());
        if (opeCustomer.getCity() != null || opeCustomer.getDistrust() != null) {
            result.setCityName(cityBaseService.queryCityDeatliById(IdEnter.builder().id(result.getCity()).build()).getName());
            result.setDistrustName(cityBaseService.queryCityDeatliById(IdEnter.builder().id(result.getDistrust()).build()).getName());
        }
        return result;
    }

    /**
     * 状态统计
     *
     * @param enter
     * @return
     */
    @Override
    public Map<String, Integer> countStatus(GeneralEnter enter) {
        List<CountByStatusResult> countByCustomerStatus = customerServiceMapper.countStatus();
        Map<String, Integer> map = new HashMap<>();
        for (CountByStatusResult item : countByCustomerStatus) {
            map.put(item.getStatus(), item.getTotalCount());
        }
        for (CustomerStatusEnum status : CustomerStatusEnum.values()) {
            if (!map.containsKey(status.getValue())) {
                map.put(status.getValue(), 0);
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
    public PageResult<DetailsCustomerResult> list(ListCustomerEnter page) {

        List<DetailsCustomerResult> resultList = new ArrayList<>();
        DetailsCustomerResult detailsResult = null;

        QueryWrapper<OpeCustomer> wrapper = new QueryWrapper<>();

        if (StringUtils.isNotBlank(page.getStatus())) {
            wrapper.eq(OpeCustomer.COL_STATUS, page.getStatus());
        }
        if (page.getOneCityiD() != null && page.getTwoCityiD() != null) {
            wrapper.eq(OpeCustomer.COL_CITY, page.getOneCityiD());
            wrapper.eq(OpeCustomer.COL_DISTRUST, page.getTwoCityiD());
        }
        if (StringUtils.isNotBlank(page.getCustomerType())) {
            wrapper.eq(OpeCustomer.COL_CUSTOMER_TYPE, page.getCustomerType());
        }
        if (StringUtils.isNotBlank(page.getCustomerIndustry())) {
            wrapper.eq(OpeCustomer.COL_INDUSTRY_TYPE, page.getCustomerIndustry());
        }
        if (StringUtils.isNotBlank(page.getCustomerSource())) {
            wrapper.eq(OpeCustomer.COL_CUSTOMER_SOURCE, page.getCustomerSource());
        }
        if (page.getCreateStartDateTime() != null && page.getCreateEndDateTime() != null) {
            wrapper.between(OpeCustomer.COL_CREATED_TIME, page.getCreateStartDateTime(), page.getCreateEndDateTime());
        }
        if (StringUtils.isNotBlank(page.getKeyword())) {
            wrapper.and(wh -> wh.like(OpeCustomer.COL_CONTACT_FULL_NAME, page.getKeyword()).or().like(OpeCustomer.COL_EMAIL, page.getKeyword()).or().like(OpeCustomer.COL_CONTACT_FULL_NAME, page.getKeyword()));
        }

        if(StringUtils.isNotBlank(page.getStatus())){
            if (page.getStatus().equals(CustomerStatusEnum.POTENTIAL_CUSTOMERS.getValue())) {
                wrapper.orderByDesc(OpeCustomer.COL_CREATED_TIME);
            } else {
                wrapper.orderByDesc(OpeCustomer.COL_UPDATED_TIME);
            }
        }

        Page<OpeCustomer> customerPage = new Page<>(page.getPageNo(), page.getPageSize());
        IPage<OpeCustomer> opeCustomerIPage = opeCustomerMapper.selectPage(customerPage, wrapper);
        List<OpeCustomer> pageRecords = opeCustomerIPage.getRecords();

        for (OpeCustomer customer : pageRecords) {
            detailsResult = new DetailsCustomerResult();
            BeanUtils.copyProperties(customer, detailsResult);
            if (customer.getCity() != null || customer.getDistrust() != null) {
                detailsResult.setCityName(cityBaseService.queryCityDeatliById(IdEnter.builder().id(detailsResult.getCity()).build()).getName());
                detailsResult.setDistrustName(cityBaseService.queryCityDeatliById(IdEnter.builder().id(detailsResult.getDistrust()).build()).getName());
            }
            resultList.add(detailsResult);
        }

        return PageResult.create(page, (int) opeCustomerIPage.getTotal(), resultList);
    }

    /**
     * 客户逻辑
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult delete(IdEnter enter) {
        //验证客户是否开通SaaS账户等信息
        OpeCustomer opeCustomer = opeCustomerMapper.selectById(enter.getId());
        if (opeCustomer.getStatus().equals(CustomerStatusEnum.TRASH_CUSTOMER.getValue())) {
            throw new SesWebRosException(ExceptionCodeEnums.INSUFFICIENT_PERMISSIONS.getCode(), ExceptionCodeEnums.INSUFFICIENT_PERMISSIONS.getMessage());
        }
        opeCustomerMapper.deleteById(enter.getId());

        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 客户垃圾
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult trash(TrashCustomerEnter enter) {

        //验证客户是否开通SaaS账户等信息
        OpeCustomer opeCustomer = opeCustomerMapper.selectById(enter.getId());
        if (opeCustomer.getAccountFlag().equals(CustomerAccountFlagEnum.ACTIVATION)) {
            throw new SesWebRosException(ExceptionCodeEnums.INSUFFICIENT_PERMISSIONS.getCode(), ExceptionCodeEnums.INSUFFICIENT_PERMISSIONS.getMessage());
        }
        OpeCustomer update = new OpeCustomer();
        update.setId(enter.getId());
        update.setStatus(CustomerStatusEnum.TRASH_CUSTOMER.getValue());
        update.setMemo(enter.getReason());
        opeCustomerMapper.updateById(update);

        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 客户转正
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult change(IdEnter enter) {

        OpeCustomer customer = opeCustomerMapper.selectById(enter.getId());
        EditCustomerEnter checkCustomer = new EditCustomerEnter();
        BeanUtils.copyProperties(customer,checkCustomer);
        checkCustomer(checkCustomer);

        if (customer == null) {
            throw new SesWebRosException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(), ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }
        customer.setStatus(CustomerStatusEnum.OFFICIAL_CUSTOMER.getValue());
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

        OpeCustomer opeCustomer = opeCustomerMapper.selectById(enter.getId());

        if (opeCustomer == null) {
            throw new SesWebRosException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(), ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }
        BooleanResult checkMail = checkMail(opeCustomer.getEmail());

        if (checkMail.isSuccess()) {
            BaseCustomerResult baseCustomer = new BaseCustomerResult();
            BeanUtils.copyProperties(opeCustomer, baseCustomer);

            DateTimeParmEnter<BaseCustomerResult> parmEnter = new DateTimeParmEnter();
            parmEnter.setStartDateTime(DateUtil.stringToDate(enter.getStartActivationTime()));
            parmEnter.setEndDateTime(DateUtil.stringToDate(enter.getEndActivationTime()));

            BaseUserResult userResult = accountBaseService.open(parmEnter);

            opeCustomer.setTenantId(userResult.getTenantId());
            opeCustomer.setAccountFlag(Integer.parseInt(CustomerAccountFlagEnum.INACTIVATED.getValue()));
            opeCustomer.setUpdatedBy(enter.getUserId());
            opeCustomer.setUpdatedTime(new Date());
            opeCustomerMapper.updateById(opeCustomer);
        } else {
            throw new SesWebRosException(ExceptionCodeEnums.ACCOUNT_ALREADY_EXIST.getCode(), ExceptionCodeEnums.ACCOUNT_ALREADY_EXIST.getMessage());
        }
        return new GeneralResult(enter.getRequestId());
    }

    private void checkCustomer(EditCustomerEnter enter) {
        if (enter.getId() == null) {
            throw new SesWebRosException(ExceptionCodeEnums.PRIMARY_KEY_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.PRIMARY_KEY_CANNOT_EMPTY.getMessage());
        }
        if (enter.getCity() == null) {
            throw new SesWebRosException(ExceptionCodeEnums.CITY_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.CITY_CANNOT_EMPTY.getMessage());
        }
        if (enter.getDistrust() == null) {
            throw new SesWebRosException(ExceptionCodeEnums.DISTRUST_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.DISTRUST_CANNOT_EMPTY.getMessage());
        }
        if (enter.getCustomerType().equals(CustomerTypeEnum.PERSONAL.getValue())) {

            if (StringUtils.isBlank(enter.getCustomerFirstName())) {
                throw new SesWebRosException(ExceptionCodeEnums.FIRST_NAME_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.FIRST_NAME_CANNOT_EMPTY.getMessage());
            }
            if (StringUtils.isBlank(enter.getCustomerLastName())) {
                throw new SesWebRosException(ExceptionCodeEnums.LAST_NAME_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.LAST_NAME_CANNOT_EMPTY.getMessage());
            }
        } else {
            if (StringUtils.isBlank(enter.getCompanyName())) {
                throw new SesWebRosException(ExceptionCodeEnums.COMPANY_NAME_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.COMPANY_NAME_CANNOT_EMPTY.getMessage());
            }
            if (StringUtils.isBlank(enter.getBusinessLicenseNum())) {
                throw new SesWebRosException(ExceptionCodeEnums.BUSINESS_LICENSE_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.BUSINESS_LICENSE_CANNOT_EMPTY.getMessage());
            }
            if (StringUtils.isBlank(enter.getBusinessLicenseNum())) {
                throw new SesWebRosException(ExceptionCodeEnums.BUSINESS_LICENSE_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.BUSINESS_LICENSE_CANNOT_EMPTY.getMessage());
            }
            if (StringUtils.isBlank(enter.getBusinessLicenseAnnex())) {
                throw new SesWebRosException(ExceptionCodeEnums.BUSINESS_LICENSE_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.BUSINESS_LICENSE_CANNOT_EMPTY.getMessage());
            }
        }
        if (StringUtils.isBlank(enter.getCustomerType())) {
            throw new SesWebRosException(ExceptionCodeEnums.CUSTOMER_TYPE_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.CUSTOMER_TYPE_CANNOT_EMPTY.getMessage());
        }
        if (StringUtils.isBlank(enter.getIndustryType())) {
            throw new SesWebRosException(ExceptionCodeEnums.INDUSTRY_TYPE_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.INDUSTRY_TYPE_CANNOT_EMPTY.getMessage());
        }
        if (StringUtils.isBlank(enter.getAddress())) {
            throw new SesWebRosException(ExceptionCodeEnums.ADDRESS_TYPE_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.ADDRESS_TYPE_CANNOT_EMPTY.getMessage());
        }
        if (StringUtils.isAllBlank(String.valueOf(enter.getLongitude()), String.valueOf(enter.getLatitude()))) {
            throw new SesWebRosException(ExceptionCodeEnums.LATITUDE_AND_LONGITUDE_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.LATITUDE_AND_LONGITUDE_CANNOT_EMPTY.getMessage());
        }
        if (StringUtils.isBlank(enter.getTelephone())) {
            throw new SesWebRosException(ExceptionCodeEnums.TELEPHONE_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.TELEPHONE_CANNOT_EMPTY.getMessage());
        }
        if (StringUtils.isBlank(enter.getCertificateType())) {
            throw new SesWebRosException(ExceptionCodeEnums.CERTIFICATETYPE_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.CERTIFICATETYPE_CANNOT_EMPTY.getMessage());
        }
        if (enter.getCertificateType().equals(CustomerCertificateTypeEnum.ID_CARD.getValue())) {
            if (StringUtils.isAllBlank(enter.getCertificatePositiveAnnex(), enter.getCertificateNegativeAnnex())) {
                throw new SesWebRosException(ExceptionCodeEnums.ID_CARD_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.ID_CARD_CANNOT_EMPTY.getMessage());
            }
        } else {
            if (StringUtils.isBlank(enter.getCertificatePositiveAnnex())) {
                throw new SesWebRosException(ExceptionCodeEnums.CERTIFICATE_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.CERTIFICATE_CANNOT_EMPTY.getMessage());
            }
        }
        if (StringUtils.isBlank(enter.getInvoiceNum())) {
            throw new SesWebRosException(ExceptionCodeEnums.INVOICE_NUM_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.INVOICE_NUM_CANNOT_EMPTY.getMessage());
        }
        if (StringUtils.isBlank(enter.getInvoiceAnnex())) {
            throw new SesWebRosException(ExceptionCodeEnums.INVOICE_ANNEX_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.INVOICE_ANNEX_CANNOT_EMPTY.getMessage());
        }
        if (StringUtils.isBlank(enter.getContractAnnex())) {
            throw new SesWebRosException(ExceptionCodeEnums.CONTRACT_ANNEX_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.CONTRACT_ANNEX_CANNOT_EMPTY.getMessage());
        }
    }

}
