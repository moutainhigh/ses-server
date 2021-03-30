package com.redescooter.ses.mobile.rps.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.mobile.rps.dm.OpeOutWhScooterB;

import java.util.List;

public interface OpeOutWhScooterBService extends IService<OpeOutWhScooterB> {


    int updateBatch(List<OpeOutWhScooterB> list);

    int batchInsert(List<OpeOutWhScooterB> list);

    int insertOrUpdate(OpeOutWhScooterB record);

    int insertOrUpdateSelective(OpeOutWhScooterB record);

    int updateBatchSelective(List<OpeOutWhScooterB> list);
}



