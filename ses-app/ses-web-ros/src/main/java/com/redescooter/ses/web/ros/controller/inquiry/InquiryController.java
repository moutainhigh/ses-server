package com.redescooter.ses.web.ros.controller.inquiry;

import com.redescooter.ses.api.common.annotation.IgnoreLoginCheck;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.web.ros.service.customer.InquiryService;
import com.redescooter.ses.web.ros.vo.inquiry.InquiryListEnter;
import com.redescooter.ses.web.ros.vo.inquiry.InquiryResult;
import com.redescooter.ses.web.ros.vo.website.SaveAboutUsEnter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @ClassName:InquiryController
 * @description: InquiryController
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/06 13:18
 */
@Api(tags = {"询价单管理"})
@CrossOrigin
@RestController
@RequestMapping(value = "/inquiry")
public class InquiryController {

    @Autowired
    private InquiryService inquiryService;


    @PostMapping(value = "/countStatus")
    @ApiOperation(value = "状态统计", response = Map.class)
    public Response<Map<String, Integer>> countStatus(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(inquiryService.countStatus(enter));
    }

    @IgnoreLoginCheck
    @PostMapping(value = "/save")
    @ApiOperation(value = "保存询价单", response = GeneralResult.class)
    public Response<GeneralResult> saveInquiry(@ModelAttribute @ApiParam("请求参数") SaveAboutUsEnter enter) {
        return new Response<>(inquiryService.saveAboutUs(enter));
    }

    @PostMapping(value = "/list")
    @ApiOperation(value = "询价单列表", response = InquiryResult.class)
    public Response<PageResult<InquiryResult>> inquiryList(@ModelAttribute @ApiParam("请求参数") InquiryListEnter enter) {
        return new Response<>(inquiryService.inquiryList(enter));
    }

    @PostMapping(value = "/detail")
    @ApiOperation(value = "询价单详情", response = InquiryResult.class)
    public Response<InquiryResult> inquiryDetail(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(inquiryService.inquiryDetail(enter));
    }

    @PostMapping(value = "/depositPaymentEmail")
    @ApiOperation(value = "询价单定金支付邮件", response = InquiryResult.class)
    public Response<GeneralResult> depositPaymentEmail(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(inquiryService.depositPaymentEmail(enter));
    }

    @PostMapping(value = "/lastParagraphEmail")
    @ApiOperation(value = "询价单尾款支付邮件", response = GeneralResult.class)
    public Response<GeneralResult> lastParagraphEmail(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(inquiryService.lastParagraphEmail(enter));
    }

    @PostMapping(value = "/accept")
    @ApiOperation(value = "接受询价单", response = GeneralResult.class)
    public Response<GeneralResult> acceptInquiry(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(inquiryService.acceptInquiry(enter));
    }

    @PostMapping(value = "/decline")
    @ApiOperation(value = "拒绝询价单", response = GeneralResult.class)
    public Response<GeneralResult> declineInquiry(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(inquiryService.declineInquiry(enter));
    }

    @IgnoreLoginCheck
    @PostMapping(value = "/inquiryExport")
    @ApiOperation(value = "询价单导出", response = GeneralResult.class)
    public Response<GeneralResult> inquiryExport(@ModelAttribute @ApiParam("请求参数") InquiryListEnter enter) {
        return new Response<>(inquiryService.inquiryExport(enter));
    }
}
