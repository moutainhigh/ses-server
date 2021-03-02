package com.redescooter.ses.web.website.controller.business;

import com.redescooter.ses.api.common.annotation.IgnoreLoginCheck;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.web.website.service.DealerService;
import com.redescooter.ses.web.website.vo.distributor.AddDealerEnter;
import com.redescooter.ses.web.website.vo.distributor.DealerDetailsResult;
import com.redescooter.ses.web.website.vo.distributor.MapDealerDetailsResult;
import com.redescooter.ses.web.website.vo.distributor.QueryDealerEnter;
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
@Api(tags = {"Dealer"})
@CrossOrigin
@RestController
@RequestMapping(value = "/dealer")
public class DealerController {

    @Autowired
    private DealerService dealerService;

//    @PostMapping(value = "/add")
//    @ApiOperation(value = "New dealer", response = DealerDetailsResult.class)
//    public Response<GeneralResult> add(@ModelAttribute @ApiParam("请求参数") AddDealerEnter enter) {
//        return new Response<>(dealerService.addDistributor(enter));
//    }

    @IgnoreLoginCheck
    @PostMapping(value = "/list")
    @ApiOperation(value = "Dealer list", response = MapDealerDetailsResult.class)
    public Response<List<MapDealerDetailsResult>> list(@ModelAttribute @ApiParam("请求参数") QueryDealerEnter enter) {
        return new Response<>(dealerService.getDistributorList(enter));
    }

    @IgnoreLoginCheck
    @PostMapping(value = "/details")
    @ApiOperation(value = "Dealer details", response = DealerDetailsResult.class)
    public Response<DealerDetailsResult> details(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(dealerService.getDistributorDetails(enter));
    }
}
