package com.redescooter.ses.web.ros.controller.wms.china;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.web.ros.service.wms.cn.china.WmsFinishStockService;
import com.redescooter.ses.web.ros.service.wms.cn.china.WmsMaterialStockService;
import com.redescooter.ses.web.ros.vo.wms.cn.china.WmsFinishScooterListEnter;
import com.redescooter.ses.web.ros.vo.wms.cn.china.WmsFinishScooterListResult;
import com.redescooter.ses.web.ros.vo.wms.cn.china.WmsfinishScooterDetailResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @description: 库存的控制器
 * @author: Aleks
 * @create: 2020/12/28 15:19
 */
@Api(tags = {"库存控制器"})
@CrossOrigin
@RestController
@RequestMapping(value = "/wms/stock")
public class WmsStockController {

    @Autowired
    private WmsFinishStockService wmsFinishStockService;

    @Autowired
    private WmsMaterialStockService wmsMaterialStockService;


    @PostMapping(value = "/finishScooterList")
    @ApiOperation(value = "成品库车辆库存列表", response = GeneralResult.class)
    public Response<PageResult<WmsFinishScooterListResult>> finishScooterList(@ModelAttribute @ApiParam("请求参数") WmsFinishScooterListEnter enter) {
        return new Response<>(wmsFinishStockService.finishScooterList(enter));
    }


    @PostMapping(value = "/finishScooterDetail")
    @ApiOperation(value = "成品库车辆库存详情", response = GeneralResult.class)
    public Response<WmsfinishScooterDetailResult> finishScooterDetail(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(wmsFinishStockService.finishScooterDetail(enter));
    }
}
