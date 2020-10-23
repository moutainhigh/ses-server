package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeOutWhCombinB;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface OpeOutWhCombinBMapper extends BaseMapper<OpeOutWhCombinB> {
    int updateBatch(List<OpeOutWhCombinB> list);

    int batchInsert(@Param("list") List<OpeOutWhCombinB> list);

    int insertOrUpdate(OpeOutWhCombinB record);

    int insertOrUpdateSelective(OpeOutWhCombinB record);
}