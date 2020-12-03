package com.redescooter.ses.admin.dev.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.admin.dev.dm.AdmScooter;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AdmScooterMapper extends BaseMapper<AdmScooter> {
    int updateBatch(List<AdmScooter> list);

    int updateBatchSelective(List<AdmScooter> list);

    int batchInsert(@Param("list") List<AdmScooter> list);

    int insertOrUpdate(AdmScooter record);

    int insertOrUpdateSelective(AdmScooter record);
}