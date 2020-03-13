package com.redescooter.ses.web.ros.service.sys;

import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.web.ros.dm.OpeSysRoleSalesCidy;
import com.redescooter.ses.web.ros.vo.tree.SalesAreaTressResult;

import java.util.List;

/**
 * @ClassName SysSalesAreaService
 * @Author Jerry
 * @date 2020/03/11 14:03
 * @Description:
 */
public interface SysSalesAreaService {

    /**
     * 获取销售区域列表
     *
     * @return
     */
    List<SalesAreaTressResult> list(IdEnter enter);

    /**
     * 获取销售区域树列表
     *
     * @param enter
     * @return
     */
    List<SalesAreaTressResult> trees(IdEnter enter);
}
