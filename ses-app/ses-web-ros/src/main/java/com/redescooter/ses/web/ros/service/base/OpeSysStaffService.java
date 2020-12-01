package com.redescooter.ses.web.ros.service.base;

import java.util.List;
import com.redescooter.ses.web.ros.dm.OpeSysStaff;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface OpeSysStaffService extends IService<OpeSysStaff> {


    int updateBatch(List<OpeSysStaff> list);

    int batchInsert(List<OpeSysStaff> list);

    int insertOrUpdate(OpeSysStaff record);

    int insertOrUpdateSelective(OpeSysStaff record);

    int updateBatchSelective(List<OpeSysStaff> list);
}






