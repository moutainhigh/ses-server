package com.redescooter.ses.mobile.client.controller;

import com.redescooter.ses.api.common.enums.user.UserServiceTypeEnum;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.api.hub.common.UserProfileService;
import com.redescooter.ses.api.hub.service.customer.ConsumerUserProfileService;
import com.redescooter.ses.api.hub.vo.QueryUserProfileResult;
import com.redescooter.ses.api.hub.vo.SaveUserProfileHubEnter;
import com.redescooter.ses.api.mobile.b.service.UserProfileMobileService;
import com.redescooter.ses.api.mobile.b.vo.SaveUserProfileEnter;
import com.redescooter.ses.api.mobile.b.vo.UserProfileResult;
import com.redescooter.ses.mobile.client.config.UserComponent;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @ClassName:UserProfileController
 * @description: UserProfileController
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/30 16:19
 */
@Slf4j
@Api(tags = {"个人信息模块"})
@CrossOrigin
@RestController
@RequestMapping(value = "/userProfile", method = RequestMethod.POST)
public class UserProfileMobileController {

    @DubboReference
    private UserProfileMobileService userProfileMobileService;

    @Resource
    private UserComponent userComponent;

    @DubboReference
    private ConsumerUserProfileService consumerUserProfileService;

    @DubboReference
    private UserProfileService userProfileService;

    @ApiOperation(value = "个人信息")
    @RequestMapping(value = "/detail")
    public Response<UserProfileResult> userProfile(@ModelAttribute GeneralEnter enter) {
        Integer userServiceType = userComponent.getUserServiceTypeById(enter);
        UserProfileResult profileResult = new UserProfileResult();
        if (UserServiceTypeEnum.B.getType().equals(userServiceType)) {
            log.info("用户类型为ToB");
            profileResult = userProfileMobileService.userProfile(enter);
        } else if (UserServiceTypeEnum.C.getType().equals(userServiceType)) {
            log.info("用户类型为ToC");
            IdEnter idEnter = new IdEnter();
            BeanUtils.copyProperties(enter, idEnter);
            idEnter.setId(enter.getUserId());
            QueryUserProfileResult toCProfile = consumerUserProfileService.queryUserProfile(idEnter);
            if (toCProfile != null) {
                BeanUtils.copyProperties(toCProfile, profileResult);
            }
        }
        return new Response<>(profileResult);
    }

    @ApiOperation(value = "个人信息修改")
    @RequestMapping(value = "/updateUserProfile")
    public Response<GeneralResult> updateUserProfile(@ModelAttribute SaveUserProfileEnter enter) {
        Integer userServiceType = userComponent.getUserServiceTypeById(enter);
        UserProfileResult profileResult = new UserProfileResult();
        if (UserServiceTypeEnum.B.getType().equals(userServiceType)) {
            log.info("用户类型为ToB");
            // 如果是ToB  还是走之前的逻辑
            userProfileMobileService.saveUserProfile(enter);
        } else if (UserServiceTypeEnum.C.getType().equals(userServiceType)) {
            // 更新2C用户信息
            log.info("用户类型为ToC");
            SaveUserProfileHubEnter saveUserProfileHubEnter = new SaveUserProfileHubEnter();
            BeanUtils.copyProperties(enter, saveUserProfileHubEnter);
            saveUserProfileHubEnter.setId(enter.getId());
            userProfileService.saveUserProfile2C(saveUserProfileHubEnter);
            // 回传ros 数据
        }
        return new Response<>(new GeneralResult(enter.getRequestId()));
    }

}
