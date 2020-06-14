package com.redescooter.ses.web.ros.dao.sys;

import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.web.ros.vo.sys.employee.EmployeeDeptResult;
import com.redescooter.ses.web.ros.vo.sys.employee.EmployeeListEnter;
import com.redescooter.ses.web.ros.vo.sys.employee.DeptEmployeeListResult;
import com.redescooter.ses.web.ros.vo.sys.employee.EmployeeResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName:EmployeeService
 * @description: EmployeeService
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/09 13:08
 */
public interface EmployeeServiceMapper {
    /**
     * 员工列表中的 部门列表
     *
     * @param enter
     * @return
     */
    List<DeptEmployeeListResult> deptList(EmployeeListEnter enter);

    /**
     * 员工列表
     *
     * @param enter
     * @return
     */
    List<EmployeeResult> employeeList(EmployeeListEnter enter);

    /**
     * 员工详情
     *
     * @param enter
     * @return
     */
    EmployeeResult employeeDetail(IdEnter enter);

    /**
     * 根据父级deptId 查询所有子集deptId
     *
     * @param tenantId
     * @param id
     * @return
     */
    List<Long> getEmployeeDeptChildList(@Param("tenantId") Long tenantId, @Param("id") Long id);

    /**
     * 根据当前级deptId 查询所有父级deptId
     *
     * @param tenantId
     * @param ids
     * @return
     */
    List<Long> getEmployeeDeptFatherList(Long tenantId, List<Long> ids);

    /**
     * 员工模块 部门小列表
     *
     * @param tenantId
     * @param ids
     * @return
     */
    List<EmployeeDeptResult> getEmployeeDeptList(@Param("tenantId") Long tenantId, @Param("ids") List<Long> ids);

    /**
     * 员工模块 职位列表
     *
     * @param tenantId
     * @param ids
     * @return
     */
    List<EmployeeDeptResult> getEmployeePositionList(@Param("tenantId") Long tenantId, @Param("ids") List<Long> ids, @Param("adminRole") String adminRole);

    /**
     * 员工模块 办公区域列表
     *
     * @param tenantId
     * @return
     */
    List<EmployeeDeptResult> getEmployeeOfficeareaList(@Param("tenantId") Long tenantId);

    /**
     * 员工模块 公司列表
     *
     * @param tenantId
     * @param bizId
     * @return
     */
    List<EmployeeDeptResult> getEmployeeCompanyList(@Param("tenantId") Long tenantId, @Param("bizId") Long bizId);
}
