package com.redescooter.ses.mobile.rps.service.base;

import java.util.List;

import com.redescooter.ses.mobile.rps.dm.OpeAllocateScooterB;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OpeAllocateScooterBService extends IService<OpeAllocateScooterB> {


    int updateBatch(List<OpeAllocateScooterB> list);

    int batchInsert(List<OpeAllocateScooterB> list);

    int insertOrUpdate(OpeAllocateScooterB record);

    int insertOrUpdateSelective(OpeAllocateScooterB record);

}
