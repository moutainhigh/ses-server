package com.redescooter.ses.web.ros.service.sim;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeSimInformation;
import com.redescooter.ses.web.ros.vo.sim.SimBaseCodeResult;
import com.redescooter.ses.web.ros.vo.sim.SimDailyStatisticsResult;
import com.redescooter.ses.web.ros.vo.sim.SimDataResult;
import com.redescooter.ses.web.ros.vo.sim.SimEnter;

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
     * @Return: com.redescooter.ses.api.common.vo.base.Response<java.util.List < com.redescooter.ses.web.ros.vo.sim.SimCardList>>
     * @Date: 2021/5/27 1:28 下午
     * @Author: Charles
     */
    SimDataResult getSimCardList(SimEnter simEnter);

    List<SimDailyStatisticsResult> getSimDailyStatistics(SimEnter simEnter);

    SimDataResult getSimConnectRecord(SimEnter simEnter);

    SimDataResult getSimTransactionRecords(SimEnter simEnter);

    SimBaseCodeResult getSimDetails(SimEnter simEnter);

    boolean activationSim(SimEnter simEnter);

    boolean deactivatedSim(SimEnter simEnter);
}
