package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeParts;
import io.seata.spring.annotation.GlobalTransactional;

import java.util.List;

public interface OpePartsService extends IService<OpeParts> {


    int updateBatch(List<OpeParts> list);

    int batchInsert(List<OpeParts> list);

    int insertOrUpdate(OpeParts record);

    int insertOrUpdateSelective(OpeParts record);

}


