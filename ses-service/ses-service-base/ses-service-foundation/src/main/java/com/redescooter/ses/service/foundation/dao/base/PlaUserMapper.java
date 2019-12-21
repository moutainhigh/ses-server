package com.redescooter.ses.service.foundation.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.service.foundation.dm.base.PlaUser;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PlaUserMapper extends BaseMapper<PlaUser> {
    int updateBatch(List<PlaUser> list);

    int batchInsert(@Param("list") List<PlaUser> list);

    int insertOrUpdate(PlaUser record);

    int insertOrUpdateSelective(PlaUser record);
}