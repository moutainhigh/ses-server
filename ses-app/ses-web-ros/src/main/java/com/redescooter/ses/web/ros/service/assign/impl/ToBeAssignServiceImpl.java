package com.redescooter.ses.web.ros.service.assign.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.enums.assign.FactoryEnum;
import com.redescooter.ses.api.common.enums.assign.ProductTypeEnum;
import com.redescooter.ses.api.common.enums.assign.ScooterTypeEnum;
import com.redescooter.ses.api.common.enums.assign.YearEnum;
import com.redescooter.ses.api.common.enums.customer.CustomerStatusEnum;
import com.redescooter.ses.api.common.enums.customer.CustomerTypeEnum;
import com.redescooter.ses.api.common.enums.date.DayCodeEnum;
import com.redescooter.ses.api.common.enums.date.MonthCodeEnum;
import com.redescooter.ses.api.common.enums.scooter.ScooterModelEnum;
import com.redescooter.ses.api.common.enums.scooter.ScooterModelEnums;
import com.redescooter.ses.api.common.enums.scooter.ScooterStatusEnums;
import com.redescooter.ses.api.common.enums.wms.WmsStockStatusEnum;
import com.redescooter.ses.api.common.vo.base.BooleanResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.StringEnter;
import com.redescooter.ses.api.common.vo.scooter.ColorDTO;
import com.redescooter.ses.api.common.vo.scooter.SpecificGroupDTO;
import com.redescooter.ses.api.common.vo.scooter.SyncScooterDataDTO;
import com.redescooter.ses.api.foundation.service.base.AccountBaseService;
import com.redescooter.ses.api.foundation.service.scooter.AssignScooterService;
import com.redescooter.ses.api.foundation.vo.tenant.QueryAccountResult;
import com.redescooter.ses.api.hub.service.admin.ScooterModelService;
import com.redescooter.ses.api.hub.service.corporate.CorporateScooterService;
import com.redescooter.ses.api.hub.service.customer.CusotmerScooterService;
import com.redescooter.ses.api.hub.service.operation.ColorService;
import com.redescooter.ses.api.hub.service.operation.SpecificService;
import com.redescooter.ses.api.hub.vo.HubSaveScooterEnter;
import com.redescooter.ses.api.hub.vo.admin.AdmScooter;
import com.redescooter.ses.api.mobile.b.service.ScooterMobileBService;
import com.redescooter.ses.api.mobile.b.vo.CorDriver;
import com.redescooter.ses.api.mobile.b.vo.CorDriverScooter;
import com.redescooter.ses.api.scooter.service.ScooterService;
import com.redescooter.ses.api.scooter.vo.ScoScooterResult;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.SesStringUtils;
import com.redescooter.ses.tool.utils.map.MapUtil;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.dao.assign.OpeCarDistributeExMapper;
import com.redescooter.ses.web.ros.dao.assign.OpeCarDistributeMapper;
import com.redescooter.ses.web.ros.dao.assign.OpeCarDistributeNodeMapper;
import com.redescooter.ses.web.ros.dao.base.OpeColorMapper;
import com.redescooter.ses.web.ros.dao.base.OpeCustomerInquiryMapper;
import com.redescooter.ses.web.ros.dao.base.OpeCustomerMapper;
import com.redescooter.ses.web.ros.dao.base.OpeProductionScooterBomMapper;
import com.redescooter.ses.web.ros.dao.base.OpeSaleScooterMapper;
import com.redescooter.ses.web.ros.dao.base.OpeSpecificatTypeMapper;
import com.redescooter.ses.web.ros.dao.base.OpeWmsScooterStockMapper;
import com.redescooter.ses.web.ros.dao.base.OpeWmsStockRecordMapper;
import com.redescooter.ses.web.ros.dao.restproductionorder.OpeInWhouseOrderSerialBindMapper;
import com.redescooter.ses.web.ros.dao.wms.cn.china.OpeWmsStockSerialNumberMapper;
import com.redescooter.ses.web.ros.dm.OpeCarDistribute;
import com.redescooter.ses.web.ros.dm.OpeCarDistributeNode;
import com.redescooter.ses.web.ros.dm.OpeCodebaseRelation;
import com.redescooter.ses.web.ros.dm.OpeCodebaseRsn;
import com.redescooter.ses.web.ros.dm.OpeCodebaseVin;
import com.redescooter.ses.web.ros.dm.OpeColor;
import com.redescooter.ses.web.ros.dm.OpeCustomer;
import com.redescooter.ses.web.ros.dm.OpeCustomerInquiry;
import com.redescooter.ses.web.ros.dm.OpeCustomerInquiryB;
import com.redescooter.ses.web.ros.dm.OpeInWhouseOrderSerialBind;
import com.redescooter.ses.web.ros.dm.OpeProductionScooterBom;
import com.redescooter.ses.web.ros.dm.OpeSaleScooter;
import com.redescooter.ses.web.ros.dm.OpeSimInformation;
import com.redescooter.ses.web.ros.dm.OpeSpecificatType;
import com.redescooter.ses.web.ros.dm.OpeWmsScooterStock;
import com.redescooter.ses.web.ros.dm.OpeWmsStockRecord;
import com.redescooter.ses.web.ros.dm.OpeWmsStockSerialNumber;
import com.redescooter.ses.web.ros.enums.assign.CustomerFormEnum;
import com.redescooter.ses.web.ros.enums.assign.IndustryTypeEnum;
import com.redescooter.ses.web.ros.enums.distributor.DelStatusEnum;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.assign.ToBeAssignService;
import com.redescooter.ses.web.ros.service.base.OpeCodebaseRelationService;
import com.redescooter.ses.web.ros.service.base.OpeCodebaseRsnService;
import com.redescooter.ses.web.ros.service.base.OpeCodebaseVinService;
import com.redescooter.ses.web.ros.service.base.OpeCustomerInquiryBService;
import com.redescooter.ses.web.ros.service.base.OpeWmsStockSerialNumberService;
import com.redescooter.ses.web.ros.service.sim.OpeSimInformationService;
import com.redescooter.ses.web.ros.vo.assign.done.enter.AssignedListEnter;
import com.redescooter.ses.web.ros.vo.assign.tobe.enter.CustomerIdEnter;
import com.redescooter.ses.web.ros.vo.assign.tobe.enter.ToBeAssignInputBatteryDetailEnter;
import com.redescooter.ses.web.ros.vo.assign.tobe.enter.ToBeAssignInputBatteryEnter;
import com.redescooter.ses.web.ros.vo.assign.tobe.enter.ToBeAssignLicensePlateNextDetailEnter;
import com.redescooter.ses.web.ros.vo.assign.tobe.enter.ToBeAssignLicensePlateNextEnter;
import com.redescooter.ses.web.ros.vo.assign.tobe.enter.ToBeAssignListEnter;
import com.redescooter.ses.web.ros.vo.assign.tobe.enter.ToBeAssignSeatNextDetailEnter;
import com.redescooter.ses.web.ros.vo.assign.tobe.enter.ToBeAssignSeatNextEnter;
import com.redescooter.ses.web.ros.vo.assign.tobe.enter.ToBeAssignSubmitDetailEnter;
import com.redescooter.ses.web.ros.vo.assign.tobe.enter.ToBeAssignSubmitEnter;
import com.redescooter.ses.web.ros.vo.assign.tobe.result.ToBeAssignColorResult;
import com.redescooter.ses.web.ros.vo.assign.tobe.result.ToBeAssignDetailCustomerInfoResult;
import com.redescooter.ses.web.ros.vo.assign.tobe.result.ToBeAssignDetailResult;
import com.redescooter.ses.web.ros.vo.assign.tobe.result.ToBeAssignDetailScooterInfoResult;
import com.redescooter.ses.web.ros.vo.assign.tobe.result.ToBeAssignDetailScooterInfoSubResult;
import com.redescooter.ses.web.ros.vo.assign.tobe.result.ToBeAssignListResult;
import com.redescooter.ses.web.ros.vo.assign.tobe.result.ToBeAssignNodeResult;
import com.redescooter.ses.web.ros.vo.assign.tobe.result.ToBeAssignNodeScooterInfoResult;
import com.redescooter.ses.web.ros.vo.assign.tobe.result.ToBeAssignNodeScooterInfoSubResult;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description ???????????????ServiceImpl
 * @Author Chris
 * @Date 2020/12/27 14:50
 */
@Service
public class ToBeAssignServiceImpl implements ToBeAssignService {

    private static final Logger logger = LoggerFactory.getLogger(ToBeAssignServiceImpl.class);

    @Autowired
    private OpeCarDistributeMapper opeCarDistributeMapper;

    @Autowired
    private OpeCarDistributeNodeMapper opeCarDistributeNodeMapper;

    @Autowired
    private OpeCarDistributeExMapper opeCarDistributeExMapper;

    @Autowired
    private OpeCustomerInquiryMapper opeCustomerInquiryMapper;

    @Autowired
    private OpeSaleScooterMapper opeSaleScooterMapper;

    @Autowired
    private OpeColorMapper opeColorMapper;

    @Autowired
    private OpeSpecificatTypeMapper opeSpecificatTypeMapper;

    @Autowired
    private OpeWmsScooterStockMapper opeWmsScooterStockMapper;

    @Autowired
    private OpeCustomerMapper opeCustomerMapper;

