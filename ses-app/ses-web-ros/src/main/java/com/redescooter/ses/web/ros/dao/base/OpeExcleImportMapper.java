package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeExcleImport;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OpeExcleImportMapper extends BaseMapper<OpeExcleImport> {
    int updateBatch(List<OpeExcleImport> list);

    int batchInsert(@Param("list") List<OpeExcleImport> list);

    int insertOrUpdate(OpeExcleImport record);

    int insertOrUpdateSelective(OpeExcleImport record);

    int insertOrUpdateWithBLOBs(OpeExcleImport record);
}