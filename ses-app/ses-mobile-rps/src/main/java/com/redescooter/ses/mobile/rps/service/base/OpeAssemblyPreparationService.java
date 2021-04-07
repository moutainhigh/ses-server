package com.redescooter.ses.mobile.rps.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.mobile.rps.dm.OpeAssemblyPreparation;

import java.util.List;

public interface OpeAssemblyPreparationService extends IService<OpeAssemblyPreparation>{


    int updateBatch(List<OpeAssemblyPreparation> list);

    int batchInsert(List<OpeAssemblyPreparation> list);

    int insertOrUpdate(OpeAssemblyPreparation record);

    int insertOrUpdateSelective(OpeAssemblyPreparation record);

}
