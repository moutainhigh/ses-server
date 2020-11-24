package com.redescooter.ses.web.ros.controller.website;


import com.redescooter.ses.api.common.annotation.IgnoreLoginCheck;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
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
    @ApiOperation(value = "Save Option", response = GeneralResult.class)
    public Response<GeneralResult> save(@ModelAttribute @ApiParam("Parameter") DeliveryOptionSaveEnter enter) {
        return new Response<>(deliveryOpionService.save(enter));
    }

    @PostMapping(value = "/edit/{id}")
    @ApiOperation(value = "Option Edit", response = GeneralResult.class)
    public Response<GeneralResult> edit(@ModelAttribute @ApiParam("Parameter") DeliveryOptionEditEnter enter, @PathVariable("id") Long id) {
        return new Response<>(deliveryOpionService.edit(enter, id));

    }

    @IgnoreLoginCheck
    @PostMapping(value = "/details/{id}")
    @ApiOperation(value = "Option Details", response = DeliveryOptionSaveResult.class)
    public Response<DeliveryOptionSaveResult> details(@ModelAttribute @ApiParam("Parameter") GeneralEnter enter, @PathVariable("id") Long id) {
        return new Response<>(deliveryOpionService.details(enter, id));
    }

    @IgnoreLoginCheck
    @PostMapping(value = "/list")
    @ApiOperation(value = "Option List Table", response = DeliveryOptionSaveResult.class)
    public Response<List<DeliveryOptionSaveResult>> list(@ModelAttribute @ApiParam("Parameter") GeneralEnter enter) {
        return new Response<>(deliveryOpionService.list(enter));
    }
}
