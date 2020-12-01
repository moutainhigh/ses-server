package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeSaleParts;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OpeSalePartsMapper extends BaseMapper<OpeSaleParts> {
    int updateBatch(List<OpeSaleParts> list);

    int batchInsert(@Param("list") List<OpeSaleParts> list);

    int insertOrUpdate(OpeSaleParts record);

    int insertOrUpdateSelective(OpeSaleParts record);
}