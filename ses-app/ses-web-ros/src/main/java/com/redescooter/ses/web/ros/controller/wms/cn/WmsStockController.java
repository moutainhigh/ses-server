package com.redescooter.ses.web.ros.controller.wms.cn;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.web.ros.service.wms.cn.WmsStockService;
import com.redescooter.ses.web.ros.vo.wms.cn.WmsStockAvailableResult;
import com.redescooter.ses.web.ros.vo.wms.cn.WmsStockEnter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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



  @PostMapping(value = "/countByType")
  @ApiOperation(value = "库存类型统计", response = Map.class)
  public Response<Map<String, Integer>> countByType(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
    return new Response<>(wmsStockService.countByType(enter));
  }

  @PostMapping(value = "/list")
  @ApiOperation(value = "显示可用列表", response = WmsStockAvailableResult.class)
  public Response<PageResult<WmsStockAvailableResult>> stockAvailableList(@ModelAttribute @ApiParam("请求参数") WmsStockEnter enter) {
    return new Response<>(wmsStockService.list(enter));
  }
}
