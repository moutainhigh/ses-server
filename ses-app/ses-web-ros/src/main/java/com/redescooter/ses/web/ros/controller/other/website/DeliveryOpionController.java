package com.redescooter.ses.web.ros.controller.other.website;


import com.redescooter.ses.api.common.annotation.IgnoreLoginCheck;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.web.ros.service.deliveryopion.DeliveryOpionService;
import com.redescooter.ses.web.ros.vo.deliveryopion.DeliveryOptionEditEnter;
import com.redescooter.ses.web.ros.vo.deliveryopion.DeliveryOptionSaveEnter;
import com.redescooter.ses.web.ros.vo.deliveryopion.DeliveryOptionSaveResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Api(tags = {"Delivery Option service"})
@RestController
@RequestMapping(value = "/deliveryopion", method = RequestMethod.POST)
public class DeliveryOpionController {

    @Autowired
    private DeliveryOpionService deliveryOpionService;

    @PostMapping(value = "/save")
    @ApiOperation(value = "新增操作", response = GeneralResult.class)
    public Response<GeneralResult> save(@ModelAttribute @ApiParam("Parameter") DeliveryOptionSaveEnter enter) {
        return new Response<>(deliveryOpionService.save(enter));
    }

    @PostMapping(value = "/edit")
    @ApiOperation(value = "编辑操作", response = GeneralResult.class)
    public Response<GeneralResult> edit(@ModelAttribute @ApiParam("Parameter") DeliveryOptionEditEnter enter) {
        return new Response<>(deliveryOpionService.edit(enter));
    }

    @IgnoreLoginCheck
    @PostMapping(value = "/details")
    @ApiOperation(value = "详情", response = DeliveryOptionSaveResult.class)
    public Response<DeliveryOptionSaveResult> details(@ModelAttribute @ApiParam("Parameter") IdEnter enter) {
        return new Response<>(deliveryOpionService.details(enter));
    }

    @IgnoreLoginCheck
    @PostMapping(value = "/list")
    @ApiOperation(value = "列表", response = DeliveryOptionSaveResult.class)
    public Response<List<DeliveryOptionSaveResult>> list(@ModelAttribute @ApiParam("Parameter") GeneralEnter enter) {
        return new Response<>(deliveryOpionService.list(enter));
    }
}
