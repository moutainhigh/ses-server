package com.redescooter.ses.service.foundation.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.service.foundation.dm.base.PlaSysParamSetting;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface PlaSysParamSettingService extends IService<PlaSysParamSetting> {


    int updateBatch(List<PlaSysParamSetting> list);

    int batchInsert(List<PlaSysParamSetting> list);

    int insertOrUpdate(PlaSysParamSetting record);

    int insertOrUpdateSelective(PlaSysParamSetting record);

}


