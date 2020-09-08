package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.SellsyException;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SellsyExceptionMapper extends BaseMapper<SellsyException> {
    int updateBatch(List<SellsyException> list);

    int batchInsert(@Param("list") List<SellsyException> list);

    int insertOrUpdate(SellsyException record);

    int insertOrUpdateSelective(SellsyException record);
}