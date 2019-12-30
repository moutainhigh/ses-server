package com.redescooter.ses.mobile.client.controller;

import com.redescooter.ses.api.common.annotation.IgnoreLoginCheck;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.api.mobile.b.service.UserProfileMobileService;
import com.redescooter.ses.api.mobile.b.vo.SaveUserProfileEnter;
import com.redescooter.ses.api.mobile.b.vo.UserProfileResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

    @Reference
    private UserProfileMobileService userProfileMobileService;

    @IgnoreLoginCheck
    @ApiOperation(value = "个人信息")
    @RequestMapping(value = "/detail")
    public Response<UserProfileResult> userProfile(@ModelAttribute GeneralEnter enter) {
        enter.setUserId(1060938L);
        enter.setTenantId(1060980L);
        return new Response<>(userProfileMobileService.userProfile(enter));
    }

    @IgnoreLoginCheck
    @ApiOperation(value = "个人信息修改")
    @RequestMapping(value = "/updateUserProfile")
    public Response<GeneralResult> updateUserProfile(@ModelAttribute SaveUserProfileEnter enter) {
        enter.setUserId(1060938L);
        enter.setTenantId(1060980L);
        return new Response<>(userProfileMobileService.saveUserProfile(enter));
    }

}
