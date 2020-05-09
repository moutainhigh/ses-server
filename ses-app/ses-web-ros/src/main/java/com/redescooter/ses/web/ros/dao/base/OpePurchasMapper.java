package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpePurchas;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpePurchasMapper extends BaseMapper<OpePurchas> {
    int updateBatch(List<OpePurchas> list);

    int batchInsert(@Param("list") List<OpePurchas> list);

    int insertOrUpdate(OpePurchas record);

    int insertOrUpdateSelective(OpePurchas record);
}