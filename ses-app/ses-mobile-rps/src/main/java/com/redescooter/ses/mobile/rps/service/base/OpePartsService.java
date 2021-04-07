package com.redescooter.ses.mobile.rps.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.mobile.rps.dm.OpeParts;

import java.util.List;

public interface OpePartsService extends IService<OpeParts> {


    int updateBatch(List<OpeParts> list);

    int batchInsert(List<OpeParts> list);

    int insertOrUpdate(OpeParts record);

    int insertOrUpdateSelective(OpeParts record);
}



