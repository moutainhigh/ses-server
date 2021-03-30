package com.redescooter.ses.service.scooter.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.service.scooter.dm.base.ScoScooter;

import java.util.List;

public interface ScoScooterService extends IService<ScoScooter> {


    int updateBatch(List<ScoScooter> list);

    int batchInsert(List<ScoScooter> list);

    int insertOrUpdate(ScoScooter record);

    int insertOrUpdateSelective(ScoScooter record);

    int updateBatchSelective(List<ScoScooter> list);
}

