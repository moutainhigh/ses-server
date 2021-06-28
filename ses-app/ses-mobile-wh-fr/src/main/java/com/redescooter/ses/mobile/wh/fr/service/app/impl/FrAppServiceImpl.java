package com.redescooter.ses.mobile.wh.fr.service.app.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Maps;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.constant.SpecificDefNameConstant;
import com.redescooter.ses.api.common.enums.date.DayCodeEnum;
import com.redescooter.ses.api.common.enums.date.MonthCodeEnum;
import com.redescooter.ses.api.common.enums.scooter.ScooterLockStatusEnums;
import com.redescooter.ses.api.common.vo.base.BooleanResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.StringEnter;
import com.redescooter.ses.api.common.vo.base.TokenResult;
import com.redescooter.ses.api.common.vo.node.BindVinEnter;
import com.redescooter.ses.api.common.vo.node.InputBatteryEnter;
import com.redescooter.ses.api.common.vo.node.InputScooterEnter;
import com.redescooter.ses.api.common.vo.node.InquiryDetailResult;
import com.redescooter.ses.api.common.vo.node.InquiryListAppEnter;
import com.redescooter.ses.api.common.vo.node.InquiryListResult;
import com.redescooter.ses.api.common.vo.node.SetModelEnter;
import com.redescooter.ses.api.common.vo.scooter.SyncScooterDataDTO;
import com.redescooter.ses.api.common.vo.specification.SpecificDefDTO;
import com.redescooter.ses.api.foundation.service.base.AccountBaseService;
import com.redescooter.ses.api.foundation.service.scooter.AssignScooterService;
import com.redescooter.ses.api.foundation.vo.user.UserToken;
import com.redescooter.ses.api.hub.service.admin.ScooterModelService;
import com.redescooter.ses.api.hub.service.customer.CusotmerScooterService;
import com.redescooter.ses.api.hub.service.operation.ColorService;
import com.redescooter.ses.api.hub.service.operation.SpecificService;
import com.redescooter.ses.api.hub.vo.admin.AdmScooter;
import com.redescooter.ses.api.hub.vo.admin.AdmScooterUpdateEnter;
import com.redescooter.ses.api.hub.vo.operation.SpecificTypeDTO;
import com.redescooter.ses.api.scooter.exception.ScooterException;
import com.redescooter.ses.api.scooter.service.ScooterEmqXService;
import com.redescooter.ses.api.scooter.service.ScooterNodeService;
import com.redescooter.ses.api.scooter.service.ScooterService;
import com.redescooter.ses.api.scooter.vo.ScoScooterResult;
import com.redescooter.ses.api.scooter.vo.emqx.SetScooterModelPublishDTO;
import com.redescooter.ses.api.scooter.vo.emqx.SpecificDefGroupPublishDTO;
import com.redescooter.ses.mobile.wh.fr.constant.SequenceName;
import com.redescooter.ses.mobile.wh.fr.dao.base.OpeCustomerInquiryMapper;
import com.redescooter.ses.mobile.wh.fr.dao.base.OpeSaleScooterMapper;
import com.redescooter.ses.mobile.wh.fr.dao.base.OpeSpecificatTypeMapper;
import com.redescooter.ses.mobile.wh.fr.dao.base.OpeWmsScooterStockMapper;
import com.redescooter.ses.mobile.wh.fr.dm.OpeCodebaseRsn;
import com.redescooter.ses.mobile.wh.fr.dm.OpeCodebaseVin;
import com.redescooter.ses.mobile.wh.fr.dm.OpeColor;
import com.redescooter.ses.mobile.wh.fr.dm.OpeCustomerInquiry;
import com.redescooter.ses.mobile.wh.fr.dm.OpeInWhouseScooterB;
import com.redescooter.ses.mobile.wh.fr.dm.OpeSaleScooter;
import com.redescooter.ses.mobile.wh.fr.dm.OpeSimInformation;
import com.redescooter.ses.mobile.wh.fr.dm.OpeSpecificatGroup;
import com.redescooter.ses.mobile.wh.fr.dm.OpeSpecificatType;
import com.redescooter.ses.mobile.wh.fr.dm.OpeWarehouseAccount;
import com.redescooter.ses.mobile.wh.fr.dm.OpeWmsScooterStock;
import com.redescooter.ses.mobile.wh.fr.dm.OpeWmsStockSerialNumber;
import com.redescooter.ses.mobile.wh.fr.enums.StatusEnum;
import com.redescooter.ses.mobile.wh.fr.exception.ExceptionCodeEnums;
import com.redescooter.ses.mobile.wh.fr.exception.SesMobileFrWhException;
import com.redescooter.ses.mobile.wh.fr.service.app.FrAppService;
import com.redescooter.ses.mobile.wh.fr.service.base.OpeCodebaseRsnService;
import com.redescooter.ses.mobile.wh.fr.service.base.OpeCodebaseVinService;
import com.redescooter.ses.mobile.wh.fr.service.base.OpeColorService;
import com.redescooter.ses.mobile.wh.fr.service.base.OpeCustomerInquiryService;
import com.redescooter.ses.mobile.wh.fr.service.base.OpeInWhouseScooterBService;
import com.redescooter.ses.mobile.wh.fr.service.base.OpeSaleScooterService;
import com.redescooter.ses.mobile.wh.fr.service.base.OpeSimInformationService;
import com.redescooter.ses.mobile.wh.fr.service.base.OpeSpecificatGroupService;
import com.redescooter.ses.mobile.wh.fr.service.base.OpeWarehouseAccountService;
import com.redescooter.ses.mobile.wh.fr.service.base.OpeWmsStockSerialNumberService;
import com.redescooter.ses.mobile.wh.fr.vo.AppLoginEnter;
import com.redescooter.ses.mobile.wh.fr.vo.CustomerIdEnter;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.starter.redis.enums.RedisExpireEnum;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author Chris
 * @Date 2021/5/10 13:57
 */
