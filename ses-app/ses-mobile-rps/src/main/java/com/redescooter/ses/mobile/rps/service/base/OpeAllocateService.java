package com.redescooter.ses.mobile.rps.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.mobile.rps.dm.OpeAllocate;

import java.util.List;

public interface OpeAllocateService extends IService<OpeAllocate> {


    int updateBatch(List<OpeAllocate> list);

    int batchInsert(List<OpeAllocate> list);

    int insertOrUpdate(OpeAllocate record);

    int insertOrUpdateSelective(OpeAllocate record);

}


