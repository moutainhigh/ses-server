package com.redescooter.ses.mobile.rps.controller;

import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.mobile.rps.service.printentry.PrintEntryService;
import com.redescooter.ses.mobile.rps.vo.printentry.PrintEnteyEnter;
import com.redescooter.ses.mobile.rps.vo.printentry.PrintEntryOrderEnter;
import com.redescooter.ses.mobile.rps.vo.printentry.PrintEntryOrderResult;
import com.redescooter.ses.mobile.rps.vo.printentry.PrintEntryResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Log4j2
@Api(tags = {"Rps重复打印条码"})
@CrossOrigin
@RestController
@RequestMapping(value = "/restcode")
public class PrintEntryController {

    @Autowired
    private PrintEntryService printEntryService;

    @PostMapping(value = "/list")
    @ApiOperation(value = "单据列表", response = PrintEntryResult.class)
    public Response<PageResult<PrintEntryResult>> list(@ModelAttribute @ApiParam("请求参数") PrintEnteyEnter enter) {
        return new Response<>(printEntryService.list(enter));
    }

    @PostMapping(value = "/orderDetailList")
    @ApiOperation(value = "单据部件列表", response = PrintEntryOrderResult.class)
    public Response<PageResult<PrintEntryOrderResult>> orderDetailList(@ModelAttribute @ApiParam("请求参数") PrintEntryOrderEnter enter) {
        return new Response<>(printEntryService.orderDetailList(enter));
    }

}
