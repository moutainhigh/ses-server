package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeFactory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OpeFactoryMapper extends BaseMapper<OpeFactory> {
    int updateBatch(List<OpeFactory> list);

    int batchInsert(@Param("list") List<OpeFactory> list);

    int insertOrUpdate(OpeFactory record);

    int insertOrUpdateSelective(OpeFactory record);
}