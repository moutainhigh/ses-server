package com.redescooter.ses.mobile.wh.fr.service.app.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Maps;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.enums.date.DayCodeEnum;
import com.redescooter.ses.api.common.enums.date.MonthCodeEnum;
import com.redescooter.ses.api.common.vo.base.BooleanResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
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
import com.redescooter.ses.api.foundation.service.base.AccountBaseService;
import com.redescooter.ses.api.foundation.service.scooter.AssignScooterService;
import com.redescooter.ses.api.foundation.vo.user.UserToken;
import com.redescooter.ses.api.hub.service.admin.ScooterModelService;
import com.redescooter.ses.api.hub.service.customer.CusotmerScooterService;
import com.redescooter.ses.api.hub.service.operation.ColorService;
import com.redescooter.ses.api.hub.service.operation.SpecificService;
import com.redescooter.ses.api.scooter.service.ScooterEmqXService;
import com.redescooter.ses.api.scooter.service.ScooterNodeService;
import com.redescooter.ses.api.scooter.service.ScooterService;
import com.redescooter.ses.mobile.wh.fr.dao.base.OpeCustomerInquiryMapper;
import com.redescooter.ses.mobile.wh.fr.dao.base.OpeSaleScooterMapper;
import com.redescooter.ses.mobile.wh.fr.dao.base.OpeSpecificatTypeMapper;
import com.redescooter.ses.mobile.wh.fr.dao.base.OpeWmsScooterStockMapper;
import com.redescooter.ses.mobile.wh.fr.dm.OpeColor;
import com.redescooter.ses.mobile.wh.fr.dm.OpeCustomerInquiry;
import com.redescooter.ses.mobile.wh.fr.dm.OpeInWhouseScooterB;
import com.redescooter.ses.mobile.wh.fr.dm.OpeSaleScooter;
import com.redescooter.ses.mobile.wh.fr.dm.OpeSpecificatGroup;
import com.redescooter.ses.mobile.wh.fr.dm.OpeSpecificatType;
import com.redescooter.ses.mobile.wh.fr.dm.OpeWarehouseAccount;
import com.redescooter.ses.mobile.wh.fr.dm.OpeWmsScooterStock;
import com.redescooter.ses.mobile.wh.fr.dm.OpeWmsStockSerialNumber;
import com.redescooter.ses.mobile.wh.fr.enums.StatusEnum;
import com.redescooter.ses.mobile.wh.fr.exception.ExceptionCodeEnums;
import com.redescooter.ses.mobile.wh.fr.exception.SesMobileFrWhException;
import com.redescooter.ses.mobile.wh.fr.service.app.FrAppService;
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
        return scooterNodeService.getList(enter);
    }

    /**
     * 详情
     */
    @Override
    public InquiryDetailResult getDetail(IdEnter enter) {
        return scooterNodeService.getDetail(enter);
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
        return scooterNodeService.inputScooter(enter);
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
        return scooterNodeService.setScooterModel(enter);
    }

    /**
     * 绑定VIN
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult bindVin(BindVinEnter enter) {
        return scooterNodeService.bindVin(enter);
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
