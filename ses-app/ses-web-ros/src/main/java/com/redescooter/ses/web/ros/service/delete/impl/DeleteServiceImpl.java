package com.redescooter.ses.web.ros.service.delete.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.foundation.service.base.UserBaseService;
import com.redescooter.ses.api.mobile.c.service.UserProfileProService;
import com.redescooter.ses.web.ros.dao.assign.OpeCarDistributeMapper;
import com.redescooter.ses.web.ros.dao.assign.OpeCarDistributeNodeMapper;
import com.redescooter.ses.web.ros.dao.base.OpeCustomerContactMapper;
import com.redescooter.ses.web.ros.dao.base.OpeCustomerInquiryBMapper;
import com.redescooter.ses.web.ros.dao.base.OpeCustomerInquiryMapper;
import com.redescooter.ses.web.ros.dao.base.OpeCustomerMapper;
import com.redescooter.ses.web.ros.dao.delete.DeleteMapper;
import com.redescooter.ses.web.ros.dm.OpeCarDistribute;
import com.redescooter.ses.web.ros.dm.OpeCarDistributeNode;
import com.redescooter.ses.web.ros.dm.OpeCustomer;
import com.redescooter.ses.web.ros.dm.OpeCustomerContact;
import com.redescooter.ses.web.ros.dm.OpeCustomerInquiry;
import com.redescooter.ses.web.ros.dm.OpeCustomerInquiryB;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.delete.DeleteService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author Chris
 * @Date 2021/6/9 11:46
 */
@Service
@Slf4j
public class DeleteServiceImpl implements DeleteService {

    @Autowired
    private OpeCustomerMapper opeCustomerMapper;
    @DubboReference
    private UserProfileProService userProfileProService;
    @Autowired
    private OpeCustomerContactMapper opeCustomerContactMapper;
    @Autowired
    private OpeCustomerInquiryMapper opeCustomerInquiryMapper;
    @Autowired
    private OpeCustomerInquiryBMapper opeCustomerInquiryBMapper;
    @Autowired
    private OpeCarDistributeMapper opeCarDistributeMapper;
    @Autowired
    private OpeCarDistributeNodeMapper opeCarDistributeNodeMapper;
    @Autowired
    private DeleteMapper deleteMapper;
    @Autowired
    private UserBaseService userBaseService;


    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult deleteCustomer(IdEnter idEnter) {
        OpeCustomer opeCustomer = opeCustomerMapper.selectById(idEnter.getId());
        if (opeCustomer == null) {
            throw new SesWebRosException(ExceptionCodeEnums.CUSTOMER_NOT_EXIST.getCode(), ExceptionCodeEnums.CUSTOMER_NOT_EXIST.getMessage());
        }
        //删除con_user_profile这个表的信息和con_user_scooter这个表的信息
        deleteConUser(opeCustomer.getEmail());

        deleteMapper.deleteCustomer(idEnter.getId());

        //开始删除ope_customer，ope_customer_contact，ope_customer_inquiry，ope_customer_inquiry_b这几张表的信息
        LambdaQueryWrapper<OpeCustomerContact> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OpeCustomerContact::getCustomerId, idEnter.getId());
        OpeCustomerContact opeCustomerContact = opeCustomerContactMapper.selectOne(wrapper);
        if (opeCustomerContact != null) {
            deleteMapper.deleteCustomerContact(opeCustomer.getId());
        }

        LambdaQueryWrapper<OpeCustomerInquiry> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OpeCustomerInquiry::getCustomerId, idEnter.getId());
        OpeCustomerInquiry opeCustomerInquiry = opeCustomerInquiryMapper.selectOne(queryWrapper);
        if (opeCustomerInquiry != null) {
            deleteMapper.deleteCustomerInquiry(opeCustomer.getId());
        }

        LambdaQueryWrapper<OpeCustomerInquiryB> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(OpeCustomerInquiryB::getInquiryId, opeCustomerInquiry.getId());
        OpeCustomerInquiryB opeCustomerInquiryB = opeCustomerInquiryBMapper.selectOne(lambdaQueryWrapper);
        if (opeCustomerInquiryB != null) {
            deleteMapper.deleteCustomerInquiryB(opeCustomerInquiry.getId());
        }

        //开始删除ope_car_distribute，ope_car_distribute_node
        LambdaQueryWrapper<OpeCarDistribute> wrapper1 = new LambdaQueryWrapper<>();
        wrapper1.eq(OpeCarDistribute::getCustomerId, idEnter.getId());
        OpeCarDistribute opeCarDistribute = opeCarDistributeMapper.selectOne(wrapper1);
        if (opeCarDistribute != null) {
            deleteMapper.deleteCarDistribute(idEnter.getId());
        }

        LambdaQueryWrapper<OpeCarDistributeNode> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(OpeCarDistributeNode::getCustomerId, idEnter.getId());
        OpeCarDistributeNode opeCarDistributeNode = opeCarDistributeNodeMapper.selectOne(queryWrapper1);
        if (opeCarDistribute != null) {
            deleteMapper.deleteCarDistributeNode(idEnter.getId());
        }

        //删除platform库里面的pla_user，pla_user_node，pla_user_password，pla_user_permission
        deletePlaUser(opeCustomer.getEmail());
        return new GeneralResult(idEnter.getRequestId());
    }

    public void deleteConUser(String email) {
        userProfileProService.deleteUserProfile(email);
    }


    public void deletePlaUser(String email) {
        userBaseService.deletePlaUser(email);
    }
}
