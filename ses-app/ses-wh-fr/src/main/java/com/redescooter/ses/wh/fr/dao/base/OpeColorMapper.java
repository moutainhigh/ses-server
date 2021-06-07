package com.redescooter.ses.wh.fr.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.wh.fr.dm.OpeColor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OpeColorMapper extends BaseMapper<OpeColor> {
    int updateBatch(List<OpeColor> list);

    int batchInsert(@Param("list") List<OpeColor> list);

    int insertOrUpdate(OpeColor record);

    int insertOrUpdateSelective(OpeColor record);
}