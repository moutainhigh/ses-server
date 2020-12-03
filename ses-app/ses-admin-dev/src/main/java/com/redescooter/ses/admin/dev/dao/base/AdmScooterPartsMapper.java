package com.redescooter.ses.admin.dev.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.admin.dev.dm.AdmScooterParts;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AdmScooterPartsMapper extends BaseMapper<AdmScooterParts> {
    int updateBatch(List<AdmScooterParts> list);

    int updateBatchSelective(List<AdmScooterParts> list);

    int batchInsert(@Param("list") List<AdmScooterParts> list);

    int insertOrUpdate(AdmScooterParts record);

    int insertOrUpdateSelective(AdmScooterParts record);
}