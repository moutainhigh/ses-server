package com.redescooter.ses.web.ros.controller.wms.cn;

import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.web.ros.service.wms.cn.WmsStockService;
import com.redescooter.ses.web.ros.vo.wms.cn.WmsStockAvailableResult;
import com.redescooter.ses.web.ros.vo.wms.cn.WmsStockEnter;
import com.redescooter.ses.web.ros.vo.wms.cn.WmsStockResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassNameWmsStockController
 * @Description
 * @Author Joan
 * @Date2020/7/17 10:33
 * @Version V1.0
 **/
@Log4j2
@Api(tags = {"CN-WH仓储库存"})
@CrossOrigin
@RestController
@RequestMapping(value = "/wms/cn/stock")
public class WmsStockController{

  @Autowired
  private WmsStockService wmsStockService;

  @PostMapping(value = "/availableList")
  @ApiOperation(value = "显示可用列表", response = WmsStockAvailableResult.class)
  public Response<PageResult<WmsStockAvailableResult>> stockAvailableList(@ModelAttribute @ApiParam("请求参数") WmsStockEnter enter) {
    return new Response<>(wmsStockService.getStockAvailableList(enter));
  }

  @PostMapping(value = "/predictedList")
  @ApiOperation(value = "待生产列表", response = WmsStockResult.class)
  public Response<PageResult<WmsStockResult>> stockResultList(@ModelAttribute @ApiParam("请求参数") WmsStockEnter enter) {
    return new Response<>(wmsStockService.getStockPredictedList(enter));
  }

  @PostMapping(value = "/storedList")
  @ApiOperation(value = "待入库列表", response = WmsStockResult.class)
  public Response<PageResult<WmsStockResult>> wmsStockList(@ModelAttribute @ApiParam("请求参数") WmsStockEnter enter) {
    return new Response<>(wmsStockService.getStockStoredList(enter));
  }

  @PostMapping(value = "/outWhList")
  @ApiOperation(value = "待出库列表", response = WmsStockResult.class)
  public Response<PageResult<WmsStockResult>> wmsStockOutStockList(@ModelAttribute @ApiParam("请求参数") WmsStockEnter enter) {
    return new Response<>(wmsStockService.getStockOutWhList(enter));
  }
}
