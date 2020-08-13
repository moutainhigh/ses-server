package com.redescooter.ses.web.ros.service.base;

import java.util.List;
import com.redescooter.ses.web.ros.dm.OpeContactUs;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface OpeContactUsService extends IService<OpeContactUs>{


    int updateBatch(List<OpeContactUs> list);

    int batchInsert(List<OpeContactUs> list);

    int insertOrUpdate(OpeContactUs record);

    int insertOrUpdateSelective(OpeContactUs record);

}
