package com.redescooter.ses.service.foundation.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.service.foundation.dm.base.PlaJpushUser;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PlaJpushUserMapper extends BaseMapper<PlaJpushUser> {
    int updateBatch(List<PlaJpushUser> list);

    int batchInsert(@Param("list") List<PlaJpushUser> list);

    int insertOrUpdate(PlaJpushUser record);

    int insertOrUpdateSelective(PlaJpushUser record);
}