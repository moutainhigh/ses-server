package com.redescooter.ses.web.ros.service.base;

import java.util.List;

import com.redescooter.ses.web.ros.dm.OpeRepairOrderStaff;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OpeRepairOrderStaffService extends IService<OpeRepairOrderStaff> {


    int updateBatch(List<OpeRepairOrderStaff> list);

    int batchInsert(List<OpeRepairOrderStaff> list);

    int insertOrUpdate(OpeRepairOrderStaff record);

    int insertOrUpdateSelective(OpeRepairOrderStaff record);

}
