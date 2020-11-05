package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeEntrustCombinB;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OpeEntrustCombinBMapper extends BaseMapper<OpeEntrustCombinB> {
    int updateBatch(List<OpeEntrustCombinB> list);

    int batchInsert(@Param("list") List<OpeEntrustCombinB> list);

    int insertOrUpdate(OpeEntrustCombinB record);

    int insertOrUpdateSelective(OpeEntrustCombinB record);
}