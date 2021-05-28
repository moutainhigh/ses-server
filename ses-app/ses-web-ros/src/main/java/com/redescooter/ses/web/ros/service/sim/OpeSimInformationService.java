package com.redescooter.ses.web.ros.service.sim;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeSimInformation;
import com.redescooter.ses.web.ros.vo.sim.*;

import java.util.List;

/**
 * sim卡信息(OpeSimInformation)表服务实现类
 *
 * @author Charles
 * @since 2021-05-26 20:58:05
 */
public interface OpeSimInformationService extends IService<OpeSimInformation> {

    /**
     * @Title: getSimCardList
     * @Description: // Sim卡 列表信息
     * @Param: [simEnter]
     * @Return: com.redescooter.ses.api.common.vo.base.Response<com.redescooter.ses.web.ros.vo.sim.SimDataResult>
     * @Date: 2021/5/28 8:58 上午
     * @Author: Charles
     */
    SimDataResult getSimCardList(SimEnter simEnter);

    /**
     * @Title: getSimDailyStatistics
     * @Description: // Sim卡 日统计
     * @Param: [simEnter]
     * @Return: com.redescooter.ses.api.common.vo.base.Response<java.util.List < com.redescooter.ses.web.ros.vo.sim.SimDailyStatisticsResult>>
     * @Date: 2021/5/28 8:59 上午
     * @Author: Charles
     */
    List<SimDailyStatisticsResult> getSimDailyStatistics(SimEnter simEnter);

    /**
     * @Title: getSimConnectRecord
     * @Description: // 获取连接记录
     * @Param: [simEnter]
     * @Return: com.redescooter.ses.api.common.vo.base.Response<com.redescooter.ses.web.ros.vo.sim.SimDataResult>
     * @Date: 2021/5/28 8:58 上午
     * @Author: Charles
     */
    SimDataResult getSimConnectRecord(SimEnter simEnter);

    /**
     * @Title: getSimTransactionRecords
     * @Description: // 获取交易记录
     * @Param: [simEnter]
     * @Return: com.redescooter.ses.api.common.vo.base.Response<com.redescooter.ses.web.ros.vo.sim.SimDataResult>
     * @Date: 2021/5/28 8:58 上午
     * @Author: Charles
     */
    SimDataResult getSimTransactionRecords(SimEnter simEnter);

    /**
     * @Title: getSimDetails
     * @Description: // Sim卡 详情
     * @Param: [simEnter]
     * @Return: com.redescooter.ses.api.common.vo.base.Response<com.redescooter.ses.web.ros.vo.sim.SimBaseCodeResult>
     * @Date: 2021/5/28 8:59 上午
     * @Author: Charles
     */
    SimBaseCodeResult getSimDetails(SimEnter simEnter);

    /**
     * @Title: activationSim
     * @Description: // Sim卡 激活
     * @Param: [simEnter]
     * @Return: com.redescooter.ses.api.common.vo.base.Response
     * @Date: 2021/5/28 8:59 上午
     * @Author: Charles
     */
    boolean activationSim(SimEnter simEnter);

    /**
     * @Title: deactivatedSim
     * @Description: // Sim卡 停用
     * @Param: [simEnter]
     * @Return: com.redescooter.ses.api.common.vo.base.Response
     * @Date: 2021/5/28 9:00 上午
     * @Author: Charles
     */
    boolean deactivatedSim(SimEnter simEnter);

    /**
     * @Title: getSimEssentialInformation
     * @Description: // 获取sim基本信息
     * @Param: []
     * @Return: com.redescooter.ses.api.common.vo.base.Response<com.redescooter.ses.web.ros.vo.sim.SimResult>
     * @Date: 2021/5/28 9:40 上午
     * @Author: Charles
     */
    SimResult getSimEssentialInformation();
}
