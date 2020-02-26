package com.redescooter.ses.web.ros.dao.base;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpePartsAssemblyB;

public interface OpePartsAssemblyBMapper extends BaseMapper<OpePartsAssemblyB> {
    int updateBatch(List<OpePartsAssemblyB> list);

    int batchInsert(@Param("list") List<OpePartsAssemblyB> list);

    int insertOrUpdate(OpePartsAssemblyB record);

    int insertOrUpdateSelective(OpePartsAssemblyB record);
}