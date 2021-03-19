package com.redescooter.ses.web.ros.controller.other.website;

import com.redescooter.ses.api.common.annotation.IgnoreLoginCheck;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.web.ros.service.wthdrawalsite.WthdrawalSiteServer;
import com.redescooter.ses.web.ros.vo.deliveryopion.DeliveryOptionSaveResult;
import com.redescooter.ses.web.ros.vo.wthdrawalsite.WthdrawalSiteEditEnter;
import com.redescooter.ses.web.ros.vo.wthdrawalsite.WthdrawalSiteResult;
import com.redescooter.ses.web.ros.vo.wthdrawalsite.WthdrawalSiteSaveEnter;
import com.redescooter.ses.web.ros.vo.wthdrawalsite.isSwitchEnter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Api(tags = {"取货地址服务"})
@RestController
@RequestMapping(value = "/wthdrawalsite", method = RequestMethod.POST)
public class WthdrawalSiteController {

    @Autowired
    private WthdrawalSiteServer wthdrawalSiteServer;

    @PostMapping(value = "/save")
    @ApiOperation(value = "保存取货地址", response = GeneralResult.class)
    public Response<GeneralResult> save(@ModelAttribute @ApiParam("Parameter") WthdrawalSiteSaveEnter enter) {
        return new Response<>(wthdrawalSiteServer.save(enter));
    }

    @PostMapping(value = "/edit")
    @ApiOperation(value = "修改取货地址", response = GeneralResult.class)
    public Response<GeneralResult> edit(@ModelAttribute @ApiParam("Parameter") WthdrawalSiteEditEnter enter) {
        return new Response<>(wthdrawalSiteServer.edit(enter));
    }

    @IgnoreLoginCheck
    @PostMapping(value = "/details")
    @ApiOperation(value = "取货地址详情", response = DeliveryOptionSaveResult.class)
    public Response<WthdrawalSiteResult> details(@ModelAttribute @ApiParam("Parameter") IdEnter enter) {
        return new Response<>(wthdrawalSiteServer.details(enter));
    }

    @IgnoreLoginCheck
    @PostMapping(value = "/list")
    @ApiOperation(value = "取货地址列表", response = DeliveryOptionSaveResult.class)
    public Response<List<WthdrawalSiteResult>> list(@ModelAttribute @ApiParam("Parameter") GeneralEnter enter) {
        return new Response<>(wthdrawalSiteServer.list(enter));
    }

    @IgnoreLoginCheck
    @PostMapping(value = "/isswitch")
    @ApiOperation(value = "开启或关闭取货地址", response = DeliveryOptionSaveResult.class)
    public Response<GeneralResult> isSwitch(@ModelAttribute @ApiParam("Parameter") isSwitchEnter enter) {
        return new Response<>(wthdrawalSiteServer.isSwitch(enter));
    }
}
