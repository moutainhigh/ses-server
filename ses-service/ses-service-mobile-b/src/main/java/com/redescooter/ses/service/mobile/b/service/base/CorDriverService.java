package com.redescooter.ses.service.mobile.b.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.service.mobile.b.dm.base.CorDriver;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface CorDriverService extends IService<CorDriver> {


    int updateBatch(List<CorDriver> list);

    int batchInsert(List<CorDriver> list);

    int insertOrUpdate(CorDriver record);

    int insertOrUpdateSelective(CorDriver record);

}

