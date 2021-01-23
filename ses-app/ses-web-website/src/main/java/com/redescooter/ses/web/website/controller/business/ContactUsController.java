package com.redescooter.ses.web.website.controller.business;

import com.redescooter.ses.api.common.annotation.IgnoreLoginCheck;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.web.website.service.SitePageHomeService;
import com.redescooter.ses.web.website.vo.aboutus.SiteSaveAboutUsEnter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@RequestMapping(value = "/contactus")
public class ContactUsController {

    @Autowired
    private SitePageHomeService sitePageHomeService;

    @IgnoreLoginCheck
    @PostMapping(value = "/save")
    @ApiOperation(value = "contact Us", response = GeneralResult.class)
    public Response<GeneralResult> save(@RequestBody SiteSaveAboutUsEnter enter) {
        return new Response<>(sitePageHomeService.saveAboutUs(enter));
    }
}
