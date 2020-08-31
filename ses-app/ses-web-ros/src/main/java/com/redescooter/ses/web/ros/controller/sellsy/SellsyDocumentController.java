package com.redescooter.ses.web.ros.controller.sellsy;

import com.alibaba.fastjson.JSONObject;
import com.redescooter.ses.api.common.annotation.IgnoreLoginCheck;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.web.ros.service.sellsy.SellsyDocumentService;
import com.redescooter.ses.web.ros.vo.sellsy.enter.SellsyClientServiceCreateDocumentEnter;
import com.redescooter.ses.web.ros.vo.sellsy.enter.document.SellsyDocumentListEnter;
import com.redescooter.ses.web.ros.vo.sellsy.enter.document.SellsyDocumentOneEnter;
import com.redescooter.ses.web.ros.vo.sellsy.result.document.SellsyDocumentListResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"ROS-Sellsy"})
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
    @ApiOperation(value = "发票创建", response = GeneralResult.class)
    @PostMapping(value = "/createDocument")
    public Response<GeneralResult> createDocument(@ModelAttribute @ApiParam("请求参数") SellsyClientServiceCreateDocumentEnter enter) {
        documentService.createDocument(enter);
        return new Response<>(new GeneralResult());
    }
}
