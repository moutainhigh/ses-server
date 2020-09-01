package com.redescooter.ses.web.ros.dao.sys;

import com.redescooter.ses.web.ros.vo.sys.staff.StaffResult;
import org.apache.ibatis.annotations.Param;

/**
 * @ClassNameStaffServiceMapper
 * @Description
 * @Author Aleks
 * @Date2020/8/31 19:14
 * @Version V1.0
 **/
public interface StaffServiceMapper {

    StaffResult staffDetail(@Param("id") Long id);

}
