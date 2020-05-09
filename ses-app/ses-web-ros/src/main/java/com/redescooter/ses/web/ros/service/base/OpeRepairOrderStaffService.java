package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeRepairOrderStaff;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional

public interface OpeRepairOrderStaffService extends IService<OpeRepairOrderStaff> {


    int updateBatch(List<OpeRepairOrderStaff> list);

    int batchInsert(List<OpeRepairOrderStaff> list);

    int insertOrUpdate(OpeRepairOrderStaff record);

    int insertOrUpdateSelective(OpeRepairOrderStaff record);

}
