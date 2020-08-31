package com.redescooter.ses.web.ros.controller.sellsy;

import com.redescooter.ses.api.common.annotation.IgnoreLoginCheck;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.web.ros.service.sellsy.SellsyAddressService;
import com.redescooter.ses.web.ros.vo.sellsy.enter.SellsyIdEnter;
import com.redescooter.ses.web.ros.vo.sellsy.result.address.SellsyAddressResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"ROS-Sellsy-Address"})
@CrossOrigin
@RestController
@RequestMapping(value = "/sellsy/address")
public class SellsyAddressController {

    @Autowired
    private SellsyAddressService SellsyAddressService;

    @IgnoreLoginCheck
    @ApiOperation(value = "地址列表", response = SellsyAddressResult.class)
    @PostMapping(value = "/queryAddressList")
    public Response<List<SellsyAddressResult>> queryAddressList() {
        return new Response<>(SellsyAddressService.queryAddressList());
    }

    @IgnoreLoginCheck
    @ApiOperation(value = "地址详情", response = SellsyAddressResult.class)
    @PostMapping(value = "/queryAddressOne")
    public Response<SellsyAddressResult> queryAddressOne(@ModelAttribute @ApiParam("请求参数") SellsyIdEnter enter) {
        return new Response<>(SellsyAddressService.queryAddressOne(enter));
    }

}
