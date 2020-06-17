package com.redescooter.ses.mobile.client.controller;

import com.redescooter.ses.api.common.annotation.IgnoreLoginCheck;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.api.foundation.service.VersionBaseService;
import com.redescooter.ses.api.foundation.vo.app.VersionTypeEnter;
import com.redescooter.ses.api.foundation.vo.app.VersionTypeResult;
import com.redescooter.ses.api.mobile.b.vo.DeliveryListEnter;
import com.redescooter.ses.api.mobile.b.vo.DeliveryListResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassNameAppBaseController
 * @Description
 * @Author Joan
 * @Date2020/6/17 11:43
 * @Version V1.0
 **/
@Slf4j
@Api(tags = {"Version"})
@CrossOrigin
@RestController
@RequestMapping(value = "/version")
public class VersionbaseController {
  @Reference
  private VersionBaseService versionBaseService;

  @ApiOperation(value = "获取仪表平板最新版本")
  @PostMapping(value = "/getCarInstrumentVersion")
  @IgnoreLoginCheck
  public Response<VersionTypeResult> getcNewVersionData(@ModelAttribute VersionTypeEnter enter) {
    return new Response(versionBaseService.getVersionData(enter));
  }
  @ApiOperation(value = "获取APP最新版本")
  @PostMapping(value = "/newAppVersion")
  public Response<VersionTypeResult> getAppNewVersionData(@ModelAttribute VersionTypeEnter enter) {
    return new Response(versionBaseService.getVersionData(enter));
  }

}
