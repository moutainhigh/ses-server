package com.redescooter.ses.web.ros.controller.sellsy;

import com.redescooter.ses.api.common.annotation.IgnoreLoginCheck;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.web.ros.service.sellsy.BriefcasesService;
import com.redescooter.ses.web.ros.vo.sellsy.enter.briefcase.SellsyBriefcasesDeleteEnter;
import com.redescooter.ses.web.ros.vo.sellsy.enter.briefcase.SellsyBriefcasesListEnter;
import com.redescooter.ses.web.ros.vo.sellsy.enter.briefcase.SellsyBriefcasesUploadFileEnter;
import com.redescooter.ses.web.ros.vo.sellsy.result.briefcases.SellsyBriefcaseUploadFileResult;
import com.redescooter.ses.web.ros.vo.sellsy.result.briefcases.SellsyBriefcasesListResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api(tags = {"ROS-Sellsy-Briefcase"})
@CrossOrigin
@RestController
@RequestMapping(value = "/sellsy/briefcase")
public class BriefcaseController {

    @Autowired
    private BriefcasesService briefcasesService;

    @IgnoreLoginCheck
    @ApiOperation(value = "查询附件列表", response = SellsyBriefcasesListResult.class)
    @PostMapping(value = "/queryBriefcasesList")
    public Response<SellsyBriefcasesListResult>
        queryBriefcasesList(@ModelAttribute @ApiParam("请求参数") SellsyBriefcasesListEnter enter) {
        return new Response<>(briefcasesService.queryBriefcasesList(enter));
    }

    @IgnoreLoginCheck
    @ApiOperation(value = "上传附件", response = SellsyBriefcasesListResult.class)
    @PostMapping(value = "/briefcasesUploadFile")
    public Response<SellsyBriefcaseUploadFileResult>
    briefcasesUploadFile(@ModelAttribute @ApiParam("请求参数") SellsyBriefcasesUploadFileEnter enter, MultipartFile multipartFile) {
        return new Response<>(briefcasesService.briefcasesUploadFile(enter, multipartFile));
    }

    @IgnoreLoginCheck
    @ApiOperation(value = "删除附件", response = SellsyBriefcasesListResult.class)
    @PostMapping(value = "/briefcasesDelete")
    public Response<GeneralResult>
        briefcasesDelete(@ModelAttribute @ApiParam("请求参数") SellsyBriefcasesDeleteEnter enter) {
        briefcasesService.briefcasesDelete(enter);
        return new Response<>(new GeneralResult());
    }

}
