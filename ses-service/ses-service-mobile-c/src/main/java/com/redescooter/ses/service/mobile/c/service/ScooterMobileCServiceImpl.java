package com.redescooter.ses.service.mobile.c.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.enums.scooter.DriverScooterStatusEnums;
import com.redescooter.ses.api.common.enums.user.UserServiceTypeEnum;
import com.redescooter.ses.api.common.vo.base.BooleanResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.StringEnter;
import com.redescooter.ses.api.common.vo.scooter.BaseScooterResult;
import com.redescooter.ses.api.common.vo.scooter.ScooterLockDTO;
import com.redescooter.ses.api.common.vo.scooter.ScooterNavigationDTO;
import com.redescooter.ses.api.foundation.vo.user.UserToken;
import com.redescooter.ses.api.mobile.c.exception.MobileCException;
import com.redescooter.ses.api.mobile.c.service.ScooterMobileCService;
import com.redescooter.ses.api.common.service.MondayProducerService;
import com.redescooter.ses.api.scooter.service.ScooterEmqXService;
import com.redescooter.ses.api.scooter.service.ScooterService;
import com.redescooter.ses.api.scooter.vo.ScoScooterResult;
import com.redescooter.ses.service.mobile.c.constant.SequenceName;
import com.redescooter.ses.service.mobile.c.dao.UserScooterMapper;
import com.redescooter.ses.service.mobile.c.dm.base.ConUserProfile;
import com.redescooter.ses.service.mobile.c.dm.base.ConUserScooter;
import com.redescooter.ses.service.mobile.c.exception.ExceptionCodeEnums;
import com.redescooter.ses.service.mobile.c.service.base.ConUserProfileService;
import com.redescooter.ses.service.mobile.c.service.base.ConUserScooterService;
import com.redescooter.ses.starter.common.service.IdAppService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisCluster;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author assert
 * @date 2020/11/18 17:50
 */
@DubboService
@Slf4j
public class ScooterMobileCServiceImpl implements ScooterMobileCService {

    @Resource
    private UserScooterMapper userScooterMapper;

    @DubboReference
    private ScooterService scooterService;

    @DubboReference
    private ScooterEmqXService scooterEmqXService;

    @Autowired
    private JedisCluster jedisCluster;

    @Autowired
    private ConUserScooterService conUserScooterService;

    @DubboReference
    private IdAppService idAppService;

    @DubboReference
    private MondayProducerService mondayRecordService;

    @Autowired
    private ConUserProfileService conUserProfileService;

    @Override
    public BaseScooterResult getScooterInfo(GeneralEnter enter) {
        ConUserScooter scooter = userScooterMapper.getUserScooterByUserIdAndStatus(enter.getUserId(),
                DriverScooterStatusEnums.USED.getValue());

        /**
         * 检查当前用户是否分配车辆
         */
        if (null == scooter) {
//            throw new MobileCException(ExceptionCodeEnums.DRIVER_NOT_ASSIGNED_VEHICLE.getCode(),
//                    ExceptionCodeEnums.DRIVER_NOT_ASSIGNED_VEHICLE.getMessage());
            // todo 分布式异常没有处理  抛出的异常 上个系统接不到 改为返回null
            return null;
        }

        /**
         * 查询车辆实时信息(ECU仪表盘的数据)
         */
        BaseScooterResult scooterResult = scooterService.getScooterInfoById(scooter.getScooterId());

        return scooterResult;
    }

    @Override
    public GeneralResult lock(ScooterLockDTO scooterLockDTO) {
        ConUserScooter scooter = userScooterMapper.getUserScooterByUserIdAndStatus(scooterLockDTO.getUserId(),
                DriverScooterStatusEnums.USED.getValue());

        /**
         * 检查当前用户是否分配车辆
         */
        if (null == scooter) {
            throw new MobileCException(ExceptionCodeEnums.DRIVER_NOT_ASSIGNED_VEHICLE.getCode(),
                    ExceptionCodeEnums.DRIVER_NOT_ASSIGNED_VEHICLE.getMessage());
        }

        if ("0".equals(scooterLockDTO.getLat()) && "0".equals(scooterLockDTO.getLng())) {
            Map<String, BigDecimal> map = scooterService.getPositionByScooterId(scooter.getScooterId());
            if (null != map) {
                BigDecimal longitude = map.get("longitude");
                BigDecimal latitude = map.get("latitude");
                scooterLockDTO.setLng(null == longitude ? String.valueOf(BigDecimal.ZERO) : String.valueOf(longitude));
                scooterLockDTO.setLat(null == latitude ? String.valueOf(BigDecimal.ZERO) : String.valueOf(latitude));
            }
        }

        /**
         * 开关车辆锁
         */
        return scooterEmqXService.lock(scooterLockDTO, scooter.getScooterId(), UserServiceTypeEnum.C.getType());
    }

    @Override
    public GeneralResult scooterNavigation(ScooterNavigationDTO enter) {
        ConUserScooter scooter = userScooterMapper.getUserScooterByUserIdAndStatus(enter.getUserId(),
                DriverScooterStatusEnums.USED.getValue());

        /**
         * 检查当前用户是否分配车辆
         */
        if (null == scooter) {
            throw new MobileCException(ExceptionCodeEnums.DRIVER_NOT_ASSIGNED_VEHICLE.getCode(),
                    ExceptionCodeEnums.DRIVER_NOT_ASSIGNED_VEHICLE.getMessage());
        }

        /**
         * 开始/结束导航
         */
        return scooterEmqXService.scooterNavigation(enter, scooter.getScooterId(), UserServiceTypeEnum.C.getType());
    }

