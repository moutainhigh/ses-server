package com.redescooter.ses.web.ros.controller.supply;

import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.web.ros.service.factory.FactoryRosService;
import com.redescooter.ses.web.ros.vo.factory.FactoryEditEnter;
import com.redescooter.ses.web.ros.vo.factory.FactoryPage;
import com.redescooter.ses.web.ros.vo.factory.FactoryResult;
import com.redescooter.ses.web.ros.vo.factory.FactorySaveEnter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 功能描述: 代工厂控制器
 *
 * @param:
 * @auther: jerry
 * @date: 2020-02-21 13:57
 */
@Api(tags = {"代工厂管理"})
@CrossOrigin
@RestController
@RequestMapping(value = "/supply/factory")
public class FactoryController {

    @Autowired
    private FactoryRosService factoryRosService;

    @PostMapping(value = "/countStatus")
    @ApiOperation(value = "状态统计", response = Map.class)
    public Response<Map<String, Integer>> countStatus(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(factoryRosService.countStatus(enter));
    }

    @PostMapping(value = "/save")
    @ApiOperation(value = "新建代工厂", response = GeneralResult.class)
    public Response<GeneralResult> save(@ModelAttribute @ApiParam("请求参数") FactorySaveEnter enter) {
        return new Response<>(factoryRosService.save(enter));
    }

    @PostMapping(value = "/edit")
    @ApiOperation(value = "编辑代工厂", response = GeneralResult.class)
    public Response<GeneralResult> edit(@ModelAttribute @ApiParam("请求参数") FactoryEditEnter enter) {
        return new Response<>(factoryRosService.edit(enter));
    }

    @PostMapping(value = "/details")
    @ApiOperation(value = "代工厂详情", response = FactoryResult.class)
    public Response<FactoryResult> details(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(factoryRosService.details(enter));
    }

    @PostMapping(value = "/list")
    @ApiOperation(value = "代工厂列表", response = FactoryResult.class)
    public Response<PageResult<FactoryResult>> list(@ModelAttribute @ApiParam("请求参数") FactoryPage page) {
        return new Response<>(factoryRosService.list(page));
    }
}