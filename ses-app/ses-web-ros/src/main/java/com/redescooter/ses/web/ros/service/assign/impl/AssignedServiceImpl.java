package com.redescooter.ses.web.ros.service.assign.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.tool.utils.SesStringUtils;
import com.redescooter.ses.web.ros.dao.assign.OpeCarDistributeExMapper;
import com.redescooter.ses.web.ros.dao.assign.OpeCarDistributeMapper;
import com.redescooter.ses.web.ros.dm.OpeCarDistribute;
import com.redescooter.ses.web.ros.enums.assign.CustomerTypeEnum;
import com.redescooter.ses.web.ros.enums.assign.IndustryTypeEnum;
import com.redescooter.ses.web.ros.enums.distributor.DelStatusEnum;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.assign.AssignedService;
import com.redescooter.ses.web.ros.vo.assign.done.enter.AssignedListEnter;
import com.redescooter.ses.web.ros.vo.assign.done.result.AssignedDetailResult;
import com.redescooter.ses.web.ros.vo.assign.done.result.AssignedListResult;
import com.redescooter.ses.web.ros.vo.assign.tobe.enter.CustomerIdEnter;
import com.redescooter.ses.web.ros.vo.assign.tobe.result.ToBeAssignDetailCustomerInfoResult;
import com.redescooter.ses.web.ros.vo.assign.tobe.result.ToBeAssignNextStopDetailResult;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * @Description 车辆已分配ServiceImpl
 * @Author Chris
 * @Date 2020/12/27 16:37
 */
@Service
public class AssignedServiceImpl implements AssignedService {

    private static final Logger logger = LoggerFactory.getLogger(AssignedServiceImpl.class);

    @Autowired
    private OpeCarDistributeMapper opeCarDistributeMapper;

    @Autowired
    private OpeCarDistributeExMapper opeCarDistributeExMapper;

    @Autowired
    private ToBeAssignServiceImpl toBeAssignServiceImpl;

    /**
     * 已分配列表
     */
    @Override
    public PageResult<AssignedListResult> getAssignedList(AssignedListEnter enter) {
        logger.info("已分配列表的入参是:[{}]", enter);
        SesStringUtils.objStringTrim(enter);

        int count = opeCarDistributeExMapper.getAssignedListCount(enter);
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        List<AssignedListResult> list = opeCarDistributeExMapper.getAssignedList(enter);
        return PageResult.create(enter, count, list);
    }

    /**
     * 已分配列表查看详情
     */
    @Override
    public AssignedDetailResult getAssignedDetail(CustomerIdEnter enter) {
        AssignedDetailResult result = new AssignedDetailResult();
        List<ToBeAssignNextStopDetailResult> scooterInfoList = Lists.newArrayList();
        ToBeAssignNextStopDetailResult scooterInfo = new ToBeAssignNextStopDetailResult();

        // 客户信息
        ToBeAssignDetailCustomerInfoResult customerInfo = opeCarDistributeExMapper.getCustomerInfo(enter.getCustomerId());
        if (null == customerInfo) {
            throw new SesWebRosException(ExceptionCodeEnums.CUSTOMER_NOT_EXIST.getCode(), ExceptionCodeEnums.CUSTOMER_NOT_EXIST.getMessage());
        }
        String customerType = customerInfo.getCustomerType();
        String industryType = customerInfo.getIndustryType();
        if (StringUtils.isNotBlank(customerType)) {
            customerInfo.setCustomerTypeMsg(CustomerTypeEnum.showMsg(customerType));
        }
        if (StringUtils.isNotBlank(industryType)) {
            customerInfo.setIndustryTypeMsg(IndustryTypeEnum.showMsg(industryType));
        }

        // 车辆信息
        LambdaQueryWrapper<OpeCarDistribute> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OpeCarDistribute::getDr, DelStatusEnum.VALID.getCode());
        wrapper.eq(OpeCarDistribute::getCustomerId, enter.getCustomerId());
        List<OpeCarDistribute> list = opeCarDistributeMapper.selectList(wrapper);
        if (CollectionUtils.isEmpty(list)) {
            throw new SesWebRosException(ExceptionCodeEnums.ASSIGN_INFO_NOT_EXIST.getCode(), ExceptionCodeEnums.ASSIGN_INFO_NOT_EXIST.getMessage());
        }
        OpeCarDistribute carDistribute = list.get(0);
        scooterInfo.setId(carDistribute.getId());
        scooterInfo.setSpecificatName(toBeAssignServiceImpl.getSpecificatNameById(carDistribute.getSpecificatTypeId()));
        scooterInfo.setSeatNumber(carDistribute.getSeatNumber());
        scooterInfo.setVinCode(carDistribute.getVinCode());
        scooterInfo.setLicensePlate(carDistribute.getLicensePlate());
        scooterInfo.setRsn(carDistribute.getRsn());
        Map<String, String> map = toBeAssignServiceImpl.getColorNameAndValueById(carDistribute.getColorId());
        scooterInfo.setColorName(map.get("colorName"));
        scooterInfo.setColorValue(map.get("colorValue"));

        scooterInfoList.add(scooterInfo);
        result.setCustomerInfo(customerInfo);
        result.setScooterInfo(scooterInfoList);
        result.setRequestId(enter.getRequestId());
        return result;
    }

    /**
     * 已分配列表生成PDF
     */
    @Override
    public GeneralResult generatePDF(IdEnter enter) {
        return null;
    }

}
