package com.redescooter.ses.mobile.rps.controller.entrustorder;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.mobile.rps.service.entrustorder.EntrustOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 委托单接口管理
 * @author assert
 * @date 2020/12/30 18:02
 */
@Api(tags = {"委托单管理"})
@CrossOrigin
@RestController
@RequestMapping(value = "/entrust/order", produces = MediaType.APPLICATION_JSON_VALUE)
public class EntrustOrderController {

    @Resource
    private EntrustOrderService entrustOrderService;


    /**
     * 委托单类型数量统计
     * @param enter
     * @return com.redescooter.ses.api.common.vo.base.Response<java.util.Map<java.lang.Integer,java.lang.Integer>>
     * @author assert
     * @date 2020/12/30
    */
    @ApiOperation(value = "委托单类型数量统计", notes = "委托单类型数量统计")
    @PostMapping(value = "/countByType")
    public Response<Map<Integer, Integer>> getEntrustOrderTypeCount(@ModelAttribute GeneralEnter enter) {
        return new Response<>(entrustOrderService.getEntrustOrderTypeCount(enter));
    }



}
