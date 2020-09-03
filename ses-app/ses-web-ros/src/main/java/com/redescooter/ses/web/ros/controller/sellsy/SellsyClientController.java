package com.redescooter.ses.web.ros.controller.sellsy;

import com.redescooter.ses.api.common.annotation.IgnoreLoginCheck;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.web.ros.service.sellsy.SellsyClientService;
import com.redescooter.ses.web.ros.vo.sellsy.enter.client.SellsyClientListEnter;
import com.redescooter.ses.web.ros.vo.sellsy.enter.client.SellsyQueryClientOneEnter;
import com.redescooter.ses.web.ros.vo.sellsy.result.client.SellsyClientResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"ROS-Sellsy-Client"})
@CrossOrigin
@RestController
@RequestMapping(value = "/sellsy/client")
public class SellsyClientController {

    @Autowired
    private SellsyClientService sellsyClientService;

    @IgnoreLoginCheck
    @ApiOperation(value = "查询指定客户", response = SellsyClientResult.class)
    @PostMapping(value = "/queryClientById")
    public Response<SellsyClientResult>
        queryClientById(@ModelAttribute @ApiParam("请求参数") SellsyQueryClientOneEnter enter) {
        return new Response<>(sellsyClientService.queryClientOne(enter));
    }

    @IgnoreLoginCheck
    @ApiOperation(value = "查询客户列表", response = SellsyClientResult.class)
    @PostMapping(value = "/queryClientList")
    public Response<List<SellsyClientResult>> queryClientList(@ModelAttribute @ApiParam("请求参数") SellsyClientListEnter enter) {
        return new Response<>(sellsyClientService.queryClientList(enter));
    }

}
