package com.redescooter.ses.web.ros.controller.codebase;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.api.common.vo.base.StringEnter;
import com.redescooter.ses.web.ros.service.codebase.RSNService;
import com.redescooter.ses.web.ros.vo.codebase.RSNDetailResult;
import com.redescooter.ses.web.ros.vo.codebase.RSNListEnter;
import com.redescooter.ses.web.ros.vo.codebase.RSNListResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description RSN管理控制器
 * @Author Chris
 * @Date 2021/5/14 9:42
 */
@Api(value = "RSN管理控制器", tags = "RSN管理控制器")
@CrossOrigin
@RestController
@RequestMapping("/codebase/rsn")
public class RSNController {

    @Autowired
    private RSNService rsnService;

    /**
     * 列表
     */
    @PostMapping("/list")
    @ApiOperation(value = "RSN列表", notes = "RSN列表")
    public Response<PageResult<RSNListResult>> getList(@ModelAttribute RSNListEnter enter) {
        return new Response<>(rsnService.getList(enter));
    }

    /**
     * 详情
     */
    @PostMapping("/detail")
    @ApiOperation(value = "RSN详情", notes = "RSN详情")
    public Response<RSNDetailResult> getDetail(@ModelAttribute StringEnter enter) {
        return new Response<>(rsnService.getDetail(enter));
    }

    /**
     * 导出
     */
    @PostMapping("/export")
    @ApiOperation(value = "RSN导出", notes = "RSN导出")
    public Response<GeneralResult> export(@ModelAttribute RSNListEnter enter) {
        return new Response<>(rsnService.export(enter));
    }

}
