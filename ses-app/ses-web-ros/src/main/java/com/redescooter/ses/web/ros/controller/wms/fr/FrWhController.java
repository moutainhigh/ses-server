package com.redescooter.ses.web.ros.controller.wms.fr;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.web.ros.service.wms.cn.fr.FrWhService;
import com.redescooter.ses.web.ros.vo.wms.cn.fr.FrStockCountResult;
import com.redescooter.ses.web.ros.vo.wms.cn.fr.FrTodayInOrOutStockCountResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public Response<FrStockCountResult> stockCount(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(frWhService.stockCount(enter));
    }



}
