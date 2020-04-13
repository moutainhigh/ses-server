package com.redescooter.ses.service.mobile.c.service.base;

import com.redescooter.ses.service.mobile.c.dm.base.ConUserScooter;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
public interface ConUserScooterService extends IService<ConUserScooter>{


    int updateBatch(List<ConUserScooter> list);

    int batchInsert(List<ConUserScooter> list);

    int insertOrUpdate(ConUserScooter record);

    int insertOrUpdateSelective(ConUserScooter record);

}
