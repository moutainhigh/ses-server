package com.redescooter.ses.service.foundation.service.base;

import java.util.List;
import com.redescooter.ses.service.foundation.dm.base.PlaMailTemplate;
import com.baomidou.mybatisplus.extension.service.IService;
public interface PlaMailTemplateService extends IService<PlaMailTemplate>{


    int batchInsert(List<PlaMailTemplate> list);

    int insertOrUpdate(PlaMailTemplate record);

    int insertOrUpdateSelective(PlaMailTemplate record);

    int insertOrUpdateWithBLOBs(PlaMailTemplate record);

}
