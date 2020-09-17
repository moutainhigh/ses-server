package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeSaleArea;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpeSaleAreaMapper extends BaseMapper<OpeSaleArea> {
    int updateBatch(List<OpeSaleArea> list);

    int updateBatchSelective(List<OpeSaleArea> list);

    int batchInsert(@Param("list") List<OpeSaleArea> list);

    int insertOrUpdate(OpeSaleArea record);

    int insertOrUpdateSelective(OpeSaleArea record);
}