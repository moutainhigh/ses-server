package com.redescooter.ses.web.ros.controller.assign;

import com.redescooter.ses.api.common.vo.base.BooleanResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.api.common.vo.base.StringEnter;
import com.redescooter.ses.web.ros.service.assign.ToBeAssignService;
import com.redescooter.ses.web.ros.vo.assign.tobe.enter.CustomerIdEnter;
import com.redescooter.ses.web.ros.vo.assign.tobe.enter.ToBeAssignLicensePlateNextEnter;
import com.redescooter.ses.web.ros.vo.assign.tobe.enter.ToBeAssignListEnter;
import com.redescooter.ses.web.ros.vo.assign.tobe.enter.ToBeAssignSeatNextEnter;
import com.redescooter.ses.web.ros.vo.assign.tobe.enter.ToBeAssignSubmitEnter;
import com.redescooter.ses.web.ros.vo.assign.tobe.result.ToBeAssignColorResult;
import com.redescooter.ses.web.ros.vo.assign.tobe.result.ToBeAssignDetailResult;
import com.redescooter.ses.web.ros.vo.assign.tobe.result.ToBeAssignListResult;
import com.redescooter.ses.web.ros.vo.assign.tobe.result.ToBeAssignNextStopResult;
import com.redescooter.ses.web.ros.vo.assign.tobe.result.ToBeAssignNodeResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

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
    public Response<ToBeAssignDetailResult> getToBeAssignDetail(@ModelAttribute CustomerIdEnter enter) {
        return new Response<>(toBeAssignService.getToBeAssignDetail(enter));
    }

    /**
     * 填写完座位数点击下一步
     */
    @ApiOperation(value = "填写完座位数点击下一步", notes = "填写完座位数点击下一步")
    @PostMapping("/seat/next")
    public Response<ToBeAssignNextStopResult> getSeatNext(@ModelAttribute ToBeAssignSeatNextEnter enter) {
        return new Response<>(toBeAssignService.getSeatNext(enter));
    }

    /**
     * 填写完车牌点击下一步
     */
    @ApiOperation(value = "填写完车牌点击下一步", notes = "填写完车牌点击下一步")
    @PostMapping("/license/next")
    public Response<ToBeAssignNextStopResult> getLicensePlateNext(@ModelAttribute ToBeAssignLicensePlateNextEnter enter) {
        return new Response<>(toBeAssignService.getLicensePlateNext(enter));
    }

    /**
     * 根据R.SN获得颜色
     */
    @ApiOperation(value = "根据R.SN获得颜色", notes = "根据R.SN获得颜色")
    @PostMapping("/color")
    public Response<ToBeAssignColorResult> getColorByRSN(@ModelAttribute StringEnter enter) {
        return new Response<>(toBeAssignService.getColorByRSN(enter));
    }

    /**
     * 填写完R.SN并点击提交
     */
    @ApiOperation(value = "填写完R.SN并点击提交", notes = "填写完R.SN并点击提交")
    @PostMapping("/submit")
    public Response<ToBeAssignNextStopResult> submit(@ModelAttribute ToBeAssignSubmitEnter enter) {
        return new Response<>(toBeAssignService.submit(enter));
    }

    /**
     * 查询客户走到哪个节点并带出数据
     */
    @ApiOperation(value = "查询客户走到哪个节点并带出数据", notes = "查询客户走到哪个节点并带出数据")
    @PostMapping("/node")
    public Response<ToBeAssignNodeResult> getNode(@ModelAttribute CustomerIdEnter enter) {
        return new Response<>(toBeAssignService.getNode(enter));
    }

    /**
     * 待分配列表和已分配列表的tab数量统计
     */
    @ApiOperation(value = "待分配列表和已分配列表的tab数量统计", notes = "待分配列表和已分配列表的tab数量统计")
    @PostMapping("/count")
    public Response<Map<String, Object>> getTabCount(@ModelAttribute GeneralEnter enter) {
        return new Response<>(toBeAssignService.getTabCount(enter));
    }

    /**
     * 点击分配按钮校验车辆库存数量
     */
    @ApiOperation(value = "点击分配按钮校验车辆库存数量", notes = "点击分配按钮校验车辆库存数量")
    @PostMapping("/check")
    public Response<BooleanResult> checkScooterStock(@ModelAttribute CustomerIdEnter enter) {
        return toBeAssignService.checkScooterStock(enter);
    }

    /**
     * 生成105条SSN
     */
    @PostMapping("/test")
    public Response<List<String>> test(@ModelAttribute GeneralEnter enter) {
        return new Response<>(toBeAssignService.testGenerateVINCode(enter));
    }

}
