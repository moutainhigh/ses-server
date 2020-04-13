package com.redescooter.ses.web.ros.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.web.ros.service.customer.CustomerRosService;
import com.redescooter.ses.web.ros.vo.account.OpenAccountEnter;
import com.redescooter.ses.web.ros.vo.customer.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

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

    @PostMapping(value = "/checkMail")
    @ApiOperation(value = "邮箱验证", response = IntResult.class)
    public Response<IntResult> checkMail(@ModelAttribute @ApiParam("邮箱") StringEnter enter ) {
        return new Response<>(customerRosService.checkMailCount(enter));
    }

    @PostMapping(value = "/save")
    @ApiOperation(value = "新建客户", response = GeneralResult.class)
    public Response<GeneralResult> save(@ModelAttribute @ApiParam("请求参数") CreateCustomerEnter enter) {
        return new Response<>(customerRosService.save(enter));
    }

    @PostMapping(value = "/edit")
    @ApiOperation(value = "编辑客户", response = GeneralResult.class)
    public Response<GeneralResult> edit(@ModelAttribute @ApiParam("请求参数") EditCustomerEnter enter) {
        return new Response<>(customerRosService.edit(enter));
    }

    @PostMapping(value = "/list")
    @ApiOperation(value = "客户列表", response = DetailsCustomerResult.class)
    public Response<PageResult<DetailsCustomerResult>> list(@ModelAttribute @ApiParam("请求参数") ListCustomerEnter enter) {
        return new Response<>(customerRosService.list(enter));
    }

    @PostMapping(value = "/details")
    @ApiOperation(value = "客户详情", response = DetailsCustomerResult.class)
    public Response<DetailsCustomerResult> details(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(customerRosService.details(enter));
    }

    @PostMapping(value = "/trash")
    @ApiOperation(value = "加入垃圾箱", response = GeneralResult.class)
    public Response<GeneralResult> trash(@ModelAttribute @ApiParam("请求参数") TrashCustomerEnter enter) {
        return new Response<>(customerRosService.trash(enter));
    }

    @PostMapping(value = "/change")
    @ApiOperation(value = "客户转换", response = GeneralResult.class)
    public Response<GeneralResult> change(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(customerRosService.change(enter));
    }

    @PostMapping(value = "/countStatus")
    @ApiOperation(value = "状态统计", response = Map.class)
    public Response<Map<String, Integer>> countStatus(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(customerRosService.countStatus(enter));
    }

    @PostMapping(value = "/openAccount")
    @ApiOperation(value = "开通账号", response = GeneralResult.class)
    public Response<GeneralResult> openAccount(@ModelAttribute @ApiParam("请求参数") OpenAccountEnter enter) {
        return new Response<>(customerRosService.openAccount(enter));
    }

    @PostMapping(value = "/sendEmailAgian")
    @ApiOperation(value = "客户再次发生邮件", response = BooleanResult.class)
    public Response<BooleanResult> sendEmailAgian(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(customerRosService.sendEmailAgian(enter));
    }

}
