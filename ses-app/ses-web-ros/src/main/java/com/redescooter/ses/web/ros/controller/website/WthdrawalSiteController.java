package com.redescooter.ses.web.ros.controller.website;

import com.redescooter.ses.api.common.annotation.IgnoreLoginCheck;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.web.ros.service.wthdrawalsite.WthdrawalSiteServer;
import com.redescooter.ses.web.ros.vo.deliveryopion.DeliveryOptionEditEnter;
import com.redescooter.ses.web.ros.vo.deliveryopion.DeliveryOptionSaveEnter;
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
@Api(tags = {"Wthdrawal Site service"})
@RestController
@RequestMapping(value = "/wthdrawalsite", method = RequestMethod.POST)
public class WthdrawalSiteController {

    @Autowired
    private WthdrawalSiteServer wthdrawalSiteServer;

    @PostMapping(value = "/save")
    @ApiOperation(value = "Save Wthdrawal Site", response = GeneralResult.class)
    public Response<GeneralResult> save(@ModelAttribute @ApiParam("Parameter") WthdrawalSiteSaveEnter enter) {
        return new Response<>(wthdrawalSiteServer.save(enter));
    }

    @PostMapping(value = "/edit/{id}")
    @ApiOperation(value = "Wthdrawal Site Edit", response = GeneralResult.class)
    public Response<GeneralResult> edit(@ModelAttribute @ApiParam("Parameter") WthdrawalSiteEditEnter enter, @PathVariable("id") Long id) {
        return new Response<>(wthdrawalSiteServer.edit(enter, id));
    }

    @IgnoreLoginCheck
    @PostMapping(value = "/details/{id}")
    @ApiOperation(value = "Wthdrawal Site Details", response = DeliveryOptionSaveResult.class)
    public Response<WthdrawalSiteResult> details(@ModelAttribute @ApiParam("Parameter") GeneralEnter enter, @PathVariable("id") Long id) {
        return new Response<>(wthdrawalSiteServer.details(enter, id));
    }

    @IgnoreLoginCheck
    @PostMapping(value = "/list")
    @ApiOperation(value = "Wthdrawal Site List Table", response = DeliveryOptionSaveResult.class)
    public Response<List<WthdrawalSiteResult>> list(@ModelAttribute @ApiParam("Parameter") GeneralEnter enter) {
        return new Response<>(wthdrawalSiteServer.list(enter));
    }

    @IgnoreLoginCheck
    @PostMapping(value = "/isswitch/{id}")
    @ApiOperation(value = "Opening and closing,[0 open, 1 close]", response = DeliveryOptionSaveResult.class)
    public Response<GeneralResult> isSwitch(@ModelAttribute @ApiParam("Parameter") isSwitchEnter enter, @PathVariable("id") Long id) {
        return new Response<>(wthdrawalSiteServer.isSwitch(enter,id));
    }
}
