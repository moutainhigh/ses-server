package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeOpTrace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OpeOpTraceMapper extends BaseMapper<OpeOpTrace> {
    int updateBatch(List<OpeOpTrace> list);

    int batchInsert(@Param("list") List<OpeOpTrace> list);

    int insertOrUpdate(OpeOpTrace record);

    int insertOrUpdateSelective(OpeOpTrace record);
}