@Service
@Slf4j
public class FrAppServiceImpl implements FrAppService {

    @Autowired
    private OpeWarehouseAccountService opeWarehouseAccountService;

    @Autowired
    private JedisCluster jedisCluster;

    @Autowired
    private OpeWmsScooterStockMapper opeWmsScooterStockMapper;

    @Autowired
    private OpeWmsStockSerialNumberService opeWmsStockSerialNumberService;

    @Autowired
    private OpeCustomerInquiryService opeCustomerInquiryService;

    @Autowired
    private OpeSaleScooterService opeSaleScooterService;

    @Autowired
    private OpeSpecificatGroupService opeSpecificatGroupService;

    @Autowired
    private OpeColorService opeColorService;

    @Autowired
    private OpeCustomerInquiryMapper opeCustomerInquiryMapper;

    @Autowired
    private OpeSaleScooterMapper opeSaleScooterMapper;

    @Autowired
    private OpeSpecificatTypeMapper opeSpecificatTypeMapper;

    @Autowired
    private OpeInWhouseScooterBService opeInWhouseScooterBService;

    @Autowired
    private OpeCodebaseRsnService opeCodebaseRsnService;

    @Autowired
    private OpeCodebaseVinService opeCodebaseVinService;

    @Autowired
    private OpeSimInformationService opeSimInformationService;

    @Value("${Request.privateKey}")
    private String privateKey;

    @DubboReference
    private IdAppService idAppService;

    @DubboReference
    private AccountBaseService accountBaseService;

    @DubboReference
    private ScooterService scooterService;

    @DubboReference
    private ScooterNodeService scooterNodeService;

    @DubboReference
    private CusotmerScooterService cusotmerScooterService;

    @DubboReference
    private ScooterModelService scooterModelService;

    @DubboReference
    private SpecificService specificService;

    @DubboReference
    private ColorService colorService;

    @DubboReference
    private ScooterEmqXService scooterEmqXService;

    @DubboReference
    private AssignScooterService assignScooterService;

    /**
     * sim card
     */
    @Autowired
    private OpeSimInformationService simInformationService;

