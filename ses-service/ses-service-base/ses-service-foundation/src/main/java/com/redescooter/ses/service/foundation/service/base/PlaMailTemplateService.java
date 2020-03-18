package com.redescooter.ses.service.foundation.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.service.foundation.dm.base.PlaMailTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface PlaMailTemplateService extends IService<PlaMailTemplate> {


    int batchInsert(List<PlaMailTemplate> list);

    int insertOrUpdate(PlaMailTemplate record);

    int insertOrUpdateSelective(PlaMailTemplate record);

    int insertOrUpdateWithBLOBs(PlaMailTemplate record);

}
