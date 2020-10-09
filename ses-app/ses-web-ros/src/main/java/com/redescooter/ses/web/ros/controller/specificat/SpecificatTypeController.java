package com.redescooter.ses.web.ros.controller.specificat;

import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.web.ros.service.specificat.SpecificatTypeService;
import com.redescooter.ses.web.ros.vo.specificat.SpecificatTypeDetailResult;
import com.redescooter.ses.web.ros.vo.specificat.SpecificatTypeListEnter;
import com.redescooter.ses.web.ros.vo.specificat.SpecificatTypeListResult;
import com.redescooter.ses.web.ros.vo.specificat.SpecificatTypeSaveOrEditEnter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassNameSpecificatTypeController
 * @Description
 * @Author Aleks
 * @Date2020/9/28 14:45
 * @Version V1.0
 **/
@Api(tags = {"规格分组管理"})
@CrossOrigin
@RestController
@RequestMapping(value = "/specificat/type")
public class SpecificatTypeController {

    @Autowired
    private SpecificatTypeService specificatTypeService;

    @PostMapping(value = "/specificatTypeSave")
    @ApiOperation(value = "规格类型新增", response = GeneralResult.class)
    public Response<GeneralResult> specificatTypeSave(@ModelAttribute @ApiParam("请求参数") SpecificatTypeSaveOrEditEnter enter) {
        return new Response(specificatTypeService.specificatTypeSave(enter));
    }


    @PostMapping(value = "/specificatTypeDelete")
    @ApiOperation(value = "规格类型删除", response = GeneralResult.class)
    public Response<GeneralResult> specificatTypeDelete(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response(specificatTypeService.specificatTypeDelete(enter));
    }


    @PostMapping(value = "/specificatTypeEdit")
    @ApiOperation(value = "规格类型编辑", response = GeneralResult.class)
    public Response<GeneralResult> specificatTypeEdit(@ModelAttribute @ApiParam("请求参数") SpecificatTypeSaveOrEditEnter enter) {
        return new Response(specificatTypeService.specificatTypeEdit(enter));
    }


    @PostMapping(value = "/specificatTypeList")
    @ApiOperation(value = "规格类型列表", response = SpecificatTypeListResult.class)
    public Response<PageResult<SpecificatTypeListResult>> specificatTypeList(@ModelAttribute @ApiParam("请求参数") SpecificatTypeListEnter enter) {
        return new Response(specificatTypeService.specificatTypeList(enter));
    }


    @PostMapping(value = "/specificatTypeDetail")
    @ApiOperation(value = "规格类型详情", response = SpecificatTypeDetailResult.class)
    public Response<SpecificatTypeDetailResult> specificatTypeDetail(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response(specificatTypeService.specificatTypeDetail(enter));
    }


    @PostMapping(value = "/specificatNameCheck")
    @ApiOperation(value = "规格类型名称的校验", response = BooleanResult.class)
    public Response<BooleanResult> specificatNameCheck(@ModelAttribute @ApiParam("请求参数") SpecificatTypeSaveOrEditEnter enter) {
        return new Response(specificatTypeService.specificatNameCheck(enter));
    }

}
