package com.redescooter.ses.web.ros.controller;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.web.ros.service.sys.SysUserProfileService;
import com.redescooter.ses.web.ros.vo.sys.user.UserInfoResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassNameUserProfileController
 * @Description
 * @Author Joan
 * @Date2020/4/27 18:44
 * @Version V1.0
 **/

@Api(tags = {"ROS-User"})
@CrossOrigin
@RestController
@RequestMapping(value = "/Info")
public class UserProfileController{

    @Autowired
    private SysUserProfileService sysUserProfileService;

    @ApiOperation(value = "获取用户个人信息", response = UserInfoResult.class)
    @PostMapping(value = "/getUserInfo")
    public Response<UserInfoResult> userInfo(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(sysUserProfileService.userInfo(enter));
    }
}
