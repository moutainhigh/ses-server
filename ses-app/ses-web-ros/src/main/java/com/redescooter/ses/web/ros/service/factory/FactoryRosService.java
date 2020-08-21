package com.redescooter.ses.web.ros.service.factory;

import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.web.ros.dm.OpeFactory;
import com.redescooter.ses.web.ros.vo.factory.FactoryEditEnter;
import com.redescooter.ses.web.ros.vo.factory.FactoryPage;
import com.redescooter.ses.web.ros.vo.factory.FactoryResult;
import com.redescooter.ses.web.ros.vo.factory.FactorySaveEnter;

import java.util.Map;

public interface FactoryRosService {

    /**
     * 状态统计
     *
     * @param enter
     * @return
     */
    Map<String, Integer> countStatus(GeneralEnter enter);

    /**
     * 保存代工厂
     *
     * @param enter
     * @return
     */
    GeneralResult save(FactorySaveEnter enter);

    /**
     * 编辑代工厂
     *
     * @param enter
     * @return
     */
    GeneralResult edit(FactoryEditEnter enter);

    /**
     * 代工厂详情
     *
     * @param enter
     * @return
     */
    FactoryResult details(IdEnter enter);


    /**
     * 代工厂列表
     *
     * @param page
     * @return
     */
    PageResult<FactoryResult> list(FactoryPage page);

    /**
     * 保存代工厂操作事件节点
     *
     * @param event
     * @param factory
     * @return
     */
    GeneralResult saveFactoryTrace(String event, OpeFactory factory);

}
