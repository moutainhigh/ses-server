package com.redescooter.ses.web.ros.service.sys;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.web.ros.vo.sys.staff.StaffOpEnter;
import com.redescooter.ses.web.ros.vo.sys.staff.StaffResult;
import com.redescooter.ses.web.ros.vo.sys.staff.StaffSaveOrEditEnter;

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


    GeneralResult staffDelete (StaffOpEnter enter);


    StaffResult staffDetail (StaffOpEnter enter);



}
