package com.redescooter.ses.web.ros.controller;

import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.web.ros.service.website.ContactUsService;
import com.redescooter.ses.web.ros.vo.customer.ContactUsListEnter;
import com.redescooter.ses.web.ros.vo.customer.ContactUsListResult;
import com.redescooter.ses.web.ros.vo.customer.DetailsCustomerResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassNameContactUsController
 * @Description
 * @Author Aleks
 * @Date2020/8/4 20:14
 * @Version V1.0
 **/
@Api(tags = {"联系我们"})
@CrossOrigin
@RestController
@RequestMapping(value = "/contactUs")
public class ContactUsController {

    @Autowired
    private ContactUsService contactUsService;

    @PostMapping(value = "/list")
    @ApiOperation(value = "联系我们列表")
    public Response<PageResult<ContactUsListResult>> list(@ModelAttribute @ApiParam("请求参数") ContactUsListEnter enter) {
        return new Response<>(contactUsService.list(enter));
    }

    @PostMapping(value = "/detail")
    @ApiOperation(value = "联系我们详情接口")
    public Response detail() {
        return new Response<>();
    }


    @PostMapping(value = "/trace")
    @ApiOperation(value = "联系我们历史记录")
    public Response trace() {
        return new Response<>();
    }

}
