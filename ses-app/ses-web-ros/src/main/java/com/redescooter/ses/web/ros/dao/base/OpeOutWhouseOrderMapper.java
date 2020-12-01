package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeOutWhouseOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OpeOutWhouseOrderMapper extends BaseMapper<OpeOutWhouseOrder> {
    int updateBatch(List<OpeOutWhouseOrder> list);

    int batchInsert(@Param("list") List<OpeOutWhouseOrder> list);

    int insertOrUpdate(OpeOutWhouseOrder record);

    int insertOrUpdateSelective(OpeOutWhouseOrder record);
}