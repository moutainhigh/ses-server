package com.redescooter.ses.wh.fr.service.app.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.constant.SpecificDefNameConstant;
import com.redescooter.ses.api.common.enums.assign.ProductTypeEnum;
import com.redescooter.ses.api.common.enums.customer.CustomerStatusEnum;
import com.redescooter.ses.api.common.enums.date.DayCodeEnum;
import com.redescooter.ses.api.common.enums.date.MonthCodeEnum;
import com.redescooter.ses.api.common.enums.scooter.ScooterLockStatusEnums;
import com.redescooter.ses.api.common.enums.scooter.ScooterModelEnum;
import com.redescooter.ses.api.common.enums.scooter.ScooterModelEnums;
import com.redescooter.ses.api.common.enums.scooter.ScooterStatusEnums;
import com.redescooter.ses.api.common.enums.wms.WmsStockStatusEnum;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.TokenResult;
import com.redescooter.ses.api.common.vo.scooter.ColorDTO;
import com.redescooter.ses.api.common.vo.scooter.SpecificGroupDTO;
import com.redescooter.ses.api.common.vo.scooter.SyncScooterDataDTO;
import com.redescooter.ses.api.common.vo.specification.SpecificDefDTO;
import com.redescooter.ses.api.foundation.service.base.AccountBaseService;
import com.redescooter.ses.api.foundation.service.scooter.AssignScooterService;
import com.redescooter.ses.api.foundation.vo.tenant.QueryAccountResult;
import com.redescooter.ses.api.foundation.vo.user.UserToken;
import com.redescooter.ses.api.hub.service.admin.ScooterModelService;
import com.redescooter.ses.api.hub.service.customer.CusotmerScooterService;
import com.redescooter.ses.api.hub.service.operation.ColorService;
import com.redescooter.ses.api.hub.service.operation.SpecificService;
import com.redescooter.ses.api.hub.vo.HubSaveScooterEnter;
import com.redescooter.ses.api.hub.vo.admin.AdmScooter;
import com.redescooter.ses.api.hub.vo.admin.AdmScooterUpdateEnter;
import com.redescooter.ses.api.hub.vo.operation.SpecificTypeDTO;
import com.redescooter.ses.api.scooter.service.ScooterEmqXService;
import com.redescooter.ses.api.scooter.service.ScooterService;
import com.redescooter.ses.api.scooter.vo.ScoScooterResult;
import com.redescooter.ses.api.scooter.vo.emqx.SetScooterModelPublishDTO;
import com.redescooter.ses.api.scooter.vo.emqx.SpecificDefGroupPublishDTO;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.starter.redis.enums.RedisExpireEnum;
import com.redescooter.ses.tool.utils.map.MapUtil;
import com.redescooter.ses.wh.fr.constant.SequenceName;
import com.redescooter.ses.wh.fr.dao.base.OpeCarDistributeExMapper;
import com.redescooter.ses.wh.fr.dao.base.OpeCarDistributeMapper;
import com.redescooter.ses.wh.fr.dao.base.OpeCarDistributeNodeMapper;
import com.redescooter.ses.wh.fr.dao.base.OpeCustomerInquiryMapper;
import com.redescooter.ses.wh.fr.dao.base.OpeCustomerMapper;
import com.redescooter.ses.wh.fr.dao.base.OpeSaleScooterMapper;
import com.redescooter.ses.wh.fr.dao.base.OpeSpecificatTypeMapper;
import com.redescooter.ses.wh.fr.dao.base.OpeWmsScooterStockMapper;
import com.redescooter.ses.wh.fr.dao.base.OpeWmsStockRecordMapper;
import com.redescooter.ses.wh.fr.dm.OpeCarDistribute;
import com.redescooter.ses.wh.fr.dm.OpeCarDistributeNode;
import com.redescooter.ses.wh.fr.dm.OpeCodebaseRelation;
import com.redescooter.ses.wh.fr.dm.OpeCodebaseRsn;
import com.redescooter.ses.wh.fr.dm.OpeCodebaseVin;
import com.redescooter.ses.wh.fr.dm.OpeColor;
import com.redescooter.ses.wh.fr.dm.OpeCustomer;
import com.redescooter.ses.wh.fr.dm.OpeCustomerInquiry;
import com.redescooter.ses.wh.fr.dm.OpeCustomerInquiryB;
import com.redescooter.ses.wh.fr.dm.OpeSaleScooter;
import com.redescooter.ses.wh.fr.dm.OpeSimInformation;
import com.redescooter.ses.wh.fr.dm.OpeSpecificatGroup;
import com.redescooter.ses.wh.fr.dm.OpeSpecificatType;
import com.redescooter.ses.wh.fr.dm.OpeWarehouseAccount;
import com.redescooter.ses.wh.fr.dm.OpeWmsScooterStock;
import com.redescooter.ses.wh.fr.dm.OpeWmsStockRecord;
import com.redescooter.ses.wh.fr.dm.OpeWmsStockSerialNumber;
import com.redescooter.ses.wh.fr.enums.StatusEnum;
import com.redescooter.ses.wh.fr.exception.ExceptionCodeEnums;
import com.redescooter.ses.wh.fr.exception.SesWebRosException;
import com.redescooter.ses.wh.fr.service.app.FrAppService;
import com.redescooter.ses.wh.fr.service.base.OpeCodebaseRelationService;
import com.redescooter.ses.wh.fr.service.base.OpeCodebaseRsnService;
import com.redescooter.ses.wh.fr.service.base.OpeCodebaseVinService;
import com.redescooter.ses.wh.fr.service.base.OpeColorService;
import com.redescooter.ses.wh.fr.service.base.OpeCustomerInquiryBService;
import com.redescooter.ses.wh.fr.service.base.OpeCustomerInquiryService;
import com.redescooter.ses.wh.fr.service.base.OpeSaleScooterService;
import com.redescooter.ses.wh.fr.service.base.OpeSimInformationService;
import com.redescooter.ses.wh.fr.service.base.OpeSpecificatGroupService;
import com.redescooter.ses.wh.fr.service.base.OpeWarehouseAccountService;
import com.redescooter.ses.wh.fr.service.base.OpeWmsStockSerialNumberService;
import com.redescooter.ses.wh.fr.vo.AppLoginEnter;
import com.redescooter.ses.wh.fr.vo.BindLicensePlateEnter;
import com.redescooter.ses.wh.fr.vo.BindVinEnter;
import com.redescooter.ses.wh.fr.vo.CustomerIdEnter;
import com.redescooter.ses.wh.fr.vo.InputBatteryEnter;
import com.redescooter.ses.wh.fr.vo.InputScooterEnter;
import com.redescooter.ses.wh.fr.vo.InquiryDetailEnter;
import com.redescooter.ses.wh.fr.vo.InquiryDetailResult;
import com.redescooter.ses.wh.fr.vo.InquiryListAppEnter;
import com.redescooter.ses.wh.fr.vo.InquiryListResult;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
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
    private OpeCarDistributeMapper opeCarDistributeMapper;

    @Autowired
    private OpeCarDistributeNodeMapper opeCarDistributeNodeMapper;

    @Autowired
    private OpeCarDistributeExMapper opeCarDistributeExMapper;

    @Autowired
    private OpeCustomerMapper opeCustomerMapper;

    @Autowired
    private OpeWmsScooterStockMapper opeWmsScooterStockMapper;

    @Autowired
    private OpeWmsStockRecordMapper opeWmsStockRecordMapper;

    @Autowired
    private OpeWmsStockSerialNumberService opeWmsStockSerialNumberService;

    @Autowired
    private OpeCustomerInquiryService opeCustomerInquiryService;

    @Autowired
    private OpeCustomerInquiryBService opeCustomerInquiryBService;

    @Autowired
    private OpeSaleScooterService opeSaleScooterService;

    @Autowired
    private OpeCodebaseRsnService opeCodebaseRsnService;

    @Autowired
    private OpeCodebaseVinService opeCodebaseVinService;

    @Autowired
    private OpeCodebaseRelationService opeCodebaseRelationService;

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

    @Value("${Request.privateKey}")
    private String privateKey;

    @DubboReference
    private IdAppService idAppService;

    @DubboReference
    private AccountBaseService accountBaseService;

    @DubboReference
    private ScooterService scooterService;

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
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }
        // 密码解密
        String decryptPassword;
        try {
            decryptPassword = RsaUtils.decrypt(enter.getPassword(), privateKey);
        } catch (Exception e) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
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
            throw new SesWebRosException(ExceptionCodeEnums.ACCOUNT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.ACCOUNT_IS_NOT_EXIST.getMessage());
        }
        // 拿着解密后的密码再次md5加密,和db存储的md5加密的密码相比较
        String encryptPassword = DigestUtils.md5Hex(decryptPassword + account.getSalt());
        if (!StringUtils.equals(encryptPassword, account.getPassword())) {
            throw new SesWebRosException(ExceptionCodeEnums.PASSROD_WRONG.getCode(), ExceptionCodeEnums.PASSROD_WRONG.getMessage());
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

    public Long getUserId(GeneralEnter enter) {
        String token = enter.getToken();
        Boolean flag = jedisCluster.exists(token);
        if (flag) {
            Map<String, String> map = jedisCluster.hgetAll(token);
            if (null != map) {
                String userId = map.get("userId");
                return Long.valueOf(userId);
            }
        }
        return null;
    }

    /**
     * 验证token
     */
    public void checkToken(GeneralEnter enter) {
        if (!jedisCluster.exists(enter.getToken())) {
            throw new SesWebRosException(ExceptionCodeEnums.TOKEN_NOT_EXIST.getCode(), ExceptionCodeEnums.TOKEN_NOT_EXIST.getMessage());
        }
    }

    /**
     * 获得个人信息
     */
    @Override
    public OpeWarehouseAccount getUserInfo(GeneralEnter enter) {
        checkToken(enter);
        Long userId = getUserId(enter);
        OpeWarehouseAccount account = opeWarehouseAccountService.getById(userId);
        if (null == account) {
            throw new SesWebRosException(ExceptionCodeEnums.ACCOUNT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.ACCOUNT_IS_NOT_EXIST.getMessage());
        }
        return account;
    }

    /**
     * 询价单列表
     */
    @Override
    public PageResult<InquiryListResult> getList(InquiryListAppEnter enter) {
        checkToken(enter);
        Long userId = getUserId(enter);
        int count = opeCarDistributeExMapper.getInquiryListCount(enter, userId);
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        if (StringUtils.isBlank(enter.getKeyword()) && null == enter.getStatus()) {
            return PageResult.createZeroRowResult(enter);
        }
        List<InquiryListResult> list = opeCarDistributeExMapper.getInquiryList(enter, userId);
        if (CollectionUtils.isNotEmpty(list)) {
            for (InquiryListResult item : list) {

                Integer flag = item.getFlag();
                Integer appNode = item.getAppNode();
                Integer webNode = item.getWebNode();

                // 这里的逻辑要和sql逻辑保持一致
                boolean todoFlag = flag == 0 && (appNode == 1 || webNode == 1);
                boolean dealFlag = flag == 1 && (appNode == 2 || appNode == 3 || appNode == 4 || appNode == 5);
                boolean doneFlag = flag == 2 && appNode == 6;

                if (todoFlag) {
                    item.setStatus(1);
                }
                if (dealFlag) {
                    item.setStatus(2);
                }
                if (doneFlag) {
                    item.setStatus(3);
                }

                // 处理中 已录入的电池数量,未录入的电池数量
                String battery = item.getBattery();
                if (StringUtils.isBlank(battery)) {
                    item.setHasInputBatteryNum(0);
                    item.setNoInputBatteryNum(item.getBatteryNum());
                } else {
                    String[] split = battery.split(",");
                    List<String> batteryList = new ArrayList<>(Arrays.asList(split));
                    batteryList.removeAll(Collections.singleton(null));
                    item.setHasInputBatteryNum(batteryList.size());
                    item.setNoInputBatteryNum(item.getBatteryNum() - batteryList.size());
                }
            }
        }

        // 正式客户且点击了创建账号的才能流转到待分配列表
        if (CollectionUtils.isNotEmpty(list)) {
            Iterator<InquiryListResult> iterator = list.iterator();
            while (iterator.hasNext()) {
                InquiryListResult next = iterator.next();
                String email = next.getEmail();
                // 拿着email去pla_user表查询,如果存在,说明已创建账号 flag:存在就是true,不存在就是false
                Boolean flag = assignScooterService.getPlaUserIsExistByEmail(email);
                if (!flag) {
                    iterator.remove();
                }
            }
        }
        return PageResult.create(enter, list.size(), list);
    }

    /**
     * 询价单详情
     */
    @Override
    public InquiryDetailResult getDetail(InquiryDetailEnter enter) {
        checkToken(enter);
        Long userId = getUserId(enter);

        // 校验此询价单是否已分配给其他仓库账号
        LambdaQueryWrapper<OpeCarDistribute> lqw = new LambdaQueryWrapper<>();
        lqw.eq(OpeCarDistribute::getDr, Constant.DR_FALSE);
        lqw.eq(OpeCarDistribute::getCustomerId, enter.getCustomerId());
        lqw.last("limit 1");
        OpeCarDistribute instance = opeCarDistributeMapper.selectOne(lqw);
        if (null != instance) {
            Long warehouseAccountId = instance.getWarehouseAccountId();
            if (null != warehouseAccountId && !userId.equals(warehouseAccountId)) {
                throw new SesWebRosException(ExceptionCodeEnums.ORDER_HAS_DISTRIBUTED.getCode(), ExceptionCodeEnums.ORDER_HAS_DISTRIBUTED.getMessage());
            }
        }

        // 先查看客户在node表是否存在数据,不存在就新建
        LambdaQueryWrapper<OpeCarDistributeNode> qw = new LambdaQueryWrapper<>();
        qw.eq(OpeCarDistributeNode::getDr, Constant.DR_FALSE);
        qw.eq(OpeCarDistributeNode::getCustomerId, enter.getCustomerId());
        List<OpeCarDistributeNode> nodeList = opeCarDistributeNodeMapper.selectList(qw);
        if (CollectionUtils.isEmpty(nodeList)) {

            OpeCustomerInquiry inquiry = opeCustomerInquiryService.getById(enter.getId());
            if (null == inquiry) {
                throw new SesWebRosException(ExceptionCodeEnums.INQUIRY_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.INQUIRY_IS_NOT_EXIST.getMessage());
            }
            Long productId = inquiry.getProductId();
            OpeSaleScooter saleScooter = opeSaleScooterService.getById(productId);
            if (null == saleScooter) {
                throw new SesWebRosException(ExceptionCodeEnums.SCOOTER_NOT_EXIST.getCode(), ExceptionCodeEnums.SCOOTER_NOT_EXIST.getMessage());
            }
            // 根据询价单id获得车辆型号id和颜色id
            Long specificatId = saleScooter.getSpecificatId();
            Long colorId = saleScooter.getColorId();

            // 新增node表
            OpeCarDistributeNode node = new OpeCarDistributeNode();
            node.setId(idAppService.getId(SequenceName.OPE_CAR_DISTRIBUTE_NODE));
            node.setDr(Constant.DR_FALSE);
            node.setCustomerId(enter.getCustomerId());
            node.setNode(1);
            node.setAppNode(1);
            node.setFlag(0);
            node.setCreatedBy(userId == null ? 0L : userId);
            node.setCreatedTime(new Date());
            opeCarDistributeNodeMapper.insert(node);

            // 新增主表
            OpeCarDistribute distribute = new OpeCarDistribute();
            distribute.setId(idAppService.getId(SequenceName.OPE_CAR_DISTRIBUTE));
            distribute.setDr(Constant.DR_FALSE);
            distribute.setCustomerId(enter.getCustomerId());
            distribute.setSpecificatTypeId(specificatId);
            distribute.setColorId(colorId);
            distribute.setSeatNumber(2);
            distribute.setQty(1);
            distribute.setCreatedBy(userId == null ? 0L : userId);
            distribute.setCreatedTime(new Date());
            opeCarDistributeMapper.insert(distribute);
        }

        InquiryDetailResult result = opeCarDistributeExMapper.getInquiryDetail(enter);
        if (null != result) {
            String battery = result.getBattery();
            if (StringUtils.isBlank(battery)) {
                result.setBatteryNumber(0);
                result.setBatteryList(new ArrayList<>());
            } else {
                String[] split = battery.split(",");
                List<String> batteryList = new ArrayList<>(Arrays.asList(split));
                batteryList.removeAll(Collections.singleton(null));
                result.setBatteryNumber(batteryList.size());
                result.setBatteryList(batteryList);
            }
        }
        return result;
    }

    /**
     * 绑定VIN
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult bindVin(BindVinEnter enter) {
        checkToken(enter);
        Long userId = getUserId(enter);
        String vinCode = enter.getVinCode().trim();
        Integer seatNumber = enter.getSeatNumber();

        // 校验此询价单是否已分配给其他仓库账号
        LambdaQueryWrapper<OpeCarDistribute> lqw = new LambdaQueryWrapper<>();
        lqw.eq(OpeCarDistribute::getDr, Constant.DR_FALSE);
        lqw.eq(OpeCarDistribute::getCustomerId, enter.getCustomerId());
        lqw.last("limit 1");
        OpeCarDistribute instance = opeCarDistributeMapper.selectOne(lqw);
        if (null != instance) {
            Long warehouseAccountId = instance.getWarehouseAccountId();
            String vin = instance.getVinCode();
            if (null != warehouseAccountId && !userId.equals(warehouseAccountId)) {
                throw new SesWebRosException(ExceptionCodeEnums.ORDER_HAS_DISTRIBUTED.getCode(), ExceptionCodeEnums.ORDER_HAS_DISTRIBUTED.getMessage());
            }
            if (StringUtils.isNotBlank(vin)) {
                throw new SesWebRosException(ExceptionCodeEnums.STEP_HAS_INPUT_IN_ROS.getCode(), ExceptionCodeEnums.STEP_HAS_INPUT_IN_ROS.getMessage());
            }
        }

        if (vinCode.length() != 17) {
            throw new SesWebRosException(ExceptionCodeEnums.VIN_NOT_MATCH.getCode(), ExceptionCodeEnums.VIN_NOT_MATCH.getMessage());
        }
        if (!vinCode.startsWith("VXSR2A")) {
            throw new SesWebRosException(ExceptionCodeEnums.VIN_NOT_MATCH.getCode(), ExceptionCodeEnums.VIN_NOT_MATCH.getMessage());
        }

        String productType = ProductTypeEnum.showCode(enter.getScooterName());
        // 截取第7位,车型编号
        String productTypeSub = vinCode.substring(6, 7);
        if (!StringUtils.equals(productType, productTypeSub)) {
            throw new SesWebRosException(ExceptionCodeEnums.VIN_NOT_MATCH.getCode(), ExceptionCodeEnums.VIN_NOT_MATCH.getMessage());
        }

        // 截取第8位,座位数量
        String seatNumberSub = vinCode.substring(7, 8);
        if (!StringUtils.equals(String.valueOf(seatNumber), seatNumberSub)) {
            throw new SesWebRosException(ExceptionCodeEnums.VIN_NOT_MATCH.getCode(), ExceptionCodeEnums.VIN_NOT_MATCH.getMessage());
        }

        LambdaQueryWrapper<OpeCarDistribute> checkWrapper = new LambdaQueryWrapper<>();
        checkWrapper.eq(OpeCarDistribute::getDr, Constant.DR_FALSE);
        checkWrapper.eq(OpeCarDistribute::getVinCode, vinCode);
        checkWrapper.last("limit 1");
        OpeCarDistribute checkModel = opeCarDistributeMapper.selectOne(checkWrapper);
        if (null != checkModel) {
            throw new SesWebRosException(ExceptionCodeEnums.VIN_HAS_INPUT.getCode(), ExceptionCodeEnums.VIN_HAS_INPUT.getMessage());
        }

        // 查看vin在码库中是否存在
        LambdaQueryWrapper<OpeCodebaseVin> existWrapper = new LambdaQueryWrapper<>();
        existWrapper.eq(OpeCodebaseVin::getDr, Constant.DR_FALSE);
        existWrapper.eq(OpeCodebaseVin::getStatus, 1);
        existWrapper.eq(OpeCodebaseVin::getVin, vinCode);
        int count = opeCodebaseVinService.count(existWrapper);
        if (count == 0) {
            throw new SesWebRosException(ExceptionCodeEnums.VIN_NOT_EXISTS_CODEBASE.getCode(), ExceptionCodeEnums.VIN_NOT_EXISTS_CODEBASE.getMessage());
        }

        // 修改主表
        OpeCarDistribute distribute = new OpeCarDistribute();
        distribute.setVinCode(vinCode);
        distribute.setWarehouseAccountId(userId);
        distribute.setUpdatedBy(enter.getUserId());
        distribute.setUpdatedTime(new Date());
        // 条件
        LambdaQueryWrapper<OpeCarDistribute> qw = new LambdaQueryWrapper<>();
        qw.eq(OpeCarDistribute::getDr, Constant.DR_FALSE);
        qw.eq(OpeCarDistribute::getCustomerId, enter.getCustomerId());
        opeCarDistributeMapper.update(distribute, qw);

        // node表appNode字段
        OpeCarDistributeNode node = new OpeCarDistributeNode();
        node.setNode(2);
        node.setAppNode(2);
        node.setFlag(1);
        node.setUpdatedBy(userId);
        node.setUpdatedTime(new Date());
        // 条件
        LambdaQueryWrapper<OpeCarDistributeNode> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OpeCarDistributeNode::getDr, Constant.DR_FALSE);
        wrapper.eq(OpeCarDistributeNode::getCustomerId, enter.getCustomerId());
        opeCarDistributeNodeMapper.update(node, wrapper);

        // 修改码库此vin为已用
        LambdaQueryWrapper<OpeCodebaseVin> vinWrapper = new LambdaQueryWrapper<>();
        vinWrapper.eq(OpeCodebaseVin::getDr, Constant.DR_FALSE);
        vinWrapper.eq(OpeCodebaseVin::getStatus, 1);
        vinWrapper.eq(OpeCodebaseVin::getVin, vinCode);
        vinWrapper.last("limit 1");
        OpeCodebaseVin codebaseVin = opeCodebaseVinService.getOne(vinWrapper);
        if (null != codebaseVin) {
            codebaseVin.setStatus(2);
            codebaseVin.setUpdatedBy(userId);
            codebaseVin.setUpdatedTime(new Date());
            opeCodebaseVinService.updateById(codebaseVin);
        }

        // 码库关系表,rsn和vin绑定,先给vin,rsn在绑定车辆时给
        LambdaQueryWrapper<OpeCodebaseRelation> relationWrapper = new LambdaQueryWrapper<>();
        relationWrapper.eq(OpeCodebaseRelation::getDr, Constant.DR_FALSE);
        relationWrapper.eq(OpeCodebaseRelation::getVin, vinCode);
        relationWrapper.last("limit 1");
        OpeCodebaseRelation codebaseRelation = opeCodebaseRelationService.getOne(relationWrapper);
        if (null == codebaseRelation) {
            OpeCodebaseRelation relation = new OpeCodebaseRelation();
            relation.setId(idAppService.getId(SequenceName.OPE_CUSTOMER));
            relation.setDr(Constant.DR_FALSE);
            relation.setVin(vinCode);
            relation.setStatus(1);
            relation.setCreatedBy(userId);
            relation.setCreatedTime(new Date());
            opeCodebaseRelationService.save(relation);
        }
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 绑定车牌
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult bindLicensePlate(BindLicensePlateEnter enter) {
        checkToken(enter);
        Long userId = getUserId(enter);
        String licensePlate = enter.getLicensePlate().trim();
        Long customerId = enter.getCustomerId();

        // 校验是否已被操作过
        LambdaQueryWrapper<OpeCarDistribute> lqw = new LambdaQueryWrapper<>();
        lqw.eq(OpeCarDistribute::getDr, Constant.DR_FALSE);
        lqw.eq(OpeCarDistribute::getCustomerId, enter.getCustomerId());
        lqw.last("limit 1");
        OpeCarDistribute instance = opeCarDistributeMapper.selectOne(lqw);
        if (null != instance) {
            String plate = instance.getLicensePlate();
            if (StringUtils.isNotBlank(plate)) {
                throw new SesWebRosException(ExceptionCodeEnums.STEP_HAS_INPUT_IN_ROS.getCode(), ExceptionCodeEnums.STEP_HAS_INPUT_IN_ROS.getMessage());
            }
        }

        LambdaQueryWrapper<OpeCarDistribute> checkWrapper = new LambdaQueryWrapper<>();
        checkWrapper.eq(OpeCarDistribute::getDr, Constant.DR_FALSE);
        checkWrapper.eq(OpeCarDistribute::getLicensePlate, licensePlate);
        checkWrapper.last("limit 1");
        OpeCarDistribute checkModel = opeCarDistributeMapper.selectOne(checkWrapper);
        if (null != checkModel) {
            throw new SesWebRosException(ExceptionCodeEnums.PLATE_HAS_USED.getCode(), ExceptionCodeEnums.PLATE_HAS_USED.getMessage());
        }

        // 修改主表
        OpeCarDistribute distribute = new OpeCarDistribute();
        distribute.setLicensePlate(licensePlate);
        distribute.setUpdatedBy(enter.getUserId());
        distribute.setUpdatedTime(new Date());
        // 条件
        LambdaQueryWrapper<OpeCarDistribute> qw = new LambdaQueryWrapper<>();
        qw.eq(OpeCarDistribute::getDr, Constant.DR_FALSE);
        qw.eq(OpeCarDistribute::getCustomerId, customerId);
        opeCarDistributeMapper.update(distribute, qw);

        // node表appNode字段
        OpeCarDistributeNode node = new OpeCarDistributeNode();
        node.setNode(3);
        node.setAppNode(3);
        node.setUpdatedBy(userId);
        node.setUpdatedTime(new Date());
        // 条件
        LambdaQueryWrapper<OpeCarDistributeNode> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OpeCarDistributeNode::getDr, Constant.DR_FALSE);
        wrapper.eq(OpeCarDistributeNode::getCustomerId, customerId);
        opeCarDistributeNodeMapper.update(node, wrapper);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 校验询价单是否被操作过
     */
    /*@Override
    public BooleanResult checkOperation(CustomerIdEnter enter) {
        checkToken(enter);
        BooleanResult result = new BooleanResult();
        result.setSuccess(true);

        LambdaQueryWrapper<OpeCarDistributeNode> qw = new LambdaQueryWrapper<>();
        qw.eq(OpeCarDistributeNode::getDr, Constant.DR_FALSE);
        qw.eq(OpeCarDistributeNode::getCustomerId, enter.getCustomerId());
        qw.last("limit 1");
        OpeCarDistributeNode node = opeCarDistributeNodeMapper.selectOne(qw);
        if (null != node) {
            Integer flag = node.getFlag();
            if (flag == 1 || flag == 2) {
                result.setSuccess(false);
            }
        }
        result.setRequestId(enter.getRequestId());
        return result;
    }*/

    /**
     * 录入车辆
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult inputScooter(InputScooterEnter enter) {
        checkToken(enter);
        Long userId = getUserId(enter);
        Long customerId = enter.getCustomerId();
        String rsn = enter.getRsn().trim();
        String tabletSn = enter.getTabletSn().trim();
        String bluetoothAddress = enter.getBluetoothAddress().trim();
        String bbi = enter.getBbi().trim();
        String controller = enter.getController().trim();
        String electricMachinery = enter.getElectricMachinery().trim();
        String meter = enter.getMeter().trim();
        String imei = enter.getImei().trim();

        // 校验是否已被操作过
        LambdaQueryWrapper<OpeCarDistribute> scooterWrapper = new LambdaQueryWrapper<>();
        scooterWrapper.eq(OpeCarDistribute::getDr, Constant.DR_FALSE);
        scooterWrapper.eq(OpeCarDistribute::getCustomerId, enter.getCustomerId());
        scooterWrapper.last("limit 1");
        OpeCarDistribute instance = opeCarDistributeMapper.selectOne(scooterWrapper);
        if (null != instance) {
            if (StringUtils.isNotBlank(instance.getRsn()) || StringUtils.isNotBlank(instance.getTabletSn()) || StringUtils.isNotBlank(instance.getBluetoothAddress())) {
                throw new SesWebRosException(ExceptionCodeEnums.STEP_HAS_INPUT_IN_ROS.getCode(), ExceptionCodeEnums.STEP_HAS_INPUT_IN_ROS.getMessage());
            }
        }

        LambdaQueryWrapper<OpeCarDistribute> lqw = new LambdaQueryWrapper<>();
        lqw.eq(OpeCarDistribute::getDr, Constant.DR_FALSE);
        lqw.eq(OpeCarDistribute::getRsn, rsn);
        lqw.last("limit 1");
        OpeCarDistribute model = opeCarDistributeMapper.selectOne(lqw);
        if (null != model) {
            throw new SesWebRosException(ExceptionCodeEnums.PARTS_HAS_INPUT.getCode(), ExceptionCodeEnums.PARTS_HAS_INPUT.getMessage());
        }

        LambdaQueryWrapper<OpeCarDistribute> checkWrapper = new LambdaQueryWrapper<>();
        checkWrapper.eq(OpeCarDistribute::getDr, Constant.DR_FALSE);
        checkWrapper.eq(OpeCarDistribute::getCustomerId, customerId);
        checkWrapper.last("limit 1");
        OpeCarDistribute checkModel = opeCarDistributeMapper.selectOne(checkWrapper);
        if (null != checkModel && null != checkModel.getWarehouseAccountId() && StringUtils.isNotBlank(checkModel.getRsn())) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_HAS_DEAL.getCode(), ExceptionCodeEnums.ORDER_HAS_DEAL.getMessage());
        }

        // 查看rsn在码库中是否存在
        LambdaQueryWrapper<OpeCodebaseRsn> existWrapper = new LambdaQueryWrapper<>();
        existWrapper.eq(OpeCodebaseRsn::getDr, Constant.DR_FALSE);
        existWrapper.eq(OpeCodebaseRsn::getStatus, 1);
        existWrapper.eq(OpeCodebaseRsn::getRsn, rsn);
        int count = opeCodebaseRsnService.count(existWrapper);
        if (count == 0) {
            throw new SesWebRosException(ExceptionCodeEnums.RSN_NOT_EXISTS_CODEBASE.getCode(), ExceptionCodeEnums.RSN_NOT_EXISTS_CODEBASE.getMessage());
        }

        // 查看rsn对应的车型(低速/高速)和询价单对应的车型(低速/高速)是否相符,不相符抛出异常
        check(rsn, customerId);

        // 修改主表
        OpeCarDistribute distribute = new OpeCarDistribute();
        distribute.setSpecificatTypeId(enter.getSpecificatTypeId());
        distribute.setSeatNumber(enter.getSeatNumber());
        distribute.setRsn(rsn);
        distribute.setBluetoothAddress(bluetoothAddress);
        distribute.setTabletSn(tabletSn);
        distribute.setBbi(bbi);
        distribute.setController(controller);
        distribute.setElectricMachinery(electricMachinery);
        distribute.setMeter(meter);
        distribute.setImei(imei);
        distribute.setColorId(enter.getColorId());
        distribute.setUpdatedBy(enter.getUserId());
        distribute.setUpdatedTime(new Date());

        // 条件
        LambdaQueryWrapper<OpeCarDistribute> qw = new LambdaQueryWrapper<>();
        qw.eq(OpeCarDistribute::getDr, Constant.DR_FALSE);
        qw.eq(OpeCarDistribute::getCustomerId, customerId);
        opeCarDistributeMapper.update(distribute, qw);

        // node表appNode字段
        OpeCarDistributeNode node = new OpeCarDistributeNode();
        node.setNode(4);
        node.setAppNode(4);
        node.setUpdatedBy(userId);
        node.setUpdatedTime(new Date());
        // 条件
        LambdaQueryWrapper<OpeCarDistributeNode> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OpeCarDistributeNode::getDr, Constant.DR_FALSE);
        wrapper.eq(OpeCarDistributeNode::getCustomerId, customerId);
        opeCarDistributeNodeMapper.update(node, wrapper);

        // 修改码库此RSN为已用
        LambdaQueryWrapper<OpeCodebaseRsn> rsnWrapper = new LambdaQueryWrapper<>();
        rsnWrapper.eq(OpeCodebaseRsn::getDr, Constant.DR_FALSE);
        rsnWrapper.eq(OpeCodebaseRsn::getStatus, 1);
        rsnWrapper.eq(OpeCodebaseRsn::getRsn, rsn);
        rsnWrapper.last("limit 1");
        OpeCodebaseRsn codebaseRsn = opeCodebaseRsnService.getOne(rsnWrapper);
        if (null != codebaseRsn) {
            codebaseRsn.setStatus(2);
            codebaseRsn.setUpdatedBy(userId);
            codebaseRsn.setUpdatedTime(new Date());
            opeCodebaseRsnService.updateById(codebaseRsn);
        }

        // 修改码库关系表
        OpeCodebaseRelation relation = new OpeCodebaseRelation();
        relation.setRsn(rsn);
        relation.setStatus(2);
        relation.setUpdatedBy(enter.getUserId());
        relation.setUpdatedTime(new Date());
        LambdaQueryWrapper<OpeCodebaseRelation> relationWrapper = new LambdaQueryWrapper<>();
        relationWrapper.eq(OpeCodebaseRelation::getDr, Constant.DR_FALSE);
        relationWrapper.eq(OpeCodebaseRelation::getVin, enter.getVin());
        relationWrapper.eq(OpeCodebaseRelation::getStatus, 1);
        opeCodebaseRelationService.update(relation, relationWrapper);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 录入电池
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult inputBattery(InputBatteryEnter enter) {
        checkToken(enter);
        Long userId = getUserId(enter);
        Long inquiryId = enter.getId();

        LambdaQueryWrapper<OpeCarDistribute> checkWrapper = new LambdaQueryWrapper<>();
        checkWrapper.eq(OpeCarDistribute::getDr, Constant.DR_FALSE);
        checkWrapper.like(OpeCarDistribute::getBattery, enter.getBattery());
        checkWrapper.last("limit 1");
        OpeCarDistribute checkModel = opeCarDistributeMapper.selectOne(checkWrapper);
        if (null != checkModel) {
            throw new SesWebRosException(ExceptionCodeEnums.PARTS_HAS_INPUT.getCode(), ExceptionCodeEnums.PARTS_HAS_INPUT.getMessage());
        }

        // 得到询价单的电池数量
        LambdaQueryWrapper<OpeCustomerInquiryB> lqw = new LambdaQueryWrapper<>();
        lqw.eq(OpeCustomerInquiryB::getDr, Constant.DR_FALSE);
        lqw.eq(OpeCustomerInquiryB::getInquiryId, inquiryId);
        lqw.last("limit 1");
        OpeCustomerInquiryB inquiryB = opeCustomerInquiryBService.getOne(lqw);
        Integer batteryNum = inquiryB.getProductQty();

        // 校验是否已被操作过
        LambdaQueryWrapper<OpeCarDistribute> batteryWrapper = new LambdaQueryWrapper<>();
        batteryWrapper.eq(OpeCarDistribute::getDr, Constant.DR_FALSE);
        batteryWrapper.eq(OpeCarDistribute::getCustomerId, enter.getCustomerId());
        batteryWrapper.last("limit 1");
        OpeCarDistribute instance = opeCarDistributeMapper.selectOne(batteryWrapper);
        if (null != instance) {
            String battery = instance.getBattery();
            if (StringUtils.isNotBlank(battery)) {
                String[] split = battery.split(",");
                List<String> batteryList = new ArrayList<>(Arrays.asList(split));
                if (CollectionUtils.isNotEmpty(batteryList)) {
                    if (batteryList.size() == batteryNum) {
                        throw new SesWebRosException(ExceptionCodeEnums.STEP_HAS_INPUT_IN_ROS.getCode(), ExceptionCodeEnums.STEP_HAS_INPUT_IN_ROS.getMessage());
                    }
                }
            }
        }

        LambdaQueryWrapper<OpeCarDistribute> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OpeCarDistribute::getDr, Constant.DR_FALSE);
        queryWrapper.eq(OpeCarDistribute::getCustomerId, enter.getCustomerId());
        queryWrapper.last("limit 1");
        OpeCarDistribute model = opeCarDistributeMapper.selectOne(queryWrapper);
        if (null != model) {
            String modelBattery = model.getBattery();
            if (StringUtils.isBlank(modelBattery)) {
                // 第一次扫描电池
                OpeCarDistribute distribute = new OpeCarDistribute();
                distribute.setBattery(enter.getBattery().trim());
                distribute.setUpdatedBy(userId);
                distribute.setUpdatedTime(new Date());
                LambdaQueryWrapper<OpeCarDistribute> qw = new LambdaQueryWrapper<>();
                qw.eq(OpeCarDistribute::getDr, Constant.DR_FALSE);
                qw.eq(OpeCarDistribute::getCustomerId, enter.getCustomerId());
                opeCarDistributeMapper.update(distribute, qw);
            } else {
                // 之前已经扫描过电池,在电池后追加
                OpeCarDistribute distribute = new OpeCarDistribute();
                distribute.setBattery(modelBattery + "," + enter.getBattery().trim());
                distribute.setUpdatedBy(userId);
                distribute.setUpdatedTime(new Date());
                LambdaQueryWrapper<OpeCarDistribute> qw = new LambdaQueryWrapper<>();
                qw.eq(OpeCarDistribute::getDr, Constant.DR_FALSE);
                qw.eq(OpeCarDistribute::getCustomerId, enter.getCustomerId());
                opeCarDistributeMapper.update(distribute, qw);
            }
        }

        // 查询最新的已经扫描过的电池数量
        OpeCarDistribute distribute = opeCarDistributeMapper.selectOne(queryWrapper);
        if (null != distribute) {
            String[] split = distribute.getBattery().split(",");
            List<String> batteryList = new ArrayList<>(Arrays.asList(split));
            // 如果已经扫描过的电池数量=询价单的电池数量
            if (CollectionUtils.isNotEmpty(batteryList)) {
                if (batteryNum.equals(batteryList.size())) {
                    // node表appNode字段
                    OpeCarDistributeNode node = new OpeCarDistributeNode();
                    node.setNode(5);
                    node.setAppNode(5);
                    node.setUpdatedBy(userId);
                    node.setUpdatedTime(new Date());
                    // 条件
                    LambdaQueryWrapper<OpeCarDistributeNode> wrapper = new LambdaQueryWrapper<>();
                    wrapper.eq(OpeCarDistributeNode::getDr, Constant.DR_FALSE);
                    wrapper.eq(OpeCarDistributeNode::getCustomerId, enter.getCustomerId());
                    opeCarDistributeNodeMapper.update(node, wrapper);
                }
            }
        }
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 设置软体
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult setScooterModel(CustomerIdEnter enter) {
        checkToken(enter);
        log.info("开始设置软体,入参是:[{}]", enter);
        Long userId = getUserId(enter);

        LambdaQueryWrapper<OpeCarDistribute> scooterWrapper = new LambdaQueryWrapper<>();
        scooterWrapper.eq(OpeCarDistribute::getDr, Constant.DR_FALSE);
        scooterWrapper.eq(OpeCarDistribute::getCustomerId, enter.getCustomerId());
        scooterWrapper.last("limit 1");
        OpeCarDistribute model = opeCarDistributeMapper.selectOne(scooterWrapper);

        // 客户信息
        OpeCustomer opeCustomer = opeCustomerMapper.selectById(enter.getCustomerId());
        if (null == opeCustomer) {
            throw new SesWebRosException(ExceptionCodeEnums.CUSTOMER_NOT_EXIST.getCode(), ExceptionCodeEnums.CUSTOMER_NOT_EXIST.getMessage());
        }
        if (!StringUtils.equals(opeCustomer.getStatus(), CustomerStatusEnum.OFFICIAL_CUSTOMER.getValue())) {
            throw new SesWebRosException(ExceptionCodeEnums.CONVERT_TO_FORMAL_CUSTOMER_FIRST.getCode(), ExceptionCodeEnums.CONVERT_TO_FORMAL_CUSTOMER_FIRST.getMessage());
        }

        // 查询客户的账号信息(查pla_user表)
        QueryAccountResult accountInfo = accountBaseService.customerAccountDeatil(opeCustomer.getEmail());
        if (null == accountInfo) {
            throw new SesWebRosException(ExceptionCodeEnums.ACCOUNT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.ACCOUNT_IS_NOT_EXIST.getMessage());
        }

        // 客户和车辆产生绑定关系
        List<HubSaveScooterEnter> saveRelationList = Lists.newArrayList();

        // 获得车牌
        String licensePlate = model.getLicensePlate();
        log.info("设置软体车牌是:[{}]", licensePlate);

        // 修改成品库车辆库存
        // 获得询价单型号id和颜色id
        CustomerIdEnter customerIdEnter = new CustomerIdEnter();
        customerIdEnter.setCustomerId(enter.getCustomerId());
        Map<String, Long> map = getSpecificatIdAndColorId(customerIdEnter);
        log.info("询价单型号和颜色分别是:[{}]", map);
        Long specificatId = map.get("specificatId");
        Long inquiryColorId = map.get("colorId");

        // 法国仓库指定车型和颜色
        LambdaQueryWrapper<OpeWmsScooterStock> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OpeWmsScooterStock::getDr, Constant.DR_FALSE);
        wrapper.eq(OpeWmsScooterStock::getGroupId, specificatId);
        wrapper.eq(OpeWmsScooterStock::getColorId, inquiryColorId);
        wrapper.eq(OpeWmsScooterStock::getStockType, 2);
        wrapper.orderByDesc(OpeWmsScooterStock::getCreatedTime);
        List<OpeWmsScooterStock> stockList = opeWmsScooterStockMapper.selectList(wrapper);
        OpeWmsScooterStock stock = null;
        if (CollectionUtils.isEmpty(stockList)) {
            throw new SesWebRosException(ExceptionCodeEnums.STOCK_IS_SHORTAGE.getCode(), ExceptionCodeEnums.STOCK_IS_SHORTAGE.getMessage());
        }

        stock = stockList.get(0);
        log.info("当前库存信息为:[{}]", stock);
        // 得到原先库存的可用库存数量和已用库存数量
        Long stockId = stock.getId();
        Integer ableStockQty = stock.getAbleStockQty();
        Integer usedStockQty = stock.getUsedStockQty();

        if (ableStockQty < 1) {
            throw new SesWebRosException(ExceptionCodeEnums.SCOOTER_STOCK_IS_NOT_ENOUGH.getCode(), ExceptionCodeEnums.SCOOTER_STOCK_IS_NOT_ENOUGH.getMessage());
        }

        // 原先库存的可用库存数量-1,已用库存数量+1
        OpeWmsScooterStock scooterStock = new OpeWmsScooterStock();
        scooterStock.setId(stockId);
        scooterStock.setAbleStockQty(ableStockQty - 1);
        scooterStock.setUsedStockQty(usedStockQty + 1);
        scooterStock.setUpdatedBy(userId);
        scooterStock.setUpdatedTime(new Date());
        opeWmsScooterStockMapper.updateById(scooterStock);

        // 新增出库记录
        if (null != stock) {
            log.info(" 新增出库记录");
            OpeWmsStockRecord record = new OpeWmsStockRecord();
            record.setId(idAppService.getId(SequenceName.OPE_WMS_STOCK_RECORD));
            record.setDr(Constant.DR_FALSE);
            record.setRelationId(stock.getId());
            record.setRelationType(7);
            record.setInWhQty(1);
            record.setRecordType(2);
            record.setStockType(2);
            record.setCreatedBy(userId);
            record.setCreatedTime(new Date());
            opeWmsStockRecordMapper.insert(record);
        }

        // 获得规格名称
        String specificatName = getSpecificatNameById(model.getSpecificatTypeId());

        // 根据rsn查询sco_scooter表
        ScoScooterResult scoScooter = scooterService.getScoScooterByTableSn(model.getRsn());
        if (null == scoScooter) {
            throw new SesWebRosException(ExceptionCodeEnums.SCOOTER_NOT_EXIST.getCode(), ExceptionCodeEnums.SCOOTER_NOT_EXIST.getMessage());
        }
        Long scooterId = scoScooter.getId();
        if (null == scooterId) {
            throw new SesWebRosException(ExceptionCodeEnums.SCOOTER_NOT_EXIST.getCode(), ExceptionCodeEnums.SCOOTER_NOT_EXIST.getMessage());
        }

        // 修改sco_scooter的牌照
        log.info("开始修改sco_scooter的牌照,入参分别是:[{},{}]", scooterId, licensePlate);
        scooterService.updateScooterNo(scooterId, licensePlate);
        log.info("修改sco_scooter的牌照结束");

        HubSaveScooterEnter item = new HubSaveScooterEnter();
        item.setScooterId(scooterId);
        item.setModel(ScooterModelEnums.showValueByCode(specificatName));
        item.setLongitude(MapUtil.randomLonLat(Constant.lng));
        item.setLatitude(MapUtil.randomLonLat(Constant.lng));
        item.setLicensePlatePicture(null);
        item.setStatus(ScooterStatusEnums.AVAILABLE.getValue());
        item.setUserId(accountInfo.getId());
        item.setTenantId(accountInfo.getTenantId());
        saveRelationList.add(item);
        cusotmerScooterService.saveScooter(saveRelationList);

        // 根据rsn修改库存产品序列号表的库存状态为不可用
        LambdaQueryWrapper<OpeWmsStockSerialNumber> qw = new LambdaQueryWrapper<>();
        qw.eq(OpeWmsStockSerialNumber::getDr, Constant.DR_FALSE);
        qw.eq(OpeWmsStockSerialNumber::getRsn, model.getRsn());
        qw.eq(OpeWmsStockSerialNumber::getStockStatus, WmsStockStatusEnum.AVAILABLE.getStatus());
        List<OpeWmsStockSerialNumber> serialNumberList = opeWmsStockSerialNumberService.list(qw);
        if (CollectionUtils.isNotEmpty(serialNumberList)) {
            for (OpeWmsStockSerialNumber serialNumber : serialNumberList) {
                if (null != serialNumber) {
                    serialNumber.setStockStatus(WmsStockStatusEnum.UNAVAILABLE.getStatus());
                    opeWmsStockSerialNumberService.updateById(serialNumber);
                }
            }
        }
        log.info("分车流程完毕");

        // 创建车辆
        AdmScooter admScooter = scooterModelService.getScooterBySn(model.getTabletSn());
        if (null != admScooter) {
            throw new SesWebRosException(ExceptionCodeEnums.SN_ALREADY_EXISTS.getCode(), ExceptionCodeEnums.SN_ALREADY_EXISTS.getMessage());
        }
        log.info("车辆不存在");
        SpecificGroupDTO group = specificService.getSpecificGroupById(specificatId);
        ColorDTO color = colorService.getColorInfoById(inquiryColorId);
        if (null == group) {
            throw new SesWebRosException(ExceptionCodeEnums.GROUP_NOT_EXIST.getCode(), ExceptionCodeEnums.GROUP_NOT_EXIST.getMessage());
        }
        if (null == color) {
            throw new SesWebRosException(ExceptionCodeEnums.COLOR_NOT_EXIST.getCode(), ExceptionCodeEnums.COLOR_NOT_EXIST.getMessage());
        }

        // 新增adm_scooter表
        AdmScooter scooter = new AdmScooter();
        scooter.setId(idAppService.getId(SequenceName.OPE_CAR_DISTRIBUTE_NODE));
        scooter.setDr(Constant.DR_FALSE);
        scooter.setSn(model.getTabletSn());
        scooter.setGroupId(specificatId);
        scooter.setColorId(inquiryColorId);
        scooter.setMacAddress(model.getBluetoothAddress());
        scooter.setScooterController(ScooterModelEnum.getScooterModelType(specificatName));
        scooter.setCreatedBy(0L);
        scooter.setCreatedTime(new Date());
        scooter.setUpdatedBy(0L);
        scooter.setUpdatedTime(new Date());
        scooter.setColorName(color.getColorName());
        scooter.setColorValue(color.getColorValue());
        scooter.setGroupName(group.getGroupName());
        scooter.setMacName(model.getBluetoothAddress());
        scooterModelService.insertScooter(scooter);
        log.info("新增adm_scooter表成功");

        // 根据平板序列号(sn)查询在sco_scooter表是否存在 不存在返回true 存在返回false
        Boolean flag = scooterService.getSnIsExist(scooter.getSn());
        if (flag) {
            log.info("sn在sco_scooter表不存在");
            String scooterNo = generateScooterNo();
            scooterService.syncScooterData(buildData(scooter.getId(), scooter.getSn(), scooter.getScooterController(), userId, scooterNo));
        }

        // 设置软体
        AdmScooter scooterModel = scooterModelService.getScooterById(scooter.getId());
        if (null == scooterModel) {
            throw new SesWebRosException(ExceptionCodeEnums.SCOOTER_NOT_EXIST.getCode(), ExceptionCodeEnums.SCOOTER_NOT_EXIST.getMessage());
        }
        // 只允许车辆关闭状态时进行软体设置
        String status = scooterService.getScooterStatusByTabletSn(scooter.getSn());
        if (ScooterLockStatusEnums.UNLOCK.getValue().equals(status)) {
            throw new SesWebRosException(ExceptionCodeEnums.SCOOTER_NOT_CLOSED.getCode(), ExceptionCodeEnums.SCOOTER_NOT_CLOSED.getMessage());
        }
        SpecificTypeDTO specificType = specificService.getSpecificTypeByName(specificatName);
        if (null == specificType) {
            throw new SesWebRosException(ExceptionCodeEnums.SPECIFICAT_TYPE_NOT_EXIST.getCode(), ExceptionCodeEnums.SPECIFICAT_TYPE_NOT_EXIST.getMessage());
        }
        Integer type = ScooterModelEnum.getScooterModelType(specificType.getSpecificatName());
        if (type == 0) {
            throw new SesWebRosException(ExceptionCodeEnums.SELECT_SCOOTER_MODEL_ERROR.getCode(), ExceptionCodeEnums.SELECT_SCOOTER_MODEL_ERROR.getMessage());
        }

        // 如果设置的型号与当前车辆的型号一致则不做操作
        /*if (null != scooterModel.getScooterController() && scooterModel.getScooterController().equals(type)) {
            log.info("设置的型号与当前车辆的型号一致,直接返回,没有发送emq消息");
            // node表appNode字段
            OpeCarDistributeNode node = new OpeCarDistributeNode();
            node.setAppNode(6);
            node.setFlag(2);
            node.setUpdatedBy(userId);
            node.setUpdatedTime(new Date());
            // 条件
            LambdaQueryWrapper<OpeCarDistributeNode> lqw = new LambdaQueryWrapper<>();
            lqw.eq(OpeCarDistributeNode::getDr, Constant.DR_FALSE);
            lqw.eq(OpeCarDistributeNode::getCustomerId, enter.getCustomerId());
            opeCarDistributeNodeMapper.update(node, lqw);
            return new GeneralResult(enter.getRequestId());
        }*/

        // 更新车辆型号信息
        AdmScooterUpdateEnter param = new AdmScooterUpdateEnter();
        param.setId(scooterModel.getId());
        param.setScooterController(type);
        param.setGroupId(specificType.getGroupId());
        param.setGroupName(specificType.getGroupName());
        scooterModelService.updateAdmScooter(param);
        log.info("更新车辆型号信息");
        scooterService.syncScooterModel(scooterModel.getSn(), type);
        log.info("同步车辆型号");

        List<SpecificDefGroupPublishDTO> list = buildSetScooterModelData(specificType.getId());
        if (CollectionUtils.isNotEmpty(list)) {
            // 发送EMQ消息,通知车辆那边进行升级处理
            SetScooterModelPublishDTO instance = new SetScooterModelPublishDTO();
            instance.setTabletSn(scooterModel.getSn());
            instance.setScooterModel(type);
            instance.setSpecificDefGroupList(list);
            scooterEmqXService.setScooterModel(instance);
        }
        log.info("设置软体完毕");

        // node表appNode字段
        OpeCarDistributeNode node = new OpeCarDistributeNode();
        node.setAppNode(6);
        node.setFlag(2);
        node.setUpdatedBy(userId);
        node.setUpdatedTime(new Date());
        // 条件
        LambdaQueryWrapper<OpeCarDistributeNode> lqw = new LambdaQueryWrapper<>();
        lqw.eq(OpeCarDistributeNode::getDr, Constant.DR_FALSE);
        lqw.eq(OpeCarDistributeNode::getCustomerId, enter.getCustomerId());
        opeCarDistributeNodeMapper.update(node, lqw);

        /*---------------sim 信息录入--------------*/
        LambdaQueryWrapper<OpeCarDistribute> qwOpeCar = new LambdaQueryWrapper<>();
        qwOpeCar.eq(OpeCarDistribute::getDr, Constant.DR_FALSE);
        qwOpeCar.eq(OpeCarDistribute::getCustomerId, opeCustomer.getId());
        qwOpeCar.last("limit 1");
        OpeCarDistribute opeCarDistribute = opeCarDistributeMapper.selectOne(qwOpeCar);
        if (null != opeCarDistribute) {
            OpeSimInformation simInfo = new OpeSimInformation();
            String iccid = jedisCluster.get(opeCarDistribute.getTabletSn());
            if (StringUtils.isNotBlank(iccid)) {
                simInfo.setSimIccid(iccid);
            }
            simInfo.setRsn(opeCarDistribute.getRsn());
            simInfo.setBluetoothMacAddress(opeCarDistribute.getBluetoothAddress());
            simInfo.setTabletSn(opeCarDistribute.getTabletSn());
            simInfo.setVin(opeCarDistribute.getVinCode());
            simInfo.setCreatedBy(enter.getUserId());
            simInfo.setCreatedTime(new Date());
            simInformationService.save(simInfo);
            jedisCluster.del(opeCarDistribute.getTabletSn());
        }
        /*---------------sim 信息录入--------------*/
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
                    throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
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
            throw new SesWebRosException(ExceptionCodeEnums.RSN_NOT_EXIST.getCode(), ExceptionCodeEnums.RSN_NOT_EXIST.getMessage());
        }
        Long relationId = serialNumber.getRelationId();
        OpeWmsScooterStock stock = opeWmsScooterStockMapper.selectById(relationId);
        if (null == stock) {
            throw new SesWebRosException(ExceptionCodeEnums.SCOOTER_STOCK_IS_EMPTY.getCode(), ExceptionCodeEnums.SCOOTER_STOCK_IS_EMPTY.getMessage());
        }
        Long groupId = stock.getGroupId();
        Long colorId = stock.getColorId();
        OpeSpecificatGroup group = opeSpecificatGroupService.getById(groupId);
        OpeColor color = opeColorService.getById(colorId);
        if (null == group) {
            throw new SesWebRosException(ExceptionCodeEnums.GROUP_NOT_EXIST.getCode(), ExceptionCodeEnums.GROUP_NOT_EXIST.getMessage());
        }
        if (null == color) {
            throw new SesWebRosException(ExceptionCodeEnums.COLOR_NOT_EXIST.getCode(), ExceptionCodeEnums.COLOR_NOT_EXIST.getMessage());
        }

        // 获得询价单产品是低速还是高速,以及颜色
        OpeSaleScooter saleScooter = getSaleScooter(customerId);
        if (!Objects.equals(groupId, saleScooter.getGroupId()) || !Objects.equals(colorId, saleScooter.getColorId())) {
            throw new SesWebRosException(ExceptionCodeEnums.SPECIFICAT_NOT_MATCH.getCode(), ExceptionCodeEnums.SPECIFICAT_NOT_MATCH.getMessage());
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
            throw new SesWebRosException(ExceptionCodeEnums.INQUIRY_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.INQUIRY_IS_NOT_EXIST.getMessage());
        }
        Long productId = inquiry.getProductId();
        OpeSaleScooter saleScooter = opeSaleScooterService.getById(productId);
        if (null == saleScooter) {
            throw new SesWebRosException(ExceptionCodeEnums.SCOOTER_NOT_EXIST.getCode(), ExceptionCodeEnums.SCOOTER_NOT_EXIST.getMessage());
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
            throw new SesWebRosException(ExceptionCodeEnums.CUSTOMER_NOT_EXIST.getCode(), ExceptionCodeEnums.CUSTOMER_NOT_EXIST.getMessage());
        }
        OpeCustomerInquiry customerInquiry = list.get(0);
        Long productId = customerInquiry.getProductId();
        if (null == productId) {
            throw new SesWebRosException(ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getMessage());
        }

        // 拿着产品id去ope_sale_scooter查询
        OpeSaleScooter opeSaleScooter = opeSaleScooterMapper.selectById(productId);
        if (null == opeSaleScooter) {
            throw new SesWebRosException(ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getMessage());
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
            throw new SesWebRosException(ExceptionCodeEnums.SPECIFICAT_ID_NOT_EMPTY.getCode(), ExceptionCodeEnums.SPECIFICAT_ID_NOT_EMPTY.getMessage());
        }
        OpeSpecificatType specificatType = opeSpecificatTypeMapper.selectById(specificatId);
        if (null != specificatType) {
            String name = specificatType.getSpecificatName();
            if (StringUtils.isNotBlank(name)) {
                return name;
            }
        }
        throw new SesWebRosException(ExceptionCodeEnums.SPECIFICAT_TYPE_NOT_EXIST.getCode(), ExceptionCodeEnums.SPECIFICAT_TYPE_NOT_EXIST.getMessage());
    }

}
