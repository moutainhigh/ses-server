package com.redescooter.ses.web.ros.controller.email;

import com.redescooter.ses.api.common.annotation.AvoidDuplicateSubmit;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IntEnter;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.api.foundation.vo.mail.MailConfigOfTermResult;
import com.redescooter.ses.api.foundation.vo.mail.SaveMailConfigEnter;
import com.redescooter.ses.web.ros.service.email.EmailParameterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author jerry
 * @Date 2021/2/3 1:16 下午
 * @Description 邮件管理模板
 **/
@Api(tags = {"邮件参数管理"})
@CrossOrigin
@RestController
@RequestMapping(value = "/email/parameter")
public class EmailParameterController {

    @Autowired
    private EmailParameterService emailParameterService;

    @ApiOperation(value = "保存邮件参数", response = GeneralResult.class)
    @PostMapping(value = "/save")
    @AvoidDuplicateSubmit
    public Response<GeneralResult> save(@RequestBody @ApiParam("请求参数") SaveMailConfigEnter enter) {
        return new Response<>(emailParameterService.save(enter));
    }

    @PostMapping(value = "/list")
    @ApiOperation(value = "邮件参数列表", response = MailConfigOfTermResult.class)
    public Response<List<MailConfigOfTermResult>> list(@ModelAttribute @ApiParam("请求参数") IntEnter enter) {
        return new Response<>(emailParameterService.list(enter));
    }
}
