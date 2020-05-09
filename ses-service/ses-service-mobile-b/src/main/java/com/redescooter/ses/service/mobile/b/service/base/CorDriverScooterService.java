package com.redescooter.ses.service.mobile.b.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.service.mobile.b.dm.base.CorDriverScooter;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface CorDriverScooterService extends IService<CorDriverScooter> {


    int updateBatch(List<CorDriverScooter> list);

    int batchInsert(List<CorDriverScooter> list);

    int insertOrUpdate(CorDriverScooter record);

    int insertOrUpdateSelective(CorDriverScooter record);

}