    @Autowired
    private OpeWmsStockRecordMapper opeWmsStockRecordMapper;

    @Autowired
    private OpeInWhouseOrderSerialBindMapper opeInWhouseOrderSerialBindMapper;

    @Autowired
    private OpeProductionScooterBomMapper opeProductionScooterBomMapper;

    @Autowired
    private OpeWmsStockSerialNumberMapper opeWmsStockSerialNumberMapper;

    @Autowired
    private OpeWmsStockSerialNumberService opeWmsStockSerialNumberService;

    @Autowired
    private OpeCustomerInquiryBService opeCustomerInquiryBService;

    @Autowired
    private OpeCodebaseVinService opeCodebaseVinService;

    @Autowired
    private OpeCodebaseRsnService opeCodebaseRsnService;

    @Autowired
    private OpeCodebaseRelationService opeCodebaseRelationService;

    @DubboReference
    private IdAppService idAppService;

    @DubboReference
    private AccountBaseService accountBaseService;

    @DubboReference
    private ScooterService scooterService;

    @DubboReference
    private CorporateScooterService corporateScooterService;

    @DubboReference
    private CusotmerScooterService cusotmerScooterService;

    @DubboReference
    private ScooterMobileBService scooterMobileBService;

    @DubboReference
    private AssignScooterService assignScooterService;

    @Autowired
    private JedisCluster jedisCluster;

    @DubboReference
    private ScooterModelService scooterModelService;

    /**
     * sim card
     */
    @Autowired
    private OpeSimInformationService simInformationService;

    @DubboReference
    private SpecificService specificService;

    @DubboReference
    private ColorService colorService;

    /**
     * ???????????????
     */
    @Override
    public PageResult<ToBeAssignListResult> getToBeAssignList(ToBeAssignListEnter enter) {
        logger.info("???????????????????????????:[{}]", enter);
        SesStringUtils.objStringTrim(enter);

        int count = opeCarDistributeExMapper.getToBeAssignListCount(enter);
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        List<ToBeAssignListResult> list = opeCarDistributeExMapper.getToBeAssignList(enter);

        // ?????????????????????????????????????????????????????????????????????
        /*if (CollectionUtils.isNotEmpty(list)) {
            Iterator<ToBeAssignListResult> iterator = list.iterator();
            while (iterator.hasNext()) {
                ToBeAssignListResult next = iterator.next();
                String email = next.getEmail();
                // ??????email???pla_user?????????,????????????,????????????????????? flag:????????????true,???????????????false
                Boolean flag = assignScooterService.getPlaUserIsExistByEmail(email);
                if (!flag) {
                    iterator.remove();
                }
            }
        }*/
        return PageResult.create(enter, count, list);
    }

    /**
     * ???????????????????????????????????????
     */
    @Override
    public ToBeAssignDetailResult getToBeAssignDetail(CustomerIdEnter enter) {
        logger.info("???????????????????????????????????????????????????:[{}]", enter);
        ToBeAssignDetailResult result = new ToBeAssignDetailResult();

        // ??????????????????node???????????????,???????????????,??????????????????
        LambdaQueryWrapper<OpeCarDistributeNode> nodeWrapper = new LambdaQueryWrapper<>();
        nodeWrapper.eq(OpeCarDistributeNode::getDr, DelStatusEnum.VALID.getCode());
        nodeWrapper.eq(OpeCarDistributeNode::getCustomerId, enter.getCustomerId());
        List<OpeCarDistributeNode> nodeList = opeCarDistributeNodeMapper.selectList(nodeWrapper);
        if (CollectionUtils.isEmpty(nodeList)) {

            LambdaQueryWrapper<OpeCustomerInquiry> qw = new LambdaQueryWrapper<>();
            qw.eq(OpeCustomerInquiry::getDr, Constant.DR_FALSE);
            qw.eq(OpeCustomerInquiry::getCustomerId, enter.getCustomerId());
            qw.last("limit 1");
            OpeCustomerInquiry inquiry = opeCustomerInquiryMapper.selectOne(qw);
            if (null == inquiry) {
                throw new SesWebRosException(ExceptionCodeEnums.INQUIRY_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.INQUIRY_IS_NOT_EXIST.getMessage());
            }
            Long productId = inquiry.getProductId();
            OpeSaleScooter saleScooter = opeSaleScooterMapper.selectById(productId);
            if (null == saleScooter) {
                throw new SesWebRosException(ExceptionCodeEnums.SCOOTER_NOT_EXIST.getCode(), ExceptionCodeEnums.SCOOTER_NOT_EXIST.getMessage());
            }
            // ???????????????id??????????????????id?????????id
            Long specificatId = saleScooter.getSpecificatId();
            Long colorId = saleScooter.getColorId();

            OpeCarDistributeNode node = new OpeCarDistributeNode();
            node.setId(idAppService.getId(SequenceName.OPE_CAR_DISTRIBUTE_NODE));
            node.setDr(DelStatusEnum.VALID.getCode());
            node.setTenantId(enter.getTenantId());
            node.setUserId(enter.getUserId());
            node.setCustomerId(enter.getCustomerId());
            node.setNode(1);
            node.setAppNode(1);
            node.setFlag(0);
            node.setCreatedBy(enter.getUserId());
            node.setCreatedTime(new Date());
            opeCarDistributeNodeMapper.insert(node);

            // ????????????????????????
            OpeCarDistribute model = new OpeCarDistribute();
            model.setId(idAppService.getId(SequenceName.OPE_CAR_DISTRIBUTE));
            model.setDr(DelStatusEnum.VALID.getCode());
            model.setTenantId(enter.getTenantId());
            model.setUserId(enter.getUserId());
            model.setCustomerId(enter.getCustomerId());
            model.setSpecificatTypeId(specificatId);
            model.setColorId(colorId);
            model.setSeatNumber(2);
            model.setQty(1);
            model.setCreatedBy(enter.getUserId());
            model.setCreatedTime(new Date());
            opeCarDistributeMapper.insert(model);
        }

        // ????????????
        ToBeAssignDetailCustomerInfoResult customerInfo = opeCarDistributeExMapper.getCustomerInfo(enter.getCustomerId());
        if (null == customerInfo) {
            throw new SesWebRosException(ExceptionCodeEnums.CUSTOMER_NOT_EXIST.getCode(), ExceptionCodeEnums.CUSTOMER_NOT_EXIST.getMessage());
        }
        String customerType = customerInfo.getCustomerType();
        String industryType = customerInfo.getIndustryType();
        if (StringUtils.isNotBlank(customerType)) {
            customerInfo.setCustomerTypeMsg(CustomerFormEnum.showMsg(customerType));
        }
        if (StringUtils.isNotBlank(industryType)) {
            customerInfo.setIndustryTypeMsg(IndustryTypeEnum.showMsg(industryType));
        }

        // ????????????????????????id
        LambdaQueryWrapper<OpeCustomerInquiry> opeCustomerInquiryWrapper = new LambdaQueryWrapper<>();
        opeCustomerInquiryWrapper.eq(OpeCustomerInquiry::getDr, DelStatusEnum.VALID.getCode());
        opeCustomerInquiryWrapper.eq(OpeCustomerInquiry::getCustomerId, enter.getCustomerId());
        List<OpeCustomerInquiry> list = opeCustomerInquiryMapper.selectList(opeCustomerInquiryWrapper);
        if (CollectionUtils.isEmpty(list)) {
            throw new SesWebRosException(ExceptionCodeEnums.CUSTOMER_NOT_EXIST.getCode(), ExceptionCodeEnums.CUSTOMER_NOT_EXIST.getMessage());
        }

        List<ToBeAssignDetailScooterInfoResult> scooterList = Lists.newArrayList();
        List<ToBeAssignDetailScooterInfoSubResult> subList = Lists.newArrayList();

        for (OpeCustomerInquiry o : list) {
            ToBeAssignDetailScooterInfoResult scooter = new ToBeAssignDetailScooterInfoResult();
            ToBeAssignDetailScooterInfoSubResult sub = new ToBeAssignDetailScooterInfoSubResult();

            Long productId = o.getProductId();
            if (null == productId) {
                throw new SesWebRosException(ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getMessage());
            }
            // ????????????id???ope_sale_scooter??????
            OpeSaleScooter opeSaleScooter = opeSaleScooterMapper.selectById(productId);
            if (null == opeSaleScooter) {
                throw new SesWebRosException(ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getMessage());
            }
            // ????????????id?????????id
            Long specificatId = opeSaleScooter.getSpecificatId();
            Long colorId = opeSaleScooter.getColorId();

            // ????????????id???ope_specificat_type??????
            if (null != specificatId) {
                String specificatName = getSpecificatNameById(specificatId);
                scooter.setSpecificatId(specificatId);
                scooter.setSpecificatName(specificatName);
            }

            // ????????????id???ope_color??????
            if (null != colorId) {
                Map<String, String> map = getColorNameAndValueById(colorId);
                sub.setColorName(map.get("colorName"));
                sub.setColorValue(map.get("colorValue"));
                sub.setColorId(colorId);
            }

            // ?????????????????????
            LambdaQueryWrapper<OpeCustomerInquiryB> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(OpeCustomerInquiryB::getDr, Constant.DR_FALSE);
            wrapper.eq(OpeCustomerInquiryB::getInquiryId, o.getId());
            wrapper.last("limit 1");
            OpeCustomerInquiryB inquiryB = opeCustomerInquiryBService.getOne(wrapper);
            if (null != inquiryB) {
                sub.setBatteryNum(inquiryB.getProductQty());
            }
            sub.setToBeAssignCount(o.getScooterQuantity());
            scooter.setTotalCount(o.getScooterQuantity());

            subList.add(sub);
            scooter.setScooterList(subList);
            scooterList.add(scooter);
        }

        result.setCustomerInfo(customerInfo);
        result.setTaskInfo(scooterList);
        result.setRequestId(enter.getRequestId());
        return result;
    }

