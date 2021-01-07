package com.redescooter.ses.web.website.controller;

import com.redescooter.ses.api.common.annotation.IgnoreLoginCheck;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.web.website.service.DeliveryService;
import com.redescooter.ses.web.website.vo.delivery.DeliveryModeResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author jerry
 * @Date 2021/1/6 7:45 下午
 * @Description 送货方式服务
 **/
@Api(tags = {"Delivery"})
@CrossOrigin
@RestController
@RequestMapping(value = "/delivery")
public class DeliveryController {

    @Autowired
    private DeliveryService deliveryService;

    /**
     * 配送类型模板
     *
     * @param enter
     * @return
     */
    @IgnoreLoginCheck
    @PostMapping(value = "/typelist")
    @ApiOperation(value = "Delivery Method list", response = DeliveryModeResult.class)
    public Response<List<DeliveryModeResult>> typelist(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(deliveryService.deliveryTypelist(enter));
    }

}