    /**
     * 登录
     */
    @Override
    public TokenResult login(AppLoginEnter enter) {
        // 邮箱解密
        /*String decryptEmail;
        try {
            decryptEmail = RsaUtils.decrypt(enter.getEmail(), privateKey);
        } catch (Exception ex) {
            throw new SesMobileFrWhException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }
        // 密码解密
        String decryptPassword;
        try {
            decryptPassword = RsaUtils.decrypt(enter.getPassword(), privateKey);
        } catch (Exception e) {
            throw new SesMobileFrWhException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }*/

        String decryptEmail = enter.getEmail();
        String decryptPassword = enter.getPassword();

        LambdaQueryWrapper<OpeWarehouseAccount> qw = new LambdaQueryWrapper<>();
        qw.eq(OpeWarehouseAccount::getDr, Constant.DR_FALSE);
        qw.eq(OpeWarehouseAccount::getStatus, StatusEnum.ENABLE.getCode());
        qw.eq(OpeWarehouseAccount::getSystem, 1);
        qw.eq(OpeWarehouseAccount::getEmail, decryptEmail);
        qw.last("limit 1");
        OpeWarehouseAccount account = opeWarehouseAccountService.getOne(qw);
        if (null == account) {
            throw new SesMobileFrWhException(ExceptionCodeEnums.ACCOUNT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.ACCOUNT_IS_NOT_EXIST.getMessage());
        }
        // 拿着解密后的密码再次md5加密,和db存储的md5加密的密码相比较
        String encryptPassword = DigestUtils.md5Hex(decryptPassword + account.getSalt());
        if (!StringUtils.equals(encryptPassword, account.getPassword())) {
            throw new SesMobileFrWhException(ExceptionCodeEnums.PASSROD_WRONG.getCode(), ExceptionCodeEnums.PASSROD_WRONG.getMessage());
        }

        String lastLoginToken = account.getLastLoginToken();
        if (StringUtils.isNotBlank(lastLoginToken)) {
            // 说明之前登录过
            Boolean flag = jedisCluster.exists(lastLoginToken);
            if (flag) {
                jedisCluster.del(lastLoginToken);
            }
        }

        String token = UUID.randomUUID().toString().replaceAll("-", "");
        UserToken userToken = new UserToken();
        userToken.setToken(token);
        userToken.setUserId(account.getId());
        userToken.setTenantId(new Long("0"));
        userToken.setSystemId(enter.getSystemId());
        userToken.setAppId(enter.getAppId());
        userToken.setClientIp(enter.getClientIp());
        userToken.setClientType(enter.getClientType());
        userToken.setCountry(enter.getCountry());
        userToken.setLanguage(enter.getLanguage());
        userToken.setTimestamp(enter.getTimestamp());
        userToken.setTimeZone(enter.getTimeZone());
        userToken.setVersion(enter.getVersion());

        try {
            Map<String, String> map = BeanUtils.describe(userToken);
            map.remove("requestId");
            map.remove("refreshToken");
            map.remove("deptId");

            jedisCluster.hmset(token, map);
            jedisCluster.expire(token, new Long(RedisExpireEnum.HOURS_24.getSeconds()).intValue());
        } catch (Exception ex) {
            log.error("设置token失败", ex);
        }

        // 修改db,更新lastLoginToken
        account.setLastLoginToken(token);
        opeWarehouseAccountService.updateById(account);

        TokenResult result = new TokenResult();
        result.setToken(token);
        result.setRequestId(enter.getRequestId());
        return result;
    }

