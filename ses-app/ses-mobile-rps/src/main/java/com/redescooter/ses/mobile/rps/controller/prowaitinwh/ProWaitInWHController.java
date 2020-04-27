package com.redescooter.ses.mobile.rps.controller.prowaitinwh;

import com.redescooter.ses.api.common.vo.base.PageEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.mobile.rps.service.prowaitinwh.ProWaitInWHService;
import com.redescooter.ses.mobile.rps.vo.prowaitinwh.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.ss.formula.functions.T;
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
@RequestMapping(value = "/rps/prowaitinwh/")
public class ProWaitInWHController {

    @Autowired
    private ProWaitInWHService proWaitInWHService;

    @PostMapping(value = "/proWaitInWHList")
    @ApiOperation(value = "生产仓库待入库商品列表", response = ProWaitInWHOneResult.class)
    public Response<PageResult<AllocateAndProductResult>> proWaitInWHList(@ModelAttribute @ApiParam("请求参数") PageEnter enter) {
        return new Response(proWaitInWHService.proWaitInWHList(enter));
    }

    @PostMapping(value = "/proWaitWHItemList")
    @ApiOperation(value = "生产仓库待入库商品部件列表", response = ProWaitWHItemListResult.class)
    public <T> Response<PageResult<T>> proWaitWHItemList(@ModelAttribute @ApiParam("请求参数") ProOrAllocateWaitInWHIdEnter enter) {
        return new Response(proWaitInWHService.proWaitWHItemList(enter));
    }

    @PostMapping(value = "/proWaitInWHInfoIn")
    @ApiOperation(value = "提交生产仓库入库信息", response = ProWaitInWHInfoResult.class)
    public Response<ProWaitInWHInfoResult> proWaitInWHInfoIn(@ModelAttribute @ApiParam("请求参数") ProWaitInWHIdItemEnter enter) {
        return new Response(proWaitInWHService.proWaitInWHInfoIn(enter));
    }

}
