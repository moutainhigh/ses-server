package com.redescooter.ses.web.ros.service.sys;

import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.web.ros.vo.sys.dept.*;
import com.redescooter.ses.web.ros.vo.tree.DeptTreeListResult;
import com.redescooter.ses.web.ros.vo.tree.DeptTreeReslt;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.ModelAttribute;

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
     * 部门创建
     *
     * @return
     */
    GeneralResult addSave(AddDeptEnter enter);

    /**
     * 部门树
     *
     * @param enter
     * @return
     */
    List<DeptTreeReslt> trees(GeneralEnter enter);

    /**
     * 部门删除
     *
     * @param enter
     * @return
     */
    GeneralResult deleteDept(IdEnter enter);

    /**
     * 部门树
     *
     * @param enter
     * @return
     */
    List<DeptTreeListResult> deptTrees(DeptListEnter enter);
    /**
     * 部门列表 平行结构
     *
     * @param enter
     * @return
     */
    List<DeptTreeReslt> deptList(GeneralEnter enter);

    /**
     * 员工列表
     *
     * @param enter
     * @return
     */
    List<EmployeeProfileResult> employeeListByDeptId(EmployeeListByDeptIdEnter enter);

    /**
     * 部门编辑
     *
     * @param enter
     * @return
     */
    GeneralResult edit(EditDeptEnter enter);
    /**
     * 查询编辑部门
     *
     * @param enter
     * @return
     */
    SelectDeptResult selectEditDept(IdEnter enter);
    /**
     * 部门编辑
     *
     * @param enter
     * @return
     */
    GeneralResult editDept(UpdateDeptEnter enter);


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
     * 部门详情
     *
     * @param enter
     * @return
     */
    DeptDetailsResult  deptDetails(IdEnter enter);


    /**
     * 部门删除查询
     *
     * @param enter
     * @return
     */
    BooleanResult deleteDeptSelect(IdEnter enter);

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

    /**
     * 负责人列表
     *
     * @param enter
     * @return
     */
    List<PrincipalResult> principals(PrincipalsEnter enter);
}
