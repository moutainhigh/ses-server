package com.redescooter.ses.mobile.rps.controller.productwaitinwh;

import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.mobile.rps.service.productwaitinwh.ProductWaitInWhService;
import com.redescooter.ses.mobile.rps.vo.productwaitinwh.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassNameProWaitInWHController
 * @Description
 * @Author kyle
 * @Date2020/4/14 17:22
 * @Version V1.0
 **/
@Log4j2
@Api(tags = {"生产仓库入库"})
@CrossOrigin
@RestController
@RequestMapping(value = "/prowaitinwh")
public class ProductWaitInWhController {

    @Autowired
    private ProductWaitInWhService productWaitInWhService;

    @PostMapping(value = "/proWaitInWHList")
    @ApiOperation(value = "生产仓库待入库商品列表", response = ProductWaitInWhOneResult.class)
    public Response<PageResult<AllocateAndProductResult>> productWaitInWhList(@ModelAttribute @ApiParam("请求参数") PageEnter enter) {
        return new Response(productWaitInWhService.productWaitInWhList(enter));
    }

    @PostMapping(value = "/proWaitWHItemList")
    @ApiOperation(value = "生产仓库待入库商品部件列表", response = ProductWaitWhItemListResult.class)
    public <T> Response<PageResult<T>> productWaitWhItemList(@ModelAttribute @ApiParam("请求参数") ProductOrAllocateWaitInWhIdEnter enter) {
        return new Response(productWaitInWhService.productWaitWhItemList(enter));
    }

    @PostMapping(value = "/allocateWaitInWhItem")
    @ApiOperation(value = "调拨单入生产库商品部件列表详情", response = ProductWaitWhItemListResult.class)
    public Response<AllocateWaitInWhItemResult> allocateWaitInWhItem(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response(productWaitInWhService.allocateWaitInWhItem(enter));
    }

    @PostMapping(value = "/proWaitInWHInfoIn")
    @ApiOperation(value = "提交生产仓库入库信息", response = ProductWaitInWhInfoResult.class)
    public Response<ProductWaitInWhInfoResult> productWaitInWhInfoIn(@ModelAttribute @ApiParam("请求参数") ProductWaitInWhIdItemEnter enter) {
        return new Response(productWaitInWhService.productWaitInWhInfoIn(enter));
    }

}
