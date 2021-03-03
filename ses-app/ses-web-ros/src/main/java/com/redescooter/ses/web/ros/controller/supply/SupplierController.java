package com.redescooter.ses.web.ros.controller.supply;

import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.web.ros.service.bom.SupplierRosService;
import com.redescooter.ses.web.ros.vo.supplier.SupplierEditEnter;
import com.redescooter.ses.web.ros.vo.supplier.SupplierPage;
import com.redescooter.ses.web.ros.vo.supplier.SupplierResult;
import com.redescooter.ses.web.ros.vo.supplier.SupplierSaveEnter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 功能描述: 采购商控制器
 *
 * @param:
 * @auther: jerry
 * @date: 2020-02-21 13:57
 */

@Api(tags = {"采购商管理"})
@CrossOrigin
@RestController
@RequestMapping(value = "/supply/supplier")
public class SupplierController {

    @Autowired
    private SupplierRosService supplierRosService;

    @PostMapping(value = "/countStatus")
    @ApiOperation(value = "状态统计", response = Map.class)
    public Response<Map<String, Integer>> countStatus(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(supplierRosService.countStatus(enter));
    }

    @PostMapping(value = "/save")
    @ApiOperation(value = "新建采购商", response = GeneralResult.class)
    public Response<GeneralResult> save(@ModelAttribute @ApiParam("请求参数") SupplierSaveEnter enter) {
        return new Response<>(supplierRosService.save(enter));
    }

    @PostMapping(value = "/edit")
    @ApiOperation(value = "编辑采购商", response = GeneralResult.class)
    public Response<GeneralResult> edit(@ModelAttribute @ApiParam("请求参数") SupplierEditEnter enter) {
        return new Response<>(supplierRosService.edit(enter));
    }

    @PostMapping(value = "/details")
    @ApiOperation(value = "采购商详情", response = SupplierResult.class)
    public Response<SupplierResult> details(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(supplierRosService.details(enter));
    }

    @PostMapping(value = "/list")
    @ApiOperation(value = "采购商列表", response = SupplierResult.class)
    public Response<PageResult<SupplierResult>> list(@ModelAttribute @ApiParam("请求参数") SupplierPage page) {
        return new Response<>(supplierRosService.list(page));
    }
}