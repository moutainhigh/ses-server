package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeSaleScooter;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OpeSaleScooterMapper extends BaseMapper<OpeSaleScooter> {
    int updateBatch(List<OpeSaleScooter> list);

    int batchInsert(@Param("list") List<OpeSaleScooter> list);

    int insertOrUpdate(OpeSaleScooter record);

    int insertOrUpdateSelective(OpeSaleScooter record);
}