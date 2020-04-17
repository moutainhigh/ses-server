package com.redescooter.ses.web.ros.service.base;

import java.util.List;
import com.redescooter.ses.web.ros.dm.OpeWhse;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface OpeWhseService extends IService<OpeWhse>{


    int batchInsert(List<OpeWhse> list);

    int insertOrUpdate(OpeWhse record);

    int insertOrUpdateSelective(OpeWhse record);

}
