package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeProductionImport;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional

public interface OpeProductionImportService extends IService<OpeProductionImport> {


    int updateBatch(List<OpeProductionImport> list);

    int batchInsert(List<OpeProductionImport> list);

    int insertOrUpdate(OpeProductionImport record);

    int insertOrUpdateSelective(OpeProductionImport record);

}
