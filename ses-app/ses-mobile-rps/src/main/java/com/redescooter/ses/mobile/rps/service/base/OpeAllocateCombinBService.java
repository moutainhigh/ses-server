package com.redescooter.ses.mobile.rps.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.mobile.rps.dm.OpeAllocateCombinB;

import java.util.List;

public interface OpeAllocateCombinBService extends IService<OpeAllocateCombinB> {


    int updateBatch(List<OpeAllocateCombinB> list);

    int batchInsert(List<OpeAllocateCombinB> list);

    int insertOrUpdate(OpeAllocateCombinB record);

    int insertOrUpdateSelective(OpeAllocateCombinB record);

}
