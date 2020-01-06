package com.redescooter.ses.web.delivery.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.enums.delivery.DeliveryStatusEnums;
import com.redescooter.ses.api.common.enums.driver.DriverStatusEnum;
import com.redescooter.ses.api.common.enums.driver.RoleEnums;
import com.redescooter.ses.api.common.enums.scooter.DriverScooterStatusEnums;
import com.redescooter.ses.api.common.enums.tenant.TenantScooterStatusEnums;
import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.api.foundation.service.base.AccountBaseService;
import com.redescooter.ses.api.foundation.vo.account.SaveDriverAccountDto;
import com.redescooter.ses.api.scooter.service.ScooterService;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.starter.redis.RedisLock;
import com.redescooter.ses.tool.utils.DateUtil;
import com.redescooter.ses.web.delivery.constant.SequenceName;
import com.redescooter.ses.web.delivery.dao.DriverServiceMapper;
import com.redescooter.ses.web.delivery.dao.base.*;
import com.redescooter.ses.web.delivery.dm.*;
import com.redescooter.ses.web.delivery.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.delivery.exception.SesWebDeliveryException;
import com.redescooter.ses.web.delivery.service.DriverService;
import com.redescooter.ses.web.delivery.service.base.*;
import com.redescooter.ses.web.delivery.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisCluster;

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
    private JedisCluster jedisCluster;
    @Autowired
    private CorDriverMapper driverMapper;
    @Autowired
    private CorDriverService driverService;
    @Autowired
    private CorTenantScooterMapper corTenantScooterMapper;
    @Autowired
    private CorUserProfileMapper profileMapper;
    @Autowired
    private DriverServiceMapper driverServiceMapper;
    @Autowired
    private CorDriverScooterMapper driverScooterMapper;
    @Autowired
    private CorDriverScooterService driverScooterService;
    @Autowired
    private CorTenantScooterMapper tenantScooterMapper;
    @Autowired
    private CorTenantScooterService tenantScooterService;
    @Autowired
    private CorDeliveryMapper deliveryMapper;
    @Autowired
    private CorDeliveryService deliveryService;
    @Autowired
    private CorDriverScooterHistoryMapper corDriverScooterHistoryMapper;
    @Autowired
    private CorDriverScooterHistoryService driverScooterHistoryService;
    @Reference
    private IdAppService idAppService;
    @Reference
    private AccountBaseService accountBaseService;
    @Reference
    private ScooterService scooterService;


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
        if (driver.getDef1() == "true") {
            throw new SesWebDeliveryException(ExceptionCodeEnums.DRIVER_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.DRIVER_IS_NOT_EXIST.getMessage());
        }
        enter.setId(driver.getUserId());
        GeneralResult result = accountBaseService.sendEmailActiv(enter);
        return result;
    }

    /**
     * 创建司机
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult save(SaveDriverEnter enter) {

        //①验证邮箱是否存在
        Boolean aBoolean = accountBaseService.chectMail(enter.getEmail());

        if (!aBoolean) {
            throw new SesWebDeliveryException(ExceptionCodeEnums.ACCOUNT_ALREADY_EXIST.getCode(), ExceptionCodeEnums.ACCOUNT_ALREADY_EXIST.getMessage());
        }

        if (enter.getId() == null || enter.getId() == 0) {
            //创建2B司机账户
            BaseUserResult user = openDriver2BAccout(enter);
            //保存司机
            CorDriver driverSave = new CorDriver();
            driverSave.setId(idAppService.getId(SequenceName.COR_DRIVER));
            driverSave.setDr(0);
            driverSave.setUserId(user.getId());
            driverSave.setTenantId(user.getTenantId());
            driverSave.setStatus(DriverStatusEnum.OFFWORK.getValue());
            //司机账号是否激活
            driverSave.setDef1(Boolean.FALSE.toString());
            driverSave.setCreatedBy(enter.getUserId());
            driverSave.setCreatedTime(new Date());
            driverSave.setUpdatedBy(enter.getUserId());
            driverSave.setUpdatedTime(new Date());
            driverService.save(driverSave);

            //创建司机分车数据槽
            CorDriverScooter driverScooterSave = new CorDriverScooter();
            driverScooterSave.setId(idAppService.getId(SequenceName.COR_DRIVER_SCOOTER));
            driverScooterSave.setDr(0);
            driverScooterSave.setTenantId(enter.getTenantId());
            driverScooterSave.setDriverId(driverSave.getId());
            driverScooterSave.setStatus(DriverScooterStatusEnums.NORMAL.getValue());
            driverScooterSave.setCreatedBy(enter.getUserId());
            driverScooterSave.setCreatedTime(new Date());
            driverScooterSave.setUpdatedBy(enter.getUserId());
            driverScooterSave.setUpdatedTime(new Date());
            driverScooterMapper.insert(driverScooterSave);

            //保存司机信息
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
            profileSave.setCertificateNegativeAnnex(enter.getDriverLicenseUpAnnex());
            profileSave.setRole(RoleEnums.DRIVER.getValue());
            profileSave.setJoinDate(new Date());
            profileSave.setTimeZone(enter.getTimeZone());
            profileSave.setCreatedBy(enter.getUserId());
            profileSave.setCreatedTime(new Date());
            profileSave.setUpdatedBy(enter.getUserId());
            profileSave.setUpdatedTime(new Date());
            profileMapper.insert(profileSave);

            //发送激活邮件
            IdEnter idEnter = new IdEnter();
            BeanUtils.copyProperties(enter, idEnter);
            idEnter.setId(driverSave.getUserId());
            accountBaseService.sendEmailActiv(idEnter);
        } else {
            CorDriver driver = driverMapper.selectById(enter.getId());
            if (driver == null) {
                new SesWebDeliveryException(ExceptionCodeEnums.ACCOUNT_ALREADY_EXIST.getCode(), ExceptionCodeEnums.ACCOUNT_ALREADY_EXIST.getMessage());
            }
            CorDriver driverUpdate = new CorDriver();
            driverUpdate.setId(enter.getId());
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
                    enter.getDriverLicenseUpAnnex() != null) {
                profile.setCertificateType(enter.getCertificateType());
                profile.setCertificateNegativeAnnex(enter.getDriverLicenseUpAnnex());
            }
            profile.setUpdatedBy(enter.getUserId());
            profile.setUpdatedTime(new Date());
            profileMapper.updateById(profile);
        }
        return new GeneralResult(enter.getRequestId());
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

        if (profile == null) {
            return new DriverDetailsResult();
        }

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
        result.setJoinDate(DateUtil.format(profile.getJoinDate(),DateUtil.DEFAULT_DATE_FORMAT));
        result.setAge(DateUtil.dateCompare(profile.getBirthday(),new Date(),1));
        return result;
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
     * 司机离职
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult leave(IdEnter enter) {

        CorDriver driver = driverService.getById(enter.getId());

        if (driver == null) {
            throw new SesWebDeliveryException(ExceptionCodeEnums.DRIVER_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.DRIVER_IS_NOT_EXIST.getMessage());
        }
        if (driver.getStatus().equals(DriverStatusEnum.DEPARTURE.getValue())) {
            return new GeneralResult(enter.getRequestId());
        }
        //如果下班，那么车辆一定已经还完
        if (driver.getStatus().equals(DriverStatusEnum.WORKING.getValue())) {
            throw new SesWebDeliveryException(ExceptionCodeEnums.PLEASE_OPERATE_AFTER_WORK.getCode(), ExceptionCodeEnums.PLEASE_OPERATE_AFTER_WORK.getMessage());
        }

        driver.setStatus(DriverStatusEnum.DEPARTURE.getValue());
        driver.setUpdatedBy(enter.getUserId());
        driver.setUpdatedTime(new Date());
        driverService.removeById(driver.getId());

        //关闭账号
        accountBaseService.cancelDriver2BAccout(new IdEnter(driver.getUserId()));

        QueryWrapper<CorUserProfile> profileQueryWrapper = new QueryWrapper<>();
        profileQueryWrapper.eq(CorUserProfile.COL_DR, 0);
        profileQueryWrapper.eq(CorUserProfile.COL_USER_ID, driver.getUserId());
        CorUserProfile userProfile = profileMapper.selectOne(profileQueryWrapper);

        if (userProfile != null) {
            profileMapper.deleteById(userProfile.getId());
        }

        QueryWrapper<CorDriverScooter> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(CorDriverScooter.COL_DRIVER_ID, enter.getId());
        queryWrapper.eq(CorDriverScooter.COL_DR, 0);
        queryWrapper.ge(CorDriverScooter.COL_TENANT_ID, enter.getTenantId());
        queryWrapper.last("LIMIT 1");
        CorDriverScooter scooterServiceOne = driverScooterService.getOne(queryWrapper);
        if (scooterServiceOne != null) {
            driverScooterService.removeById(scooterServiceOne.getId());
        }

        return new GeneralResult(enter.getRequestId());
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
        corTenantScooterQueryWrapper.eq(CorTenantScooter.COL_STATUS, TenantScooterStatusEnums.AVAILABLE.getValue());
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

        RedisLock.getInstance(jedisCluster).lock(String.valueOf(enter.getScooterId()));
        try {
            CorDriver driver = driverService.getById(enter.getDriverId());

            if (driver == null) {
                throw new SesWebDeliveryException(ExceptionCodeEnums.DRIVER_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.DRIVER_IS_NOT_EXIST.getMessage());
            }
            if (StringUtils.equals(driver.getStatus(), DriverStatusEnum.DEPARTURE.getValue())) {
                throw new SesWebDeliveryException(ExceptionCodeEnums.DRIVER_STATUS_IS_DEPARTURE.getCode(), ExceptionCodeEnums.DRIVER_STATUS_IS_DEPARTURE.getMessage());
            }
            if (StringUtils.equals(driver.getStatus(), DriverStatusEnum.WORKING.getValue())) {
                throw new SesWebDeliveryException(ExceptionCodeEnums.DRIVER_STATUS_IS_WORKING.getCode(), ExceptionCodeEnums.DRIVER_STATUS_IS_WORKING.getMessage());
            }

            QueryWrapper<CorDriverScooter> driverScooterQueryWrapper = new QueryWrapper<>();
            driverScooterQueryWrapper.eq(CorDriverScooter.COL_DRIVER_ID, enter.getDriverId());
            driverScooterQueryWrapper.eq(CorDriverScooter.COL_TENANT_ID, enter.getTenantId());
            driverScooterQueryWrapper.eq(CorDriverScooter.COL_DR, 0);
            CorDriverScooter driverScooterOne = driverScooterService.getOne(driverScooterQueryWrapper);

            if (driverScooterOne == null) {
                throw new SesWebDeliveryException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
            }

            //先进行分配车辆，后更改司机状态
            driverScooterOne.setStatus(DriverScooterStatusEnums.USED.getValue());
            driverScooterOne.setScooterId(enter.getScooterId());
            driverScooterOne.setBeginTime(new Date());
            driverScooterOne.setEndTime(null);
            driverScooterOne.setUpdatedBy(enter.getDriverId());
            driverScooterOne.setUpdatedTime(new Date());
            driverScooterMapper.updateById(driverScooterOne);

            //加入分车历史记录
            CorDriverScooterHistory driverScooterHistory = new CorDriverScooterHistory();
            driverScooterHistory.setId(idAppService.getId(SequenceName.COR_DRIVER_SCOOTER));
            driverScooterHistory.setDr(0);
            driverScooterHistory.setTenantId(enter.getTenantId());
            driverScooterHistory.setDriverId(driver.getId());
            driverScooterHistory.setScooterId(driverScooterOne.getScooterId());
            driverScooterHistory.setBeginTime(driverScooterOne.getBeginTime());
            driverScooterHistory.setStatus(driverScooterOne.getStatus());
            driverScooterHistory.setCreatedBy(enter.getUserId());
            driverScooterHistory.setCreatedTime(new Date());
            driverScooterHistory.setUpdatedBy(enter.getUserId());
            driverScooterHistory.setUpdatedTime(new Date());
            driverScooterHistoryService.save(driverScooterHistory);

            CorTenantScooter updateTenantScooter = new CorTenantScooter();
            updateTenantScooter.setStatus(TenantScooterStatusEnums.USEING.getValue());
            updateTenantScooter.setUpdatedBy(enter.getUserId());
            updateTenantScooter.setUpdatedTime(new Date());

            QueryWrapper<CorTenantScooter> scooterQueryWrapper = new QueryWrapper<>();
            scooterQueryWrapper.eq(CorTenantScooter.COL_DR, 0);
            scooterQueryWrapper.eq(CorTenantScooter.COL_TENANT_ID, enter.getTenantId());
            scooterQueryWrapper.eq(CorTenantScooter.COL_SCOOTER_ID, enter.getScooterId());
            tenantScooterService.update(updateTenantScooter, scooterQueryWrapper);

            //更新司机状态
            driver.setStatus(DriverStatusEnum.WORKING.getValue());
            driver.setUpdatedTime(new Date());
            driver.setUpdatedBy(enter.getUserId());
            driverMapper.updateById(driver);

        } catch (SesWebDeliveryException e) {
            RedisLock.getInstance(jedisCluster).unlock(String.valueOf(enter.getScooterId()));
            throw new SesWebDeliveryException(ExceptionCodeEnums.NON_REPEATABLE.getCode(), ExceptionCodeEnums.NON_REPEATABLE.getMessage());
        } finally {
            RedisLock.getInstance(jedisCluster).unlock(String.valueOf(enter.getScooterId()));
        }
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

        CorDriver driver = driverService.getById(enter.getId());

        if (driver == null) {
            throw new SesWebDeliveryException(ExceptionCodeEnums.DRIVER_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.DRIVER_IS_NOT_EXIST.getMessage());
        }
        //验证订单是否已完结状态
        QueryWrapper<CorDelivery> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(CorDelivery.COL_DELIVERER_ID, driver.getUserId());
        queryWrapper.eq(CorDelivery.COL_TENANT_ID, enter.getTenantId());
        queryWrapper.eq(CorDelivery.COL_DR, 0);
        queryWrapper.in(CorDelivery.COL_STATUS, DeliveryStatusEnums.PENDING.getValue(), DeliveryStatusEnums.DELIVERING.getValue(), DeliveryStatusEnums.TIMEOUT_WARNING.getValue());
        int count = deliveryService.count(queryWrapper);
        if (count != 0) {
            throw new SesWebDeliveryException(ExceptionCodeEnums.YOU_HAVE_AN_ORDER_IN_PROGRESS.getCode(), ExceptionCodeEnums.YOU_HAVE_AN_ORDER_IN_PROGRESS.getMessage());
        }

        //不验证司机状态，进行强制换车
        QueryWrapper<CorDriverScooter> driverScooterQueryWrapper = new QueryWrapper<>();
        driverScooterQueryWrapper.eq(CorDriverScooter.COL_DRIVER_ID, enter.getId());
        driverScooterQueryWrapper.eq(CorDriverScooter.COL_TENANT_ID, enter.getTenantId());
        driverScooterQueryWrapper.eq(CorDriverScooter.COL_DR, 0);
        driverScooterQueryWrapper.last("LIMIT 1");
        CorDriverScooter driverScooterOne = driverScooterService.getOne(driverScooterQueryWrapper);

        long scooterId = driverScooterOne.getScooterId() == null ? 0 : driverScooterOne.getScooterId();

        driverScooterOne.setStatus(DriverScooterStatusEnums.FINSH.getValue());
        driverScooterOne.setScooterId(new Long("0"));
        driverScooterOne.setEndTime(null);
        driverScooterOne.setBeginTime(null);
        driverScooterOne.setUpdatedBy(enter.getUserId());
        driverScooterOne.setUpdatedTime(new Date());
        driverScooterService.updateById(driverScooterOne);

        //加入分车历史记录
        CorDriverScooterHistory driverScooterHistory = new CorDriverScooterHistory();
        driverScooterHistory.setId(idAppService.getId(SequenceName.COR_DRIVER_SCOOTER));
        driverScooterHistory.setDr(0);
        driverScooterHistory.setStatus(driverScooterOne.getStatus());
        driverScooterHistory.setTenantId(enter.getTenantId());
        driverScooterHistory.setDriverId(driver.getId());
        driverScooterHistory.setScooterId(scooterId);
        driverScooterHistory.setBeginTime(driverScooterOne.getBeginTime());
        driverScooterHistory.setEndTime(driverScooterOne.getEndTime());
        driverScooterHistory.setCreatedBy(enter.getUserId());
        driverScooterHistory.setCreatedTime(new Date());
        driverScooterHistory.setUpdatedBy(enter.getId());
        driverScooterHistory.setUpdatedTime(new Date());
        driverScooterHistoryService.save(driverScooterHistory);


        QueryWrapper<CorTenantScooter> tenantScooterQueryWrapper = new QueryWrapper<>();
        tenantScooterQueryWrapper.eq(CorTenantScooter.COL_SCOOTER_ID, scooterId);
        tenantScooterQueryWrapper.eq(CorTenantScooter.COL_TENANT_ID, enter.getTenantId());
        tenantScooterQueryWrapper.eq(CorTenantScooter.COL_DR, 0);

        CorTenantScooter updateTenantScooter = new CorTenantScooter();
        updateTenantScooter.setStatus(TenantScooterStatusEnums.AVAILABLE.getValue());
        updateTenantScooter.setUpdatedBy(enter.getUserId());
        updateTenantScooter.setUpdatedTime(new Date());
        tenantScooterService.update(updateTenantScooter, tenantScooterQueryWrapper);


        driver.setStatus(DriverStatusEnum.OFFWORK.getValue());
        driver.setUpdatedBy(enter.getUserId());
        driver.setUpdatedTime(new Date());
        driverMapper.updateById(driver);

        return new GeneralResult(enter.getRequestId());
    }

}
