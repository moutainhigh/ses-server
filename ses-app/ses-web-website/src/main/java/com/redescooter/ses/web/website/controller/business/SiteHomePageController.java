package com.redescooter.ses.web.website.controller.business;

import com.redescooter.ses.api.common.annotation.IgnoreLoginCheck;
import com.redescooter.ses.api.common.vo.base.CheckEmailEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.web.website.service.SitePageHomeService;
import com.redescooter.ses.web.website.service.TokenWebsiteService;
import com.redescooter.ses.web.website.vo.aboutus.SiteSaveAboutUsEnter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 13/1/2020 4:43 下午
 * @ClassName: ProductModelController
 * @Function: 关于我们
 */
@Api(tags = {"Contact us"})
@CrossOrigin
@RestController
@RequestMapping(value = "/home/page")
public class SiteHomePageController {

    @Autowired
    private SitePageHomeService sitePageHomeService;

    @Autowired
    private TokenWebsiteService tokenWebsiteService;

    /**
     * 联系我们
     * @param enter
     * @return
     */
    @IgnoreLoginCheck
    @PostMapping(value = "/contactUsSave")
    @ApiOperation(value = "contact Us", response = GeneralResult.class)
    public Response<GeneralResult> contactUsSave(@RequestBody SiteSaveAboutUsEnter enter) {
        return new Response<>(sitePageHomeService.saveAboutUs(enter));
    }

    /**
     * 邮件订阅
     *
     * @param enter
     * @return
     */
    @IgnoreLoginCheck
    @PostMapping(value = "/emailSubscribe")
    @ApiOperation(value = "Mail subscription", response = GeneralResult.class)
    public Response<GeneralResult> emailSubscribe(@ModelAttribute @ApiParam("Parameter") CheckEmailEnter enter) {
        return new Response<>(tokenWebsiteService.emailSubscribe(enter));
    }
}
