package com.redescooter.ses.web.ros.controller.wms.china;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.web.ros.service.wms.cn.china.ChinaWhService;
import com.redescooter.ses.web.ros.vo.wms.cn.china.ChinaInOrOutCountResult;
import com.redescooter.ses.web.ros.vo.wms.cn.china.StockCountResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName:ChinaWhController
 * @description: 中国仓库控制层
 * @author: Aleks
 * @Version：1.3
 * @create: 2020/12/27 14:59
 */
@Api(tags = {"中国仓库控制器"})
@CrossOrigin
@RestController
@RequestMapping(value = "/cn/wh")
public class ChinaWhController {

    @Autowired
    private ChinaWhService chinaWhService;


    @PostMapping(value = "/inOrOutCount")
    @ApiOperation(value = "出入库统计", response = ChinaInOrOutCountResult.class)
    public Response<List<ChinaInOrOutCountResult>> inOrOutCount(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(chinaWhService.inOrOutCount(enter));
    }


    @PostMapping(value = "/stockCount")
    @ApiOperation(value = "库存统计", response = StockCountResult.class)
    public Response<List<StockCountResult>> stockCount(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(chinaWhService.stockCount(enter));
    }


}
