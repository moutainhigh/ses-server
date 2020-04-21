package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeFactory;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpeFactoryMapper extends BaseMapper<OpeFactory> {
    int updateBatch(List<OpeFactory> list);

    int batchInsert(@Param("list") List<OpeFactory> list);

    int insertOrUpdate(OpeFactory record);

    int insertOrUpdateSelective(OpeFactory record);
}