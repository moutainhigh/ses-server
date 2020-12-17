package com.redescooter.ses.web.ros.controller.specificat;

import com.redescooter.ses.api.common.annotation.AvoidDuplicateSubmit;
import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.api.common.vo.specification.QuerySpecificTypeDetailResultDTO;
import com.redescooter.ses.web.ros.service.specificat.SpecificatTypeService;
import com.redescooter.ses.web.ros.vo.specificat.*;
import com.redescooter.ses.web.ros.vo.specificat.dto.InsertSpecificTypeParamDTO;
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
    @AvoidDuplicateSubmit
    public Response<GeneralResult> specificatTypeSave(@ModelAttribute @ApiParam("请求参数") SpecificatTypeSaveOrEditEnter enter) {
        return new Response(specificatTypeService.specificatTypeSave(enter));
    }

    /**
     * 新增规格类型,新接口(new)
     * @param paramDTO
     * @return com.redescooter.ses.api.common.vo.base.Response<com.redescooter.ses.api.common.vo.base.GeneralResult>
     * @author assert
     * @date 2020/12/7
    */
    @ApiOperation(value = "新增规格类型", notes = "新增规格类型,新接口(new)")
    @PostMapping(value = "/insert")
    @AvoidDuplicateSubmit
    public Response<GeneralResult> insertSpecificType(@ModelAttribute InsertSpecificTypeParamDTO paramDTO) {
        return new Response<>(specificatTypeService.insertSpecificType(paramDTO));
    }

    /**
     * 修改规格类型,新接口(new)
     * @param paramDTO
     * @return com.redescooter.ses.api.common.vo.base.Response<com.redescooter.ses.api.common.vo.base.GeneralResult>
     * @author assert
     * @date 2020/12/17
    */
    @ApiOperation(value = "修改规格类型", notes = "修改规格类型,新接口(new)")
    @PostMapping(value = "/update")
    public Response<GeneralResult> updateSpecificType(@ModelAttribute InsertSpecificTypeParamDTO paramDTO) {
        return new Response<>(specificatTypeService.updateSpecificType(paramDTO));
    }

    /**
     * 根据id查询规格类型详情 -- new
     * @param enter
     * @return com.redescooter.ses.api.common.vo.base.Response<QuerySpecificTypeDetailResultDTO>
     * @author assert
     * @date 2020/12/8
    */
    @ApiOperation(value = "查询规格类型详情", notes = "根据id查询规格类型详情")
    @PostMapping(value = "/detail")
    public Response<QuerySpecificTypeDetailResultDTO> getSpecificTypeDetailById(@ModelAttribute IdEnter enter) {
        return new Response<>(specificatTypeService.getSpecificTypeDetailById(enter));
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
