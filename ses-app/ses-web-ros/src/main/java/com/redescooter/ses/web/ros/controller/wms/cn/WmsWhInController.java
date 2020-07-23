package com.redescooter.ses.web.ros.controller.wms.cn;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.web.ros.service.wms.cn.WmsWhInService;
import com.redescooter.ses.web.ros.vo.wms.cn.WmsInWhDetailsResult;
import com.redescooter.ses.web.ros.vo.wms.cn.WmsInWhResult;
import com.redescooter.ses.web.ros.vo.wms.cn.WmsProductListResult;
import com.redescooter.ses.web.ros.vo.wms.cn.WmsWhInDetailsEnter;
import com.redescooter.ses.web.ros.vo.wms.cn.WmsWhInEnter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @ClassNameWmsWhInController
 * @Description
 * @Author Joan
 * @Date2020/7/20 14:00
 * @Version V1.0
 **/
@Log4j2
@Api(tags = {"CN-WH入库管理"})
@CrossOrigin
@RestController
@RequestMapping(value = "/wms/cn/whIn")
public class WmsWhInController{

  @Autowired
  private WmsWhInService wmswhinservice;

  @PostMapping(value = "/countByType")
  @ApiOperation(value = "入库单类型统计", response = Map.class)
  public Response<Map<String, Integer>> countByType(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
    return new Response<>(wmswhinservice.countByType(enter));
  }

  @PostMapping(value = "/list")
  @ApiOperation(value = "已入库", response = WmsInWhResult.class)
  public Response<PageResult<WmsInWhResult>> wmsInWhResultList(@ModelAttribute @ApiParam("请求参数") WmsWhInEnter enter) {
    return new Response<>(wmswhinservice.getWmsInWhList(enter));
  }
  @PostMapping(value = "/detail")
  @ApiOperation(value = "详情", response = WmsInWhDetailsResult.class)
  public Response<WmsInWhDetailsResult> wmsInWhInfoDetails(@ModelAttribute @ApiParam("请求参数") WmsWhInDetailsEnter enter) {
    return new Response<>(wmswhinservice.details(enter));
  }

  @PostMapping(value = "/detailProductList")
  @ApiOperation(value = "入库部件/产品信息详情", response = WmsProductListResult.class)
  public Response<List<WmsProductListResult>> whInStockPendingList(@ModelAttribute @ApiParam("请求参数") WmsWhInDetailsEnter enter) {
    return new Response<>(wmswhinservice.productList(enter));
  }
}
