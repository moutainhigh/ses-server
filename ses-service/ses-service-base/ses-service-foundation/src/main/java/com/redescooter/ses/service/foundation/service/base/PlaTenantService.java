package com.redescooter.ses.service.foundation.service.base;

import java.util.List;
import com.redescooter.ses.service.foundation.dm.base.PlaTenant;
import com.baomidou.mybatisplus.extension.service.IService;
public interface PlaTenantService extends IService<PlaTenant>{


    int updateBatch(List<PlaTenant> list);

    int batchInsert(List<PlaTenant> list);

    int insertOrUpdate(PlaTenant record);

    int insertOrUpdateSelective(PlaTenant record);

}
