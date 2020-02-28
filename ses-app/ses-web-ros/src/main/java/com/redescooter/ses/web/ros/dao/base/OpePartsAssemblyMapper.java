package com.redescooter.ses.web.ros.dao.base;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpePartsAssembly;

public interface OpePartsAssemblyMapper extends BaseMapper<OpePartsAssembly> {
    int updateBatch(List<OpePartsAssembly> list);

    int batchInsert(@Param("list") List<OpePartsAssembly> list);

    int insertOrUpdate(OpePartsAssembly record);

    int insertOrUpdateSelective(OpePartsAssembly record);
}