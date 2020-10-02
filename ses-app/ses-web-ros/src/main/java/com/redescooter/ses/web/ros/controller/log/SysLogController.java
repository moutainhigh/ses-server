package com.redescooter.ses.web.ros.controller.log;

import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.web.ros.service.log.SysLogService;
import com.redescooter.ses.web.ros.vo.log.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @ClassNameSysLogController
 * @Description
 * @Author Aleks
 * @Date2020/9/16 19:36
 * @Version V1.0
 **/
@Api(tags = {"系统日志管理模块"})
@CrossOrigin
@RestController
@RequestMapping(value = "/sys/log")
public class SysLogController {

    @Autowired
    private SysLogService sysLogService;



    @PostMapping(value = "/logList")
    @ApiOperation(value = "系统日志列表", response = GeneralResult.class)
    public Response<PageResult<LogListResult>> logList(@ModelAttribute @ApiParam("请求参数") LogListEnter enter) {
        return new Response(sysLogService.logList(enter));
    }


    @GetMapping(value = "/logExport")
    @ApiOperation(value = "系统日志导出", response = GeneralResult.class)
    public Response<GeneralResult> logExport(@ApiParam("请求参数 id") String id, HttpServletResponse response) {
        return new Response(sysLogService.logExport(id,response));
    }


    @PostMapping(value = "/logCount")
    @ApiOperation(value = "系统日志统计", response = GeneralResult.class)
    public Response<Map<String,Integer>> logCount(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response(sysLogService.logCount(enter));
    }

    @PostMapping(value = "/logDetail")
    @ApiOperation(value = "系统日志详情", response = GeneralResult.class)
    public Response<LogDetailResult> logDetail(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response(sysLogService.logDetail(enter));
    }

}
