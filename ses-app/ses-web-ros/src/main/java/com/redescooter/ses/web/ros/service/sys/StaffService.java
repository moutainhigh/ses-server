package com.redescooter.ses.web.ros.service.sys;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.dm.OpeSysStaff;
import com.redescooter.ses.web.ros.vo.sys.staff.*;

import java.util.List;

/**
 * @ClassNameStaffService
 * @Description
 * @Author Aleks
 * @Date2020/8/31 19:47
 * @Version V1.0
 **/
public interface StaffService {

    GeneralResult staffSave (StaffSaveOrEditEnter enter);


    GeneralResult staffEdit (StaffSaveOrEditEnter enter);


    GeneralResult staffDelete (StaffDeleteEnter enter);


    StaffResult staffDetail (StaffOpEnter enter);


    PageResult<StaffListResult> staffList (StaffListEnter enter);


    /**
     * @Author Aleks
     * @Description  选择负责人的下拉数据来源接口
     * @Date  2020/9/2 14:16
     * @Param
     * @return
     **/
    List<StaffDataResult> principalData(Long tenantId);


    /**
     * @Author Aleks
     * @Description  统计部门下面的总人数
     * @Date  2020/9/2 14:25
     * @Param type为1的时候 id为部门id，查部门下面的人数；type为2的时候 id为岗位id，查岗位下面的人数
     * @return
     **/
    Integer deptStaffCount(Long idd,Integer type);


    GeneralResult openAccount(StaffOpEnter enter);


    /**
     * @Author Aleks
     * @Description  禁用部门下面的员工
     * @Date  2020/9/8 13:59
     * @Param [deptIds]
     * @return
     **/
    void disAbleStaff(List<Long> deptIds);

    void emailToStaff(OpeSysStaff staff, String requestId);

}
