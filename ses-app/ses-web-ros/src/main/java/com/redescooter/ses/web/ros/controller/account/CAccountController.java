package com.redescooter.ses.web.ros.controller.account;

import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.web.ros.service.customer.CustomerRosService;
import com.redescooter.ses.web.ros.vo.account.AccountDeatilResult;
import com.redescooter.ses.web.ros.vo.account.AccountNodeResult;
import com.redescooter.ses.web.ros.vo.account.RenewAccountEnter;
import com.redescooter.ses.web.ros.vo.account.VerificationCodeResult;
import com.redescooter.ses.web.ros.vo.customer.AccountListEnter;
import com.redescooter.ses.web.ros.vo.customer.AccountListResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 20/12/2019 10:09 上午
 * @ClassName: CustomerAccountController
 * @Function: TODO
 */
@Log4j2
@Api(tags = {"账号管理"})
@CrossOrigin
@RestController
@RequestMapping(value = "/account/customer")
public class CAccountController {

    @Autowired
    private CustomerRosService customerRosService;

    @PostMapping(value = "/accountList")
    @ApiOperation(value = "账户列表", response = AccountListResult.class)
    public Response<PageResult<AccountListResult>> accountList(@ModelAttribute @ApiParam("请求参数") AccountListEnter enter) {
        return new Response<>(customerRosService.accountList(enter));
    }

    @PostMapping(value = "/accountCountStatus")
    @ApiOperation(value = "状态统计", response = Map.class)
    public Response<Map<String, Integer>> accountCountStatus(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(customerRosService.accountCountStatus(enter));
    }

    @PostMapping(value = "/accountDeatil")
    @ApiOperation(value = "账户详情", response = AccountDeatilResult.class)
    public Response<AccountDeatilResult> accountDeatil(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(customerRosService.accountDeatil(enter));
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

    @PostMapping(value = "/verificationCode")
    @ApiOperation(value = "验证码", response = VerificationCodeResult.class)
    public Response<VerificationCodeResult> verificationCode(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(customerRosService.verificationCode(enter));
    }

    @PostMapping(value = "/resetPassword")
    @ApiOperation(value = "重置密码", response = GeneralResult.class)
    public Response<GeneralResult> resetPassword(@ModelAttribute @ApiParam("请求参数") SetPasswordEnter enter) {
        enter.setRequestId(enter.getConfirmRequestId());
        return new Response<>(customerRosService.customerSetPassword(enter));
    }
}
