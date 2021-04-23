package com.redescooter.ses.mobile.rps.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.mobile.rps.dm.OpeEntrustPartsB;

import java.util.List;

public interface OpeEntrustPartsBService extends IService<OpeEntrustPartsB> {


    int updateBatch(List<OpeEntrustPartsB> list);

    int batchInsert(List<OpeEntrustPartsB> list);

    int insertOrUpdate(OpeEntrustPartsB record);

    int insertOrUpdateSelective(OpeEntrustPartsB record);

}

