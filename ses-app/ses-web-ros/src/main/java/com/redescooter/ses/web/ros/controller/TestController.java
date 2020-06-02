package com.redescooter.ses.web.ros.controller;

import com.redescooter.ses.api.common.annotation.IgnoreLoginCheck;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.api.common.vo.base.StringEnter;
import com.redescooter.ses.web.ros.service.sys.SysUserProfileService;
import com.redescooter.ses.web.ros.vo.account.AddSysUserEnter;
import com.redescooter.ses.web.ros.vo.sys.user.UserInfoResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
public class TestController {

    @IgnoreLoginCheck
    @ApiOperation(value = "测试1", response = GeneralEnter.class)
    @PostMapping(value = "/test")
    public Response<GeneralEnter> test(@ModelAttribute @ApiParam("请求参数") AddSysUserEnter enter) {

        System.out.println(enter);
        return new Response<>(enter);
    }

    @IgnoreLoginCheck
    @ApiOperation(value = "测试2", response = GeneralEnter.class)
    @PostMapping(value = "/test1")
    public void test1(@RequestBody @ApiParam("请求参数") AddSysUserEnter enter) {
        System.out.println(enter);
    }

    @IgnoreLoginCheck
    @ApiOperation(value = "测试3", response = GeneralEnter.class)
    @GetMapping(value = "/test2")
    public void test2(@ApiParam("请求参数") AddSysUserEnter enter) {
        System.out.println(enter);
    }
}
