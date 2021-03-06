package com.redescooter.ses.service.hub.source.operation.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.enums.website.ContantUsMessageType;
import com.redescooter.ses.api.common.vo.base.BaseCustomerEnter;
import com.redescooter.ses.api.common.vo.base.BaseCustomerResult;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.hub.exception.SeSHubException;
import com.redescooter.ses.api.hub.service.operation.CustomerService;
import com.redescooter.ses.api.hub.vo.operation.SyncContactUsDataEnter;
import com.redescooter.ses.api.hub.vo.operation.SyncCustomerDataEnter;
import com.redescooter.ses.service.common.service.CityAppService;
import com.redescooter.ses.service.hub.constant.SequenceName;
import com.redescooter.ses.service.hub.exception.ExceptionCodeEnums;
import com.redescooter.ses.service.hub.source.operation.dao.base.OpeCustomerMapper;
import com.redescooter.ses.service.hub.source.operation.dm.OpeContactUs;
import com.redescooter.ses.service.hub.source.operation.dm.OpeContactUsTrace;
import com.redescooter.ses.service.hub.source.operation.dm.OpeCustomer;
import com.redescooter.ses.service.hub.source.operation.service.base.OpeContactUsService;
import com.redescooter.ses.service.hub.source.operation.service.base.OpeContactUsTraceService;
import com.redescooter.ses.starter.common.service.IdAppService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @ClassName:CustomerServiceImpl
 * @description: CustomerServiceImpl
 * @author: Alex
 * @Version???1.3
 * @create: 2020/01/10 13:28
 */
