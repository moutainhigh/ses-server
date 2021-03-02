package com.redescooter.ses.web.website.controller.business;

import com.redescooter.ses.api.common.annotation.IgnoreLoginCheck;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.web.website.service.WebSiteCustomerService;
import com.redescooter.ses.web.website.vo.customer.AddCustomerEnter;
import com.redescooter.ses.web.website.vo.customer.CustomerDetailsResult;
import com.redescooter.ses.web.website.vo.customer.EditSiteCustomerEnter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author jerry
 * @Date 2021/1/6 9:27 下午
 * @Description 客户服务
 **/
@Api(tags = {"Customer"})
@CrossOrigin
@RestController
@RequestMapping(value = "/customer")
public class CustomerController {

    @Autowired
    private WebSiteCustomerService webSiteCustomerService;

    /**
     * 创建客户
     *
     * @param enter
     * @return
     */
    @IgnoreLoginCheck
    @PostMapping(value = "/add")
    @ApiOperation(value = "Create Customer", response = GeneralResult.class)
    public Response<GeneralResult> add(@ModelAttribute @ApiParam("请求参数") AddCustomerEnter enter) {
        return new Response<>(webSiteCustomerService.addCustomer(enter));
    }

    /**
     * 客户详情
     *
     * @param enter
     * @return
     */
    @PostMapping(value = "/details")
    @ApiOperation(value = "Customer Details", response = CustomerDetailsResult.class)
    public Response<CustomerDetailsResult> details(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(webSiteCustomerService.getCustomerDetails(enter));
    }

    /**
     * 编辑客户
     *
     * @param enter
     * @return
     */
    @PostMapping(value = "/edit")
    @ApiOperation(value = "Edit Customer", response = GeneralResult.class)
    public Response<GeneralResult> edit(@RequestBody EditSiteCustomerEnter enter) {
        return new Response<>(webSiteCustomerService.editCustomer(enter));
    }
}
