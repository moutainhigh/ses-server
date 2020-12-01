package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeAllocateScooterB;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface OpeAllocateScooterBMapper extends BaseMapper<OpeAllocateScooterB> {
    int updateBatch(List<OpeAllocateScooterB> list);

    int batchInsert(@Param("list") List<OpeAllocateScooterB> list);

    int insertOrUpdate(OpeAllocateScooterB record);

    int insertOrUpdateSelective(OpeAllocateScooterB record);
}