package com.redescooter.ses.web.ros.service.sys.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.ros.dm.OpeSysUserProfile;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
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
    /**
     * 用户信息
     *
     * @param enter
     * @return
     */
    @Override
    public UserInfoResult userInfo(GeneralEnter enter) {

        QueryWrapper<OpeSysUserProfile> queryWrapper = new QueryWrapper();
        queryWrapper.eq(OpeSysUserProfile.COL_SYS_USER_ID,enter.getUserId());
        queryWrapper.last("limit 1");
        OpeSysUserProfile user = opeSysUserProfileService.getOne(queryWrapper);
        if (user==null){
            throw new SesWebRosException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(), ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }

        return UserInfoResult.builder()
                .id(user.getId())
                .address(user.getAddress())
                .addressBureau(user.getAddressBureau())
                .addressCountryCode(user.getAddressCountryCode())
                .birthday(user.getBirthday())
                .certificateNegativeAnnex(user.getCertificateNegativeAnnex())
                .email(user.getEmail())
                .certificateType(user.getCertificateType())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .fullName(user.getFullName())
                .gender(user.getGender())
                .picture(user.getPicture())
                .placeBirth(user.getPlaceBirth())
                .telNumber(user.getTelNumber())
                .sysUserId(user.getSysUserId())
                .countryCode(user.getCountryCode())
                .build();
    }

}
