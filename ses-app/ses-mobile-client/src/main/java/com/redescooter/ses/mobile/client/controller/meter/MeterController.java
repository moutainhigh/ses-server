package com.redescooter.ses.mobile.client.controller.meter;

import com.redescooter.ses.api.common.annotation.IgnoreLoginCheck;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.api.mobile.b.service.meter.MeterService;
import com.redescooter.ses.api.mobile.b.vo.DeliveryListEnter;
import com.redescooter.ses.api.mobile.b.vo.DeliveryListResult;
import com.redescooter.ses.api.mobile.b.vo.meter.MeterDeliveryOrderReuslt;
import com.redescooter.ses.api.mobile.b.vo.meter.MeterOrderEnter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName:MeterController
 * @description: MeterController
 * @author: Alex
 * @Version：1.3
 * @create: 2020/11/16 17:26 
 */
@Slf4j
@Api(tags = {"车载仪表临时接口"})
@CrossOrigin
@RestController
@RequestMapping(value = "/meter", method = RequestMethod.POST)
public class MeterController {

    @Reference
    private MeterService meterService;

    @IgnoreLoginCheck
    @ApiOperation(value = "仪表订单")
    @RequestMapping(value = "/meterOrder")
    public Response<MeterDeliveryOrderReuslt> meterOrder(@ModelAttribute MeterOrderEnter enter) {
        return new Response<>(meterService.meterOrder(enter));
    }


}
