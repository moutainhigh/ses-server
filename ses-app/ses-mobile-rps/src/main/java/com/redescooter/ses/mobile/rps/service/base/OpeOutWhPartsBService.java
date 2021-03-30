package com.redescooter.ses.mobile.rps.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.mobile.rps.dm.OpeOutWhPartsB;

import java.util.List;

public interface OpeOutWhPartsBService extends IService<OpeOutWhPartsB> {


    int updateBatch(List<OpeOutWhPartsB> list);

    int batchInsert(List<OpeOutWhPartsB> list);

    int insertOrUpdate(OpeOutWhPartsB record);

    int insertOrUpdateSelective(OpeOutWhPartsB record);

    int updateBatchSelective(List<OpeOutWhPartsB> list);
}