    /**
     * ?????????????????????????????????
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult getSeatNext(ToBeAssignSeatNextEnter enter) {
        logger.info("?????????????????????????????????????????????:[{}]", enter);
        if (null == enter || StringUtils.isBlank(enter.getList())) {
            throw new SesWebRosException(ExceptionCodeEnums.SEAT_NOT_EMPTY.getCode(), ExceptionCodeEnums.SEAT_NOT_EMPTY.getMessage());
        }

        // ??????
        List<ToBeAssignSeatNextDetailEnter> list;
        try {
            list = JSONArray.parseArray(enter.getList(), ToBeAssignSeatNextDetailEnter.class);
        } catch (Exception ex) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }
        if (CollectionUtils.isEmpty(list)) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }

        for (ToBeAssignSeatNextDetailEnter o : list) {
            Long specificatId = o.getSpecificatId();
            Integer seatNumber = o.getSeatNumber();
            String vinCode = o.getVin().trim();
            Integer qty = o.getQty();

            // ??????VIN Code,????????????????????????????????????????????????
            /*String vinCode = generateVINCode(specificatId, specificatName, seatNumber);
            logger.info("?????????VIN Code???:[{}]", vinCode);*/

            // ???????????????????????????
            LambdaQueryWrapper<OpeCarDistribute> lqw = new LambdaQueryWrapper<>();
            lqw.eq(OpeCarDistribute::getDr, Constant.DR_FALSE);
            lqw.eq(OpeCarDistribute::getCustomerId, enter.getCustomerId());
            lqw.last("limit 1");
            OpeCarDistribute instance = opeCarDistributeMapper.selectOne(lqw);
            if (null != instance) {
                String vin = instance.getVinCode();
                if (StringUtils.isNotBlank(vin)) {
                    throw new SesWebRosException(ExceptionCodeEnums.STEP_HAS_INPUT_IN_APP.getCode(), ExceptionCodeEnums.STEP_HAS_INPUT_IN_APP.getMessage());
                }
            }

            if (vinCode.length() != 17) {
                throw new SesWebRosException(ExceptionCodeEnums.VIN_NOT_MATCH.getCode(), ExceptionCodeEnums.VIN_NOT_MATCH.getMessage());
            }
            if (!vinCode.startsWith("VXSR2A")) {
                throw new SesWebRosException(ExceptionCodeEnums.VIN_NOT_MATCH.getCode(), ExceptionCodeEnums.VIN_NOT_MATCH.getMessage());
            }

            // ????????????id??????????????????
            OpeSpecificatType specificatType = opeSpecificatTypeMapper.selectById(specificatId);
            if (null == specificatType) {
                throw new SesWebRosException(ExceptionCodeEnums.SPECIFICAT_TYPE_NOT_EXIST.getCode(), ExceptionCodeEnums.SPECIFICAT_TYPE_NOT_EXIST.getMessage());
            }
            String productType = ProductTypeEnum.showCode(specificatType.getSpecificatName());
            // ?????????7???,????????????
            String productTypeSub = vinCode.substring(6, 7);
            if (!StringUtils.equals(productType, productTypeSub)) {
                throw new SesWebRosException(ExceptionCodeEnums.VIN_NOT_MATCH.getCode(), ExceptionCodeEnums.VIN_NOT_MATCH.getMessage());
            }

