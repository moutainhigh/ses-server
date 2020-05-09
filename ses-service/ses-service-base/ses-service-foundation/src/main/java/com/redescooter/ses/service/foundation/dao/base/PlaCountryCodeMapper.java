package com.redescooter.ses.service.foundation.dao.base;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.service.foundation.dm.base.PlaCountryCode;

public interface PlaCountryCodeMapper extends BaseMapper<PlaCountryCode> {
    int updateBatch(List<PlaCountryCode> list);

    int batchInsert(@Param("list") List<PlaCountryCode> list);

    int insertOrUpdate(PlaCountryCode record);

    int insertOrUpdateSelective(PlaCountryCode record);
}