package com.redescooter.ses.web.ros.controller.sellsy;

import com.redescooter.ses.api.common.annotation.IgnoreLoginCheck;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.web.ros.service.sellsy.SellsyCatalogueService;
import com.redescooter.ses.web.ros.vo.sellsy.enter.catalogue.SellsyCatalogueListEnter;
import com.redescooter.ses.web.ros.vo.sellsy.enter.catalogue.SellsyQueryCatalogueOneEnter;
import com.redescooter.ses.web.ros.vo.sellsy.result.catalogue.SellsyCatalogueResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"ROS-Sellsy-Catalogue"})
@CrossOrigin
@RestController
@RequestMapping(value = "/sellsy/catalogue")
public class SellsyCatalogueController {

    @Autowired
    private SellsyCatalogueService sellsyCatalogueService;

    @IgnoreLoginCheck
    @ApiOperation(value = "产品列表", response = SellsyCatalogueResult.class)
    @PostMapping(value = "/queryCatalogueList")
    public Response<List<SellsyCatalogueResult>>
        queryCatalogueList(@ModelAttribute @ApiParam("请求参数") SellsyCatalogueListEnter enter) {
        return new Response<>(sellsyCatalogueService.queryCatalogueList(enter));
    }

    @IgnoreLoginCheck
    @ApiOperation(value = "产品详情", response = SellsyCatalogueResult.class)
    @PostMapping(value = "/queryCatalogueOne")
    public Response<SellsyCatalogueResult>
        queryCatalogueOne(@ModelAttribute @ApiParam("请求参数") SellsyQueryCatalogueOneEnter enter) {
        return new Response<>(sellsyCatalogueService.queryCatalogueOne(enter));
    }
}
