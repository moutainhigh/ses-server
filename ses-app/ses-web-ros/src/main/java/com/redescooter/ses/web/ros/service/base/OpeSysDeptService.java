package com.redescooter.ses.web.ros.service.base;

import java.util.List;
import com.redescooter.ses.web.ros.dm.OpeSysDept;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface OpeSysDeptService extends IService<OpeSysDept> {


    int updateBatch(List<OpeSysDept> list);

    int batchInsert(List<OpeSysDept> list);

    int insertOrUpdate(OpeSysDept record);

    int insertOrUpdateSelective(OpeSysDept record);

}

