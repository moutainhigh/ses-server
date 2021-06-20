package com.redescooter.ses.web.ros.controller.app;

import com.redescooter.ses.api.common.annotation.IgnoreLoginCheck;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.api.common.vo.base.TokenResult;
import com.redescooter.ses.web.ros.dm.OpeWarehouseAccount;
import com.redescooter.ses.web.ros.manager.ScanCodeRecordManager;
import com.redescooter.ses.web.ros.service.app.ChAppService;
import com.redescooter.ses.web.ros.vo.app.AppLoginEnter;
import com.redescooter.ses.web.ros.vo.app.ScanCodeRecordEnter;
import com.redescooter.ses.web.ros.vo.app.ScanCodeRecordReqEnter;
import com.redescooter.ses.web.ros.vo.app.ScanCodeRecordResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author Chris
 * @Date 2021/5/28 10:37
 */
@Api(value = "中国仓库控制器", tags = "中国仓库控制器")
@CrossOrigin
@RestController
@RequestMapping("/app/ch")
public class ChAppController {

    @Autowired
    private ChAppService chAppService;

    /**
     * 部件扫码
     */
    @Autowired
    private ScanCodeRecordManager scanCodeRecordManager;

    /**
     * 登录
     */
    @PostMapping("/login")
    @ApiOperation(value = "登录", notes = "登录")
    @IgnoreLoginCheck
    public Response<TokenResult> login(@ModelAttribute AppLoginEnter enter) {
        return new Response<>(chAppService.login(enter));
    }

    /**
     * 登出
     */
    @PostMapping("/logout")
    @ApiOperation(value = "登出", notes = "登出")
    @IgnoreLoginCheck
    public Response<GeneralResult> logout(@ModelAttribute GeneralEnter enter) {
        return new Response<>(chAppService.logout(enter));
    }

    /**
     * 获得个人信息
     */
    @PostMapping("/info")
    @ApiOperation(value = "获得个人信息", notes = "获得个人信息")
    @IgnoreLoginCheck
    public Response<OpeWarehouseAccount> getUserInfo(@ModelAttribute GeneralEnter enter) {
        return new Response<>(chAppService.getUserInfo(enter));
    }

    /**
     * @Title: checkRsn
     * @Description: // 验证rsn码
     * @Param: [enter]
     * @Return: com.redescooter.ses.api.common.vo.base.Response<java.lang.Boolean>
     * @Date: 2021/6/4 5:08 下午
     * @Author: Charles
     */
    @PostMapping("/checkRsn")
    @ApiOperation(value = "验证rsn码", notes = "验证rsn码")
    @IgnoreLoginCheck
    public Response<Boolean> checkRsn(@ModelAttribute ScanCodeRecordReqEnter enter) {
        return new Response<>(scanCodeRecordManager.checkRsn(enter));
    }

    /**
     * @Title: checkScanCodeRecode
     * @Description: // 校验录入信息
     * @Param: [enter]
     * @Return: com.redescooter.ses.api.common.vo.base.Response<java.lang.Boolean>
     * @Date: 2021/6/7 11:54 上午
     * @Author: Charles
     */
    @PostMapping("/checkScanCodeRecode")
    @ApiOperation(value = "校验录入信息", notes = "校验录入信息")
    @IgnoreLoginCheck
    public Response<Boolean> checkScanCodeRecode(@ModelAttribute ScanCodeRecordEnter enter) {
        return new Response<>(scanCodeRecordManager.checkScanCodeRecode(enter));
    }

    /**
     * @Title: insertScanCodeRecode
     * @Description: // 部件信息录入/修改时加id
     * @Param: [enter]
     * @Return: com.redescooter.ses.api.common.vo.base.Response<java.lang.Boolean>
     * @Date: 2021/6/4 4:41 下午
     * @Author: Charles
     */
    @PostMapping("/insertScanCodeRecode")
    @ApiOperation(value = "部件信息录入/修改时加id", notes = "部件信息录入/修改时加id")
    @IgnoreLoginCheck
    public Response<Boolean> insertScanCodeRecode(@ModelAttribute ScanCodeRecordEnter enter) {
        return new Response<>(scanCodeRecordManager.insertScanCodeRecode(enter));
    }

    /**
     * @Title: scanCodeRecordList
     * @Description: // 部件信息列表
     * @Param: [enter]
     * @Return: com.redescooter.ses.api.common.vo.base.Response<com.redescooter.ses.api.common.vo.base.PageResult < com.redescooter.ses.web.ros.vo.app.ScanCodeRecordResult>>
     * @Date: 2021/6/7 11:55 上午
     * @Author: Charles
     */
    @PostMapping("/scanCodeRecordList")
    @ApiOperation(value = "部件信息列表", notes = "部件信息列表", response = ScanCodeRecordResult.class)
    @IgnoreLoginCheck
    public Response<PageResult<ScanCodeRecordResult>> scanCodeRecordList(@ModelAttribute ScanCodeRecordReqEnter enter) {
        return new Response<>(scanCodeRecordManager.scanCodeRecordList(enter));
    }

    /**
     * @Title: scanCodeRecordDetail
     * @Description: // 根据id查询部件信息详情
     * @Param: [enter]
     * @Return: com.redescooter.ses.api.common.vo.base.Response<com.redescooter.ses.web.ros.vo.app.ScanCodeRecordEnter>
     * @Date: 2021/6/7 11:59 上午
     * @Author: Charles
     */
    @PostMapping("/scanCodeRecordDetail")
    @ApiOperation(value = "根据id查询部件信息详情", notes = "根据id查询部件信息详情", response = ScanCodeRecordEnter.class)
    @IgnoreLoginCheck
    public Response<ScanCodeRecordEnter> scanCodeRecordDetail(@ModelAttribute ScanCodeRecordReqEnter enter) {
        return new Response<>(scanCodeRecordManager.scanCodeRecordDetail(enter));
    }


}
