package com.redescooter.ses.web.ros.service.sys;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.web.ros.vo.sys.dept.EditDeptEnter;
import com.redescooter.ses.web.ros.vo.sys.dept.SaveDeptEnter;
import com.redescooter.ses.web.ros.vo.tree.DeptTreeReslt;

import java.util.List;

/**
 * @ClassName SysDeptService
 * @Author Jerry
 * @date 2020/03/11 20:06
 * @Description:
 */
public interface SysDeptService {

    /**
     * 部门创建
     *
     * @return
     */
    GeneralResult save(SaveDeptEnter enter);

    /**
     * 部门树
     *
     * @param enter
     * @return
     */
    List<DeptTreeReslt> trees(GeneralEnter enter);

    /**
     * 部门编辑
     *
     * @param enter
     * @return
     */
    GeneralResult edit(EditDeptEnter enter);

    /**
     * 部门删除
     *
     * @param enter
     * @return
     */
    GeneralResult delete(IdEnter enter);

    /**
     * 部门详情
     *
     * @param enter
     * @return
     */
    DeptTreeReslt details(IdEnter enter);

    /**
     * 获取部门子列表
     *
     * @param enter
     * @return
     */
    DeptTreeReslt getDescendants(IdEnter enter);

    /**
     * 获取顶级部门
     *
     * @param enter
     * @param level
     * @return
     */
    DeptTreeReslt topDeptartment(IdEnter enter, String level);

}
