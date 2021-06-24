package com.redescooter.ses.web.ros.controller.app;

import com.redescooter.ses.api.common.annotation.AvoidDuplicateSubmit;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.api.scooter.service.ScooterService;
import com.redescooter.ses.api.scooter.vo.ScoScooterResult;
import com.redescooter.ses.web.ros.dm.OpeWarehouseAccount;
import com.redescooter.ses.web.ros.service.app.WarehouseAccountService;
import com.redescooter.ses.web.ros.service.base.MondayRecordService;
import com.redescooter.ses.web.ros.vo.app.WarehouseAccountListEnter;
import com.redescooter.ses.web.ros.vo.app.WarehouseAccountSaveEnter;
import com.redescooter.ses.web.ros.vo.app.WarehouseAccountUpdateEnter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Description 仓库账号管理控制器
 * @Author Chris
 * @Date 2021/5/10 10:04
 */
@Api(value = "仓库账号管理控制器", tags = "仓库账号管理控制器")
@CrossOrigin
@RestController
@RequestMapping("/warehouse/account")
public class WarehouseAccountController {

    @Autowired
    private WarehouseAccountService warehouseAccountService;

    /**
     * 每个tab的count
     */
    @PostMapping("/count")
    @ApiOperation(value = "每个tab的count", notes = "每个tab的count")
    public Response<Map<String, Integer>> getTabCount(@ModelAttribute GeneralEnter enter) {
        return new Response<>(warehouseAccountService.getTabCount(enter));
    }

    /**
     * 列表
     */
    @PostMapping("/list")
    @ApiOperation(value = "列表", notes = "列表")
    public Response<PageResult<OpeWarehouseAccount>> getList(@ModelAttribute WarehouseAccountListEnter enter) {
        return new Response<>(warehouseAccountService.getList(enter));
    }

    /**
     * 新增
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增", notes = "新增")
    @AvoidDuplicateSubmit
    public Response<GeneralResult> add(@ModelAttribute WarehouseAccountSaveEnter enter) {
        return new Response<>(warehouseAccountService.add(enter));
    }

    /**
     * 详情
     */
    @PostMapping("/detail")
    @ApiOperation(value = "详情", notes = "详情")
    public Response<OpeWarehouseAccount> getDetail(@ModelAttribute IdEnter enter) {
        return new Response<>(warehouseAccountService.getDetail(enter));
    }

    /**
     * 编辑
     */
    @PostMapping("/edit")
    @ApiOperation(value = "编辑", notes = "编辑")
    @AvoidDuplicateSubmit
    public Response<GeneralResult> edit(@ModelAttribute WarehouseAccountUpdateEnter enter) {
        return new Response<>(warehouseAccountService.edit(enter));
    }

    /**
     * 开启或关闭状态
     */
    @PostMapping("/status")
    @ApiOperation(value = "开启或关闭状态", notes = "开启或关闭状态")
    @AvoidDuplicateSubmit
    public Response<GeneralResult> editStatus(@ModelAttribute IdEnter enter) {
        return new Response<>(warehouseAccountService.editStatus(enter));
    }

    /**
     * 修改密码
     */
    /*@PostMapping("/password")
    @ApiOperation(value = "修改密码", notes = "修改密码")
    @AvoidDuplicateSubmit
    public Response<GeneralResult> updatePassword(@ModelAttribute UpdatePasswordEnter enter) {
        return new Response<>(warehouseAccountService.updatePassword(enter));
    }*/

    /**
     * 删除
     */
    @PostMapping("/delete")
    @ApiOperation(value = "删除", notes = "删除")
    public Response<GeneralResult> delete(@ModelAttribute IdEnter enter) {
        return new Response<>(warehouseAccountService.delete(enter));
    }


    @Autowired
    private MondayRecordService recordService;

    /*@Autowired
    private ScooterService searchService;*/

    @GetMapping("/testMonday")
    public void testMonday(){
        ScoScooterResult salcoScooterResult = new ScoScooterResult();
        salcoScooterResult.setBattery("www.rthj.123,www.dhj.123");
        salcoScooterResult.setModel("2");
        salcoScooterResult.setTabletSn("465464645");
        recordService.save(1654654L, salcoScooterResult);
    }
}
