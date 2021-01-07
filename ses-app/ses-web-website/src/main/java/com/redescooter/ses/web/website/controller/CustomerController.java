package com.redescooter.ses.web.website.controller;

import com.redescooter.ses.api.common.annotation.IgnoreLoginCheck;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.web.website.service.WebSiteCustomerService;
import com.redescooter.ses.web.website.vo.customer.AddCustomerEnter;
import com.redescooter.ses.web.website.vo.customer.CustomerDetailsResult;
import com.redescooter.ses.web.website.vo.product.ModelPriceResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author jerry
 * @Date 2021/1/6 9:27 下午
 * @Description 客户服务
 **/
@Api(tags = {"Customer Service"})
@CrossOrigin
@RestController
@RequestMapping(value = "/customer")
public class CustomerController {

    @Autowired(required = true)
    private WebSiteCustomerService webSiteCustomerService;

    /**
     * 创建客户
     *
     * @param enter
     * @return
     */
    @IgnoreLoginCheck
    @PostMapping(value = "/list")
    @ApiOperation(value = "Create Customer", response = ModelPriceResult.class)
    public Response<GeneralResult> add(@ModelAttribute @ApiParam("请求参数") AddCustomerEnter enter) {
        return new Response<>(webSiteCustomerService.addCustomer(enter));
    }

    /**
     * 客户详情
     *
     * @param enter
     * @return
     */
    @PostMapping(value = "/list")
    @ApiOperation(value = "Customer Details", response = ModelPriceResult.class)
    public Response<CustomerDetailsResult> Details(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(webSiteCustomerService.getCustomerDetails(enter));
    }
}
