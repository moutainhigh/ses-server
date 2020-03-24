package com.redescooter.ses.web.ros.service.base;

import com.redescooter.ses.web.ros.dm.OpeProductionImport;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

public interface OpeProductionImportService extends IService<OpeProductionImport> {


    int updateBatch(List<OpeProductionImport> list);

    int batchInsert(List<OpeProductionImport> list);

    int insertOrUpdate(OpeProductionImport record);

    int insertOrUpdateSelective(OpeProductionImport record);

}
