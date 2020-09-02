package com.redescooter.ses.web.ros.service.sys;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.PageResult;
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
     * @Param
     * @return
     **/
    Integer deptStaffCount(Long deptId);

}
