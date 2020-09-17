package com.redescooter.ses.service.foundation.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.service.foundation.dm.base.PlaSysGroupSetting;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface PlaSysGroupSettingMapper extends BaseMapper<PlaSysGroupSetting> {
    int updateBatch(List<PlaSysGroupSetting> list);

    int batchInsert(@Param("list") List<PlaSysGroupSetting> list);

    int insertOrUpdate(PlaSysGroupSetting record);

    int insertOrUpdateSelective(PlaSysGroupSetting record);
}