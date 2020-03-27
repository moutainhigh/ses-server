package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeSalesArea;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional

public interface OpeSalesAreaService extends IService<OpeSalesArea> {


    int updateBatch(List<OpeSalesArea> list);

    int batchInsert(List<OpeSalesArea> list);

    int insertOrUpdate(OpeSalesArea record);

    int insertOrUpdateSelective(OpeSalesArea record);

}

