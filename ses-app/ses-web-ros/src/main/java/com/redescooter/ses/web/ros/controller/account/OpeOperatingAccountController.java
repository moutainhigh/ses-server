package com.redescooter.ses.web.ros.controller.account;

import com.redescooter.ses.api.common.annotation.AvoidDuplicateSubmit;
import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.api.hub.service.admin.AdmOperatingAccountService;
import com.redescooter.ses.api.hub.vo.admin.AdmSysUser;
import com.redescooter.ses.web.ros.vo.account.AccountDeatilResult;
import com.redescooter.ses.web.ros.vo.account.OperatingAccountListResult;
import com.redescooter.ses.web.ros.vo.specificat.ColorDataResult;
import io.swagger.annotations.*;

import lombok.extern.log4j.Log4j2;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;


/**
 *
 * @author Courtney
 * @since 2021-04-30
 */
@Log4j2
@RestController
@CrossOrigin
@RequestMapping("/opeOperatingAccount")
@Api( tags = {"运营账号管理"})
public class OpeOperatingAccountController {

    @DubboReference
    private AdmOperatingAccountService opeOperatingAccountService;

/**
 * 分页列表
 */
@ApiOperation(value = "分页列表", notes = "分页列表", response = ColorDataResult.class)
@PostMapping("/list")
public Response<PageResult<OperatingAccountListResult>> list(@ModelAttribute @ApiParam("请求参数") OperatingEnter enter){
    return new Response(opeOperatingAccountService.list(enter));
    }


    /**
     * 新增
     */
@ApiOperation(value = "新增", notes = "新增", response = AdmSysUser.class)
@PostMapping("/save")
@AvoidDuplicateSubmit
public Response<GeneralResult> save(@ModelAttribute @ApiParam("请求参数") AdmSysUser admOperatingEnter) throws Exception {
    return new Response(opeOperatingAccountService.saveAdmOperatingAccount(admOperatingEnter));
    }

    /**
     * 修改
     */
    @ApiOperation(value = "修改", notes = "修改", response = ColorDataResult.class)
    @PostMapping("/update")
    @AvoidDuplicateSubmit
    public Response<GeneralResult> update(@ModelAttribute @ApiParam("请求参数") AdmSysUser admOperatingEnter){
        return new Response(opeOperatingAccountService.updateByPk(admOperatingEnter));
        }

    /**
     * 删除
     */
    @ApiOperation(value = "删除", notes = "删除", response = ColorDataResult.class)
    @PostMapping("/delete")
    public Response<GeneralResult> delete(@ModelAttribute @ApiParam("请求参数") IdEnter enter){
        return new Response(opeOperatingAccountService.deleteByPk(enter));
        }


    /**
     * 详情
     * @param enter
     * @return
     */
    @PostMapping(value = "/Deatil")
    @ApiOperation(value = "详情", response = AccountDeatilResult.class)
    public Response<AccountDeatilResult> accountDeatil(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response(opeOperatingAccountService.accountDeatil(enter));
    }

    @PostMapping(value = "/updateStatus")
    @AvoidDuplicateSubmit
    @ApiOperation(value = "修改状态", response = AccountDeatilResult.class)
    public Response<GeneralResult> updateStatus(@ModelAttribute @ApiParam("请求参数") IdEnter idEnter) {
        return new Response(opeOperatingAccountService.updateStatus(idEnter));
    }
}