    /**
     * 登录后验证此账号下是否有车
     */
    @Override
    public BooleanResult checkScooter(GeneralEnter enter) {
        Boolean flag = jedisCluster.exists(enter.getToken());
        if (!flag) {
            throw new MobileCException(ExceptionCodeEnums.TOKEN_NOT_EXIST.getCode(), ExceptionCodeEnums.TOKEN_NOT_EXIST.getMessage());
        }
        Map<String, String> map = jedisCluster.hgetAll(enter.getToken());
        if (map == null) {
            throw new MobileCException(ExceptionCodeEnums.TOKEN_NOT_EXIST.getCode(), ExceptionCodeEnums.TOKEN_NOT_EXIST.getMessage());
        }
        UserToken userToken = new UserToken();
        try {
            org.apache.commons.beanutils.BeanUtils.populate(userToken, map);
        } catch (Exception e) {
            log.error("checkToken Exception sessionMap:" + map, e);
        }

        LambdaQueryWrapper<ConUserScooter> qw = new LambdaQueryWrapper<>();
        qw.eq(ConUserScooter::getDr, Constant.DR_FALSE);
        qw.eq(ConUserScooter::getStatus, "1");
        qw.eq(ConUserScooter::getUserId, userToken.getUserId());
        List<ConUserScooter> list = conUserScooterService.list(qw);
        if (CollectionUtils.isNotEmpty(list)) {
            return new BooleanResult(Boolean.TRUE);
        }
        return new BooleanResult(Boolean.FALSE);
    }

    /**
     * 登录后如果账号下没车进行绑车操作
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public BooleanResult bindScooter(StringEnter enter) {
        Boolean flag = jedisCluster.exists(enter.getToken());
        if (!flag) {
            throw new MobileCException(ExceptionCodeEnums.TOKEN_NOT_EXIST.getCode(), ExceptionCodeEnums.TOKEN_NOT_EXIST.getMessage());
        }
        Map<String, String> map = jedisCluster.hgetAll(enter.getToken());
        if (map == null) {
            throw new MobileCException(ExceptionCodeEnums.TOKEN_NOT_EXIST.getCode(), ExceptionCodeEnums.TOKEN_NOT_EXIST.getMessage());
        }
        UserToken userToken = new UserToken();
        try {
            org.apache.commons.beanutils.BeanUtils.populate(userToken, map);
        } catch (Exception e) {
            log.error("checkToken Exception sessionMap:" + map, e);
        }
        LambdaQueryWrapper<ConUserProfile> lambda = new LambdaQueryWrapper<>();
        lambda.eq(ConUserProfile :: getDr, Constant.DR_FALSE);
        lambda.eq(ConUserProfile :: getUserId, userToken.getUserId());
        ConUserProfile userProfile = conUserProfileService.getOne(lambda);
        String userEmail = "";
        String telphone = "";
        if(null != userProfile){
            userEmail = userProfile.getEmail1();
            telphone = userProfile.getTelNumber1();
        }

        // 根据rsn获取scooterId
        Long scooterId = scooterService.getScooterIdByRsn(enter);
        // 校验车辆是否存在
        if (null == scooterId) {
            throw new MobileCException(ExceptionCodeEnums.SCOOTER_NOT_EXIST.getCode(), ExceptionCodeEnums.SCOOTER_NOT_EXIST.getMessage());
        }

        // 校验车辆是否重复绑定
        LambdaQueryWrapper<ConUserScooter> qw = new LambdaQueryWrapper<>();
        qw.eq(ConUserScooter::getDr, Constant.DR_FALSE);
        qw.eq(ConUserScooter::getStatus, "1");
        qw.eq(ConUserScooter::getScooterId, scooterId);
        List<ConUserScooter> list = conUserScooterService.list(qw);
        if (CollectionUtils.isNotEmpty(list)) {
            throw new MobileCException(ExceptionCodeEnums.SCOOTER_CANNOT_REPEAT_BIND.getCode(), ExceptionCodeEnums.SCOOTER_CANNOT_REPEAT_BIND.getMessage());
        }

        // 保存
        ConUserScooter model = new ConUserScooter();
        model.setId(idAppService.getId(SequenceName.CON_USER_PROFILE));
        model.setDr(Constant.DR_FALSE);
        model.setTenantId(0L);
        model.setUserId(userToken.getUserId());
        model.setScooterId(scooterId);
        model.setBeginTime(new Date());
        model.setStatus("1");
        model.setMileage(new Double("0"));
        model.setCreatedBy(enter.getUserId());
        model.setCreatedTime(new Date());
        model.setUpdatedBy(enter.getUserId());
        model.setUpdatedTime(new Date());
        boolean saveFlag = conUserScooterService.save(model);
        log.info("-----------------------------绑车完成-------------------");
        if (saveFlag) {
            ScoScooterResult scoScooterByTableSn = scooterService.getScoScooterByTableSn(enter.getKeyword().trim());
            mondayRecordService.save(userToken.getUserId(), JSON.toJSONString(scoScooterByTableSn), userEmail, telphone);
            log.info("-----------------------------绑车Over-------------------");
            return new BooleanResult(Boolean.TRUE);
        }
        return new BooleanResult(Boolean.FALSE);
    }

}
