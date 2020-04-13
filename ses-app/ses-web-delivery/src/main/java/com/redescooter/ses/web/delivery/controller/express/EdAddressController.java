package com.redescooter.ses.web.delivery.controller.express;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.web.delivery.service.EdAddressServices;
import com.redescooter.ses.web.delivery.vo.edorder.GetAddressOfLonLatEnter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName EdAddressController
 * @Author Jerry
 * @date 2020/02/24 11:14
 * @Description: 快递单据地址服务
 */
@Api(tags = {"单据地址服务"})
@CrossOrigin
@RestController
@RequestMapping(value = "/ed/address", method = RequestMethod.POST)
public class EdAddressController {

    @Autowired
    private EdAddressServices addressServices;

    @PostMapping(value = "/getAddressOfLonLat")
    @ApiOperation(value = "根据地址获取经纬度", response = GeneralResult.class)
    public Response<GeneralResult> getAddressOfLonLat(@ModelAttribute @ApiParam("请求参数") GetAddressOfLonLatEnter enter) {
        return new Response<>(addressServices.getAddressOfLonLat(enter));
    }

}
