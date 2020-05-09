package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.PdmanDbVersion;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface PdmanDbVersionMapper extends BaseMapper<PdmanDbVersion> {
    int batchInsert(@Param("list") List<PdmanDbVersion> list);

    int insertOrUpdate(PdmanDbVersion record);

    int insertOrUpdateSelective(PdmanDbVersion record);
}