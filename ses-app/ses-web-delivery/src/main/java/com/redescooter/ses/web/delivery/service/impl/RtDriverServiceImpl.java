package com.redescooter.ses.web.delivery.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.enums.delivery.DeliveryStatusEnums;
import com.redescooter.ses.api.common.enums.driver.DriverLoginTypeEnum;
import com.redescooter.ses.api.common.enums.driver.DriverStatusEnum;
import com.redescooter.ses.api.common.enums.driver.RoleEnums;
import com.redescooter.ses.api.common.enums.scooter.DriverScooterStatusEnums;
import com.redescooter.ses.api.common.enums.tenant.TenantScooterStatusEnums;
import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.api.common.vo.scooter.BaseScooterResult;
import com.redescooter.ses.api.foundation.service.base.AccountBaseService;
import com.redescooter.ses.api.foundation.service.base.TenantBaseService;
import com.redescooter.ses.api.foundation.service.base.UserBaseService;
import com.redescooter.ses.api.foundation.vo.account.SaveDriverAccountDto;
import com.redescooter.ses.api.foundation.vo.user.QueryUserResult;
import com.redescooter.ses.api.scooter.service.ScooterService;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.starter.redis.RedisLock;
import com.redescooter.ses.tool.utils.DateUtil;
import com.redescooter.ses.web.delivery.constant.SequenceName;
import com.redescooter.ses.web.delivery.dao.DriverServiceMapper;
import com.redescooter.ses.web.delivery.dao.EdScooterServiceMapper;
import com.redescooter.ses.web.delivery.dao.base.CorTenantScooterMapper;
import com.redescooter.ses.web.delivery.dm.*;
import com.redescooter.ses.web.delivery.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.delivery.exception.SesWebDeliveryException;
import com.redescooter.ses.web.delivery.service.RtDriverService;
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

import java.math.BigDecimal;
import java.util.*;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 2/1/2020 6:57 上午
 * @ClassName: RtDriverServiceImpl
 * @Function: TODO
 */
@Slf4j
@Service
public class RtDriverServiceImpl implements RtDriverService {
    @Autowired
    private JedisCluster jedisCluster;
    @Autowired
    private CorDriverService driverService;
    @Autowired
    private CorTenantScooterMapper corTenantScooterMapper;
    @Autowired
    private CorUserProfileService userProfileService;
    @Autowired
    private CorDriverScooterService driverScooterService;
    @Autowired
    private CorTenantScooterService tenantScooterService;
    @Autowired
    private CorDeliveryService deliveryService;
    @Autowired
    private CorDeliveryTraceService deliveryTraceService;
    @Autowired
    private CorDriverScooterHistoryService driverScooterHistoryService;
    @Autowired
    private CorScooterRideStatService scooterRideStatService;
    @Autowired
    private DriverServiceMapper driverServiceMapper;
    @Autowired
    private EdScooterServiceMapper scooterServiceMapper;
    @Reference
    private IdAppService idAppService;
    @Reference
    private AccountBaseService accountBaseService;
    @Reference
    private UserBaseService userBaseService;
    @Reference
    private ScooterService scooterService;
    @Reference
    private TenantBaseService tenantBaseService;


