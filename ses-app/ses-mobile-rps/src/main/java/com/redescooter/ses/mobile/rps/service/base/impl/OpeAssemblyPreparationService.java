package com.redescooter.ses.mobile.rps.service.base.impl;

import java.util.List;
import com.redescooter.ses.mobile.rps.dm.OpeAssemblyPreparation;
import com.baomidou.mybatisplus.extension.service.IService;
public interface OpeAssemblyPreparationService extends IService<OpeAssemblyPreparation>{


    int updateBatch(List<OpeAssemblyPreparation> list);

    int batchInsert(List<OpeAssemblyPreparation> list);

    int insertOrUpdate(OpeAssemblyPreparation record);

    int insertOrUpdateSelective(OpeAssemblyPreparation record);

}
