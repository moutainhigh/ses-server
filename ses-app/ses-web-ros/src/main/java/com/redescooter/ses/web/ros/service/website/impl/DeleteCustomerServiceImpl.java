package com.redescooter.ses.web.ros.service.website.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.enums.customer.CustomerSourceEnum;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.web.ros.dm.OpeCustomer;
import com.redescooter.ses.web.ros.dm.OpeCustomerInquiry;
import com.redescooter.ses.web.ros.dm.OpeCustomerInquiryB;
import com.redescooter.ses.web.ros.dm.OpeSysUser;
import com.redescooter.ses.web.ros.dm.OpeSysUserProfile;
import com.redescooter.ses.web.ros.service.base.OpeCustomerInquiryBService;
import com.redescooter.ses.web.ros.service.base.OpeCustomerInquiryService;
import com.redescooter.ses.web.ros.service.base.OpeCustomerService;
import com.redescooter.ses.web.ros.service.base.OpeSysUserProfileService;
import com.redescooter.ses.web.ros.service.base.OpeSysUserService;
import com.redescooter.ses.web.ros.service.website.DeleteService;
import com.redescooter.ses.web.ros.vo.website.StorageEamilEnter;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    private OpeSysUserProfileService opeSysUserProfileService;

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
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult deleteCustomer(StorageEamilEnter email) {
        OpeCustomer opeCustomer = opeCustomerService.getOne(new LambdaQueryWrapper<OpeCustomer>()
                .eq(OpeCustomer::getId, email.getId())
                .eq(OpeCustomer::getDr, Constant.DR_FALSE));
        if (opeCustomer == null) {
            return new GeneralResult(email.getRequestId());
        }
        /**
         * 1. 验证客户是否真实
         * 2. 验证客户是否有订单
         * 3. 有订单，删除失败，先删除订单，后删除客户
         * 4. 无订单，直接删除即可
         * **/
        List<OpeCustomerInquiry> inquiryList = opeCustomerInquiryService.list(new LambdaQueryWrapper<OpeCustomerInquiry>()
                .eq(OpeCustomerInquiry::getCustomerId, opeCustomer.getId())
                .eq(OpeCustomerInquiry::getDr, Constant.DR_FALSE));
        if (inquiryList.size() > 0) {
            inquiryList.forEach(inquiry -> {
                deleteOrder(inquiry);
            });
        }

        OpeSysUser opeSysUser = opeSysUserService.getOne(new LambdaQueryWrapper<OpeSysUser>()
                .eq(OpeSysUser::getLoginName, opeCustomer.getEmail())
                .eq(OpeSysUser::getDr, Constant.DR_FALSE)
                .eq(OpeSysUser::getDef1, CustomerSourceEnum.WEBSITE.getValue()));

        if (opeSysUser != null) {
            List<OpeSysUserProfile> profileList = opeSysUserProfileService.list(new LambdaQueryWrapper<OpeSysUserProfile>()
                    .eq(OpeSysUserProfile::getSysUserId, opeSysUser.getId())
                    .eq(OpeSysUserProfile::getDr, Constant.DR_FALSE));
            if (profileList.size() > 0) {
                profileList.forEach(profile -> {
                    opeSysUserProfileService.removeById(profile.getId());
                });
            }
            opeSysUserService.removeById(opeSysUser.getId());
        }
        opeCustomerService.removeById(opeCustomer.getId());
        return new GeneralResult(email.getRequestId());
    }


    /**
     * 删除联系我们询价单
     *
     * @param enter
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult deleteInquiry(StorageEamilEnter enter) {
        OpeCustomerInquiry opeCustomerInquiry =
                opeCustomerInquiryService.getOne(new LambdaQueryWrapper<OpeCustomerInquiry>()
                        .eq(OpeCustomerInquiry::getId, enter.getId())
                        .eq(OpeCustomerInquiry::getDr, Constant.DR_FALSE));
        if (opeCustomerInquiry != null) {
            deleteOrder(opeCustomerInquiry);
        }

        return new GeneralResult(enter.getRequestId());
    }

    @GlobalTransactional(rollbackFor = Exception.class)
    private void deleteOrder(OpeCustomerInquiry inquiry) {
        /**先删除自订单，在删除主订单**/
        List<OpeCustomerInquiryB> list = opeCustomerInquiryBService.list(new LambdaQueryWrapper<OpeCustomerInquiryB>()
                .eq(OpeCustomerInquiryB::getInquiryId, inquiry.getId())
                .eq(OpeCustomerInquiryB::getDr, Constant.DR_FALSE));
        if (list != null) {
            list.forEach(inquiryb -> {
                opeCustomerInquiryBService.removeById(inquiryb.getId());
            });
        }
        /**主订单删除**/
        opeCustomerInquiryService.removeById(inquiry.getId());

    }
}
