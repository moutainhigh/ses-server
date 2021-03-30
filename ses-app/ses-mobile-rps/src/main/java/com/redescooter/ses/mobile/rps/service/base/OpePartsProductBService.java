package com.redescooter.ses.mobile.rps.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.mobile.rps.dm.OpePartsProductB;

import java.util.List;

public interface OpePartsProductBService extends IService<OpePartsProductB>{


    int updateBatch(List<OpePartsProductB> list);

    int batchInsert(List<OpePartsProductB> list);

    int insertOrUpdate(OpePartsProductB record);

    int insertOrUpdateSelective(OpePartsProductB record);

}
