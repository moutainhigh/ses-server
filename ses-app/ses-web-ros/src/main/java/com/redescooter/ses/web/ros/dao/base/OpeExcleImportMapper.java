package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeExcleImport;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpeExcleImportMapper extends BaseMapper<OpeExcleImport> {
    int updateBatch(List<OpeExcleImport> list);

    int batchInsert(@Param("list") List<OpeExcleImport> list);

    int insertOrUpdate(OpeExcleImport record);

    int insertOrUpdateSelective(OpeExcleImport record);
}