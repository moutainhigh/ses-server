package com.redescooter.ses.web.ros.service.assign.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.constant.StringManaConstant;
import com.redescooter.ses.web.ros.dao.assign.OpeCarDistributeExMapper;
import com.redescooter.ses.web.ros.dao.assign.OpeCarDistributeMapper;
import com.redescooter.ses.web.ros.dm.OpeCarDistribute;
import com.redescooter.ses.web.ros.enums.assign.CustomerFormEnum;
import com.redescooter.ses.web.ros.enums.assign.IndustryTypeEnum;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.assign.AssigningService;
import com.redescooter.ses.web.ros.utils.NumberUtil;
import com.redescooter.ses.web.ros.vo.assign.doing.result.AssigningDetailResult;
import com.redescooter.ses.web.ros.vo.assign.doing.result.AssigningDetailScooterInfoResult;
import com.redescooter.ses.web.ros.vo.assign.doing.result.AssigningDetailScooterResult;
import com.redescooter.ses.web.ros.vo.assign.doing.result.AssigningListResult;
import com.redescooter.ses.web.ros.vo.assign.tobe.enter.CustomerIdEnter;
import com.redescooter.ses.web.ros.vo.assign.tobe.enter.ToBeAssignListEnter;
import com.redescooter.ses.web.ros.vo.assign.tobe.result.ToBeAssignDetailCustomerInfoResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author Chris
 * @Date 2021/5/11 12:49
 */
@Service
@Slf4j
public class AssigningServiceImpl implements AssigningService {

    @Autowired
    private OpeCarDistributeExMapper opeCarDistributeExMapper;

    @Autowired
    private OpeCarDistributeMapper opeCarDistributeMapper;

    @Autowired
    private ToBeAssignServiceImpl toBeAssignServiceImpl;

    /**
     * ???????????????
     */
    @Override
    public PageResult<AssigningListResult> getList(ToBeAssignListEnter enter) {
        int count = opeCarDistributeExMapper.getDoingListCount(enter);
        if (NumberUtil.eqZero(count)) {
            return PageResult.createZeroRowResult(enter);
        }
        List<AssigningListResult> list = opeCarDistributeExMapper.getDoingList(enter);
        return PageResult.create(enter, count, list);
    }

    /**
     * ?????????????????????
     */
    @Override
    public AssigningDetailResult getDetail(CustomerIdEnter enter) {
        AssigningDetailResult result = new AssigningDetailResult();
        List<AssigningDetailScooterResult> scooterInfo = Lists.newArrayList();

        // ????????????
        ToBeAssignDetailCustomerInfoResult customerInfo = opeCarDistributeExMapper.getCustomerInfo(enter.getCustomerId());
        if (StringManaConstant.entityIsNull(customerInfo)) {
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
        LambdaQueryWrapper<OpeCarDistribute> qw = new LambdaQueryWrapper<>();
        qw.eq(OpeCarDistribute::getDr, Constant.DR_FALSE);
        qw.eq(OpeCarDistribute::getCustomerId, enter.getCustomerId());
        List<OpeCarDistribute> list = opeCarDistributeMapper.selectList(qw);
        if (CollectionUtils.isEmpty(list)) {
            throw new SesWebRosException(ExceptionCodeEnums.ASSIGN_INFO_NOT_EXIST.getCode(), ExceptionCodeEnums.ASSIGN_INFO_NOT_EXIST.getMessage());
        }

        for (OpeCarDistribute o : list) {
            AssigningDetailScooterResult scooter = new AssigningDetailScooterResult();
            scooter.setTotalCount(1);
            if (StringManaConstant.entityIsNotNull(o.getSpecificatTypeId())) {
                scooter.setSpecificatId(o.getSpecificatTypeId());
                scooter.setSpecificatName(toBeAssignServiceImpl.getSpecificatNameById(o.getSpecificatTypeId()));
            }

            // ????????????
            List<AssigningDetailScooterInfoResult> scooterList = Lists.newArrayList();
            AssigningDetailScooterInfoResult model = new AssigningDetailScooterInfoResult();
            model.setId(o.getId());
            model.setSeatNumber(o.getSeatNumber());
            model.setVinCode(o.getVinCode());
            model.setLicensePlate(o.getLicensePlate());
            model.setRsn(o.getRsn());
            model.setBluetoothAddress(o.getBluetoothAddress());
            model.setTabletSn(o.getTabletSn());
            if (StringManaConstant.entityIsNotNull(o.getColorId())) {
                Map<String, String> map = toBeAssignServiceImpl.getColorNameAndValueById(o.getColorId());
                model.setColorName(map.get("colorName"));
                model.setColorValue(map.get("colorValue"));
            }
            // ??????
            String battery = o.getBattery();
            if (StringUtils.isBlank(battery)) {
                model.setBatteryNum(0);
                model.setBatteryList(new ArrayList<>());
            } else {
                String[] split = battery.split(",");
                List<String> batteryList = new ArrayList<>(Arrays.asList(split));
                batteryList.removeAll(Collections.singleton(null));
                model.setBatteryNum(batteryList.size());
                model.setBatteryList(batteryList);
            }
            scooterList.add(model);
            scooter.setScooterList(scooterList);
            scooterInfo.add(scooter);
        }
        result.setCustomerInfo(customerInfo);
        result.setScooterInfo(scooterInfo);
        result.setRequestId(enter.getRequestId());
        return result;
    }

}
