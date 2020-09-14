package com.redescooter.ses.web.ros.dao.sys;

import com.redescooter.ses.web.ros.vo.sys.staff.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    int totalRows(@Param("enter") StaffListEnter enter,@Param("userIds") List<Long>  userIds);

    List<StaffListResult> staffList(@Param("enter") StaffListEnter enter,List<Long>  userIds);

    List<StaffDataResult> principalData(@Param("tenantId")Long tenantId);

}
