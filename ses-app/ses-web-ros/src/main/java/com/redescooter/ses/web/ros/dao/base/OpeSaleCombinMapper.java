package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeSaleCombin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OpeSaleCombinMapper extends BaseMapper<OpeSaleCombin> {
    int updateBatch(List<OpeSaleCombin> list);

    int batchInsert(@Param("list") List<OpeSaleCombin> list);

    int insertOrUpdate(OpeSaleCombin record);

    int insertOrUpdateSelective(OpeSaleCombin record);
}