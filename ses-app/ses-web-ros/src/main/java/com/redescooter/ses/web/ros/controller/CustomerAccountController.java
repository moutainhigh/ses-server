package com.redescooter.ses.web.ros.controller;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.web.ros.service.CustomerRosService;
import com.redescooter.ses.web.ros.vo.account.AccountNodeResult;
import com.redescooter.ses.web.ros.vo.account.RenewAccountEnter;
import com.redescooter.ses.web.ros.vo.customer.AccountListEnter;
import com.redescooter.ses.web.ros.vo.customer.AccountListResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 20/12/2019 10:09 上午
 * @ClassName: CustomerAccountController
 * @Function: TODO
 */
@Api(tags = {"客户账号管理"})
@CrossOrigin
@RestController
@RequestMapping(value = "/customer/account")
public class CustomerAccountController {

    @Autowired
    private CustomerRosService customerRosService;

    @PostMapping(value = "/accountList")
    @ApiOperation(value = "账户列表", response = AccountListResult.class)
    public Response<PageResult<AccountListResult>> accountList(@ModelAttribute @ApiParam("请求参数") AccountListEnter enter) {
        return new Response<>(customerRosService.accountList(enter));
    }


    @PostMapping(value = "/accountCountStatus")
    @ApiOperation(value = "账户状态", response = Map.class)
    public Response<Map<String, Integer>> accountCountStatus(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(customerRosService.accountCountStatus(enter));
    }

    @PostMapping(value = "/accountNode")
    @ApiOperation(value = "账户节点", response = AccountNodeResult.class)
    public Response<List<AccountNodeResult>> accountNode(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(customerRosService.accountNode(enter));
    }

    @PostMapping(value = "/freeze")
    @ApiOperation(value = "账户冻结", response = GeneralResult.class)
    public Response<GeneralResult> freezeAccount(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(customerRosService.freezeAccount(enter));
    }

    @PostMapping(value = "/unFreeze")
    @ApiOperation(value = "账户解冻", response = GeneralResult.class)
    public Response<GeneralResult> unFreezeAccount(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(customerRosService.unFreezeAccount(enter));
    }

    @PostMapping(value = "/renew")
    @ApiOperation(value = "账户续期", response = GeneralResult.class)
    public Response<GeneralResult> renewAccont(@ModelAttribute @ApiParam("请求参数") RenewAccountEnter enter) {
        return new Response<>(customerRosService.renewAccont(enter));
    }
}
