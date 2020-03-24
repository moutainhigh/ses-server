package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeProductionImport;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface OpeProductionImportMapper extends BaseMapper<OpeProductionImport> {
    int updateBatch(List<OpeProductionImport> list);

    int batchInsert(@Param("list") List<OpeProductionImport> list);

    int insertOrUpdate(OpeProductionImport record);

    int insertOrUpdateSelective(OpeProductionImport record);
}