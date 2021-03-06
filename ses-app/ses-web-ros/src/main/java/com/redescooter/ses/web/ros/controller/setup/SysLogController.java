package com.redescooter.ses.web.ros.controller.setup;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.web.ros.service.log.SysLogService;
import com.redescooter.ses.web.ros.vo.log.LogDetailResult;
import com.redescooter.ses.web.ros.vo.log.LogListEnter;
import com.redescooter.ses.web.ros.vo.log.LogListResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
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
@RequestMapping(value = "/setup/sys/log")
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
