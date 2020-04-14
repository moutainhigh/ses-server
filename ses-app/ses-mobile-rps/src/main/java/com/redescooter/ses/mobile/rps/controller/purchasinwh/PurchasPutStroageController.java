package com.redescooter.ses.mobile.rps.controller.purchasinwh;

import com.redescooter.ses.api.common.annotation.IgnoreLoginCheck;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.mobile.rps.exception.ExceptionCodeEnums;
import com.redescooter.ses.mobile.rps.exception.SesMobileRpsException;
import com.redescooter.ses.mobile.rps.service.impl.purchasinwh.PurchasPutStroageService;
import com.redescooter.ses.mobile.rps.vo.materialqc.MaterialQcListResult;
import com.redescooter.ses.mobile.rps.vo.purchasinwh.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Log4j2
@Api(tags = {"采购仓库待入库信息"})
@CrossOrigin
@RestController
@RequestMapping(value = "/rps")
public class PurchasPutStroageController{

    @Autowired
     PurchasPutStroageService purchasPutStroageService;
    /**
     * @Author Joan
     * @Description //TODO
     * @Date  2020/4/14 14:34
     * @Param [enter]
     * @return com.redescooter.ses.api.common.vo.base.Response<com.redescooter.ses.api.common.vo.base.PageResult<com.redescooter.ses.mobile.rps.vo.purchasinwh.PutStorageResult>>
     **/

    @PostMapping(value = "/List")
    @ApiOperation(value = "采购仓库待入库列表", response = PutStorageResult.class)
    @IgnoreLoginCheck
    public Response<PageResult<PutStorageResult>> purchasPutStroageList(@ModelAttribute @ApiParam("请求参数") PageEnter enter) {
        return new Response<>(purchasPutStroageService.purchasPutStroageList(enter));
    }

    @IgnoreLoginCheck
    @PostMapping(value = "/DetailsList")
    @ApiOperation(value = "采购仓库待入库部件列表", response = PurchasDetailsListResult.class)
    public Response<PageResult<PurchasDetailsListResult>> storageDetailsList(@ModelAttribute @ApiParam("请求参数") PurchasDetailsEnter enter) {
        return new Response<>(purchasPutStroageService.storageDetailsList(enter));
    }


    @IgnoreLoginCheck
    @PostMapping(value = "/HaveSucceed")
    @ApiOperation(value = "有ID入库成功", response = HaveIDPartsResult.class)
    public Response<HaveIDPartsResult> HaveIDPartsResult(@ModelAttribute @ApiParam("请求参数") PurchasDetailsEnter enter) {
        return new Response<>(purchasPutStroageService.haveIDPartsResult(enter));
    }

    @IgnoreLoginCheck
    @PostMapping(value = "/NotID")
    @ApiOperation(value = "无ID入库", response = NotIDPartsResult.class)
    public Response<NotIDPartsResult> NotIDPartsResult(@ModelAttribute @ApiParam("请求参数") PurchasDetailsEnter enter) {
        return new Response<>(purchasPutStroageService.notIDPartsResult(enter));
    }
    @IgnoreLoginCheck
    @PostMapping(value = "/NotIDSucceed")
    @ApiOperation(value = "无ID入库成功", response = PurchasDetailsListResult.class)
    public Response<NotIDPartsSucceedResult> NotIDPartsSucceedResult(@ModelAttribute @ApiParam("请求参数") PurchasDetailsEnter enter) {
        return new Response<>(purchasPutStroageService.otIDPartsSucceedResult(enter));
    }
}
