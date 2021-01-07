package com.redescooter.ses.web.website.controller;

import com.redescooter.ses.api.common.annotation.IgnoreLoginCheck;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.web.website.service.DistributorService;
import com.redescooter.ses.web.website.vo.distributor.DistributorDetailsResult;
import com.redescooter.ses.web.website.vo.distributor.QueryDistributorEnter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 13/1/2020 4:43 下午
 * @ClassName: ProductModelController
 * @Function: 经销商管理
 */
@Api(tags = {"Distributor Management"})
@CrossOrigin
@RestController
@RequestMapping(value = "/distributor")
public class DistributorController {

    @Autowired
    private DistributorService distributorService;

    @IgnoreLoginCheck
    @PostMapping(value = "/list")
    @ApiOperation(value = "经销商列表", response = DistributorDetailsResult.class)
    public Response<List<DistributorDetailsResult>> list(@ModelAttribute @ApiParam("请求参数") QueryDistributorEnter enter) {
        return new Response<>(distributorService.getDistributorList(enter));
    }

    @IgnoreLoginCheck
    @PostMapping(value = "/details")
    @ApiOperation(value = "经销商详情", response = DistributorDetailsResult.class)
    public Response<DistributorDetailsResult> details(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(distributorService.getDistributorDetails(enter));
    }
}
