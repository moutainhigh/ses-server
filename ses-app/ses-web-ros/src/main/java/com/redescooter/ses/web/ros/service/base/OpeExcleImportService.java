package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeExcleImport;

import java.util.List;


public interface OpeExcleImportService extends IService<OpeExcleImport>{


    int updateBatch(List<OpeExcleImport> list);

    int batchInsert(List<OpeExcleImport> list);

    int insertOrUpdate(OpeExcleImport record);

    int insertOrUpdateSelective(OpeExcleImport record);

}
