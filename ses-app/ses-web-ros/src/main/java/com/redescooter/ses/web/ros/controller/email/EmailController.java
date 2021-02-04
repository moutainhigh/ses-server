package com.redescooter.ses.web.ros.controller.email;

import com.redescooter.ses.api.common.annotation.AvoidDuplicateSubmit;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.api.foundation.vo.mail.MailTemplateResult;
import com.redescooter.ses.api.foundation.vo.mail.SaveMailTemplateEnter;
import com.redescooter.ses.api.foundation.vo.mail.UpdateMailTemplateEnter;
import com.redescooter.ses.web.ros.service.email.EmailService;
import com.redescooter.ses.web.ros.vo.email.EmailListEnter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author jerry
 * @Date 2021/2/3 1:16 下午
 * @Description 邮件管理模板
 **/
@Api(tags = {"邮件管理模板"})
@CrossOrigin
@RestController
@RequestMapping(value = "/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @ApiOperation(value = "保存邮件模板", response = GeneralResult.class)
    @PostMapping(value = "/save")
    @AvoidDuplicateSubmit
    public Response<GeneralResult> save(@ModelAttribute @ApiParam("请求参数") SaveMailTemplateEnter enter) {
        return new Response<>(emailService.save(enter));
    }

    @ApiOperation(value = "更新邮件模板", response = GeneralResult.class)
    @PostMapping(value = "/update")
    @AvoidDuplicateSubmit
    public Response<GeneralResult> update(@ModelAttribute @ApiParam("请求参数") UpdateMailTemplateEnter enter) {
        return new Response<>(emailService.save(enter));
    }

    @PostMapping(value = "/delete")
    @ApiOperation(value = "删除邮件模板", response = GeneralResult.class)
    public Response<GeneralResult> delete(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(emailService.delete(enter));
    }

    @PostMapping(value = "/list")
    @ApiOperation(value = "邮件模板列表", response = MailTemplateResult.class)
    public Response<List<MailTemplateResult>> list(@ModelAttribute @ApiParam("请求参数") EmailListEnter enter) {
        return new Response<>(emailService.getList(enter));
    }

}