@DubboService
@DS("operation")
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private OpeCustomerMapper opeCustomerMapper;

    @Autowired
    private OpeContactUsService opeContactUsService;

    @Autowired
    private OpeContactUsTraceService opeContactUsTraceService;

    @DubboReference
    private CityAppService cityAppService;

    @DubboReference
    private IdAppService idAppService;

    /**
     * id ??? ??????Id
     *
     * @param enter
     * @return
     */
    @Override
    public BaseCustomerResult customerInfo(IdEnter enter) {
        QueryWrapper<OpeCustomer> opeCustomerQueryWrapper = new QueryWrapper<>();
        opeCustomerQueryWrapper.eq(OpeCustomer.COL_DR, 0);
        opeCustomerQueryWrapper.eq(OpeCustomer.COL_TENANT_ID, enter.getId());
        OpeCustomer opeCustomer = opeCustomerMapper.selectOne(opeCustomerQueryWrapper);
        if (opeCustomer == null) {
            throw new SeSHubException(ExceptionCodeEnums.CUSTOMER_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.CUSTOMER_IS_NOT_EXIST.getMessage());
        }
        BaseCustomerResult baseCustomerResult = new BaseCustomerResult();
        BeanUtils.copyProperties(opeCustomer, baseCustomerResult);
        if (null != opeCustomer.getCity() && 0 != opeCustomer.getCity()) {
            baseCustomerResult.setCity(opeCustomer.getCity());
            String cityNameById = cityAppService.getCityNameById(opeCustomer.getCity());
            if (StringUtils.isNotBlank(cityNameById)) {
                baseCustomerResult.setCityName(cityNameById);
            }
        }
        if (null != opeCustomer.getDistrust() && 0 != opeCustomer.getDistrust()) {
            baseCustomerResult.setDistrust(opeCustomer.getDistrust());
            String cityNameById = cityAppService.getCityNameById(opeCustomer.getDistrust());
            if (StringUtils.isNotBlank(cityNameById)) {
                baseCustomerResult.setDistrustName(cityNameById);
            }
        }
        return baseCustomerResult;
    }

    /**
     * ?????? ????????????
     *
     * @param enter
     * @return
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    @DS("operation")
    public GeneralResult updateCustomerInfo(BaseCustomerEnter enter) {

        OpeCustomer opeCustomer = opeCustomerMapper.selectById(enter.getId());
        if (opeCustomer == null) {
            throw new SeSHubException(ExceptionCodeEnums.CUSTOMER_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.CUSTOMER_IS_NOT_EXIST.getMessage());
        }
        if (StringUtils.isNotBlank(enter.getContactFirstName()) && StringUtils.isNotBlank(enter.getContactLastName())) {
//            opeCustomer.setCustomerFirstName(enter.getCustomerFirstName());
//            opeCustomer.setCustomerLastName(enter.getCustomerLastName());
//            opeCustomer.setCustomerFullName(enter.getCustomerFullName());
            opeCustomer.setContactFirstName(enter.getContactFirstName());
            opeCustomer.setContactLastName(enter.getContactLastName());
            opeCustomer.setContactFullName(enter.getContactFullName());
        }
        if (StringUtils.isNotBlank(enter.getPicture())) {
            opeCustomer.setPicture(enter.getPicture());
        }
        if (StringUtils.isNotBlank(enter.getTelephone())) {
            opeCustomer.setTelephone(enter.getTelephone());
        }

        opeCustomerMapper.updateById(opeCustomer);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * ??????????????????????????????
     *
     * @param enter
     * @return
     */
    @Override
    @DS("operation")
    public GeneralResult updateCustomerInfoByAnyProperty(BaseCustomerEnter enter) {
        QueryWrapper<OpeCustomer> opeCustomerQueryWrapper = new QueryWrapper<>();
        opeCustomerQueryWrapper.eq(OpeCustomer.COL_DR, 0);
        opeCustomerQueryWrapper.eq(OpeCustomer.COL_TENANT_ID, 0);
        opeCustomerQueryWrapper.eq(OpeCustomer.COL_CUSTOMER_TYPE, enter.getCustomerType());
        opeCustomerQueryWrapper.eq(OpeCustomer.COL_INDUSTRY_TYPE, enter.getIndustryType());
        OpeCustomer opeCustomer = opeCustomerMapper.selectOne(opeCustomerQueryWrapper);
        if (opeCustomer == null) {

        }

        return null;
    }

    /**
     * @param enter
     * @Description
     * @Author: alex
     * @Date: 2020/11/26 10:51 ??????
     * @Param: enter
     * @Return: GeneralResult
     * @desc: ????????????????????????
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    @DS("operation")
    public GeneralResult updateCustomerInfoByEmail(BaseCustomerEnter enter) {
        OpeCustomer opeCustomer = opeCustomerMapper.selectOne(new LambdaQueryWrapper<OpeCustomer>().eq(OpeCustomer::getEmail, enter.getEmail()).last(" limit 1"));
        if (opeCustomer == null) {
            throw new SeSHubException(ExceptionCodeEnums.CUSTOMER_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.CUSTOMER_IS_NOT_EXIST.getMessage());
        }
        if (!StringUtils.isAllBlank(enter.getCustomerFirstName(), enter.getCustomerLastName())) {
            opeCustomer.setCustomerFirstName(enter.getCustomerFirstName());
            opeCustomer.setCustomerLastName(enter.getCustomerLastName());
        }
        if (StringUtils.isNotEmpty(enter.getTelephone())) {
            opeCustomer.setTelephone(enter.getTelephone());
        }
        opeCustomerMapper.updateById(opeCustomer);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * ????????????????????????????????????ros
     */
    @Override
    @DS("operation")
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult syncCustomerData(SyncCustomerDataEnter enter) {
        log.info("**********************??????????????????????????????ROS?????????*************************");
        LambdaQueryWrapper<OpeCustomer> qw = new LambdaQueryWrapper<>();
        qw.eq(OpeCustomer::getDr, Constant.DR_FALSE);
        qw.eq(OpeCustomer::getEmail, enter.getEmail());
        qw.last("limit 1");
        OpeCustomer customer = opeCustomerMapper.selectOne(qw);
        // ????????????
        OpeCustomer param = new OpeCustomer();
        BeanUtils.copyProperties(enter, param);
        if (null == customer) {
            // ???????????????
            param.setId(idAppService.getId(SequenceName.OPE_CUSTOMER));
            opeCustomerMapper.insert(param);
        } else {
            // ???????????????
            param.setId(customer.getId());
            opeCustomerMapper.updateById(param);
        }
        return new GeneralResult();
    }

    /**
     * ?????????????????????????????????ros
     */
    @Override
    @DS("operation")
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult syncContactUsData(SyncContactUsDataEnter enter) {
        // ?????????????????????????????????
        QueryWrapper<OpeContactUs> qw = new QueryWrapper<>();
        qw.eq("email", enter.getEmail());
        qw.last("limit 1");
        OpeContactUs opeContactUs = opeContactUsService.getOne(qw);
        if (opeContactUs != null) {
            // ??????????????????????????????
            opeContactUs.setFrequency(opeContactUs.getFrequency() + 1);
        } else {
            // ??????????????????????????????????????????
            opeContactUs = new OpeContactUs();
            opeContactUs.setId(idAppService.getId(SequenceName.OPE_CONTACT_US));
            opeContactUs.setEmail(enter.getEmail());
            opeContactUs.setFrequency(1);
            opeContactUs.setCreatedTime(new Date());
        }
        opeContactUs.setFirstName(enter.getFirstName());
        opeContactUs.setLastName(enter.getLastName());
        opeContactUs.setFullName(opeContactUs.getFirstName() + " " + opeContactUs.getLastName());
        opeContactUs.setTelephone(enter.getTelephone());
        opeContactUs.setAddress(enter.getAddress());
        opeContactUs.setRemark(enter.getRemark());
        opeContactUs.setUpdatedTime(new Date());
        opeContactUsService.saveOrUpdate(opeContactUs);

        // ?????????????????? ?????????????????????ope_contact_us_trace????????????
        OpeContactUsTrace opeContactUsTraceEntity = new OpeContactUsTrace();
        opeContactUsTraceEntity.setId(idAppService.getId(SequenceName.OPE_CONTACT_US_TRACE));
        opeContactUsTraceEntity.setCreatedBy(0L);
        opeContactUsTraceEntity.setCreatedTime(new Date());
        opeContactUsTraceEntity.setUpdatedBy(0L);
        opeContactUsTraceEntity.setUpdatedTime(new Date());
        opeContactUsTraceEntity.setContactUsId(opeContactUs.getId());
        opeContactUsTraceEntity.setFirstName(opeContactUs.getFirstName());
        opeContactUsTraceEntity.setLastName(opeContactUs.getLastName());
        opeContactUsTraceEntity.setEmail(opeContactUs.getEmail());
        opeContactUsTraceEntity.setFullName(opeContactUs.getFullName());
        opeContactUsTraceEntity.setTelephone(opeContactUs.getTelephone());
        opeContactUsTraceEntity.setCountryName(opeContactUs.getCountryName());
        opeContactUsTraceEntity.setCityName(opeContactUs.getCityName());
        opeContactUsTraceEntity.setDistrictName(opeContactUs.getDistrictName());
        opeContactUsTraceEntity.setAddress(opeContactUs.getAddress());
        opeContactUsTraceEntity.setRemark(opeContactUs.getRemark());
        opeContactUsTraceEntity.setMessageType(ContantUsMessageType.LEAVE_MESSAGE.getValue());
        opeContactUsTraceService.saveOrUpdate(opeContactUsTraceEntity);
        return new GeneralResult();
    }

}