    /**
     * 再次发生激活邮件2B
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult againSendEmail(IdEnter enter) {
        CorDriver driver = driverService.getById(enter.getId());

        if (driver == null) {
            throw new SesWebDeliveryException(ExceptionCodeEnums.DRIVER_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.DRIVER_IS_NOT_EXIST.getMessage());
        }
        if (driver.getStatus().equals(DriverStatusEnum.DEPARTURE.getValue())) {
            throw new SesWebDeliveryException(ExceptionCodeEnums.DRIVER_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.DRIVER_IS_NOT_EXIST.getMessage());
        }
        if ("true".equals(driver.getDef1())) {
            throw new SesWebDeliveryException(ExceptionCodeEnums.ACCOUNT_IS_ACTIVATED.getCode(), ExceptionCodeEnums.ACCOUNT_IS_ACTIVATED.getMessage());
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

        Boolean aBoolean = Boolean.FALSE;

        if (enter.getId() == null || enter.getId() == 0) {
            if (enter.getDriverLoginType().equals(DriverLoginTypeEnum.EMAIL.getValue())) {
                //①验证邮箱是否存在
                aBoolean = accountBaseService.chectMail(enter.getEmail());
            } else if (enter.getDriverLoginType().equals(DriverLoginTypeEnum.NICKNAME.getValue())) {
                if (enter.getNickName().contains("@")) {
                    throw new SesWebDeliveryException(ExceptionCodeEnums.ILLEGAL_NICKNAME.getCode(), ExceptionCodeEnums.ILLEGAL_NICKNAME.getMessage());
                }
                //验证租户与手机用户不可通用
                Boolean tenantBoolean = accountBaseService.chectMail(enter.getNickName());
                //验证骑手非邮箱账号用户名是否存在
                Boolean userBoolean = accountBaseService.checkNaickname(enter.getNickName());
                aBoolean = (tenantBoolean.equals(Boolean.TRUE) && userBoolean.equals(Boolean.TRUE)) ? Boolean.TRUE : Boolean.FALSE;
            } else {
                throw new SesWebDeliveryException(ExceptionCodeEnums.OPERATION_ERROR.getCode(), ExceptionCodeEnums.OPERATION_ERROR.getMessage());
            }

            if (!aBoolean) {
                throw new SesWebDeliveryException(ExceptionCodeEnums.ACCOUNT_ALREADY_EXIST.getCode(), ExceptionCodeEnums.ACCOUNT_ALREADY_EXIST.getMessage());
            }
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
            driverSave.setDef1(enter.getDriverLoginType().equals(DriverLoginTypeEnum.EMAIL.getValue()) ? Boolean.FALSE.toString() : Boolean.TRUE.toString());
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
            driverScooterService.save(driverScooterSave);

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
            if (enter.getDriverLoginType().equals(DriverLoginTypeEnum.EMAIL.getValue())) {
                profileSave.setEmail1(enter.getEmail());
            } else {
                profileSave.setNickname(enter.getNickName());
            }
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
            userProfileService.save(profileSave);

            if (enter.getDriverLoginType().equals(DriverLoginTypeEnum.EMAIL.getValue())) {
                //发送激活邮件
                IdEnter idEnter = new IdEnter();
                BeanUtils.copyProperties(enter, idEnter);
                idEnter.setId(driverSave.getUserId());
                accountBaseService.sendEmailActiv(idEnter);
            }

            //维护租户的司机数量
            tenantBaseService.updateDriverCount(enter);
        } else {
            CorDriver driver = driverService.getById(enter.getId());
            if (driver == null) {
                throw new SesWebDeliveryException(ExceptionCodeEnums.ACCOUNT_ALREADY_EXIST.getCode(), ExceptionCodeEnums.ACCOUNT_ALREADY_EXIST.getMessage());
            }
            CorDriver driverUpdate = new CorDriver();
            driverUpdate.setId(enter.getId());
            driverUpdate.setUpdatedBy(enter.getUserId());
            driverUpdate.setUpdatedTime(new Date());
            driverService.updateById(driverUpdate);

            QueryWrapper<CorUserProfile> wrapper = new QueryWrapper<>();
            wrapper.eq(CorUserProfile.COL_USER_ID, driver.getUserId());
            wrapper.eq(CorUserProfile.COL_DR, 0);

            CorUserProfile profile = userProfileService.getOne(wrapper);
            profile.setPicture(enter.getAvatar());
            profile.setGender(enter.getGender());
            profile.setTelNumber1(enter.getDriverPhone());
            profile.setBirthday(DateUtil.parse(enter.getBirthday(), DateUtil.DEFAULT_DATETIME_FORMAT));
            profile.setPlaceBirth(enter.getAddress());
            profile.setFirstName(enter.getDriverFirstName());
            profile.setLastName(enter.getDriverLastName());
            profile.setFullName(new StringBuffer().append(enter.getDriverFirstName()).append(" ").append(enter.getDriverLastName()).toString());
            profile.setBirthday(DateUtil.timaConversion(enter.getBirthday()));
            if (enter.getCertificateType() != null &&
                    enter.getDriverLicenseUpAnnex() != null) {
                profile.setCertificateType(enter.getCertificateType());
                profile.setCertificateNegativeAnnex(enter.getDriverLicenseUpAnnex());
            }
            profile.setUpdatedBy(enter.getUserId());
            profile.setUpdatedTime(new Date());
            userProfileService.updateById(profile);
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

        log.info("司机的主键为===={}", enter.getId());
        CorDriver driver = driverService.getById(enter.getId());

        if (driver == null) {
            return new DriverDetailsResult();
        }

        QueryWrapper<CorUserProfile> wrapper = new QueryWrapper<>();
        wrapper.eq(CorUserProfile.COL_USER_ID, driver.getUserId());
        wrapper.eq(CorUserProfile.COL_DR, 0);
        CorUserProfile profile = userProfileService.getOne(wrapper);

        if (profile == null) {
            return new DriverDetailsResult();
        }

        QueryUserResult userResult = userBaseService.queryUserById(new IdEnter(driver.getUserId()));

        DriverDetailsResult result = new DriverDetailsResult();
        result.setId(driver.getId());
        result.setDriverLoginType(profile.getEmail1() == null ? DriverLoginTypeEnum.NICKNAME.getValue() : DriverLoginTypeEnum.EMAIL.getValue());
        result.setAvatar(profile.getPicture());
        result.setDriverFirstName(profile.getFirstName());
        result.setDriverLastName(profile.getLastName());
        result.setGender(profile.getGender());
        result.setCountryCodel(profile.getCountryCode1());
        result.setDriverPhone(profile.getTelNumber1());
        result.setEmail(profile.getEmail1());
        result.setNickName(profile.getNickname());
        result.setAddress(profile.getPlaceBirth());
        result.setBirthday(DateUtil.getDateTime(profile.getBirthday(), null));
        result.setPlateNumber(null);
        result.setUserType(userResult.getUserType());
        result.setCertificateType(profile.getCertificateType());
        result.setDriverLicenseDownAnnex(profile.getCertificatePositiveAnnex());
        result.setDriverLicenseUpAnnex(profile.getCertificateNegativeAnnex());
        result.setRequestId(enter.getRequestId());
        result.setJoinDate(profile.getJoinDate());
        result.setAge(DateUtil.dateCompare(profile.getBirthday(), new Date(), 1));
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
        return accountBaseService.openDriver2BAccout(dto);
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
        CorUserProfile userProfile = userProfileService.getOne(profileQueryWrapper);

        if (userProfile != null) {
            userProfileService.removeById(userProfile.getId());
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
     * @param enter
     * @Description
     * @Author: AlexLi
     * @Date: 2020/2/13 10:23
     * @Param: enter
     * @Return: map
     * @desc: 车辆类型
     */
    @Override
    public ScooterModelListResult scooterModelList(GeneralEnter enter) {
        List<String> modelList = driverServiceMapper.queryScooterModelByTenantId(enter.getTenantId());
        return ScooterModelListResult.builder().modelList(modelList).build();
    }

