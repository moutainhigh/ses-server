package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeParts;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpePartsMapper extends BaseMapper<OpeParts> {
    int updateBatch(List<OpeParts> list);

    int batchInsert(@Param("list") List<OpeParts> list);

    int insertOrUpdate(OpeParts record);

    int insertOrUpdateSelective(OpeParts record);
}