package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeOutWhScooterB;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface OpeOutWhScooterBMapper extends BaseMapper<OpeOutWhScooterB> {
    int updateBatch(List<OpeOutWhScooterB> list);

    int batchInsert(@Param("list") List<OpeOutWhScooterB> list);

    int insertOrUpdate(OpeOutWhScooterB record);

    int insertOrUpdateSelective(OpeOutWhScooterB record);
}