package com.redescooter.ses.mobile.rps.controller.purchasinwh;

import com.redescooter.ses.api.common.vo.base.PageEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.mobile.rps.service.purchasinwh.PurchasPutStroageService;
import com.redescooter.ses.mobile.rps.vo.purchasinwh.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Log4j2
@Api(tags = {"采购仓库待入库信息"})
@CrossOrigin
@RestController
@RequestMapping(value = "/purchas")
public class PurchasPutStroageController{

    @Autowired
    PurchasPutStroageService purchasPutStroageService;

    @PostMapping(value = "/list")
    @ApiOperation(value = "采购仓库待入库列表", response = PutStorageResult.class)
    public Response<PageResult<PutStorageResult>> purchasPutStroageList(@ModelAttribute @ApiParam("请求参数") PageEnter enter) {
        return new Response<>(purchasPutStroageService.purchasPutStroageList(enter));
    }

    @PostMapping(value = "/detailsList")
    @ApiOperation(value = "采购仓库待入库部件列表", response = PurchasDetailsListResult.class)
    public Response<PageResult<PurchasDetailsListResult>> storageDetailsList(@ModelAttribute @ApiParam("请求参数") PurchasDetailsEnter enter) {
        return new Response<>(purchasPutStroageService.storageDetailsList(enter));
    }

    @PostMapping(value = "/haveSucceed")
    @ApiOperation(value = "有ID入库成功接口", response = HaveIdPartsResult.class)
    public Response<HaveIdPartsResult> HaveIDPartsResult(@ModelAttribute @ApiParam("请求参数") HaveIdEnter enter) {
        return new Response<>(purchasPutStroageService.haveIdPartsResult(enter));
    }

    @PostMapping(value = "/notId")
    @ApiOperation(value = "无ID入库界面", response = NotIdPartsResult.class)
    public Response<NotIdPartsResult> NotIDPartsResult(@ModelAttribute @ApiParam("请求参数") NotIdDetailsEnter enter) {
        return new Response<>(purchasPutStroageService.notIdPartsResult(enter));
    }

    @PostMapping(value = "/notIdSucceed")
    @ApiOperation(value = "无ID入库成功接口", response = PurchasDetailsListResult.class)
    public Response<NotIdPartsSucceedResult> NotIDPartsSucceedResult(@ModelAttribute @ApiParam("请求参数") NotIdEnter enter) {
        return new Response<>(purchasPutStroageService.notIdInWh(enter));
    }
}
