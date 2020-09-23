package com.redescooter.ses.web.ros.dao.restproduction;

import com.redescooter.ses.web.ros.vo.restproduct.production.RosProductionScooterListEnter;
import com.redescooter.ses.web.ros.vo.restproduct.production.RosProductionScooterListResult;

import java.util.List;

public interface RosProductionProductServiceMapper {
    /**
     * 车辆草稿列表
     * 
     * @param enter
     * @return
     */
    List<RosProductionScooterListResult> scooterDraftList(RosProductionScooterListEnter enter);

    /**
     * 车辆列表
     * 
     * @param enter
     * @return
     */
    List<RosProductionScooterListResult> scooterBomList(RosProductionScooterListEnter enter);
}
