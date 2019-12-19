package com.redescooter.ses.web.ros.controller;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.web.ros.service.CustomerRosService;
import com.redescooter.ses.web.ros.vo.customer.CreateCustomerEnter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName:CustomerController
 * @description: CustomerController
 * @author: Alex
 * @Version：1.0
 * @create: 2019/12/18 10:07
 */

@Api(tags = {"客户管理"})
@CrossOrigin
@RestController
@RequestMapping(value = "/customer")
public class CustomerController {

    @Autowired
    private CustomerRosService customerRosService;

    @PostMapping(value = "/save")
    @ApiOperation(value = "新建客户", response = GeneralResult.class)
    public Response<GeneralResult> save(@ModelAttribute @ApiParam("请求参数") CreateCustomerEnter enter) {
        return new Response<>(customerRosService.save(enter));
    }

    @PostMapping(value = "/queryCustomerListByPage")
    @ApiOperation(value = "客户列表", response = GeneralResult.class)
    public Response<PageResult<GeneralResult>> queryCustomerListByPage(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>();
    }

    @PostMapping(value = "/queryCustomerDetail")
    @ApiOperation(value = "客户详情", response = GeneralResult.class)
    public Response<GeneralResult> queryCustomerDetail(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>();
    }

    @PostMapping(value = "/modifyCustomerStatus")
    @ApiOperation(value = "潜在客户转正式客户", response = GeneralResult.class)
    public Response<GeneralResult> modifyCustomerStatus(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>();
    }

    @PostMapping(value = "/createSaasAccount")
    @ApiOperation(value = "开通saas账户", response = GeneralResult.class)
    public Response<GeneralResult> createSaasAccount(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>();
    }

}
