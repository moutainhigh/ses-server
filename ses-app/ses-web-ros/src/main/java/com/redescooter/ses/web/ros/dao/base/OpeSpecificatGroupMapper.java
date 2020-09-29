package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeSpecificatGroup;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OpeSpecificatGroupMapper extends BaseMapper<OpeSpecificatGroup> {
    int updateBatch(List<OpeSpecificatGroup> list);

    int updateBatchSelective(List<OpeSpecificatGroup> list);

    int batchInsert(@Param("list") List<OpeSpecificatGroup> list);

    int insertOrUpdate(OpeSpecificatGroup record);

    int insertOrUpdateSelective(OpeSpecificatGroup record);
}