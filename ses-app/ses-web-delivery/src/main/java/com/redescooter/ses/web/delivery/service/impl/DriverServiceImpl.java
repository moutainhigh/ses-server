package com.redescooter.ses.web.delivery.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.enums.driver.DriverStatusEnum;
import com.redescooter.ses.api.common.enums.driver.RoleEnums;
import com.redescooter.ses.api.common.enums.scooter.ScooterStatusEnums;
import com.redescooter.ses.api.common.enums.tenant.TenantScooterStatusEnums;
import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.api.foundation.service.base.AccountBaseService;
import com.redescooter.ses.api.foundation.vo.account.SaveDriverAccountDto;
import com.redescooter.ses.api.scooter.service.ScooterService;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.DateUtil;
import com.redescooter.ses.web.delivery.constant.SequenceName;
import com.redescooter.ses.web.delivery.dao.DriverServiceMapper;
import com.redescooter.ses.web.delivery.dao.base.CorDriverMapper;
import com.redescooter.ses.web.delivery.dao.base.CorDriverScooterMapper;
import com.redescooter.ses.web.delivery.dao.base.CorTenantScooterMapper;
import com.redescooter.ses.web.delivery.dao.base.CorUserProfileMapper;
import com.redescooter.ses.web.delivery.dm.CorDriver;
import com.redescooter.ses.web.delivery.dm.CorDriverScooter;
import com.redescooter.ses.web.delivery.dm.CorTenantScooter;
import com.redescooter.ses.web.delivery.dm.CorUserProfile;
import com.redescooter.ses.web.delivery.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.delivery.exception.SesWebDeliveryException;
import com.redescooter.ses.web.delivery.service.DriverService;
import com.redescooter.ses.web.delivery.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
    private CorTenantScooterMapper corTenantScooterMapper;
    @Autowired
    private CorUserProfileMapper profileMapper;
    @Autowired
    private DriverServiceMapper driverServiceMapper;
    @Autowired
    private CorDriverScooterMapper driverScooterMapper;
    @Autowired
    private CorTenantScooterMapper tenantScooterMapper;
    @Reference
    private IdAppService idAppService;
    @Reference
    private AccountBaseService accountBaseService;
    @Reference
    private ScooterService scooterService;

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
            profileSave.setCertificatePositiveAnnex(enter.getDriverLicenseDownAnnex());
            profileSave.setCertificateNegativeAnnex(enter.getDriverLicenseUpAnnex());
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
                new SesWebDeliveryException(ExceptionCodeEnums.ACCOUNT_ALREADY_EXIST.getCode(), ExceptionCodeEnums.ACCOUNT_ALREADY_EXIST.getMessage());
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
            if (enter.getCertificateType() != null &&
                    enter.getDriverLicenseUpAnnex() != null &&
                    enter.getDriverLicenseDownAnnex() != null) {
                profile.setCertificateType(enter.getCertificateType());
                profile.setCertificatePositiveAnnex(enter.getDriverLicenseDownAnnex());
                profile.setCertificateNegativeAnnex(enter.getDriverLicenseUpAnnex());
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

    /**
     * 门店车辆列表
     *
     * @param enter
     * @return
     */
    @Override
    public List<ListScooterResult> scooterList(GeneralEnter enter) {
        QueryWrapper<CorTenantScooter> corTenantScooterQueryWrapper = new QueryWrapper<>();
        corTenantScooterQueryWrapper.eq(CorTenantScooter.COL_TENANT_ID, enter.getTenantId());
        corTenantScooterQueryWrapper.eq(CorTenantScooter.COL_DR, 0);
        List<CorTenantScooter> corTenantScooterList = corTenantScooterMapper.selectList(corTenantScooterQueryWrapper);
        if (CollectionUtils.isEmpty(corTenantScooterList)) {
            return new ArrayList<>();
        }
        List<Long> scooterIdList = new ArrayList<>();
        corTenantScooterList.forEach(item -> {
            scooterIdList.add(item.getScooterId());
        });

        List<ListScooterResult> resultList = new ArrayList<>();
        scooterService.scooterInfor(scooterIdList).forEach(scooter -> {
            ListScooterResult scooterResult = ListScooterResult.builder()
                    .id(scooter.getId())
                    .battery(scooter.getBattery())
                    .scooterLicense(scooter.getLicensePlate())
                    .build();
            resultList.add(scooterResult);
        });

        return resultList;
    }

    /**
     * 司机车辆分配
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult assignScooter(AssignScooterEnter enter) {

        CorDriver driver = driverMapper.selectById(enter.getDriverId());

        if (driver == null) {
            throw new SesWebDeliveryException(ExceptionCodeEnums.DRIVER_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.DRIVER_IS_NOT_EXIST.getMessage());
        }

        if (StringUtils.equals(driver.getStatus(), DriverStatusEnum.DEPARTURE.getCode())) {
            throw new SesWebDeliveryException(ExceptionCodeEnums.DRIVER_STATUS_IS_DEPARTURE.getCode(), ExceptionCodeEnums.DRIVER_STATUS_IS_DEPARTURE.getMessage());
        }
        if (StringUtils.equals(driver.getStatus(), DriverStatusEnum.WORKING.getCode())) {
            throw new SesWebDeliveryException(ExceptionCodeEnums.DRIVER_STATUS_IS_WORKING.getCode(), ExceptionCodeEnums.DRIVER_STATUS_IS_WORKING.getMessage());
        }

        QueryWrapper<CorDriverScooter> wrapper = new QueryWrapper<>();
        wrapper.eq(CorDriverScooter.COL_DRIVER_ID, enter.getDriverId());
        wrapper.eq(CorDriverScooter.COL_TENANT_ID, enter.getTenantId());
        wrapper.in(CorDriverScooter.COL_STATUS, ScooterStatusEnums.USED.getValue(), ScooterStatusEnums.ALLOCATION.getValue());
        Integer count = driverScooterMapper.selectCount(wrapper);

        if (count > 0) {
            throw new SesWebDeliveryException(ExceptionCodeEnums.DRIVER_STATUS_IS_WORKING.getCode(), ExceptionCodeEnums.DRIVER_STATUS_IS_WORKING.getMessage());
        }

        driver.setStatus(DriverStatusEnum.WORKING.getValue());
        driver.setUpdatedTime(new Date());
        driver.setUpdatedBy(enter.getUserId());
        driverMapper.updateById(driver);

        CorTenantScooter update = new CorTenantScooter();
        update.setStatus(TenantScooterStatusEnums.USEING.getValue());
        update.setUpdatedBy(enter.getUserId());
        update.setUpdatedTime(new Date());

        QueryWrapper<CorTenantScooter> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(CorTenantScooter.COL_DR, 0);
        queryWrapper.eq(CorTenantScooter.COL_TENANT_ID, enter.getTenantId());
        queryWrapper.eq(CorTenantScooter.COL_SCOOTER_ID, enter.getScooterId());
        tenantScooterMapper.update(update, queryWrapper);

        CorDriverScooter insertDriverScooter = new CorDriverScooter();
        insertDriverScooter.setId(idAppService.getId(SequenceName.COR_DRIVER_SCOOTER));
        insertDriverScooter.setDr(0);
        insertDriverScooter.setTenantId(enter.getTenantId());
        insertDriverScooter.setDriverId(enter.getDriverId());
        insertDriverScooter.setScooterId(enter.getScooterId());
        insertDriverScooter.setBeginTime(new Date());
        insertDriverScooter.setStatus(ScooterStatusEnums.ALLOCATION.getValue());
        insertDriverScooter.setCreatedBy(enter.getUserId());
        insertDriverScooter.setCreatedTime(new Date());
        insertDriverScooter.setUpdatedBy(enter.getDriverId());
        insertDriverScooter.setUpdatedTime(new Date());
        driverScooterMapper.insert(insertDriverScooter);

        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 司机归还车辆
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult removeScooter(IdEnter enter) {

        CorDriver driver = driverMapper.selectById(enter.getId());

        if (driver == null) {
            throw new SesWebDeliveryException(ExceptionCodeEnums.DRIVER_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.DRIVER_IS_NOT_EXIST.getMessage());
        }
        if (StringUtils.equals(driver.getStatus(), DriverStatusEnum.OFFWORK.getCode())) {
            throw new SesWebDeliveryException(ExceptionCodeEnums.DRIVER_STATUS_IS_OFFWORK.getCode(), ExceptionCodeEnums.DRIVER_STATUS_IS_OFFWORK.getMessage());
        }

        QueryWrapper<CorDriverScooter> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(CorDriverScooter.COL_DRIVER_ID, enter.getId());
        queryWrapper.in(CorDriverScooter.COL_STATUS, ScooterStatusEnums.USED.getValue(), ScooterStatusEnums.ALLOCATION.getValue());
        queryWrapper.eq(CorDriverScooter.COL_TENANT_ID, enter.getTenantId());
        CorDriverScooter driverScooter = driverScooterMapper.selectOne(queryWrapper);
        if (driverScooter == null) {
            throw new SesWebDeliveryException(ExceptionCodeEnums.DRIVER_HAS_NOT_AVAILABLE_SCOOTER.getCode(), ExceptionCodeEnums.DRIVER_HAS_NOT_AVAILABLE_SCOOTER.getMessage());
        }
        /**
         * TODO 验证订单是否已完成，目前没有，暂时空缺
         */
        driverScooter.setStatus(ScooterStatusEnums.FINSH.getValue());
        driverScooter.setEndTime(new Date());
        driverScooter.setUpdatedBy(enter.getUserId());
        driverScooter.setUpdatedTime(new Date());
        driverScooterMapper.updateById(driverScooter);

        driver.setStatus(DriverStatusEnum.OFFWORK.getCode());
        driver.setUpdatedBy(enter.getUserId());
        driver.setUpdatedTime(new Date());
        driverMapper.updateById(driver);

        return new GeneralResult(enter.getRequestId());
    }
}
