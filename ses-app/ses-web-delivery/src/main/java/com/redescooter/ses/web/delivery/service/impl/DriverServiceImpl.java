package com.redescooter.ses.web.delivery.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.enums.driver.DriverStatusEnum;
import com.redescooter.ses.api.common.enums.driver.RoleEnums;
import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.api.foundation.service.base.AccountBaseService;
import com.redescooter.ses.api.foundation.vo.account.SaveDriverAccountDto;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.DateUtil;
import com.redescooter.ses.web.delivery.constant.SequenceName;
import com.redescooter.ses.web.delivery.dao.DriverServiceMapper;
import com.redescooter.ses.web.delivery.dao.base.CorDriverMapper;
import com.redescooter.ses.web.delivery.dao.base.CorUserProfileMapper;
import com.redescooter.ses.web.delivery.dm.CorDriver;
import com.redescooter.ses.web.delivery.dm.CorUserProfile;
import com.redescooter.ses.web.delivery.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.delivery.exception.SesWebDeliveryException;
import com.redescooter.ses.web.delivery.service.DriverService;
import com.redescooter.ses.web.delivery.vo.DriverDetailsResult;
import com.redescooter.ses.web.delivery.vo.ListDriverPage;
import com.redescooter.ses.web.delivery.vo.ListDriverResult;
import com.redescooter.ses.web.delivery.vo.SaveDriverEnter;
import lombok.extern.slf4j.Slf4j;
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
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 2/1/2020 6:57 上午
 * @ClassName: DriverServiceImpl
 * @Function: TODO
 */
@Slf4j
@Service
public class DriverServiceImpl implements DriverService {

    @Autowired
    private CorDriverMapper driverMapper;
    @Autowired
    private CorUserProfileMapper profileMapper;
    @Autowired
    private DriverServiceMapper driverServiceMapper;
    @Reference
    private IdAppService idAppService;
    @Reference
    private AccountBaseService accountBaseService;

    /**
     * 创建司机
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult save(SaveDriverEnter enter) {

        //①验证邮箱是否存在
        Boolean aBoolean = accountBaseService.chectMail(enter.getEmail());

        if (!aBoolean) {
            throw new SesWebDeliveryException(ExceptionCodeEnums.ACCOUNT_ALREADY_EXIST.getCode(), ExceptionCodeEnums.ACCOUNT_ALREADY_EXIST.getMessage());
        }

        if (enter.getId() == null || enter.getId() == 0) {
            BaseUserResult user = openDriver2BAccout(enter);

            CorDriver driverSave = new CorDriver();
            driverSave.setId(idAppService.getId(SequenceName.COR_DRIVER));
            driverSave.setDr(0);
            driverSave.setUserId(user.getId());
            driverSave.setTenantId(user.getTenantId());
            driverSave.setDrivingLicense(enter.getDriverLicense());
            driverSave.setStatus(DriverStatusEnum.OFFWORK.getValue());
            driverSave.setCreatedBy(enter.getUserId());
            driverSave.setCreatedTime(new Date());
            driverSave.setUpdatedBy(enter.getUserId());
            driverSave.setUpdatedTime(new Date());
            driverMapper.insert(driverSave);

            CorUserProfile profileSave = new CorUserProfile();
            profileSave.setId(idAppService.getId(SequenceName.COR_USER_PROFILE));
            profileSave.setDr(0);
            profileSave.setTenantId(user.getTenantId());
            profileSave.setUserId(user.getId());
            profileSave.setPicture(enter.getAvatar());
            profileSave.setFirstName(enter.getDriverFirstName());
            profileSave.setLastName(enter.getDriverLastName());
            profileSave.setFullName(new StringBuffer().append(enter.getDriverFirstName()).append(" ").append(enter.getDriverLastName()).toString());
            profileSave.setEmail1(enter.getEmail());
            profileSave.setCountryCode1(enter.getCountryCodel());
            profileSave.setTelNumber1(enter.getDriverPhone());
            profileSave.setGender(enter.getGender());
            profileSave.setPlaceBirth(enter.getAddress());
            profileSave.setBirthday(DateUtil.timaConversion(enter.getBirthday()));
            profileSave.setCertificateType(enter.getCertificateType());
            profileSave.setCertificatePositiveAnnex(enter.getDriverLicenseUpAnnex());
            profileSave.setCertificateNegativeAnnex(enter.getDriverLicenseDownAnnex());
            profileSave.setRole(RoleEnums.DRIVER.getValue());
            profileSave.setJoinDate(new Date());
            profileSave.setTimeZone(enter.getTimeZone());
            profileSave.setCreatedBy(enter.getUserId());
            profileSave.setCreatedTime(new Date());
            profileSave.setUpdatedBy(enter.getUserId());
            profileSave.setUpdatedTime(new Date());
            profileMapper.insert(profileSave);
            IdEnter idEnter = new IdEnter();
            BeanUtils.copyProperties(enter, idEnter);
            idEnter.setId(driverSave.getUserId());
            accountBaseService.sendEmailAgian(idEnter);
        } else {

            CorDriver driver = driverMapper.selectById(enter.getId());
            if (driver == null) {
                throw new SesWebDeliveryException(ExceptionCodeEnums.ACCOUNT_ALREADY_EXIST.getCode(), ExceptionCodeEnums.ACCOUNT_ALREADY_EXIST.getMessage());
            }

            CorDriver driverUpdate = new CorDriver();
            driverUpdate.setId(enter.getId());
            if (enter.getDriverLicense() != null) {
                driverUpdate.setDrivingLicense(enter.getDriverLicense());
            }
            driverUpdate.setUpdatedBy(enter.getUserId());
            driverUpdate.setUpdatedTime(new Date());
            driverMapper.updateById(driverUpdate);

            QueryWrapper<CorUserProfile> wrapper = new QueryWrapper<>();
            wrapper.eq(CorUserProfile.COL_USER_ID, driver.getUserId());
            wrapper.eq(CorUserProfile.COL_DR, 0);

            CorUserProfile profile = profileMapper.selectOne(wrapper);
            if (enter.getAvatar() != null) {
                profile.setPicture(enter.getAvatar());
            }
            if (enter.getDriverFirstName() != null && enter.getDriverLastName() != null) {
                profile.setFirstName(enter.getDriverFirstName());
                profile.setLastName(enter.getDriverLastName());
                profile.setFullName(new StringBuffer().append(enter.getDriverFirstName()).append(" ").append(enter.getDriverLastName()).toString());
            }
            if (enter.getBirthday() != null) {
                profile.setBirthday(DateUtil.timaConversion(enter.getBirthday()));
            }
            profile.setUpdatedBy(enter.getUserId());
            profile.setUpdatedTime(new Date());
            profileMapper.updateById(profile);
        }

        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 2B司机账号开通
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public BaseUserResult openDriver2BAccout(SaveDriverEnter enter) {
        SaveDriverAccountDto dto = new SaveDriverAccountDto();
        BeanUtils.copyProperties(enter, dto);
        BaseUserResult baseUserResult = accountBaseService.openDriver2BAccout(dto);
        return baseUserResult;
    }

    /**
     * 司机状态统计
     *
     * @param enter
     * @return
     */
    @Override
    public Map<String, Integer> countStatus(GeneralEnter enter) {

        List<CountByStatusResult> countByStatusResults = driverServiceMapper.countStatus(enter);

        Map<String, Integer> map = new HashMap<>();
        for (CountByStatusResult item : countByStatusResults) {
            map.put(item.getStatus(), item.getTotalCount());
        }
        for (DriverStatusEnum status : DriverStatusEnum.values()) {
            if (!map.containsKey(status.getValue())) {
                map.put(status.getValue(), 0);
            }
        }
        map.remove(DriverStatusEnum.DEPARTURE.getValue());
        return map;
    }

