package com.redescooter.ses.web.ros.service.sys;

import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.web.ros.dm.OpeSysRoleSalesCidy;
import com.redescooter.ses.web.ros.vo.tree.SalesAreaTressResult;

import java.util.List;

/**
 * @ClassName SalesAreaService
 * @Author Jerry
 * @date 2020/03/11 14:03
 * @Description:
 */
public interface SalesAreaService {

    /**
     * 根据岗位角色ID获取销售区域列表
     *
     * @return
     */
    List<SalesAreaTressResult> list(IdEnter enter);

    /**
     * 根据岗位角色ID获取销售区域树列表
     *
     * @param enter
     * @return
     */
    List<SalesAreaTressResult> trees(IdEnter enter);

    /**
     * 删除角色下对应的销售区域
     *
     * @param enter
     */
    void deleteRoleSalesAreaByRoleId(IdEnter enter);
}
