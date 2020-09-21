package com.redescooter.ses.service.foundation.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.service.foundation.dm.base.PlaSysGroupSetting;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface PlaSysGroupSettingService extends IService<PlaSysGroupSetting> {


    int updateBatch(List<PlaSysGroupSetting> list);

    int batchInsert(List<PlaSysGroupSetting> list);

    int insertOrUpdate(PlaSysGroupSetting record);

    int insertOrUpdateSelective(PlaSysGroupSetting record);

}