    /**
     * 司机分页列表
     *
     * @param page
     * @return
     */
    @Override
    public PageResult<ListDriverResult> list(ListDriverPage page) {

        int totalRows = driverServiceMapper.listCount(page);

        if (totalRows == 0) {
            return PageResult.createZeroRowResult(page);
        }

        List<ListDriverResult> list = driverServiceMapper.list(page);

        return PageResult.create(page, totalRows, list);
    }

    /**
     * 司机详情
     *
     * @param enter
     * @return
     */
    @Override
    public DriverDetailsResult details(IdEnter enter) {

        CorDriver driver = driverMapper.selectById(enter.getId());

        if (driver == null) {
            return new DriverDetailsResult();
        }
        QueryWrapper<CorUserProfile> wrapper = new QueryWrapper<>();
        wrapper.eq(CorUserProfile.COL_USER_ID, driver.getUserId());
        wrapper.eq(CorUserProfile.COL_DR, 0);
        CorUserProfile profile = profileMapper.selectOne(wrapper);

        DriverDetailsResult result = new DriverDetailsResult();

        result.setId(driver.getId());
        result.setAvatar(profile.getPicture());
        result.setDriverFirstName(profile.getFirstName());
        result.setDriverLastName(profile.getLastName());
        result.setGender(profile.getGender());
        result.setCountryCodel(profile.getCountryCode1());
        result.setDriverPhone(profile.getTelNumber1());
        result.setEmail(profile.getEmail1());
        result.setAddress(profile.getPlaceBirth());
        result.setBirthday(DateUtil.getDateTime(profile.getBirthday(), null));
        result.setPlateNumber(null);
        result.setCertificateType(profile.getCertificateType());
        result.setDriverLicenseDownAnnex(profile.getCertificatePositiveAnnex());
        result.setDriverLicenseUpAnnex(profile.getCertificateNegativeAnnex());
        result.setRequestId(enter.getRequestId());

        return result;
    }

    /**
     * 司机离职
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult leave(IdEnter enter) {
        CorDriver driver = driverMapper.selectById(enter.getId());

        if (driver == null) {
            throw new SesWebDeliveryException(ExceptionCodeEnums.DRIVER_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.DRIVER_IS_NOT_EXIST.getMessage());
        }
        if (driver.getStatus().equals(DriverStatusEnum.DEPARTURE.getValue())) {
            return new GeneralResult(enter.getRequestId());
        }
        driver.setStatus(DriverStatusEnum.DEPARTURE.getValue());
        driver.setUpdatedBy(enter.getUserId());
        driver.setUpdatedTime(new Date());

        GeneralResult result = accountBaseService.cancelDriver2BAccout(new IdEnter(driver.getUserId()));

        driverMapper.deleteById(driver.getId());
        return result;
    }

    /**
     * 再次发生激活邮件2B
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult againSendEmail(IdEnter enter) {
        CorDriver driver = driverMapper.selectById(enter.getId());

        if (driver == null) {
            throw new SesWebDeliveryException(ExceptionCodeEnums.DRIVER_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.DRIVER_IS_NOT_EXIST.getMessage());
        }
        if (driver.getStatus().equals(DriverStatusEnum.DEPARTURE.getValue())) {
            throw new SesWebDeliveryException(ExceptionCodeEnums.DRIVER_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.DRIVER_IS_NOT_EXIST.getMessage());
        }
        enter.setId(driver.getUserId());
        GeneralResult result = accountBaseService.sendEmailAgian(enter);
        return result;
    }
}
