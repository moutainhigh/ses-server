package com.redescooter.ses.web.ros.controller.restproduction.inwhouse;

import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.web.ros.service.restproductionorder.inwhouse.InWhouseService;
import com.redescooter.ses.web.ros.vo.restproductionorder.inwhouse.InWhouseListEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.inwhouse.InWhouseListResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassNameAllocateOrderController
 * @Description
 * @Author Aleks
 * @Date2020/10/23 11:37
 * @Version V1.0
 **/
@Api(tags = {"入库单控制层"})
@CrossOrigin
@RestController
@RequestMapping(value = "/sys/inWhouse/order")
public class InWhouseOrderController {


    @Autowired
    private InWhouseService inWhouseService;


    @PostMapping(value = "/list")
    @ApiOperation(value = "入库单列表", response = InWhouseListResult.class)
    public Response<PageResult<InWhouseListResult>> inWhouseList(@ModelAttribute @ApiParam("请求参数") InWhouseListEnter enter) {
        return new Response<>(inWhouseService.inWhouseList(enter));
    }

}
