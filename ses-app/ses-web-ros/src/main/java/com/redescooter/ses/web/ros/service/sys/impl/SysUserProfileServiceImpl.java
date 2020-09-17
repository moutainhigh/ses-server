package com.redescooter.ses.web.ros.service.sys.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.ros.dm.OpeSysStaff;
import com.redescooter.ses.web.ros.dm.OpeSysUserProfile;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpeSysStaffService;
import com.redescooter.ses.web.ros.service.base.OpeSysUserProfileService;
import com.redescooter.ses.web.ros.service.sys.SysUserProfileService;
import com.redescooter.ses.web.ros.vo.sys.user.UserInfoResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassNameUserProfileServiceImpl
 * @Description
 * @Author Joan
 * @Date2020/4/27 18:47
 * @Version V1.0
 **/
@Slf4j
@Service
public class SysUserProfileServiceImpl implements SysUserProfileService {
    @Autowired
    OpeSysUserProfileService opeSysUserProfileService;

    @Autowired
    private OpeSysStaffService opeSysStaffService;
    /**
     * 用户信息
     *
     * @param enter
     * @return
     */
    @Override
    public UserInfoResult userInfo(GeneralEnter enter) {

//        QueryWrapper<OpeSysUserProfile> queryWrapper = new QueryWrapper();
//        queryWrapper.eq(OpeSysUserProfile.COL_SYS_USER_ID,enter.getUserId());
//        queryWrapper.last("limit 1");
//        OpeSysUserProfile user = opeSysUserProfileService.getOne(queryWrapper);
//        if (user==null){
//            throw new SesWebRosException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(), ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
//        }
//
//        return UserInfoResult.builder()
//                .id(user.getId())
//                .address(user.getAddress())
//                .addressBureau(user.getAddressBureau())
//                .addressCountryCode(user.getAddressCountryCode())
//                .birthday(user.getBirthday())
//                .certificateNegativeAnnex(user.getCertificateNegativeAnnex())
//                .email(user.getEmail())
//                .certificateType(user.getCertificateType())
//                .firstName(user.getFirstName())
//                .lastName(user.getLastName())
//                .fullName(user.getFullName())
//                .gender(user.getGender())
//                .picture(user.getPicture())
//                .placeBirth(user.getPlaceBirth())
//                .telNumber(user.getTelNumber())
//                .sysUserId(user.getSysUserId())
//                .countryCode(user.getCountryCode())
//                .build();
        QueryWrapper<OpeSysStaff> qw = new QueryWrapper();
        qw.eq(OpeSysStaff.COL_ID,enter.getUserId());
        qw.last("limit 1");
        OpeSysStaff staff = opeSysStaffService.getOne(qw);
        if (staff == null){
            throw new SesWebRosException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(), ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }
        UserInfoResult result = new UserInfoResult();
        result.setId(staff.getId());
        result.setSysUserId(staff.getId());
        result.setPicture(staff.getEmployeePicture());
        result.setFirstName(staff.getFirstName());
        result.setLastName(staff.getLastName());
        result.setFullName(staff.getFullName());
        result.setEmail(staff.getEmail());
        result.setTelNumber(staff.getTelephone());
        result.setGender(staff.getGender()==null?"1":staff.getGender()+"");
        result.setBirthday(staff.getBirthday());
        result.setCertificateType(staff.getCertificateType()==null?"1":staff.getCertificateType().toString());
        result.setCertificateNegativeAnnex(staff.getCertificatPicture1());
        result.setCertificatePositiveAnnex(staff.getCertificatPicture2());
        return result;
    }

}
