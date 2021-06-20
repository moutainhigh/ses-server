package com.redescooter.ses.web.ros.service.sys.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.constant.JedisConstant;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.ros.constant.StringManaConstant;
import com.redescooter.ses.web.ros.dm.OpeSysStaff;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpeSysStaffService;
import com.redescooter.ses.web.ros.service.base.OpeSysUserProfileService;
import com.redescooter.ses.web.ros.service.sys.SysUserProfileService;
import com.redescooter.ses.web.ros.vo.sys.user.UserInfoResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

import java.util.Map;

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

    @Autowired
    private JedisCluster jedisCluster;

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
        qw.eq(OpeSysStaff.COL_ID, enter.getUserId());
        qw.last("limit 1");
        OpeSysStaff staff = opeSysStaffService.getOne(qw);
        if (StringManaConstant.entityIsNull(staff)) {
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
        result.setGender(staff.getGender() == null ? "1" : staff.getGender() + "");
        result.setBirthday(staff.getBirthday());
        result.setCertificateType(staff.getCertificateType() == null ? "1" : staff.getCertificateType().toString());
        result.setCertificateNegativeAnnex(staff.getCertificatPicture1());
        result.setCertificatePositiveAnnex(staff.getCertificatPicture2());
        // 从缓存获取 需不需要重置密码（第一次登陆）
        boolean flag = false;
        String key = JedisConstant.FIRST_LOGIN_RESET_PSD + staff.getId();
        if (jedisCluster.exists(key)) {
            // 如果缓存存在
            Map<String, String> map = jedisCluster.hgetAll(key);
            String value = map.get("flag");
            if ("1".equals(value)) {
                flag = true;
            }
            jedisCluster.del(key);
        }
        result.setResetPsd(flag);
        return result;
    }

}
