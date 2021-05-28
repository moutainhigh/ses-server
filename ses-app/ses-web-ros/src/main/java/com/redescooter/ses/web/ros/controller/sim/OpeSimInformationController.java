package com.redescooter.ses.web.ros.controller.sim;

import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.web.ros.service.sim.OpeSimInformationService;
import com.redescooter.ses.web.ros.vo.sim.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Charles
 * @since 2021-05-26 20:58:06
 */
@Api(tags = "sim卡信息")
@RestController
@RequestMapping("/ope/sim/information")
public class OpeSimInformationController {

    @Autowired
    private OpeSimInformationService opeSimInformationService;

    /**
     * @Title: getSimEssentialInformation
     * @Description: // 获取sim基本信息
     * @Param: []
     * @Return: com.redescooter.ses.api.common.vo.base.Response<com.redescooter.ses.web.ros.vo.sim.SimResult>
     * @Date: 2021/5/28 9:40 上午
     * @Author: Charles
     */
    @PostMapping(value = "/getSimEssentialInformation")
    @ApiOperation(value = "获取sim基本信息", response = SimResult.class)
    public Response<SimResult> getSimEssentialInformation() {
        return new Response<>(opeSimInformationService.getSimEssentialInformation());
    }

    /**
     * @Title: getSimTransactionRecords
     * @Description: // 获取交易记录
     * @Param: [simEnter]
     * @Return: com.redescooter.ses.api.common.vo.base.Response<com.redescooter.ses.web.ros.vo.sim.SimDataResult>
     * @Date: 2021/5/28 8:58 上午
     * @Author: Charles
     */
    @PostMapping(value = "/getSimTransactionRecords")
    @ApiOperation(value = "获取交易记录", response = SimTransactionRecordsResult.class)
    public Response<SimDataResult> getSimTransactionRecords(@ModelAttribute @ApiParam("sim 请求参数") SimEnter simEnter) {
        return new Response<>(opeSimInformationService.getSimTransactionRecords(simEnter));
    }

    /**
     * @Title: getSimCardList
     * @Description: // Sim卡 列表信息
     * @Param: [simEnter]
     * @Return: com.redescooter.ses.api.common.vo.base.Response<com.redescooter.ses.web.ros.vo.sim.SimDataResult>
     * @Date: 2021/5/28 8:58 上午
     * @Author: Charles
     */
    @PostMapping(value = "/getSimCardList")
    @ApiOperation(value = "Sim卡 列表信息", response = SimCardListResult.class)
    public Response<SimDataResult> getSimCardList(@ModelAttribute @ApiParam("sim 请求参数") SimEnter simEnter) {
        return new Response<>(opeSimInformationService.getSimCardList(simEnter));
    }

    /**
     * @Title: getSimConnectRecord
     * @Description: // 获取连接记录
     * @Param: [simEnter]
     * @Return: com.redescooter.ses.api.common.vo.base.Response<com.redescooter.ses.web.ros.vo.sim.SimDataResult>
     * @Date: 2021/5/28 8:58 上午
     * @Author: Charles
     */
    @PostMapping(value = "/getSimConnectRecord")
    @ApiOperation(value = "获取连接记录", response = SimCardSessionsResult.class)
    public Response<SimDataResult> getSimConnectRecord(@ModelAttribute @ApiParam("sim 请求参数") SimEnter simEnter) {
        return new Response<>(opeSimInformationService.getSimConnectRecord(simEnter));
    }

    /**
     * @Title: getSimDetails
     * @Description: // Sim卡 详情
     * @Param: [simEnter]
     * @Return: com.redescooter.ses.api.common.vo.base.Response<com.redescooter.ses.web.ros.vo.sim.SimBaseCodeResult>
     * @Date: 2021/5/28 8:59 上午
     * @Author: Charles
     */
    @PostMapping(value = "/getSimDetails")
    @ApiOperation(value = "Sim卡 详情", response = SimBaseCodeResult.class)
    public Response<SimBaseCodeResult> getSimDetails(@ModelAttribute @ApiParam("sim 请求参数") SimEnter simEnter) {
        return new Response<>(opeSimInformationService.getSimDetails(simEnter));
    }

    /**
     * @Title: getSimDailyStatistics
     * @Description: // Sim卡 日统计
     * @Param: [simEnter]
     * @Return: com.redescooter.ses.api.common.vo.base.Response<java.util.List < com.redescooter.ses.web.ros.vo.sim.SimDailyStatisticsResult>>
     * @Date: 2021/5/28 8:59 上午
     * @Author: Charles
     */
    @PostMapping(value = "/getSimDailyStatistics")
    @ApiOperation(value = "Sim卡 日统计", response = SimDailyStatisticsResult.class)
    public Response<List<SimDailyStatisticsResult>> getSimDailyStatistics(@ModelAttribute @ApiParam("sim 请求参数") SimEnter simEnter) {
        return new Response<>(opeSimInformationService.getSimDailyStatistics(simEnter));
    }

    /**
     * @Title: activationSim
     * @Description: // Sim卡 激活
     * @Param: [simEnter]
     * @Return: com.redescooter.ses.api.common.vo.base.Response
     * @Date: 2021/5/28 8:59 上午
     * @Author: Charles
     */
    @PostMapping(value = "/activationSim")
    @ApiOperation(value = "Sim卡 激活")
    public Response activationSim(@ModelAttribute @ApiParam("sim 请求参数") SimEnter simEnter) {
        return new Response<>(opeSimInformationService.activationSim(simEnter));
    }

    /**
     * @Title: deactivatedSim
     * @Description: // Sim卡 停用
     * @Param: [simEnter]
     * @Return: com.redescooter.ses.api.common.vo.base.Response
     * @Date: 2021/5/28 9:00 上午
     * @Author: Charles
     */
    @PostMapping(value = "/deactivatedSim")
    @ApiOperation(value = "Sim卡 停用")
    public Response deactivatedSim(@ModelAttribute @ApiParam("sim 请求参数") SimEnter simEnter) {
        return new Response<>(opeSimInformationService.deactivatedSim(simEnter));
    }


}
