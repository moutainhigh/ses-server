package com.redescooter.ses.web.ros.controller.specificat;

import com.redescooter.ses.api.common.annotation.AvoidDuplicateSubmit;
import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.web.ros.service.specificat.ColorService;
import com.redescooter.ses.web.ros.vo.specificat.ColorDataResult;
import com.redescooter.ses.web.ros.vo.specificat.ColorListResult;
import com.redescooter.ses.web.ros.vo.specificat.ColorSaveOrEditEnter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassNameOpeColorController
 * @Description
 * @Author Aleks
 * @Date2020/9/28 14:31
 * @Version V1.0
 **/
@Api(tags = {"规格颜色管理模块"})
@CrossOrigin
@RestController
@RequestMapping(value = "/color")
public class ColorController {

    @Autowired
    private ColorService colorService;

    @PostMapping(value = "/colorSave")
    @ApiOperation(value = "颜色新增", response = GeneralResult.class)
    @AvoidDuplicateSubmit
    public Response<GeneralResult> colorSave(@ModelAttribute @ApiParam("请求参数") ColorSaveOrEditEnter enter) {
        return new Response(colorService.colorSave(enter));
    }

    @PostMapping(value = "/colorList")
    @ApiOperation(value = "颜色列表", response = ColorListResult.class)
    public Response<PageResult<ColorListResult>> colorList(@ModelAttribute @ApiParam("请求参数") PageEnter enter) {
        return new Response(colorService.colorList(enter));
    }

    @PostMapping(value = "/colorDelete")
    @ApiOperation(value = "颜色删除", response = GeneralResult.class)
    public Response<GeneralResult> colorDelete(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response(colorService.colorDelete(enter));
    }

    @PostMapping(value = "/colorData")
    @ApiOperation(value = "颜色下拉数据源接口", response = ColorDataResult.class)
    public Response<List<ColorDataResult>> colorData(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response(colorService.colorData(enter));
    }

}
