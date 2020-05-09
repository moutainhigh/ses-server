package com.redescooter.ses.service.foundation.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.service.foundation.dm.base.PlaUserPassword;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PlaUserPasswordMapper extends BaseMapper<PlaUserPassword> {
    int updateBatch(List<PlaUserPassword> list);

    int batchInsert(@Param("list") List<PlaUserPassword> list);

    int insertOrUpdate(PlaUserPassword record);

    int insertOrUpdateSelective(PlaUserPassword record);
}