    /**
     * 门店车辆列表
     *
     * @param enter
     * @return
     */
    @Override
    public List<ListScooterResult> scooterList(ScooterListEnter enter) {
        QueryWrapper<CorTenantScooter> corTenantScooterQueryWrapper = new QueryWrapper<>();
        corTenantScooterQueryWrapper.eq(CorTenantScooter.COL_TENANT_ID, enter.getTenantId());
        corTenantScooterQueryWrapper.eq(CorTenantScooter.COL_DR, 0);
        corTenantScooterQueryWrapper.eq(CorTenantScooter.COL_MODEL, enter.getModelId());
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

            // 车辆验证
            QueryWrapper<CorDriverScooter> corDriverScooterQueryWrapper = new QueryWrapper<>();
            corDriverScooterQueryWrapper.eq(CorDriverScooter.COL_SCOOTER_ID, enter.getScooterId());
            corDriverScooterQueryWrapper.eq(CorDriverScooter.COL_TENANT_ID, enter.getTenantId());
            corDriverScooterQueryWrapper.eq(CorDriverScooter.COL_DR, 0);
            corDriverScooterQueryWrapper.eq(CorDriverScooter.COL_STATUS,DriverScooterStatusEnums.USED.getValue());
            CorDriverScooter driverScooter = driverScooterService.getOne(driverScooterQueryWrapper);
            if (driverScooter!=null){
                throw new SesWebDeliveryException(ExceptionCodeEnums.STATUS_IS_UNAVAILABLE.getCode(),ExceptionCodeEnums.STATUS_IS_UNAVAILABLE.getMessage());
            }

            //先进行分配车辆，后更改司机状态
            driverScooterOne.setStatus(DriverScooterStatusEnums.USED.getValue());
            driverScooterOne.setScooterId(enter.getScooterId());
            driverScooterOne.setBeginTime(new Date());
            driverScooterOne.setEndTime(null);
            driverScooterOne.setUpdatedBy(enter.getDriverId());
            driverScooterOne.setUpdatedTime(new Date());
            driverScooterService.updateById(driverScooterOne);

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
            driverService.updateById(driver);

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
        queryWrapper.in(CorDelivery.COL_STATUS, DeliveryStatusEnums.PENDING.getValue(), DeliveryStatusEnums.DELIVERING.getValue());
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
        BigDecimal mileage = driverServiceMapper.queryScooterMileage(enter, driverScooterOne.getBeginTime());
        if (null == mileage) {
            mileage = BigDecimal.ZERO;
        }
        // 统计配送订单的行驶里程
        //加入分车历史记录
        CorDriverScooterHistory driverScooterHistory = new CorDriverScooterHistory();
        driverScooterHistory.setId(idAppService.getId(SequenceName.COR_DRIVER_SCOOTER));
        driverScooterHistory.setDr(0);
        driverScooterHistory.setStatus(DriverScooterStatusEnums.FINSH.getValue());
        driverScooterHistory.setTenantId(enter.getTenantId());
        driverScooterHistory.setDriverId(driver.getId());
        driverScooterHistory.setScooterId(scooterId);
        driverScooterHistory.setBeginTime(driverScooterOne.getBeginTime());
        driverScooterHistory.setEndTime(new Date());
        driverScooterHistory.setMileage(mileage.toString());
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
        driverService.updateById(driver);

        driverScooterOne.setStatus(DriverScooterStatusEnums.FINSH.getValue());
        driverScooterOne.setScooterId(new Long("0"));
        driverScooterOne.setEndTime(null);
        driverScooterOne.setBeginTime(null);
        driverScooterOne.setUpdatedBy(enter.getUserId());
        driverScooterOne.setUpdatedTime(new Date());
        driverScooterService.updateById(driverScooterOne);

        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 司机已配送订单状态统计
     *
     * @return
     */
    @Override
    public Map<String, Integer> driverDeliveryCountByStatus(IdEnter enter) {

        CorDriver corDriver = driverService.getById(enter.getId());

        if (corDriver == null) {
            throw new SesWebDeliveryException(ExceptionCodeEnums.DELIVERY_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.DELIVERY_IS_NOT_EXIST.getMessage());
        }

        List<CountByStatusResult> countByStatusResults = driverServiceMapper.driverDeliveryCountByStatus(enter);

        Map<String, Integer> map = new HashMap<>();
        for (CountByStatusResult item : countByStatusResults) {
            map.put(item.getStatus(), item.getTotalCount());
        }
        for (DeliveryStatusEnums status : DeliveryStatusEnums.values()) {
            if (!map.containsKey(status.getValue())) {
                map.put(status.getValue(), 0);
            }
        }
        map.remove(DeliveryStatusEnums.REJECTED.getValue());

        QueryWrapper<CorDeliveryTrace> corDeliveryTraceQueryWrapper = new QueryWrapper<>();
        corDeliveryTraceQueryWrapper.eq(CorDeliveryTrace.COL_USER_ID, corDriver.getUserId());
        corDeliveryTraceQueryWrapper.eq(CorDeliveryTrace.COL_DR, 0);
        corDeliveryTraceQueryWrapper.eq(CorDeliveryTrace.COL_STATUS, DeliveryStatusEnums.REJECTED.getValue());

        int count = deliveryTraceService.count(corDeliveryTraceQueryWrapper);
        map.put(DeliveryStatusEnums.REJECTED.getValue(), count);
        return map;
    }

    /**
     * 车辆 信息
     *
     * @param enter
     * @return
     */
    @Override
    public DriverScooterInforResult driverScooterInfor(IdEnter enter) {

        QueryWrapper<CorDriverScooter> corDriverScooterQueryWrapper = new QueryWrapper<>();
        corDriverScooterQueryWrapper.eq(CorDriverScooter.COL_DR, 0);
        corDriverScooterQueryWrapper.eq(CorDriverScooter.COL_DRIVER_ID, enter.getId());
        corDriverScooterQueryWrapper.eq(CorDriverScooter.COL_STATUS, DriverScooterStatusEnums.USED.getValue());
        CorDriverScooter corDriverScooter = driverScooterService.getOne(corDriverScooterQueryWrapper);

        if (corDriverScooter == null) {
            return null;
        }
        List<Long> scooterIdList = new ArrayList<>();
        scooterIdList.add(corDriverScooter.getScooterId());
        List<BaseScooterResult> scooterResultList = scooterService.scooterInfor(scooterIdList);

        QueryWrapper<CorScooterRideStat> corScooterRideStatQueryWrapper = new QueryWrapper<>();
        corScooterRideStatQueryWrapper.eq(CorScooterRideStat.COL_SCOOTER_ID, scooterResultList.get(0).getId());
        corScooterRideStatQueryWrapper.eq(CorScooterRideStat.COL_DR, 0);
        CorScooterRideStat corScooterRideStat = scooterRideStatService.getOne(corScooterRideStatQueryWrapper);

        return DriverScooterInforResult.builder()
                .id(scooterResultList.get(0).getId())
                .battery(scooterResultList.get(0).getBattery())
                .licensePlate(scooterResultList.get(0).getLicensePlate())
                .mileage(corScooterRideStat == null ? "0" : corScooterRideStat.getTotalMileage().toString())
                .build();
    }

    /**
     * 订单历史
     *
     * @param enter
     * @return
     */
    @Override
    public PageResult<DeliveryHistroyResult> deliveryHistroy(DeliveryHistroyEnter enter) {

        int count = driverServiceMapper.deliveryHistroyCount(enter);
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        List<DeliveryHistroyResult> deliveryHistroyList = driverServiceMapper.deliveryHistroyList(enter);
        return PageResult.create(enter, count, deliveryHistroyList);
    }

    /**
     * 司机车辆骑行分配记录
     *
     * @param enter
     * @return
     */
    @Override
    public PageResult<DriverScooterHistoryResult> driverscooterHistroy(DriverScooterHistroyEnter enter) {
        int total = 0;

        int usedCount = driverServiceMapper.driverScooterUsedHistroyCount(enter);

        int usingCount = driverServiceMapper.driverScooterUsingHistroyCount(enter);

        total = usedCount + usingCount;
        if (total == 0) {
            return PageResult.createZeroRowResult(enter);
        }

        List<DriverScooterHistoryResult> resultList = new ArrayList<>();

        List<DriverScooterHistoryResult> driverScooterUsedHistoryList = driverServiceMapper.driverScooterUsedHistroyList(enter);

        List<DriverScooterHistoryResult> driverScooterUsingHistoryList = driverServiceMapper.driverScooterUsingHistroyList(enter);

        if (CollectionUtils.isNotEmpty(driverScooterUsedHistoryList)) {
            resultList.addAll(driverScooterUsedHistoryList);
        }
        if (CollectionUtils.isNotEmpty(driverScooterUsingHistoryList)) {
            if (CollectionUtils.isNotEmpty(resultList)) {
                resultList.addAll(0, driverScooterUsingHistoryList);
            }
            if (CollectionUtils.isEmpty(resultList)) {
                resultList.addAll(driverScooterUsingHistoryList);
            }
        }
        return PageResult.create(enter, total, resultList);
    }

    /**
     * 司机仪表盘订单柱状图
     *
     * @param enter
     * @return
     */
    @Override
    public DeliveryChartListResult driverDeliveryChartList(DeliveryChartEnter enter) {
        if (enter.getId() == 0) {
            throw new SesWebDeliveryException(ExceptionCodeEnums.ID_IS_EMPTY.getCode(), ExceptionCodeEnums.ID_IS_EMPTY.getMessage());
        }
        Map<String, DeliveryChartResult> map = new LinkedHashMap<>();
        List<DeliveryChartResult> deliveryChartResults = new ArrayList<>();
        Double max = 0.00, avg = 0.00, min = 0.00;

        //天数
        int heavens = enter.getHeavens() == 0 ? 1 : enter.getHeavens();
        enter.setHeavens(heavens);
        enter.setDateTimes(enter.getDateTimes() == null ? new Date() : enter.getDateTimes());
        switch (heavens) {
            case 1:
                //今日Today（单位为小时，显示今日配送数据）
                DeliveryChartDto dateTimeParmToday = new DeliveryChartDto();

                BeanUtils.copyProperties(enter, dateTimeParmToday);
                dateTimeParmToday.setDateTime(enter.getDateTimes());
                deliveryChartResults = driverServiceMapper.driverDeliveryChartToday(dateTimeParmToday);
                break;

            case 7:
                //近七日<7Day（单位为日，显示近7天配送数据，点击某一日可查看该日数据，并且时间筛选变更为返回）
                Date start7 = DateUtil.addDays(enter.getDateTimes(), -7);
                DeliveryChartDto dateTimeParm7 = new DeliveryChartDto();

                BeanUtils.copyProperties(enter, dateTimeParm7);
                dateTimeParm7.setEndDateTime(enter.getDateTimes());
                dateTimeParm7.setStartDateTime(start7);

                deliveryChartResults = driverServiceMapper.driverDeliveryChart7Day(dateTimeParm7);
                break;

            case 30:
                //近30日<30Day（单位为日，显示近30天配送数据，点击某一日可查看该日数据，并且时间筛选变更为返回）
                Date start30 = DateUtil.addDays(enter.getDateTimes(), -30);
                DeliveryChartDto dateTimeParm30 = new DeliveryChartDto();

                BeanUtils.copyProperties(enter, dateTimeParm30);
                dateTimeParm30.setEndDateTime(enter.getDateTimes());
                dateTimeParm30.setStartDateTime(start30);
                deliveryChartResults = driverServiceMapper.driverDeliveryChart30Day(dateTimeParm30);
                break;

            case 365:
                //年Year（单位为月，显示截止至今所有数据，点击某一月可查看该月数据，点击某一日可查看该日数据
                Date start365 = DateUtil.addDays(enter.getDateTimes(), -365);
                DeliveryChartDto dateTimeParm365 = new DeliveryChartDto();

                BeanUtils.copyProperties(enter, dateTimeParm365);
                dateTimeParm365.setEndDateTime(enter.getDateTimes());
                dateTimeParm365.setStartDateTime(start365);
                deliveryChartResults = driverServiceMapper.driverDeliveryChart365Day(dateTimeParm365);
                break;

            default:
                throw new SesWebDeliveryException(ExceptionCodeEnums.OPERATION_ERROR.getCode(), ExceptionCodeEnums.OPERATION_ERROR.getMessage());

        }
        List<String> dateList = new LinkedList();
        dateList = getDateList(enter.getHeavens(), enter.getDateTimes());

        if (deliveryChartResults.size() > 0) {
            //获取最大值
            max = deliveryChartResults.stream().mapToDouble(DeliveryChartResult::getTotal).max().getAsDouble();
            //获取平均值
            avg = deliveryChartResults.stream().mapToDouble(DeliveryChartResult::getTotal).average().getAsDouble();
            //取最小值
            min = deliveryChartResults.stream().mapToDouble(DeliveryChartResult::getTotal).min().getAsDouble();

            DeliveryChartResult result = null;

            for (String str : dateList) {
                for (DeliveryChartResult chart : deliveryChartResults) {
                    if (chart.getTimes().equals(str)) {
                        map.put(str, chart);
                    }
                }
                if (!map.containsKey(str)) {
                    result = new DeliveryChartResult();
                    result.setTimes(str);
                    map.put(str, result);
                }
            }

        } else {
            map = null;
        }
        DeliveryChartListResult result = new DeliveryChartListResult();
        result.setMap(map);
        result.setAvg(avg);
        result.setMax(max);
        result.setMin(min);


        return result;
    }

    private List<String> getDateList(int heavens, Date date) {
        ArrayList<String> list = new ArrayList<>();
        switch (heavens) {
            case 1:
                list = DateUtil.get24HourList(DateUtil.getDateTimeStamp(date));
                break;
            case 7:
                list = DateUtil.getDayList(date, 7, null);
                break;
            case 30:
                list = DateUtil.getDayList(date, 30, null);
                break;
            case 365:
                list = DateUtil.getDayList(date, 365, DateUtil.DEFAULT_YYMM_FORMAT);
                break;
        }

        return checkDayResultSingle(list);
    }

    //去除重复的时间，只供柱状图使用。
    private ArrayList<String> checkDayResultSingle(ArrayList<String> dayList) {
        ArrayList<String> temp = new ArrayList<String>();
        Iterator<String> iterator = dayList.iterator();
        while (iterator.hasNext()) {
            String str = iterator.next();
            if (!temp.contains(str)) {
                temp.add(str);
            }
        }
        return temp;
    }

}
