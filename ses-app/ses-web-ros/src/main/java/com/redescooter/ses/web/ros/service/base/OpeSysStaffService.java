package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeSysStaff;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface OpeSysStaffService extends IService<OpeSysStaff> {


    int updateBatch(List<OpeSysStaff> list);

    int batchInsert(List<OpeSysStaff> list);

    int insertOrUpdate(OpeSysStaff record);

    int insertOrUpdateSelective(OpeSysStaff record);

}




