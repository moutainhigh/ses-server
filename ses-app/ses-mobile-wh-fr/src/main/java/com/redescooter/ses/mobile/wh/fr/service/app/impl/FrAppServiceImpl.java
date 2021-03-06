package com.redescooter.ses.mobile.wh.fr.service.app.impl;

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
import com.redescooter.ses.api.common.vo.base.StringEnter;
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
import com.redescooter.ses.mobile.wh.fr.constant.SequenceName;
import com.redescooter.ses.mobile.wh.fr.dao.base.OpeCarDistributeExMapper;
import com.redescooter.ses.mobile.wh.fr.dao.base.OpeCarDistributeMapper;
import com.redescooter.ses.mobile.wh.fr.dao.base.OpeCarDistributeNodeMapper;
import com.redescooter.ses.mobile.wh.fr.dao.base.OpeCustomerInquiryMapper;
import com.redescooter.ses.mobile.wh.fr.dao.base.OpeCustomerMapper;
import com.redescooter.ses.mobile.wh.fr.dao.base.OpeSaleScooterMapper;
import com.redescooter.ses.mobile.wh.fr.dao.base.OpeSpecificatTypeMapper;
import com.redescooter.ses.mobile.wh.fr.dao.base.OpeWmsScooterStockMapper;
import com.redescooter.ses.mobile.wh.fr.dao.base.OpeWmsStockRecordMapper;
import com.redescooter.ses.mobile.wh.fr.dm.OpeCarDistribute;
import com.redescooter.ses.mobile.wh.fr.dm.OpeCarDistributeNode;
import com.redescooter.ses.mobile.wh.fr.dm.OpeCodebaseRelation;
import com.redescooter.ses.mobile.wh.fr.dm.OpeCodebaseRsn;
import com.redescooter.ses.mobile.wh.fr.dm.OpeCodebaseVin;
import com.redescooter.ses.mobile.wh.fr.dm.OpeColor;
import com.redescooter.ses.mobile.wh.fr.dm.OpeCustomer;
import com.redescooter.ses.mobile.wh.fr.dm.OpeCustomerInquiry;
import com.redescooter.ses.mobile.wh.fr.dm.OpeCustomerInquiryB;
import com.redescooter.ses.mobile.wh.fr.dm.OpeSaleScooter;
import com.redescooter.ses.mobile.wh.fr.dm.OpeSimInformation;
import com.redescooter.ses.mobile.wh.fr.dm.OpeSpecificatGroup;
import com.redescooter.ses.mobile.wh.fr.dm.OpeSpecificatType;
import com.redescooter.ses.mobile.wh.fr.dm.OpeWarehouseAccount;
import com.redescooter.ses.mobile.wh.fr.dm.OpeWmsScooterStock;
import com.redescooter.ses.mobile.wh.fr.dm.OpeWmsStockRecord;
import com.redescooter.ses.mobile.wh.fr.dm.OpeWmsStockSerialNumber;
import com.redescooter.ses.mobile.wh.fr.enums.StatusEnum;
import com.redescooter.ses.mobile.wh.fr.exception.ExceptionCodeEnums;
import com.redescooter.ses.mobile.wh.fr.exception.SesMobileFrWhException;
import com.redescooter.ses.mobile.wh.fr.service.app.FrAppService;
import com.redescooter.ses.mobile.wh.fr.service.base.OpeSaleScooterService;
import com.redescooter.ses.mobile.wh.fr.service.base.OpeSimInformationService;
import com.redescooter.ses.mobile.wh.fr.service.base.OpeSpecificatGroupService;
import com.redescooter.ses.mobile.wh.fr.service.base.OpeWarehouseAccountService;
import com.redescooter.ses.mobile.wh.fr.service.base.OpeWmsStockSerialNumberService;
import com.redescooter.ses.mobile.wh.fr.vo.AppLoginEnter;
import com.redescooter.ses.mobile.wh.fr.vo.BindLicensePlateEnter;
import com.redescooter.ses.mobile.wh.fr.vo.BindVinEnter;
import com.redescooter.ses.mobile.wh.fr.vo.CustomerIdEnter;
import com.redescooter.ses.mobile.wh.fr.vo.InputBatteryEnter;
import com.redescooter.ses.mobile.wh.fr.vo.InputScooterEnter;
import com.redescooter.ses.mobile.wh.fr.vo.InquiryDetailEnter;
import com.redescooter.ses.mobile.wh.fr.vo.InquiryDetailResult;
import com.redescooter.ses.mobile.wh.fr.vo.InquiryListAppEnter;
import com.redescooter.ses.mobile.wh.fr.vo.InquiryListResult;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.starter.redis.enums.RedisExpireEnum;
import com.redescooter.ses.tool.utils.map.MapUtil;
import com.redescooter.ses.mobile.wh.fr.service.base.OpeCodebaseRelationService;
import com.redescooter.ses.mobile.wh.fr.service.base.OpeCodebaseRsnService;
import com.redescooter.ses.mobile.wh.fr.service.base.OpeCodebaseVinService;
import com.redescooter.ses.mobile.wh.fr.service.base.OpeColorService;
import com.redescooter.ses.mobile.wh.fr.service.base.OpeCustomerInquiryBService;
import com.redescooter.ses.mobile.wh.fr.service.base.OpeCustomerInquiryService;
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
     * ??????
     */
    @Override
    public TokenResult login(AppLoginEnter enter) {
        // ????????????
        /*String decryptEmail;
        try {
            decryptEmail = RsaUtils.decrypt(enter.getEmail(), privateKey);
        } catch (Exception ex) {
            throw new SesMobileFrWhException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }
        // ????????????
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
        // ??????????????????????????????md5??????,???db?????????md5????????????????????????
        String encryptPassword = DigestUtils.md5Hex(decryptPassword + account.getSalt());
        if (!StringUtils.equals(encryptPassword, account.getPassword())) {
            throw new SesMobileFrWhException(ExceptionCodeEnums.PASSROD_WRONG.getCode(), ExceptionCodeEnums.PASSROD_WRONG.getMessage());
        }

        String lastLoginToken = account.getLastLoginToken();
        if (StringUtils.isNotBlank(lastLoginToken)) {
            // ?????????????????????
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
            log.error("??????token??????", ex);
        }

        // ??????db,??????lastLoginToken
        account.setLastLoginToken(token);
        opeWarehouseAccountService.updateById(account);

        TokenResult result = new TokenResult();
        result.setToken(token);
        result.setRequestId(enter.getRequestId());
        return result;
    }

    /**
     * ??????
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
     * ??????????????????
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
     * ????????????????????????
     */
    @Override
    public List<String> getDataList(StringEnter enter) {
        if (null == enter || StringUtils.isBlank(enter.getKeyword())) {
            return Collections.EMPTY_LIST;
        }
        // ??????????????????
        List<String> resultList = Lists.newArrayList();

        List<String> nameList = opeCarDistributeExMapper.getNameDataList(enter, enter.getUserId());
        if (CollectionUtils.isNotEmpty(nameList)) {
            for (String name : nameList) {
                resultList.add(name);
                if (CollectionUtils.isNotEmpty(resultList) && resultList.size() == 10) {
                    break;
                }
            }
        }

        List<String> orderNoList = opeCarDistributeExMapper.getOrderNoDataList(enter, enter.getUserId());
        if (CollectionUtils.isNotEmpty(orderNoList)) {
            for (String orderNo : orderNoList) {
                resultList.add(orderNo);
                if (CollectionUtils.isNotEmpty(resultList) && resultList.size() == 10) {
                    break;
                }
            }
        }
        return resultList;
    }

    /**
     * ???????????????
     */
    @Override
    public PageResult<InquiryListResult> getList(InquiryListAppEnter enter) {
        int count = opeCarDistributeExMapper.getInquiryListCount(enter, enter.getUserId());
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        if (StringUtils.isBlank(enter.getKeyword()) && null == enter.getStatus()) {
            return PageResult.createZeroRowResult(enter);
        }
        List<InquiryListResult> list = opeCarDistributeExMapper.getInquiryList(enter, enter.getUserId());
        if (CollectionUtils.isNotEmpty(list)) {
            for (InquiryListResult item : list) {

                Integer flag = item.getFlag();
                Integer appNode = item.getAppNode();
                Integer webNode = item.getWebNode();

                // ?????????????????????sql??????????????????
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

                // ????????? ????????????????????????,????????????????????????
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

        // ?????????????????????????????????????????????????????????????????????
        if (CollectionUtils.isNotEmpty(list)) {
            Iterator<InquiryListResult> iterator = list.iterator();
            while (iterator.hasNext()) {
                InquiryListResult next = iterator.next();
                String email = next.getEmail();
                // ??????email???pla_user?????????,????????????,????????????????????? flag:????????????true,???????????????false
                Boolean flag = assignScooterService.getPlaUserIsExistByEmail(email);
                if (!flag) {
                    iterator.remove();
                }
            }
        }
        return PageResult.create(enter, list.size(), list);
    }

    /**
     * ???????????????
     */
    @Override
    public InquiryDetailResult getDetail(InquiryDetailEnter enter) {
        // ??????????????????????????????????????????????????????
        LambdaQueryWrapper<OpeCarDistribute> lqw = new LambdaQueryWrapper<>();
        lqw.eq(OpeCarDistribute::getDr, Constant.DR_FALSE);
        lqw.eq(OpeCarDistribute::getCustomerId, enter.getCustomerId());
        lqw.last("limit 1");
        OpeCarDistribute instance = opeCarDistributeMapper.selectOne(lqw);
        if (null != instance) {
            Long warehouseAccountId = instance.getWarehouseAccountId();
            if (null != warehouseAccountId && !enter.getUserId().equals(warehouseAccountId)) {
                throw new SesMobileFrWhException(ExceptionCodeEnums.ORDER_HAS_DISTRIBUTED.getCode(), ExceptionCodeEnums.ORDER_HAS_DISTRIBUTED.getMessage());
            }
        }

        // ??????????????????node?????????????????????,??????????????????
        LambdaQueryWrapper<OpeCarDistributeNode> qw = new LambdaQueryWrapper<>();
        qw.eq(OpeCarDistributeNode::getDr, Constant.DR_FALSE);
        qw.eq(OpeCarDistributeNode::getCustomerId, enter.getCustomerId());
        List<OpeCarDistributeNode> nodeList = opeCarDistributeNodeMapper.selectList(qw);
        if (CollectionUtils.isEmpty(nodeList)) {

            OpeCustomerInquiry inquiry = opeCustomerInquiryService.getById(enter.getId());
            if (null == inquiry) {
                throw new SesMobileFrWhException(ExceptionCodeEnums.INQUIRY_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.INQUIRY_IS_NOT_EXIST.getMessage());
            }
            Long productId = inquiry.getProductId();
            OpeSaleScooter saleScooter = opeSaleScooterService.getById(productId);
            if (null == saleScooter) {
                throw new SesMobileFrWhException(ExceptionCodeEnums.SCOOTER_NOT_EXIST.getCode(), ExceptionCodeEnums.SCOOTER_NOT_EXIST.getMessage());
            }
            // ???????????????id??????????????????id?????????id
            Long specificatId = saleScooter.getSpecificatId();
            Long colorId = saleScooter.getColorId();

            // ??????node???
            OpeCarDistributeNode node = new OpeCarDistributeNode();
            node.setId(idAppService.getId(SequenceName.OPE_CAR_DISTRIBUTE_NODE));
            node.setDr(Constant.DR_FALSE);
            node.setCustomerId(enter.getCustomerId());
            node.setNode(1);
            node.setAppNode(1);
            node.setFlag(0);
            node.setCreatedBy(enter.getUserId() == null ? 0L : enter.getUserId());
            node.setCreatedTime(new Date());
            opeCarDistributeNodeMapper.insert(node);

            // ????????????
            OpeCarDistribute distribute = new OpeCarDistribute();
            distribute.setId(idAppService.getId(SequenceName.OPE_CAR_DISTRIBUTE));
            distribute.setDr(Constant.DR_FALSE);
            distribute.setCustomerId(enter.getCustomerId());
            distribute.setSpecificatTypeId(specificatId);
            distribute.setColorId(colorId);
            distribute.setSeatNumber(2);
            distribute.setQty(1);
            distribute.setCreatedBy(enter.getUserId() == null ? 0L : enter.getUserId());
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
     * ??????VIN
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult bindVin(BindVinEnter enter) {
        String vinCode = enter.getVinCode().trim();
        Integer seatNumber = enter.getSeatNumber();

        // ??????????????????????????????????????????????????????
        LambdaQueryWrapper<OpeCarDistribute> lqw = new LambdaQueryWrapper<>();
        lqw.eq(OpeCarDistribute::getDr, Constant.DR_FALSE);
        lqw.eq(OpeCarDistribute::getCustomerId, enter.getCustomerId());
        lqw.last("limit 1");
        OpeCarDistribute instance = opeCarDistributeMapper.selectOne(lqw);
        if (null != instance) {
            Long warehouseAccountId = instance.getWarehouseAccountId();
            String vin = instance.getVinCode();
            if (null != warehouseAccountId && !enter.getUserId().equals(warehouseAccountId)) {
                throw new SesMobileFrWhException(ExceptionCodeEnums.ORDER_HAS_DISTRIBUTED.getCode(), ExceptionCodeEnums.ORDER_HAS_DISTRIBUTED.getMessage());
            }
            if (StringUtils.isNotBlank(vin)) {
                throw new SesMobileFrWhException(ExceptionCodeEnums.STEP_HAS_INPUT_IN_ROS.getCode(), ExceptionCodeEnums.STEP_HAS_INPUT_IN_ROS.getMessage());
            }
        }

        if (vinCode.length() != 17) {
            throw new SesMobileFrWhException(ExceptionCodeEnums.VIN_NOT_MATCH.getCode(), ExceptionCodeEnums.VIN_NOT_MATCH.getMessage());
        }
        if (!vinCode.startsWith("VXSR2A")) {
            throw new SesMobileFrWhException(ExceptionCodeEnums.VIN_NOT_MATCH.getCode(), ExceptionCodeEnums.VIN_NOT_MATCH.getMessage());
        }

        String productType = ProductTypeEnum.showCode(enter.getScooterName());
        // ?????????7???,????????????
        String productTypeSub = vinCode.substring(6, 7);
        if (!StringUtils.equals(productType, productTypeSub)) {
            throw new SesMobileFrWhException(ExceptionCodeEnums.VIN_NOT_MATCH.getCode(), ExceptionCodeEnums.VIN_NOT_MATCH.getMessage());
        }

        // ?????????8???,????????????
        String seatNumberSub = vinCode.substring(7, 8);
        if (!StringUtils.equals(String.valueOf(seatNumber), seatNumberSub)) {
            throw new SesMobileFrWhException(ExceptionCodeEnums.VIN_NOT_MATCH.getCode(), ExceptionCodeEnums.VIN_NOT_MATCH.getMessage());
        }

        LambdaQueryWrapper<OpeCarDistribute> checkWrapper = new LambdaQueryWrapper<>();
        checkWrapper.eq(OpeCarDistribute::getDr, Constant.DR_FALSE);
        checkWrapper.eq(OpeCarDistribute::getVinCode, vinCode);
        checkWrapper.last("limit 1");
        OpeCarDistribute checkModel = opeCarDistributeMapper.selectOne(checkWrapper);
        if (null != checkModel) {
            throw new SesMobileFrWhException(ExceptionCodeEnums.VIN_HAS_INPUT.getCode(), ExceptionCodeEnums.VIN_HAS_INPUT.getMessage());
        }

        // ??????vin????????????????????????
        LambdaQueryWrapper<OpeCodebaseVin> existWrapper = new LambdaQueryWrapper<>();
        existWrapper.eq(OpeCodebaseVin::getDr, Constant.DR_FALSE);
        existWrapper.eq(OpeCodebaseVin::getStatus, 1);
        existWrapper.eq(OpeCodebaseVin::getVin, vinCode);
        int count = opeCodebaseVinService.count(existWrapper);
        if (count == 0) {
            throw new SesMobileFrWhException(ExceptionCodeEnums.VIN_NOT_EXISTS_CODEBASE.getCode(), ExceptionCodeEnums.VIN_NOT_EXISTS_CODEBASE.getMessage());
        }

        // ????????????
        OpeCarDistribute distribute = new OpeCarDistribute();
        distribute.setVinCode(vinCode);
        distribute.setWarehouseAccountId(enter.getUserId());
        distribute.setUpdatedBy(enter.getUserId());
        distribute.setUpdatedTime(new Date());
        // ??????
        LambdaQueryWrapper<OpeCarDistribute> qw = new LambdaQueryWrapper<>();
        qw.eq(OpeCarDistribute::getDr, Constant.DR_FALSE);
        qw.eq(OpeCarDistribute::getCustomerId, enter.getCustomerId());
        opeCarDistributeMapper.update(distribute, qw);

        // node???appNode??????
        OpeCarDistributeNode node = new OpeCarDistributeNode();
        node.setNode(2);
        node.setAppNode(2);
        node.setFlag(1);
        node.setUpdatedBy(enter.getUserId());
        node.setUpdatedTime(new Date());
        // ??????
        LambdaQueryWrapper<OpeCarDistributeNode> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OpeCarDistributeNode::getDr, Constant.DR_FALSE);
        wrapper.eq(OpeCarDistributeNode::getCustomerId, enter.getCustomerId());
        opeCarDistributeNodeMapper.update(node, wrapper);

        // ???????????????vin?????????
        LambdaQueryWrapper<OpeCodebaseVin> vinWrapper = new LambdaQueryWrapper<>();
        vinWrapper.eq(OpeCodebaseVin::getDr, Constant.DR_FALSE);
        vinWrapper.eq(OpeCodebaseVin::getStatus, 1);
        vinWrapper.eq(OpeCodebaseVin::getVin, vinCode);
        vinWrapper.last("limit 1");
        OpeCodebaseVin codebaseVin = opeCodebaseVinService.getOne(vinWrapper);
        if (null != codebaseVin) {
            codebaseVin.setStatus(2);
            codebaseVin.setUpdatedBy(enter.getUserId());
            codebaseVin.setUpdatedTime(new Date());
            opeCodebaseVinService.updateById(codebaseVin);
        }

        // ???????????????,rsn???vin??????,??????vin,rsn?????????????????????
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
            relation.setCreatedBy(enter.getUserId());
            relation.setCreatedTime(new Date());
            opeCodebaseRelationService.save(relation);
        }
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * ????????????
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult bindLicensePlate(BindLicensePlateEnter enter) {
        String licensePlate = enter.getLicensePlate().trim();
        Long customerId = enter.getCustomerId();

        // ???????????????????????????
        LambdaQueryWrapper<OpeCarDistribute> lqw = new LambdaQueryWrapper<>();
        lqw.eq(OpeCarDistribute::getDr, Constant.DR_FALSE);
        lqw.eq(OpeCarDistribute::getCustomerId, enter.getCustomerId());
        lqw.last("limit 1");
        OpeCarDistribute instance = opeCarDistributeMapper.selectOne(lqw);
        if (null != instance) {
            String plate = instance.getLicensePlate();
            if (StringUtils.isNotBlank(plate)) {
                throw new SesMobileFrWhException(ExceptionCodeEnums.STEP_HAS_INPUT_IN_ROS.getCode(), ExceptionCodeEnums.STEP_HAS_INPUT_IN_ROS.getMessage());
            }
        }

        LambdaQueryWrapper<OpeCarDistribute> checkWrapper = new LambdaQueryWrapper<>();
        checkWrapper.eq(OpeCarDistribute::getDr, Constant.DR_FALSE);
        checkWrapper.eq(OpeCarDistribute::getLicensePlate, licensePlate);
        checkWrapper.last("limit 1");
        OpeCarDistribute checkModel = opeCarDistributeMapper.selectOne(checkWrapper);
        if (null != checkModel) {
            throw new SesMobileFrWhException(ExceptionCodeEnums.PLATE_HAS_USED.getCode(), ExceptionCodeEnums.PLATE_HAS_USED.getMessage());
        }

        // ????????????
        OpeCarDistribute distribute = new OpeCarDistribute();
        distribute.setLicensePlate(licensePlate);
        distribute.setUpdatedBy(enter.getUserId());
        distribute.setUpdatedTime(new Date());
        // ??????
        LambdaQueryWrapper<OpeCarDistribute> qw = new LambdaQueryWrapper<>();
        qw.eq(OpeCarDistribute::getDr, Constant.DR_FALSE);
        qw.eq(OpeCarDistribute::getCustomerId, customerId);
        opeCarDistributeMapper.update(distribute, qw);

        // node???appNode??????
        OpeCarDistributeNode node = new OpeCarDistributeNode();
        node.setNode(3);
        node.setAppNode(3);
        node.setUpdatedBy(enter.getUserId());
        node.setUpdatedTime(new Date());
        // ??????
        LambdaQueryWrapper<OpeCarDistributeNode> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OpeCarDistributeNode::getDr, Constant.DR_FALSE);
        wrapper.eq(OpeCarDistributeNode::getCustomerId, customerId);
        opeCarDistributeNodeMapper.update(node, wrapper);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * ????????????
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult inputScooter(InputScooterEnter enter) {
        Long customerId = enter.getCustomerId();
        String rsn = enter.getRsn().trim();
        String tabletSn = enter.getTabletSn().trim();
        String bluetoothAddress = enter.getBluetoothAddress().trim();
        String bbi = enter.getBbi().trim();
        String controller = enter.getController().trim();
        String electricMachinery = enter.getElectricMachinery().trim();
        String meter = enter.getMeter().trim();
        String imei = enter.getImei().trim();

        // ???????????????????????????
        LambdaQueryWrapper<OpeCarDistribute> scooterWrapper = new LambdaQueryWrapper<>();
        scooterWrapper.eq(OpeCarDistribute::getDr, Constant.DR_FALSE);
        scooterWrapper.eq(OpeCarDistribute::getCustomerId, enter.getCustomerId());
        scooterWrapper.last("limit 1");
        OpeCarDistribute instance = opeCarDistributeMapper.selectOne(scooterWrapper);
        if (null != instance) {
            if (StringUtils.isNotBlank(instance.getRsn()) || StringUtils.isNotBlank(instance.getTabletSn()) || StringUtils.isNotBlank(instance.getBluetoothAddress())) {
                throw new SesMobileFrWhException(ExceptionCodeEnums.STEP_HAS_INPUT_IN_ROS.getCode(), ExceptionCodeEnums.STEP_HAS_INPUT_IN_ROS.getMessage());
            }
        }

        LambdaQueryWrapper<OpeCarDistribute> lqw = new LambdaQueryWrapper<>();
        lqw.eq(OpeCarDistribute::getDr, Constant.DR_FALSE);
        lqw.eq(OpeCarDistribute::getRsn, rsn);
        lqw.last("limit 1");
        OpeCarDistribute model = opeCarDistributeMapper.selectOne(lqw);
        if (null != model) {
            throw new SesMobileFrWhException(ExceptionCodeEnums.PARTS_HAS_INPUT.getCode(), ExceptionCodeEnums.PARTS_HAS_INPUT.getMessage());
        }

        LambdaQueryWrapper<OpeCarDistribute> checkWrapper = new LambdaQueryWrapper<>();
        checkWrapper.eq(OpeCarDistribute::getDr, Constant.DR_FALSE);
        checkWrapper.eq(OpeCarDistribute::getCustomerId, customerId);
        checkWrapper.last("limit 1");
        OpeCarDistribute checkModel = opeCarDistributeMapper.selectOne(checkWrapper);
        if (null != checkModel && null != checkModel.getWarehouseAccountId() && StringUtils.isNotBlank(checkModel.getRsn())) {
            throw new SesMobileFrWhException(ExceptionCodeEnums.ORDER_HAS_DEAL.getCode(), ExceptionCodeEnums.ORDER_HAS_DEAL.getMessage());
        }

        // ??????rsn????????????????????????
        LambdaQueryWrapper<OpeCodebaseRsn> existWrapper = new LambdaQueryWrapper<>();
        existWrapper.eq(OpeCodebaseRsn::getDr, Constant.DR_FALSE);
        existWrapper.eq(OpeCodebaseRsn::getStatus, 1);
        existWrapper.eq(OpeCodebaseRsn::getRsn, rsn);
        int count = opeCodebaseRsnService.count(existWrapper);
        if (count == 0) {
            throw new SesMobileFrWhException(ExceptionCodeEnums.RSN_NOT_EXISTS_CODEBASE.getCode(), ExceptionCodeEnums.RSN_NOT_EXISTS_CODEBASE.getMessage());
        }

        // ??????rsn???????????????(??????/??????)???????????????????????????(??????/??????)????????????,?????????????????????
        check(rsn, customerId);

        // ????????????
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

        // ??????
        LambdaQueryWrapper<OpeCarDistribute> qw = new LambdaQueryWrapper<>();
        qw.eq(OpeCarDistribute::getDr, Constant.DR_FALSE);
        qw.eq(OpeCarDistribute::getCustomerId, customerId);
        opeCarDistributeMapper.update(distribute, qw);

        // node???appNode??????
        OpeCarDistributeNode node = new OpeCarDistributeNode();
        node.setNode(4);
        node.setAppNode(4);
        node.setUpdatedBy(enter.getUserId());
        node.setUpdatedTime(new Date());
        // ??????
        LambdaQueryWrapper<OpeCarDistributeNode> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OpeCarDistributeNode::getDr, Constant.DR_FALSE);
        wrapper.eq(OpeCarDistributeNode::getCustomerId, customerId);
        opeCarDistributeNodeMapper.update(node, wrapper);

        // ???????????????RSN?????????
        LambdaQueryWrapper<OpeCodebaseRsn> rsnWrapper = new LambdaQueryWrapper<>();
        rsnWrapper.eq(OpeCodebaseRsn::getDr, Constant.DR_FALSE);
        rsnWrapper.eq(OpeCodebaseRsn::getStatus, 1);
        rsnWrapper.eq(OpeCodebaseRsn::getRsn, rsn);
        rsnWrapper.last("limit 1");
        OpeCodebaseRsn codebaseRsn = opeCodebaseRsnService.getOne(rsnWrapper);
        if (null != codebaseRsn) {
            codebaseRsn.setStatus(2);
            codebaseRsn.setUpdatedBy(enter.getUserId());
            codebaseRsn.setUpdatedTime(new Date());
            opeCodebaseRsnService.updateById(codebaseRsn);
        }

        // ?????????????????????
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
     * ????????????
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult inputBattery(InputBatteryEnter enter) {
        Long inquiryId = enter.getId();

        LambdaQueryWrapper<OpeCarDistribute> checkWrapper = new LambdaQueryWrapper<>();
        checkWrapper.eq(OpeCarDistribute::getDr, Constant.DR_FALSE);
        checkWrapper.like(OpeCarDistribute::getBattery, enter.getBattery());
        checkWrapper.last("limit 1");
        OpeCarDistribute checkModel = opeCarDistributeMapper.selectOne(checkWrapper);
        if (null != checkModel) {
            throw new SesMobileFrWhException(ExceptionCodeEnums.PARTS_HAS_INPUT.getCode(), ExceptionCodeEnums.PARTS_HAS_INPUT.getMessage());
        }

        // ??????????????????????????????
        LambdaQueryWrapper<OpeCustomerInquiryB> lqw = new LambdaQueryWrapper<>();
        lqw.eq(OpeCustomerInquiryB::getDr, Constant.DR_FALSE);
        lqw.eq(OpeCustomerInquiryB::getInquiryId, inquiryId);
        lqw.last("limit 1");
        OpeCustomerInquiryB inquiryB = opeCustomerInquiryBService.getOne(lqw);
        Integer batteryNum = inquiryB.getProductQty();

        // ???????????????????????????
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
                        throw new SesMobileFrWhException(ExceptionCodeEnums.STEP_HAS_INPUT_IN_ROS.getCode(), ExceptionCodeEnums.STEP_HAS_INPUT_IN_ROS.getMessage());
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
                // ?????????????????????
                OpeCarDistribute distribute = new OpeCarDistribute();
                distribute.setBattery(enter.getBattery().trim());
                distribute.setUpdatedBy(enter.getUserId());
                distribute.setUpdatedTime(new Date());
                LambdaQueryWrapper<OpeCarDistribute> qw = new LambdaQueryWrapper<>();
                qw.eq(OpeCarDistribute::getDr, Constant.DR_FALSE);
                qw.eq(OpeCarDistribute::getCustomerId, enter.getCustomerId());
                opeCarDistributeMapper.update(distribute, qw);
            } else {
                // ???????????????????????????,??????????????????
                OpeCarDistribute distribute = new OpeCarDistribute();
                distribute.setBattery(modelBattery + "," + enter.getBattery().trim());
                distribute.setUpdatedBy(enter.getUserId());
                distribute.setUpdatedTime(new Date());
                LambdaQueryWrapper<OpeCarDistribute> qw = new LambdaQueryWrapper<>();
                qw.eq(OpeCarDistribute::getDr, Constant.DR_FALSE);
                qw.eq(OpeCarDistribute::getCustomerId, enter.getCustomerId());
                opeCarDistributeMapper.update(distribute, qw);
            }
        }

        // ?????????????????????????????????????????????
        OpeCarDistribute distribute = opeCarDistributeMapper.selectOne(queryWrapper);
        if (null != distribute) {
            String[] split = distribute.getBattery().split(",");
            List<String> batteryList = new ArrayList<>(Arrays.asList(split));
            // ????????????????????????????????????=????????????????????????
            if (CollectionUtils.isNotEmpty(batteryList)) {
                if (batteryNum.equals(batteryList.size())) {
                    // node???appNode??????
                    OpeCarDistributeNode node = new OpeCarDistributeNode();
                    node.setNode(5);
                    node.setAppNode(5);
                    node.setUpdatedBy(enter.getUserId());
                    node.setUpdatedTime(new Date());
                    // ??????
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
     * ????????????
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult setScooterModel(CustomerIdEnter enter) {
        log.info("??????????????????,?????????:[{}]", enter);

        LambdaQueryWrapper<OpeCarDistribute> scooterWrapper = new LambdaQueryWrapper<>();
        scooterWrapper.eq(OpeCarDistribute::getDr, Constant.DR_FALSE);
        scooterWrapper.eq(OpeCarDistribute::getCustomerId, enter.getCustomerId());
        scooterWrapper.last("limit 1");
        OpeCarDistribute model = opeCarDistributeMapper.selectOne(scooterWrapper);

        // ????????????
        OpeCustomer opeCustomer = opeCustomerMapper.selectById(enter.getCustomerId());
        if (null == opeCustomer) {
            throw new SesMobileFrWhException(ExceptionCodeEnums.CUSTOMER_NOT_EXIST.getCode(), ExceptionCodeEnums.CUSTOMER_NOT_EXIST.getMessage());
        }
        if (!StringUtils.equals(opeCustomer.getStatus(), CustomerStatusEnum.OFFICIAL_CUSTOMER.getValue())) {
            throw new SesMobileFrWhException(ExceptionCodeEnums.CONVERT_TO_FORMAL_CUSTOMER_FIRST.getCode(), ExceptionCodeEnums.CONVERT_TO_FORMAL_CUSTOMER_FIRST.getMessage());
        }

        // ???????????????????????????(???pla_user???)
        QueryAccountResult accountInfo = accountBaseService.customerAccountDeatil(opeCustomer.getEmail());
        if (null == accountInfo) {
            throw new SesMobileFrWhException(ExceptionCodeEnums.ACCOUNT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.ACCOUNT_IS_NOT_EXIST.getMessage());
        }

        // ?????????????????????????????????
        List<HubSaveScooterEnter> saveRelationList = Lists.newArrayList();

        // ????????????
        String licensePlate = model.getLicensePlate();
        log.info("?????????????????????:[{}]", licensePlate);

        // ???????????????????????????
        // ?????????????????????id?????????id
        CustomerIdEnter customerIdEnter = new CustomerIdEnter();
        customerIdEnter.setCustomerId(enter.getCustomerId());
        Map<String, Long> map = getSpecificatIdAndColorId(customerIdEnter);
        log.info("?????????????????????????????????:[{}]", map);
        Long specificatId = map.get("specificatId");
        Long inquiryColorId = map.get("colorId");

        // ?????????????????????????????????
        LambdaQueryWrapper<OpeWmsScooterStock> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OpeWmsScooterStock::getDr, Constant.DR_FALSE);
        wrapper.eq(OpeWmsScooterStock::getGroupId, specificatId);
        wrapper.eq(OpeWmsScooterStock::getColorId, inquiryColorId);
        wrapper.eq(OpeWmsScooterStock::getStockType, 2);
        wrapper.orderByDesc(OpeWmsScooterStock::getCreatedTime);
        List<OpeWmsScooterStock> stockList = opeWmsScooterStockMapper.selectList(wrapper);
        OpeWmsScooterStock stock = null;
        if (CollectionUtils.isEmpty(stockList)) {
            throw new SesMobileFrWhException(ExceptionCodeEnums.STOCK_IS_SHORTAGE.getCode(), ExceptionCodeEnums.STOCK_IS_SHORTAGE.getMessage());
        }

        stock = stockList.get(0);
        log.info("?????????????????????:[{}]", stock);
        // ????????????????????????????????????????????????????????????
        Long stockId = stock.getId();
        Integer ableStockQty = stock.getAbleStockQty();
        Integer usedStockQty = stock.getUsedStockQty();

        if (ableStockQty < 1) {
            throw new SesMobileFrWhException(ExceptionCodeEnums.SCOOTER_STOCK_IS_NOT_ENOUGH.getCode(), ExceptionCodeEnums.SCOOTER_STOCK_IS_NOT_ENOUGH.getMessage());
        }

        // ?????????????????????????????????-1,??????????????????+1
        OpeWmsScooterStock scooterStock = new OpeWmsScooterStock();
        scooterStock.setId(stockId);
        scooterStock.setAbleStockQty(ableStockQty - 1);
        scooterStock.setUsedStockQty(usedStockQty + 1);
        scooterStock.setUpdatedBy(enter.getUserId());
        scooterStock.setUpdatedTime(new Date());
        opeWmsScooterStockMapper.updateById(scooterStock);

        // ??????????????????
        if (null != stock) {
            log.info(" ??????????????????");
            OpeWmsStockRecord record = new OpeWmsStockRecord();
            record.setId(idAppService.getId(SequenceName.OPE_WMS_STOCK_RECORD));
            record.setDr(Constant.DR_FALSE);
            record.setRelationId(stock.getId());
            record.setRelationType(7);
            record.setInWhQty(1);
            record.setRecordType(2);
            record.setStockType(2);
            record.setCreatedBy(enter.getUserId());
            record.setCreatedTime(new Date());
            opeWmsStockRecordMapper.insert(record);
        }

        // ??????????????????
        String specificatName = getSpecificatNameById(model.getSpecificatTypeId());

        // ??????rsn??????sco_scooter???
        ScoScooterResult scoScooter = scooterService.getScoScooterByTableSn(model.getRsn());
        if (null == scoScooter) {
            throw new SesMobileFrWhException(ExceptionCodeEnums.SCOOTER_NOT_EXIST.getCode(), ExceptionCodeEnums.SCOOTER_NOT_EXIST.getMessage());
        }
        Long scooterId = scoScooter.getId();
        if (null == scooterId) {
            throw new SesMobileFrWhException(ExceptionCodeEnums.SCOOTER_NOT_EXIST.getCode(), ExceptionCodeEnums.SCOOTER_NOT_EXIST.getMessage());
        }

        // ??????sco_scooter?????????
        log.info("????????????sco_scooter?????????,???????????????:[{},{}]", scooterId, licensePlate);
        scooterService.updateScooterNo(scooterId, licensePlate);
        log.info("??????sco_scooter???????????????");

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

        // ??????rsn?????????????????????????????????????????????????????????
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
        log.info("??????????????????");

        // ????????????
        AdmScooter admScooter = scooterModelService.getScooterBySn(model.getTabletSn());
        if (null != admScooter) {
            throw new SesMobileFrWhException(ExceptionCodeEnums.SN_ALREADY_EXISTS.getCode(), ExceptionCodeEnums.SN_ALREADY_EXISTS.getMessage());
        }
        log.info("???????????????");
        SpecificGroupDTO group = specificService.getSpecificGroupById(specificatId);
        ColorDTO color = colorService.getColorInfoById(inquiryColorId);
        if (null == group) {
            throw new SesMobileFrWhException(ExceptionCodeEnums.GROUP_NOT_EXIST.getCode(), ExceptionCodeEnums.GROUP_NOT_EXIST.getMessage());
        }
        if (null == color) {
            throw new SesMobileFrWhException(ExceptionCodeEnums.COLOR_NOT_EXIST.getCode(), ExceptionCodeEnums.COLOR_NOT_EXIST.getMessage());
        }

        // ??????adm_scooter???
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
        log.info("??????adm_scooter?????????");

        // ?????????????????????(sn)?????????sco_scooter??????????????? ???????????????true ????????????false
        Boolean flag = scooterService.getSnIsExist(scooter.getSn());
        if (flag) {
            log.info("sn???sco_scooter????????????");
            String scooterNo = generateScooterNo();
            scooterService.syncScooterData(buildData(scooter.getId(), scooter.getSn(), scooter.getScooterController(), enter.getUserId(), scooterNo));
        }

        // ????????????
        AdmScooter scooterModel = scooterModelService.getScooterById(scooter.getId());
        if (null == scooterModel) {
            throw new SesMobileFrWhException(ExceptionCodeEnums.SCOOTER_NOT_EXIST.getCode(), ExceptionCodeEnums.SCOOTER_NOT_EXIST.getMessage());
        }
        // ????????????????????????????????????????????????
        String status = scooterService.getScooterStatusByTabletSn(scooter.getSn());
        if (ScooterLockStatusEnums.UNLOCK.getValue().equals(status)) {
            throw new SesMobileFrWhException(ExceptionCodeEnums.SCOOTER_NOT_CLOSED.getCode(), ExceptionCodeEnums.SCOOTER_NOT_CLOSED.getMessage());
        }
        SpecificTypeDTO specificType = specificService.getSpecificTypeByName(specificatName);
        if (null == specificType) {
            throw new SesMobileFrWhException(ExceptionCodeEnums.SPECIFICAT_TYPE_NOT_EXIST.getCode(), ExceptionCodeEnums.SPECIFICAT_TYPE_NOT_EXIST.getMessage());
        }
        Integer type = ScooterModelEnum.getScooterModelType(specificType.getSpecificatName());
        if (type == 0) {
            throw new SesMobileFrWhException(ExceptionCodeEnums.SELECT_SCOOTER_MODEL_ERROR.getCode(), ExceptionCodeEnums.SELECT_SCOOTER_MODEL_ERROR.getMessage());
        }

        // ??????????????????????????????????????????????????????????????????
        /*if (null != scooterModel.getScooterController() && scooterModel.getScooterController().equals(type)) {
            log.info("?????????????????????????????????????????????,????????????,????????????emq??????");
            // node???appNode??????
            OpeCarDistributeNode node = new OpeCarDistributeNode();
            node.setAppNode(6);
            node.setFlag(2);
            node.setUpdatedBy(userId);
            node.setUpdatedTime(new Date());
            // ??????
            LambdaQueryWrapper<OpeCarDistributeNode> lqw = new LambdaQueryWrapper<>();
            lqw.eq(OpeCarDistributeNode::getDr, Constant.DR_FALSE);
            lqw.eq(OpeCarDistributeNode::getCustomerId, enter.getCustomerId());
            opeCarDistributeNodeMapper.update(node, lqw);
            return new GeneralResult(enter.getRequestId());
        }*/

        // ????????????????????????
        AdmScooterUpdateEnter param = new AdmScooterUpdateEnter();
        param.setId(scooterModel.getId());
        param.setScooterController(type);
        param.setGroupId(specificType.getGroupId());
        param.setGroupName(specificType.getGroupName());
        scooterModelService.updateAdmScooter(param);
        log.info("????????????????????????");
        scooterService.syncScooterModel(scooterModel.getSn(), type);
        log.info("??????????????????");

        List<SpecificDefGroupPublishDTO> list = buildSetScooterModelData(specificType.getId());
        if (CollectionUtils.isNotEmpty(list)) {
            // ??????EMQ??????,????????????????????????????????????
            SetScooterModelPublishDTO instance = new SetScooterModelPublishDTO();
            instance.setTabletSn(scooterModel.getSn());
            instance.setScooterModel(type);
            instance.setSpecificDefGroupList(list);
            scooterEmqXService.setScooterModel(instance);
        }
        log.info("??????????????????");

        // node???appNode??????
        OpeCarDistributeNode node = new OpeCarDistributeNode();
        node.setAppNode(6);
        node.setFlag(2);
        node.setUpdatedBy(enter.getUserId());
        node.setUpdatedTime(new Date());
        // ??????
        LambdaQueryWrapper<OpeCarDistributeNode> lqw = new LambdaQueryWrapper<>();
        lqw.eq(OpeCarDistributeNode::getDr, Constant.DR_FALSE);
        lqw.eq(OpeCarDistributeNode::getCustomerId, enter.getCustomerId());
        opeCarDistributeNodeMapper.update(node, lqw);

        /*---------------sim ????????????--------------*/
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
        /*---------------sim ????????????--------------*/
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * ??????????????????????????????
     */
    private List<SpecificDefGroupPublishDTO> buildSetScooterModelData(Long specificTypeId) {
        List<SpecificDefGroupPublishDTO> list = new ArrayList<>();

        // ??????????????????????????????,????????????????????????????????????????????????/???????????????(???????????????????????????) ???????????????????????????/?????????(???????????????????????????????????????)
        // ????????????????????????????????????
        List<SpecificDefDTO> specificDefList = specificService.getSpecificDefBySpecificId(specificTypeId);
        if (CollectionUtils.isNotEmpty(specificDefList)) {
            // ?????????ope_specificat_def?????????def_group_id??????????????????,???????????????stream?????????????????????
            specificDefList.forEach(def -> {
                if (null == def.getSpecificDefGroupId()) {
                    throw new SesMobileFrWhException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
                }
            });

            Map<Long, List<SpecificDefDTO>> specificDefGroupMap = specificDefList.stream().collect(Collectors.groupingBy(SpecificDefDTO::getSpecificDefGroupId));

            for (Map.Entry<Long, List<SpecificDefDTO>> map : specificDefGroupMap.entrySet()) {
                Map<String, String> specificDefMap = map.getValue().stream().collect(Collectors.toMap(SpecificDefDTO::getDefName, SpecificDefDTO::getDefValue));

                // ???????????????????????? -- ???????????????????????????
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
     * ????????????????????????
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
     * ??????????????????
     */
    private String generateScooterNo() {
        Calendar cal = Calendar.getInstance();
        String year = String.valueOf(cal.get(Calendar.YEAR));
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);

        // ????????????????????? + ???????????? + ???????????? + ???????????? + ???????????? + ?????? + ?????? + ???????????????(?????????1??????)
        StringBuilder sb = new StringBuilder();
        sb.append("FR");
        sb.append("ED");
        sb.append("D");
        sb.append("0");
        sb.append(year.substring(2, 4));
        sb.append(MonthCodeEnum.getMonthCodeByMonth(month));
        // ?????????????????????,???????????????6????????????????????????
        String timeStamp = String.valueOf(System.currentTimeMillis());
        String sub = timeStamp.substring(timeStamp.length() - 6);
        String number = String.format("%s%s%s", DayCodeEnum.getDayCodeByDay(day), "1", sub);
        sb.append(number);
        return sb.toString();
    }

    /**
     * ??????rsn???????????????(??????/??????)???????????????????????????(??????/??????)????????????
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

        // ??????????????????????????????????????????,????????????
        OpeSaleScooter saleScooter = getSaleScooter(customerId);
        if (!Objects.equals(groupId, saleScooter.getGroupId()) || !Objects.equals(colorId, saleScooter.getColorId())) {
            throw new SesMobileFrWhException(ExceptionCodeEnums.SPECIFICAT_NOT_MATCH.getCode(), ExceptionCodeEnums.SPECIFICAT_NOT_MATCH.getMessage());
        }
    }

    /**
     * ????????????id??????????????????????????????
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
     * ????????????id?????????????????????id?????????id
     */
    public Map<String, Long> getSpecificatIdAndColorId(CustomerIdEnter enter) {
        Map<String, Long> result = Maps.newHashMapWithExpectedSize(2);
        // ????????????????????????id
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

        // ????????????id???ope_sale_scooter??????
        OpeSaleScooter opeSaleScooter = opeSaleScooterMapper.selectById(productId);
        if (null == opeSaleScooter) {
            throw new SesMobileFrWhException(ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getMessage());
        }
        // ????????????id?????????id
        Long groupId = opeSaleScooter.getGroupId();
        Long colorId = opeSaleScooter.getColorId();
        result.put("specificatId", groupId);
        result.put("colorId", colorId);
        return result;
    }

    /**
     * ????????????id??????????????????
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
