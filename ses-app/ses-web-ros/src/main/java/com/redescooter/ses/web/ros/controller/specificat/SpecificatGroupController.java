package com.redescooter.ses.web.ros.controller.specificat;

import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.web.ros.service.specificat.SpecificatGroupService;
import com.redescooter.ses.web.ros.vo.specificat.SpecificatGroupListEnter;
import com.redescooter.ses.web.ros.vo.specificat.SpecificatGroupListResult;
import com.redescooter.ses.web.ros.vo.specificat.SpecificatGroupSaveOrEditEnter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassNameSpecificatGroupController
 * @Description
 * @Author Aleks
 * @Date2020/9/28 14:41
 * @Version V1.0
 **/
@Api(tags = {"规格分组管理"})
@CrossOrigin
@RestController
@RequestMapping(value = "/specificat/group")
public class SpecificatGroupController {

    @Autowired
    private SpecificatGroupService specificatGroupService;

    @PostMapping(value = "/specificatGroupSave")
    @ApiOperation(value = "规格分组新增", response = GeneralResult.class)
    public Response<GeneralResult> specificatGroupSave(@ModelAttribute @ApiParam("请求参数") SpecificatGroupSaveOrEditEnter enter) {
        return new Response(specificatGroupService.specificatGroupSave(enter));
    }


    @PostMapping(value = "/specificatGroupEdit")
    @ApiOperation(value = "规格分组编辑", response = GeneralResult.class)
    public Response<GeneralResult> specificatGroupEdit(@ModelAttribute @ApiParam("请求参数") SpecificatGroupSaveOrEditEnter enter) {
        return new Response(specificatGroupService.specificatGroupEdit(enter));
    }


    @PostMapping(value = "/specificatGroupList")
    @ApiOperation(value = "规格分组列表", response = SpecificatGroupListResult.class)
    public Response<PageResult<SpecificatGroupListResult>> specificatGroupList(@ModelAttribute @ApiParam("请求参数") SpecificatGroupListEnter enter) {
        return new Response(specificatGroupService.specificatGroupList(enter));
    }


    @PostMapping(value = "/specificatGroupDelete")
    @ApiOperation(value = "规格分组删除", response = GeneralResult.class)
    public Response<GeneralResult> specificatGroupDelete(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response(specificatGroupService.specificatGroupDelete(enter));
    }


    @PostMapping(value = "/specificatGroupData")
    @ApiOperation(value = "规格分组下拉数据源接口", response = SpecificatGroupSaveOrEditEnter.class)
    public Response<List<SpecificatGroupSaveOrEditEnter>> specificatGroupData(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response(specificatGroupService.specificatGroupData(enter));
    }



}
