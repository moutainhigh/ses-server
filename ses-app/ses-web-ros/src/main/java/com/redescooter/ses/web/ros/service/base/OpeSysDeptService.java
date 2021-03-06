package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeSysDept;

import java.util.List;

public interface OpeSysDeptService extends IService<OpeSysDept> {


    int updateBatch(List<OpeSysDept> list);

    int batchInsert(List<OpeSysDept> list);

    int insertOrUpdate(OpeSysDept record);

    int insertOrUpdateSelective(OpeSysDept record);

    int updateBatchSelective(List<OpeSysDept> list);
}







