package com.redescooter.ses.web.ros.service.sellsy;

import com.redescooter.ses.web.ros.vo.sellsy.result.staff.SellsyStaffGroupResult;
import com.redescooter.ses.web.ros.vo.sellsy.result.staff.SellsyStaffResult;

import java.util.List;

public interface SellsyStaffService {

    /**
     * 员工列表
     * 
     * @return
     */
    public List<SellsyStaffResult> queryStaffList();

    /**
     * 查询指定员工
     * 
     * @param enter
     * @return
     */
    public SellsyStaffGroupResult queryStaffGroups();
}
