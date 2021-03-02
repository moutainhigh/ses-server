package com.redescooter.ses.web.ros.controller.sales;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.web.ros.service.website.ContactUsService;
import com.redescooter.ses.web.ros.vo.customer.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
@RequestMapping(value = "/sales/contactUs")
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
    public Response<List<ContactUsDetailResult>> detail(@ModelAttribute @ApiParam("请求参数") ContactUsEnter enter) {
        return new Response<>(contactUsService.detail(enter));
    }

    @PostMapping(value = "/trace")
    @ApiOperation(value = "联系我们历史记录")
    public Response<List<ContactUsHistoryResult>> trace(@ModelAttribute @ApiParam("请求参数") ContactUsEnter enter) {
        return new Response<>(contactUsService.trace(enter));
    }

    @PostMapping(value = "/reply")
    @ApiOperation(value = "联系我们留言回复")
    public Response<GeneralResult> reply(@ModelAttribute @ApiParam("请求参数") ContactUsMessageEnter enter) {
        return new Response<>(contactUsService.message(enter));
    }

    @PostMapping(value = "/export")
    @ApiOperation(value = "联系我们导出")
    public Response<GeneralResult> export(@ModelAttribute @ApiParam("请求参数") ContactUsListEnter enter) {
        return new Response(contactUsService.export(enter));
    }

}
