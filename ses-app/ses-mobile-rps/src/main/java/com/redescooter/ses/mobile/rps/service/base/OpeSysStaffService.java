package com.redescooter.ses.mobile.rps.service.base;


import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.mobile.rps.dm.OpeSysStaff;

import java.util.List;

public interface OpeSysStaffService extends IService<OpeSysStaff> {


    int updateBatch(List<OpeSysStaff> list);

    int batchInsert(List<OpeSysStaff> list);

    int insertOrUpdate(OpeSysStaff record);

    int insertOrUpdateSelective(OpeSysStaff record);

}
