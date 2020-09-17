package com.redescooter.ses.service.foundation.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.service.foundation.dm.base.PlaSysParamSetting;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface PlaSysParamSettingMapper extends BaseMapper<PlaSysParamSetting> {
    int updateBatch(List<PlaSysParamSetting> list);

    int batchInsert(@Param("list") List<PlaSysParamSetting> list);

    int insertOrUpdate(PlaSysParamSetting record);

    int insertOrUpdateSelective(PlaSysParamSetting record);
}