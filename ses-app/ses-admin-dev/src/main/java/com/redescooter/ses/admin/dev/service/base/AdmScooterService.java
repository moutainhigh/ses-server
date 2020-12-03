package com.redescooter.ses.admin.dev.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.admin.dev.dm.AdmScooter;

import java.util.List;
public interface AdmScooterService extends IService<AdmScooter> {


    int updateBatch(List<AdmScooter> list);

    int updateBatchSelective(List<AdmScooter> list);

    int batchInsert(List<AdmScooter> list);

    int insertOrUpdate(AdmScooter record);

    int insertOrUpdateSelective(AdmScooter record);

}
