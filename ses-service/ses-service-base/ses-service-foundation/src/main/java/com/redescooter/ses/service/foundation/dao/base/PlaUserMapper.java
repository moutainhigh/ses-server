package com.redescooter.ses.service.foundation.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.service.foundation.dm.base.PlaUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PlaUserMapper extends BaseMapper<PlaUser> {
    int updateBatch(List<PlaUser> list);

    int batchInsert(@Param("list") List<PlaUser> list);

    int insertOrUpdate(PlaUser record);

    int insertOrUpdateSelective(PlaUser record);

}