package com.redescooter.ses.web.ros.dao.sys;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.web.ros.dm.OpeSysDept;
import com.redescooter.ses.web.ros.vo.sys.dept.*;
import com.redescooter.ses.web.ros.vo.tree.DeptTreeListResult;
import com.redescooter.ses.web.ros.vo.tree.DeptTreeReslt;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * @ClassName:SysRoleServiceMapper
 * @description: RoleServiceMapper
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/13 12:20
 */
public interface DeptServiceMapper {

    /**
     * 获取顶级部门
     *
     * @param enter
     * @param level
     * @return
     */
    DeptTreeReslt topDeptartment(@Param("enter") IdEnter enter, @Param("level") String level);

    /**
     * 获取部门信息
     *
     * @return
     */
    List<DeptTreeReslt> deptList();

    /**
     * 获取编辑部门信息
     *
     * @return
     * */
    SelectDeptResult selectEditDept (Long id);
    /**
     * 获取部门信息
     *
     * @return
     */
    List<DeptTypeResult> deptType(@Param("enter") TypeListEnter enter);

    /**
     * 获取部门信息
     *
     * @return
     */
    List<DeptTreeListResult> getDeptList(@Param("enter") DeptListEnter enter, @Param("deptIds")Set<Long> deptIds);

    List<DeptTreeListResult> saveDeptSelectParent(@Param("enter") DeptListEnter enter);
    /**
     * 根据部门查询员工信息
     *
     * @return
     */
    List<EmployeeProfileResult> employeeList(@Param("deptIds") List<Long> deptIds, @Param("keyword") String keyword);

    /**
     * @Author Aleks
     * @Description 向下递归，查找子部门的id
     * @Date  2020/6/5 10:18
     * @Param [deptId]
     * @return
     **/
    List<Long> getChildDeptIds(@Param("deptId")  Long deptId);

    /**
     * @Author Aleks
     * @Description  向上递归 查找当前部门和上面的所有父级部门的id
     * @Date  2020/9/16 10:28
     * @Param [deptId]
     * @return
     **/
    Set<Long> getParentIds(@Param("deptId")  Long deptId);


    /**
     * @Author Aleks
     * @Description 向下递归，查找子部门
     * @Date  2020/6/5 10:18
     * @Param [deptId]
     * @return
     **/
    List<OpeSysDept> getChildDept(@Param("deptId")  Long deptId);

    /**
     * 负责人列表
     *
     * @return
     */
    List<PrincipalResult> principals(List<Long> deptIds);


    /*
     * @Author Aleks
     * @Description  通过角色id找到对应的部门id
     * @Date  2020/9/14 18:33
     * @Param
     * @return
     **/
    Long getDeptIdByRoleId(@Param("roleId")Long roleId);
}
