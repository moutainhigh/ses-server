package com.redescooter.ses.web.ros.controller.sellsy;

import com.alibaba.fastjson.JSONObject;
import com.redescooter.ses.api.common.annotation.IgnoreLoginCheck;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.web.ros.service.sellsy.SellsyDocumentService;
import com.redescooter.ses.web.ros.vo.sellsy.enter.SellsyImportExcelResult;
import com.redescooter.ses.web.ros.vo.sellsy.enter.document.*;
import com.redescooter.ses.web.ros.vo.sellsy.result.SellsyIdResult;
import com.redescooter.ses.web.ros.vo.sellsy.result.document.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Api(tags = {"ROS-Sellsy发票业务"})
@CrossOrigin
@RestController
@RequestMapping(value = "/sellsy/document")
@Log4j2
public class SellsyDocumentController {

    @Autowired
    private SellsyDocumentService documentService;

    @IgnoreLoginCheck
    @ApiOperation(value = "单据列表", response = SellsyDocumentListResult.class)
    @PostMapping(value = "/queryDocumentList")
    public Response<List<SellsyDocumentListResult>>
    queryDocumentList(@ModelAttribute @ApiParam("请求参数") SellsyDocumentListEnter enter) {
        return new Response<>(documentService.queryDocumentList(enter));
    }

    @IgnoreLoginCheck
    @ApiOperation(value = "查询指定单据", response = JSONObject.class)
    @PostMapping(value = "/queryDocumentById")
    public Response<JSONObject> queryDocumentById(@ModelAttribute @ApiParam("请求参数") SellsyDocumentOneEnter enter) {
        return new Response<>(documentService.queryDocumentOne(enter));
    }

//    @IgnoreLoginCheck
//    @ApiOperation(value = "发票创建", response = SellsyIdResult.class)
//    @PostMapping(value = "/createDocument")
//    public Response<SellsyIdResult> createDocument(@ModelAttribute @ApiParam("请求参数") SellsyClientServiceCreateDocumentEnter enter) {
//        return new Response<>(documentService.createDocument(enter));
//    }

    @IgnoreLoginCheck
    @ApiOperation(value = "批量创建", response = GeneralResult.class)
    @PostMapping(value = "/createDcumentTotalList")
    public Response<GeneralResult> createDcumentTotalList(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        documentService.createDcumentTotalList(enter);
        return new Response<>(new GeneralResult());
    }

    @IgnoreLoginCheck
    @ApiOperation(value = "创建一个单据", response = GeneralResult.class)
    @PostMapping(value = "/createDcumentTotalOne")
    public Response<GeneralResult> createDcumentTotalOne(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        documentService.createDcumentTotalOne(enter);
        return new Response<>(new GeneralResult());
    }

    @IgnoreLoginCheck
    @ApiOperation(value = "checkDocumentExist", response = GeneralResult.class)
    @PostMapping(value = "/checkDocumentExist")
    public Response<GeneralResult> checkDocumentExist(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(documentService.checkDocumentExist(enter));
    }

    @IgnoreLoginCheck
    @ApiOperation(value = "checkDocumentTTCFile", response = GeneralResult.class)
    @PostMapping(value = "/checkDocumentTTCFile")
    public Response<GeneralResult> checkDocumentTTCFile(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(documentService.checkDocumentTTCFile(enter));
    }


    @IgnoreLoginCheck
    @ApiOperation(value = "根据导入到数据库的数据批量创建发票", response = SellsyIdResult.class)
    @PostMapping(value = "/createDcumentList")
    public Response<List<SellsyIdResult>> createDcumentList() {
        return new Response<>(documentService.createDcumentList());
    }


    @IgnoreLoginCheck
    @ApiOperation(value = "发票excel导入", response = SellsyImportExcelResult.class)
    @PostMapping(value = "/import")
    public Response<SellsyImportExcelResult> importDocument(MultipartFile file) {
        return new Response<>(documentService.importSellsyDocument(file));
    }


    @IgnoreLoginCheck
    @ApiOperation(value = "创建付款记录", response = SellsyCreateDocumentPaymentResult.class)
    @PostMapping(value = "/createDocumentPayment")
    public Response<SellsyCreateDocumentPaymentResult> createDocumentPayment(@ModelAttribute @ApiParam("请求参数") SellsyCreateDocumentPaymentEnter enter) {
        return new Response<>(documentService.createDocumentPayment(enter));
    }

    @IgnoreLoginCheck
    @ApiOperation(value = "删除付款记录", response = DeleteDocumentPaymentReuslt.class)
    @PostMapping(value = "/deleteDocumentPayment")
    public Response<DeleteDocumentPaymentReuslt> deleteDocumentPayment(@ModelAttribute @ApiParam("请求参数") SellsyDeleteDocumentPaymentEnter enter) {
        return new Response<>(documentService.deleteDocumentPayment(enter));
    }

    @IgnoreLoginCheck
    @ApiOperation(value = "查询指定付款记录", response = SellsyQueryDocumentPaymentOneResult.class)
    @PostMapping(value = "/queryDocumentPaymentOne")
    public Response<SellsyQueryDocumentPaymentOneResult> queryDocumentPaymentOne(@ModelAttribute @ApiParam("请求参数") SellsyQueryDocumentPaymentEnter enter) {
        return new Response<>(documentService.queryDocumentPaymentOne(enter));
    }

    @IgnoreLoginCheck
    @ApiOperation(value = "查询付款记录列表", response = SellsyQueryDocumentPaymentListEnter.class)
    @PostMapping(value = "/queryDocumentPaymentList")
    public Response<List<SellsyQueryDocumentPaymentListEnter>> queryDocumentPaymentList(@ModelAttribute @ApiParam("请求参数") QueryDocumentPaymentListEnter enter) {
        return new Response<>(documentService.queryDocumentPaymentList(enter));
    }

}
