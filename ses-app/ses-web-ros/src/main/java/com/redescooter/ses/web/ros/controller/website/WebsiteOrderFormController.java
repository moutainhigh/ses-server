package com.redescooter.ses.web.ros.controller.website;

import com.redescooter.ses.api.common.annotation.WebsiteSignIn;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.web.ros.service.website.WebsiteOrderFormService;
import com.redescooter.ses.web.ros.vo.website.AccessoryResult;
import com.redescooter.ses.web.ros.vo.website.ProductColorResult;
import com.redescooter.ses.web.ros.vo.website.ProductResult;
import com.redescooter.ses.web.ros.vo.website.SaveSaleOrderEnter;
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
public class WebsiteOrderFormController {

    @Autowired
    private WebsiteOrderFormService websiteOrderFormService;




    @WebsiteSignIn
    @PostMapping(value = "/scooters")
    @ApiOperation(value = "车辆列表", response = ProductResult.class)
    public Response<List<ProductResult>> scooterList(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(websiteOrderFormService.scooterList(enter));
    }

//    @WebsiteSignIn
//    @PostMapping(value = "/scooterColors")
//    @ApiOperation(value = "车辆颜色", response = ProductColorResult.class)
//    public Response<List<ProductColorResult>> scooterColors(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
//        return new Response<>(websiteOrderFormService.scooterColors(enter));
//    }

    @WebsiteSignIn
    @PostMapping(value = "/accessoryBatterys")
    @ApiOperation(value = "车辆电池配件列表", response = AccessoryResult.class)
    public Response<List<AccessoryResult>> accessoryBatteryList(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(websiteOrderFormService.accessoryBatteryList(enter));
    }

    @WebsiteSignIn
    @PostMapping(value = "/accessoryTopCases")
    @ApiOperation(value = "车辆后备箱配件列表", response = AccessoryResult.class)
    public Response<List<AccessoryResult>> accessoryTopCaseList(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(websiteOrderFormService.accessoryTopCaseList(enter));
    }

    @WebsiteSignIn
    @PostMapping(value = "/saveOrderForm")
    @ApiOperation(value = "保存预订单", response = GeneralResult.class)
    public Response<GeneralResult> saveOrderForm(@ModelAttribute @ApiParam("请求参数") SaveSaleOrderEnter enter) {
        return new Response<>(websiteOrderFormService.saveOrderForm(enter));
    }

}
