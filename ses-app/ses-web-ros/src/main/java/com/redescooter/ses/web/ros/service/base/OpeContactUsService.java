package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeContactUs;

import java.util.List;

public interface OpeContactUsService extends IService<OpeContactUs>{


    int updateBatch(List<OpeContactUs> list);

    int batchInsert(List<OpeContactUs> list);

    int insertOrUpdate(OpeContactUs record);

    int insertOrUpdateSelective(OpeContactUs record);

}
