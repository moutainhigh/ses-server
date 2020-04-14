package com.redescooter.ses.mobile.rps.controller.prowaitinwh;

import com.redescooter.ses.api.common.annotation.IgnoreLoginCheck;
import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.mobile.rps.service.prowaitinwh.ProWaitInWHService;
import com.redescooter.ses.mobile.rps.vo.prowaitinwh.ProWaitInWHInfoEnter;
import com.redescooter.ses.mobile.rps.vo.prowaitinwh.ProWaitInWHInfoResult;
import com.redescooter.ses.mobile.rps.vo.prowaitinwh.ProWaitInWHListResult;
import com.redescooter.ses.mobile.rps.vo.prowaitinwh.ProWaitWHItemListResult;
import com.redescooter.ses.mobile.rps.vo.scooterqc.ScooterQcItemResult;
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
@RequestMapping(value = "/rps/prowaitinwh/")
public class ProWaitInWHController {

    @Autowired
    ProWaitInWHService proWaitInWHService;

    /**
     * @Author kyle
     * @Description //1、查询生产仓库待入库列表
     * @Date  2020/4/14 17:24
     * @Param [enter]
     * @return
     **/
    @IgnoreLoginCheck
    @PostMapping(value = "/proWaitInWHList")
    @ApiOperation(value = "生产仓库待入库列表", response = ProWaitInWHListResult.class)
    public Response<PageResult<ProWaitInWHListResult>> proWaitInWHList(@ModelAttribute @ApiParam("请求参数") PageEnter enter) {
        return new Response(proWaitInWHService.proWaitInWHList(enter));
    }

    /**
     * @Author kyle
     * @Description //1、根据组装单id查询对应的部件详情列表
     * @Date  2020/4/14 17:26
     * @Param [enter]
     * @return
     **/
    @IgnoreLoginCheck
    @PostMapping(value = "/proWaitWHItemList")
    @ApiOperation(value = "生产仓库待入库部件列表", response = ProWaitWHItemListResult.class)
    public Response<ScooterQcItemResult> proWaitWHItemList(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response(proWaitInWHService.proWaitWHItemList(enter));
    }

    /**
     * @Author kyle
     * @Description //1、根据具体的部品id查询生产仓库待入库详情
     * @Date  2020/4/14 17:35
     * @Param [enter]
     * @return
     **/
    @IgnoreLoginCheck
    @PostMapping(value = "/proWaitInWHInfoOut")
    @ApiOperation(value = "查询生产仓库待入库详情", response = ProWaitInWHInfoResult.class)
    public Response<ScooterQcItemResult> proWaitInWHInfoOut(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response(proWaitInWHService.proWaitInWHInfoOut(enter));
    }

    /**
     * @Author kyle
     * @Description //1、提交生产仓库入库信息
     * @Date  2020/4/14 17:50
     * @Param [enter]
     * @return
     **/
    @IgnoreLoginCheck
    @PostMapping(value = "/proWaitInWHInfoIn")
    @ApiOperation(value = "提交生产仓库入库信息", response = ProWaitInWHInfoResult.class)
    public Response<ProWaitInWHInfoResult> proWaitInWHInfoIn(@ModelAttribute @ApiParam("请求参数") ProWaitInWHInfoEnter enter) {
        return new Response(proWaitInWHService.proWaitInWHInfoIn(enter));
    }


}
