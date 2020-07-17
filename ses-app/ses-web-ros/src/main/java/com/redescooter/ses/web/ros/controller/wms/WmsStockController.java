package com.redescooter.ses.web.ros.controller.wms;

import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.web.ros.service.wms.WmsStockService;
import com.redescooter.ses.web.ros.vo.wms.WmsStockAvailableResult;
import com.redescooter.ses.web.ros.vo.wms.WmsStockEnter;
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
@Api(tags = {"仓储库存可用"})
@CrossOrigin
@RestController
@RequestMapping(value = "/wms/stock/")
public class WmsStockController{

  @Autowired
  private WmsStockService wmsStockService;

  @PostMapping(value = "/available")
  @ApiOperation(value = "显示列表", response = WmsStockAvailableResult.class)
  public Response<PageResult<WmsStockAvailableResult>> stockAvailableList(@ModelAttribute @ApiParam("请求参数") WmsStockEnter enter) {
    return new Response<>(wmsStockService.getStockAvailableList(enter));
  }
}