    /**
     * 登出
     */
    @Override
    public GeneralResult logout(GeneralEnter enter) {
        String token = enter.getToken();
        Boolean flag = jedisCluster.exists(token);
        if (flag) {
            jedisCluster.del(token);
        }
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 注册时输入邮箱校验是否可用
     */
    @Override
    public BooleanResult checkEmail(StringEnter enter) {
        LambdaQueryWrapper<OpeWarehouseAccount> qw = new LambdaQueryWrapper<>();
        qw.eq(OpeWarehouseAccount::getDr, Constant.DR_FALSE);
        qw.eq(OpeWarehouseAccount::getEmail, enter.getKeyword().trim());
        qw.eq(OpeWarehouseAccount::getSystem, 1);
        OpeWarehouseAccount account = opeWarehouseAccountService.getOne(qw);
        if (null == account) {
            return new BooleanResult(Boolean.FALSE);
        }
        return new BooleanResult(Boolean.TRUE);
    }

    /**
     * 注册
     */
    @Override
    public GeneralResult register(AppLoginEnter enter) {
        LambdaQueryWrapper<OpeWarehouseAccount> qw = new LambdaQueryWrapper<>();
        qw.eq(OpeWarehouseAccount::getDr, Constant.DR_FALSE);
        qw.eq(OpeWarehouseAccount::getEmail, enter.getEmail().trim());
        qw.eq(OpeWarehouseAccount::getSystem, 1);
        OpeWarehouseAccount account = opeWarehouseAccountService.getOne(qw);
        if (null != account) {
            int salt = RandomUtils.nextInt(10000, 99999);
            String pwd = DigestUtils.md5Hex(enter.getPassword() + salt);
            account.setSalt(String.valueOf(salt));
            account.setPassword(pwd);
            account.setStatus(StatusEnum.ENABLE.getCode());
            account.setUpdatedTime(new Date());
            opeWarehouseAccountService.updateById(account);
        }
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 获得个人信息
     */
    @Override
    public OpeWarehouseAccount getUserInfo(GeneralEnter enter) {
        OpeWarehouseAccount account = opeWarehouseAccountService.getById(enter.getUserId());
        if (null == account) {
            throw new SesMobileFrWhException(ExceptionCodeEnums.ACCOUNT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.ACCOUNT_IS_NOT_EXIST.getMessage());
        }
        return account;
    }

    /**
     * 列表
     */
    @Override
    public PageResult<InquiryListResult> getList(InquiryListAppEnter enter) {
        PageResult<InquiryListResult> pageResult = scooterNodeService.getList(enter);
        if (null != pageResult) {
            List<InquiryListResult> list = pageResult.getList();
            if (CollectionUtils.isNotEmpty(list)) {
                for (InquiryListResult item : list) {
                    OpeColor color = opeColorService.getById(item.getColorId());
                    if (null != color) {
                        item.setColorName(color.getColorName());
                    }
                }
            }
        }
        return pageResult;
    }

    /**
     * 详情
     */
    @Override
    public InquiryDetailResult getDetail(StringEnter enter) {
        InquiryDetailResult result = scooterNodeService.getDetail(enter);
        Long colorId = result.getColorId();
        if (null == colorId) {
            throw new SesMobileFrWhException(ExceptionCodeEnums.COLOR_NOT_EXIST.getCode(), ExceptionCodeEnums.COLOR_NOT_EXIST.getMessage());
        }
        OpeColor color = opeColorService.getById(colorId);
        if (null == color) {
            throw new SesMobileFrWhException(ExceptionCodeEnums.COLOR_NOT_EXIST.getCode(), ExceptionCodeEnums.COLOR_NOT_EXIST.getMessage());
        }
        result.setColorName(color.getColorName());
        return result;
    }

    /**
     * 根据rsn带出其他6个码
     */
    @Override
    public Map<String, String> getOtherCode(StringEnter enter) {
        LambdaQueryWrapper<OpeInWhouseScooterB> qw = new LambdaQueryWrapper<>();
        qw.eq(OpeInWhouseScooterB::getDr, Constant.DR_FALSE);
        qw.eq(OpeInWhouseScooterB::getRsn, enter.getKeyword());
        qw.last("limit 1");
        OpeInWhouseScooterB scooterB = opeInWhouseScooterBService.getOne(qw);
        if (null == scooterB) {
            throw new SesMobileFrWhException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }

        Map<String, String> result = Maps.newLinkedHashMap();
        result.put("rsn", scooterB.getRsn());
        result.put("bbi", scooterB.getBbi());
        result.put("controller", scooterB.getController());
        result.put("motor", scooterB.getMotor());
        result.put("tabletSn", scooterB.getTabletSn());
        result.put("imei", scooterB.getImei());
        result.put("bluetoothMacAddress", scooterB.getBluetoothMacAddress());
        return result;
    }

    /**
     * 录入车辆
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult inputScooter(InputScooterEnter enter) {
        // 校验码库
        LambdaQueryWrapper<OpeCodebaseRsn> qw = new LambdaQueryWrapper<>();
        qw.eq(OpeCodebaseRsn::getDr, Constant.DR_FALSE);
        qw.eq(OpeCodebaseRsn::getRsn, enter.getRsn());
        int count = opeCodebaseRsnService.count(qw);
        if (count <= 0) {
            throw new SesMobileFrWhException(ExceptionCodeEnums.RSN_NOT_EXISTS_CODEBASE.getCode(), ExceptionCodeEnums.RSN_NOT_EXISTS_CODEBASE.getMessage());
        }

        LambdaQueryWrapper<OpeCodebaseRsn> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OpeCodebaseRsn::getDr, Constant.DR_FALSE);
        wrapper.eq(OpeCodebaseRsn::getRsn, enter.getRsn());
        wrapper.eq(OpeCodebaseRsn::getStatus, 2);
        int num = opeCodebaseRsnService.count(wrapper);
        if (num > 0) {
            throw new SesMobileFrWhException(ExceptionCodeEnums.RSN_HAS_USED.getCode(), ExceptionCodeEnums.RSN_HAS_USED.getMessage());
        }

        // 录入车辆
        scooterNodeService.inputScooter(enter);

        // 修改码库此rsn为已用
        LambdaQueryWrapper<OpeCodebaseRsn> lqw = new LambdaQueryWrapper<>();
        lqw.eq(OpeCodebaseRsn::getDr, Constant.DR_FALSE);
        lqw.eq(OpeCodebaseRsn::getStatus, 1);
        lqw.eq(OpeCodebaseRsn::getRsn, enter.getRsn());
        lqw.last("limit 1");
        OpeCodebaseRsn model = opeCodebaseRsnService.getOne(lqw);
        if (null != model) {
            model.setStatus(2);
            model.setUpdatedBy(enter.getUserId());
            model.setUpdatedTime(new Date());
            opeCodebaseRsnService.updateById(model);
        }
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 录入电池
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public List<String> inputBattery(InputBatteryEnter enter) {
        return scooterNodeService.inputBattery(enter);
    }

    /**
     * 设置软体
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult setScooterModel(SetModelEnter enter) {
        ScoScooterResult scoScooter = scooterNodeService.setScooterModel(enter);

        // 创建车辆
        AdmScooter admScooter = scooterModelService.getScooterBySn(scoScooter.getTabletSn());
        if (null != admScooter) {
            throw new ScooterException(ExceptionCodeEnums.SN_ALREADY_EXISTS.getCode(), ExceptionCodeEnums.SN_ALREADY_EXISTS.getMessage());
        }
        log.info("车辆不存在");

        OpeColor color = opeColorService.getById(scoScooter.getColorId());
        if (null == color) {
            throw new ScooterException(ExceptionCodeEnums.COLOR_NOT_EXIST.getCode(), ExceptionCodeEnums.COLOR_NOT_EXIST.getMessage());
        }

        // 获取低速
        LambdaQueryWrapper<OpeSpecificatGroup> qw = new LambdaQueryWrapper<>();
        qw.eq(OpeSpecificatGroup::getDr, Constant.DR_FALSE);
        qw.like(OpeSpecificatGroup::getGroupName, "Low");
        qw.last("limit 1");
        OpeSpecificatGroup group = opeSpecificatGroupService.getOne(qw);
        if (null == group) {
            throw new ScooterException(ExceptionCodeEnums.GROUP_NOT_EXIST.getCode(), ExceptionCodeEnums.GROUP_NOT_EXIST.getMessage());
        }

        // 新增adm_scooter表
        AdmScooter scooter = new AdmScooter();
        scooter.setId(idAppService.getId(SequenceName.OPE_CAR_DISTRIBUTE));
        scooter.setDr(Constant.DR_FALSE);
        scooter.setSn(scoScooter.getTabletSn());
        scooter.setGroupId(group.getId());
        scooter.setColorId(scoScooter.getColorId());
        scooter.setMacAddress(scoScooter.getBluetoothMacAddress());
        scooter.setScooterController(enter.getModel());
        scooter.setCreatedBy(0L);
        scooter.setCreatedTime(new Date());
        scooter.setUpdatedBy(0L);
        scooter.setUpdatedTime(new Date());
        scooter.setColorName(color.getColorName());
        scooter.setColorValue(color.getColorValue());
        scooter.setGroupName(group.getGroupName());
        scooter.setMacName(scoScooter.getBluetoothMacAddress());
        scooterModelService.insertScooter(scooter);
        log.info("新增adm_scooter表成功");

        // 设置软体
        AdmScooter scooterModel = scooterModelService.getScooterById(scooter.getId());
        if (null == scooterModel) {
            throw new ScooterException(ExceptionCodeEnums.SCOOTER_NOT_EXIST.getCode(), ExceptionCodeEnums.SCOOTER_NOT_EXIST.getMessage());
        }
        // 只允许车辆关闭状态时进行软体设置
        String status = scooterService.getScooterStatusByTabletSn(scooter.getSn());
        if (ScooterLockStatusEnums.UNLOCK.getValue().equals(status)) {
            throw new ScooterException(ExceptionCodeEnums.SCOOTER_NOT_CLOSED.getCode(), ExceptionCodeEnums.SCOOTER_NOT_CLOSED.getMessage());
        }

        // 更新车辆型号信息
        AdmScooterUpdateEnter param = new AdmScooterUpdateEnter();
        param.setId(scooterModel.getId());
        Integer type = 2;
        String specificName = "E50";
        if (enter.getModel() == 1) {
            type = 2;
            specificName = "E50";
        }
        if (enter.getModel() == 2) {
            type = 3;
            specificName = "E100";
        }
        param.setScooterController(type);
        param.setGroupId(group.getId());
        param.setGroupName(group.getGroupName());
        scooterModelService.updateAdmScooter(param);
        log.info("更新车辆型号信息");
        scooterService.syncScooterModel(scooterModel.getSn(), type);
        log.info("同步车辆型号");

        SpecificTypeDTO specificType = specificService.getSpecificTypeByName(specificName);
        List<SpecificDefGroupPublishDTO> list = buildSetScooterModelData(specificType.getId());
        if (CollectionUtils.isNotEmpty(list)) {
            // 发送EMQ消息,通知车辆那边进行升级处理
            SetScooterModelPublishDTO instance = new SetScooterModelPublishDTO();
            instance.setTabletSn(scooterModel.getSn());
            instance.setScooterModel(type);
            instance.setSpecificDefGroupList(list);
            log.info("设置软体的请求参数是:[{}]", instance);
            scooterEmqXService.setScooterModel(instance);
        }
        log.info("设置软体完毕");

        // 设置软体完成后节点流转
        scooterNodeService.updateNode(enter.getUserId(), scoScooter.getId());
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 绑定VIN
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult bindVin(BindVinEnter enter) {
        if (enter.getVinCode().length() != 17) {
            throw new SesMobileFrWhException(ExceptionCodeEnums.VIN_NOT_MATCH.getCode(), ExceptionCodeEnums.VIN_NOT_MATCH.getMessage());
        }
        if (!enter.getVinCode().startsWith("VXSR2A")) {
            throw new SesMobileFrWhException(ExceptionCodeEnums.VIN_NOT_MATCH.getCode(), ExceptionCodeEnums.VIN_NOT_MATCH.getMessage());
        }

        // 校验码库
        LambdaQueryWrapper<OpeCodebaseVin> qw = new LambdaQueryWrapper<>();
        qw.eq(OpeCodebaseVin::getDr, Constant.DR_FALSE);
        qw.eq(OpeCodebaseVin::getVin, enter.getVinCode());
        int count = opeCodebaseVinService.count(qw);
        if (count <= 0) {
            throw new SesMobileFrWhException(ExceptionCodeEnums.VIN_NOT_EXISTS_CODEBASE.getCode(), ExceptionCodeEnums.VIN_NOT_EXISTS_CODEBASE.getMessage());
        }

        LambdaQueryWrapper<OpeCodebaseVin> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OpeCodebaseVin::getDr, Constant.DR_FALSE);
        wrapper.eq(OpeCodebaseVin::getVin, enter.getVinCode());
        wrapper.eq(OpeCodebaseVin::getStatus, 2);
        int num = opeCodebaseVinService.count(wrapper);
        if (num > 0) {
            throw new SesMobileFrWhException(ExceptionCodeEnums.VIN_HAS_INPUT.getCode(), ExceptionCodeEnums.VIN_HAS_INPUT.getMessage());
        }

        // 录入vin
        Map<String, String> map = scooterNodeService.bindVin(enter);
        log.info("录入vin的返回结果是:[{}]", map);

        // 录入sim卡
        OpeSimInformation sim = new OpeSimInformation();
        String iccid = jedisCluster.get(map.get("tabletSn"));
        if (StringUtils.isNotBlank(iccid)) {
            sim.setSimIccid(iccid);
        }
        sim.setRsn(map.get("rsn"));
        sim.setBluetoothMacAddress(map.get("bluetoothMacAddress"));
        sim.setTabletSn(map.get("tabletSn"));
        sim.setVin(map.get("vin"));
        sim.setCreatedBy(enter.getUserId());
        sim.setCreatedTime(new Date());
        opeSimInformationService.save(sim);
        jedisCluster.del(map.get("tabletSn"));

        // 修改码库vin为已用
        LambdaQueryWrapper<OpeCodebaseVin> lqw = new LambdaQueryWrapper<>();
        lqw.eq(OpeCodebaseVin::getDr, Constant.DR_FALSE);
        lqw.eq(OpeCodebaseVin::getStatus, 1);
        lqw.eq(OpeCodebaseVin::getVin, enter.getVinCode());
        lqw.last("limit 1");
        OpeCodebaseVin model = opeCodebaseVinService.getOne(lqw);
        if (null != model) {
            model.setStatus(2);
            model.setUpdatedBy(enter.getUserId());
            model.setUpdatedTime(new Date());
            opeCodebaseVinService.updateById(model);
        }
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 组装设置车辆型号数据
     */
    private List<SpecificDefGroupPublishDTO> buildSetScooterModelData(Long specificTypeId) {
        List<SpecificDefGroupPublishDTO> list = new ArrayList<>();

        // 查询当前车辆电池信息,这里主要是为了拿车辆电池的出厂号/流水号信息(用于设置软体时使用) 先不给电池的出厂号/流水号(车辆那边现在不是强制性需要)
        // 查询车辆型号自定义项信息
        List<SpecificDefDTO> specificDefList = specificService.getSpecificDefBySpecificId(specificTypeId);
        if (CollectionUtils.isNotEmpty(specificDefList)) {
            // 旧数据ope_specificat_def表里面def_group_id字段值是空的,这里会导致stream分组的时候报错
            specificDefList.forEach(def -> {
                if (null == def.getSpecificDefGroupId()) {
                    throw new ScooterException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
                }
            });

            Map<Long, List<SpecificDefDTO>> specificDefGroupMap = specificDefList.stream().collect(Collectors.groupingBy(SpecificDefDTO::getSpecificDefGroupId));

            for (Map.Entry<Long, List<SpecificDefDTO>> map : specificDefGroupMap.entrySet()) {
                Map<String, String> specificDefMap = map.getValue().stream().collect(Collectors.toMap(SpecificDefDTO::getDefName, SpecificDefDTO::getDefValue));

                // 组装自定义项数据 -- 自定义项名称固定值
                SpecificDefGroupPublishDTO publish = SpecificDefGroupPublishDTO.builder()
                        .wheelDiameter(specificDefMap.get(SpecificDefNameConstant.WHEEL_DIAMETER))
                        .speedRatio(specificDefMap.get(SpecificDefNameConstant.SPEED_RATIO))
                        .limitSpeedBos(specificDefMap.get(SpecificDefNameConstant.LIMIT_SPEED_BOS))
                        .limiting(specificDefMap.get(SpecificDefNameConstant.LIMITING))
                        .speedLimit(specificDefMap.get(SpecificDefNameConstant.SPEED_LIMIT))
                        .socRedWarning(specificDefMap.get(SpecificDefNameConstant.SOC_RED_WARNING))
                        .orangeWarning(specificDefMap.get(SpecificDefNameConstant.ORANGE_WARNING))
                        .stallSOC(specificDefMap.get(SpecificDefNameConstant.STALL_SOC))
                        .setSOCTo0AtStallUndervoltage(specificDefMap.get(SpecificDefNameConstant.SET_SOC_TO_0_AT_STALL_UNDER_VOLTAGE))
                        .stallVoltageUndervoltage(specificDefMap.get(SpecificDefNameConstant.STALL_VOLTAGE_UNDER_VOLTAGE))
                        .voltageLegalRecognitionMin(specificDefMap.get(SpecificDefNameConstant.VOLTAGE_LEGAL_RECOGNITION_MAX))
                        .voltageLegalRecognitionMax(specificDefMap.get(SpecificDefNameConstant.VOLTAGE_LEGAL_RECOGNITION_MIN))
                        .controllerUndervoltage(specificDefMap.get(SpecificDefNameConstant.CONTROLLER_UNDER_VOLTAGE))
                        .controllerUndervoltageRecovery(specificDefMap.get(SpecificDefNameConstant.CONTROLLER_UNDER_VOLTAGE_RECOVERY))
                        .build();
                list.add(publish);
            }
        }
        return list;
    }

    /**
     * 组装同步车辆数据
     */
    private List<SyncScooterDataDTO> buildData(Long scooterId, String tabletSn, Integer scooterModel, Long userId, String scooterNo) {
        SyncScooterDataDTO model = new SyncScooterDataDTO();
        model.setId(scooterId);
        model.setScooterNo(scooterNo);
        model.setTabletSn(tabletSn);
        model.setModel(String.valueOf(scooterModel));
        model.setUserId(userId);
        return new ArrayList<>(Arrays.asList(model));
    }

    /**
     * 生成车辆编号
     */
    private String generateScooterNo() {
        Calendar cal = Calendar.getInstance();
        String year = String.valueOf(cal.get(Calendar.YEAR));
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);

        // 编号规则：区域 + 产品范围 + 结构类型 + 额定功率 + 生产地点 + 年份 + 月份 + 生产流水号(数量从1开始)
        StringBuilder sb = new StringBuilder();
        sb.append("FR");
        sb.append("ED");
        sb.append("D");
        sb.append("0");
        sb.append(year.substring(2, 4));
        sb.append(MonthCodeEnum.getMonthCodeByMonth(month));
        // 获取当前时间戳,并截取最后6位拼接在编号最后
        String timeStamp = String.valueOf(System.currentTimeMillis());
        String sub = timeStamp.substring(timeStamp.length() - 6);
        String number = String.format("%s%s%s", DayCodeEnum.getDayCodeByDay(day), "1", sub);
        sb.append(number);
        return sb.toString();
    }

    /**
     * 查看rsn对应的车型(低速/高速)和询价单对应的车型(低速/高速)是否相符
     */
    private void check(String rsn, Long customerId) {
        LambdaQueryWrapper<OpeWmsStockSerialNumber> qw = new LambdaQueryWrapper<>();
        qw.eq(OpeWmsStockSerialNumber::getDr, Constant.DR_FALSE);
        qw.eq(OpeWmsStockSerialNumber::getRelationType, 1);
        qw.eq(OpeWmsStockSerialNumber::getRsn, rsn);
        qw.last("limit 1");
        OpeWmsStockSerialNumber serialNumber = opeWmsStockSerialNumberService.getOne(qw);
        if (null == serialNumber) {
            throw new SesMobileFrWhException(ExceptionCodeEnums.RSN_NOT_EXIST.getCode(), ExceptionCodeEnums.RSN_NOT_EXIST.getMessage());
        }
        Long relationId = serialNumber.getRelationId();
        OpeWmsScooterStock stock = opeWmsScooterStockMapper.selectById(relationId);
        if (null == stock) {
            throw new SesMobileFrWhException(ExceptionCodeEnums.SCOOTER_STOCK_IS_EMPTY.getCode(), ExceptionCodeEnums.SCOOTER_STOCK_IS_EMPTY.getMessage());
        }
        Long groupId = stock.getGroupId();
        Long colorId = stock.getColorId();
        OpeSpecificatGroup group = opeSpecificatGroupService.getById(groupId);
        OpeColor color = opeColorService.getById(colorId);
        if (null == group) {
            throw new SesMobileFrWhException(ExceptionCodeEnums.GROUP_NOT_EXIST.getCode(), ExceptionCodeEnums.GROUP_NOT_EXIST.getMessage());
        }
        if (null == color) {
            throw new SesMobileFrWhException(ExceptionCodeEnums.COLOR_NOT_EXIST.getCode(), ExceptionCodeEnums.COLOR_NOT_EXIST.getMessage());
        }

        // 获得询价单产品是低速还是高速,以及颜色
        OpeSaleScooter saleScooter = getSaleScooter(customerId);
        if (!Objects.equals(groupId, saleScooter.getGroupId()) || !Objects.equals(colorId, saleScooter.getColorId())) {
            throw new SesMobileFrWhException(ExceptionCodeEnums.SPECIFICAT_NOT_MATCH.getCode(), ExceptionCodeEnums.SPECIFICAT_NOT_MATCH.getMessage());
        }
    }

    /**
     * 根据客户id获取询价单的销售车辆
     */
    private OpeSaleScooter getSaleScooter(Long customerId) {
        LambdaQueryWrapper<OpeCustomerInquiry> qw = new LambdaQueryWrapper<>();
        qw.eq(OpeCustomerInquiry::getDr, Constant.DR_FALSE);
        qw.eq(OpeCustomerInquiry::getCustomerId, customerId);
        qw.last("limit 1");
        OpeCustomerInquiry inquiry = opeCustomerInquiryService.getOne(qw);
        if (null == inquiry) {
            throw new SesMobileFrWhException(ExceptionCodeEnums.INQUIRY_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.INQUIRY_IS_NOT_EXIST.getMessage());
        }
        Long productId = inquiry.getProductId();
        OpeSaleScooter saleScooter = opeSaleScooterService.getById(productId);
        if (null == saleScooter) {
            throw new SesMobileFrWhException(ExceptionCodeEnums.SCOOTER_NOT_EXIST.getCode(), ExceptionCodeEnums.SCOOTER_NOT_EXIST.getMessage());
        }
        return saleScooter;
    }

    /**
     * 根据客户id获得询价单型号id和颜色id
     */
    public Map<String, Long> getSpecificatIdAndColorId(CustomerIdEnter enter) {
        Map<String, Long> result = Maps.newHashMapWithExpectedSize(2);
        // 得到询价单的产品id
        LambdaQueryWrapper<OpeCustomerInquiry> opeCustomerInquiryWrapper = new LambdaQueryWrapper<>();
        opeCustomerInquiryWrapper.eq(OpeCustomerInquiry::getDr, Constant.DR_FALSE);
        opeCustomerInquiryWrapper.eq(OpeCustomerInquiry::getCustomerId, enter.getCustomerId());
        opeCustomerInquiryWrapper.orderByDesc(OpeCustomerInquiry::getCreatedTime);
        List<OpeCustomerInquiry> list = opeCustomerInquiryMapper.selectList(opeCustomerInquiryWrapper);
        if (CollectionUtils.isEmpty(list)) {
            throw new SesMobileFrWhException(ExceptionCodeEnums.CUSTOMER_NOT_EXIST.getCode(), ExceptionCodeEnums.CUSTOMER_NOT_EXIST.getMessage());
        }
        OpeCustomerInquiry customerInquiry = list.get(0);
        Long productId = customerInquiry.getProductId();
        if (null == productId) {
            throw new SesMobileFrWhException(ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getMessage());
        }

        // 拿着产品id去ope_sale_scooter查询
        OpeSaleScooter opeSaleScooter = opeSaleScooterMapper.selectById(productId);
        if (null == opeSaleScooter) {
            throw new SesMobileFrWhException(ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getMessage());
        }
        // 得到型号id和颜色id
        Long groupId = opeSaleScooter.getGroupId();
        Long colorId = opeSaleScooter.getColorId();
        result.put("specificatId", groupId);
        result.put("colorId", colorId);
        return result;
    }

    /**
     * 根据型号id获取型号名称
     */
    public String getSpecificatNameById(Long specificatId) {
        if (null == specificatId) {
            throw new SesMobileFrWhException(ExceptionCodeEnums.SPECIFICAT_ID_NOT_EMPTY.getCode(), ExceptionCodeEnums.SPECIFICAT_ID_NOT_EMPTY.getMessage());
        }
        OpeSpecificatType specificatType = opeSpecificatTypeMapper.selectById(specificatId);
        if (null != specificatType) {
            String name = specificatType.getSpecificatName();
            if (StringUtils.isNotBlank(name)) {
                return name;
            }
        }
        throw new SesMobileFrWhException(ExceptionCodeEnums.SPECIFICAT_TYPE_NOT_EXIST.getCode(), ExceptionCodeEnums.SPECIFICAT_TYPE_NOT_EXIST.getMessage());
    }

}
