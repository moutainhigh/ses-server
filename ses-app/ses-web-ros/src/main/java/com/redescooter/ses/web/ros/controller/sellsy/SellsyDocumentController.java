package com.redescooter.ses.web.ros.controller.sellsy;

import com.alibaba.fastjson.JSONObject;
import com.redescooter.ses.api.common.annotation.IgnoreLoginCheck;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.web.ros.service.sellsy.SellsyDocumentService;
import com.redescooter.ses.web.ros.vo.sellsy.enter.SellsyClientServiceCreateDocumentEnter;
import com.redescooter.ses.web.ros.vo.sellsy.enter.SellsyImportExcelResult;
import com.redescooter.ses.web.ros.vo.sellsy.enter.document.SellsyDocumentListEnter;
import com.redescooter.ses.web.ros.vo.sellsy.enter.document.SellsyDocumentOneEnter;
import com.redescooter.ses.web.ros.vo.sellsy.result.SellsyIdResult;
import com.redescooter.ses.web.ros.vo.sellsy.result.document.SellsyDocumentListResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Api(tags = {"ROS-Sellsy发票业务"})
@CrossOrigin
@RestController
@RequestMapping(value = "/sellsy/document")
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

    @IgnoreLoginCheck
    @ApiOperation(value = "发票创建", response = SellsyIdResult.class)
    @PostMapping(value = "/createDocument")
    public Response<SellsyIdResult> createDocument(@ModelAttribute @ApiParam("请求参数") SellsyClientServiceCreateDocumentEnter enter) {
        return new Response<>(documentService.createDocument(enter));
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


}
