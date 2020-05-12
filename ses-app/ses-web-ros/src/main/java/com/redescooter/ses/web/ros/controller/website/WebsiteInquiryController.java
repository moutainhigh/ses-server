package com.redescooter.ses.web.ros.controller.website;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.web.ros.service.website.WebsiteInquiryService;
import com.redescooter.ses.web.ros.vo.website.AccessoryBatteryResult;
import com.redescooter.ses.web.ros.vo.website.ProductColorResult;
import com.redescooter.ses.web.ros.vo.website.ProductTypeResult;
import com.redescooter.ses.web.ros.vo.website.SaveSaleOrderEnter;
import com.redescooter.ses.web.ros.vo.website.TopClassResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName:WebsiteInquiryController
 * @description: WebsiteInquiryController
 * @author: Alex
 * @Version：1.3
 * @create: 2020/05/12 15:51
 */
@Log4j2
@Api(tags = {"官网询价模块"})
@CrossOrigin
@RestController
@RequestMapping(value = "/website")
public class WebsiteInquiryController {

    @Autowired
    private WebsiteInquiryService websiteInquiryService;

    @PostMapping(value = "/scooterType")
    @ApiOperation(value = "车辆类型", response = ProductTypeResult.class)
    public Response<List<ProductTypeResult>> scooterType(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(websiteInquiryService.scooterType(enter));
    }

    @PostMapping(value = "/scooterColor")
    @ApiOperation(value = "车辆颜色", response = ProductColorResult.class)
    public Response<List<ProductColorResult>> scooterColor(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(websiteInquiryService.scooterColor(enter));
    }

    @PostMapping(value = "/accessoryBatteryList")
    @ApiOperation(value = "车辆电池配件列表", response = AccessoryBatteryResult.class)
    public Response<List<AccessoryBatteryResult>> accessoryBatteryList(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(websiteInquiryService.accessoryBatteryList(enter));
    }

    @PostMapping(value = "/topClass")
    @ApiOperation(value = "车辆后备箱配件列表", response = TopClassResult.class)
    public Response<List<TopClassResult>> topClass(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(websiteInquiryService.topClass(enter));
    }

    @PostMapping(value = "/saveInquiry")
    @ApiOperation(value = "保存询价单", response = GeneralResult.class)
    public Response<GeneralResult> saveInquiry(@ModelAttribute @ApiParam("请求参数") SaveSaleOrderEnter enter) {
        return new Response<>(websiteInquiryService.saveInquiry(enter));
    }

}
