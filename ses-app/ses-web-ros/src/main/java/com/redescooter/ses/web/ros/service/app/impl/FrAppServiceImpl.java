package com.redescooter.ses.web.ros.service.app.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.enums.customer.CustomerStatusEnum;
import com.redescooter.ses.api.common.enums.scooter.ScooterModelEnums;
import com.redescooter.ses.api.common.enums.scooter.ScooterStatusEnums;
import com.redescooter.ses.api.common.enums.wms.WmsStockStatusEnum;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.TokenResult;
import com.redescooter.ses.api.foundation.service.base.AccountBaseService;
import com.redescooter.ses.api.foundation.vo.tenant.QueryAccountResult;
import com.redescooter.ses.api.foundation.vo.user.UserToken;
import com.redescooter.ses.api.hub.service.customer.CusotmerScooterService;
import com.redescooter.ses.api.hub.vo.HubSaveScooterEnter;
import com.redescooter.ses.api.scooter.service.ScooterService;
import com.redescooter.ses.api.scooter.vo.ScoScooterResult;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.starter.redis.enums.RedisExpireEnum;
import com.redescooter.ses.tool.utils.map.MapUtil;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.dao.assign.OpeCarDistributeExMapper;
import com.redescooter.ses.web.ros.dao.assign.OpeCarDistributeMapper;
import com.redescooter.ses.web.ros.dao.assign.OpeCarDistributeNodeMapper;
import com.redescooter.ses.web.ros.dao.base.OpeCustomerMapper;
import com.redescooter.ses.web.ros.dao.base.OpeWmsScooterStockMapper;
import com.redescooter.ses.web.ros.dao.base.OpeWmsStockRecordMapper;
import com.redescooter.ses.web.ros.dao.wms.cn.china.OpeWmsStockSerialNumberMapper;
import com.redescooter.ses.web.ros.dm.OpeCarDistribute;
import com.redescooter.ses.web.ros.dm.OpeCarDistributeNode;
import com.redescooter.ses.web.ros.dm.OpeCustomer;
import com.redescooter.ses.web.ros.dm.OpeCustomerInquiryB;
import com.redescooter.ses.web.ros.dm.OpeWarehouseAccount;
import com.redescooter.ses.web.ros.dm.OpeWmsScooterStock;
import com.redescooter.ses.web.ros.dm.OpeWmsStockRecord;
import com.redescooter.ses.web.ros.dm.OpeWmsStockSerialNumber;
import com.redescooter.ses.web.ros.enums.assign.ProductTypeEnum;
import com.redescooter.ses.web.ros.enums.distributor.StatusEnum;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.app.FrAppService;
import com.redescooter.ses.web.ros.service.assign.impl.ToBeAssignServiceImpl;
import com.redescooter.ses.web.ros.service.base.OpeCustomerInquiryBService;
import com.redescooter.ses.web.ros.service.base.OpeCustomerInquiryService;
import com.redescooter.ses.web.ros.service.base.OpeWarehouseAccountService;
import com.redescooter.ses.web.ros.service.base.OpeWmsStockSerialNumberService;
import com.redescooter.ses.web.ros.vo.app.AppLoginEnter;
import com.redescooter.ses.web.ros.vo.app.BindVinEnter;
import com.redescooter.ses.web.ros.vo.app.InputBatteryEnter;
import com.redescooter.ses.web.ros.vo.app.InputScooterEnter;
import com.redescooter.ses.web.ros.vo.app.InquiryDetailEnter;
import com.redescooter.ses.web.ros.vo.app.InquiryDetailResult;
import com.redescooter.ses.web.ros.vo.app.InquiryListAppEnter;
import com.redescooter.ses.web.ros.vo.app.InquiryListResult;
import com.redescooter.ses.web.ros.vo.assign.tobe.enter.CustomerIdEnter;
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
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
    private OpeWmsStockSerialNumberMapper opeWmsStockSerialNumberMapper;

    @Autowired
    private OpeWmsStockSerialNumberService opeWmsStockSerialNumberService;

    @Autowired
    private OpeCustomerInquiryService opeCustomerInquiryService;

    @Autowired
    private OpeCustomerInquiryBService opeCustomerInquiryBService;

    @Autowired
    private ToBeAssignServiceImpl toBeAssignService;

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
     * 获得个人信息
     */
    @Override
    public OpeWarehouseAccount getUserInfo(GeneralEnter enter) {
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
        int count = opeCarDistributeExMapper.getInquiryListCount(enter);
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        List<InquiryListResult> list = opeCarDistributeExMapper.getInquiryList(enter);
        if (CollectionUtils.isNotEmpty(list)) {
            for (InquiryListResult item : list) {
                if (item.getFlag() == 0 && item.getAppNode() == 0) {
                    item.setStatus(1);
                }
                if (item.getFlag() == 1 && (item.getAppNode() == 1 || item.getAppNode() == 2 || item.getAppNode() == 3)) {
                    item.setStatus(2);
                }
                if (item.getFlag() == 2) {
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
        return PageResult.create(enter, count, list);
    }

    /**
     * 询价单详情
     */
    @Override
    public InquiryDetailResult getDetail(InquiryDetailEnter enter) {
        Long userId = getUserId(enter);
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

        // 先查看客户在node表是否存在数据,不存在就新建
        LambdaQueryWrapper<OpeCarDistributeNode> qw = new LambdaQueryWrapper<>();
        qw.eq(OpeCarDistributeNode::getDr, Constant.DR_FALSE);
        qw.eq(OpeCarDistributeNode::getCustomerId, enter.getCustomerId());
        List<OpeCarDistributeNode> nodeList = opeCarDistributeNodeMapper.selectList(qw);
        if (CollectionUtils.isEmpty(nodeList)) {
            // 新增node表
            OpeCarDistributeNode node = new OpeCarDistributeNode();
            node.setId(idAppService.getId(SequenceName.OPE_CAR_DISTRIBUTE_NODE));
            node.setDr(Constant.DR_FALSE);
            node.setCustomerId(enter.getCustomerId());
            node.setAppNode(0);
            node.setFlag(0);
            node.setCreatedBy(userId);
            node.setCreatedTime(new Date());
            opeCarDistributeNodeMapper.insert(node);

            // 新增主表
            OpeCarDistribute distribute = new OpeCarDistribute();
            distribute.setId(idAppService.getId(SequenceName.OPE_CAR_DISTRIBUTE));
            distribute.setDr(Constant.DR_FALSE);
            distribute.setCustomerId(enter.getCustomerId());
            distribute.setQty(1);
            distribute.setCreatedBy(userId);
            distribute.setCreatedTime(new Date());
            opeCarDistributeMapper.insert(distribute);
        }
        return result;
    }

    /**
     * 录入车辆
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult inputScooter(InputScooterEnter enter) {
        Long userId = getUserId(enter);
        Long customerId = enter.getCustomerId();
        String rsn = enter.getRsn();
        String tabletSn = enter.getTabletSn();
        String bluetoothAddress = enter.getBluetoothAddress();

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

        // 修改主表
        OpeCarDistribute distribute = new OpeCarDistribute();
        distribute.setWarehouseAccountId(userId);
        distribute.setSpecificatTypeId(enter.getSpecificatTypeId());
        distribute.setSeatNumber(enter.getSeatNumber());
        distribute.setRsn(rsn);
        distribute.setBluetoothAddress(bluetoothAddress);
        distribute.setTabletSn(tabletSn);
        distribute.setColorId(enter.getColorId());
        // 条件
        LambdaQueryWrapper<OpeCarDistribute> qw = new LambdaQueryWrapper<>();
        qw.eq(OpeCarDistribute::getDr, Constant.DR_FALSE);
        qw.eq(OpeCarDistribute::getCustomerId, customerId);
        opeCarDistributeMapper.update(distribute, qw);

        // node表appNode字段
        OpeCarDistributeNode node = new OpeCarDistributeNode();
        node.setAppNode(1);
        node.setFlag(1);
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
     * 录入电池
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult inputBattery(InputBatteryEnter enter) {
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
                distribute.setBattery(enter.getBattery());
                LambdaQueryWrapper<OpeCarDistribute> qw = new LambdaQueryWrapper<>();
                qw.eq(OpeCarDistribute::getDr, Constant.DR_FALSE);
                qw.eq(OpeCarDistribute::getCustomerId, enter.getCustomerId());
                opeCarDistributeMapper.update(distribute, qw);
            } else {
                // 之前已经扫描过电池,在电池后追加
                OpeCarDistribute distribute = new OpeCarDistribute();
                distribute.setBattery(modelBattery + "," + enter.getBattery());
                LambdaQueryWrapper<OpeCarDistribute> qw = new LambdaQueryWrapper<>();
                qw.eq(OpeCarDistribute::getDr, Constant.DR_FALSE);
                qw.eq(OpeCarDistribute::getCustomerId, enter.getCustomerId());
                opeCarDistributeMapper.update(distribute, qw);
            }
        }

        // 查询最新的已经扫描过的电池数量
        OpeCarDistribute distribute = opeCarDistributeMapper.selectOne(queryWrapper);
        String[] split = distribute.getBattery().split(",");
        List<String> batteryList = new ArrayList<>(Arrays.asList(split));
        // 如果已经扫描过的电池数量=询价单的电池数量
        if (CollectionUtils.isNotEmpty(batteryList)) {
            if (batteryNum.equals(batteryList.size())) {
                // node表appNode字段
                OpeCarDistributeNode node = new OpeCarDistributeNode();
                node.setAppNode(2);
                node.setUpdatedBy(userId);
                node.setUpdatedTime(new Date());
                // 条件
                LambdaQueryWrapper<OpeCarDistributeNode> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(OpeCarDistributeNode::getDr, Constant.DR_FALSE);
                wrapper.eq(OpeCarDistributeNode::getCustomerId, enter.getCustomerId());
                opeCarDistributeNodeMapper.update(node, wrapper);
            }
        }
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 绑定VIN
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult bindVin(BindVinEnter enter) {
        Long userId = getUserId(enter);
        String vinCode = enter.getVinCode();

        LambdaQueryWrapper<OpeCarDistribute> checkWrapper = new LambdaQueryWrapper<>();
        checkWrapper.eq(OpeCarDistribute::getDr, Constant.DR_FALSE);
        checkWrapper.eq(OpeCarDistribute::getVinCode, vinCode);
        checkWrapper.last("limit 1");
        OpeCarDistribute checkModel = opeCarDistributeMapper.selectOne(checkWrapper);
        if (null != checkModel) {
            throw new SesWebRosException(ExceptionCodeEnums.VIN_HAS_INPUT.getCode(), ExceptionCodeEnums.VIN_HAS_INPUT.getMessage());
        }

        String productType = ProductTypeEnum.showCode(enter.getScooterName());
        // 截取第7位,车型编号
        String productTypeSub = vinCode.substring(0, 8);
        if (!StringUtils.equals(productType, productTypeSub)) {
            throw new SesWebRosException(ExceptionCodeEnums.VIN_NOT_MATCH.getCode(), ExceptionCodeEnums.VIN_NOT_MATCH.getMessage());
        }

        Integer seatNumber = enter.getSeatNumber();
        // 截取第8位,座位数量
        String seatNumberSub = vinCode.substring(0, 9);
        if (!StringUtils.equals(String.valueOf(seatNumber), seatNumberSub)) {
            throw new SesWebRosException(ExceptionCodeEnums.VIN_NOT_MATCH.getCode(), ExceptionCodeEnums.VIN_NOT_MATCH.getMessage());
        }

        // 修改主表
        OpeCarDistribute distribute = new OpeCarDistribute();
        distribute.setVinCode(vinCode);
        // 条件
        LambdaQueryWrapper<OpeCarDistribute> qw = new LambdaQueryWrapper<>();
        qw.eq(OpeCarDistribute::getDr, Constant.DR_FALSE);
        qw.eq(OpeCarDistribute::getCustomerId, enter.getCustomerId());
        opeCarDistributeMapper.update(distribute, qw);

        // node表appNode字段
        OpeCarDistributeNode node = new OpeCarDistributeNode();
        node.setAppNode(3);
        node.setUpdatedBy(userId);
        node.setUpdatedTime(new Date());
        // 条件
        LambdaQueryWrapper<OpeCarDistributeNode> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OpeCarDistributeNode::getDr, Constant.DR_FALSE);
        wrapper.eq(OpeCarDistributeNode::getCustomerId, enter.getCustomerId());
        opeCarDistributeNodeMapper.update(node, wrapper);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 设置软体
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult setScooterModel(CustomerIdEnter enter) {
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

        // 修改成品库车辆库存
        // 获得询价单型号id和颜色id
        CustomerIdEnter customerIdEnter = new CustomerIdEnter();
        customerIdEnter.setCustomerId(enter.getCustomerId());
        Map<String, Long> map = toBeAssignService.getSpecificatIdAndColorId(customerIdEnter);
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
        if (CollectionUtils.isNotEmpty(stockList)) {
            stock = stockList.get(0);
            // 得到原先库存的可用库存数量和已用库存数量
            Long stockId = stock.getId();
            Integer ableStockQty = stock.getAbleStockQty();
            Integer usedStockQty = stock.getUsedStockQty();

            // 原先库存的可用库存数量-1,已用库存数量+1
            OpeWmsScooterStock param = new OpeWmsScooterStock();
            param.setId(stockId);
            param.setAbleStockQty(ableStockQty - 1);
            param.setUsedStockQty(usedStockQty + 1);
            param.setUpdatedBy(userId);
            param.setUpdatedTime(new Date());
            opeWmsScooterStockMapper.updateById(param);
        }

        // 新增出库记录
        if (null != stock) {
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
        String specificatName = toBeAssignService.getSpecificatNameById(model.getSpecificatTypeId());

        // 数据同步
        // 根据rsn查询sco_scooter表
        ScoScooterResult scoScooter = scooterService.getScoScooterByTableSn(model.getRsn());
        if (null == scoScooter) {
            throw new SesWebRosException(ExceptionCodeEnums.SCOOTER_NOT_EXIST.getCode(), ExceptionCodeEnums.SCOOTER_NOT_EXIST.getMessage());
        }
        Long scooterId = scoScooter.getId();
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
        List<OpeWmsStockSerialNumber> serialNumberList = opeWmsStockSerialNumberMapper.selectList(qw);
        if (CollectionUtils.isNotEmpty(serialNumberList)) {
            for (OpeWmsStockSerialNumber serialNumber : serialNumberList) {
                if (null != serialNumber) {
                    serialNumber.setStockStatus(WmsStockStatusEnum.UNAVAILABLE.getStatus());
                    opeWmsStockSerialNumberService.updateById(serialNumber);
                }
            }
        }

        // node表appNode字段
        OpeCarDistributeNode node = new OpeCarDistributeNode();
        node.setAppNode(4);
        node.setFlag(2);
        node.setUpdatedBy(userId);
        node.setUpdatedTime(new Date());
        // 条件
        LambdaQueryWrapper<OpeCarDistributeNode> lqw = new LambdaQueryWrapper<>();
        lqw.eq(OpeCarDistributeNode::getDr, Constant.DR_FALSE);
        lqw.eq(OpeCarDistributeNode::getCustomerId, enter.getCustomerId());
        opeCarDistributeNodeMapper.update(node, lqw);
        return new GeneralResult(enter.getRequestId());
    }

}
