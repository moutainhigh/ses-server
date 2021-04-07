package com.redescooter.ses.mobile.rps.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.mobile.rps.dm.OpeAllocateB;

import java.util.List;

public interface OpeAllocateBService extends IService<OpeAllocateB>{


    int updateBatch(List<OpeAllocateB> list);

    int batchInsert(List<OpeAllocateB> list);

    int insertOrUpdate(OpeAllocateB record);

    int insertOrUpdateSelective(OpeAllocateB record);

}
