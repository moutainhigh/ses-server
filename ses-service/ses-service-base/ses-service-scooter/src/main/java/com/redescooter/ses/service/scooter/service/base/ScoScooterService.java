package com.redescooter.ses.service.scooter.service.base;

import java.util.List;

import com.redescooter.ses.service.scooter.dm.base.ScoScooter;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ScoScooterService extends IService<ScoScooter> {


    int updateBatch(List<ScoScooter> list);

    int batchInsert(List<ScoScooter> list);

    int insertOrUpdate(ScoScooter record);

    int insertOrUpdateSelective(ScoScooter record);

    int updateBatchSelective(List<ScoScooter> list);
}