            // ?????????8???,????????????
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
                throw new SesWebRosException(ExceptionCodeEnums.VIN_HAS_USED.getCode(), ExceptionCodeEnums.VIN_HAS_USED.getMessage());
            }

            // ??????vin????????????????????????
            LambdaQueryWrapper<OpeCodebaseVin> existWrapper = new LambdaQueryWrapper<>();
            existWrapper.eq(OpeCodebaseVin::getDr, Constant.DR_FALSE);
            existWrapper.eq(OpeCodebaseVin::getStatus, 1);
            existWrapper.eq(OpeCodebaseVin::getVin, vinCode);
            int count = opeCodebaseVinService.count(existWrapper);
            if (count == 0) {
                throw new SesWebRosException(ExceptionCodeEnums.VIN_NOT_EXISTS_CODEBASE.getCode(), ExceptionCodeEnums.VIN_NOT_EXISTS_CODEBASE.getMessage());
            }

            // ????????????
            OpeCarDistribute model = new OpeCarDistribute();
            model.setSpecificatTypeId(specificatId);
            model.setSeatNumber(seatNumber);
            model.setVinCode(vinCode);
            model.setQty(qty);
            model.setUpdatedBy(enter.getUserId());
            model.setUpdatedTime(new Date());
            LambdaQueryWrapper<OpeCarDistribute> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(OpeCarDistribute::getCustomerId, enter.getCustomerId());
            opeCarDistributeMapper.update(model, wrapper);

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
        }

        // node???node??????+1
        LambdaQueryWrapper<OpeCarDistributeNode> nodeWrapper = new LambdaQueryWrapper<>();
        nodeWrapper.eq(OpeCarDistributeNode::getDr, DelStatusEnum.VALID.getCode());
        nodeWrapper.eq(OpeCarDistributeNode::getCustomerId, enter.getCustomerId());
        OpeCarDistributeNode node = new OpeCarDistributeNode();
        node.setNode(2);
        node.setAppNode(2);
        node.setFlag(1);
        node.setUpdatedBy(enter.getUserId());
        node.setUpdatedTime(new Date());
        opeCarDistributeNodeMapper.update(node, nodeWrapper);
        return new GeneralResult();
    }

    /**
     * ??????????????????????????????
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult getLicensePlateNext(ToBeAssignLicensePlateNextEnter enter) {
        logger.info("??????????????????????????????????????????:[{}]", enter);
        if (null == enter || StringUtils.isBlank(enter.getList())) {
            throw new SesWebRosException(ExceptionCodeEnums.LICENSE_PLATE_NOT_EMPTY.getCode(), ExceptionCodeEnums.LICENSE_PLATE_NOT_EMPTY.getMessage());
        }

        // ??????
        List<ToBeAssignLicensePlateNextDetailEnter> list;
        try {
            list = JSONArray.parseArray(enter.getList(), ToBeAssignLicensePlateNextDetailEnter.class);
        } catch (Exception ex) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }
        if (CollectionUtils.isEmpty(list)) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }

        for (ToBeAssignLicensePlateNextDetailEnter o : list) {
            Long id = o.getId();
            String licensePlate = o.getLicensePlate().trim();

            // ???????????????????????????
            LambdaQueryWrapper<OpeCarDistribute> lqw = new LambdaQueryWrapper<>();
            lqw.eq(OpeCarDistribute::getDr, Constant.DR_FALSE);
            lqw.eq(OpeCarDistribute::getCustomerId, enter.getCustomerId());
            lqw.last("limit 1");
            OpeCarDistribute instance = opeCarDistributeMapper.selectOne(lqw);
            if (null != instance) {
                String plate = instance.getLicensePlate();
                if (StringUtils.isNotBlank(plate)) {
                    throw new SesWebRosException(ExceptionCodeEnums.STEP_HAS_INPUT_IN_APP.getCode(), ExceptionCodeEnums.STEP_HAS_INPUT_IN_APP.getMessage());
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

            // ????????????
            OpeCarDistribute model = new OpeCarDistribute();
            model.setId(id);
            model.setLicensePlate(licensePlate);
            model.setUpdatedBy(enter.getUserId());
            model.setUpdatedTime(new Date());
            opeCarDistributeMapper.updateById(model);
        }

        // node???node??????+1
        LambdaQueryWrapper<OpeCarDistributeNode> nodeWrapper = new LambdaQueryWrapper<>();
        nodeWrapper.eq(OpeCarDistributeNode::getDr, DelStatusEnum.VALID.getCode());
        nodeWrapper.eq(OpeCarDistributeNode::getCustomerId, enter.getCustomerId());
        OpeCarDistributeNode node = new OpeCarDistributeNode();
        node.setNode(3);
        node.setAppNode(3);
        node.setUpdatedBy(enter.getUserId());
        node.setUpdatedTime(new Date());
        opeCarDistributeNodeMapper.update(node, nodeWrapper);
        return new GeneralResult();
    }

    /**
     * ??????R.SN????????????
     */
    @Override
    public ToBeAssignColorResult getColorByRSN(StringEnter enter) {
        logger.info("??????R.SN????????????????????????:[{}]", enter);
        ToBeAssignColorResult result = new ToBeAssignColorResult();

        LambdaQueryWrapper<OpeInWhouseOrderSerialBind> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OpeInWhouseOrderSerialBind::getDr, DelStatusEnum.VALID.getCode());
        wrapper.eq(OpeInWhouseOrderSerialBind::getSerialNum, enter.getKeyword());
        wrapper.orderByDesc(OpeInWhouseOrderSerialBind::getCreatedTime);
        List<OpeInWhouseOrderSerialBind> list = opeInWhouseOrderSerialBindMapper.selectList(wrapper);
        if (CollectionUtils.isNotEmpty(list)) {
            OpeInWhouseOrderSerialBind model = list.get(0);
            Long productId = model.getProductId();
            if (null == productId) {
                throw new SesWebRosException(ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getMessage());
            }
            OpeProductionScooterBom bom = opeProductionScooterBomMapper.selectById(productId);
            Long colorId = bom.getColorId();
            if (null == colorId) {
                throw new SesWebRosException(ExceptionCodeEnums.COLOR_NOT_EXIST.getCode(), ExceptionCodeEnums.COLOR_NOT_EXIST.getMessage());
            }
            OpeColor color = opeColorMapper.selectById(colorId);
            if (null != color) {
                result.setColorId(color.getId());
                result.setColorName(color.getColorName());
                result.setColorValue(color.getColorValue());
                result.setRequestId(enter.getRequestId());
            }
        }
        return result;
    }

    /**
     * ?????????R.SN???????????????
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult submit(ToBeAssignSubmitEnter enter) {
        logger.info("?????????R.SN???????????????????????????:[{}]", enter);
        if (null == enter || StringUtils.isBlank(enter.getList())) {
            throw new SesWebRosException(ExceptionCodeEnums.RSN_NOT_EMPTY.getCode(), ExceptionCodeEnums.RSN_NOT_EMPTY.getMessage());
        }

        // ??????
        List<ToBeAssignSubmitDetailEnter> list;
        try {
            list = JSONArray.parseArray(enter.getList(), ToBeAssignSubmitDetailEnter.class);
        } catch (Exception ex) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }
        if (CollectionUtils.isEmpty(list)) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }

        for (ToBeAssignSubmitDetailEnter o : list) {
            Long id = o.getId();
            String rsn = o.getRsn().trim();
            Long colorId = o.getColorId();
            String bluetoothAddress = o.getBluetoothAddress().trim();
            String tabletSn = o.getTabletSn().trim();
            String bbi = o.getBbi().trim();
            String controller = o.getController().trim();
            String electricMachinery = o.getElectricMachinery().trim();
            String meter = o.getMeter().trim();
            String imei = o.getImei().trim();

            // ???????????????????????????
            LambdaQueryWrapper<OpeCarDistribute> scooterWrapper = new LambdaQueryWrapper<>();
            scooterWrapper.eq(OpeCarDistribute::getDr, Constant.DR_FALSE);
            scooterWrapper.eq(OpeCarDistribute::getCustomerId, enter.getCustomerId());
            scooterWrapper.last("limit 1");
            OpeCarDistribute instance = opeCarDistributeMapper.selectOne(scooterWrapper);
            if (null != instance) {
                if (StringUtils.isNotBlank(instance.getRsn()) || StringUtils.isNotBlank(instance.getTabletSn()) || StringUtils.isNotBlank(instance.getBluetoothAddress())) {
                    throw new SesWebRosException(ExceptionCodeEnums.STEP_HAS_INPUT_IN_APP.getCode(), ExceptionCodeEnums.STEP_HAS_INPUT_IN_APP.getMessage());
                }
            }

            LambdaQueryWrapper<OpeCarDistribute> lqw = new LambdaQueryWrapper<>();
            lqw.eq(OpeCarDistribute::getDr, Constant.DR_FALSE);
            lqw.eq(OpeCarDistribute::getRsn, rsn);
            lqw.last("limit 1");
            OpeCarDistribute checkModel = opeCarDistributeMapper.selectOne(lqw);
            if (null != checkModel) {
                throw new SesWebRosException(ExceptionCodeEnums.RSN_HAS_USED.getCode(), ExceptionCodeEnums.RSN_HAS_USED.getMessage());
            }

            // ??????rsn????????????????????????
            LambdaQueryWrapper<OpeCodebaseRsn> existWrapper = new LambdaQueryWrapper<>();
            existWrapper.eq(OpeCodebaseRsn::getDr, Constant.DR_FALSE);
            existWrapper.eq(OpeCodebaseRsn::getStatus, 1);
            existWrapper.eq(OpeCodebaseRsn::getRsn, rsn);
            int count = opeCodebaseRsnService.count(existWrapper);
            if (count == 0) {
                throw new SesWebRosException(ExceptionCodeEnums.RSN_NOT_EXISTS_CODEBASE.getCode(), ExceptionCodeEnums.RSN_NOT_EXISTS_CODEBASE.getMessage());
            }

            // ????????????
            OpeCarDistribute model = new OpeCarDistribute();
            model.setId(id);
            model.setRsn(rsn);
            model.setBluetoothAddress(bluetoothAddress);
            model.setTabletSn(tabletSn);
            model.setColorId(colorId);
            model.setBbi(bbi);
            model.setController(controller);
            model.setElectricMachinery(electricMachinery);
            model.setMeter(meter);
            model.setImei(imei);
            model.setUpdatedBy(enter.getUserId());
            model.setUpdatedTime(new Date());
            opeCarDistributeMapper.updateById(model);

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
            LambdaQueryWrapper<OpeCodebaseRelation> relationWrapper = new LambdaQueryWrapper<>();
            relationWrapper.eq(OpeCodebaseRelation::getDr, Constant.DR_FALSE);
            relationWrapper.eq(OpeCodebaseRelation::getVin, o.getVin());
            relationWrapper.eq(OpeCodebaseRelation::getStatus, 1);
            opeCodebaseRelationService.update(relation, relationWrapper);
        }

        // node???node??????+1
        LambdaQueryWrapper<OpeCarDistributeNode> nodeWrapper = new LambdaQueryWrapper<>();
        nodeWrapper.eq(OpeCarDistributeNode::getDr, DelStatusEnum.VALID.getCode());
        nodeWrapper.eq(OpeCarDistributeNode::getCustomerId, enter.getCustomerId());
        OpeCarDistributeNode node = new OpeCarDistributeNode();
        node.setNode(4);
        node.setAppNode(4);
        node.setUpdatedBy(enter.getUserId());
        node.setUpdatedTime(new Date());
        opeCarDistributeNodeMapper.update(node, nodeWrapper);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * ????????????
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult inputBattery(ToBeAssignInputBatteryEnter enter) {
        logger.info("????????????????????????:[{}]", enter);
        if (null == enter || StringUtils.isBlank(enter.getList())) {
            throw new SesWebRosException(ExceptionCodeEnums.BATTERY_NOT_EMPTY.getCode(), ExceptionCodeEnums.BATTERY_NOT_EMPTY.getMessage());
        }

        // ??????
        List<ToBeAssignInputBatteryDetailEnter> list;
        try {
            list = JSONArray.parseArray(enter.getList(), ToBeAssignInputBatteryDetailEnter.class);
        } catch (Exception ex) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }
        if (CollectionUtils.isEmpty(list)) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }

        // ????????????
        OpeCustomer opeCustomer = opeCustomerMapper.selectById(enter.getCustomerId());
        if (null == opeCustomer) {
            throw new SesWebRosException(ExceptionCodeEnums.CUSTOMER_NOT_EXIST.getCode(), ExceptionCodeEnums.CUSTOMER_NOT_EXIST.getMessage());
        }
        if (!StringUtils.equals(opeCustomer.getStatus(), CustomerStatusEnum.OFFICIAL_CUSTOMER.getValue())) {
            throw new SesWebRosException(ExceptionCodeEnums.CONVERT_TO_FORMAL_CUSTOMER_FIRST.getCode(), ExceptionCodeEnums.CONVERT_TO_FORMAL_CUSTOMER_FIRST.getMessage());
        }

        // ???????????????????????????(???pla_user???)
        QueryAccountResult accountInfo = accountBaseService.customerAccountDeatil(opeCustomer.getEmail());
        if (null == accountInfo) {
            throw new SesWebRosException(ExceptionCodeEnums.ACCOUNT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.ACCOUNT_IS_NOT_EXIST.getMessage());
        }

        // ?????????????????????????????????
        List<HubSaveScooterEnter> saveRelationList = Lists.newArrayList();
        boolean stockFlag = false;

        for (ToBeAssignInputBatteryDetailEnter o : list) {
            Long id = o.getId();
            String battery = o.getBattery().trim();

            // ???????????????????????????
            LambdaQueryWrapper<OpeCarDistribute> batteryWrapper = new LambdaQueryWrapper<>();
            batteryWrapper.eq(OpeCarDistribute::getDr, Constant.DR_FALSE);
            batteryWrapper.eq(OpeCarDistribute::getCustomerId, enter.getCustomerId());
            batteryWrapper.last("limit 1");
            OpeCarDistribute instance = opeCarDistributeMapper.selectOne(batteryWrapper);
            if (null != instance) {
                String cell = instance.getBattery();
                if (StringUtils.isNotBlank(cell)) {
                    throw new SesWebRosException(ExceptionCodeEnums.STEP_HAS_INPUT_IN_APP.getCode(), ExceptionCodeEnums.STEP_HAS_INPUT_IN_APP.getMessage());
                }
            }

            LambdaQueryWrapper<OpeCarDistribute> checkWrapper = new LambdaQueryWrapper<>();
            checkWrapper.eq(OpeCarDistribute::getDr, Constant.DR_FALSE);
            checkWrapper.like(OpeCarDistribute::getBattery, battery);
            checkWrapper.last("limit 1");
            OpeCarDistribute checkModel = opeCarDistributeMapper.selectOne(checkWrapper);
            if (null != checkModel) {
                throw new SesWebRosException(ExceptionCodeEnums.BATTERY_HAS_USED.getCode(), ExceptionCodeEnums.BATTERY_HAS_USED.getMessage());
            }

            // ????????????
            OpeCarDistribute obj = new OpeCarDistribute();
            obj.setId(id);
            obj.setBattery(battery);
            obj.setUpdatedBy(enter.getUserId());
            obj.setUpdatedTime(new Date());
            opeCarDistributeMapper.updateById(obj);

            // ???????????????
            OpeCarDistribute opeCarDistribute = opeCarDistributeMapper.selectById(id);
            String licensePlate = opeCarDistribute.getLicensePlate();
            String rsn = opeCarDistribute.getRsn();

            // ???????????????????????????
            // ?????????????????????id?????????id
            CustomerIdEnter customerIdEnter = new CustomerIdEnter();
            customerIdEnter.setCustomerId(enter.getCustomerId());
            Map<String, Long> map = getSpecificatIdAndColorId(customerIdEnter);
            Long specificatId = map.get("specificatId");
            Long inquiryColorId = map.get("colorId");

            // ?????????????????????????????????
            LambdaQueryWrapper<OpeWmsScooterStock> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(OpeWmsScooterStock::getDr, DelStatusEnum.VALID.getCode());
            wrapper.eq(OpeWmsScooterStock::getGroupId, specificatId);
            wrapper.eq(OpeWmsScooterStock::getColorId, inquiryColorId);
            wrapper.eq(OpeWmsScooterStock::getStockType, 2);
            wrapper.orderByDesc(OpeWmsScooterStock::getCreatedTime);
            List<OpeWmsScooterStock> stockList = opeWmsScooterStockMapper.selectList(wrapper);
            OpeWmsScooterStock stock = null;
            if (CollectionUtils.isNotEmpty(stockList)) {
                stock = stockList.get(0);
                // ????????????????????????????????????????????????????????????
                Long stockId = stock.getId();
                Integer ableStockQty = stock.getAbleStockQty();
                Integer usedStockQty = stock.getUsedStockQty();

                // ????????????????????????,????????????
                if (ableStockQty < 1) {
                    stockFlag = true;
                    break;
                }

                // ?????????????????????????????????-1,??????????????????+1
                OpeWmsScooterStock param = new OpeWmsScooterStock();
                param.setId(stockId);
                param.setAbleStockQty(ableStockQty - 1);
                param.setUsedStockQty(usedStockQty + 1);
                param.setUpdatedBy(enter.getUserId());
                param.setUpdatedTime(new Date());
                opeWmsScooterStockMapper.updateById(param);
            }

            // ??????????????????
            if (null != stock) {
                OpeWmsStockRecord record = new OpeWmsStockRecord();
                record.setId(idAppService.getId(SequenceName.OPE_WMS_STOCK_RECORD));
                record.setDr(DelStatusEnum.VALID.getCode());
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
            String specificatName = getSpecificatNameById(opeCarDistribute.getSpecificatTypeId());

            // ????????????
            // ??????rsn??????sco_scooter???
            ScoScooterResult scoScooter = scooterService.getScoScooterByTableSn(rsn);
            if (null == scoScooter) {
                throw new SesWebRosException(ExceptionCodeEnums.SCOOTER_NOT_EXIST.getCode(), ExceptionCodeEnums.SCOOTER_NOT_EXIST.getMessage());
            }
            Long scooterId = scoScooter.getId();

            // ??????sco_scooter?????????
            scooterService.updateScooterNo(scooterId, licensePlate);

            // ??????????????????corporate???(tob)
            logger.info("???????????????:[{}]", opeCustomer.getCustomerType());
            if (StringUtils.equals(opeCustomer.getCustomerType(), CustomerTypeEnum.ENTERPRISE.getValue())) {
                // ??????cor_tenant_scooter???
                HubSaveScooterEnter item = new HubSaveScooterEnter();
                item.setScooterId(scooterId);
                item.setModel(ScooterModelEnums.showValueByCode(specificatName));
                item.setLongitude(MapUtil.randomLonLat(Constant.lng));
                item.setLatitude(MapUtil.randomLonLat(Constant.lng));
                item.setLicensePlate(licensePlate);
                item.setLicensePlatePicture(null);
                item.setStatus(ScooterStatusEnums.AVAILABLE.getValue());
                item.setUserId(accountInfo.getId());
                item.setTenantId(accountInfo.getTenantId());
                saveRelationList.add(item);
                corporateScooterService.saveScooter(saveRelationList);
                logger.info("?????????????????????,??????corporate???");

                // ??????cor_driver???
                long driverId = idAppService.getId(SequenceName.OPE_CAR_DISTRIBUTE);
                CorDriver driver = new CorDriver();
                driver.setId(driverId);
                driver.setTenantId(accountInfo.getTenantId());
                driver.setUserId(accountInfo.getId());
                driver.setStatus("1");
                driver.setCreatedBy(enter.getUserId());
                driver.setCreatedTime(new Date());
                scooterMobileBService.addCorDriver(driver);

                // ??????cor_driver_scooter???
                CorDriverScooter scooter = new CorDriverScooter();
                scooter.setId(idAppService.getId(SequenceName.OPE_CAR_DISTRIBUTE));
                scooter.setTenantId(accountInfo.getTenantId());
                scooter.setDriverId(driverId);
                scooter.setScooterId(scooterId);
                scooter.setStatus("1");
                scooter.setBeginTime(new Date());
                scooter.setCreatedBy(enter.getUserId());
                scooter.setCreatedTime(new Date());
                scooterMobileBService.addCorDriverScooter(scooter);
            }
            // ??????????????????consumer???(toc)
            if (StringUtils.equals(opeCustomer.getCustomerType(), CustomerTypeEnum.PERSONAL.getValue())) {
                HubSaveScooterEnter item = new HubSaveScooterEnter();
                item.setScooterId(scooterId);
                item.setModel(ScooterModelEnums.showValueByCode(specificatName));
                item.setLongitude(MapUtil.randomLonLat(Constant.lng));
                item.setLatitude(MapUtil.randomLonLat(Constant.lng));
                item.setLicensePlate(licensePlate);
                item.setLicensePlatePicture(null);
                item.setStatus(ScooterStatusEnums.AVAILABLE.getValue());
                item.setUserId(accountInfo.getId());
                item.setTenantId(accountInfo.getTenantId());
                saveRelationList.add(item);
                cusotmerScooterService.saveScooter(saveRelationList);
                logger.info("?????????????????????,??????consumer???");
            }

            // ??????rsn?????????????????????????????????????????????????????????
            LambdaQueryWrapper<OpeWmsStockSerialNumber> qw = new LambdaQueryWrapper<>();
            qw.eq(OpeWmsStockSerialNumber::getDr, DelStatusEnum.VALID.getCode());
            qw.eq(OpeWmsStockSerialNumber::getRsn, rsn);
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

            // ??????oms????????????
            LambdaQueryWrapper<OpeCarDistribute> omsWrapper = new LambdaQueryWrapper<>();
            omsWrapper.eq(OpeCarDistribute::getDr, Constant.DR_FALSE);
            omsWrapper.eq(OpeCarDistribute::getCustomerId, enter.getCustomerId());
            omsWrapper.last("limit 1");
            OpeCarDistribute model = opeCarDistributeMapper.selectOne(omsWrapper);
            if (null == model) {
                throw new SesWebRosException(ExceptionCodeEnums.ASSIGN_SCOOTER_WRONG.getCode(), ExceptionCodeEnums.ASSIGN_SCOOTER_WRONG.getMessage());
            }

            // ????????????
            String modelName = getSpecificatNameById(model.getSpecificatTypeId());

            CustomerIdEnter customerEnter = new CustomerIdEnter();
            customerEnter.setCustomerId(enter.getCustomerId());
            Map<String, Long> inquiryMap = getSpecificatIdAndColorId(customerEnter);
            logger.info("?????????????????????????????????:[{}]", inquiryMap);
            Long groupId = inquiryMap.get("specificatId");
            Long colorId = inquiryMap.get("colorId");

            AdmScooter admScooter = scooterModelService.getScooterBySn(model.getTabletSn());
            if (null != admScooter) {
                throw new SesWebRosException(ExceptionCodeEnums.SN_ALREADY_EXISTS.getCode(), ExceptionCodeEnums.SN_ALREADY_EXISTS.getMessage());
            }
            logger.info("???????????????");
            SpecificGroupDTO group = specificService.getSpecificGroupById(groupId);
            ColorDTO color = colorService.getColorInfoById(colorId);
            if (null == group) {
                throw new SesWebRosException(ExceptionCodeEnums.GROUP_NOT_EXIST.getCode(), ExceptionCodeEnums.GROUP_NOT_EXIST.getMessage());
            }
            if (null == color) {
                throw new SesWebRosException(ExceptionCodeEnums.COLOR_NOT_EXIST.getCode(), ExceptionCodeEnums.COLOR_NOT_EXIST.getMessage());
            }

            // ??????adm_scooter???
            AdmScooter scooter = new AdmScooter();
            scooter.setId(idAppService.getId(SequenceName.OPE_CAR_DISTRIBUTE_NODE));
            scooter.setDr(Constant.DR_FALSE);
            scooter.setSn(model.getTabletSn());
            scooter.setGroupId(groupId);
            scooter.setColorId(colorId);
            scooter.setMacAddress(model.getBluetoothAddress());
            scooter.setScooterController(ScooterModelEnum.getScooterModelType(modelName));
            scooter.setCreatedBy(0L);
            scooter.setCreatedTime(new Date());
            scooter.setUpdatedBy(0L);
            scooter.setUpdatedTime(new Date());
            scooter.setColorName(color.getColorName());
            scooter.setColorValue(color.getColorValue());
            scooter.setGroupName(group.getGroupName());
            scooter.setMacName(model.getBluetoothAddress());
            scooterModelService.insertScooter(scooter);
            logger.info("??????adm_scooter?????????");

            // ?????????????????????(sn)?????????sco_scooter??????????????? ???????????????true ????????????false
            Boolean flag = scooterService.getSnIsExist(scooter.getSn());
            if (flag) {
                logger.info("sn???sco_scooter????????????");
                String scooterNo = generateScooterNo();
                scooterService.syncScooterData(buildData(scooter.getId(), scooter.getSn(), scooter.getScooterController(), enter.getUserId(), scooterNo));
            }
        }

        if (stockFlag) {
            throw new SesWebRosException(ExceptionCodeEnums.SCOOTER_STOCK_IS_NOT_ENOUGH.getCode(), ExceptionCodeEnums.SCOOTER_STOCK_IS_NOT_ENOUGH.getMessage());
        }

        /*---------------sim ????????????--------------*/
        LambdaQueryWrapper<OpeCarDistribute> qw = new LambdaQueryWrapper<>();
        qw.eq(OpeCarDistribute::getDr, Constant.DR_FALSE);
        qw.eq(OpeCarDistribute::getCustomerId, opeCustomer.getId());
        qw.last("limit 1");
        OpeCarDistribute opeCarDistribute = opeCarDistributeMapper.selectOne(qw);
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

        // node???node??????+1
        LambdaQueryWrapper<OpeCarDistributeNode> nodeWrapper = new LambdaQueryWrapper<>();
        nodeWrapper.eq(OpeCarDistributeNode::getDr, Constant.DR_FALSE);
        nodeWrapper.eq(OpeCarDistributeNode::getCustomerId, enter.getCustomerId());
        OpeCarDistributeNode node = new OpeCarDistributeNode();
        node.setNode(5);
        node.setAppNode(5);
        node.setFlag(2);
        node.setUpdatedBy(enter.getUserId());
        node.setUpdatedTime(new Date());
        opeCarDistributeNodeMapper.update(node, nodeWrapper);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * ?????????????????????????????????????????????
     */
    @Override
    public ToBeAssignNodeResult getNode(CustomerIdEnter enter) {
        logger.info("?????????????????????????????????????????????????????????:[{}]", enter);
        ToBeAssignNodeResult result = new ToBeAssignNodeResult();

        // ????????????id??????node???
        LambdaQueryWrapper<OpeCarDistributeNode> nodeWrapper = new LambdaQueryWrapper<>();
        nodeWrapper.eq(OpeCarDistributeNode::getDr, DelStatusEnum.VALID.getCode());
        nodeWrapper.eq(OpeCarDistributeNode::getCustomerId, enter.getCustomerId());
        nodeWrapper.orderByDesc(OpeCarDistributeNode::getCreatedTime);
        List<OpeCarDistributeNode> nodeList = opeCarDistributeNodeMapper.selectList(nodeWrapper);
        if (CollectionUtils.isEmpty(nodeList)) {
            throw new SesWebRosException(ExceptionCodeEnums.CUSTOMER_NOT_EXIST.getCode(), ExceptionCodeEnums.CUSTOMER_NOT_EXIST.getMessage());
        }
        OpeCarDistributeNode opeCarDistributeNode = nodeList.get(0);
        Integer node = opeCarDistributeNode.getNode();
        node = null == node ? 1 : node;

        // ????????????
        ToBeAssignDetailCustomerInfoResult customerInfo = opeCarDistributeExMapper.getCustomerInfo(enter.getCustomerId());
        if (null == customerInfo) {
            throw new SesWebRosException(ExceptionCodeEnums.CUSTOMER_NOT_EXIST.getCode(), ExceptionCodeEnums.CUSTOMER_NOT_EXIST.getMessage());
        }
        String customerType = customerInfo.getCustomerType();
        String industryType = customerInfo.getIndustryType();
        if (StringUtils.isNotBlank(customerType)) {
            customerInfo.setCustomerTypeMsg(CustomerFormEnum.showMsg(customerType));
        }
        if (StringUtils.isNotBlank(industryType)) {
            customerInfo.setIndustryTypeMsg(IndustryTypeEnum.showMsg(industryType));
        }

        // ????????????
        LambdaQueryWrapper<OpeCustomerInquiry> opeCustomerInquiryWrapper = new LambdaQueryWrapper<>();
        opeCustomerInquiryWrapper.eq(OpeCustomerInquiry::getDr, DelStatusEnum.VALID.getCode());
        opeCustomerInquiryWrapper.eq(OpeCustomerInquiry::getCustomerId, enter.getCustomerId());
        List<OpeCustomerInquiry> tempList = opeCustomerInquiryMapper.selectList(opeCustomerInquiryWrapper);
        if (CollectionUtils.isEmpty(tempList)) {
            throw new SesWebRosException(ExceptionCodeEnums.CUSTOMER_NOT_EXIST.getCode(), ExceptionCodeEnums.CUSTOMER_NOT_EXIST.getMessage());
        }

        List<ToBeAssignDetailScooterInfoResult> taskList = Lists.newArrayList();
        List<ToBeAssignDetailScooterInfoSubResult> taskSubList = Lists.newArrayList();
        Long inquiryId = null;

        for (OpeCustomerInquiry o : tempList) {
            inquiryId = o.getId();
            ToBeAssignDetailScooterInfoResult task = new ToBeAssignDetailScooterInfoResult();
            ToBeAssignDetailScooterInfoSubResult sub = new ToBeAssignDetailScooterInfoSubResult();

            Long productId = o.getProductId();
            if (null == productId) {
                throw new SesWebRosException(ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getMessage());
            }
            // ????????????id???ope_sale_scooter??????
            OpeSaleScooter opeSaleScooter = opeSaleScooterMapper.selectById(productId);
            if (null == opeSaleScooter) {
                throw new SesWebRosException(ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getMessage());
            }
            // ????????????id?????????id
            Long specificatId = opeSaleScooter.getSpecificatId();
            Long colorId = opeSaleScooter.getColorId();

            // ????????????id???ope_specificat_type??????
            if (null != specificatId) {
                String specificatName = getSpecificatNameById(specificatId);
                task.setSpecificatId(specificatId);
                task.setSpecificatName(specificatName);
            }

            // ????????????id???ope_color??????
            if (null != colorId) {
                Map<String, String> map = getColorNameAndValueById(colorId);
                sub.setColorName(map.get("colorName"));
                sub.setColorValue(map.get("colorValue"));
                sub.setColorId(colorId);
            }

            // ?????????????????????
            LambdaQueryWrapper<OpeCustomerInquiryB> lqw = new LambdaQueryWrapper<>();
            lqw.eq(OpeCustomerInquiryB::getDr, Constant.DR_FALSE);
            lqw.eq(OpeCustomerInquiryB::getInquiryId, inquiryId);
            lqw.last("limit 1");
            OpeCustomerInquiryB inquiryB = opeCustomerInquiryBService.getOne(lqw);
            if (null != inquiryB) {
                sub.setBatteryNum(inquiryB.getProductQty());
            }

            sub.setToBeAssignCount(o.getScooterQuantity());
            task.setTotalCount(o.getScooterQuantity());

            taskSubList.add(sub);
            task.setScooterList(taskSubList);
            taskList.add(task);
        }

        // ????????????,????????????????????????
        LambdaQueryWrapper<OpeCarDistribute> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OpeCarDistribute::getDr, DelStatusEnum.VALID.getCode());
        wrapper.eq(OpeCarDistribute::getCustomerId, enter.getCustomerId());
        List<OpeCarDistribute> list = opeCarDistributeMapper.selectList(wrapper);
        if (CollectionUtils.isEmpty(list)) {
            result.setNode(node);
            result.setCustomerInfo(customerInfo);
            result.setTaskInfo(taskList);
            return result;
        }

        List<ToBeAssignNodeScooterInfoResult> scooterList = Lists.newArrayList();
        List<ToBeAssignNodeScooterInfoSubResult> subList = Lists.newArrayList();

        Integer totalCount = 0;
        for (OpeCarDistribute model : list) {
            ToBeAssignNodeScooterInfoSubResult sub = new ToBeAssignNodeScooterInfoSubResult();
            sub.setId(model.getId());
            sub.setToBeAssignCount(model.getQty());
            if (null != model.getQty()) {
                totalCount += model.getQty();
            }

            sub.setSeatNumber(model.getSeatNumber());
            sub.setVinCode(model.getVinCode());
            sub.setLicensePlate(model.getLicensePlate());
            sub.setRsn(model.getRsn());

            // ?????????????????????
            LambdaQueryWrapper<OpeCustomerInquiryB> lqw = new LambdaQueryWrapper<>();
            lqw.eq(OpeCustomerInquiryB::getDr, Constant.DR_FALSE);
            lqw.eq(OpeCustomerInquiryB::getInquiryId, inquiryId);
            lqw.last("limit 1");
            OpeCustomerInquiryB inquiryB = opeCustomerInquiryBService.getOne(lqw);
            if (null != inquiryB) {
                sub.setBatteryNum(inquiryB.getProductQty());
            }

            // ??????customerId??????node,??????node??????appNode????????????3(????????????),????????????,????????????3,?????????
            LambdaQueryWrapper<OpeCarDistributeNode> qw = new LambdaQueryWrapper<>();
            qw.eq(OpeCarDistributeNode::getDr, Constant.DR_FALSE);
            qw.eq(OpeCarDistributeNode::getCustomerId, enter.getCustomerId());
            qw.last("limit 1");
            OpeCarDistributeNode nodeModel = opeCarDistributeNodeMapper.selectOne(qw);
            if (null != nodeModel) {
                if (nodeModel.getNode() > 3 || nodeModel.getAppNode() > 3) {
                    if (null != model.getColorId()) {
                        Map<String, String> map = getColorNameAndValueById(model.getColorId());
                        sub.setColorId(model.getColorId());
                        sub.setColorName(map.get("colorName"));
                        sub.setColorValue(map.get("colorValue"));
                    }
                }
            }
            /*if (null != model.getColorId()) {
                Map<String, String> map = getColorNameAndValueById(model.getColorId());
                sub.setColorId(model.getColorId());
                sub.setColorName(map.get("colorName"));
                sub.setColorValue(map.get("colorValue"));
            }*/

            sub.setQty(model.getQty());
            sub.setBluetoothAddress(model.getBluetoothAddress());
            sub.setTabletSn(model.getTabletSn());
            if (StringUtils.isBlank(model.getBattery())) {
                sub.setBatteryList(new ArrayList<>());
            } else {
                String[] split = model.getBattery().split(",");
                List<String> batteryList = new ArrayList<>(Arrays.asList(split));
                batteryList.removeAll(Collections.singleton(null));
                sub.setBatteryList(batteryList);
            }
            subList.add(sub);

            ToBeAssignNodeScooterInfoResult scooter = new ToBeAssignNodeScooterInfoResult();
            if (null != model.getSpecificatTypeId()) {
                String specificatName = getSpecificatNameById(model.getSpecificatTypeId());
                scooter.setSpecificatId(model.getSpecificatTypeId());
                scooter.setSpecificatName(specificatName);
            }
            scooterList.add(scooter);
            scooter.setScooterList(subList);
            scooter.setTotalCount(totalCount);
        }

        result.setNode(node);
        result.setCustomerInfo(customerInfo);
        result.setScooterInfo(scooterList);
        result.setTaskInfo(taskList);
        result.setRequestId(enter.getRequestId());
        return result;
    }

    /**
     * ????????????????????????????????????tab????????????
     */
    @Override
    public Map<String, Object> getTabCount(GeneralEnter enter) {
        Map<String, Object> result = Maps.newHashMapWithExpectedSize(3);

        // ?????????????????????
        int toBeAssignCount = opeCarDistributeExMapper.getToBeAssignListCount(new ToBeAssignListEnter());
        /*List<ToBeAssignListResult> list = opeCarDistributeExMapper.getToBeAssignListNoPage(enter);
        // ?????????????????????????????????????????????????????????????????????
        if (CollectionUtils.isNotEmpty(list)) {
            Iterator<ToBeAssignListResult> iterator = list.iterator();
            while (iterator.hasNext()) {
                ToBeAssignListResult next = iterator.next();
                String email = next.getEmail();
                // ??????email???pla_user?????????,????????????,????????????????????? flag:????????????true,???????????????false
                Boolean flag = assignScooterService.getPlaUserIsExistByEmail(email);
                if (!flag) {
                    iterator.remove();
                }
            }
        }*/
        // ?????????????????????
        int assignedCount = opeCarDistributeExMapper.getAssignedListCount(new AssignedListEnter());
        // ?????????????????????
        int doingCount = opeCarDistributeExMapper.getDoingListCount(new ToBeAssignListEnter());
        result.put("toBeAssignCount", toBeAssignCount);
        result.put("assignedCount", assignedCount);
        result.put("doingCount", doingCount);
        return result;
    }

    /**
     * ??????????????????????????????????????????
     */
    @Override
    public BooleanResult checkScooterStock(CustomerIdEnter enter) {
        logger.info("??????????????????????????????????????????????????????:[{}]", enter);
        BooleanResult result = new BooleanResult();

        // ????????????????????????????????????
        Integer scooterQuantity = 0;
        LambdaQueryWrapper<OpeCustomerInquiry> inquiryWrapper = new LambdaQueryWrapper<>();
        inquiryWrapper.eq(OpeCustomerInquiry::getDr, DelStatusEnum.VALID.getCode());
        inquiryWrapper.eq(OpeCustomerInquiry::getCustomerId, enter.getCustomerId());
        List<OpeCustomerInquiry> inquiryList = opeCustomerInquiryMapper.selectList(inquiryWrapper);
        if (CollectionUtils.isNotEmpty(inquiryList)) {
            OpeCustomerInquiry customerInquiry = inquiryList.get(0);
            scooterQuantity = customerInquiry.getScooterQuantity();
        }

        // ?????????????????????id?????????id
        Map<String, Long> map = getSpecificatIdAndColorId(enter);
        Long specificatId = map.get("specificatId");
        Long colorId = map.get("colorId");

        // ????????????????????????????????????????????????????????????
        LambdaQueryWrapper<OpeWmsScooterStock> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OpeWmsScooterStock::getDr, DelStatusEnum.VALID.getCode());
        wrapper.eq(OpeWmsScooterStock::getGroupId, specificatId);
        wrapper.eq(OpeWmsScooterStock::getColorId, colorId);
        wrapper.eq(OpeWmsScooterStock::getStockType, 2);
        wrapper.orderByDesc(OpeWmsScooterStock::getCreatedTime);
        List<OpeWmsScooterStock> list = opeWmsScooterStockMapper.selectList(wrapper);
        if (CollectionUtils.isEmpty(list)) {
            result.setSuccess(Boolean.FALSE); // ??????
            //result.setSuccess(Boolean.TRUE); //??????
            return result;
        }
        OpeWmsScooterStock scooterStock = list.get(0);
        Integer ableStockQty = scooterStock.getAbleStockQty();
        ableStockQty = null == ableStockQty ? 0 : ableStockQty;

        // ????????????????????????????????????????????????????????????
        if (scooterQuantity > ableStockQty) {
            result.setSuccess(Boolean.FALSE);
        } else if (scooterQuantity <= ableStockQty) {
            result.setSuccess(Boolean.TRUE);
        }
        result.setRequestId(enter.getRequestId());
        return result;
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
     * ???????????????????????????????????????????????????
     */
    /*@Override
    public BooleanResult checkOperation(CustomerIdEnter enter) {
        BooleanResult result = new BooleanResult();
        result.setSuccess(Boolean.TRUE);

        LambdaQueryWrapper<OpeCarDistributeNode> qw = new LambdaQueryWrapper<>();
        qw.eq(OpeCarDistributeNode::getDr, Constant.DR_FALSE);
        qw.eq(OpeCarDistributeNode::getCustomerId, enter.getCustomerId());
        qw.last("limit 1");
        OpeCarDistributeNode node = opeCarDistributeNodeMapper.selectOne(qw);
        if (null != node) {
            Integer flag = node.getFlag();
            if (flag == 1 || flag == 2) {
                result.setSuccess(Boolean.FALSE);
            }
        }
        result.setRequestId(enter.getRequestId());
        return result;
    }*/

    /**
     * ????????????id?????????????????????id?????????id
     */
    public Map<String, Long> getSpecificatIdAndColorId(CustomerIdEnter enter) {
        Map<String, Long> result = Maps.newHashMapWithExpectedSize(2);
        // ????????????????????????id
        LambdaQueryWrapper<OpeCustomerInquiry> opeCustomerInquiryWrapper = new LambdaQueryWrapper<>();
        opeCustomerInquiryWrapper.eq(OpeCustomerInquiry::getDr, DelStatusEnum.VALID.getCode());
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

        // ????????????id???ope_sale_scooter??????
        OpeSaleScooter opeSaleScooter = opeSaleScooterMapper.selectById(productId);
        if (null == opeSaleScooter) {
            throw new SesWebRosException(ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getMessage());
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

    /**
     * ????????????id???????????????????????????
     */
    public Map<String, String> getColorNameAndValueById(Long colorId) {
        if (null == colorId) {
            throw new SesWebRosException(ExceptionCodeEnums.COLOR_ID_NOT_EMPTY.getCode(), ExceptionCodeEnums.COLOR_ID_NOT_EMPTY.getMessage());
        }
        Map<String, String> map = Maps.newHashMapWithExpectedSize(2);
        OpeColor color = opeColorMapper.selectById(colorId);
        if (null != color) {
            String colorName = color.getColorName();
            String colorValue = color.getColorValue();
            if (StringUtils.isNotBlank(colorName)) {
                map.put("colorName", colorName);
            }
            if (StringUtils.isNotBlank(colorValue)) {
                map.put("colorValue", colorValue);
            }
            return map;
        }
        throw new SesWebRosException(ExceptionCodeEnums.COLOR_NOT_EXIST.getCode(), ExceptionCodeEnums.COLOR_NOT_EXIST.getMessage());
    }

    /**
     * ?????????????????????????????????
     */
    public String generateRangeRandom() {
        String[] array = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "X"};
        int index = (int) (Math.random() * array.length);
        return array[index];
    }

    /**
     * ??????Vin???????????????????????????vin???
     * @param vin
     * @return
     */
    public static String checkVIN(String vin) {
        Map vinMapWeighting = null;

        Map vinMapValue = null;

        vinMapWeighting = new HashMap();

        vinMapValue = new HashMap();

        vinMapWeighting.put(1, 8);

        vinMapWeighting.put(2, 7);

        vinMapWeighting.put(3, 6);

        vinMapWeighting.put(4, 5);

        vinMapWeighting.put(5, 4);

        vinMapWeighting.put(6, 3);

        vinMapWeighting.put(7, 2);

        vinMapWeighting.put(8, 10);

        vinMapWeighting.put(9, 0);

        vinMapWeighting.put(10, 9);

        vinMapWeighting.put(11, 8);

        vinMapWeighting.put(12, 7);

        vinMapWeighting.put(13, 6);

        vinMapWeighting.put(14, 5);

        vinMapWeighting.put(15, 4);

        vinMapWeighting.put(16, 3);

        vinMapWeighting.put(17, 2);

        vinMapValue.put('0', 0);

        vinMapValue.put('1', 1);

        vinMapValue.put('2', 2);

        vinMapValue.put('3', 3);

        vinMapValue.put('4', 4);

        vinMapValue.put('5', 5);

        vinMapValue.put('6', 6);

        vinMapValue.put('7', 7);

        vinMapValue.put('8', 8);

        vinMapValue.put('9', 9);

        vinMapValue.put('A', 1);

        vinMapValue.put('B', 2);

        vinMapValue.put('C', 3);

        vinMapValue.put('D', 4);

        vinMapValue.put('E', 5);

        vinMapValue.put('F', 6);

        vinMapValue.put('G', 7);

        vinMapValue.put('H', 8);

        vinMapValue.put('J', 1);

        vinMapValue.put('K', 2);

        vinMapValue.put('M', 4);

        vinMapValue.put('L', 3);

        vinMapValue.put('N', 5);

        vinMapValue.put('P', 7);

        vinMapValue.put('R', 9);

        vinMapValue.put('S', 2);

        vinMapValue.put('T', 3);

        vinMapValue.put('U', 4);

        vinMapValue.put('V', 5);

        vinMapValue.put('W', 6);

        vinMapValue.put('X', 7);

        vinMapValue.put('Y', 8);

        vinMapValue.put('Z', 9);

        boolean reultFlag = false;

        String uppervin = vin.toUpperCase();

//????????????O???I

        if (vin == null || uppervin.indexOf("O") >= 0 || uppervin.indexOf("I") >= 0) {
            reultFlag = false;

        } else {
//1:?????????17

            if (vin.length() == 17) {
                int amount = 0;
                char[] vinArr = uppervin.toCharArray();
                for (int i = 0; i < vinArr.length; i++) {

//VIN???????????????????????????????????????????????????????????????????????????????????17?????????????????????
                    Object o = vinMapValue.get(vinArr[i]);
                    Object o2 = vinMapWeighting.get(i + 1);
                    amount += Integer.parseInt(o==null?"":o.toString()) * Integer.parseInt(o2==null?"":o2.toString());

                }
                if (amount % 11 == 10) {
                   return "X";
                } else {
                    int result = amount%11;
                    return String.valueOf(result);
                }
            }
        }
        return null;
    }



    /**
     * ??????VIN Code
     */
    public String generateVINCode(Long specificatId, String specificatName, Integer seatNumber) {
        String msg = "VXS";
        StringBuffer result = new StringBuffer();

        // ?????????????????????????????????
        result.append(msg);
        result.append(ScooterTypeEnum.R2A.getCode());

        // ???????????????????????????
        String productType = ProductTypeEnum.showCode(specificatName);
        result.append(productType);
        result.append(seatNumber);

        // ???????????????
        String random = generateRangeRandom();
        result.append(random);

        // ???????????????????????????
        Calendar cal = Calendar.getInstance();
        String year = String.valueOf(cal.get(Calendar.YEAR));
        String value = YearEnum.showValue(year);
        result.append(value);
        result.append(FactoryEnum.AOGE.getCode());

        // 6?????????????????????
        List<Integer> codeList = Lists.newArrayList();
        LambdaQueryWrapper<OpeCarDistribute> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OpeCarDistribute::getSpecificatTypeId, specificatId);
        wrapper.eq(OpeCarDistribute::getSeatNumber, seatNumber);
        List<OpeCarDistribute> list = opeCarDistributeMapper.selectList(wrapper);
        if (CollectionUtils.isNotEmpty(list)) {
            // ??????????????????,????????????6???????????????
            for (OpeCarDistribute o : list) {
                String vinCode = o.getVinCode();
                if (StringUtils.isNotBlank(vinCode)) {
                    String sub = vinCode.substring(vinCode.length() - 6);
                    codeList.add(Integer.valueOf(sub));
                }
            }
            if (CollectionUtils.isNotEmpty(codeList)) {
                // ????????????
                codeList.sort(Comparator.reverseOrder());
                NumberFormat nf = NumberFormat.getInstance();
                nf.setGroupingUsed(false);
                nf.setMaximumIntegerDigits(6);
                nf.setMinimumIntegerDigits(6);
                String code = nf.format(new Double(codeList.get(0) + 1));
                result.append(code);
            } else {
                result.append("000001");
            }
        } else {
            result.append("000001");
        }
        //???????????????vin??????????????????????????????
        String nineCode = checkVIN(result.toString());
        //?????????????????????????????????????????????????????????????????????
        result.replace(8,9, nineCode);
        return result.toString();
    }


    /**
     * ??????105???SSN
     */
    @Override
    public List<String> testGenerateVINCode(GeneralEnter enter) {
        Integer[] array = {1, 2};
        List<String> list = Lists.newArrayList();
        for (int i = 0; i < 400; i++) {
            int index = (int) (Math.random() * array.length);
            Integer seat = array[index];
            String s = show(Long.valueOf("1006236"), "E50", 2, i + 1);
            list.add(s);
        }
        return list;
    }

    /**
     * @param specificatId   ????????????id
     * @param specificatName ????????????
     * @param seatNumber     ??????????????????
     * @param i
     * @return
     */
    @Override
    public String show(Long specificatId, String specificatName, Integer seatNumber, int i) {
        String msg = "VXS";
        StringBuffer result = new StringBuffer();

        // ?????????????????????????????????
        result.append(msg);
        result.append(ScooterTypeEnum.R2A.getCode());

        // ???????????????????????????
        String productType = ProductTypeEnum.showCode(specificatName);
        result.append(productType);
        result.append(seatNumber);

        // ???????????????
        String random = generateRangeRandom();
        result.append(random);

        // ???????????????????????????
        Calendar cal = Calendar.getInstance();
        String year = String.valueOf(cal.get(Calendar.YEAR));
        String value = YearEnum.showValue(year);
        result.append(value);
        result.append(FactoryEnum.AOGE.getCode());

        /*if (i < 10) {
            result.append("00000" + i);
        } else if (i < 100) {
            result.append("0000" + i);
        } else if (i < 1000) {
            result.append("000" + i);
        }*/

        List<Integer> codeList = Lists.newArrayList();
        LambdaQueryWrapper<OpeCarDistribute> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OpeCarDistribute::getSpecificatTypeId, specificatId);
        wrapper.eq(OpeCarDistribute::getSeatNumber, seatNumber);
        List<OpeCarDistribute> list = opeCarDistributeMapper.selectList(wrapper);
        if (CollectionUtils.isNotEmpty(list)) {
            // ??????????????????,????????????6???????????????
            for (OpeCarDistribute o : list) {
                String vinCode = o.getVinCode();
                String sub = vinCode.substring(vinCode.length() - 6);
                codeList.add(Integer.valueOf(sub));
            }
            // ????????????
            codeList.sort(Comparator.reverseOrder());
            NumberFormat nf = NumberFormat.getInstance();
            nf.setGroupingUsed(false);
            nf.setMaximumIntegerDigits(6);
            nf.setMinimumIntegerDigits(6);
            String code = nf.format(new Double(codeList.get(0) + 1));
            result.append(code);
        } else {
            result.append("000001");
        }
        //???????????????vin??????????????????????????????
        String nineCode = checkVIN(result.toString());

        //?????????????????????????????????????????????????????????????????????
        result.replace(8,9, nineCode);
        logger.info(result.toString()+"{result>>>>>>>>>>>>>>>>>>>>>>>}");
        // ???????????????
        OpeCarDistribute model = new OpeCarDistribute();
        model.setTenantId(1L);
        model.setUserId(1L);
        model.setCustomerId(1L);
        model.setSpecificatTypeId(specificatId);
        model.setSeatNumber(seatNumber);
        model.setCreatedBy(1L);
        model.setVinCode(result.toString());
        model.setCreatedTime(new Date());
        opeCarDistributeMapper.insert(model);
        return result.toString();
    }

}
