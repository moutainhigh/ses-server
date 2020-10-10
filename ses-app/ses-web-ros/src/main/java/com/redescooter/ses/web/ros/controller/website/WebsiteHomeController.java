package com.redescooter.ses.web.ros.controller.website;

import com.redescooter.ses.api.common.annotation.IgnoreLoginCheck;
import com.redescooter.ses.api.common.annotation.WebsiteSignIn;
import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.web.ros.service.customer.InquiryService;
import com.redescooter.ses.web.ros.service.website.WebsiteOrderFormService;
import com.redescooter.ses.web.ros.vo.website.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName:WebsiteInquiryController
 * @description: WebsiteInquiryController
 * @author: Alex
 * @Version：1.3
 * @create: 2020/05/12 15:51
 */
@Log4j2
@Api(tags = {"Homepage of official website"})
@CrossOrigin
@RestController
@RequestMapping(value = "/website")
public class WebsiteHomeController {

    @Autowired
    private WebsiteOrderFormService websiteOrderFormService;

    @Autowired
    private InquiryService inquiryService;

    @IgnoreLoginCheck
    @PostMapping(value = "/saveAboutUs")
    @ApiOperation(value = "contact us", response = GeneralResult.class)
    public Response<GeneralResult> saveAboutUs(@ModelAttribute @ApiParam("Parameter") SaveAboutUsEnter enter) {
        return new Response<>(inquiryService.saveAboutUs(enter));
    }

    @IgnoreLoginCheck
    @PostMapping(value = "/email")
    @ApiOperation(value = "Mail subscription", response = GeneralResult.class)
    public Response<GeneralResult> email(@ModelAttribute @ApiParam("Parameter") StorageEamilEnter enter) {
        return new Response<>(websiteOrderFormService.email(enter));
    }

    @IgnoreLoginCheck
    @PostMapping(value = "/checkMail")
    @ApiOperation(value = "Mailbox verification", response = GeneralResult.class)
    public Response<BooleanResult> checkMail(@ModelAttribute @ApiParam("Parameter") CheckEmailEnter enter) {
        return new Response<>(websiteOrderFormService.checkMail(enter));
    }

}
