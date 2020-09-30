package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeSpecificatDef;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OpeSpecificatDefMapper extends BaseMapper<OpeSpecificatDef> {
    int updateBatch(List<OpeSpecificatDef> list);

    int batchInsert(@Param("list") List<OpeSpecificatDef> list);

    int insertOrUpdate(OpeSpecificatDef record);

    int insertOrUpdateSelective(OpeSpecificatDef record);
}