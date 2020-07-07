package com.redescooter.ses.web.ros.service.website.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.web.ros.dm.OpeCustomer;
import com.redescooter.ses.web.ros.dm.OpeCustomerInquiry;
import com.redescooter.ses.web.ros.dm.OpeCustomerInquiryB;
import com.redescooter.ses.web.ros.dm.OpeSysUser;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpeCustomerInquiryBService;
import com.redescooter.ses.web.ros.service.base.OpeCustomerInquiryService;
import com.redescooter.ses.web.ros.service.base.OpeCustomerService;
import com.redescooter.ses.web.ros.service.base.OpeSysUserService;
import com.redescooter.ses.web.ros.service.website.DeleteService;
import com.redescooter.ses.web.ros.vo.website.StorageEamilEnter;
import lombok.extern.log4j.Log4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName:DeleteCustomerServiceImpl
 * @description: DeleteCustomerServiceImpl
 * @author: Alex
 * @Version：1.3
 * @create: 2020/07/07 13:12
 */
@Log4j
@Service
public class DeleteCustomerServiceImpl implements DeleteService {

    @Autowired
    private OpeCustomerService opeCustomerService;

    @Autowired
    private OpeSysUserService opeSysUserService;

    @Autowired
    private OpeCustomerInquiryService opeCustomerInquiryService;

    @Autowired
    private OpeCustomerInquiryBService opeCustomerInquiryBService;

    /**
     * 删除客户
     *
     * @param email
     * @return
     */
    @Override
    public GeneralResult deleteCustomer(StorageEamilEnter email) {
        OpeCustomer opeCustomer = opeCustomerService.getOne(new LambdaQueryWrapper<OpeCustomer>().eq(OpeCustomer::getEmail, email.getEmail()).eq(OpeCustomer::getDr, 0));
        if (opeCustomer == null) {
            throw new SesWebRosException(ExceptionCodeEnums.CUSTOMER_NOT_EXIST.getCode(), ExceptionCodeEnums.CUSTOMER_NOT_EXIST.getMessage());
        }
        //删除客户
        opeCustomerService.removeById(opeCustomer.getId());

        OpeSysUser opeSysUser = opeSysUserService.getOne(new LambdaQueryWrapper<OpeSysUser>().eq(OpeSysUser::getLoginName, email.getEmail()).eq(OpeSysUser::getDr, 0));
        if (opeSysUser == null) {
            throw new SesWebRosException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(),ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }
        //删除用户
        opeSysUserService.removeById(opeSysUser.getId());

        List<OpeCustomerInquiry> customerInquiryList = opeCustomerInquiryService.list(new LambdaQueryWrapper<OpeCustomerInquiry>().eq(OpeCustomerInquiry::getCustomerId, opeCustomer.getId()));
        if (CollectionUtils.isNotEmpty(customerInquiryList)){
            List<OpeCustomerInquiryB> customerInquiryBList = opeCustomerInquiryBService.list(new LambdaQueryWrapper<OpeCustomerInquiryB>().in(OpeCustomerInquiryB::getInquiryId,
                    customerInquiryList.stream().map(OpeCustomerInquiry::getId).collect(Collectors.toList())));
            if (CollectionUtils.isNotEmpty(customerInquiryBList)){
                //删除子表数据
                opeCustomerInquiryBService.removeByIds(customerInquiryBList.stream().map(OpeCustomerInquiryB::getId).collect(Collectors.toList()));
            }
            //删除询价单主表数据
            opeCustomerInquiryService.removeByIds(customerInquiryList.stream().map(OpeCustomerInquiry::getId).collect(Collectors.toList()));
        }
        return new GeneralResult();
    }
}
