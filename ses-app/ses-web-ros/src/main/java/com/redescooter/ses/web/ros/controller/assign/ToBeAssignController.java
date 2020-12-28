package com.redescooter.ses.web.ros.controller.assign;

import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.web.ros.service.assign.ToBeAssignService;
import com.redescooter.ses.web.ros.vo.assign.tobe.enter.ToBeAssignListEnter;
import com.redescooter.ses.web.ros.vo.assign.tobe.result.ToBeAssignListResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description 车辆待分配控制器
 * @Author Chris
 * @Date 2020/12/27 14:48
 */
@Api(value = "车辆待分配控制器", tags = "车辆待分配控制器")
@CrossOrigin
@RestController
@RequestMapping("/tobe/assign")
public class ToBeAssignController {

    @Autowired
    private ToBeAssignService toBeAssignService;

    /**
     * 待分配列表
     */
    @ApiOperation(value = "待分配列表", notes = "待分配列表")
    @PostMapping("/list")
    public Response<PageResult<ToBeAssignListResult>> getToBeAssignList(@ModelAttribute ToBeAssignListEnter enter) {
        return new Response<>(toBeAssignService.getToBeAssignList(enter));
    }

    /**
     * 待分配列表点击分配带出数据
     */
    @ApiOperation(value = "待分配列表点击分配带出数据", notes = "待分配列表点击分配带出数据")
    @PostMapping("/info")
    public Response<>

    /**
     * 填写完座位数点击下一步
     */
    @ApiOperation(value = "填写完座位数点击下一步", notes = "填写完座位数点击下一步")
    @PostMapping("/seat/next")
    public Response<>

    /**
     * 得到各个型号的座位数和VIN Code
     */
    @ApiOperation(value = "得到各个型号的座位数和VIN Code", notes = "得到各个型号的座位数和VIN Code")
    @PostMapping("/seat/vin")
    public Response<>

    /**
     * 填写完车牌点击下一步
     */
    @ApiOperation(value = "填写完车牌点击下一步", notes = "填写完车牌点击下一步")
    @PostMapping("/license/next")
    public Response<>

    /**
     * 得到各个型号的座位数,VIN Code和车牌
     */
    @ApiOperation(value = "得到各个型号的座位数,VIN Code和车牌", notes = "得到各个型号的座位数,VIN Code和车牌")
    @PostMapping("/seat/vin/license")
    public Response<>

    /**
     * 根据R.SN获得颜色
     */
    @ApiOperation(value = "根据R.SN获得颜色", notes = "根据R.SN获得颜色")
    @PostMapping("/color")
    public Response<>

    /**
     * 填写完R.SN并点击提交
     */
    @ApiOperation(value = "填写完R.SN并点击提交", notes = "填写完R.SN并点击提交")
    @PostMapping("/submit")
    public Response<>


}
