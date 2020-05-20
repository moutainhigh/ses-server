package com.redescooter.ses.web.ros.controller.website;

import com.redescooter.ses.api.common.annotation.IgnoreLoginCheck;
import com.redescooter.ses.api.common.annotation.WebsiteSignIn;
import com.redescooter.ses.api.common.vo.base.BooleanResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.api.common.vo.base.StringEnter;
import com.redescooter.ses.web.ros.service.website.WebsiteOrderFormService;
import com.redescooter.ses.web.ros.vo.website.*;
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

    @IgnoreLoginCheck
    @PostMapping(value = "/scooters")
    @ApiOperation(value = "车辆列表", response = ProductResult.class)
    public Response<List<ProductResult>> scooters(@ModelAttribute @ApiParam("请求参数") ScootersEnter enter) {
        return new Response<>(websiteOrderFormService.scooters(enter));
    }

    @IgnoreLoginCheck
    @PostMapping(value = "/productModels")
    @ApiOperation(value = "车辆型号", response = ProductModelResult.class)
    public Response<List<ProductModelResult>> productModels(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(websiteOrderFormService.productModels(enter));
    }

    @IgnoreLoginCheck
    @PostMapping(value = "/accessoryBatterys")
    @ApiOperation(value = "车辆电池配件列表", response = AccessoryResult.class)
    public Response<List<AccessoryResult>> accessoryBatteryList(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(websiteOrderFormService.accessoryBatteryList(enter));
    }

    //todo 暂存
    @IgnoreLoginCheck
    @PostMapping(value = "/accessoryTopCases")
    @ApiOperation(value = "车辆后备箱配件列表", response = AccessoryResult.class)
    public Response<List<AccessoryResult>> accessoryTopCaseList(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(websiteOrderFormService.accessoryTopCaseList(enter));
    }

    @IgnoreLoginCheck
    @PostMapping(value = "/checkMail")
    @ApiOperation(value = "邮箱校验", response = GeneralResult.class)
    public Response<BooleanResult> checkMail(@ModelAttribute @ApiParam("请求参数") StringEnter enter) {
        return new Response<>(websiteOrderFormService.checkMail(enter));
    }

    @WebsiteSignIn
    @PostMapping(value = "/saveOrderForm")
    @ApiOperation(value = "保存预订单", response = SaveOrderFormResult.class)
    public Response<SaveOrderFormResult> saveOrderForm(@ModelAttribute @ApiParam("请求参数") SaveSaleOrderEnter enter) {
        return new Response<>(websiteOrderFormService.saveOrderForm(enter));
    }

    @WebsiteSignIn
    @PostMapping(value = "/editOrderForm")
    @ApiOperation(value = "编辑预订单", response = SaveOrderFormResult.class)
    public Response<SaveOrderFormResult> editOrderForm(@ModelAttribute @ApiParam("请求参数") SaveSaleOrderEnter enter) {
        return new Response<>(websiteOrderFormService.editOrderForm(enter));
    }

    @WebsiteSignIn
    @PostMapping(value = "/payDeposit")
    @ApiOperation(value = "定金支付", response = GeneralResult.class)
    public Response<GeneralResult> payDeposit(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(websiteOrderFormService.payDeposit(enter));
    }

    @WebsiteSignIn
    @PostMapping(value = "/orderForms")
    @ApiOperation(value = "预订单列表", response = OrderFormsResult.class)
    public Response<List<OrderFormsResult>> orderForms(@ModelAttribute @ApiParam("请求参数") OrderFormsEnter enter) {
        return new Response<>(websiteOrderFormService.orderForms(enter));
    }

    @WebsiteSignIn
    @PostMapping(value = "/orderFormInfo")
    @ApiOperation(value = "预订单详情", response = OrderFormInfoResult.class)
    public Response<OrderFormInfoResult> orderFormInfo(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(websiteOrderFormService.orderFormInfo(enter));
    }

    @WebsiteSignIn
    @PostMapping(value = "/payLastParagraph")
    @ApiOperation(value = "支付尾款", response = GeneralResult.class)
    public Response<GeneralResult> payLastParagraph(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(websiteOrderFormService.payLastParagraph(enter));
    }

    @WebsiteSignIn
    @PostMapping(value = "/customerInfo")
    @ApiOperation(value = "客户信息", response = CustomerInfoResult.class)
    public Response<CustomerInfoResult> customerInfo(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(websiteOrderFormService.customerInfo(enter));
    }
    @IgnoreLoginCheck
    @PostMapping(value = "/email")
    @ApiOperation(value = "存储邮箱", response = GeneralResult.class)
    public Response<GeneralResult> email(@ModelAttribute @ApiParam("请求参数") StorageEamilEnter enter) {
      return new Response<>(websiteOrderFormService.email(enter));
  }
}
