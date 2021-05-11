package com.redescooter.ses.web.ros.service.assign.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.dao.assign.OpeCarDistributeExMapper;
import com.redescooter.ses.web.ros.dao.assign.OpeCarDistributeMapper;
import com.redescooter.ses.web.ros.dm.OpeCarDistribute;
import com.redescooter.ses.web.ros.enums.assign.CustomerFormEnum;
import com.redescooter.ses.web.ros.enums.assign.IndustryTypeEnum;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.assign.AssigningService;
import com.redescooter.ses.web.ros.vo.assign.doing.result.AssigningDetailResult;
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
     * 处理中列表
     */
    @Override
    public PageResult<AssigningListResult> getList(ToBeAssignListEnter enter) {
        int count = opeCarDistributeExMapper.getDoingListCount(enter);
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        List<AssigningListResult> list = opeCarDistributeExMapper.getDoingList(enter);
        return PageResult.create(enter, count, list);
    }

    /**
     * 处理中查看详情
     */
    @Override
    public AssigningDetailResult getDetail(CustomerIdEnter enter) {
        AssigningDetailResult result = new AssigningDetailResult();
        List<AssigningDetailScooterResult> scooterList = Lists.newArrayList();

        // 客户信息
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

        // 车辆信息
        LambdaQueryWrapper<OpeCarDistribute> qw = new LambdaQueryWrapper<>();
        qw.eq(OpeCarDistribute::getDr, Constant.DR_FALSE);
        qw.eq(OpeCarDistribute::getCustomerId, enter.getCustomerId());
        List<OpeCarDistribute> list = opeCarDistributeMapper.selectList(qw);
        if (CollectionUtils.isEmpty(list)) {
            throw new SesWebRosException(ExceptionCodeEnums.ASSIGN_INFO_NOT_EXIST.getCode(), ExceptionCodeEnums.ASSIGN_INFO_NOT_EXIST.getMessage());
        }

        for (OpeCarDistribute o : list) {
            AssigningDetailScooterResult scooter = new AssigningDetailScooterResult();
            scooter.setSeatNumber(o.getSeatNumber());
            scooter.setVinCode(o.getVinCode());
            scooter.setLicensePlate(o.getLicensePlate());
            scooter.setRsn(o.getRsn());
            if (null != o.getSpecificatTypeId()) {
                scooter.setSpecificatName(toBeAssignServiceImpl.getSpecificatNameById(o.getSpecificatTypeId()));
            }
            if (null != o.getColorId()) {
                Map<String, String> map = toBeAssignServiceImpl.getColorNameAndValueById(o.getColorId());
                scooter.setColorName(map.get("colorName"));
                scooter.setColorValue(map.get("colorValue"));
            }
            scooter.setBluetoothAddress(o.getBluetoothAddress());
            scooter.setTabletSn(o.getTabletSn());

            // 电池
            String battery = o.getBattery();
            if (StringUtils.isBlank(battery)) {
                scooter.setBatteryNum(0);
                scooter.setBatteryList(new ArrayList<>());
            } else {
                String[] split = battery.split(",");
                List<String> batteryList = new ArrayList<>(Arrays.asList(split));
                batteryList.removeAll(Collections.singleton(null));
                scooter.setBatteryNum(batteryList.size());
                scooter.setBatteryList(batteryList);
            }
            scooterList.add(scooter);
        }
        result.setCustomerInfo(customerInfo);
        result.setScooterInfo(scooterList);
        result.setRequestId(enter.getRequestId());
        return result;
    }

}
