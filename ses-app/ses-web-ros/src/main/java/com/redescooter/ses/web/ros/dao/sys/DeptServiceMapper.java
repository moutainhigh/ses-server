package com.redescooter.ses.web.ros.dao.sys;

import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.web.ros.vo.sys.dept.DeptListEnter;
import com.redescooter.ses.web.ros.vo.sys.dept.EmployeeProfileResult;
import com.redescooter.ses.web.ros.vo.sys.dept.PrincipalResult;
import com.redescooter.ses.web.ros.vo.tree.DeptTreeListResult;
import com.redescooter.ses.web.ros.vo.tree.DeptTreeReslt;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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
     * 获取部门信息
     *
     * @return
     */
    List<DeptTreeListResult> getDeptList(DeptListEnter enter);
    /**
     * 根据部门查询员工信息
     *
     * @return
     */
    List<EmployeeProfileResult> employeeList(@Param("deptIds") List<Long> deptIds, @Param("keyword") String keyword);

    /**
     * @Author Aleks
     * @Description
     * @Date  2020/6/5 10:18
     * @Param [deptId]
     * @return
     **/
    List<Long> getChildDeptIds(@Param("deptId")  Long deptId);

    /**
     * 负责人列表
     *
     * @return
     */
    List<PrincipalResult> principals(List<Long> deptIds);

}
