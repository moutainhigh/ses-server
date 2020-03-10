package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeExcleImport;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface OpeExcleImportService extends IService<OpeExcleImport> {


    int updateBatch(List<OpeExcleImport> list);

    int batchInsert(List<OpeExcleImport> list);

    int insertOrUpdate(OpeExcleImport record);

    int insertOrUpdateSelective(OpeExcleImport record);

    int insertOrUpdateWithBLOBs(OpeExcleImport record);

}

