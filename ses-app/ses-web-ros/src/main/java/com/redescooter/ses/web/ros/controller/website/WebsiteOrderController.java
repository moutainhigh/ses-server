package com.redescooter.ses.web.ros.controller.website;

import com.redescooter.ses.api.common.annotation.IgnoreLoginCheck;
import com.redescooter.ses.api.common.annotation.WebsiteSignIn;
import com.redescooter.ses.api.common.vo.base.BooleanResult;
import com.redescooter.ses.api.common.vo.base.CheckEmailEnter;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.Response;
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
@Api(tags = {"Official website order"})
@CrossOrigin
@RestController
@RequestMapping(value = "/website")
public class WebsiteOrderController {

    @Autowired
    private WebsiteOrderFormService websiteOrderFormService;

    @IgnoreLoginCheck
    @PostMapping(value = "/scooters")
    @ApiOperation(value = "Vehicle list", response = ProductResult.class)
    public Response<List<ProductResult>> scooters(@ModelAttribute @ApiParam("Parameter") ScootersEnter enter) {
        return new Response<>(websiteOrderFormService.scooters(enter));
    }

    @IgnoreLoginCheck
    @PostMapping(value = "/productModels")
    @ApiOperation(value = "Vehicle model list", response = ProductModelResult.class)
    public Response<List<ProductModelResult>> productModels(@ModelAttribute @ApiParam("Parameter") GeneralEnter enter) {
        return new Response<>(websiteOrderFormService.productModels(enter));
    }

    @IgnoreLoginCheck
    @PostMapping(value = "/accessoryBatterys")
    @ApiOperation(value = "List of vehicle battery accessories", response = AccessoryResult.class)
    public Response<List<AccessoryResult>> accessoryBatteryList(@ModelAttribute @ApiParam("Parameter") GeneralEnter enter) {
        return new Response<>(websiteOrderFormService.accessoryBatteryList(enter));
    }

    @IgnoreLoginCheck
    @PostMapping(value = "/accessoryTopCases")
    @ApiOperation(value = "Spare parts list of vehicle trunk", response = AccessoryResult.class)
    public Response<List<AccessoryResult>> accessoryTopCaseList(@ModelAttribute @ApiParam("Parameter") GeneralEnter enter) {
        return new Response<>(websiteOrderFormService.accessoryTopCaseList(enter));
    }

    @WebsiteSignIn
    @PostMapping(value = "/saveOrderForm")
    @ApiOperation(value = "Reservation of advance order", response = SaveOrderFormResult.class)
    public Response<SaveOrderFormResult> saveOrderForm(@ModelAttribute @ApiParam("Parameter") SaveSaleOrderEnter enter) {
        return new Response<>(websiteOrderFormService.saveOrderForm(enter));
    }

    @WebsiteSignIn
    @PostMapping(value = "/editOrderForm")
    @ApiOperation(value = "Edit advance order", response = SaveOrderFormResult.class)
    public Response<SaveOrderFormResult> editOrderForm(@ModelAttribute @ApiParam("Parameter") SaveSaleOrderEnter enter) {
        return new Response<>(websiteOrderFormService.editOrderForm(enter));
    }

    @WebsiteSignIn
    @PostMapping(value = "/payDeposit")
    @ApiOperation(value = "Deposit payment", response = GeneralResult.class)
    public Response<GeneralResult> payDeposit(@ModelAttribute @ApiParam("Parameter") IdEnter enter) {
        return new Response<>(websiteOrderFormService.payDeposit(enter));
    }

    @WebsiteSignIn
    @PostMapping(value = "/orderForms")
    @ApiOperation(value = "Pre order list", response = OrderFormsResult.class)
    public Response<List<OrderFormsResult>> orderForms(@ModelAttribute @ApiParam("Parameter") OrderFormsEnter enter) {
        return new Response<>(websiteOrderFormService.orderForms(enter));
    }

    @WebsiteSignIn
    @PostMapping(value = "/orderFormInfo")
    @ApiOperation(value = "Pre order details", response = OrderFormInfoResult.class)
    public Response<OrderFormInfoResult> orderFormInfo(@ModelAttribute @ApiParam("Parameter") IdEnter enter) {
        return new Response<>(websiteOrderFormService.orderFormInfo(enter));
    }

    @WebsiteSignIn
    @PostMapping(value = "/payLastParagraph")
    @ApiOperation(value = "Pay the balance", response = GeneralResult.class)
    public Response<GeneralResult> payLastParagraph(@ModelAttribute @ApiParam("Parameter") IdEnter enter) {
        return new Response<>(websiteOrderFormService.payLastParagraph(enter));
    }

    @WebsiteSignIn
    @PostMapping(value = "/customerInfo")
    @ApiOperation(value = "Customer information", response = CustomerInfoResult.class)
    public Response<CustomerInfoResult> customerInfo(@ModelAttribute @ApiParam("Parameter") GeneralEnter enter) {
        return new Response<>(websiteOrderFormService.customerInfo(enter));
    }

}
