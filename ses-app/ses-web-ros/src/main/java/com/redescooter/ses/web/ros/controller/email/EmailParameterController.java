package com.redescooter.ses.web.ros.controller.email;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.IntEnter;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.api.foundation.vo.mail.MailConfigOfTermResult;
import com.redescooter.ses.api.foundation.vo.mail.MailTemplateConfigResult;
import com.redescooter.ses.api.foundation.vo.mail.QueryMailConfigEnter;
import com.redescooter.ses.api.foundation.vo.mail.SaveMailConfigEnter;
import com.redescooter.ses.web.ros.service.email.EmailParameterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Author jerry
 * @Date 2021/2/3 1:16 下午
 * @Description 邮件管理模板
 **/
@Api(tags = {"邮件参数管理"})
@CrossOrigin
@RestController
@RequestMapping(value = "/email/param")
public class EmailParameterController {

    @Autowired
    private EmailParameterService emailParameterService;

    @ApiOperation(value = "保存邮件参数", response = GeneralResult.class)
    @PostMapping(value = "/save")
    public Response<GeneralResult> save(@ModelAttribute @ApiParam("请求参数") SaveMailConfigEnter enter) {
        return new Response<>(emailParameterService.save(enter));
    }

    @ApiOperation(value = "删除邮件参数", response = GeneralResult.class)
    @PostMapping(value = "/delete")
    public Response<GeneralResult> delete(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(emailParameterService.delete(enter));
    }

    @PostMapping(value = "/list")
    @ApiOperation(value = "邮件参数列表", response = MailTemplateConfigResult.class)
    public Response<MailTemplateConfigResult> list(@ModelAttribute @ApiParam("请求参数") QueryMailConfigEnter enter) {
        return new Response<>(emailParameterService.list(enter));
    }


}
