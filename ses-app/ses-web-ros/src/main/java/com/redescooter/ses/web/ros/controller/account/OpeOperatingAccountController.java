package com.redescooter.ses.web.ros.controller.account;

import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.web.ros.dm.OpeOperatingAccount;
import com.redescooter.ses.web.ros.service.base.OpeOperatingAccountService;
import com.redescooter.ses.web.ros.vo.account.AccountDeatilResult;
import com.redescooter.ses.web.ros.vo.account.OperatingAccountListResult;
import com.redescooter.ses.web.ros.vo.specificat.ColorDataResult;
import io.swagger.annotations.*;

import java.util.List;
import javax.annotation.Resource;

import io.swagger.models.auth.In;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


/**
 *
 * @author Courtney
 * @since 2021-04-30
 */
@Log4j2
@RestController
@RequestMapping("/opeOperatingAccount")
@Api( tags = {"运营账号管理"})
public class OpeOperatingAccountController {

@Resource
private OpeOperatingAccountService opeOperatingAccountService;

/**
 * 分页列表
 */
@ApiOperation(value = "分页列表", notes = "分页列表", response = ColorDataResult.class)
@PostMapping("/list")
public PageResult<OperatingAccountListResult> list(@ModelAttribute @ApiParam("请求参数") PageEnter enter){
    return opeOperatingAccountService.list(enter);
    }


/**
 * 新增
 */
@ApiOperation(value = "新增", notes = "新增", response = ColorDataResult.class)
@PostMapping("/save")
public Response<GeneralResult> save(@ModelAttribute @ApiParam("请求参数") OpeOperatingAccount opeOperatingAccount){
    return new Response(opeOperatingAccountService.saveOpeOperatingAccount(opeOperatingAccount));
    }

    /**
     * 修改
     */
    @ApiOperation(value = "修改", notes = "修改", response = ColorDataResult.class)
    @PostMapping("/update")
    public Response<GeneralResult> update(@ModelAttribute @ApiParam("请求参数") OpeOperatingAccount opeOperatingAccount){
        return new Response(opeOperatingAccountService.updateByPk(opeOperatingAccount));
        }

/**
 * 删除
 */
    @ApiOperation(value = "删除", notes = "删除", response = ColorDataResult.class)
    @PostMapping("/delete")
    public Response<GeneralResult> delete(@ModelAttribute @ApiParam("请求参数") IdEnter enter){
        return new Response(opeOperatingAccountService.deleteByPk(enter));
        }



    @PostMapping(value = "/Deatil")
    @ApiOperation(value = "详情", response = AccountDeatilResult.class)
    public Response<AccountDeatilResult> accountDeatil(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response(opeOperatingAccountService.accountDeatil(enter));
    }
    }