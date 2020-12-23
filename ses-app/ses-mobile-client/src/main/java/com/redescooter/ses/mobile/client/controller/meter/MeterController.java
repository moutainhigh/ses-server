package com.redescooter.ses.mobile.client.controller.meter;

import com.redescooter.ses.api.common.annotation.IgnoreLoginCheck;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.api.mobile.b.service.meter.MeterService;
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

    /**
     * 同步订单数量到平板(车辆每次开锁时调用)
     * @param enter
     * @return com.redescooter.ses.api.common.vo.base.Response<com.redescooter.ses.api.common.vo.base.GeneralResult>
     * @author assert
     * @date 2020/12/18
    */
    @ApiOperation(value = "同步订单数量到平板", notes = "同步订单数量到平板(车辆每次开锁时调用)")
    @PostMapping(value = "/syncOrderQuantity")
    public Response<GeneralResult> syncOrderQuantity(@ModelAttribute MeterOrderEnter enter) {
        return new Response<>(meterService.syncOrderQuantity(enter));
    }

}
