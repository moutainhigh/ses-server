package com.redescooter.ses.web.ros.controller;

import com.alibaba.fastjson.JSONObject;
import com.redescooter.ses.api.common.annotation.IgnoreLoginCheck;
import com.redescooter.ses.api.common.vo.base.PageEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.web.ros.service.sellsy.SellsyService;
import com.redescooter.ses.web.ros.vo.sellsy.enter.document.SellsyDocumentByIdEnter;
import com.redescooter.ses.web.ros.vo.sellsy.enter.document.SellsyDocumentListEnter;
import com.redescooter.ses.web.ros.vo.sellsy.enter.SellsyQueryClientOneEnter;
import com.redescooter.ses.web.ros.vo.sellsy.result.SellsyClientResult;
import com.redescooter.ses.web.ros.vo.sellsy.result.document.SellsyDocumentListResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName:SellsyController
 * @description: SellsyController
 * @author: Alex
 * @Version：1.3
 * @create: 2020/08/25 13:39
 */
@Api(tags = {"ROS-Sellsy"})
@CrossOrigin
@RestController
@RequestMapping(value = "/sellsy")
public class SellsyController {
    @Autowired
    private SellsyService sellsyService;
    
    @IgnoreLoginCheck
    @ApiOperation(value = "查询指定客户", response = SellsyClientResult.class)
    @PostMapping(value = "/queryClientById")
    public Response<SellsyClientResult> queryClientById(@ModelAttribute @ApiParam("请求参数") SellsyQueryClientOneEnter enter) {
        return new Response<>(sellsyService.queryClientById(enter));
    }
    
    @IgnoreLoginCheck
    @ApiOperation(value = "查询客户列表", response = SellsyClientResult.class)
    @PostMapping(value = "/queryClientList")
    public Response<PageResult<SellsyClientResult>> queryClientList(@ModelAttribute @ApiParam("请求参数") PageEnter enter) {
        return new Response<>(sellsyService.queryClientList(enter));
    }
    
    @IgnoreLoginCheck
    @ApiOperation(value = "单据列表", response = SellsyDocumentListResult.class)
    @PostMapping(value = "/queryDocumentList")
    public Response<PageResult<SellsyDocumentListResult>> queryDocumentList(@ModelAttribute @ApiParam("请求参数") SellsyDocumentListEnter enter) {
        return new Response<>(sellsyService.queryDocumentList(enter));
    }
    
    @IgnoreLoginCheck
    @ApiOperation(value = "查询指定单据", response = JSONObject.class)
    @PostMapping(value = "/queryDocumentById")
    public Response<JSONObject> queryDocumentById(@ModelAttribute @ApiParam("请求参数") SellsyDocumentByIdEnter enter) {
        return new Response<> (sellsyService.queryDocumentById(enter));
    }
}
