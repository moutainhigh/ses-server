package com.redescooter.ses.admin.dev.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.admin.dev.dm.AdmScooterParts;

import java.util.List;
public interface AdmScooterPartsService extends IService<AdmScooterParts> {


    int updateBatch(List<AdmScooterParts> list);

    int updateBatchSelective(List<AdmScooterParts> list);

    int batchInsert(List<AdmScooterParts> list);

    int insertOrUpdate(AdmScooterParts record);

    int insertOrUpdateSelective(AdmScooterParts record);

}
