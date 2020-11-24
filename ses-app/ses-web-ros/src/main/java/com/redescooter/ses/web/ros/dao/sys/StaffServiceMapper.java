package com.redescooter.ses.web.ros.dao.sys;

import com.redescooter.ses.web.ros.dm.OpeSysRoleData;
import com.redescooter.ses.web.ros.vo.restproductionorder.allocateorder.UserDataEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.allocateorder.UserDataResult;
import com.redescooter.ses.web.ros.vo.sys.staff.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * @ClassNameStaffServiceMapper
 * @Description
 * @Author Aleks
 * @Date2020/8/31 19:14
 * @Version V1.0
 **/
public interface StaffServiceMapper {

    StaffResult staffDetail(@Param("id") Long id);

    List<Long> userIds(@Param("id") Long id);

    StaffRoleResult staffRoleMsg(@Param("staffId") Long staffId);

    int totalRows(@Param("enter") StaffListEnter enter, @Param("userIds") List<Long>  userIds, @Param("deptIds")Set<Long> deptIds);

    List<StaffListResult> staffList(@Param("enter") StaffListEnter enter,List<Long>  userIds,@Param("deptIds")Set<Long> deptIds);

    List<StaffDataResult> principalData(@Param("tenantId")Long tenantId);

    /**
     * @Author Aleks
     * @Description
     * @Date  2020/9/14 17:45
     * @Param 获取用户对应的角色有哪些部门的数据权限
     * @return
     **/
     List<OpeSysRoleData> roleDatas(@Param("id") Long id);


    List<StaffDataResult> announUser(@Param("tenantId") Long tenantId);


    List<UserDataResult> userData(@Param("enter") UserDataEnter enter);
}
