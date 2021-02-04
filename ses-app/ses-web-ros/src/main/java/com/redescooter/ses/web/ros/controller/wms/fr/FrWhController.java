package com.redescooter.ses.web.ros.controller.wms.fr;

import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.web.ros.service.wms.cn.fr.FrWhService;
import com.redescooter.ses.web.ros.vo.bom.combination.CombinationListEnter;
import com.redescooter.ses.web.ros.vo.wms.cn.WmsDetailResult;
import com.redescooter.ses.web.ros.vo.wms.cn.china.*;
import com.redescooter.ses.web.ros.vo.wms.cn.fr.FrStockCountResult;
import com.redescooter.ses.web.ros.vo.wms.cn.fr.FrTodayInOrOutStockCountResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @ClassName:FrWhController
 * @description: 法国仓库的控制层
 * @author: Aleks
 * @Version：1.3
 * @create: 2020/12/27 15:10
 */
@Api(tags = {"法国仓库控制器"})
@CrossOrigin
@RestController
@RequestMapping(value = "/fr/wh")
public class FrWhController {


    @Autowired
    private FrWhService frWhService;


    @PostMapping(value = "/frInOrOutCount")
    @ApiOperation(value = "出入库统计", response = FrTodayInOrOutStockCountResult.class)
    public Response<List<FrTodayInOrOutStockCountResult>> inOrOutCount(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(frWhService.inOrOutCount(enter));
    }


    @PostMapping(value = "/frStockCount")
    @ApiOperation(value = "库存统计", response = FrStockCountResult.class)
    public Response<FrStockCountResult> frStockCount(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(frWhService.stockCount(enter));
    }


    @PostMapping(value = "/frScooterList")
    @ApiOperation(value = "法国仓库车辆库存列表", response = WmsFinishScooterListResult.class)
    public Response<PageResult<WmsFinishScooterListResult>> frScooterList(@ModelAttribute @ApiParam("请求参数") WmsFinishScooterListEnter enter) {
        return new Response<>(frWhService.frScooterList(enter));
    }


    @PostMapping(value = "/frScooterDetail")
    @ApiOperation(value = "法国仓库车辆库存详情", response = WmsfinishScooterDetailResult.class)
    public Response<WmsfinishScooterDetailResult> frScooterDetail(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(frWhService.frScooterDetail(enter));
    }


    @PostMapping(value = "/frCombinList")
    @ApiOperation(value = "法国组装件库存列表", response = WmsFinishCombinListResult.class)
    public Response<PageResult<WmsFinishCombinListResult>> frCombinList(@ModelAttribute @ApiParam("请求参数") CombinationListEnter enter) {
        return new Response<>(frWhService.frCombinList(enter));
    }


    @PostMapping(value = "/frCombinDetail")
    @ApiOperation(value = "法国组装件库存详情", response = WmsfinishCombinDetailResult.class)
    public Response<WmsfinishCombinDetailResult> frCombinDetail(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(frWhService.frCombinDetail(enter));
    }


    @PostMapping(value = "/frPartsList")
    @ApiOperation(value = "法国部件的库存列表", response = MaterialStockPartsListResult.class)
    public Response<PageResult<MaterialStockPartsListResult>> frPartsList(@ModelAttribute @ApiParam("请求参数") MaterialStockPartsListEnter enter) {
        return new Response<>(frWhService.frPartsList(enter));
    }


    @PostMapping(value = "/frPartsDetail")
    @ApiOperation(value = "法国部件的库存详情", response = MaterialpartsStockDetailResult.class)
    public Response<MaterialpartsStockDetailResult> frPartsDetail(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(frWhService.frPartsDetail(enter));
    }


    @PostMapping(value = "/scooterNum")
    @ApiOperation(value = "新建出库单时，计算同车型/颜色的车辆可用库存时多少（出库数量要小于库存数）", response = IntResult.class)
    public Response<IntResult> scooterNum(@ModelAttribute @ApiParam("请求参数") WmsFinishScooterListEnter enter) {
        return new Response<>(frWhService.scooterNum(enter, 2));
    }


    @PostMapping(value = "/stockTabCount")
    @ApiOperation(value = "法国仓库tab的数量统计（车辆/组装件/部件）", response = Map.class)
    public Response<Map<String, Integer>> stockTabCount(@ModelAttribute @ApiParam("请求参数") WmsStockCountEnter enter) {
        return new Response<>(frWhService.stockTabCount(enter));
    }

    /**
     * 法国仓库车辆,组装件和部件详情
     */
    @PostMapping(value = "/detail")
    @ApiOperation(value = "法国仓库车辆,组装件和部件详情", tags = "法国仓库车辆,组装件和部件详情")
    public Response<PageResult<WmsDetailResult>> getDetail(@ModelAttribute WmsDetailEnter enter) {
        return new Response<>(frWhService.getDetail(enter));
    }

}
